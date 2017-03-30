package com.nascenia.biyeta.adapter;

/**
 * Created by god father on 3/27/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.ExpireList.ExpireProfile;
import com.nascenia.biyeta.model.ExpireList.Profile;
import com.nascenia.biyeta.utils.Utils;

/**
 * Created by god father on 3/16/2017.
 */

public abstract class ExpireListAdapter extends RecyclerView.Adapter<ExpireListAdapter.ViewHolder> {


    public abstract void onClickSmile(int id,int postion);

    private ExpireProfile ExpireProfile;
    private int itemLayout;

    public ExpireListAdapter(ExpireProfile ExpireProfile, int itemLayout) {
        this.ExpireProfile = ExpireProfile;
        this.itemLayout = itemLayout;
    }

    @Override
    public ExpireListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ExpireListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExpireListAdapter.ViewHolder holder, final int position) {

      final Profile profile = ExpireProfile.getProfiles().get(position);

        holder.userName.setText(profile.getDisplayName());
        holder.details.setText(profile.getAge() + "বয়স" + ", " + profile.getHeightFt() + "'" + profile.getHeightInc() + "''" + ", " + profile.getProfessionalGroup() + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());
        String gender = new SharePref(holder.image.getContext()).get_data("gender");
        Glide.
                with(holder.image.getContext()).
                load(Utils.Base_URL + profile.getImage()).
                placeholder(gender.equalsIgnoreCase("female")?R.drawable.profile_icon_male:R.drawable.profile_icon_female).
                into(holder.image);
        holder.time_date.setText(profile.getIsCreatedAt());




//        if (profile.isSmileSent()==false)
//        {
//            holder.imageViewSmile.setVisibility(View.VISIBLE);
//            holder.status.setText("হাসি পাঠান");
//        }
//
//        else {
//            holder.imageViewSmile.setVisibility(View.GONE);
//            holder.status.setText(profile.getRequestStatus().getMessage());
//        }
//
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSmile(profile.getId(),position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return ExpireProfile.getProfiles().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView userName;
        public TextView details;
        public TextView time_date;
        Button status;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            details = (TextView) itemView.findViewById(R.id.details);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.date_time);
            status=(Button) itemView.findViewById(R.id.status);


        }
    }
}