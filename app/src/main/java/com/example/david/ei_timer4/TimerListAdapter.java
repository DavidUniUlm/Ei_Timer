package com.example.david.ei_timer4;

import android.content.Context;
import android.net.Uri;
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
        String name = getItem(position).getName();
        long time = getItem(position).getTime();
        Uri picture = getItem(position).getPicture();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView iv = convertView.findViewById(R.id.imageViewTimerLayout);
        TextView nameTv = convertView.findViewById(R.id.nameTimerLayout);
        TextView countdownTv = convertView.findViewById(R.id.countdownTimerLayout);

        System.out.println("Uri crash\n" + picture);
        iv.setImageURI(picture);
        nameTv.setText(name);
        countdownTv.setText("" + time);

        return convertView;
    }
}
