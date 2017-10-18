package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nascenia.biyeta.R;

public class RegistrationPersonalInformation extends AppCompatActivity {

    LinearLayout maritalStatus,educationalStatus,professonalalStatus,religiousStatus,rojaStatus,disableStatus,smokeStatus,professionalGroupStatus;
    TextView marriageTV,educationTV,professonTV,religionTV,rojaTV,disableTV,smokeTV,professionalGroupTV;
    Button next;
    ImageView back;

    public static String marriageArray[] = {"অবিবাহিত","বিপত্মীক","তালাকপ্রাপ্ত","বিবাহিত"};
    public static String educationArray[] = {"মাধ্যমিক", "উচ্চমাধ্যমিক পড়ছি", "উচ্চমাধ্যমিক/ডিপ্লোমা","বাচেলর পড়ছি", "বাচেলর", "মাস্টার্স পড়ছি","মাস্টার্স", "ডক্টরেট পড়ছি","ডক্টরেট"};
    public static String professionArray[] = {"ব্যবসায়ী","অবসরপ্রাপ্ত","শিক্ষার্থী", "পেশায় জড়িত নাই", "সরকারী চাকরিজীবী","বেসরকারী চাকরিজীবী"};
    public static String religionArray[] = {"৫ ওয়াক্ত","৫ ওয়াক্তের কম","শুধু জুম্মা","শুধু ঈদের নামায","কখনো না"};
    public static String rojaArray[] = {"পুরো রমজান","রমজানে ৩০ টির কম","কখনো না"};
    public static String disableArray[] = {"কোন প্রতিবন্ধকতা নেই","সামান্য শারীরিক প্রতিবন্ধকতা","প্রতিবন্ধকতা","অটিজম","মানসিক প্রতিবন্ধকতা"};
    public static String smokeArray[] = {"অধুমপায়ী","মাঝে মাঝে","প্রায়ই"};
    public static String professonalGroupArray[] = {"ডাক্তার","ইঞ্জিনিয়ার","আইনজীবি","মেরিনার","কনসালটেন্ট","চার্টার একাউন্টেন্ট","সাংবাদিক","পুলিশ","শিক্ষক","পাইলট","ব্যাংকার","ডিফেন্স : আর্মি/ নেভী/ এয়ারফোর্স","অন্যান্য","প্রফেশনাল গ্রুপ প্রযোজ্য নয়"};

    public static int selectedPopUp = 0;

    public static int marriage = -1;
    public static int education = -1;
    public static int profession = -1;
    public static int religion = -1;
    public static int roja = -1;
    public static int disable = -1;
    public static int smoke = -1;
    public static int professonalGroup = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_personal_information);

        maritalStatus = (LinearLayout) findViewById(R.id.meritalStatus);
        educationalStatus = (LinearLayout) findViewById(R.id.educationalStatus);
        professonalalStatus = (LinearLayout) findViewById(R.id.professonalalStatus);
        religiousStatus = (LinearLayout) findViewById(R.id.religiousStatus);
        rojaStatus = (LinearLayout) findViewById(R.id.rojaStatus);
        disableStatus = (LinearLayout) findViewById(R.id.disableStatus);
        smokeStatus = (LinearLayout) findViewById(R.id.smokeStatus);
        professionalGroupStatus = (LinearLayout) findViewById(R.id.professonalGroupStatus);

        marriageTV = (TextView) findViewById(R.id.merital_text_view);
        educationTV = (TextView) findViewById(R.id.education_text_view);
        professonTV = (TextView) findViewById(R.id.profession_text_view);
        religionTV = (TextView) findViewById(R.id.religion_text_view);
        rojaTV = (TextView) findViewById(R.id.roja_text_view);
        disableTV = (TextView) findViewById(R.id.disable_text_view);
        smokeTV = (TextView) findViewById(R.id.smoke_text_view);
        professionalGroupTV = (TextView) findViewById(R.id.profession_group_text_view);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationPersonalInformation.this, RegistrationFamilyInfoFirstPage.class ));

            }
        });


        maritalStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 1;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));

                    }
                }
        );

        educationalStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 2;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );

        professonalalStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 3;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );

        religiousStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 4;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );

        rojaStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 5;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );

        disableStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 6;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );

        smokeStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 7;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );
        professionalGroupStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 8;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class ));
                    }
                }
        );


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(RegistrationPersonalInformation.selectedPopUp == 1 ){
            if(marriage>0){
                marriageTV.setText(marriageArray[marriage-1]);
            }

        }else if(RegistrationPersonalInformation.selectedPopUp == 2){
            if(education>0)
            {
                educationTV.setText(educationArray[education-1]);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 3){
            if(profession>0)
            {
                professonTV.setText(professionArray[profession-1]);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 4){
            if(religion>0)
            {
                religionTV.setText(religionArray[religion-1]);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 5){
            if(roja>0)
            {
                rojaTV.setText(rojaArray[roja-1]);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 6){
            if(disable>0)
            {
                disableTV.setText(disableArray[disable-1]);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 7){
            if(smoke>0)
            {
                smokeTV.setText(smokeArray[smoke-1]);
            }
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 8){
            if(professonalGroup>0)
            {
                professionalGroupTV.setText(professonalGroupArray[professonalGroup-1]);
            }
        }

    }
}
