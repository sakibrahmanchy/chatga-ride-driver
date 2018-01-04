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

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

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
                            LoginHelper loginHelper = new LoginHelper(UserCheckActivity.this);
                            loginHelper.AccessTokenCall(clientId,clientSecret,phoneNumber);

                        }else{

                            Intent intent = new Intent(UserCheckActivity.this, PhoneVerificationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                            startActivity(intent);
                        }
                        break;
                    case 500:
                        try {
                            JSONObject error = new JSONObject(response.errorBody().string());
                            String errorCode = error.getString("response_code");

                            if(errorCode.equals("auth/user-not-found")){

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
}

