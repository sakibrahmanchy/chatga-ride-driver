package com.chaatgadrive.arif.chaatgadrive.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.R;

/**
 * Created by Arif on 12/20/2017.
 */

public class ViewDialog {

    public void showDialog(Context context, String msg){
        final Dialog dialog = new Dialog( context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ride_request_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnYes);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
