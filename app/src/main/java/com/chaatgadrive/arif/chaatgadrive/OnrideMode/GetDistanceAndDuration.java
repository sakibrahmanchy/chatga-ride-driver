package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
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

/**
 * Created by Arif on 11/12/2017.
 */

public class GetDistanceAndDuration extends AsyncTask<String, Void, String> {
    private String distance;
    private String duration;
    private LatLng source, dest;
    CostEstimation costEstimation;

    public GetDistanceAndDuration(LatLng source, LatLng dest) {
        this.source = source;
        this.dest = dest;
        costEstimation = new CostEstimation();
        String Url =getDirectionsUrl(source,dest);
        this.execute(Url);

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

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, String> {

        // Parsing the data in non-ui thread
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

             String value="";
             for(int i=0; i<result.length()-3; i++){
                 value+=result.charAt(i);
             }
            double x = Double.parseDouble(value);

         costEstimation.getTotalCost(x,0);
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

    /**
     * A method to download json data from url
     */
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


