package com.nascenia.biyeta.adapter;

/**
 * Created by god father on 3/22/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;

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
import com.nascenia.biyeta.model.communication_request_from_me.Profile;
import com.nascenia.biyeta.model.communication_request_from_me.CommuncationRequestFromMeModel;
import com.nascenia.biyeta.model.newuserprofile.Image;

public class CommunicationRequestFromMeAdapter extends RecyclerView.Adapter<CommunicationRequestFromMeAdapter.ViewHolder> {

    private CommuncationRequestFromMeModel communcationRequestFromMeModel;
    private int itemLayout;

    public CommunicationRequestFromMeAdapter(CommuncationRequestFromMeModel biodataProfile, int itemLayout) {
        this.communcationRequestFromMeModel = biodataProfile;
        this.itemLayout = itemLayout;
    }

    @Override
    public CommunicationRequestFromMeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new CommunicationRequestFromMeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CommunicationRequestFromMeAdapter.ViewHolder holder, final int position) {

        Profile profile = communcationRequestFromMeModel.getProfiles().get(position);

        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(profile.getAge() + "বয়স" + ", " + profile.getHeightFt() + "'" + profile.getHeightInc() + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        Glide.
                with(holder.image.getContext()).
                load(profile.getImage()).
                placeholder(R.drawable.fake_image).
                into(holder.image);
//        holder.itemView.setTag(item);


        if (null != profile.getRequestStatus().getExpired()) {
            if (profile.getRequestStatus().getExpired() == true) {
                holder.connectoion.setVisibility(View.VISIBLE);
                holder.connectoion.setText("আবারো যোগাযোগ করুন");
                holder.status.setText(profile.getRequestStatus().getMessage());
            } else {
                holder.connectoion.setVisibility(View.GONE);
                holder.status.setText(profile.getRequestStatus().getMessage());
            }
        }
        else {
            holder.connectoion.setVisibility(View.GONE);
            holder.status.setText(profile.getRequestStatus().getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return communcationRequestFromMeModel.getProfiles().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView userName;
        public TextView details;
        public TextView time_date;
        TextView connectoion;
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            details = (TextView) itemView.findViewById(R.id.details);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.date_time);
            status = (TextView) itemView.findViewById(R.id.status);
            connectoion = (TextView) itemView.findViewById(R.id.connection_button);


        }
    }
}