package com.nascenia.biyeta.adapter;

/**
 * Created by masum on 10/9/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.List;


public class BrotherViewAdapter extends RecyclerView.Adapter<BrotherViewAdapter.MyViewHolder> {

    LinearLayout professonalStatusBrother;
    LinearLayout occupationStatusBrother;
    LinearLayout maritalStatusBrother;
    LinearLayout ageBrother;

    public static TextView brotherOccupation;
    public static TextView brotherProfessionalGroup;
    public static TextView brotherMaritalStatus;
    public static TextView brotherAge;



    private List<Integer> brotherCount;

    private Context context;

    public static int adapterPosition = 0;

    public static int selectedPopUp = 0, brother = 0;
    public static String responseBrother = "";

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout reject;
        public EditText nameBrother;
        public EditText designationBrother;
        public EditText institutionBrother;

        public int age = 0;

        public String occupation="";
        public String professonalGroup="";
        public String maritalStatus="";

        public MyViewHolder(View view){
            super(view);
            nameBrother = (EditText) view.findViewById(R.id.name_brother);
            designationBrother = (EditText) view.findViewById(R.id.designation_brother);
            institutionBrother = (EditText) view.findViewById(R.id.institution_brother);
            brotherOccupation = (TextView) view.findViewById(R.id.profession_text_view_brother);
            brotherProfessionalGroup = (TextView) view.findViewById(R.id.profession_group_text_view_brother);
            brotherMaritalStatus = (TextView) view.findViewById(R.id.marital_text_view_brother);
            brotherAge = (TextView) view.findViewById(R.id.age_text_view_brother);
            reject = (LinearLayout) view.findViewById(R.id.reject);



            reject.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    brotherCount.remove(position);
                    notifyItemRemoved(position);

                }
            });

            occupationStatusBrother = (LinearLayout) view.findViewById(R.id.professonal_status_brother);
            occupationStatusBrother.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    selectedPopUp = 1;
                    brother = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position",getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            professonalStatusBrother = (LinearLayout) view.findViewById(R.id.professonal_group_status_brother);
            professonalStatusBrother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 2;
                    brother = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position",getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            maritalStatusBrother = (LinearLayout) view.findViewById(R.id.marital_status_brother);
            maritalStatusBrother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 3;
                    brother = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position",getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            ageBrother = (LinearLayout) view.findViewById(R.id.age_brother);
            ageBrother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 5;
                    brother = 1;
                    Intent setIntent = new Intent(context,PopUpFamilyInfoSecondPage.class);
                    setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                    setIntent.putExtra("position",getAdapterPosition());
                    context.startActivity(setIntent);
                }
            });

            String siblingType="", name="", designation="", institute="", spouse="", sOccupation="",sProfessionalGroup="", sDesignation="", sInstitute="";

            siblingType = "1";
            name = nameBrother.getText().toString();

            Toast.makeText(view.getContext(),name,Toast.LENGTH_LONG).show();

            designation = designationBrother.getText().toString();
            institute = institutionBrother.getText().toString();
            spouse = "";
            sOccupation = "";
            sProfessionalGroup = "";
            sDesignation = "";
            sInstitute = "";





        }


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        
    }

    public int listSize()
    {
        return brotherCount.size();
    }

    public void add(Integer item, int position) {
        this.brotherCount.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Integer item) {
        int position = brotherCount.indexOf(item);
        brotherCount.remove(position);
        notifyItemRemoved(position);
    }


    public BrotherViewAdapter(List<Integer> brotherCount, Context context){
        this.brotherCount = brotherCount;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brother, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Integer brother = brotherCount.get(position);
    }

    @Override
    public int getItemCount() {
        return brotherCount.size();
    }

}