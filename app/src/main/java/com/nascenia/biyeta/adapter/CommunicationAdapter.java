package com.nascenia.biyeta.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
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
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.InboxAllThreads.Inbox;
import com.nascenia.biyeta.model.communication.profile.CommunicationProfile;
import com.nascenia.biyeta.model.communication.profile.Profile;
import com.nascenia.biyeta.utils.Utils;

import java.util.List;

/**
 * Created by god father on 3/16/2017.
 */

public abstract class CommunicationAdapter extends RecyclerView.Adapter<CommunicationAdapter.ViewHolder> {

    private Tracker mTracker;
    private AnalyticsApplication application;

    public abstract void LoadData();
    public abstract void onClickProfile(int position);

    Context context;
    private List<Profile> communicationProfile;
    private int itemLayout;
    int currentUserSignedIn;

    public CommunicationAdapter(Context context, List<Profile> CommunicationProfile, int itemLayout, int cureentUserSignIn,
                                AnalyticsApplication application, Tracker mTracker) {
        this.context = context;
        this.communicationProfile = CommunicationProfile;
        this.currentUserSignedIn = cureentUserSignIn;
        this.itemLayout = itemLayout;
        this.application = application;
        this.mTracker = mTracker;
    }

    @Override
    public CommunicationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new CommunicationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CommunicationAdapter.ViewHolder holder, final int position) {

        final Profile profile = communicationProfile.get(position);

        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(Utils.convertEnglishDigittoBangla(profile.getAge()) + " বছর" + ", " +
                Utils.convertEnglishDigittoBangla(profile.getHeightFt()) + "'" +
                Utils.convertEnglishDigittoBangla(profile.getHeightInc()) + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        holder.time_date.setText(Utils.getTime(profile.getIsCreatedAt()));
        String gender = new SharePref(holder.image.getContext()).get_data("gender");
        Glide.
                with(holder.image.getContext()).
                load(Utils.Base_URL + profile.getImage()).
                placeholder(gender.equalsIgnoreCase("female") ? R.drawable.profile_icon_male : R.drawable.profile_icon_female).
                into(holder.image);
//        holder.itemView.setTag(item);

        if (position == communicationProfile.size() - 1)
            LoadData();


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + profile.getMobileNumber()));
                if (ActivityCompat.checkSelfPermission(holder.call.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                } else {
                    TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    int simState = telMgr.getSimState();
                    if(simState==TelephonyManager.SIM_STATE_ABSENT)
                    {
                        Toast.makeText(context,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
                    }
                    else if(simState==TelephonyManager.SIM_STATE_UNKNOWN)
                    {
                        Toast.makeText(context,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
                    }
                    else
                        holder.call.getContext().startActivity(callIntent);
                }
                application.setEvent("Action", "Click", "Phone Called", mTracker);

            }
        });
        holder.message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.image.getContext(), InboxSingleChat.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sender_id", currentUserSignedIn);
                bundle.putInt("receiver_id", profile.getUserId());
                bundle.putInt("current_user", currentUserSignedIn);
                bundle.putString("userName", profile.getDisplayName());
                in.putExtras(bundle);
                holder.image.getContext().startActivity(in);

                application.setEvent("Action", "Click", "Send Message", mTracker);
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
        holder.time_date.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return communicationProfile.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView userName;
        public TextView details;
        public TextView time_date;
        Button call, message;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            details = (TextView) itemView.findViewById(R.id.details);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.date_time);
            call = (Button) itemView.findViewById(R.id.phone);
            message = (Button) itemView.findViewById(R.id.message);

        }
    }
}