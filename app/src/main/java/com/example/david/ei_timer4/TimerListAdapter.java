package com.example.david.ei_timer4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
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


    public TimerListAdapter(Context context, int resource, ArrayList<Timer> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView iv = convertView.findViewById(R.id.imageViewTimerLayout);
        TextView nameTv = convertView.findViewById(R.id.nameTimerLayout);
        final TextView countdownTv = convertView.findViewById(R.id.countdownTimerLayout);

        iv.setImageURI(getItem(position).getPicture());
        nameTv.setText(getItem(position).getName());
        countdownTv.setText(millisToString(getItem(position).getTime()));

        convertView.setOnClickListener(new View.OnClickListener() {

            CountDownTimer countDownTimer;

            @Override
            public void onClick(View v) {

//                TODO: Bug
//                Timer 1 starten
//                Neuen timer hinzufügen
//                Timer 1 anhalten – wieder starten und die zeit springt

                if (getItem(position).isRunning() && countDownTimer != null) {
                    countDownTimer.cancel();
                    //getItem(position).setRunning(false);
                } else {
                    countDownTimer = new CountDownTimer(getItem(position).getTime(), 1000) {
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
                            intent.putExtra("name", getItem(position).getName());
                            intent.putExtra("picture", getItem(position).getPicture());
                            intent.putExtra("ringtone", getItem(position).getRingtone());
                            mContext.startActivity(intent);
                        }
                    };
                    countDownTimer.start();
                    //getItem(position).setRunning(true);
                }
                getItem(position).setRunning(!getItem(position).isRunning());
            }
        });
        if (getItem(position).isRunning()) {
            convertView.callOnClick();
        }
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
