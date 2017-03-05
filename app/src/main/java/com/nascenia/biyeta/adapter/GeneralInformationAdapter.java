package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.GeneralInformation;

import java.util.ArrayList;

/**
 * Created by saiful on 3/5/17.
 */
public class GeneralInformationAdapter extends RecyclerView.Adapter<GeneralInformationAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<GeneralInformation> generalInformationArrayList;

    public GeneralInformationAdapter(Context context, ArrayList<GeneralInformation> generalInformationArrayList) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.generalInformationArrayList = generalInformationArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.general_information_recylerview_item, parent, false);
        MyViewHolder myViewHolder = new GeneralInformationAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.titleValueTextview.setText(this.generalInformationArrayList.get(position).getGeneralInfo());

    }

    @Override
    public int getItemCount() {
        return this.generalInformationArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleValueTextview;


        public MyViewHolder(View itemView) {
            super(itemView);

            titleValueTextview = (TextView) itemView.findViewById(R.id.title_value_textview);


        }
    }
}
