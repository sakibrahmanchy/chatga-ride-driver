package com.chaatgadrive.arif.chaatgadrive.Adapters.History;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RiderHistory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 1/22/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RiderHistory> riderHistory;

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder viewHolder, int position) {

        RiderHistory history = riderHistory.get(position);
        viewHolder.tv_date_time.setText(history.getDateTime());
        viewHolder.tv_distance_minute.setText(history.getDistanceTime());
        String pickPoint = "";
        if(history.getPickPointAddress().length()>18)
            pickPoint = history.getPickPointAddress().substring(18);
        else
            pickPoint = history.getPickPointAddress();
        String destinationPoint = "";
        if(history.getDestinationAddress().length()>18)
            destinationPoint = history.getDestinationAddress().substring(18);
        else
            destinationPoint = history.getDestinationAddress();
        viewHolder.tv_src_address.setText(pickPoint);
        viewHolder.tv_dest_address.setText(destinationPoint);
        viewHolder.tv_total_fare.setText(history.getTotalFare());
        viewHolder.tv_history_client_name.setText(history.getRiderName());
        Picasso.with(context).invalidate(history.getRiderAvatar());
        Picasso.with(context)
                .load(history.getRiderAvatar())
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(viewHolder.iv_history_client_image);
    }

    public HistoryAdapter(Context mContext, ArrayList<RiderHistory> history) {
        this.context = mContext;
        this.riderHistory = history;
    }

    @Override
    public int getItemCount() {
        return riderHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_date_time;
        private TextView tv_total_fare;
        private TextView tv_distance_minute;
        private TextView tv_src_address;
        private TextView tv_dest_address;
        private TextView tv_history_promotion;
        private TextView tv_history_client_name;
        private ImageView iv_history_client_image;

        public ViewHolder(View view) {
            super(view);

                tv_date_time = (TextView) view.findViewById(R.id.date_time);
                tv_total_fare = (TextView) view.findViewById(R.id.total_fare);
                tv_distance_minute = (TextView) view.findViewById(R.id.distance_minute);
                tv_src_address = (TextView) view.findViewById(R.id.source_address);
                tv_dest_address = (TextView) view.findViewById(R.id.destination_address);
                tv_history_client_name = (TextView) view.findViewById(R.id.history_client_name);
                iv_history_client_image = (ImageView) view.findViewById(R.id.history_client_image);

//            tv_country = (TextView)view.findViewById(R.id.tv_country);
        }
    }

}
