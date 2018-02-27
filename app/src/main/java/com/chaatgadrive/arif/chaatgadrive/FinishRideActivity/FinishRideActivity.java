package com.chaatgadrive.arif.chaatgadrive.FinishRideActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.Rating.UserRating;

import ContactWithFirebase.Main;

public class FinishRideActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    public Button btnOk;
    private RatingBar ratingBar;
    private Main main;
    private TextView total_cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_ride);
        btnOk = (Button) findViewById(R.id.btnOk);
        total_cost = (TextView) findViewById(R.id.total_cost);
        btnOk.setOnClickListener(this);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        main = new Main(this);
        total_cost.setText("৳"+AppConstant.TOTAL_RIDING_COST);
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
                if(AppConstant.RATING !=0){
                    new UserRating(this).RatingClient();
                }
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();


                break;
            default:
                break;
        }

    }

}
