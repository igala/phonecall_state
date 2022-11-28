package com.inno.phonecall_state;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** PhonecallStatePlugin */
public class PhonecallStatePlugin implements FlutterPlugin, MethodCallHandler , EventChannel.StreamHandler, ActivityAware {
  private static final int REQUEST_PHONE_CALL = 9999 ;
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  TelephonyManager telephonyManager;
  private PhoneStateListener mPhoneListener;
  private CustomTelephonyCallback mPhoneListenerS;
  public static boolean phoneCallOn = false;
  private static final String PHONE_STATE =
          "PHONE_STATE_99";
  private Context context;
  private Activity activity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "phonecall_state");
    channel.setMethodCallHandler(this);
    telephonyManager = (TelephonyManager) flutterPluginBinding.getApplicationContext().getSystemService(flutterPluginBinding.getApplicationContext().TELEPHONY_SERVICE);
    final EventChannel phoneStateCallChannel =
            new EventChannel(flutterPluginBinding.getBinaryMessenger(), PHONE_STATE);
    phoneStateCallChannel.setStreamHandler(this);
    context = flutterPluginBinding.getApplicationContext();

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }


  }


  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }


  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {


    channel.setMethodCallHandler(null);
  }
  @RequiresApi(Build.VERSION_CODES.S)
  CustomTelephonyCallback createPhoneStateListenerS(final EventChannel.EventSink events){
//    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
      return new CustomTelephonyCallback(new CallBack() {

        @Override
        public void callStateChanged(int state) {

          switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
              phoneCallOn = false;
              break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
              phoneCallOn = true;
              break;
            case TelephonyManager.CALL_STATE_RINGING:
              phoneCallOn = false;
              break;
          }
          events.success(String.valueOf(phoneCallOn));
        }
      });
//    }
  }



  PhoneStateListener createPhoneStateListener(final EventChannel.EventSink events){
    return new PhoneStateListener(){
      @Override
      public void onCallStateChanged (int state, String phoneNumber){

        switch (state) {
          case TelephonyManager.CALL_STATE_IDLE:
            phoneCallOn = false;
            break;
          case TelephonyManager.CALL_STATE_OFFHOOK:
            phoneCallOn = true;
            break;
          case TelephonyManager.CALL_STATE_RINGING:
            phoneCallOn = false;
            break;
        }
        events.success(String.valueOf(phoneCallOn));
      }
    };
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink events) {


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (activity.checkSelfPermission( Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        activity.requestPermissions( new String[]{Manifest.permission.READ_PHONE_STATE},REQUEST_PHONE_CALL);
      }
      else
      {



    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

      mPhoneListenerS = createPhoneStateListenerS(events);
      telephonyManager.registerTelephonyCallback(context.getMainExecutor(),mPhoneListenerS);
    }
    else
    {
      mPhoneListener = createPhoneStateListener(events);
      telephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

      //telephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

      }
    }
  }

  @Override
  public void onCancel(Object arguments) {

  }
  @RequiresApi(Build.VERSION_CODES.S)
  class CustomTelephonyCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
    private CallBack mCallBack;

    public CustomTelephonyCallback(CallBack callBack) {
      mCallBack = callBack;
    }

    @Override
    public void onCallStateChanged(int state) {

      mCallBack.callStateChanged(state);

    }
  }
  interface CallBack {
    void callStateChanged(int state);
  }
}
