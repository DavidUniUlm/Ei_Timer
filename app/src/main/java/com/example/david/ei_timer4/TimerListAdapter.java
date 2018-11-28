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
    public View getView(int position, View convertView, ViewGroup parent) {
        final String name = getItem(position).getName();
        long time = getItem(position).getTime();
        final Uri picture = getItem(position).getPicture();
        final Uri ringtone = getItem(position).getRingtone();

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView iv = convertView.findViewById(R.id.imageViewTimerLayout);
        TextView nameTv = convertView.findViewById(R.id.nameTimerLayout);
        final TextView countdownTv = convertView.findViewById(R.id.countdownTimerLayout);

        System.out.println("Uri crash\n" + picture);
        iv.setImageURI(picture);
        nameTv.setText(name);
        countdownTv.setText("" + time);


        CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTv.setText(millisToString(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                System.out.println("onFinish");
                Intent intent = new Intent(mContext, TimerFinished.class);
                intent.putExtra("name", name);
                intent.putExtra("picture", picture);
                intent.putExtra("ringtone", ringtone);
                mContext.startActivity(intent);
            }
        }.start();

        return convertView;
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
