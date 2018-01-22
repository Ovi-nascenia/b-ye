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

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.PopUpFamilyInfoSecondPage;
import com.nascenia.biyeta.activity.RegistrationFamilyInfoSecondPage;
import com.nascenia.biyeta.utils.RemoveSisterItemCallBack;
import com.nascenia.biyeta.utils.Utils;

import java.util.List;


public class SisterViewAdapter extends RecyclerView.Adapter<SisterViewAdapter.MyViewHolder> {

    LinearLayout professonalStatusSister;
    LinearLayout occupationStatusSister;
    LinearLayout maritalStatusSister;
    LinearLayout ageSister;

    public static TextView sisterOccupation;
    public static TextView sisterProfessionalGroup;
    public static TextView sisterMaritalStatus;
    public static TextView sisterAge;

    private List<Integer> sisterCount;

    public static String responseSister = "";

    private Context context;

    public static int adapterPosition = 0;

    public static int selectedPopUp = 0, sister = 0, age = 0;
    //public static String occupation = "", professonalGroup = "", maritalStatus = "";

    private RemoveSisterItemCallBack removeSisterItemCallBack;

    public SisterViewAdapter(List<Integer> SisterCount, Context context,
                             RemoveSisterItemCallBack removeSisterItemCallBack) {
        this.sisterCount = SisterCount;
        this.context = context;
        this.removeSisterItemCallBack = removeSisterItemCallBack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout reject;

        public EditText nameSister;
        public EditText designationSister;
        public EditText institutionSister;
        public TextView sisterOccupation;
        public TextView sisterProfessionalGroup;
        public TextView sisterMaritalStatus;
        public TextView sisterAge;
        public TextView tv_sister;
        public LinearLayout spouse;
        public LinearLayout rejectSpouse;
        public EditText nameSisterSpouse;
        public TextView sisterOcupationSpouse;
        public TextView sisterProfessionalGroupSpouse;
        LinearLayout occupationStatusSisterSpouse;
        LinearLayout professonalStatusSisterSpouse;



        public MyViewHolder(View view) {
            super(view);
            nameSister = (EditText) view.findViewById(R.id.name_sister);
            designationSister = (EditText) view.findViewById(R.id.designation_sister);
            institutionSister = (EditText) view.findViewById(R.id.institution_sister);

            sisterOccupation = (TextView) view.findViewById(R.id.profession_text_view_sister);
            sisterProfessionalGroup = (TextView) view.findViewById(R.id.profession_group_text_view_sister);
            sisterMaritalStatus = (TextView) view.findViewById(R.id.marital_text_view_sister);
            sisterAge = (TextView) view.findViewById(R.id.age_text_view_sister);
            tv_sister = (TextView) view.findViewById(R.id.tv_sister);

            sisterOcupationSpouse = (TextView) view.findViewById(R.id.profession_text_view_sister_spouse);
            sisterProfessionalGroupSpouse = (TextView) view.findViewById(R.id.profession_group_text_view_sister_spouse);
            nameSisterSpouse = (EditText) view.findViewById(R.id.name_sister_spouse);

            spouse = (LinearLayout) view.findViewById(R.id.spouse);

            final String siblingType, name, designation, institute, spouseName, sOccupation, sProfessionalGroup, sDesignation, sInstitute;
            reject = (LinearLayout) view.findViewById(R.id.reject);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    sisterCount.remove(position);
                    notifyItemRemoved(position);
                    removeSisterItemCallBack.removeSisterItem();

                }
            });

            occupationStatusSister = (LinearLayout) view.findViewById(R.id.professonal_status_sister);
            occupationStatusSister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 1;
                    sister = 1;
                    Intent setIntent = new Intent(context, PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position", getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            professonalStatusSister = (LinearLayout) view.findViewById(R.id.professonal_group_status_sister);
            professonalStatusSister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 2;
                    sister = 1;
                    Intent setIntent = new Intent(context, PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position", getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            maritalStatusSister = (LinearLayout) view.findViewById(R.id.marital_status_sister);
            maritalStatusSister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 3;
                    sister = 1;
                    Intent setIntent = new Intent(context, PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position", getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            ageSister = (LinearLayout) view.findViewById(R.id.age_sister);
            ageSister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 5;
                    sister = 1;
                    Intent setIntent = new Intent(context, PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position", getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            occupationStatusSisterSpouse = view.findViewById(R.id.professonal_status_sister_spouse);
            occupationStatusSisterSpouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 6;
                    sister = 1;
                    Intent setIntent = new Intent(context, PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position", getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });
            professonalStatusSisterSpouse = view.findViewById(R.id.professonal_group_status_sister_spouse);
            professonalStatusSisterSpouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 7;
                    sister = 1;
                    Intent setIntent = new Intent(context, PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position", getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            rejectSpouse = (LinearLayout) view.findViewById(R.id.reject_spouse);
            rejectSpouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spouse.setVisibility(View.GONE);
                }
            });

            siblingType = "2";
            name = nameSister.getText().toString();
            designation = designationSister.getText().toString();
            institute = institutionSister.getText().toString();
            spouseName = "";
            sOccupation = "";
            sProfessionalGroup = "";
            sDesignation = "";
            sInstitute = "";
        }

    }

    public int listSize() {
        return sisterCount.size();
    }

    public void add(Integer item, int position) {
        this.sisterCount.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Integer item) {
        int position = sisterCount.indexOf(item);
        sisterCount.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sister, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Integer Sister = sisterCount.get(position);
        if (position == 0) {
            holder.reject.setVisibility(View.GONE);
        }
        holder.tv_sister.setText("বোন " + Utils.convertEnglishDigittoBangla(position + 1));
    }

    @Override
    public int getItemCount() {
        return sisterCount.size();
    }
}