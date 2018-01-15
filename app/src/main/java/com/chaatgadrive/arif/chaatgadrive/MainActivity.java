package com.chaatgadrive.arif.chaatgadrive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.Dailog.RiderDailog;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.ConnectionCheck;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.InternetCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.Mapfragment;
import com.chaatgadrive.arif.chaatgadrive.dashboard.DashboardFragment;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.profile.ProfileViewFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Mapfragment mapfragment = new Mapfragment();
    private DashboardFragment dashboardFragment = new DashboardFragment();
    private ProfileViewFragment profileViewFragment = new ProfileViewFragment();
    private FragmentManager manager = getSupportFragmentManager();
    private Main main = new Main(this);
    private GetCurrentLocation getCurrentLocation = null;
    private Handler handler = new Handler();
    private Switch OffOnSwitch;
    private ConnectionCheck connectionCheck;
    private LoginData loginData;
    private UserInformation userInformation;
    private boolean check = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    if (!connectionCheck.isNetworkConnected()) {
                        Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
                        startActivity(intent);

                    } else if (!connectionCheck.isGpsEnable()) {
                        connectionCheck.showGPSDisabledAlertToUser();
                    } else {
                        manager.beginTransaction().replace(R.id.content, mapfragment, mapfragment.getTag()).commit();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    manager.beginTransaction().replace(R.id.content, dashboardFragment, dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    manager.beginTransaction().replace(R.id.content, dashboardFragment, dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_earning:
                    manager.beginTransaction().replace(R.id.content, dashboardFragment, dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_profile:
                    if (!connectionCheck.isNetworkConnected()) {
                        Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
                        startActivityForResult(intent, AppConstant.INTERNET_CHECK);

                    } else if (!connectionCheck.isGpsEnable()) {
                        connectionCheck.showGPSDisabledAlertToUser();
                    } else {

                        manager.beginTransaction().replace(R.id.content, profileViewFragment, profileViewFragment.getTag()).commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCurrentLocation = new GetCurrentLocation(this);
        connectionCheck = new ConnectionCheck(this);
        createLocationRequest();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!connectionCheck.isNetworkConnected()) {

            Intent intent = new Intent(MainActivity.this, InternetCheckActivity.class);
            startActivity(intent);

        } else if (!connectionCheck.isGpsEnable()) {
            connectionCheck.showGPSDisabledAlertToUser();
        } else {

            manager.beginTransaction().replace(R.id.content, mapfragment, mapfragment.getTag()).commit();
        }

        String notification = getIntent().getStringExtra(FirebaseConstant.RIDE_NOTIFICATION);
        if (notification != null) {

            RiderDailog riderDailog = new RiderDailog(MainActivity.this);
            riderDailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            riderDailog.show();
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (pref.getString("userData", null) == null && !check) {
            Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
            startActivity(intent);
        } else {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            userInformation = new UserInformation(this);
            loginData = userInformation.getuserInformation();
            getCurrentLocation = new GetCurrentLocation(this);
            connectionCheck = new ConnectionCheck(this);
            this.MandatoryCall();
        }

        //mTextMessage = (TextView) findViewById(R.id.message);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            AppConstant.GlobalLocation = location;
                        }
                    }
                });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if(location !=null){
                        final Pair newLocation = Pair.create(location.getLatitude(), location.getLongitude());
                        if (FirebaseWrapper.getInstance().getRiderModelInstance().RiderID > 0) {
                            main.UpdateRiderLocation(
                                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                                    newLocation
                            );
                            Log.d(FirebaseConstant.UPDATE_LOCATION_TIMER, FirebaseConstant.UPDATE_LOCATION_TIMER);
                        }
                    }
                }
            };
        };


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
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                break;
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
        OffOnSwitch.setChecked(true);
        OffOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(), "Refresh selected= " + isChecked, Toast.LENGTH_SHORT).show();
                if (isChecked) {
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

        if (loginData != null) {
            main.CreateNewRiderFirebase(loginData, userInformation.getRiderPhoneNumber());
        } else {
            loginData = new LoginData();
            loginData.userId = "1010";
            loginData.firstName = "Jobayer";
            main.CreateNewRiderFirebase(loginData, userInformation.getRiderPhoneNumber());
        }

        /*
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Pair newLocation = Pair.create(getCurrentLocation.getLatitude(), getCurrentLocation.getLongitude());
                if (FirebaseWrapper.getInstance().getRiderModelInstance().RiderID > 0) {
                    main.UpdateRiderLocation(
                            FirebaseWrapper.getInstance().getRiderModelInstance(),
                            newLocation
                    );
                    Log.d(FirebaseConstant.UPDATE_LOCATION_TIMER, FirebaseConstant.UPDATE_LOCATION_TIMER);
                }
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
        */
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (AppConstant.mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(AppConstant.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(AppConstant.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.myLooper() /* Looper */);
    }
}
