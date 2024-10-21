package ebdsms.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rbmjltd.ebdsms.eBDSMS;


public class MainActivity extends AppCompatActivity {

    private static String API_KEY = "YOUR_API_KEY";
    private static String NUMBER = "SEND_NUMBER";
    private static String MESSAGE = "MESSAGE";
    private static String OTP = "OTP";
    private static String DEVICE_NUMBER = "DEVICE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        eBDSMS sms = new eBDSMS(API_KEY,NUMBER,MESSAGE,OTP,DEVICE_NUMBER,null,null);
        sms.sendSms(this);


    }
}