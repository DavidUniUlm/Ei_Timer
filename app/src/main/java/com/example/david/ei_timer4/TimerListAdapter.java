package com.example.david.ei_timer4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class TimerListAdapter extends ArrayAdapter<Timer> {

    private Context mContext;
    int mResource;
    //private Sparse s = new SparseArray<>()
    HashMap<View, CountDownTimer> hashMap = new HashMap<>();


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

//        CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                countdownTv.setText(millisToString(millisUntilFinished));
//                getItem(position).setTime(millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                getItem(position).setTime(0);
//                getItem(position).setRunning(false);
//                Intent intent = new Intent(mContext, TimerFinished.class);
//                intent.putExtra("name", name);
//                intent.putExtra("picture", picture);
//                intent.putExtra("ringtone", ringtone);
//                mContext.startActivity(intent);
//            }
//        };

//        if (time != 0 && isRunning) {
//            countDownTimer.start();
//        }

        hashMap.put(convertView, null);

        convertView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                if (getItem(position).getTime() != 0) {
                    if(getItem(position).isRunning()){
                        hashMap.get(v).cancel();
                    }
                    else{
                        //getItem(position).setRunning(true);
                        hashMap.replace(v, new CountDownTimer(getItem(position).getTime(), 1000) {
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
                        }.start());

                }
                getItem(position).setRunning(!(getItem(position).isRunning()));
            }
        });

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
