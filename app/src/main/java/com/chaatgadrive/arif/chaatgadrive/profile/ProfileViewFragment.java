package com.chaatgadrive.arif.chaatgadrive.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.Adapters.History.RiderHistoryActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.RiderDocumentsActivity;
import com.chaatgadrive.arif.chaatgadrive.Setting.EditProfile;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderProfileStats.RiderProfileStats;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderProfileStats.RiderProfileStatsResponse;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Arif on 12/23/2017.
 */
public class ProfileViewFragment extends Fragment{

    private TextView user_Name;
    private TextView phone_number;
    private TextView mail, totalTrips, rating, totalEarning;
    private ImageView uploadProfile;
    private UserInformation userInformation;
    private LinearLayout profileView,historyView,documentationsView,referralView;
    private ProgressDialog dialog;
    private ApiInterface apiService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


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
        historyView = (LinearLayout) view.findViewById(R.id.viewRiderHistory);
        documentationsView = (LinearLayout) view.findViewById(R.id.documentations);
        totalTrips = (TextView) view.findViewById(R.id.total_trips);
        rating = (TextView) view.findViewById(R.id.rating);
        totalEarning = (TextView) view.findViewById(R.id.total_earning);
       // referralView = (LinearLayout) view.findViewById(R.id.referral);

        pref = getActivity().getSharedPreferences("MyPref", 0);
        userInformation = new UserInformation(getContext());

        user_Name.setText(userInformation.getuserInformation().firstName+" "+userInformation.getuserInformation().lastName);
        phone_number.setText(userInformation.getRiderPhoneNumber());

        String url = userInformation.getuserInformation().getAvatar();
        Picasso.with(getActivity()).invalidate(url);
        Picasso.with(getActivity())
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(uploadProfile);


        AllBuutonClick();
        getProfileStats();

        return view;
    }

    void AllBuutonClick(){

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileViewIntent = new Intent(getActivity(), EditProfile.class);
                startActivity(profileViewIntent);
            }
        });

        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyViewIntent = new Intent(getActivity(), RiderHistoryActivity.class);
                startActivity(historyViewIntent);
            }
        });
        documentationsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent docuemntationsViewIntent = new Intent(getActivity(), RiderDocumentsActivity.class);
                startActivity(docuemntationsViewIntent);
            }
        });
//        referralView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent referralViewIntent = new Intent(getActivity(), ReferralActivity.class);
//                startActivity(referralViewIntent);
//            }
//        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();




            }
    }

    public void getProfileStats(){

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please wait..");
        dialog.show();

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RiderProfileStatsResponse> call = apiService.getRiderProfileStats(authHeader,Integer.parseInt(userInformation.getuserInformation().getRiderId()));

        call.enqueue(new Callback<RiderProfileStatsResponse>() {
            @Override
            public void onResponse(Call<RiderProfileStatsResponse> call, Response<RiderProfileStatsResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();

                switch(statusCode){
                    case 200:
                        RiderProfileStats stats = response.body().getData();
                        totalTrips.setText(stats.getTrips());
                        totalEarning.setText(stats.getEarnings());
                        rating.setText(stats.getRating());
                        break;
                    default:
                        Snackbar.make(getActivity().findViewById(R.id.content),"Error loading profile",Snackbar.LENGTH_LONG);
                        break;
                }
            }
            @Override
            public void onFailure(Call<RiderProfileStatsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
