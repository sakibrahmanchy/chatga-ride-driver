package com.chaatgadrive.arif.chaatgadrive;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.AccessTokenModels.AuthToken;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Sakib Rahman on 12/13/2017.
 */

public class LoginHelper {

    String clientId;
    String clientSecret;
    String grantType;
    String APP_ID;

    EditText mPhoneNumberField;
    Button mStartButton;

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public LoginHelper(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
    }

    public void AccessTokenCall(String clientId,String clientSecret,final String phoneNumber){

        dialog = new ProgressDialog(context);
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

                            Intent intent = new Intent(context, RegistrationActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("loginStatus","REGISTRATION_REQUIRED");
                            context.startActivity(intent);
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

        dialog = new ProgressDialog(context);
        dialog.setMessage("Logging in To App..");
        dialog.show();

        //String deviceToken = "asfs2xfasas2xx";
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

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
//                            Snackbar.make(findViewById(android.R.id.content),data.getUserId(),
//                                    Snackbar.LENGTH_SHORT).show();
//                            Snackbar.make(findViewById(android.R.id.content),accessToken,
//                                    Snackbar.LENGTH_SHORT).show();

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
                                    Intent intent = new Intent(context, PhoneVerificationActivity.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    intent.putExtra("loginStatus","PHONE_VERIFICATION_REQUIRED");
                                    context.startActivity(intent);
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
//                if(status.equals("true") && statusCode == 200){
//                    Intent intent = new Intent(UserCheckActivity.this, MapActivity.class);
//                    intent.putExtra("phoneNumber",phoneNumber);
//                    startActivity(intent);
//                }else{
//                    Snackbar.make(findViewById(android.R.id.content), "Error Verifying.",
//                            Snackbar.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }
}


