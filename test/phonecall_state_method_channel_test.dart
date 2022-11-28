import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:phonecall_state/phonecall_state_method_channel.dart';

void main() {
  MethodChannelPhonecallState platform = MethodChannelPhonecallState();
  const MethodChannel channel = MethodChannel('phonecall_state');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
