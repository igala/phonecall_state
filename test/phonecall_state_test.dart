import 'dart:async';

import 'package:flutter_test/flutter_test.dart';
import 'package:phonecall_state/phonecall_state.dart';
import 'package:phonecall_state/phonecall_state_platform_interface.dart';
import 'package:phonecall_state/phonecall_state_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockPhonecallStatePlatform
    with MockPlatformInterfaceMixin
    implements PhonecallStatePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<String?> Listen(StreamSubscription eventChannel) {
    // TODO: implement Listen
    throw UnimplementedError();
  }
}

void main() {
  final PhonecallStatePlatform initialPlatform = PhonecallStatePlatform.instance;

  test('$MethodChannelPhonecallState is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelPhonecallState>());
  });

  test('getPlatformVersion', () async {
    PhonecallState phonecallStatePlugin = PhonecallState();
    MockPhonecallStatePlatform fakePlatform = MockPhonecallStatePlatform();
    PhonecallStatePlatform.instance = fakePlatform;

    expect(await phonecallStatePlugin.getPlatformVersion(), '42');
  });
}
