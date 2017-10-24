package com.nascenia.biyeta.adapter;

/**
 * Created by masum on 10/9/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.PopUpFamilyInfoSecondPage;
import com.nascenia.biyeta.activity.RegistrationFamilyInfoSecondPage;

import java.util.List;


public class OtherViewAdapter extends RecyclerView.Adapter<OtherViewAdapter.MyViewHolder> {

    LinearLayout professonalStatusOther;
    LinearLayout occupationStatusOther;
    LinearLayout maritalStatusOther;
    LinearLayout relationalStatusOther;
    LinearLayout ageOther;

    public static TextView otherOccupation;
    public static TextView otherProfessionalGroup;
    public static TextView otherMaritalStatus;
    public static TextView otherRelationalStatus;
    public static TextView otherAge;

    public static String responseOther = "";


    private List<Integer> otherCount;

    private Context context;
    public static int selectedPopUp = 0, other = 0,age = 0;
    public static String occupation="", professonalGroup="", maritalStatus="", relation="";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout reject;
        public EditText nameOther;
        public EditText designationOther;
        public EditText institutionOther;

        String name="", designation="", institute="";
        public MyViewHolder(View view){
            super(view);

            nameOther = (EditText) view.findViewById(R.id.name_other);
            designationOther = (EditText) view.findViewById(R.id.designation_other);
            institutionOther = (EditText) view.findViewById(R.id.institution_other);

            otherOccupation = (TextView) view.findViewById(R.id.profession_text_view_other);
            otherProfessionalGroup = (TextView) view.findViewById(R.id.profession_group_text_view_other);
            otherMaritalStatus = (TextView) view.findViewById(R.id.marital_text_view_other);
            otherRelationalStatus = (TextView) view.findViewById(R.id.relation_text_view_other);
            otherAge = (TextView) view.findViewById(R.id.age_text_view_other);


            reject = (LinearLayout) view.findViewById(R.id.reject);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    otherCount.remove(position);
                    notifyItemRemoved(position);

                }
            });

            occupationStatusOther = (LinearLayout) view.findViewById(R.id.professonal_status_other);
            occupationStatusOther.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    selectedPopUp = 1;
                    other = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    context.startActivity(setIntent);
                }
            });

            professonalStatusOther = (LinearLayout) view.findViewById(R.id.professonal_group_status_other);
            professonalStatusOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 2;
                    other = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    context.startActivity(setIntent);
                }
            });

            maritalStatusOther = (LinearLayout) view.findViewById(R.id.marital_status_other);
            maritalStatusOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 3;
                    other = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    context.startActivity(setIntent);
                }
            });

            relationalStatusOther = (LinearLayout) view.findViewById(R.id.relational_status_other);
            relationalStatusOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    selectedPopUp = 4;
                    other = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    context.startActivity(setIntent);
                }
            });

            ageOther = (LinearLayout) view.findViewById(R.id.age_other);
            ageOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    selectedPopUp = 5;
                    other = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    context.startActivity(setIntent);
                }
            });



        }

    }

    public int listSize()
    {
        return otherCount.size();
    }

    public void add(Integer item, int position) {
        this.otherCount.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Integer item) {
        int position = otherCount.indexOf(item);
        otherCount.remove(position);
        notifyItemRemoved(position);
    }


    public OtherViewAdapter(List<Integer> otherCount, Context context){
        this.otherCount = otherCount;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.other, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        if(position == 0){
            holder.reject.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return otherCount.size();
    }
}