package com.chaatgadrive.arif.chaatgadrive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.chaatgamap.Mapfragment;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Mapfragment mapfragment = new Mapfragment();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.mapView,mapfragment,mapfragment.getTag()).commit();
                    return true;
                case R.id.navigation_dashboard:
                 //   mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                   // mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mTextMessage = (TextView) findViewById(R.id.message);
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
