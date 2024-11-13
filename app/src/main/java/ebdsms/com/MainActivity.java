package ebdsms.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rbmjltd.ebdsms.eBDSMS_OTP;
import com.rbmjltd.ebdsms.eBDSMS;


public class MainActivity extends AppCompatActivity {

    private static String API_KEY = "YOUR_API_KEY";
    private static String DEVICE_NUMBER = "DEVICE_NUMBER";

    private static String NUMBER = "SEND_NUMBER";
    private static String MESSAGE = "MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        eBDSMS_OTP otpValue = new eBDSMS_OTP();
        String otp = otpValue.OTPString(6);
        MESSAGE = MESSAGE + otp;

        eBDSMS sms = new eBDSMS(API_KEY,DEVICE_NUMBER,MESSAGE,null,NUMBER,null,null);
        sms.sendSms(this);


    }
}