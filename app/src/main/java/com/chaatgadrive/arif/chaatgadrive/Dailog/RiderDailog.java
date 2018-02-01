package com.chaatgadrive.arif.chaatgadrive.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.OnrideMode.InitialAndFinalCostEstimation;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by Arif on 12/30/2017.
 */

public class RiderDailog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Button btnYes, btnNo;
    private FragmentActivity myContext;
    private Main main;
    private ClientModel clientModel;
    private TextView From_road_location, To_road_location;
    private InitialAndFinalCostEstimation initialAndFinalCostEstimation;

    private NotificationModel notificationModel;

    public RiderDailog(Activity activity) {
        super(activity);
        this.activity = activity;
        myContext = (FragmentActivity) activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_ride);
        btnYes = (Button) findViewById(R.id.btn_yes);
        btnNo = (Button) findViewById(R.id.btn_no);
        From_road_location = (TextView) findViewById(R.id.from_road_location);
        To_road_location = (TextView) findViewById(R.id.to_road_location);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        main = new Main(getContext());
        initialAndFinalCostEstimation = new InitialAndFinalCostEstimation(myContext);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        //  clientModel = FirebaseWrapper.getInstance().getClientModelInstance();
        From_road_location.setText(notificationModel.sourceName);
        To_road_location.setText(notificationModel.destinationName);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                initialAndFinalCostEstimation.CreateInitialHistory();
                Intent intent = new Intent(getContext(), OnRideModeActivity.class);
                getContext().startActivity(intent);
                SendPushNotification();
                break;
            case R.id.btn_no:
                RejectRide();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void SendPushNotification() {
        new Main(getContext()).ForcedAcceptanceOfRide(FirebaseConstant.INITIAL_ACCEPTANCE);
        /*
          new Main(getContext()).SentNotificationToClient(
              FirebaseWrapper.getInstance().getRiderModelInstance(),
              FirebaseWrapper.getInstance().getNotificationModelInstance().clientDeviceToken
          );
         */
    }

    private void RejectRide(){
        new Main(getContext()).ForcedRejectedRide(notificationModel.clientId);
    }
}
