package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nascenia.biyeta.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.bendik.simplerangeview.SimpleRangeView;

public class RegistrationChoiceSelectionThirdPage extends AppCompatActivity {

    Button next;

    Map<Integer, String> prayerMale = new HashMap<Integer, String>();

    Map<Integer, String> prayerFemale = new HashMap<Integer, String>();

    Map<Integer, String> fasting = new HashMap<Integer, String>();

    Map<Integer, String> hijab = new HashMap<Integer, String>();

    public static int castReligionChoice = 0;

    public static String religionValue, castValue, otherCast, otherReligion;
    public static String jobAfterMarriage, maritalStatus;

    public static ArrayList<String> jobArray = new ArrayList<String>();
    public static ArrayList<String> jobConstant = new ArrayList<String>();

    public static ArrayList<String> marriageArray = new ArrayList<String>();
    public static ArrayList<String> marriageArrayConstant = new ArrayList<String>();

    public static ArrayList<String> religionArray = new ArrayList<String>();
    public static ArrayList<String> religionArrayConstant = new ArrayList<String>();

    LinearLayout jobLayout, maritalStatusLayout, religionLayout, mCastLayout, hCastLayout, cCastLayout, namajLayout, rojaLayout, hijabLayout;
    public static LinearLayout onlyForMuslimLayout;


    public static TextView jobLabel, maritalStatusLabel, religionLabel, castLabel, namajLabel, rojaLabel, hijabLabel;
    public static TextView castReligionText;

    SimpleRangeView rangeView_namaj, rangeView_roja, rangeView_hijab;
    public static int job = -1;
    public static int marriage = -1;
    public static int religion = -1;
    public static int selectedPopUp = 0;
    int currentStep;
    String constant;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        constant = intent.getStringExtra("constant");

        currentStep = 6;

        try{
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject jobAfterMarriageObject = jsonObject.getJSONObject("job_after_marriage");
            JSONObject meritalStatusObject = jsonObject.getJSONObject("marital_status_constant");

            JSONObject prayerMaleObject = jsonObject.getJSONObject("prayer_options_male");
            JSONObject prayerFemaleObject = jsonObject.getJSONObject("prayer_options_female");
            JSONObject fastingObject = jsonObject.getJSONObject("fasting");
            JSONObject hijabObject = jsonObject.getJSONObject("hijab_options");



            for(int i=0;i<jobAfterMarriageObject.length();i++)
            {
                jobConstant.add(jobAfterMarriageObject.names().getString(i));
                jobArray.add((String) jobAfterMarriageObject.get(jobAfterMarriageObject.names().getString(i)));
            }

            for(int i=0;i<meritalStatusObject.length();i++)
            {
                marriageArrayConstant.add(meritalStatusObject.names().getString(i));
                marriageArray.add((String) meritalStatusObject.get(meritalStatusObject.names().getString(i)));
            }

            for(int i=0;i<prayerMaleObject.length();i++)
            {
                prayerMale.put(Integer.parseInt(prayerMaleObject.names().getString(i)),(String) prayerMaleObject.get(prayerMaleObject.names().getString(i)));
            }

            for(int i=0;i<prayerFemaleObject.length();i++)
            {
                prayerFemale.put(Integer.parseInt(prayerFemaleObject.names().getString(i)),(String) prayerFemaleObject.get(prayerFemaleObject.names().getString(i)));
            }

            for(int i=0;i<fastingObject.length();i++)
            {
                fasting.put(Integer.parseInt(fastingObject.names().getString(i)),(String) fastingObject.get(fastingObject.names().getString(i)));
            }

            for(int i=0;i<hijabObject.length();i++)
            {
                hijab.put(Integer.parseInt(hijabObject.names().getString(i)),(String) hijabObject.get(hijabObject.names().getString(i)));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_registration_choice_selection_third_page);
        rangeView_namaj = (SimpleRangeView) findViewById(R.id.namaj);
        rangeView_roja = (SimpleRangeView) findViewById(R.id.roja);
        rangeView_hijab = (SimpleRangeView) findViewById(R.id.hajab);

        onlyForMuslimLayout = (LinearLayout) findViewById(R.id.only_for_muslim);
        onlyForMuslimLayout.setVisibility(View.GONE);

        castReligionText = (TextView) findViewById(R.id.religion_cast_text_view);

        jobLayout = (LinearLayout) findViewById(R.id.job);

        maritalStatusLayout = (LinearLayout) findViewById(R.id.marital);

        religionLayout = (LinearLayout) findViewById(R.id.cast_religion);

        namajLayout = (LinearLayout) findViewById(R.id.namaj_layout);

        rojaLayout = (LinearLayout) findViewById(R.id.roja_layout);

        hijabLayout = (LinearLayout) findViewById(R.id.hijab_layout);

        jobLabel = (TextView) findViewById(R.id.job_label);
        maritalStatusLabel = (TextView) findViewById(R.id.marital_status_label);
        religionLabel = (TextView) findViewById(R.id.religion_cast_label);
        namajLabel = (TextView) findViewById(R.id.namaj_label);
        rojaLabel = (TextView) findViewById(R.id.roja_label);
        hijabLabel = (TextView) findViewById(R.id.hijab_label);


        jobLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedPopUp = 1;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class ));
            }
        });
        maritalStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selectedPopUp = 2;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class ));
            }
        });
        religionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selectedPopUp = 3;
                castReligionChoice = 1;
                Intent intent = new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpCastReligion.class );
                intent.putExtra("constants",constant);
                startActivity(intent);
            }
        });

        rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setLabelColor(Color.TRANSPARENT);

        rangeView_namaj.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener(){
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i){
                rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setLabelColor(Color.TRANSPARENT);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i){
                rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setLabelColor(Color.TRANSPARENT);
            }
        });

        rangeView_namaj.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener(){
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {

                if(Login.gender=="female")
                    return prayerMale.get(i);
                else{
                    return prayerFemale.get(i);
                }
            }
        });
        rangeView_namaj.setStart(0);
        rangeView_namaj.setEnd(3);


        rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_roja.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_roja.setLabelColor(Color.TRANSPARENT);

        rangeView_roja.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_roja.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener(){
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i){
                rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_roja.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_roja.setLabelColor(Color.TRANSPARENT);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i){
                rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_roja.setLabelColor(Color.TRANSPARENT);
            }
        });

        rangeView_roja.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return fasting.get(i);
            }
        });
        rangeView_roja.setStart(0);
        rangeView_roja.setEnd(2);

        rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setLabelColor(Color.TRANSPARENT);

        rangeView_hijab.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setLabelColor(Color.TRANSPARENT);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i){
                rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setLabelColor(Color.TRANSPARENT);
            }
        });

        rangeView_hijab.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener(){
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return hijab.get(i);
            }
        });
        rangeView_hijab.setStart(0);
        rangeView_hijab.setEnd(2);



        if (Login.gender == "female"){
            jobLabel.setVisibility(View.GONE);
            jobLayout.setVisibility(View.GONE);

            maritalStatusLabel.setText("পাত্রের বৈবাহিক অবস্থা");

            religionLabel.setText("পাত্রের ধর্ম ও বর্ণ");

            hijabLabel.setVisibility(View.GONE);

            hijabLayout.setVisibility(View.GONE);
        }

        else if (Login.gender == "male"){

            maritalStatusLabel.setText("পাত্রীর বৈবাহিক অবস্থা");

            religionLabel.setText("পাত্রীর ধর্ম ও বর্ণ");
        }


        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String forFemale = new StringBuilder().append("{")
                        .append("\"job_permission\":")
                        .append("\"")
                       // .append()
                        .append("\"")
                        .append(",")
                        .append("\"marital_status\":")
                        .append("[")
                        .append("\"")
                       // .append()
                        .append("\"")
                        .append("]")
                        .append(",")
                        .append("\"religion\":")
                        .append("\"")
                        //.append()
                        .append("\"")
                        .append(",")
                        .append("\"cast\":")
                        .append("[")
                        .append("\"")
                       // .append()
                        .append("\"")
                        .append("]")
                        .append(",")
                        .append("\"other_cast\":")
                        .append("\"")
                       // .append()
                        .append("\"")
                        .append(",")
                        .append("\"other_religion\":")
                        .append("\"")
                       // .append()
                        .append("\"")
                        .append(",")
                        .append("\"prayer\":")
                        .append("\"")
                        //.append()
                        .append(";")
                       // .append()
                        .append("\"")
                        .append(",")
                        .append("\"fast\":")
                        .append("\"")
                        //.append()
                        .append(";")
                       // .append()
                        .append("\"")
                        .append(",")
                        .append("\"hijab\":")
                        .append("\"")
                        //.append()
                        .append(";")
                       // .append()
                        .append("\"")
                        .append(",")
                        .append("\"house_in_dhaka\":")
                        .append("\"")
                        .append("")
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append("\"")
                        .append(6)
                        .append("\"")
                        .toString();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(selectedPopUp == 1){
            jobAfterMarriage = jobConstant.get(job - 1);
            jobLabel.setText(jobArray.get(job-1));
        }if(selectedPopUp == 2){
            maritalStatus = marriageArrayConstant.get(marriage - 1);
            maritalStatusLabel.setText(marriageArray.get(marriage - 1));
        }
    }
}
