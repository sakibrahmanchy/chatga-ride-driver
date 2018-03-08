package com.chaatgadrive.arif.chaatgadrive.Earning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.FacebookAccountVerificationActivity;
import com.chaatgadrive.arif.chaatgadrive.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings.RiderEarnings;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by SakibRahman on 3/9/2018.
 */

public class EarningFragment extends Fragment {
    public int defaultValue = Color.GRAY;

    private ProgressDialog dialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private UserInformation userInformation;

    public EarningFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_earning, container, false);

        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        userInformation = new UserInformation(getContext());

        getGraphData();
        //setGraphValue(graph);

        return view;
    }

    public void getGraphData(){
        
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Logging in To App..");
        dialog.show();

        String authHeader = "Bearer "+pref.getString("access_token",null);
        String rider_id = userInformation.getuserInformation().getRiderId();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RiderEarnings> call = apiService.getRiderEarnings(authHeader,rider_id);
        call.enqueue(new Callback<RiderEarnings>() {
            @Override
            public void onResponse(Call<RiderEarnings> call, Response<RiderEarnings> response) {

                int statusCode = response.code();
                dialog.dismiss();

                switch(statusCode){
                    case 200:

                        break;
                    default:

                        break;
                }
            }
            @Override
            public void onFailure(Call<RiderEarnings> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    public void setGraphValue(GraphView graph){
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 100),
                new DataPoint(2, 220),
                new DataPoint(3, 320),
                new DataPoint(4, 200),
                new DataPoint(5, 300),
                new DataPoint(6, 200),
                new DataPoint(7, 300),
                new DataPoint(8, 0)
        });
        graph.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","SAT", "SUN", "MON", "TUE","WED", "THU", "FRI","" });
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return R.color.colorPrimary;
            }
        });


// draw values on top
        series.setSpacing(20);
        series.setDrawValuesOnTop(true);
        series.setAnimated(true);
    }

}
