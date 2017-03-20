package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.UserProfileChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saiful on 3/17/17.
 */

public class OtherInfoRecylerViewAdapter extends RecyclerView.Adapter<OtherInfoRecylerViewAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private List<MatchUserChoice> infoArrayList;
    private Context baseContext;


    public OtherInfoRecylerViewAdapter(Context baseContext, ArrayList<MatchUserChoice> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.baseContext = baseContext;
        this.inflater = LayoutInflater.from(baseContext);
    }

    @Override
    public OtherInfoRecylerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.other_info_child_item, parent, false);
        OtherInfoRecylerViewAdapter.MyViewHolder myViewHolder = new OtherInfoRecylerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(OtherInfoRecylerViewAdapter.MyViewHolder holder, int position) {
        holder.titleTextView.setText(infoArrayList.get(position).getTitleName());
        holder.titleResultTextView.setText(infoArrayList.get(position).getTitleNameValue());
    }

    @Override
    public int getItemCount() {
        return this.infoArrayList.size();

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView titleResultTextView;


        public MyViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            titleResultTextView = (TextView) itemView.findViewById(R.id.titleResultTextView);

        }
    }


}
