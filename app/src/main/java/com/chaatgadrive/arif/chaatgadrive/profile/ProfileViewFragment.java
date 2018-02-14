package com.chaatgadrive.arif.chaatgadrive.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.Setting.EditProfile;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.squareup.picasso.Picasso;

/**
 * Created by Arif on 12/23/2017.
 */
public class ProfileViewFragment extends Fragment{

    private TextView user_Name;
    private TextView phone_number;
    private TextView mail;
    private ImageView uploadProfile;
    private UserInformation userInformation;
    private LinearLayout profileView;


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
        user_Name =(TextView) view.findViewById(R.id.name);
        phone_number =(TextView) view.findViewById(R.id.field_phone_number);
        mail = (TextView) view.findViewById(R.id.email);
        uploadProfile = (ImageView) view.findViewById(R.id.profile);
        profileView = (LinearLayout) view.findViewById(R.id.viewProfile);

        userInformation = new UserInformation(getContext());

        user_Name.setText(userInformation.getuserInformation().firstName+" "+userInformation.getuserInformation().lastName);
        phone_number.setText(userInformation.getRiderPhoneNumber());

        String url = userInformation.getuserInformation().getAvatar();
        Picasso.with(getActivity()).invalidate(url);
        Picasso.with(getActivity())
                .load(url)
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(uploadProfile);


        AllBuutonClick();


        return view;
    }

    void AllBuutonClick(){
        uploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileViewIntent = new Intent(getActivity(), EditProfile.class);
                startActivity(profileViewIntent);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();




            }
    }
}
