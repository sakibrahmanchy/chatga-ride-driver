package com.chaatgadrive.arif.chaatgadrive.CostEstimation;

import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Arif on 1/4/2018.
 */

public class CostEstimation {

    private double distance;
    private double duration;
    private double total;
    public CostEstimation() {

    }

   public double getTotalCost(String Stringdistance,String Stringduration){
        double distance = getDistance(Stringdistance);
        double duration = getDuration(Stringduration);

        double totalCost =  AppConstant.PER_KILOMITTER*distance + AppConstant.DURATION_PER_KILOMITTER*duration;
        return totalCost;
    }

    public double getDistance(String StringDistance){
             String value="0";
             double distance =0;
             try {

                 for(int i=0; i<StringDistance.length()-3; i++){
                     value+=StringDistance.charAt(i);
                 }

                  distance =  Double.parseDouble(value);
             }catch (Exception e){
                 Log.d("Distance Execption",e+"");
             }

       return distance;
    }


    public long getDuration(String StringDuration){
        String value="0";
        long duration =0;
        try{
            for(int i=0; i<StringDuration.length()-4; i++){
                value+=StringDuration.charAt(i);
            }

            duration = (long) Double.parseDouble(value);
        }
        catch (Exception e){
            Log.d("Duration Execption",e+"");
        }

        return duration;
    }
}
