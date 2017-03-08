package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.MatchUserChoice;

import java.util.ArrayList;

/**
 * Created by saiful on 3/5/17.
 */
public class MatchUserChoiceAdapter extends RecyclerView.Adapter<MatchUserChoiceAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList;


    public MatchUserChoiceAdapter(Context context, ArrayList<MatchUserChoice> matchUserChoiceArrayList) {

        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.matchUserChoiceArrayList = matchUserChoiceArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.match_user_choice_recyclerview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.titleTextview.setText(matchUserChoiceArrayList.get(position).getTitleName());
        holder.titleValueTextview.setText(matchUserChoiceArrayList.get(position).getTitleNameValue());


    }

    @Override
    public int getItemCount() {
        return matchUserChoiceArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextview;
        TextView titleValueTextview;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleTextview = (TextView) itemView.findViewById(R.id.title_textview);
            titleValueTextview = (TextView) itemView.findViewById(R.id.title_value_textview);


        }
    }

}
