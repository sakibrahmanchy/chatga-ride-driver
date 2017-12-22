package com.chaatgadrive.arif.chaatgadrive.profile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaatgadrive.arif.chaatgadrive.R;

/**
 * Created by Arif on 12/23/2017.
 */
public class ProfileViewFragment extends Fragment{


    public ProfileViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_view, container, false);
    }
}
