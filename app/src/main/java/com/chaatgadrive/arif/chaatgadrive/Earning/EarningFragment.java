package com.chaatgadrive.arif.chaatgadrive.Earning;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings.EarningByDay;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings.EarningData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings.RiderEarnings;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by SakibRahman on 3/9/2018.
 */



public class EarningFragment extends Fragment {
    public int defaultValue = Color.GRAY;
    public String TAG = "TEST";
    private ProgressDialog dialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private UserInformation userInformation;
    private TextView totalEarning;
    private TextView totalCompleteTrips;
    private  TextView totalRideRequest;
    private TextView totalDue;
    private  TextView totalPaid;
    private TextView completionRate;
    DataPoint dataPoints[];
    BarGraphSeries<DataPoint> series;
    GraphView graph;
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

        graph = (GraphView) view.findViewById(R.id.graph);
        totalRideRequest = view.findViewById(R.id.total_ride_request);
        totalCompleteTrips = view.findViewById(R.id.total_ride_complete);
        totalEarning =view.findViewById(R.id.total_earning_for_ride);
        totalDue = view.findViewById(R.id.total_trips_due);
        totalPaid = view.findViewById(R.id.total_paid);
        completionRate = view.findViewById(R.id.total_complete_rate);


        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        userInformation = new UserInformation(getContext());

        getGraphData();
        //setGraphValue(graph);

        return view;
    }

    public void getGraphData(){
        
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please wait...");
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

                        EarningByDay earningByDay = response.body().getData().getEarningByDay();
                        EarningData earningData = response.body().getData();
                        Gson gson = new Gson();
                        String json = gson.toJson(earningByDay);
                        totalRideRequest.setText(earningData.getRideRequests());
                        totalEarning.setText(earningData.getEarnings());
                        totalCompleteTrips.setText(earningData.getTripsCompleted());
                        totalDue.setText(earningData.getTotalDue());
                        totalPaid.setText(earningData.getTotalPaid());
                        completionRate.setText(earningData.getCompletionRate());
                        JSONObject json_array = null;
                        try {
                            json_array = new JSONObject(json);
                            Iterator<?> keys = json_array.keys();
                            int i = 0;
                            ArrayList<DataPoint> list = new ArrayList<DataPoint>();
                            ArrayList<String> dayNames = new ArrayList<String>();
                            dayNames.add("");
                            while( keys.hasNext() ) {
                                String key = (String) keys.next();
                                dayNames.add(key);
                                String value = "" +json_array.get(key);
                                list.add(new DataPoint(i, Double.parseDouble(value)));
                                i++;
                            }
                            dayNames.add("");

                            series = new BarGraphSeries<>(new DataPoint[]{
                                    list.get(0),list.get(1),list.get(2),list.get(3),
                                    list.get(4),list.get(5),list.get(6)
                            });

                            setGraphValue(graph,dayNames);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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


    public void setGraphValue(GraphView graph, ArrayList<String> dayNames){

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//        staticLabelsFormatter.setHorizontalLabels(new String[]{
//                dayNames.get(0),dayNames.get(1),dayNames.get(2),dayNames.get(3),
//                dayNames.get(4),dayNames.get(5),dayNames.get(6),dayNames.get(7),
//                dayNames.get(8)});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return R.color.colorPrimary;
            }
        });

        graph.addSeries(series);

// draw values on top
        series.setSpacing(20);
        series.setDrawValuesOnTop(true);
        series.setAnimated(true);
    }

}
