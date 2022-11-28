
import 'package:flutter/services.dart';

import 'phonecall_state_platform_interface.dart';

class PhonecallState {
  Future<String?> getPlatformVersion() {
    return PhonecallStatePlatform.instance.getPlatformVersion();
  }

}
