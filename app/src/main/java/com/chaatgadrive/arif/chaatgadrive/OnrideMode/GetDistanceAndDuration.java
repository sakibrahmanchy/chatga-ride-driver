package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.HistoryModel.RiderHistory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by Arif on 11/12/2017.
 */

public class GetDistanceAndDuration extends AsyncTask<String, Void, String> {
    private String distance;
    private String duration;
    private LatLng source, dest;
    CostEstimation costEstimation;
    private NotificationModel notificationModel;
    private Main main;
    private Context mContex;
    private RiderHistory riderHistory;
    private UserInformation userInformation;

    public GetDistanceAndDuration(Context context, LatLng source, LatLng dest) {
        this.source = source;
        this.dest = dest;
        costEstimation = new CostEstimation();
        String Url =getDirectionsUrl(source,dest);
        this.execute(Url);
        notificationModel = new NotificationModel();
        this.mContex = context;
        main = new Main(mContex);
        riderHistory = new RiderHistory();
        userInformation = new UserInformation(mContex);

    }

    @Override
    protected String doInBackground(String... url) {

        String data = "";

        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask();
        parserTask.execute(result);

    }

    private class ParserTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... jsonData) {

            JSONObject jObject;


            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser(jObject);

                 distance= parser.getDistance();
                //GET Time to Source to Destination
                //Distance= parser.getDuration().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return distance;
        }

        @Override
        protected void onPostExecute(String result) {

             String value="0";
             if(result !=null){
                 for(int i=0; i<result.length()-3; i++){
                     value+=result.charAt(i);
                 }
             }

            double distance = Double.parseDouble(value);

            LatLng Source = new LatLng(notificationModel.sourceLatitude,notificationModel.sourceLongitude);
            LatLng Destination = new LatLng(notificationModel.destinationLatitude,notificationModel.destinationLongitude);
            riderHistory.ClientID = notificationModel.clientId;
            riderHistory.CostSoFar = (long) costEstimation.getTotalCost(distance,0);
            riderHistory.HistoryID=2;
            riderHistory.RiderID=Integer.parseInt(userInformation.getuserInformation().userId);
            riderHistory.StartLocation =Source;
            riderHistory.EndLocation=Destination;
            riderHistory.Client_History = notificationModel.destinationName;
            riderHistory.Rider_History=notificationModel.destinationName;
            riderHistory.IsRideFinished= FirebaseConstant.RIDE_NOT_FINISHED;
            riderHistory.IsRideStart=FirebaseConstant.RIDE_NOT_START;

            main.CreateNewHistoryModelFirebase(riderHistory);

        }


    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

}


