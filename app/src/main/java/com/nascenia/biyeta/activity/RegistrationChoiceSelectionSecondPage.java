package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RegistrationChoiceSelectionSecondPage extends AppCompatActivity{
    public String constant;
    public int currentStep;
    LinearLayout l1;
    CheckBox occupationCheckbox, professonalGroupCheckBox;
    CheckBox dhakaDivision, mymensingDivision, chittagongDivision, khulnaDivision, rajshahiDivision, barisalDivision, sylhetDivision, rangpurDivision;
    CheckBox dhakaDivisionDistricts, mymensingDivisionDistricts, chittagongDivisionDistricts, khulnaDivisionDistricts, rajshahiDivisionDistricts, barisalDivisionDistricts, sylhetDivisionDistricts, rangpurDivisionDistricts;

    ArrayList<String> occupationSelectedArray;
    ArrayList<String> professonalGroupSelectedArray;
    ArrayList<String> districtSelectedArray;

    Button next;
    ImageView back;


    int dhakaDivisionCheckboxColor = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");

        occupationSelectedArray = new ArrayList<String>();
        professonalGroupSelectedArray = new ArrayList<String>();
        districtSelectedArray = new ArrayList<String>();

        final ArrayList<String> occupation = new ArrayList<String>();
        final ArrayList<String> occupationConstant = new ArrayList<String>();

        ArrayList<String> professonalGroup = new ArrayList<String>();
        final ArrayList<String> professonalGroupConstant = new ArrayList<String>();

        final ArrayList<String> dhakaDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> dhakaDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> mymensinghDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> mymensinghDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> chittagongDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> chittagongDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> khulnaDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> khulnaDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> rajshahiDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> rajshahiDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> barisalDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> barisalDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> sylhetDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> sylhetDivisionDistrictConstant = new ArrayList<String>();

        ArrayList<String> rangpurDivisionDistrict = new ArrayList<String>();
        final ArrayList<String> rangpurDivisionDistrictConstant = new ArrayList<String>();


        try {
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject professonalGroupObject = jsonObject.getJSONObject("professional_group_constant");
            JSONObject occupationObject = jsonObject.getJSONObject("occupation_constant");
            JSONObject dhakaDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("dhaka");
            JSONObject mymensinghDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("mymensingh");
            JSONObject chittagongDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("chittagong");
            JSONObject khulnaDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("khulna");
            JSONObject rajshahiDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("rajshahi");
            JSONObject barisalDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("barisal");
            JSONObject sylhetDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("sylhet");
            JSONObject rangpurDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("rangpur");


            for(int i=0;i<professonalGroupObject.length();i++)
            {
                professonalGroupConstant.add(professonalGroupObject.names().getString(i));
                professonalGroup.add((String) professonalGroupObject.get(professonalGroupObject.names().getString(i)));
            }

            for(int i=0;i<occupationObject.length();i++)
            {
                occupationConstant.add(occupationObject.names().getString(i));
                occupation.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            for(int i=0;i<mymensinghDivisionObject.length();i++)
            {
                mymensinghDivisionDistrictConstant.add(mymensinghDivisionObject.names().getString(i));
                mymensinghDivisionDistrict.add((String) mymensinghDivisionObject.get(mymensinghDivisionObject.names().getString(i)));
            }

            for(int i=0;i<dhakaDivisionObject.length();i++)
            {
                dhakaDivisionDistrictConstant.add(dhakaDivisionObject.names().getString(i));
                dhakaDivisionDistrict.add((String) dhakaDivisionObject.get(dhakaDivisionObject.names().getString(i)));
            }

            for(int i=0;i<chittagongDivisionObject.length();i++)
            {
                chittagongDivisionDistrictConstant.add(chittagongDivisionObject.names().getString(i));
                chittagongDivisionDistrict.add((String) chittagongDivisionObject.get(chittagongDivisionObject.names().getString(i)));
            }

            for(int i=0;i<khulnaDivisionObject.length();i++)
            {
                khulnaDivisionDistrictConstant.add(khulnaDivisionObject.names().getString(i));
                khulnaDivisionDistrict.add((String) khulnaDivisionObject.get(khulnaDivisionObject.names().getString(i)));
            }

            for(int i=0;i<rajshahiDivisionObject.length();i++)
            {
                rajshahiDivisionDistrictConstant.add(rajshahiDivisionObject.names().getString(i));
                rajshahiDivisionDistrict.add((String) rajshahiDivisionObject.get(rajshahiDivisionObject.names().getString(i)));
            }

            for(int i=0;i<barisalDivisionObject.length();i++)
            {
                barisalDivisionDistrictConstant.add(barisalDivisionObject.names().getString(i));
                barisalDivisionDistrict.add((String) barisalDivisionObject.get(barisalDivisionObject.names().getString(i)));
            }

            for(int i=0;i<sylhetDivisionObject.length();i++)
            {
                sylhetDivisionDistrictConstant.add(sylhetDivisionObject.names().getString(i));
                sylhetDivisionDistrict.add((String) sylhetDivisionObject.get(sylhetDivisionObject.names().getString(i)));
            }

            for(int i=0;i<rangpurDivisionObject.length();i++)
            {
                rangpurDivisionDistrictConstant.add(rangpurDivisionObject.names().getString(i));
                rangpurDivisionDistrict.add((String) rangpurDivisionObject.get(rangpurDivisionObject.names().getString(i)));
            }

            currentStep = 5;

        } catch(JSONException e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_registration_choice_selection_second_page);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Intent(RegistrationChoiceSelectionSecondPage.this,Login.class);
                finish();
            }
        });

        LinearLayout linear1 = (LinearLayout) findViewById(R.id.l1);

        for(int i = 0; i<occupationConstant.size() ; i++){
            occupationCheckbox = new CheckBox(this);
            occupationCheckbox.setText(occupation.get(i));
            occupationCheckbox.setId(Integer.parseInt(occupationConstant.get(i)));
            final int index = i;
            occupationCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(occupationConstant.get(index)));
                    if(checkBox.isChecked()){
                        occupationSelectedArray.add(occupationConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        occupationSelectedArray.remove(occupationConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ occupationConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            linear1.addView(occupationCheckbox);
        }

        LinearLayout linear2 = (LinearLayout) findViewById(R.id.l2);

        for(int i = 0; i<professonalGroupConstant.size() ; i++){
            professonalGroupCheckBox = new CheckBox(this);
            professonalGroupCheckBox.setText(professonalGroup.get(i));
            professonalGroupCheckBox.setId(Integer.parseInt(professonalGroupConstant.get(i)));
            final int index = i;
            professonalGroupCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(professonalGroupConstant.get(index)));
                    if(checkBox.isChecked()){
                        professonalGroupSelectedArray.add(professonalGroupConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        professonalGroupSelectedArray.remove(professonalGroupConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ professonalGroupConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            linear2.addView(professonalGroupCheckBox);
        }


        final LinearLayout dhaka = (LinearLayout) findViewById(R.id.dhaka_districts);

        for(int i = 0; i<dhakaDivisionDistrictConstant.size() ; i++){
            dhakaDivisionDistricts = new CheckBox(this);
            dhakaDivisionDistricts.setText(dhakaDivisionDistrict.get(i));
            dhakaDivisionDistricts.setId(Integer.parseInt(dhakaDivisionDistrictConstant.get(i)));
            final int index = i;
            dhakaDivisionDistricts.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(dhakaDivisionDistrictConstant.get(index)));
                    Log.i("Check", " "+dhakaDivisionDistricts.isChecked());
                    if(checkBox.isChecked()){
                        districtSelectedArray.add(dhakaDivisionDistrictConstant.get(index));
                        dhakaDivisionCheckboxColor++;
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(dhakaDivisionDistrictConstant.get(index));
                        dhakaDivisionCheckboxColor--;
                    }


                    if(dhakaDivisionCheckboxColor!=dhakaDivisionDistrict.size()){
//                        dhakaDivision.setBackgroundColor(Color.rgb(64, 131, 207));
                    }

                    else if(dhakaDivisionCheckboxColor==dhakaDivisionDistrict.size()){
//                        dhakaDivision.setBackgroundColor(Color.WHITE);
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ dhakaDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }


            });
            dhaka.addView(dhakaDivisionDistricts);
        }

        dhakaDivision = (CheckBox) findViewById(R.id.dhaka);
        dhakaDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(dhaka.getVisibility()==View.GONE){
                    dhaka.setVisibility(View.VISIBLE);
                }
                else{
                    dhaka.setVisibility(View.GONE);
                }
            }
        });



        final LinearLayout mymensing = (LinearLayout) findViewById(R.id.mymensing_districts);

        for(int i = 0; i<mymensinghDivisionDistrictConstant.size() ; i++){
            mymensingDivisionDistricts = new CheckBox(this);
            mymensingDivisionDistricts.setText(mymensinghDivisionDistrict.get(i));
            mymensingDivisionDistricts.setId(Integer.parseInt(mymensinghDivisionDistrictConstant.get(i)));
            final int index = i;
            mymensingDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(mymensinghDivisionDistrictConstant.get(index)));
                    if(checkBox.isChecked()){
                        districtSelectedArray.add(mymensinghDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(mymensinghDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ mymensinghDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            mymensing.addView(mymensingDivisionDistricts);
        }

        mymensingDivision = (CheckBox) findViewById(R.id.mymensing);
        mymensingDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mymensing.getVisibility()==View.GONE){
                    mymensing.setVisibility(View.VISIBLE);
                }
                else{
                    mymensing.setVisibility(View.GONE);
                }
            }
        });



        final LinearLayout chittagong = (LinearLayout) findViewById(R.id.chittagong_districts);

        for(int i = 0; i<chittagongDivisionDistrictConstant.size() ; i++){
            chittagongDivisionDistricts = new CheckBox(this);
            chittagongDivisionDistricts.setText(chittagongDivisionDistrict.get(i));
            chittagongDivisionDistricts.setId(Integer.parseInt(chittagongDivisionDistrictConstant.get(i)));
            final int index = i;
            chittagongDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(chittagongDivisionDistrictConstant.get(index)));

                    if(checkBox.isChecked()){
                        districtSelectedArray.add(chittagongDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(chittagongDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ chittagongDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            chittagong.addView(chittagongDivisionDistricts);
        }

        chittagongDivision = (CheckBox) findViewById(R.id.chittagong);
        chittagongDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(chittagong.getVisibility()==View.GONE){
                    chittagong.setVisibility(View.VISIBLE);
                }
                else{
                    chittagong.setVisibility(View.GONE);
                }
            }
        });

        final LinearLayout khulna = (LinearLayout) findViewById(R.id.khulna_districts);

        for(int i = 0; i<khulnaDivisionDistrictConstant.size() ; i++){
            khulnaDivisionDistricts = new CheckBox(this);
            khulnaDivisionDistricts.setText(khulnaDivisionDistrict.get(i));
            khulnaDivisionDistricts.setId(Integer.parseInt(khulnaDivisionDistrictConstant.get(i)));
            final int index = i;
            khulnaDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(khulnaDivisionDistrictConstant.get(index)));

                    if(checkBox.isChecked()){
                        districtSelectedArray.add(khulnaDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(khulnaDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ khulnaDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            khulna.addView(khulnaDivisionDistricts);
        }

        khulnaDivision = (CheckBox) findViewById(R.id.khulna);
        khulnaDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(khulna.getVisibility()==View.GONE){
                    khulna.setVisibility(View.VISIBLE);
                }
                else{
                    khulna.setVisibility(View.GONE);
                }
            }
        });


        final LinearLayout rajshahi = (LinearLayout) findViewById(R.id.rajshahi_districts);

        for(int i = 0; i<rajshahiDivisionDistrictConstant.size() ; i++){
            rajshahiDivisionDistricts = new CheckBox(this);
            rajshahiDivisionDistricts.setText(rajshahiDivisionDistrict.get(i));
            rajshahiDivisionDistricts.setId(Integer.parseInt(rajshahiDivisionDistrictConstant.get(i)));
            final int index = i;
            rajshahiDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(rajshahiDivisionDistrictConstant.get(index)));

                    if(checkBox.isChecked()){
                        districtSelectedArray.add(rajshahiDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(rajshahiDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ rajshahiDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            rajshahi.addView(rajshahiDivisionDistricts);
        }

        rajshahiDivision = (CheckBox) findViewById(R.id.rajshahi);
        rajshahiDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(rajshahi.getVisibility()==View.GONE){
                    rajshahi.setVisibility(View.VISIBLE);
                }
                else{
                    rajshahi.setVisibility(View.GONE);
                }
            }
        });



        final LinearLayout barisal = (LinearLayout) findViewById(R.id.barisal_districts);

        for(int i = 0; i<barisalDivisionDistrictConstant.size() ; i++){
            barisalDivisionDistricts = new CheckBox(this);
            barisalDivisionDistricts.setText(barisalDivisionDistrict.get(i));
            barisalDivisionDistricts.setId(Integer.parseInt(barisalDivisionDistrictConstant.get(i)));
            final int index = i;
            barisalDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(barisalDivisionDistrictConstant.get(index)));

                    if(checkBox.isChecked()){
                        districtSelectedArray.add(barisalDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(barisalDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ barisalDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            barisal.addView(barisalDivisionDistricts);
        }

        barisalDivision = (CheckBox) findViewById(R.id.barisal);
        barisalDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(barisal.getVisibility()==View.GONE){
                    barisal.setVisibility(View.VISIBLE);
                }
                else{
                    barisal.setVisibility(View.GONE);
                }
            }
        });


        final LinearLayout sylhet = (LinearLayout) findViewById(R.id.sylhet_districts);

        for(int i = 0; i<sylhetDivisionDistrictConstant.size() ; i++){
            sylhetDivisionDistricts = new CheckBox(this);
            sylhetDivisionDistricts.setText(sylhetDivisionDistrict.get(i));
            sylhetDivisionDistricts.setId(Integer.parseInt(sylhetDivisionDistrictConstant.get(i)));
            final int index = i;
            sylhetDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(sylhetDivisionDistrictConstant.get(index)));

                    if(checkBox.isChecked()){
                        districtSelectedArray.add(sylhetDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(sylhetDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ sylhetDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            sylhet.addView(sylhetDivisionDistricts);
        }

        sylhetDivision = (CheckBox) findViewById(R.id.sylhet);
        sylhetDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(sylhet.getVisibility()==View.GONE){
                    sylhet.setVisibility(View.VISIBLE);
                }
                else{
                    sylhet.setVisibility(View.GONE);
                }
            }
        });



        final LinearLayout rangpur = (LinearLayout) findViewById(R.id.rangpur_districts);

        for(int i = 0; i<rangpurDivisionDistrictConstant.size() ; i++){
            rangpurDivisionDistricts = new CheckBox(this);
            rangpurDivisionDistricts.setText(rangpurDivisionDistrict.get(i));
            rangpurDivisionDistricts.setId(Integer.parseInt(rangpurDivisionDistrictConstant.get(i)));
            final int index = i;
            rangpurDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(rangpurDivisionDistrictConstant.get(index)));

                    if(checkBox.isChecked()){
                        districtSelectedArray.add(rajshahiDivisionDistrictConstant.get(index));
                    }
                    else if(!checkBox.isChecked()){
                        districtSelectedArray.remove(rajshahiDivisionDistrictConstant.get(index));
                    }
                    Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ rangpurDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            rangpur.addView(rangpurDivisionDistricts);
        }

        rangpurDivision = (CheckBox) findViewById(R.id.rangpur);
        rangpurDivision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(rangpur.getVisibility()==View.GONE){
                    rangpur.setVisibility(View.VISIBLE);
                }
                else{
                    rangpur.setVisibility(View.GONE);
                }
            }
        });


        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String total = new StringBuilder().append("{")
                        .append("\"occupation\":")
                        .append("")
                        .append(occupationListString())
                        .append("")
                        .append(",")
                        .append("\"professional_group\":")
                        .append("")
                        .append(professonalGroupListString())
                        .append("")
                        .append(",")
                        .append("\"location\":")
                        .append(locationString())
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(currentStep)
                        .append("}")
                        .toString();
                Log.i("result",total);
                new RegistrationChoiceSelectionSecondPage.SendChoiceInfo().execute(total);

            }
        });
    }

    String occupationListString(){
        StringBuilder occupationdata = new StringBuilder();
        for(int i = 0 ; i < occupationSelectedArray.size();i++){
            if(i != occupationSelectedArray.size()-1){
                occupationdata.append("\"")
                        .append(occupationSelectedArray.get(i))
                        .append("\"")
                        .append(",");
            }
            else{
                occupationdata.append("\"")
                        .append(occupationSelectedArray.get(i))
                        .append("\"");
            }

        }

        String occupation = new StringBuilder().append("[")
                .append(occupationdata.toString())
                .append("]")
                .toString();
        return occupation;
    }


    String professonalGroupListString(){
        StringBuilder professonalGroupData = new StringBuilder();
        for(int i = 0 ; i < professonalGroupSelectedArray.size();i++){
            if(i != professonalGroupSelectedArray.size()-1){
                professonalGroupData.append("\"")
                        .append(professonalGroupSelectedArray.get(i))
                        .append("\"")
                        .append(",");
            }
            else{
                professonalGroupData.append("\"")
                        .append(professonalGroupSelectedArray.get(i))
                        .append("\"");
            }

        }

        String professonalGroup = new StringBuilder().append("[")
                .append(professonalGroupData.toString())
                .append("]")
                .toString();
        return professonalGroup;
    }


    String locationString(){
        StringBuilder locationData = new StringBuilder();
        for(int i = 0 ; i < districtSelectedArray.size();i++){
            if(i != districtSelectedArray.size()-1){
                locationData.append("\"")
                        .append(districtSelectedArray.get(i))
                        .append("\"")
                        .append(",");
            }

            else{
                locationData.append("\"")
                        .append(districtSelectedArray.get(i))
                        .append("\"");
            }

        }

        String professonalGroup = new StringBuilder().append("[")
                .append(locationData.toString())
                .append("]")
                .toString();
        return professonalGroup;
    }



    class SendChoiceInfo extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(RegistrationChoiceSelectionSecondPage.this);
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            if ( !progress.isShowing() ){

            }
               // progress.show();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s == null){
                progress.cancel();
                Utils.ShowAlert(RegistrationChoiceSelectionSecondPage.this, getString(R.string.no_internet_connection));
            }
            else{
                try{
                    progress.cancel();
                    JSONObject jsonObject=new JSONObject(s);
                    Log.e("Response",s);
                    if(jsonObject.has("errors"))
                    {
                        jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationChoiceSelectionSecondPage.this, "error", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new RegistrationChoiceSelectionSecondPage.FetchConstant().execute();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings){
            SharePref sharePref = new SharePref(RegistrationChoiceSelectionSecondPage.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,strings[0]);
            Request request = new Request.Builder()
                    .url(Utils.SEND_INFO)
                    .addHeader("Authorization", "Token token=" + token)
                    .post(body)
                    .build();
            Response response = null;
            String responseString = null;
            try {
                response = client.newCall(request).execute();
                responseString = response.body().string();

            }catch (IOException e){
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }


    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s == null){
                Utils.ShowAlert(RegistrationChoiceSelectionSecondPage.this, getString(R.string.no_internet_connection));
            }
            else{
                Intent signupIntent;
                signupIntent = new Intent(RegistrationChoiceSelectionSecondPage.this, RegistrationChoiceSelectionThirdPage.class);
                signupIntent.putExtra("constants",s);
                startActivity(signupIntent);
            }
        }

        @Override
        protected String doInBackground(String... parameters){
            Login.currentMobileSignupStep+=1;
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep )
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e){
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
    }
}
