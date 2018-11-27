package com.example.david.ei_timer4;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TimerFinished extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_finished);
    }

    public void onOkButtonClick(View view){
        setResult(Activity.RESULT_OK, null);
        finish();
    }



}
