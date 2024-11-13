package ebdsms.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.ebdsms.otp.eBDSMS;


public class MainActivity extends AppCompatActivity {

     String API_KEY = "YOUR_API_KEY";
     String DEVICE_NUMBER = "DEVICE_NUMBER";

     String NUMBER = "SEND_NUMBER";
     String MESSAGE = "MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*eBDSMS.OTP otp = new eBDSMS.OTP();
        String otpString = otp.OTPString(6);
        System.out.println(otpString);*/

       /* eBDSMS sms = new eBDSMS(API_KEY,DEVICE_NUMBER,NUMBER,MESSAGE+" ",null,null);
        sms.sendSms(this);*/



        eBDSMS sms = new eBDSMS(API_KEY,DEVICE_NUMBER,NUMBER,MESSAGE,null,null,this, "");

        if (sms.SendOTP("01716537544","This is message",5).equals("true")){
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}