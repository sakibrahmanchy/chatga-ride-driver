package com.chaatgadrive.arif.chaatgadrive.Adapters.Notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.NotificationModels.Notification;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 1/22/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Notification> clientNotification;

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_row_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder viewHolder, int position) {

        Notification notification = clientNotification.get(position);
        viewHolder.tv_title.setText(notification.getTitle());
        viewHolder.tv_description.setText(notification.getDescription());
        viewHolder.tv_start_time.setText(notification.getStartTime());
    }

    public NotificationAdapter(Context mContext, ArrayList<Notification> notification) {
        this.context = mContext;
        this.clientNotification = notification;
    }

    @Override
    public int getItemCount() {
        return clientNotification.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_description;
        private TextView tv_start_time;

        public ViewHolder(View view) {
            super(view);

            tv_title = (TextView) view.findViewById(R.id.notification_title);
            tv_description = (TextView) view.findViewById(R.id.notification_descriptions);
            tv_start_time = (TextView) view.findViewById(R.id.notification_start_time);
        }
    }

}
