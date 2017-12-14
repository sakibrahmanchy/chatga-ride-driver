package com.chaatgadrive.arif.chaatgadrive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        if(pref.getString("access_token",null)==null){
            Intent intent = new Intent(MainActivity.this, UserCheckActivity.class);
            startActivity(intent);
        }
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Main main = new Main();
        //main.CreateNewRiderFirebase();
        //main.CreateNewHistoryModelFirebase();
        main.GetCurrentRider(1104006); /*Save this ID SQ_LITE or somewhere else*/
        //main.GetRecentHistory(10101010); /*Save this ID SQ_LITE or somewhere else*/
        //main.SetRiderBusyOrFre(FirebaseWrapper.getInstance().getRiderModelInstance(), FirebaseConstant.SET_RIDER_BUSY);
        //main.SetRiderOnRideOrFree(FirebaseWrapper.getInstance().getRiderModelInstance(), FirebaseConstant.SET_RIDER_ON_RIDE);
        //main.SetRiderOnLineOrOffLine(FirebaseWrapper.getInstance().getRiderModelInstance(), FirebaseConstant.SET_RIDER_OFF_ONLINE);
        //main.UpdateRiderLocation(FirebaseWrapper.getInstance().getRiderModelInstance(), Pair.create(1010d, 1010d));
        //main.GetCurrentClient(1104011);
    }
}
