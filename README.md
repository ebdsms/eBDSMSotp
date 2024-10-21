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
	        implementation 'com.github.ebdsms:eBDSMS:2.0.1'
	}
```

# Constants for SMS Send String
Add the following code inside your `Activity`:
``` gradle

    private static String API_KEY = "YOUR_API_KEY";
    private static String NUMBER = "SEND_NUMBER";
    private static String MESSAGE = "MESSAGE";
    private static String OTP = "OTP";
    private static String DEVICE_NUMBER = "DEVICE_NUMBER";
```  

# SMS Send Process
Note: Use this code when the sms send button is clicked:
``` gradle
  eBDSMS sms = new eBDSMS(API_KEY,NUMBER,MESSAGE,OTP,DEVICE_NUMBER,null);
  sms.sendSms(this);
```
# Contact With Us
If you face any problem using this library then feel free to contact me.
To contact me message me on Facebook or email me at:

`Email`: info@ebdsms.com

`Facebook`: <a href="https://www.facebook.com/M220719" rel="nofollow">Monir Hossain (Shohag)</a> 

# Authors
<a href="https://www.ebdsms.com" rel="nofollow">eBDSMS.com</a>
