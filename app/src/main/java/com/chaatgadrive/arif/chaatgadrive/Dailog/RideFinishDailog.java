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
import android.widget.RatingBar;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.Rating.UserRating;

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
    private RatingBar ratingBar;
    private Main main;
    private TextView total_cost;
    public static float rating = (float) 0.0;
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
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        main = new Main(getContext());
        total_cost.setText(AppConstant.TOTAL_RIDING_COST+" TK");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                     AppConstant.RATING = ratingBar.getRating();

            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                new UserRating(getContext()).RatingClient();
                myContext.finish();
                break;
            default:
                break;
        }
        dismiss();
    }



}
