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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;
import com.nascenia.biyeta.model.communication.profile.CommunicationProfile;
import com.nascenia.biyeta.model.communication.profile.Profile;
import com.nascenia.biyeta.model.newuserprofile.Image;
import com.nascenia.biyeta.utils.Utils;

/**
 * Created by god father on 3/16/2017.
 */

public abstract class BiodatarequestFromMe extends RecyclerView.Adapter<BiodatarequestFromMe.ViewHolder> {


    public abstract void onClickSmile(int id, int position);

    public abstract void onClickItem(int id, int position);

    private BiodataProfile biodataProfile;
    private int itemLayout;

    public BiodatarequestFromMe(BiodataProfile biodataProfile, int itemLayout) {
        this.biodataProfile = biodataProfile;
        this.itemLayout = itemLayout;
    }

    @Override
    public BiodatarequestFromMe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new BiodatarequestFromMe.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BiodatarequestFromMe.ViewHolder holder, final int position) {

        final com.nascenia.biyeta.model.biodata.profile.Profile profile = biodataProfile.getProfiles().get(position);

        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(Utils.convertEnglishDigittoBangla(profile.getAge()) + " বছর" + ", " +
                Utils.convertEnglishDigittoBangla(profile.getHeightFt()) + "'" +
                Utils.convertEnglishDigittoBangla(profile.getHeightInc()) + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        String gender = new SharePref(holder.image.getContext()).get_data("gender");
        Glide.
                with(holder.image.getContext()).
                load(Utils.Base_URL + profile.getImage()).
                placeholder(gender.equalsIgnoreCase("female") ? R.drawable.profile_icon_male : R.drawable.profile_icon_female).
                into(holder.image);
        holder.time_date.setText(Utils.getTime(profile.getIsCreatedAt()));
//        holder.itemView.setTag(item);


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem(profile.getId(), position);


            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem(profile.getId(), position);


            }
        });
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem(profile.getId(), position);


            }
        });


        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSmile(profile.getId(), position);


            }
        });

//        if (profile.isSmileSent() == false) {
//            holder.imageViewSmile.setVisibility(View.VISIBLE);
//            holder.status.setText("হাসি পাঠান");
//            holder.message.setVisibility(View.GONE);
//            //holder.message.setText(profile.getRequestStatus().getMessage());
//        } else {
            holder.imageViewSmile.setVisibility(View.GONE);
            holder.status.setVisibility(View.GONE);
            holder.message.setText(profile.getRequestStatus().getMessage());
//        }

        holder.imageViewSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSmile(profile.getId(), position);
            }
        });


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
        public TextView message;
        public ImageView imageViewSmile;
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            details = (TextView) itemView.findViewById(R.id.details);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.date_time);
            status = (TextView) itemView.findViewById(R.id.status);
            imageViewSmile = (ImageView) itemView.findViewById(R.id.smile_ico);
            message = (TextView) itemView.findViewById(R.id.message);


        }
    }
}