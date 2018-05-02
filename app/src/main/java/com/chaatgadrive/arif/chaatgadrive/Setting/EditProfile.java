package com.chaatgadrive.arif.chaatgadrive.Setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ContactWithFirebase.Main;
import __Firebase.FirebaseWrapper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity  {


    private ImageView editProfile,edit_profile;
    private EditText editProfileFirstName;
    private EditText editProfileLastName;
    private EditText editEmail;
    private RadioGroup editGender;
    private Button profileUpdate;
    private EditText editPhoneNunber;
    private EditText editDate;
    private LoginData loginData;
    private RadioButton male,female;
    private DatePickerDialog birthDayPickerDialog;
    private Uri picUri;
    private SimpleDateFormat dateFormatter;
    private String deviceToken,email,firstName,lastName,gender,birthDate,phone;
    //keep track of cropping intent
    final int PIC_CROP = 2;


    private static final int RESULT_LOAD_IMAGE = 1;
    private String imageEncodedToBase64;

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private UserInformation userInformation;
    private File profilePicture;
    String TAG = "EditProfile";
   private  Main main = new Main(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        editProfile = (ImageView) findViewById(R.id.edit_profile_pic);
        editProfileFirstName = (EditText) findViewById(R.id.edit_profile_first_name);
        editProfileLastName = (EditText) findViewById(R.id.edit_profile_last_name);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editGender =(RadioGroup) findViewById(R.id.edit_gender_radio_group);
        editDate =(EditText) findViewById(R.id.edit_birthday_edittext);
        editPhoneNunber = (EditText) findViewById(R.id.phone_number_view);
        profileUpdate = (Button) findViewById(R.id.profile_save);
        userInformation = new UserInformation(this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        editPhoneNunber.setEnabled(false);
        male = (RadioButton) findViewById(R.id.male_radio_btn);
        female = (RadioButton) findViewById(R.id.female_radio_btn);
        viewAndEditProfile();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editDate.setInputType(InputType.TYPE_NULL);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthDayPickerDialog.show();
            }
        });

        setDateTimeField();
    }

    private void viewAndEditProfile(){

        loginData = userInformation.getuserInformation();
        String url = loginData.getAvatar();
        Picasso.with(this).invalidate(url);
        Picasso.with(this)
                .load(url)
                .noFade()
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(editProfile);
        editProfileFirstName.setText(loginData.firstName);
        editProfileLastName.setText(loginData.lastName);
        editEmail.setText(loginData.getEmail());
        editPhoneNunber.setText(loginData.phone);
        if(loginData.getGender() !=null ){
            if(loginData.getGender().equals("male")){
               male.setChecked(true);
            }
           if(loginData.getGender().equals("female")){
                female.setChecked(true);
           }
        }
        editDate .setText(loginData.getBirthDate());


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCropImageActivity();

            }
        });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attemptLogin()){
                    updateProfile(firstName,lastName,gender,email,profilePicture);
                }
            }
        });

    }


    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        birthDayPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Log.d(TAG, dayOfMonth+"");
                newDate.set(year, monthOfYear+1, dayOfMonth);
                editDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
    private void startCropImageActivity() {
        CropImage.activity()
                .start(this);
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                picUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},   CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    InputStream image_stream = getContentResolver().openInputStream(resultUri);
                    Bitmap thePic= BitmapFactory.decodeStream(image_stream );
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    thePic.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    profilePicture = new File(getApplicationContext().getCacheDir(),"profileImage.png");
                    try {
                        profilePicture.createNewFile();
                        FileOutputStream fos = new FileOutputStream(profilePicture);
                        fos.write(byteArray);
                        fos.flush();
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editProfile.setImageBitmap(thePic);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (picUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(picUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    private boolean attemptLogin() {

        boolean ok=true;
        editEmail.setError(null);
        editProfileFirstName.setError(null);
        editProfileLastName.setError(null);

        email = editEmail.getText().toString();
        firstName = editProfileFirstName.getText().toString();
        lastName = editProfileLastName.getText().toString();

        deviceToken = FirebaseWrapper.getDeviceToken();
        birthDate = editDate.getText().toString();

        int selectedId = editGender.getCheckedRadioButtonId();

        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            editProfileFirstName.setError(getString(R.string.error_field_required));
            focusView = editProfileFirstName;

            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(lastName)) {
            editProfileLastName.setError(getString(R.string.error_field_required));
            focusView = editProfileLastName;

            cancel = true;
            ok=false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.error_field_required));
            focusView = editEmail;
            cancel = true;
            ok=false;
        }

        if(TextUtils.isEmpty(birthDate)){
            ok=false;
        }
        return ok;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(EditProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
        return false;
    }

    public void updateProfile(String firstName, String lastName, String gender, String email, File avatar){

        MultipartBody.Part fileToUpload;
        if(avatar!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), avatar);
            fileToUpload = MultipartBody.Part.createFormData("avatar", avatar.getName(), requestBody);
        }else{
            fileToUpload = null;
        }

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(EditProfile.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        String riderId = userInformation.getuserInformation().riderId;
        String authHeader = "Bearer "+pref.getString("access_token",null);
        RequestBody riderIdRequest = RequestBody.create(MediaType.parse("text/plain"),riderId);
        RequestBody firstNameRequest = RequestBody.create(MediaType.parse("text/plain"),firstName);
        RequestBody lastNameRequest = RequestBody.create(MediaType.parse("text/plain"),lastName);
        RequestBody genderRequest = RequestBody.create(MediaType.parse("text/plain"),gender);
        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"),email);
        RequestBody phoneRequest = RequestBody.create(MediaType.parse("text/plain"),editPhoneNunber.getText().toString());


        Call<LoginModel>call = apiService.updateRiderProfile(authHeader,riderIdRequest,firstNameRequest,lastNameRequest,genderRequest,emailRequest,fileToUpload,phoneRequest);
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
                        if(newLoginData.getAvatar()!=null){
                            main.UpdateNameImageAndRatting(newLoginData.getFirstName() +" "+newLoginData.getLastName()
                            ,newLoginData.getAvatar(),null);
                        }
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
                Log.e(TAG, t.toString());
            }
        });

    }

    public void generateFile(Bitmap bitmap){

    }


}
