package com.chaatgadrive.arif.chaatgadrive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiderDocumentsActivity extends AppCompatActivity {

    private LinearLayout notificationView,nidView,bike_registration_view,driving_license_view;
    private CircleImageView close_notification_button, nidImageView, bikeRegistrationImageView,drivingLicenseImageView;
    private Button uploadDocumentsButton;
    private TextView nidStatus, drivingLicenseStatus, motorbikeRegistrationStatus;

    private static final int RESULT_LOAD_IMAGE = 0;
    private final int NID_IMAGE_RESPONSE = 1;
    private final int BIKE_REGISTRATION_RESPONSE = 2;
    private final int DRIVING_LICENSE_RESPONSE = 3;
    private final int PIC_CROP = 4;
    private Uri picUri;
    private File nidImage, bikeRegistrationImage, drivingLicenseImage;

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public String uploadedNidImage, uploadedMotorBikeImage, uploadedDrivingLicenseImage;

    private UserInformation userInformation;

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
        uploadDocumentsButton = (Button) findViewById(R.id.upload_documents_button);
        nidStatus = (TextView) findViewById(R.id.tv_nid_upload_status);
        drivingLicenseStatus = (TextView) findViewById(R.id.tv_driving_license_text);
        motorbikeRegistrationStatus = (TextView) findViewById(R.id.tv_bike_registration_upload_status);
        userInformation = new UserInformation(this);

        validateCurrentUploadStatus();

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

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

        uploadDocumentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadRiderDocuments();
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
            thePic = Bitmap.createScaledBitmap(thePic, 400, 400, true);
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
                    bikeRegistrationImage = new File(getApplicationContext().getCacheDir(),"bikeRegistration.png");
                    try {
                        bikeRegistrationImage.createNewFile();
                        FileOutputStream fos = new FileOutputStream(bikeRegistrationImage);
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
                    drivingLicenseImage = new File(getApplicationContext().getCacheDir(),"drivingLicense.png");
                    try {
                        drivingLicenseImage.createNewFile();
                        FileOutputStream fos = new FileOutputStream(drivingLicenseImage);
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

    public void UploadRiderDocuments(){

        MultipartBody.Part nidImageToUpload, drivingLicenseToUpload, motorbikeRegistrationToUpload;

        if(nidImage!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), nidImage);
            nidImageToUpload = MultipartBody.Part.createFormData("nid", nidImage.getName(), requestBody);
        }else{
            nidImageToUpload = null;
        }

        if(drivingLicenseImage!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), drivingLicenseImage);
            drivingLicenseToUpload = MultipartBody.Part.createFormData("driving_license", drivingLicenseImage.getName(), requestBody);
        }else{
            drivingLicenseToUpload = null;
        }

        if(bikeRegistrationImage!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), bikeRegistrationImage);
            motorbikeRegistrationToUpload = MultipartBody.Part.createFormData("motorbike_reg", bikeRegistrationImage.getName(), requestBody);
        }else{
            motorbikeRegistrationToUpload = null;
        }

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(RiderDocumentsActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        String riderId = userInformation.getuserInformation().getRiderId();

        String authHeader = "Bearer "+pref.getString("access_token",null);

        RequestBody riderIdRequest = RequestBody.create(MediaType.parse("text/plain"),riderId);



        Call<LoginModel> call = apiService.updateRiderDocuments(authHeader,riderIdRequest,nidImageToUpload,motorbikeRegistrationToUpload,drivingLicenseToUpload);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        LoginData newLoginData = response.body().getLoginData();
                        Gson gson = new Gson();
                        String json = gson.toJson(newLoginData);
                        editor.putString("userData",json);
                        editor.commit();
                        validateCurrentUploadStatus();
                        break;
                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setUploadStatus(TextView view, int TextColor, String status){
        view.setText(status);
        view.setTextColor(TextColor);
    }

    public void CheckImageUploadStatus(String uploadedImage, TextView view, String errorText, String successText, ImageView imageViewToSet){
        if(uploadedImage==null){
            setUploadStatus(view,Color.RED,errorText);
        }
        else{
            setUploadStatus(view,Color.BLACK,successText);
            Picasso.with(this).invalidate(uploadedImage);
            Picasso.with(this)
                    .load(uploadedImage)
                    .placeholder(R.drawable.profile_image)
                    .error(R.drawable.profile_image)
                    .into(imageViewToSet);
        }
    }

    public void validateCurrentUploadStatus(){
        uploadedNidImage = userInformation.getuserInformation().getNidImage();
        uploadedMotorBikeImage = userInformation.getuserInformation().getMotorbikeRegistrationImage();
        uploadedDrivingLicenseImage = userInformation.getuserInformation().getDrivingLicenseImage();

        CheckImageUploadStatus(uploadedNidImage,nidStatus,"Missing",userInformation.getuserInformation().getNid(),nidImageView);
        CheckImageUploadStatus(uploadedMotorBikeImage,motorbikeRegistrationStatus,"Missing",userInformation.getuserInformation().getMotorbikeRegistration(),bikeRegistrationImageView);
        CheckImageUploadStatus(uploadedDrivingLicenseImage,drivingLicenseStatus,"Missing",userInformation.getuserInformation().getDrivingLicense(),drivingLicenseImageView);
    }
}

