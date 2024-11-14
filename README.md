# eBDSMS OTP SDK Integration for Android
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
   implementation 'com.github.ebdsms:eBDSMSotp:2.0.1'
	}
```

# Constants for SMS OTP Send String
Add the following code inside your `Activity`:
``` gradle

    String API_KEY = "YOUR_API_KEY";
    String DEVICE_NUMBER = "YOUR_DEVICE_NUMBER";
    String NUMBER = "USER_NUMBER";
    String MESSAGE = "YOUR_MESSAGE";

```

# Send Process
Note: Use this code when the sms send button is clicked:
``` gradle
eBDSMS sms = new eBDSMS(MESSAGE, NUMBER, DEVICE_NUMBER, API_KEY, MainActivity.this);
sms.sendOTP(success -> {
    if (success){
     // OTP sent successfully
     Toast.makeText(MainActivity.this, "OTP sent successfully.", Toast.LENGTH_SHORT).show();
    }else {
     // OTP failed to send
     Toast.makeText(MainActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
   }
},4);

```
# Verify OTP Activity
Note: Use this code when the OTP Verify button is clicked:
``` gradle

String userOtp = "USER_OTP";
String userNumber = "USER_NUMBER";
        
eBDSMS sms2 = new eBDSMS(userOtp, userNumber, this);
sms2.verifyOTP(new eBDSMS.otpVerifyCallback() {
       @Override
    public void onResult(boolean success) {
     if (success) {
      // OTP verified successfully
     Toast.makeText(MainActivity.this, "OTP verified successfully.", Toast.LENGTH_SHORT).show();
   }else {
     // OTP verification failed
       Toast.makeText(MainActivity.this, "Failed to verify OTP", Toast.LENGTH_SHORT).show();
     }
   }
});
```

# Contact With Us
If you face any problem using this library then feel free to contact me.
To contact me message me on Facebook or email me at:

`Email`: info@ebdsms.com

`Facebook`: <a href="https://www.facebook.com/M220719" rel="nofollow">Shohag Hossain</a> 

# Authors
<a href="https://www.ebdsms.com" rel="nofollow">eBDSMS.com</a>
