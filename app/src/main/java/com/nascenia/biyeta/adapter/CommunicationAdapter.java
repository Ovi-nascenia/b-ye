package com.nascenia.biyeta.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
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

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.model.InboxAllThreads.Inbox;
import com.nascenia.biyeta.model.communication.profile.CommunicationProfile;
import com.nascenia.biyeta.model.communication.profile.Profile;

/**
 * Created by god father on 3/16/2017.
 */

public class CommunicationAdapter extends RecyclerView.Adapter<CommunicationAdapter.ViewHolder> {

    private CommunicationProfile communicationProfile;
    private int itemLayout;

    public CommunicationAdapter(CommunicationProfile CommunicationProfile, int itemLayout) {
        this.communicationProfile = CommunicationProfile;
        this.itemLayout = itemLayout;
    }

    @Override
    public CommunicationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new CommunicationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CommunicationAdapter.ViewHolder holder, final int position) {

        final Profile profile = communicationProfile.getProfiles().get(position);

        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(profile.getAge() + "বয়স" + ", " + profile.getHeightFt() + "'" + profile.getHeightInc() + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        Glide.
                with(holder.image.getContext()).
                load("http://test.biyeta.com"+profile.getImage()).
                placeholder(R.drawable.fake_image).
                into(holder.image);
//        holder.itemView.setTag(item);


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
                }
                else {
                    holder.call.getContext().startActivity(callIntent);
                }

            }
        });
        holder.message.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent in=new Intent(holder.image.getContext(), InboxSingleChat.class);
                Bundle bundle=new Bundle();
                bundle.putInt("sender_id",communicationProfile.getCurrentUserSignedIn());
                bundle.putInt("receiver_id",profile.getUserId());
                bundle.putInt("current_user",communicationProfile.getCurrentUserSignedIn());
                bundle.putString("userName",profile.getDisplayName());
                in.putExtras(bundle);
                holder.image.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return communicationProfile.getProfiles().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView userName;
        public TextView details;
        public TextView time_date;
        Button call,message;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            details = (TextView) itemView.findViewById(R.id.details);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.date_time);
            call=(Button)itemView.findViewById(R.id.phone);
            message=(Button)itemView.findViewById(R.id.message);

        }
    }
}