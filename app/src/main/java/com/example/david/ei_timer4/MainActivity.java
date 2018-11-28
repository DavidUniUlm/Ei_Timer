package com.example.david.ei_timer4;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String[] PERMS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int REQUEST_CREATE_TIMER = 42;

    ListView listView;
    ArrayList<Timer> timerList = new ArrayList<>();
    TimerListAdapter tla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modifiedPermissions(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTimerActivity.class);
                startActivityForResult(intent, REQUEST_CREATE_TIMER);
            }
        });

        listView = findViewById(R.id.listView);
        tla = new TimerListAdapter(this, R.layout.timer_layout, timerList);
        listView.setAdapter(tla);
    }

    private void modifiedPermissions(Activity activity) {
        int storagePerm = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (storagePerm != PackageManager.PERMISSION_GRANTED || camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMS, 12);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_TIMER:
                    String name = data.getExtras().getString("name");
                    long time = data.getExtras().getLong("time");
                    Uri picture = (Uri) data.getExtras().get("picture");
                    Uri ringtone = (Uri) data.getExtras().get("ringtone");

                    Log.i("name", name);
                    Log.i("time", "" + time);
                    Log.i("picture", String.valueOf(picture));
                    Log.i("ringtoe", ringtone.toString());

                    timerList.add(new Timer(name, time, picture, ringtone));
                    tla.notifyDataSetChanged();
                    //tla.add(timerList.get(timerList.size()-1));
                    break;
            }
        }
    }

    public void dataChanged(){
        tla.notifyDataSetChanged();
    }
}
