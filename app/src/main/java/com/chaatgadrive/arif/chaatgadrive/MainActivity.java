package com.chaatgadrive.arif.chaatgadrive;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.Mapfragment;
import com.chaatgadrive.arif.chaatgadrive.dashboard.DashboardFragment;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Mapfragment mapfragment = new Mapfragment();
    private DashboardFragment dashboardFragment = new DashboardFragment();
    private FragmentManager manager = getSupportFragmentManager();
    private Main main = new Main();
    private GetCurrentLocation getCurrentLocation = null;
    private Handler handler = new Handler();
    private  Switch OffOnSwitch;
    private ConnectionCheck connectionCheck;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    if(connectionCheck.isNetworkConnected() && connectionCheck.isGpsEnable()){
                        manager.beginTransaction().replace(R.id.content,mapfragment,mapfragment.getTag()).commit();
                    }
                    else if (!connectionCheck.isGpsEnable()){
                        connectionCheck.showGPSDisabledAlertToUser();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Check Internet Or GPS Connection", Toast.LENGTH_SHORT)
                                .show();

                    }

                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    manager.beginTransaction().replace(R.id.content,dashboardFragment,dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    manager.beginTransaction().replace(R.id.content,dashboardFragment,dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_earning:
                    manager.beginTransaction().replace(R.id.content,dashboardFragment,dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_profile:
                    manager.beginTransaction().replace(R.id.content,dashboardFragment,dashboardFragment.getTag()).commit();
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
        this.MandatoryCall();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        if(pref.getString("access_token",null) == null){
            Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
            startActivity(intent);
        }
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem actionViewItem = menu.findItem(R.id.switchView);
        // Retrieve the action-view from menu
        View v = MenuItemCompat.getActionView(actionViewItem);
        // Find the button within action-view
         OffOnSwitch = (Switch) v.findViewById(R.id.switch1);
        // Handle button click here
        OffOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Toast.makeText(getApplicationContext(), "Refresh selected= "+isChecked, Toast.LENGTH_SHORT)
                        .show();
                if(isChecked){
                    OffOnSwitch.setText("ON");
                }
                else{
                    OffOnSwitch.setText("OFF");
                }
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }



    private void MandatoryCall() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FirebaseWrapper.getInstance().getRiderModelInstance().RiderID = 10001;
                final Pair newLocation = Pair.create(getCurrentLocation.getLatitude(), getCurrentLocation.getLongitude());
                main.UpdateRiderLocation(
                        FirebaseWrapper.getInstance().getRiderModelInstance(),
                        newLocation
                );
                Log.d(FirebaseConstant.UPDATE_LOCATION_TIMER, FirebaseConstant.UPDATE_LOCATION_TIMER);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }
}
