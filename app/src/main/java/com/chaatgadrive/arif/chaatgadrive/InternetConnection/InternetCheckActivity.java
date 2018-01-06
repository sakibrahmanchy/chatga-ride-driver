package com.chaatgadrive.arif.chaatgadrive.InternetConnection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chaatgadrive.arif.chaatgadrive.R;

public class InternetCheckActivity extends AppCompatActivity {

    private ImageView alartImage;
    private ImageView restorImage;
    private ConnectionCheck connectionCheck;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_check);
        alartImage = (ImageView) findViewById(R.id.alart_image);
        restorImage =(ImageView) findViewById(R.id.internet_check);
        connectionCheck = new ConnectionCheck(this);

        restorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(InternetCheckActivity.this);
                dialog.setMessage("Internet Conection Checking...");
                dialog.show();
                  if(connectionCheck.isNetworkConnected()){
                      dialog.dismiss();
                      finish();
                  }
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit from the App?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                    }
                }).create().show();

    }

}
