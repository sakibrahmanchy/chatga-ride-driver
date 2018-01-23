package com.chaatgadrive.arif.chaatgadrive.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;

/**
 * Created by Arif on 1/17/2018.
 */

public class RideFinishDailog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    public Button btnOk;
    private FragmentActivity myContext;
    private Main main;
    private TextView total_cost;


    public RideFinishDailog(Activity activity) {
        super(activity);
        this.activity = activity;
        myContext = (FragmentActivity) activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ride_finish_dailog);
        btnOk = (Button) findViewById(R.id.btnOk);
        total_cost = (TextView) findViewById(R.id.total_cost);
        btnOk.setOnClickListener(this);
        main = new Main(getContext());
        total_cost.setText(AppConstant.TOTAL_RIDING_COST+" TK");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                ForceFinishedRide();
                Intent intent = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(intent);
                myContext.finish();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void ForceFinishedRide(){
        Pair<Double, Double> finalDestination = Pair.create(00d, 00d);
        long finalCost = 10101;
        main.ForcedFinishedRide(finalCost, finalDestination);
    }

}
