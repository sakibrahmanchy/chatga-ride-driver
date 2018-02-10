package com.chaatgadrive.arif.chaatgadrive;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.chaatgadrive.arif.chaatgadrive.Adapters.Notification.NotificationAdapter;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.NotificationModels.Notification;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.NotificationModels.NotificationResponse;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends Fragment {

    RecyclerView rv;
    SwipeRefreshLayout swiper;
    NotificationAdapter adapter;

    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Notification> notifications;
    String TAG = "NotificaftionActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_notification, container, false);

        pref = getActivity().getSharedPreferences("MyPref", 0);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_notification);

        rv = (RecyclerView) view.findViewById(R.id.notification_recycler_view);
        //  dialog = new ProgressDialog(this);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        getClientNotifications();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // implement Handler to wait for 3 seconds and then update UI means update value of TextView
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // cancle the Visual indication of a refresh
                        swipeRefreshLayout.setRefreshing(false);
                        // Generate a random integer number
                        getClientNotifications();
                    }
                }, 3000);
            }
        });

        return view;
    }


    public void getClientNotifications(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();

        //String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<NotificationResponse> call = apiService.getRiderNotifications(authHeader);

        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        boolean isSuccess = response.body().isSuccess();
                        if(isSuccess){
                            notifications = response.body().getData();
                            adapter = new NotificationAdapter(getActivity(),notifications);
                            rv.setAdapter(adapter);
                        }else{
                            Toast.makeText(getActivity(),"Error Occurred!",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        Toast.makeText(getActivity(),"Error Occurred!",Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item){
        getActivity().finish();
        return true;

    }



}
