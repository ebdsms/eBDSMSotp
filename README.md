# eBDSMS SDK Integration for Android
This document provides instructions for integrating the eBDSMS Android SDK into your project. 

# Getting Started
> 
How to use ?

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file 

``` build.gradle

dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
 ```
> Step 2. Add the dependency to your `build.gradle`:
``` gradle
dependencies {
	        implementation 'com.github.ebdsms:eBDSMS:2.0.2'
	}
```

# Constants for SMS Send String
Add the following code inside your `Activity`:
``` gradle

    private static String API_KEY = "YOUR_API_KEY";
    private static String DEVICE_NUMBER = "DEVICE_NUMBER";
    private static String NUMBER = "SEND_NUMBER";
    private static String MESSAGE = "MESSAGE";
```  

# SMS Send Process
Note: Use this code when the sms send button is clicked:
``` gradle
 eBDSMS sms = new eBDSMS(API_KEY,NUMBER,MESSAGE,null,DEVICE_NUMBER,null,null);
 sms.sendSms(this);
```
# OTP Send Process
Note: Use this code send otp:
``` gradle
eBDSMS_OTP otpValue = new eBDSMS_OTP();
String otp = otpValue.OTPString(6); // Enter the value of the number you want to send OTP like 4,6
```

# eBDSMS SDK Integration for Flutter
This document provides instructions for integrating the eBDSMS Flutter project. 

# Getting Started
> 
How to use ?
# yaml
``` gradle
  dependencies:
  flutter:
  sdk: flutter
  http: ^0.14.0  # Make sure to use the latest version
```
# Implement the eBDSMS class in Flutter:
``` gradle
  import 'dart:convert';
import 'package:http/http.dart' as http;

class EBDSMS {
  String apiKey;
  String number;
  String message;
  String otp;
  String device;
  String extra;

  EBDSMS({
    required this.apiKey,
    required this.number,
    required this.message,
    required this.otp,
    required this.device,
    required this.extra,
  });

  Future<void> sendSms() async {
    String baseUrl = 'https://client.ebdsms.com/services/send.php';
    String url = '$baseUrl?key=$apiKey&number=$number&message=$message&devices=$device&type=sms&prioritize=0';

    try {
      final response = await http.get(Uri.parse(url));

      if (response.statusCode == 200) {
        // Successfully sent the SMS, handle response
        print('SMS Sent: ${response.body}');
      } else {
        // Error handling
        print('Failed to send SMS: ${response.statusCode}');
      }
    } catch (e) {
      print('Error occurred while sending SMS: $e');
    }
  }
}

```
# How to use:
> 
dart
``` gradle
 void main() {
  EBDSMS sms = EBDSMS(
    apiKey: 'your_api_key',
    number: 'recipient_number',
    message: 'Hello, this is your message!',
    otp: '1234',
    device: 'device_id',
    extra: 'extra_info',
  );

  sms.sendSms();
}

```



# Contact With Us
If you face any problem using this library then feel free to contact me.
To contact me message me on Facebook or email me at:

`Email`: info@ebdsms.com

`Facebook`: <a href="https://www.facebook.com/M220719" rel="nofollow">Shohag Hossain</a> 

# Authors
<a href="https://www.ebdsms.com" rel="nofollow">eBDSMS.com</a>
