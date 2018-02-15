package com.chaatgadrive.arif.chaatgadrive.Dailog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.InitialAndFinalCostEstimation;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;

/**
 * Created by Arif on 12/30/2017.
 */

public class RiderDailog extends Dialog implements android.view.View.OnClickListener, ICallBackCurrentServerTime {

    public Activity activity;
    public Button btnYes, btnNo;
    private FragmentActivity myContext;
    private Main main;
    private ClientModel clientModel;
    private TextView From_road_location, To_road_location;
    private InitialAndFinalCostEstimation initialAndFinalCostEstimation;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Context wrapperContext;
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
        setCanceledOnTouchOutside(false);
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
                myContext.finish();
                cancelAlarm();
                break;
            case R.id.btn_no:
                RejectRide();
                dismiss();
                cancelAlarm();
                myContext.finish();
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

    private void RejectRide() {
        GetTime(FirebaseConstant.REJECT_RIDE);
    }

    private void GetTime(int type) {
        FirebaseUtilMethod.getNetworkTime(type, getContext(), this);
    }

    @Override
    public void OnResponseServerTime(long Time, int type) {
        if (Time > 0) {
            if (Math.abs(FirebaseWrapper.getInstance().getNotificationModelInstance().time - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND) {
                switch (type) {
                    case FirebaseConstant.REJECT_RIDE: {
                        new Main(getContext()).ForcedRejectedRide(notificationModel.clientId);
                        break;
                    }
                    case FirebaseConstant.ACCEPT_RIDE: {
                        new Main(getContext()).ForcedAcceptanceOfRide(FirebaseConstant.INITIAL_ACCEPTANCE);
                        break;
                    }
                }
            }
        }
    }

    public void cancelAlarm() {
        AppConstant.IS_ALARM=false;

    }
}
