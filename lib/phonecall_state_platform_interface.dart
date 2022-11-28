import 'dart:async';

import 'package:flutter/services.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'phonecall_state_method_channel.dart';

abstract class PhonecallStatePlatform extends PlatformInterface {
  /// Constructs a PhonecallStatePlatform.
  PhonecallStatePlatform() : super(token: _token);

  static final Object _token = Object();

  static PhonecallStatePlatform _instance = MethodChannelPhonecallState();

  /// The default instance of [PhonecallStatePlatform] to use.
  ///
  /// Defaults to [MethodChannelPhonecallState].
  static PhonecallStatePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [PhonecallStatePlatform] when
  /// they register themselves.
  static set instance(PhonecallStatePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
  Future<String?> Listen(StreamSubscription eventChannel) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
