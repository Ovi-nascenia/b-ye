package com.nascenia.biyeta.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;
import com.nascenia.biyeta.model.communication.profile.CommunicationProfile;
import com.nascenia.biyeta.model.communication.profile.Profile;

/**
 * Created by god father on 3/16/2017.
 */

public class BiodataProfileAdapter extends RecyclerView.Adapter<BiodataProfileAdapter.ViewHolder> {

    private BiodataProfile biodataProfile;
    private int itemLayout;

    public BiodataProfileAdapter(BiodataProfile biodataProfile, int itemLayout) {
        this.biodataProfile = biodataProfile;
        this.itemLayout = itemLayout;
    }

    @Override
    public BiodataProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new BiodataProfileAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BiodataProfileAdapter.ViewHolder holder, final int position) {

        final com.nascenia.biyeta.model.biodata.profile.Profile profile = biodataProfile.getProfiles().get(position);

        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(profile.getAge() + "বয়স" + ", " + profile.getHeightFt() + "'" + profile.getHeightInc() + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        Glide.
                with(holder.image.getContext()).
                load(profile.getImage()).
                placeholder(R.drawable.default_profile_female_icon).
                into(holder.image);
//        holder.itemView.setTag(item);




        holder.call.setText(profile.getRequestStatus().getMessage());



    }

    @Override
    public int getItemCount() {
        return biodataProfile.getProfiles().size();
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
            call=(Button)itemView.findViewById(R.id.phone);


        }
    }
}