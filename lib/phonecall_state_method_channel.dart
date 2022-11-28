import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'phonecall_state_platform_interface.dart';

/// An implementation of [PhonecallStatePlatform] that uses method channels.
class MethodChannelPhonecallState extends PhonecallStatePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('phonecall_state');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
  Future<String?> listen(StreamSubscription eventChannel) async {
    final version = await methodChannel.invokeMethod<String>('listen',eventChannel);
    return version;
  }
}
