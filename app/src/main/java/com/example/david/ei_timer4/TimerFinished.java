package com.example.david.ei_timer4;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerFinished extends AppCompatActivity {


    TextView timerName;
    ImageView finishedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_finished);

        timerName = findViewById(R.id.timerName2);
        finishedImageView = findViewById(R.id.finishedImageView);

        timerName.setText(getIntent().getExtras().getString("name"));
        finishedImageView.setImageURI((Uri) (getIntent().getExtras().get("picture")));

        Uri ringtone = (Uri) (getIntent().getExtras().get("ringtone"));
        System.out.println(ringtone.toString());
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ringtone);
        r.play();
    }


    public void onOkButtonClick(View view) {
        setResult(Activity.RESULT_OK, null);
        finish();
    }


}
