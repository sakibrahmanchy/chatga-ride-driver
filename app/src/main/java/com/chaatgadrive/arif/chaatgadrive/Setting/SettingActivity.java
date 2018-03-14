package com.chaatgadrive.arif.chaatgadrive.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class SettingActivity extends AppCompatActivity {

    private ImageView profileImage;
    private ImageView editProfile;
    private TextView profileName;
    private TextView phoneNumber;
    private TextView email;
    private TextView homeLocation;
    private TextView workLocation;
    private TextView signOut;
    private UserInformation userInformation;
    private LoginData loginData;
    private  int PLACE_PICKER_REQUEST = 1;
    private  int PLACE_PICKER_REQUEST_FOR_WORK = 2;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userInformation = new UserInformation(this);
        loginData = userInformation.getuserInformation();
        pref = this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();


        profileImage = (ImageView) findViewById(R.id.profile_pic);
        profileName = (TextView) findViewById(R.id.profile_name);
        phoneNumber = (TextView) findViewById(R.id.profile_phone);
        email = (TextView) findViewById(R.id.profile_email);
        signOut = (TextView) findViewById(R.id.action_logout);
        editProfile = (ImageView) findViewById(R.id.edit_profile);

        setAllInformation();
        setFovaritesLocation();

    }

    private void setAllInformation(){
        profileName.setText(loginData.firstName);
        phoneNumber.setText("+88"+ loginData.phone);
        email.setText(loginData.email);

        String url = loginData.getAvatar();
        Picasso.with(this).invalidate(url);
        Picasso.with(this)
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .noFade()
                .into(profileImage);

    }

    private void setFovaritesLocation(){
        homeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SettingActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        workLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SettingActivity.this), PLACE_PICKER_REQUEST_FOR_WORK);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

}
