package com.example.david.ei_timer4;

import android.graphics.Bitmap;
import android.net.Uri;

public class Timer {

    private String name;
    private long time;
    private Uri picture;
    private Uri ringtone;


    public Timer(String name, long time, Uri picture, Uri ringtone) {
        this.name = name;
        this.time = time;
        this.picture = picture;
        this.ringtone = ringtone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public Uri getRingtone() {
        return ringtone;
    }

    public void setRingtone(Uri ringtone) {
        this.ringtone = ringtone;
    }
}
