package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 1/6/2017.
 */

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.SearchProfileModel;

import java.util.List;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;

public class Match_Adapter extends RecyclerView.Adapter<Match_Adapter.MyViewHolder> {

    //list of all profile
    private List<SearchProfileModel> profile_list;
    //parent context
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, profession, age,skin_color,marital_status,weight_status,religion,city;
        public ImageView profile_image;


        ///constractor
        //initialize all the view here
        public MyViewHolder(View view) {
            super(view);
            user_name=(TextView)view.findViewById(R.id.user_name);
            profile_image=(ImageView)view.findViewById(R.id.profile_image);
            profession=(TextView) view.findViewById(R.id.profession);


        }
    }


    public Match_Adapter(List<SearchProfileModel> moviesList) {
        this.profile_list = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matching_list_item, parent, false);
        context=parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SearchProfileModel prfile = profile_list.get(position);
        holder.user_name.setText(prfile.getDisplay_name());

        String gender = new SharePref(holder.profile_image.getContext()).get_data("gender");
        Glide.
                with(holder.profile_image.getContext()).
                load(Utils.Base_URL + prfile.getImage()).
                placeholder(gender.equalsIgnoreCase("female")?R.drawable.profile_icon_male:R.drawable.profile_icon_female).
                into(holder.profile_image);
        ;

    }

    @Override
    public int getItemCount() {
        return profile_list.size();
    }
}