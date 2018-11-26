package com.example.david.ei_timer4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import java.io.IOException;

public class CreateTimerActivity extends AppCompatActivity {

    NumberPicker numberPicker_Hour;
    NumberPicker numberPicker_Min;
    NumberPicker numberPicker_Sec;

    ImageView imageView;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_FILE = 1;
    private static final int PICK_FROM_RINGTONE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);

        //takePhotoButton = (Button)findViewById(R.id.takePhotoButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        initializeNumberPicker();
    }

    private void initializeNumberPicker() {

        numberPicker_Hour = findViewById(R.id.hour);
        numberPicker_Min = findViewById(R.id.min);
        numberPicker_Sec = findViewById(R.id.sec);

        numberPicker_Hour.setMaxValue(99);
        numberPicker_Min.setMaxValue(59);
        numberPicker_Sec.setMaxValue(59);
        
        numberPicker_Hour.setMinValue(0);
        numberPicker_Min.setMinValue(0);
        numberPicker_Sec.setMinValue(0);
    }

    public void onPhotoButtonClick(View view) {
        //Achtung: Stürzt ab, wenn das Foto verworfen wird
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, PICK_FROM_CAMERA);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent intent2 = new Intent(Intent.ACTION_PICK);
        intent2.setType("image/*");
        intent2.setData(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent2.putExtra("return-data", true);

        Intent intent3 = Intent.createChooser(intent, "sdjfsal");
        ((Intent) intent3).putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{
                intent2
        });

        startActivityForResult(intent3, PICK_FROM_CAMERA);


    }

    public void onChoosePhotoFromGalleryButtonClick(View view) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), PICK_FROM_FILE);
    }

    public void onChooseRingtoneButtonClick(View view) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, PICK_FROM_RINGTONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    Uri imageFromGallery = data.getData();
                    if (imageFromGallery == null) {
                        Bitmap picFromCam = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(picFromCam);
                    } else {
                        imageView.setImageURI(imageFromGallery);
                    }
                    break;


                case PICK_FROM_FILE:
                    Uri image = data.getData();
                    try {
                        Bitmap picFromFile = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                        imageView.setImageBitmap(picFromFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case PICK_FROM_RINGTONE:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        String ringTonePath = uri.toString();
                        System.out.println(ringTonePath);
                    }
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                r.play();
                    break;
            }
        }

    }
}
