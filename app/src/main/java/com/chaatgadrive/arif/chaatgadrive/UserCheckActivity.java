package com.chaatgadrive.arif.chaatgadrive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.AccessTokenModels.AuthToken;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.UserCheckResponse;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class UserCheckActivity extends Activity {

    String clientId;
    String clientSecret;
    String grantType;
    String APP_ID;

    EditText mPhoneNumberField;
    Button mStartButton;

    private ProgressDialog dialog;
    private   ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    LoginHelper loginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
        mStartButton = (Button) findViewById(R.id.button_start_verification);

        clientId = getString(R.string.APP_CLIENT);
        clientSecret = getString(R.string.APP_CLIENT_SECRET);
        loginHelper = new LoginHelper(this);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = mPhoneNumberField.getText().toString();
                if(phoneNumber.length() < 11){
                    mPhoneNumberField.setError(getString(R.string.error_invalid_phone_number));
                }
                else {
                    editor.putString("phoneNumber", phoneNumber);
                    editor.commit();
                    UserExists(phoneNumber);
                }

            }
        });

    }

    public String GenerateAppId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid;
    }

    public void UserExists(final String phoneNumber){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        APP_ID = GenerateAppId();
        dialog = new ProgressDialog(UserCheckActivity.this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        Call<UserCheckResponse> call = apiService.checkUser(phoneNumber);

        call.enqueue(new Callback<UserCheckResponse>() {
            @Override
            public void onResponse(Call<UserCheckResponse> call, Response<UserCheckResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getResponseCode().toString();
                        if(responseCode.equals("user-found")){
                            //No phone verification required, call for access token
                            AccessTokenCall(clientId,clientSecret,phoneNumber);

                        }else{

                            Snackbar.make(findViewById(android.R.id.content), "Error Verifying.",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject error = new JSONObject(response.errorBody().string());
                            String errorCode = error.getString("response_code");
                            Snackbar.make(findViewById(android.R.id.content), errorCode,
                                    Snackbar.LENGTH_SHORT).show();
                            if(errorCode.equals("auth/user-not-found")){
                                Snackbar.make(findViewById(android.R.id.content), phoneNumber,
                                        Snackbar.LENGTH_SHORT).show();

                                Intent intent = new Intent(UserCheckActivity.this, PhoneVerificationActivity.class);
                                intent.putExtra("phoneNumber",phoneNumber);
                                intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                                startActivity(intent);
                            }

                        } catch (Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserCheckResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void AccessTokenCall(String clientId,String clientSecret,final String phoneNumber){

        dialog = new ProgressDialog(UserCheckActivity.this);
        dialog.setMessage("Gaining Access To App..");
        dialog.show();
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<AuthToken> call = apiService.getAccessToken(phoneNumber,clientId,clientSecret);

        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getStatus();
                        if(responseCode.equals("true")){
                            //No phone verification required, redirect to home
                            String accessToken = response.body().getAccessToken();
                            editor.putString("access_token",accessToken);
                            editor.commit();
                            LoginCall(phoneNumber);

                        }else{

                            Intent intent = new Intent(UserCheckActivity.this, RegistrationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            startActivity(intent);
//                            Snackbar.make(findViewById(android.R.id.content), "Error Verifying.",
//                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case 500:
                        try {

                        } catch (Exception e) {
//                            Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
//                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;

                    default:
//                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
//                                Snackbar.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    public void LoginCall(final String phoneNumber){

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in To App..");
        dialog.show();

        String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<LoginModel> call = apiService.loginUser(authHeader,phoneNumber, deviceToken);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
//                Snackbar.make(findViewById(android.R.id.content), testStatusCode,
//                        Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        String responseCode = response.body().getResponseCode();
                        if(responseCode.equals("auth/logged-in-successfully")){
                            //No phone verification required, redirect to home
                            LoginData data = response.body().getLoginData();

                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            editor.putString("userData",json);
                            editor.commit();

                            Intent intent = new Intent(UserCheckActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{

//                            Snackbar.make(findViewById(android.R.id.content), "Error Verifying.",
//                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case 500:
                        try {

                            JSONObject errorBody = new JSONObject(response.errorBody().string());
                            String errorResponseCode = errorBody.getString("response_code");
                            switch(errorResponseCode){
                                case "auth/phone-verification-required":
                                    Intent intent = new Intent(UserCheckActivity.this, PhoneVerificationActivity.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    intent.putExtra("loginStatus","PHONE_VERIFICATION_REQUIRED");
                                    startActivity(intent);
                                    break;
                                default:

                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    default:
//                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
//                                Snackbar.LENGTH_SHORT).show();
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


}

