package com.chaatgadrive.arif.chaatgadrive.Adapters.History;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RiderHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RiderHistory;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiderHistoryActivity extends AppCompatActivity {


    RecyclerView rv;
    SwipeRefreshLayout swiper;
    HistoryAdapter adapter;

    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ArrayList<RiderHistory> riderHistories;;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserInformation userInformation;
    private String TAG = "RiderHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        userInformation = new UserInformation(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);


        rv = (RecyclerView) findViewById(R.id.history_recycler_view);
      //  dialog = new ProgressDialog(this);

        rv.setLayoutManager(new LinearLayoutManager(this));

        try{
            getRiderHistory();
        }catch (Exception e){
            e.printStackTrace();
        }


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
                        getRiderHistory();
                    }
                }, 3000);
            }
        });


    }

    public void getRiderHistory(){


        //String deviceToken = FirebaseWrapper.getDeviceToken();
        String authHeader = "Bearer "+pref.getString("access_token",null);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        LoginData loginData = userInformation.getuserInformation();
        Call<RiderHistoryResponse> call = apiService.getRiderHistory(authHeader,loginData.getRiderId());

        call.enqueue(new Callback<RiderHistoryResponse>() {
            @Override
            public void onResponse(Call<RiderHistoryResponse> call, Response<RiderHistoryResponse> response) {

                int statusCode = response.code();
                String testStatusCode = statusCode+"";

                switch(statusCode){
                    case 200:
                        boolean isSuccess = response.body().isSuccess();
                        if(isSuccess){
                            riderHistories = response.body().getData();
                            adapter = new HistoryAdapter(getApplicationContext(), riderHistories);
                            rv.setAdapter(adapter);
                        }else{
                        }
                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onFailure(Call<RiderHistoryResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
