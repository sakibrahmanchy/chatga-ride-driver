package com.chaatgadrive.arif.chaatgadrive.profile;

import android.icu.util.ValueIterator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;

/**
 * Created by Arif on 12/23/2017.
 */
public class ProfileViewFragment extends Fragment{

    private TextView user_Name;
    private TextView phone_number;
    private TextView mail;
    private UserInformation userInformation;
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
        View view = inflater.inflate(R.layout.profile_view, container, false);
        user_Name = view.findViewById(R.id.name);
        phone_number =view.findViewById(R.id.field_phone_number);
        mail = view.findViewById(R.id.email);
        userInformation = new UserInformation(getContext());

        user_Name.setText(userInformation.getuserInformation().firstName);
        phone_number.setText(userInformation.getRiderPhoneNumber());


        return view;
    }
}
