package com.chaatgadrive.arif.chaatgadrive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RiderDocumentsActivity extends AppCompatActivity {

    private LinearLayout notificationView,nidView,bike_registration_view,driving_license_view;
    private CircleImageView close_notification_button, nidImageView, bikeRegistrationImageView,drivingLicenseImageView;

    private static final int RESULT_LOAD_IMAGE = 0;
    private final int NID_IMAGE_RESPONSE = 1;
    private final int BIKE_REGISTRATION_RESPONSE = 2;
    private final int DRIVING_LICENSE_RESPONSE = 3;
    private final int PIC_CROP = 4;
    private Uri picUri;
    private File nidImage, bikeRegistrationImage, drivingLicenseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_documents);
        notificationView = findViewById(R.id.notification_submission);
        nidView = findViewById(R.id.nid_view);
        bike_registration_view = findViewById(R.id.bike_registration_view);
        driving_license_view = findViewById(R.id.driving_license_view);
        nidImageView = (CircleImageView) findViewById(R.id.nid_image);
        bikeRegistrationImageView = (CircleImageView) findViewById(R.id.bike_registraion_image);
        drivingLicenseImageView = (CircleImageView) findViewById(R.id.driving_license_image);
        close_notification_button = (CircleImageView) findViewById(R.id.close_notification_button);

        close_notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationView.setVisibility(View.GONE);
            }
        });

        nidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, NID_IMAGE_RESPONSE);
            }
        });

        bike_registration_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, BIKE_REGISTRATION_RESPONSE);
            }
        });

        driving_license_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, DRIVING_LICENSE_RESPONSE);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ){


            picUri = data.getData();

            Bitmap thePic = null;
            try {
                thePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thePic.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            switch (requestCode){
                case NID_IMAGE_RESPONSE:
                    nidImage = new File(getApplicationContext().getCacheDir(),"nidImage.png");
                    try {
                        nidImage.createNewFile();
                        FileOutputStream fos = new FileOutputStream(nidImage);
                        fos.write(byteArray);
                        fos.flush();
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //imageEncodedToBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    nidImageView.setImageBitmap(thePic);
                    break;
                case BIKE_REGISTRATION_RESPONSE:
                    bikeRegistrationImage = new File(getApplicationContext().getCacheDir(),"nidImage.png");
                    try {
                        bikeRegistrationImage.createNewFile();
                        FileOutputStream fos = new FileOutputStream(nidImage);
                        fos.write(byteArray);
                        fos.flush();
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //imageEncodedToBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    bikeRegistrationImageView.setImageBitmap(thePic);
                    break;

                case DRIVING_LICENSE_RESPONSE:
                    drivingLicenseImage = new File(getApplicationContext().getCacheDir(),"nidImage.png");
                    try {
                        drivingLicenseImage.createNewFile();
                        FileOutputStream fos = new FileOutputStream(nidImage);
                        fos.write(byteArray);
                        fos.flush();
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //imageEncodedToBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    drivingLicenseImageView.setImageBitmap(thePic);
                    break;

                default:
                    Toast.makeText(getApplicationContext(),"Error ocurred",Toast.LENGTH_LONG).show();
                    break;
            }

        }
    }


}

