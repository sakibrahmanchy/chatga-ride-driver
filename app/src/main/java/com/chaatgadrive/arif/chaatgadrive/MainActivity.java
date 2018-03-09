package com.chaatgadrive.arif.chaatgadrive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.Earning.EarningFragment;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.ConnectionCheck;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.InternetCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.OnLocationChange.UpdateLocationService;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.Mapfragment;
import com.chaatgadrive.arif.chaatgadrive.dashboard.DashboardFragment;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.profile.ProfileViewFragment;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;

public class MainActivity extends AppCompatActivity implements ICallBackCurrentServerTime {

    private TextView mTextMessage;
    private Mapfragment mapfragment = new Mapfragment();
    private DashboardFragment dashboardFragment = new DashboardFragment();
    private EarningFragment earningFragment = new EarningFragment();
    private ProfileViewFragment profileViewFragment = new ProfileViewFragment();
    private NotificationActivity notificationFragment = new NotificationActivity();
    private FragmentManager manager = getSupportFragmentManager();
    private Main main = new Main(this);
    private GetCurrentLocation getCurrentLocation = null;
    private Handler handler = new Handler();
    private Switch OffOnSwitch;
    private ConnectionCheck connectionCheck;
    private LoginData loginData;
    private UserInformation userInformation;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    public static Context contextOfApplication;
    public static Activity MainActivityContext;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    if (!connectionCheck.isNetworkConnected()) {
                        Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (!connectionCheck.isGpsEnable()) {
                        Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        manager.beginTransaction().replace(R.id.content, mapfragment, mapfragment.getTag()).commit();
                    }
                    return true;
//                case R.id.navigation_dashboard:
//                    //mTextMessage.setText(R.string.title_dashboard);
//                    manager.beginTransaction().replace(R.id.content, dashboardFragment, dashboardFragment.getTag()).commit();
//                    return true;
                case R.id.navigation_notifications:
                    manager.beginTransaction().replace(R.id.content, notificationFragment, notificationFragment.getTag()).commit();
                    return true;
                case R.id.navigation_earning:
                    manager.beginTransaction().replace(R.id.content, earningFragment, earningFragment.getTag()).commit();
                    return true;
                case R.id.navigation_profile:
                        manager.beginTransaction().replace(R.id.content, profileViewFragment, profileViewFragment.getTag()).commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextOfApplication = getApplicationContext();
        setContentView(R.layout.activity_main);
        getCurrentLocation = new GetCurrentLocation(this);
        connectionCheck = new ConnectionCheck(this);

        MainActivityContext = this;


        if (!connectionCheck.isNetworkConnected()) {

            Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
            startActivity(intent);
            finish();

        } else if (!connectionCheck.isGpsEnable()) {

            Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
            startActivity(intent);
            finish();

        } else {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            if (pref.getString("userData", null) == null) {
                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    Toast.makeText(getApplicationContext(), "We need Some Permission", Toast.LENGTH_SHORT).show();
                    if (!checkIfAlreadyhavePermission()) {
                        requestForSpecificPermission();
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                manager.beginTransaction().replace(R.id.content, mapfragment, mapfragment.getTag()).commit();
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                userInformation = new UserInformation(this);
                loginData = userInformation.getuserInformation();
                getCurrentLocation = new GetCurrentLocation(this);
                this.MandatoryCall();
            }
        }

        String notification = getIntent().getStringExtra(FirebaseConstant.RIDE_NOTIFICATION);
        if (notification != null) {
            FirebaseUtilMethod.getNetworkTime(FirebaseConstant.GET_NOTIFICATION_TO_NOTIFY_RIDER, this, this);
        }
        //mTextMessage = (TextView) findViewById(R.id.message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshView:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
                main.ForcedRefreshRider();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem actionViewItem = menu.findItem(R.id.switchView);
        View v = MenuItemCompat.getActionView(actionViewItem);
        OffOnSwitch = (Switch) v.findViewById(R.id.switch1);

        if (AppConstant.OnOffSwith == 1) {
            OffOnSwitch.setChecked(true);
            OffOnSwitch.setText("ON");
        }

        if (AppConstant.OnOffSwith == 0) {
            OffOnSwitch.setChecked(false);
            OffOnSwitch.setText("OFF");
        }
        OffOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "Refresh selected= " + isChecked, Toast.LENGTH_SHORT).show();

                if (isChecked && loginData.getIsVerified()==1) {
                    AppConstant.OnOffSwith = 1;
                    OffOnSwitch.setText("ON");
                    if (FirebaseWrapper.getInstance().getRiderModelInstance().RiderID > 0) {
                        main.SetRiderOnLineOrOffLine(
                                FirebaseWrapper.getInstance().getRiderModelInstance(),
                                FirebaseConstant.SET_RIDER_ONLINE
                        );
                        main.SetRiderOnlineBusyOnRider(
                                FirebaseWrapper.getInstance().getRiderModelInstance(),
                                FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE
                        );
                    }
                } else {
                    OffOnSwitch.setText("OFF");
                    AppConstant.OnOffSwith = 0;
                    OffOnSwitch.setChecked(false);
                    if (FirebaseWrapper.getInstance().getRiderModelInstance().RiderID > 0) {
                        main.SetRiderOnLineOrOffLine(
                                FirebaseWrapper.getInstance().getRiderModelInstance(),
                                FirebaseConstant.SET_RIDER_OFF_ONLINE
                        );
                        main.SetRiderOnlineBusyOnRider(
                                FirebaseWrapper.getInstance().getRiderModelInstance(),
                                FirebaseConstant.OFFLINE_NOT_BUSY_ON_RIDE
                        );
                    }
                }
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    private void MandatoryCall() {
        new Main(this);
        Intent intent = new Intent(MainActivity.this, UpdateLocationService.class);
        startService(intent);
        if (loginData != null) {
            main.CreateNewRiderFirebase(loginData, userInformation.getRiderPhoneNumber());
        } else {
            /*main.GetRiderStatus(Long.parseLong(loginData.getRiderId()));*/
        }
    }

    private void SwitchingActivity(){
       Intent intent = new Intent(MainActivity.this, OnRideModeActivity.class);
       startActivity(intent);
    }

    @Override
    public void OnResponseServerTime(long Time, int type) {
        if (Time > 0 && type == FirebaseConstant.GET_NOTIFICATION_TO_NOTIFY_RIDER) {
            if (Math.abs(FirebaseWrapper.getInstance().getNotificationModelInstance().time - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND) {
                SwitchingActivity();
            }
        }
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    public void onStart() {
        super.onStart();
        AppConstant.MAIN_ACTIVITY = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        AppConstant.MAIN_ACTIVITY = false;
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS,
                android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.CALL_PHONE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED &&
                        grantResults[2]==PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, FacebookAccountVerificationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //not granted
                    finish();
                    Toast.makeText(getApplicationContext(),"Please Restart Application",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
