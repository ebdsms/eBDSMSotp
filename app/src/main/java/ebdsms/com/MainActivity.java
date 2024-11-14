package ebdsms.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.ebdsms.otp.eBDSMS;


public class MainActivity extends AppCompatActivity {

     String API_KEY = "6ac4a89461dfb5c13198da93d14bcc12b7500834";
     String DEVICE_NUMBER = "28";

     String NUMBER = "01716537544";
     String MESSAGE = "This is Your OTP: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        eBDSMS sms = new eBDSMS(MESSAGE, NUMBER, DEVICE_NUMBER, API_KEY, this);
        sms.sendOTP(success -> {
            if (success){
                // OTP sent successfully
                Toast.makeText(MainActivity.this, "OTP sent successfully.", Toast.LENGTH_SHORT).show();
            }else {
                // OTP failed to send
                Toast.makeText(MainActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
            }
        },4);


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




    }
}