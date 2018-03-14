package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 1/6/2017.
 */

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.activity.OwnUserProfileActivity;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.SearchProfileModel;

import java.util.List;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public abstract class Profile_Adapter extends RecyclerView.Adapter<Profile_Adapter.MyViewHolder> {
    //parent context
    Context context;
    //list of all profile
    private List<SearchProfileModel> profile_list;

    public Profile_Adapter(List<SearchProfileModel> moviesList) {
        this.profile_list = moviesList;
    }

    public abstract void load();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_item, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SearchProfileModel prfile = profile_list.get(position);
        if(prfile.getReal_name()=="null")
        {
            if(prfile.getDisplay_name()=="null")
            {
                holder.user_name.setText(prfile.getId());
            }
            else{
                holder.user_name.setText(prfile.getDisplay_name());
            }
        }

        else if(prfile.getReal_name()!="null"){
            holder.user_name.setText(prfile.getReal_name());
        }
        holder.details.setText(Utils.convertEnglishDigittoBangla(Integer.parseInt(prfile.getAge()))
                + " বছর, " + Utils.convertEnglishDigittoBangla(Integer.parseInt(prfile.getHeight_ft()))
                + "'" + Utils.convertEnglishDigittoBangla(Integer.parseInt(prfile.getHeight_inc()))
                + "\", " + prfile.getProfessional_group() + ", "
                + prfile.getSkin_color() + ", " + prfile.getHealth() + ", " + prfile.getLocation());
//        Log.e("image_link", prfile.getImage());
        if ((position >= getItemCount() - 1))
            load();
        final String gender = new SharePref(holder.profile_image.getContext()).get_data("gender");
        holder.profile_image.setImageResource(gender.equalsIgnoreCase("male") ? R.drawable.profile_icon_female : R.drawable.profile_icon_male);
//        Glide.
//                with(holder.profile_image.getContext()).
//                load(Utils.Base_URL + prfile.getImage()).
//                placeholder(gender.equalsIgnoreCase("male") ? R.drawable.profile_icon_female : R.drawable.profile_icon_male).
//                into(holder.profile_image);

        if(prfile.getImage()!= null) {
            Picasso.with(context)
                    .load(Utils.Base_URL + prfile.getImage())
                    .into(holder.profile_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.profile_image.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.profile_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                                    Utils.scaleImage(context, holder.profile_image);
                                }
                            });
                        }

                        @Override
                        public void onError() {
//                            holder.profile_image.setImageResource(gender.equalsIgnoreCase("male") ? R.drawable.profile_icon_female : R.drawable.profile_icon_male);
                        }
                    });
        }else{
            holder.profile_image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public int getItemCount() {
        return profile_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;   //profession, age,skin_color,marital_status,weight_status,religion,city;
        public TextView details;
        public ImageView profile_image;


        ///constractor
        //initialize all the view here
        public MyViewHolder(View view) {
            super(view);
            details = (TextView) view.findViewById(R.id.details);
            user_name = (TextView) view.findViewById(R.id.user_name);
            profile_image = (ImageView) view.findViewById(R.id.profile_image);
        }
    }
}