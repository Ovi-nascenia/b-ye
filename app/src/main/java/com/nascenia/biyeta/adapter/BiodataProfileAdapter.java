package com.nascenia.biyeta.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.activity.Login;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;
import com.nascenia.biyeta.model.biodata.profile.Profile;
import com.nascenia.biyeta.model.communication.profile.CommunicationProfile;
import com.nascenia.biyeta.utils.Utils;


import java.util.List;

/**
 * Created by god father on 3/16/2017.
 */

public abstract class BiodataProfileAdapter extends RecyclerView.Adapter<BiodataProfileAdapter.ViewHolder> {
    private Tracker mTracker;
    private AnalyticsApplication application;

    private List<Profile> biodataProfile;
    private int itemLayout;
    int position;
    Profile profile;
    Context context;

    public abstract void setConnectionRequest(int id, int position);

    public abstract void LoadData();

    public abstract void onClickProfile(int position);

    SharePref sharePref;


    public BiodataProfileAdapter(Context context, List<Profile> biodataProfile, int itemLayout, AnalyticsApplication application, Tracker mTracker) {
        this.context = context;
        this.biodataProfile = biodataProfile;
        this.itemLayout = itemLayout;
        this.application = application;
        this.mTracker = mTracker;
        sharePref = new SharePref(context.getApplicationContext());
    }

    @Override
    public BiodataProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new BiodataProfileAdapter.ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final BiodataProfileAdapter.ViewHolder holder, final int position) {

        profile = biodataProfile.get(position);
        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(Utils.convertEnglishDigittoBangla(profile.getAge()) + " বছর" + ", " +
                Utils.convertEnglishDigittoBangla(profile.getHeightFt()) +
                "'" + Utils.convertEnglishDigittoBangla(profile.getHeightInc()) + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        holder.time_date.setText(Utils.getTime(profile.getIsCreatedAt()));


        String gender = new SharePref(holder.image.getContext()).get_data("gender");
        Glide.
                with(holder.image.getContext()).
                load(Utils.Base_URL + profile.getImage()).
                placeholder(gender.equalsIgnoreCase("female") ? R.drawable.profile_icon_male : R.drawable.profile_icon_female).
                into(holder.image);


        Log.e("BiodataMessage", profile.getRequestStatus().getMessage() + "hello ");

        holder.call.setText(profile.getRequestStatus().getMessage());

        try {
            if(profile.getRequestStatus().getCommunicationRequestId() == null){
                if (profile.getRequestStatus().getRejected() || profile.getRequestStatus().getMessage().equals("আপনি যোগাযোগের  অনুরোধ  করেছেন") || profile.getRequestStatus().getMessage().equals("আপনি সফলভাবে যোগাযোগের অনুরোধ করেছেন")){
                    holder.call.setEnabled(false);
                    holder.call.setText(profile.getRequestStatus().getMessage());
                    holder.call.setTextColor(Color.BLACK);
                    holder.call.setBackgroundColor(Color.parseColor("#F2F1F1"));
                } else if(profile.getRequestStatus().getExpired()){
                    holder.call.setEnabled(true);
                    holder.call.setText(profile.getRequestStatus().getMessage() + ", পুনরায় যোগাযোগের অনুরোধ করুন");
                } else if(profile.getRequestStatus().getName().equals("profile request")){
                    holder.call.setEnabled(true);
                    holder.call.setText(profile.getRequestStatus().getMessage());
                }
            }else{
                holder.call.setEnabled(false);
                holder.call.setTextColor(Color.BLACK);
                holder.call.setBackgroundColor(Color.parseColor("#F2F1F1"));
            }
//            if (null != profile.getRequestStatus().getCommunicationRequestId() || profile.getRequestStatus().getMessage().equals("আপনি যোগাযোগের  অনুরোধ  করেছেন") || profile.getRequestStatus().getMessage().equals("আপনি সফলভাবে যোগাযোগের অনুরোধ করেছেন") ||
//                    profile.getRequestStatus().getName().equals("profile request") || profile.getRequestStatus().getRejected() || profile.getRequestStatus().getExpired() || profile.getRequestStatus().getAccepted()) {
//                holder.call.setEnabled(false);
//                holder.call.setText(profile.getRequestStatus().getMessage());
//                holder.call.setTextColor(Color.BLACK);
//                holder.call.setBackgroundColor(Color.parseColor("#F2F1F1"));
//            }else if(profile.getRequestStatus().getCommunicationRequestId() == null && profile.getRequestStatus().getExpired() && !profile.getRequestStatus().getRejected()){
//                holder.call.setEnabled(true);
//                holder.call.setText(profile.getRequestStatus().getMessage() + ", পুনরায় যোগাযোগের অনুরোধ করুন");
//            } else {
//                holder.call.setEnabled(true);
//                holder.call.setText(profile.getRequestStatus().getMessage());
//            }
        } catch (Exception e) {
            holder.call.setEnabled(true);
            holder.call.setText(profile.getRequestStatus().getMessage());
//            application.trackEception(e, "onBindViewHolder", "BiodataProfileAdapter", e.getMessage().toString(), mTracker);
        }

        if (position == biodataProfile.size() - 1)
            LoadData();


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("userid", sharePref.get_data("user_id") + " " + biodataProfile.get(position).getId() + " " + position + " " + biodataProfile.get(position).getDisplayName());
                /*
                if(profile.getRequestStatus().getReceiver()!=null && profile.getRequestStatus().getSender()!=null)
                {
                    if (Integer.parseInt(sharePref.get_data("user_id")) == Integer.parseInt(profile.getRequestStatus().getReceiver())) {
                        setConnectionRequest(Integer.parseInt(profile.getRequestStatus().getSender()), position);
                    } else if (Integer.parseInt(sharePref.get_data("user_id")) == Integer.parseInt(profile.getRequestStatus().getSender())) {
                        setConnectionRequest(Integer.parseInt(profile.getRequestStatus().getReceiver()), position);
                    }
                }
                holder.call.setEnabled(false);
                */


                setConnectionRequest(biodataProfile.get(position).getId(), position);
//                Toast.makeText(context,biodataProfile.get(position).getDisplayName(), Toast.LENGTH_SHORT).show();
                holder.call.setEnabled(false);
            }
        });


        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProfile(position);
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProfile(position);
            }
        });


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProfile(position);

            }
        });

        holder.time_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProfile(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        if(biodataProfile != null)
            return biodataProfile.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView userName;
        public TextView details;
        public TextView time_date;
        Button call;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            details = (TextView) itemView.findViewById(R.id.details);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.date_time);
            call = (Button) itemView.findViewById(R.id.phone);


        }
    }
}