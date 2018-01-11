package com.chaatgadrive.arif.chaatgadrive.CostEstimation;

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

        double totalCost = AppConstant.BASE_TAKA + AppConstant.PER_KILOMITTER*distance + AppConstant.DURATION_PER_KILOMITTER*duration;
        return totalCost;
    }

    public long getDistance(String StringDistance){
             String value="0";
            for(int i=0; i<StringDistance.length()-3; i++){
                value+=StringDistance.charAt(i);
            }

        long distance = (long) Double.parseDouble(value);
       return distance;
    }


    public long getDuration(String StringDuration){
        String value="0";
        for(int i=0; i<StringDuration.length()-4; i++){
            value+=StringDuration.charAt(i);
        }

        long duration = (long) Double.parseDouble(value);
        return duration;
    }
}
