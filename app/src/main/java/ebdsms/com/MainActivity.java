package ebdsms.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.UpdatedCircle.ImageView.UpdatedCircleImg;

public class MainActivity extends AppCompatActivity {

    UpdatedCircleImg updatedCircleImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updatedCircleImg = findViewById(R.id.CircleImgId);
        updatedCircleImg.setImageResource(R.drawable.new_pc);

    }
}