package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nascenia.biyeta.R;

public class RegistrationFamilyInfoFirstPage extends AppCompatActivity {

    public static String professionArray[] = {"ব্যবসায়ী","অবসরপ্রাপ্ত","শিক্ষার্থী", "পেশায় জড়িত নাই", "সরকারী চাকরিজীবী","বেসরকারী চাকরিজীবী"};
    public static String professonalGroupArray[] = {"ডাক্তার","ইঞ্জিনিয়ার","আইনজীবি","মেরিনার","কনসালটেন্ট","চার্টার একাউন্টেন্ট","সাংবাদিক","পুলিশ","শিক্ষক","পাইলট","ব্যাংকার","ডিফেন্স : আর্মি/ নেভী/ এয়ারফোর্স","অন্যান্য","প্রফেশনাল গ্রুপ প্রযোজ্য নয়"};

    LinearLayout professionFatherStatus, professionMotherStatus, professionalGroupFatherStatus, professionalGroupMotherStatus;

    TextView professionFatherTV,professionMotherTV,professionalGroupFatherTV,professionalGroupMotherTV;

    Button next;

    ImageView back;


    public static int professionFather = -1;
    public static int professionMother = -1;
    public static int professionalGroupFather = -1;
    public static int professionalGroupMother = -1;

    public static int selectedPopUp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_family_info_first_page);
        professionFatherStatus = (LinearLayout)findViewById(R.id.professonalalStatusFather);
        professionMotherStatus = (LinearLayout)findViewById(R.id.professonalalStatusMother);
        professionalGroupFatherStatus = (LinearLayout) findViewById(R.id.professonalGroupStatusFather);
        professionalGroupMotherStatus = (LinearLayout) findViewById(R.id.professonalGroupStatusMother);

        professionFatherTV = (TextView) findViewById(R.id.profession_text_view_father);
        professionalGroupFatherTV = (TextView) findViewById(R.id.profession_group_text_view_father);


        professionMotherTV = (TextView) findViewById(R.id.profession_text_view_mother);
        professionalGroupMotherTV = (TextView) findViewById(R.id.profession_group_text_view_mother);

        professionFatherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 1;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class ));
            }
        });

        professionalGroupFatherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 2;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class ));
            }
        });

        professionMotherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 3;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class ));
            }
        });
        professionalGroupMotherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 4;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class ));
            }
        });
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, ImageChoose.class ));

            }
        });

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(RegistrationFamilyInfoFirstPage.selectedPopUp == 1 ){
            if(professionFather>0)
                professionFatherTV.setText(professionArray[professionFather-1]);
        }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 2){
            if(professionalGroupFather>0)
            professionalGroupFatherTV.setText(professonalGroupArray[professionalGroupFather-1]);
        }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 3){
            if(professionMother>0)
            professionMotherTV.setText(professionArray[professionMother-1]);
        }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 4){
            if(professionalGroupMother>0)
            professionalGroupMotherTV.setText(professonalGroupArray[professionalGroupMother-1]);
        }
    }
}
