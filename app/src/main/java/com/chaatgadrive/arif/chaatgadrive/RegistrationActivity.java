package com.chaatgadrive.arif.chaatgadrive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RegistrationModels.RegistrationModel;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;
import static android.content.ContentValues.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends Activity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;



    // UI references.
    private EditText mEmailView;
    private EditText userFirstName,userLastName;
    private EditText referralCode;
    private View mProgressView;
    private View mLoginFormView;
    private RadioGroup mGender;
    private  String email,phoneNumber, firstName,lastName,password,gender,
            deviceToken,birthDate, nid, drivingLicense, motorbikeRegistration,promocode;
    private   ApiInterface apiService ;
    private ProgressDialog dialog;
    private EditText birthDayText;
    private EditText nidText, drivingLicenseText, motorbikeRegistrationText;
    private DatePickerDialog birthDayPickerDialog;
    private SimpleDateFormat dateFormatter;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static Activity registrationActivity;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userInformation = new UserInformation(this);
        // Set up the login form.

        mEmailView = (EditText) findViewById(R.id.email);
        mGender = (RadioGroup) findViewById(R.id.gender_radio_group);
        userFirstName = (EditText) findViewById(R.id.userFirstName);
        userLastName = (EditText) findViewById(R.id.userLastName);
        referralCode = (EditText) findViewById(R.id.referral_code);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        registrationActivity = this;

        birthDayText = (EditText) findViewById(R.id.birthday_edittext);
        birthDayText.setInputType(InputType.TYPE_NULL);
        birthDayText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                birthDayPickerDialog.show();
            }
        });

        nidText = (EditText) findViewById(R.id.nid_edittext);
        drivingLicenseText = (EditText) findViewById(R.id.driving_license_edittext);
        motorbikeRegistrationText = (EditText) findViewById(R.id.motorbike_registration_edittext);

        setDateTimeField();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               if(attemptLogin()){
                   UserRegistration();
               }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    private boolean attemptLogin() {

        gender = "Male";
        boolean ok=true;
        mEmailView.setError(null);
        userFirstName.setError(null);

        email = mEmailView.getText().toString();
        firstName = userFirstName.getText().toString();
        lastName = userLastName.getText().toString();

        if(userInformation.GetDeviceToken()!=null){
            deviceToken = userInformation.GetDeviceToken();
        }
        else{
            deviceToken = FirebaseWrapper.getDeviceToken();
        }

        birthDate = birthDayText.getText().toString();
        nid = nidText.getText().toString();
        drivingLicense = drivingLicenseText.getText().toString();
        motorbikeRegistration = motorbikeRegistrationText.getText().toString();
        promocode =referralCode.getText().toString();
        int selectedId = mGender.getCheckedRadioButtonId();
        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            userFirstName.setError(getString(R.string.error_field_required));
            focusView = userFirstName;
            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(lastName)) {
            userLastName.setError(getString(R.string.error_field_required));
            focusView = userLastName;
            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(birthDate)) {
            birthDayText.setError(getString(R.string.error_field_required));
            focusView = birthDayText;
            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(nid)) {
            nidText.setError(getString(R.string.error_field_required));
            focusView = nidText;
            cancel = true;
            ok=false;
        }
        if (TextUtils.isEmpty(drivingLicense)) {
            drivingLicenseText.setError(getString(R.string.error_field_required));
            focusView = drivingLicenseText;
            cancel = true;
            ok=false;
        }

        if (TextUtils.isEmpty(motorbikeRegistration)) {
            motorbikeRegistrationText.setError(getString(R.string.error_field_required));
            focusView = motorbikeRegistrationText;
            cancel = true;
            ok=false;
        }



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
            ok=false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
            ok=false;
        }

       return ok;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();

        birthDayPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Log.d(TAG, dayOfMonth+"");
                newDate.set(year, (monthOfYear+1), dayOfMonth);
                birthDayText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    public void UserRegistration(){

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

       // Call<RegistrationModel> call = apiService.signUpClient(firstName,"shaikat",email,"01815003723",password,"","",gender);
        Call<RegistrationModel> call = apiService.signUpRider(firstName, lastName,email,phoneNumber,
                                                                deviceToken, birthDate,gender, nid,drivingLicense,motorbikeRegistration);

        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){

                    case 201:
                        String responseCode = response.body().getSuccess();

                        String clientId = getString(R.string.APP_CLIENT);
                        String clientSecret = getString(R.string.APP_CLIENT_SECRET);

                        LoginHelper loginHelper = new LoginHelper(RegistrationActivity.this);
                        loginHelper.AccessTokenCall(clientId, clientSecret,phoneNumber);

                        break;

                    default:
                        try {
                            JSONObject error = new JSONObject(response.errorBody().string());
                            String errorMessage = error.getString("message");
                            Snackbar.make(findViewById(android.R.id.content), errorMessage,
                                    Snackbar.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        AppConstant.REGISTRATION_ACTIVITY = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppConstant.REGISTRATION_ACTIVITY = false;
    }


}

