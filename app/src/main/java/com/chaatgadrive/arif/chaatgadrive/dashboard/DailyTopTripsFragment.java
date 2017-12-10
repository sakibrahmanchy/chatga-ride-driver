package com.chaatgadrive.arif.chaatgadrive.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaatgadrive.arif.chaatgadrive.R;

/**
 * Created by Arif on 12/10/2017.
 */

public class DailyTopTripsFragment extends Fragment {
    public DailyTopTripsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.daily_top_trips, container, false);
    }
}
