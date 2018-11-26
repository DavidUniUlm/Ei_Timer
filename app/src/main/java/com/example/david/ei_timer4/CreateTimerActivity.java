package com.example.david.ei_timer4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTimerActivity extends AppCompatActivity {

    private static final int PICK_PHOTO = 0;
    private static final int PICK_RINGTONE = 1;

    EditText editText;
    ImageView imageView;

    NumberPicker numberPicker_Hour;
    NumberPicker numberPicker_Min;
    NumberPicker numberPicker_Sec;

    private String name = "NEW TIMER";
    private long time;
    private Uri picture;
    private Uri ringtone = Uri.parse("content://media/internal/audio/media/12");


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 7;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.david.ei_timer4.fileprovider",
                        photoFile);
                System.out.println("photoUri\n" + photoURI);
                imageView.setImageURI(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);
        //takePhotoButton = (Button)findViewById(R.id.takePhotoButton);
        editText = findViewById(R.id.editText);
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
        dispatchTakePictureIntent();
////        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        startActivityForResult(intent, PICK_FROM_CAMERA);
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // Camera
//        Intent intent2 = new Intent(Intent.ACTION_PICK);                // Gallery
//        intent2.setType("image/*");
//        intent2.setData(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        intent2.putExtra("return-data", true);
//
//        Intent intent3 = Intent.createChooser(intent, "Choose Picture");
//        intent3.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{
//                intent2
//        });
//
//        startActivityForResult(intent3, PICK_PHOTO);
    }

    public void onChooseRingtoneButtonClick(View view) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, PICK_RINGTONE);
    }

    public void onDoneButtonClick(View view) {
        time = numberPicker_Hour.getValue() * 3600 + numberPicker_Min.getValue() * 60
                + numberPicker_Sec.getValue();
        if (time == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(CreateTimerActivity.this).create();
            alertDialog.setTitle("Notification");
            alertDialog.setMessage("YOU HAVE TO SET A TIMER");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        String timerName = editText.getText().toString();
        if (!timerName.equals("")) {
            name = timerName;
        }

        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("time", time);
        intent.putExtra("picture", picture);
        intent.putExtra("ringtone", ringtone);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void onAbortButtonClick(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    Uri imageFromCamera = data.getData();
                    System.out.println(data.getData());
                    if (imageFromCamera != null) {                                 // Gallery
                        imageView.setImageURI(imageFromCamera);
                    }
                    break;
                case PICK_PHOTO:
                    Uri imageFromGallery = data.getData();
                    if (imageFromGallery != null) {                                 // Gallery
                        imageView.setImageURI(imageFromGallery);
                    } else {                                                        // Cam
                        Bitmap picFromCam = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(picFromCam);
                    }
                    break;

                case PICK_RINGTONE:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        ringtone = uri;
                    }
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                r.play();
                    break;
            }
        }

    }
}
