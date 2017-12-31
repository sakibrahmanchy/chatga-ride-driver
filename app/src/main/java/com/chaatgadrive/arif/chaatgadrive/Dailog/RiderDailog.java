package com.chaatgadrive.arif.chaatgadrive.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.chaatgadrive.arif.chaatgadrive.R;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseResponse.NotificationModel;
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
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        main = new Main(getContext());
        NotificationModel nm = FirebaseWrapper.getInstance().getNotificationModelInstance();
      //  clientModel = FirebaseWrapper.getInstance().getClientModelInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                final BottomSheetDialogFragment myBottomSheet = BottomSheetDailogRide.newInstance("Modal Bottom Sheet");
                myBottomSheet.show(myContext.getSupportFragmentManager(), myBottomSheet.getTag());
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
