package com.nascenia.biyeta.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
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

    private List<Profile> biodataProfile;
    private int itemLayout;
    int position;
    Profile profile;


    public abstract void setConnectionRequest(int id, int position);

    public abstract void LoadData();
    public abstract void onClickProfile(int position);



    public BiodataProfileAdapter(List<Profile> biodataProfile, int itemLayout) {
        this.biodataProfile = biodataProfile;
        this.itemLayout = itemLayout;
    }

    @Override
    public BiodataProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new BiodataProfileAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BiodataProfileAdapter.ViewHolder holder,final int position) {

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


        if (null != profile.getRequestStatus().getCommunicationRequestId() || profile.getRequestStatus().getMessage().equals("আপনি যোগাযোগের  অনুরোধ  করেছেন") ||
                profile.getRequestStatus().getRejected() || profile.getRequestStatus().getExpired() || profile.getRequestStatus().getAccepted()) {
            holder.call.setEnabled(false);
            holder.call.setText(profile.getRequestStatus().getMessage());
        } else {
            holder.call.setEnabled(true);
            holder.call.setText(profile.getRequestStatus().getMessage());
        }
        }catch (Exception e)
        {
            holder.call.setEnabled(true);
            holder.call.setText(profile.getRequestStatus().getMessage());
        }

        if (position == biodataProfile.size() - 1)
            LoadData();


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setConnectionRequest(profile.getId(), position);

            }
        });


        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProfile(position);;

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
        return biodataProfile.size();
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