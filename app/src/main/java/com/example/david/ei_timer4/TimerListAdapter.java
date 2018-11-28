package com.example.david.ei_timer4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerListAdapter extends ArrayAdapter<Timer> {

    private Context mContext;
    int mResource;

    public TimerListAdapter(Context context, int resource, ArrayList<Timer> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final String name = getItem(position).getName();
        long time = getItem(position).getTime();
        final Uri picture = getItem(position).getPicture();
        final Uri ringtone = getItem(position).getRingtone();
        final boolean isRunning = getItem(position).isRunning();

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView iv = convertView.findViewById(R.id.imageViewTimerLayout);
        TextView nameTv = convertView.findViewById(R.id.nameTimerLayout);
        final TextView countdownTv = convertView.findViewById(R.id.countdownTimerLayout);

        iv.setImageURI(picture);
        nameTv.setText(name);
        countdownTv.setText(millisToString(time));

//        startCountdownTimer(time, 1000);

        final CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTv.setText(millisToString(millisUntilFinished));
                getItem(position).setTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                getItem(position).setTime(0);
                getItem(position).setRunning(false);
                Intent intent = new Intent(mContext, TimerFinished.class);
                intent.putExtra("name", name);
                intent.putExtra("picture", picture);
                intent.putExtra("ringtone", ringtone);
                mContext.startActivity(intent);
            }
        };

        if (time != 0 && isRunning) {
            countDownTimer.start();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).getTime() != 0) {
                    if(isRunning){
                        countDownTimer.cancel();
                    }
                    else{
                        countDownTimer.start();
                    }
                    getItem(position).setRunning(!isRunning);
                }
            }
        });
        return convertView;
    }

    private void startCountdownTimer(long time, int i) {
    }

    private String millisToString(long millis) {
        int hour = (int) millis / 1000 / 3600;
        int min = (int) (millis / 1000 % 3600) / 60;
        int sec = (int) millis / 1000 % 3600 % 60;

        String timeLeft = "";

        if (hour < 10) {
            timeLeft += "0";
        }
        timeLeft += hour + ":";
        if (min < 10) {
            timeLeft += "0";
        }
        timeLeft += min + ":";

        if (sec < 10) {
            timeLeft += "0";
        }
        timeLeft += sec;
        return timeLeft;
    }
}
