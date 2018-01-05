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

   public double getTotalCost(double distance,double duration){
        double totalCost = AppConstant.BASE_TAKA + AppConstant.PER_KILOMITTER*distance + AppConstant.DURATION_PER_KILOMITTER*duration;
        return totalCost;
    }
}
