package com.chaatgadrive.arif.chaatgadrive;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.Dailog.ViewDialog;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    manager.beginTransaction().replace(R.id.content,mapfragment,mapfragment.getTag()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    manager.beginTransaction().replace(R.id.content,dashboardFragment,dashboardFragment.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(MainActivity.this, "Error de conexión al servidor");
                  //  manager.beginTransaction().replace(R.id.content,dashboardFragment,dashboardFragment.getTag()).commit();
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
