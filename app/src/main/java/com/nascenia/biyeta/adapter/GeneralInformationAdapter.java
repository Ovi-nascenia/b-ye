package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private String generalInfoValue;


    public GeneralInformationAdapter(Context context, ArrayList<GeneralInformation> generalInformationArrayList) {


        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.generalInformationArrayList = generalInformationArrayList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.general_information_recylerview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        generalInfoValue = this.generalInformationArrayList.get(position).getGeneralInfo();
        if (generalInfoValue.startsWith(",")) {

            generalInfoValue = removeChar(generalInfoValue, 0);
        }

        if (generalInfoValue.charAt(generalInfoValue.length() - 1) == ',') {

            generalInfoValue = removeChar(generalInfoValue, generalInfoValue.length() - 1);
        }


        holder.titlegeneralInfoValueTextview.setText(generalInfoValue);
        holder.titleImageView.setImageResource(this.generalInformationArrayList.get(position).getItemImageDrwableId());

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return this.generalInformationArrayList.size();
    }


    public String removeChar(String str, Integer n) {

        return str.substring(0, n) + str.substring(n + 1, str.length());
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titlegeneralInfoValueTextview;
        ImageView titleImageView;


        public MyViewHolder(View itemView) {
            super(itemView);

            titlegeneralInfoValueTextview = (TextView) itemView.findViewById(R.id.title_value_textview);
            titleImageView = (ImageView) itemView.findViewById(R.id.title_imageView);

        }
    }
}
