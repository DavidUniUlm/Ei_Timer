package com.example.david.ei_timer4;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class TimerFinished extends AppCompatActivity {

    TextView timerName;
    TextView closeTextView;
    ImageView finishedImageView;
    Ringtone r;
    CountDownTimer countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_finished);

        timerName = findViewById(R.id.timerName);
        finishedImageView = findViewById(R.id.finishedImageView);
        closeTextView = findViewById(R.id.closeTextView);

        timerName.setText(getIntent().getExtras().getString("name"));
        finishedImageView.setImageURI((Uri) (getIntent().getExtras().get("picture")));

        Uri ringtone = (Uri) (getIntent().getExtras().get("ringtone"));
        System.out.println(ringtone.toString());
        r = RingtoneManager.getRingtone(getApplicationContext(), ringtone);
        r.play();

        start10secTimer();
    }

    private void start10secTimer() {
        countdown = new CountDownTimer(10000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                closeTextView.setText("closes in 0" + millisUntilFinished / 1000 + " seconds");
            }
            @Override
            public void onFinish() {
                closeActivity();
            }
        }.start();
    }

    public void onOkButtonClick(View view) {
        closeActivity();
    }

    private void closeActivity() {
        r.stop();
        if (countdown != null) {
            countdown.cancel();
        }
        setResult(Activity.RESULT_OK, null);
        finish();
    }


}
