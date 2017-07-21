package com.nascenia.biyeta.adapter;

/**
 * Created by god father on 2/24/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.PartialProfileItemModel;

import java.util.List;


/**
 * Created by user on 1/6/2017.
 */

public class PartialProfileViewAdapter extends RecyclerView.Adapter<PartialProfileViewAdapter.MyViewHolder> {


    //list of all profile
    private List<PartialProfileItemModel> profile_list;
    //parent context
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;   //profession, age,skin_color,marital_status,weight_status,religion,city;
        public TextView item_value;


        ///constractor
        //initialize all the view here
        public MyViewHolder(View view) {
            super(view);
            item_name = (TextView) view.findViewById(R.id.item_name);
            item_value = (TextView) view.findViewById(R.id.item_value);


        }
    }


    public PartialProfileViewAdapter(List<PartialProfileItemModel> moviesList) {
        this.profile_list = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_profile_item, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PartialProfileItemModel profile = profile_list.get(position);
        holder.item_name.setText(profile.getKey());
        holder.item_value.setText(profile.getValue());
        

    }

    @Override
    public int getItemCount() {
        return profile_list.size();
    }
}