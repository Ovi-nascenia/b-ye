package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;

public class RegistrationPersonalInformation extends AppCompatActivity {

    String maritalStatusValue = "", degreeValue = "", subjectValue = "", institutionValue,
            occupationValue = "", professionalGroupValue = "",
            designationValue, occupationInstitutionValue, religionValue = "",
            rojaValue = "", disableValue = "", smokeValue = "", hijabValue = "", houseValue = "",
            heightValue = "", weightValue = "", skinValue = "", bloodValue = "",
            feetValue = "", inchValue = "", childValue = "", stayStatusValue = "";
    EditText subjectText, institutionText, designationText, occupationInstitutionText, disableDescEdit;
    LinearLayout maritalStatus, educationalStatus, professonalalStatus, religiousStatus,
            rojaStatus, disableStatus, smokeStatus, professionalGroupStatus, houseLinearLayout, hijabStatus,
            heightStatus, skinColorStatus, weightStatus, bloodGroupStatus, childNumber, stayStatus;
    TextView marriageTV, educationTV, professonTV, religionTV, rojaTV, disableTV, smokeTV,
            professionalGroupTV, houseTV, hijabTV, heightTV, weightTV, bloodTV, skinTV, limitTV, childTV, stayTV;
    Button next;
    ImageView back;

    String constant;
    JSONObject marriageObject, educationObject, occupationObject, professionalGroupObject,
            religionObject, rojaObject, hijabObject, disableObject, smokeObject, houseObject, bloodObject, heightObject, weightObject, skinColorObject;


    public static ArrayList<String> professonalGroupArray = new ArrayList<String>();
    public static String[] professonalGroupName = new String[professonalGroupArray.size()];
    public static ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    public static String[] professonalGroupConstantValue = new String[professonalGroupArray.size()];

    public static ArrayList<String> occupationArray = new ArrayList<String>();
    public static String[] occupationName = new String[occupationArray.size()];
    public static ArrayList<String> occupationConstant = new ArrayList<String>();
    public static String[] occupationConstantValue = new String[occupationArray.size()];

    public static ArrayList<String> marriageArray = new ArrayList<String>();
    public static String[] maritalStatusName = new String[marriageArray.size()];
    public static ArrayList<String> maritalStatusConstant = new ArrayList<String>();
    public static String[] maritalStatusConstantValue = new String[marriageArray.size()];

    public static ArrayList<String> religionArray = new ArrayList<String>();
    public static String[] religionName = new String[religionArray.size()];
    public static ArrayList<String> religionConstant = new ArrayList<String>();
    public static String[] religionConstantValue = new String[religionArray.size()];

    public static ArrayList<String> educationArray = new ArrayList<String>();
    public static String[] educationName = new String[educationArray.size()];
    public static ArrayList<String> educationConstant = new ArrayList<String>();
    public static String[] educationConstantValue = new String[educationArray.size()];

    public static ArrayList<String> rojaArray = new ArrayList<String>();
    public static String[] rojaName = new String[rojaArray.size()];
    public static ArrayList<String> rojaConstant = new ArrayList<String>();
    public static String[] rojaConstantValue = new String[rojaArray.size()];

    public static ArrayList<String> hijabArray = new ArrayList<String>();
    public static String[] hijabName = new String[hijabArray.size()];
    public static ArrayList<String> hijabConstant = new ArrayList<String>();
    public static String[] hijabConstantValue = new String[hijabArray.size()];

    public static ArrayList<String> disableArray = new ArrayList<String>();
    public static String[] disableName = new String[disableArray.size()];
    public static ArrayList<String> disableConstant = new ArrayList<String>();
    public static String[] disableConstantValue = new String[disableArray.size()];

    public static ArrayList<String> smokeArray = new ArrayList<String>();
    public static String[] smokeName = new String[smokeArray.size()];
    public static ArrayList<String> smokeConstant = new ArrayList<String>();
    public static String[] smokeConstantValue = new String[smokeArray.size()];

    public static ArrayList<String> houseArray = new ArrayList<String>();
    public static String[] houseName = new String[houseArray.size()];
    public static ArrayList<String> houseConstant = new ArrayList<String>();
    public static String[] houseConstantValue = new String[houseArray.size()];

    public static ArrayList<String> heightArray = new ArrayList<String>();
    public static String[] heightName = new String[heightArray.size()];
    public static ArrayList<String> heightConstant = new ArrayList<String>();
    public static String[] heightConstantValue = new String[heightArray.size()];

    public static ArrayList<String> weightArray = new ArrayList<String>();
    public static String[] weightName = new String[weightArray.size()];
    public static ArrayList<String> weightConstant = new ArrayList<String>();
    public static String[] weightConstantValue = new String[weightArray.size()];

    public static ArrayList<String> skinColorArray = new ArrayList<String>();
    public static String[] skinColorName = new String[skinColorArray.size()];
    public static ArrayList<String> skinColorConstant = new ArrayList<String>();
    public static String[] skinColorConstantValue = new String[skinColorArray.size()];

    public static ArrayList<String> bloodGroupArray = new ArrayList<String>();
    public static String[] bloodGroupName = new String[bloodGroupArray.size()];
    public static ArrayList<String> bloodGroupConstant = new ArrayList<String>();
    public static String[] bloodGroupConstantValue = new String[bloodGroupArray.size()];

    public static ArrayList<String> childArray = new ArrayList<String>();
    public static String[] childName = new String[childArray.size()];
    public static ArrayList<String> childConstant = new ArrayList<String>();
    public static String[] childConstantValue = new String[childArray.size()];

    public static ArrayList<String> stayStausArray = new ArrayList<String>();
    public static String[] stayStausArrayName = new String[stayStausArray.size()];
    public static ArrayList<String> stayStausArrayConstant = new ArrayList<String>();
    public static String[] stayStausArrayConstantValue = new String[stayStausArray.size()];

    // public static String marriageArray[] = {"অবিবাহিত","বিপত্মীক","তালাকপ্রাপ্ত","বিবাহিত"};
    //public static String educationArray[] = {"মাধ্যমিক", "উচ্চমাধ্যমিক পড়ছি", "উচ্চমাধ্যমিক/ডিপ্লোমা","বাচেলর পড়ছি", "বাচেলর", "মাস্টার্স পড়ছি","মাস্টার্স", "ডক্টরেট পড়ছি","ডক্টরেট"};
    //public static String professionArray[] = {"ব্যবসায়ী","অবসরপ্রাপ্ত","শিক্ষার্থী", "পেশায় জড়িত নাই", "সরকারী চাকরিজীবী","বেসরকারী চাকরিজীবী"};
    // public static String religionArray[] = {"৫ ওয়াক্ত","৫ ওয়াক্তের কম","শুধু জুম্মা","শুধু ঈদের নামায","কখনো না"};
    // public static String rojaArray[] = {"পুরো রমজান","রমজানে ৩০ টির কম","কখনো না"};
//    public static String disableArray[] = {"কোন প্রতিবন্ধকতা নেই","সামান্য শারীরিক প্রতিবন্ধকতা","প্রতিবন্ধকতা","অটিজম","মানসিক প্রতিবন্ধকতা"};
//    public static String smokeArray[] = {"অধুমপায়ী","মাঝে মাঝে","প্রায়ই"};
    //public static String professonalGroupArray[] = {"ডাক্তার","ইঞ্জিনিয়ার","আইনজীবি","মেরিনার","কনসালটেন্ট","চার্টার একাউন্টেন্ট","সাংবাদিক","পুলিশ","শিক্ষক","পাইলট","ব্যাংকার","ডিফেন্স : আর্মি/ নেভী/ এয়ারফোর্স","অন্যান্য","প্রফেশনাল গ্রুপ প্রযোজ্য নয়"};

    public static int selectedPopUp = 0;

    public static int marriage = -1;
    public static int education = -1;
    public static int profession = -1;
    public static int religion = -1;
    public static int roja = -1;
    public static int disable = -1;
    public static int smoke = -1;
    public static int professonalGroup = -1;
    public static int house = -1;
    public static int hijab = -1;
    public static int blood_group = -1;
    public static int height = -1;
    public static int weight = -1;
    public static int skin_color = -1;
    public static int child_number = -1;
    public static int stay_status = -1;

    private SharePref sharePref;

    TextView maritalStatusLabel, educationalStatusLabel, professionStatusLabel, religionStatusLabel,
            rojaStatuLabel, smokeStatusLabel, houseStatusLabel, hijabStatuLabel, heightLabel, weightLabel,
            skinLabel, childLabel, stayLabel;

    ProgressDialog progress;

    OkHttpClient client;
    private String loginUserReligion;
    private boolean isSignUp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_personal_information);

        progress = new ProgressDialog(RegistrationPersonalInformation.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        client = new OkHttpClient();
        sharePref = new SharePref(RegistrationPersonalInformation.this);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        isSignUp = getIntent().getBooleanExtra("isSignUp", false);

        try {
            JSONObject jsonObject = new JSONObject(constant);

            if (sharePref.get_data("gender").equalsIgnoreCase("female")) {
                marriageObject = jsonObject.getJSONObject("marital_status_constant_for_female");
            } else if (sharePref.get_data("gender").equalsIgnoreCase("male")) {
                marriageObject = jsonObject.getJSONObject("marital_status_constant_for_male");
            }
            educationObject = jsonObject.getJSONObject("education_constant");
            occupationObject = jsonObject.getJSONObject("occupation_constant");
            professionalGroupObject = jsonObject.getJSONObject("professional_group_constant");
            //religionObject = jsonObject.getJSONObject("prayer_options_female");
            religionObject = jsonObject.getJSONObject("prayer_options");
            rojaObject = jsonObject.getJSONObject("fasting");
            hijabObject = jsonObject.getJSONObject("hijab_options");
            disableObject = jsonObject.getJSONObject("physical_disability_options");
            smokeObject = jsonObject.getJSONObject("smoking_options");
            houseObject = jsonObject.getJSONObject("house_options");

            ////////////////////////////////
//            heightObject = jsonObject.getJSONObject("height_options");
            heightObject = new JSONObject("{\"3\":\"ব্যবসায়ী\",\"5\":\"অবসরপ্রাপ্ত\","
                    + "\"6\":\"শিক্ষার্থী\",\"7\":\"পেশায় জড়িত নাই\",\"1\":\"সরকারি চাকরিজীবী\","
                    + "\"2\":\"বেসরকারি চাকরিজীবী\"}");
//            weightObject = jsonObject.getJSONObject("weight_options");
//            weightObject = new JSONObject("{\"30\":\"30\",\"31\":\"31\","
//                    + "\"6\":\"শিক্ষার্থী\",\"7\":\"পেশায় জড়িত নাই\",\"1\":\"সরকারি চাকরিজীবী\","
//                    + "\"2\":\"বেসরকারি চাকরিজীবী\"}");
            skinColorObject = jsonObject.getJSONObject("skin_color_options");
//            skinColorObject = new JSONObject(" {\n"
//                    + "\"0\": \"শ্যামলা\",\n"
//                    + "\"1\": \"উজ্জ্বল শ্যামলা\",\n"
//                    + "\"2\": \"ফর্সা\",\n"
//                    + "\"3\": \"অনেক ফর্সা\"\n"
//                    + "}");
            bloodObject = jsonObject.getJSONObject("blood_group_options");
//            bloodObject = new JSONObject("{\n"
//                    + "\"a_positive\": \"এ+\",\n"
//                    + "\"a_negative\": \"এ-\",\n"
//                    + "\"b_positive\": \"বি+\",\n"
//                    + "\"b_negative\": \"বি-\",\n"
//                    + "\"ab_positive\": \"এবি+\",\n"
//                    + "\"ab_negative\": \"এবি-\",\n"
//                    + "\"o_positive\": \"ও+\",\n"
//                    + "\"o_negative\": \"ও-\"\n"
//                    + "}");
            ////////////////////////////////

            loginUserReligion = (jsonObject.has("login_user_religion")?jsonObject.getString("login_user_religion"):"");
//            Log.i("login_user_religion", loginUserReligion);


            for (int i = 0; i < marriageObject.length(); i++) {
                maritalStatusConstant.add(marriageObject.names().getString(i));
                marriageArray.add((String) marriageObject.get(marriageObject.names().getString(i)));
            }

            maritalStatusName = marriageArray.toArray(maritalStatusName);

            for (int i = 0; i < educationObject.length(); i++) {
                educationConstant.add(educationObject.names().getString(i));
                educationArray.add((String) educationObject.get(educationObject.names().getString(i)));
            }

            educationName = educationArray.toArray(educationName);

            for (int i = 0; i < occupationObject.length(); i++) {
                occupationConstant.add(occupationObject.names().getString(i));
                occupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            occupationName = occupationArray.toArray(occupationName);

            for (int i = 0; i < professionalGroupObject.length(); i++) {
                professonalGroupConstant.add(professionalGroupObject.names().getString(i));
                professonalGroupArray.add((String) professionalGroupObject.get(professionalGroupObject.names().getString(i)));
            }

            professonalGroupName = professonalGroupArray.toArray(professonalGroupName);

            for (int i = 0; i < religionObject.length(); i++) {
                religionConstant.add(religionObject.names().getString(i));
                religionArray.add((String) religionObject.get(religionObject.names().getString(i)));
            }

            religionName = religionArray.toArray(religionName);

            for (int i = 0; i < rojaObject.length(); i++) {
                rojaConstant.add(rojaObject.names().getString(i));
                rojaArray.add((String) rojaObject.get(rojaObject.names().getString(i)));
            }

            rojaName = rojaArray.toArray(rojaName);

            for (int i = 0; i < hijabObject.length(); i++) {
                hijabConstant.add(hijabObject.names().getString(i));
                hijabArray.add((String) hijabObject.get(hijabObject.names().getString(i)));
            }

            hijabName = hijabArray.toArray(hijabName);

            for (int i = 0; i < disableObject.length(); i++) {
                disableConstant.add(disableObject.names().getString(i));
                disableArray.add((String) disableObject.get(disableObject.names().getString(i)));
            }

            disableName = disableArray.toArray(disableName);

            for (int i = 0; i < smokeObject.length(); i++) {
                smokeConstant.add(smokeObject.names().getString(i));
                smokeArray.add((String) smokeObject.get(smokeObject.names().getString(i)));
            }

            smokeName = smokeArray.toArray(smokeName);

            for (int i = 0; i < houseObject.length(); i++) {
                houseConstant.add(houseObject.names().getString(i));
                houseArray.add((String) houseObject.get(houseObject.names().getString(i)));
            }

            houseName = houseArray.toArray(houseName);

            ///////////////////////////////////////////////////////////
            for (int i = 4; i < 8; i++) {           //height from 4ft to 7ft 11inch
//                heightConstant.add(heightObject.names().getString(i));
                for(int j = 0; j < 12; j++)
                    heightArray.add(Utils.convertEnglishDigittoBangla(i) + " ফিট " + Utils.convertEnglishDigittoBangla(j) +" ইঞ্চি");
            }

            heightName = heightArray.toArray(heightName);

            for (int i = 30; i <= 200; i++) {         //weight from 30kg to 200kg
                weightConstant.add(i+"");
                weightArray.add(Utils.convertEnglishDigittoBangla(i)+" কেজি ");
            }

            weightName = weightArray.toArray(weightName);

            for (int i = 0; i < bloodObject.length(); i++) {
                bloodGroupConstant.add(bloodObject.names().getString(i));
                bloodGroupArray.add((String) bloodObject.get(bloodObject.names().getString(i)));
            }

            bloodGroupName = bloodGroupArray.toArray(bloodGroupName);

            for (int i = 0; i < skinColorObject.length(); i++) {
                skinColorConstant.add(skinColorObject.names().getString(i));
                skinColorArray.add((String) skinColorObject.get(skinColorObject.names().getString(i)));
            }

            skinColorName = skinColorArray.toArray(skinColorName);

            for (int i = 0; i <= 20; i++) {         //child number from 0 to 20
                childConstant.add(i+"");
                childArray.add(Utils.convertEnglishDigittoBangla(i));
            }

            childName = childArray.toArray(childName);

            //child stay with mother yes/no
            stayStausArrayConstant.add("1");
            stayStausArrayConstant.add("0");
            stayStausArray.add("হ্যাঁ");
            stayStausArray.add("না");

            stayStausArrayName = stayStausArray.toArray(stayStausArrayName);
            /////////////////////////////////////////////////

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("gender",sharePref.get_data("gender"));
        subjectText = (EditText) findViewById(R.id.subject_text);
        institutionText = (EditText) findViewById(R.id.institution_text);
        designationText = (EditText) findViewById(R.id.designation_text);
        occupationInstitutionText = (EditText) findViewById(R.id.occupation_institution_text);
        disableDescEdit = (EditText) findViewById(R.id.disable_desc);
        limitTV = findViewById(R.id.limit_label);


        maritalStatusLabel = (TextView) findViewById(R.id.marital_status_label);
        educationalStatusLabel = (TextView) findViewById(R.id.educational_status_label);
        professionStatusLabel = (TextView) findViewById(R.id.profession_status_label);
        religionStatusLabel = (TextView) findViewById(R.id.religion_status_label);
        rojaStatuLabel = (TextView) findViewById(R.id.roja_status_label);
        hijabStatuLabel = (TextView) findViewById(R.id.hijab_status_label);
        smokeStatusLabel = (TextView) findViewById(R.id.smoke_status_label);
        houseStatusLabel = (TextView) findViewById(R.id.house_status_label);
        heightLabel = (TextView) findViewById(R.id.height);
        weightLabel = (TextView) findViewById(R.id.weight);
        skinLabel = (TextView) findViewById(R.id.skin_color);
//        childLabel = (TextView) findViewById(R.id.child_number_text_view);

        heightLabel.getParent().requestChildFocus(heightLabel, heightLabel);

        maritalStatus = (LinearLayout) findViewById(R.id.meritalStatus);
        educationalStatus = (LinearLayout) findViewById(R.id.educationalStatus);
        professonalalStatus = (LinearLayout) findViewById(R.id.professonalalStatus);
        religiousStatus = (LinearLayout) findViewById(R.id.religiousStatus);
        rojaStatus = (LinearLayout) findViewById(R.id.rojaStatus);
        hijabStatus = (LinearLayout) findViewById(R.id.hijabStatus);
        disableStatus = (LinearLayout) findViewById(R.id.disableStatus);
        smokeStatus = (LinearLayout) findViewById(R.id.smokeStatus);
        professionalGroupStatus = (LinearLayout) findViewById(R.id.professonalGroupStatus);
        houseLinearLayout = (LinearLayout) findViewById(R.id.house_linearLayout);
        childNumber = (LinearLayout) findViewById(R.id.childNumber);
        stayStatus = (LinearLayout) findViewById(R.id.stayStatus);
        ///////////////////////////////////////////////////////////////
        heightStatus = findViewById(R.id.ln_height);
        weightStatus = findViewById(R.id.ln_weight);
        skinColorStatus = findViewById(R.id.ln_skin_color);
        bloodGroupStatus = findViewById(R.id.ln_blood_group);
        ///////////////////////////////////////////////////////////////

        marriageTV = (TextView) findViewById(R.id.merital_text_view);
        educationTV = (TextView) findViewById(R.id.education_text_view);
        professonTV = (TextView) findViewById(R.id.profession_text_view);
        religionTV = (TextView) findViewById(R.id.religion_text_view);
        rojaTV = (TextView) findViewById(R.id.roja_text_view);
        hijabTV = (TextView) findViewById(R.id.hijab_text_view);
        disableTV = (TextView) findViewById(R.id.disable_text_view);
        smokeTV = (TextView) findViewById(R.id.smoke_text_view);
        professionalGroupTV = (TextView) findViewById(R.id.profession_group_text_view);
        houseTV = (TextView) findViewById(R.id.house_text_view);
        childTV = (TextView) findViewById(R.id.child_number_text_view);
        stayTV = (TextView) findViewById(R.id.stay_status_text_view);
        //////////////////////////////////////////////////////
        heightTV = findViewById(R.id.height_text_view);
        weightTV = findViewById(R.id.weight_text_view);
        bloodTV = findViewById(R.id.blood_group_text_view);
        skinTV = findViewById(R.id.skin_color_text_view);
        //////////////////////////////////////////////////////

        if (sharePref.get_data("gender").equalsIgnoreCase("female")) {
            smokeStatusLabel.setVisibility(View.GONE);
            smokeStatus.setVisibility(View.GONE);

            houseStatusLabel.setVisibility(View.GONE);
            houseLinearLayout.setVisibility(View.GONE);

            if(loginUserReligion != null && loginUserReligion.equalsIgnoreCase("muslim")){
                hijabStatuLabel.setVisibility(View.VISIBLE);
                hijabStatus.setVisibility(View.VISIBLE);
            }
        }

        if (loginUserReligion != null && !loginUserReligion.equalsIgnoreCase("muslim")) {
            religionStatusLabel.setVisibility(View.GONE);
            religiousStatus.setVisibility(View.GONE);

            rojaStatuLabel.setVisibility(View.GONE);
            rojaStatus.setVisibility(View.GONE);

        }


        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new Intent(RegistrationPersonalInformation.this, Login.class);
                finish();*/
                new GetPreviousStepFetchConstant().execute();
            }
        });

        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                designationValue = designationText.getText().toString();
                occupationInstitutionValue = occupationInstitutionText.getText().toString();
                institutionValue = institutionText.getText().toString();
                subjectValue = subjectText.getText().toString();

                Log.i("response",institutionValue+" kjfadsj");

                ///////////////////////////////////////////////
                if (heightTV.getText().toString().equalsIgnoreCase("উচ্চতা")) {
                    Toast.makeText(getBaseContext(), "আপনার উচ্চতা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    heightLabel.getParent().requestChildFocus(heightLabel, heightLabel);
                    return;
                }

                if (skinValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার গায়ের রং নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    skinLabel.getParent().requestChildFocus(skinLabel, skinLabel);
                    return;
                }

                if (weightTV.getText().toString().equalsIgnoreCase("ওজন")) {
                    Toast.makeText(getBaseContext(), "আপনার ওজন নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    weightLabel.getParent().requestChildFocus(weightLabel, weightLabel);
                    return;
                }


                ////////////////////////////////////////////////

                if (maritalStatusValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার বৈবাহিক অবস্থা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    maritalStatusLabel.getParent().requestChildFocus(maritalStatusLabel, maritalStatusLabel);
                    return;
                }

                if(childNumber.getVisibility() == View.VISIBLE && childTV.getText().toString().length()==0)
                {
                    Toast.makeText(getBaseContext(), "আপনার সন্তানের সংখ্যা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    childTV.getParent().requestChildFocus(childTV, childTV);
                    return;
                }

                if(stayStatus.getVisibility() == View.VISIBLE && stayTV.getText().toString().length()==0)
                {
                    Toast.makeText(getBaseContext(), "আপনার সন্তানের বর্তমান অবস্থান নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    stayTV.getParent().requestChildFocus(stayTV, stayTV);
                    return;
                }

                if (degreeValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার ডিগ্রীর নাম নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    educationalStatusLabel.getParent().requestChildFocus(educationalStatusLabel, educationalStatusLabel);
                    return;
                }

                if (subjectValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার শিক্ষার বিষয় লিখুন", Toast.LENGTH_LONG).show();
                    educationalStatusLabel.getParent().requestChildFocus(educationalStatusLabel, educationalStatusLabel);
                    return;
                }

                if (occupationValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার পেশা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    professionStatusLabel.getParent().requestChildFocus(professionStatusLabel, professionStatusLabel);
                    return;
                }

                if (professionalGroupValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার প্রফেশনাল গ্রুপ নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    professionStatusLabel.getParent().requestChildFocus(professionStatusLabel, professionStatusLabel);
                    return;
                }

                if (loginUserReligion.equalsIgnoreCase(Utils.MUSLIM_TAG) && religionValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার ধর্ম পালন নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    religionStatusLabel.getParent().requestChildFocus(religionStatusLabel, religionStatusLabel);
                    return;
                }

                if (loginUserReligion.equalsIgnoreCase(Utils.MUSLIM_TAG) && rojaValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনি রোজা রাখেন কিনা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    rojaStatuLabel.getParent().requestChildFocus(rojaStatuLabel, rojaStatuLabel);
                    return;
                }

                if(loginUserReligion.equalsIgnoreCase(Utils.MUSLIM_TAG) && sharePref.get_data("gender")
                        .equalsIgnoreCase("female") && hijabValue.isEmpty()){

                    Toast.makeText(getBaseContext(), "আপনি হিজাব করেন কিনা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    hijabStatuLabel.getParent().requestChildFocus( hijabStatuLabel,  hijabStatuLabel);
                    return;
                }

                if (sharePref.get_data("gender").equalsIgnoreCase("male") && houseValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার আবাসস্থল আছে কিনা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    houseStatusLabel.getParent().requestChildFocus(houseStatusLabel, houseStatusLabel);
                    return;
                }
                if (disableValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার কোন প্রতিবন্ধকতা আছে কিনা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    return;
                }

                if (sharePref.get_data("gender").equalsIgnoreCase("male") && smokeValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনি ধূমপান করেন কিনা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    return;
                }



                String response = new StringBuilder().append("{")
                        .append("\"marital_status\":")
                        .append("\"")
                        .append(maritalStatusValue)
                        .append("\"")
                        .append(",")
                        .append("\"is_child_with\":")
                        .append("\"")
                        .append((stayStatus.getVisibility() == View.VISIBLE && stay_status>0)?stay_status:0)
                        .append("\"")
                        .append(",")
                        .append("\"child\":")
                        .append("\"")
                        .append((childNumber.getVisibility() == View.VISIBLE && child_number > 0)?child_number-1:0)
                        .append("\"")
                        .append(",")
                        .append("\"occupation\":")
                        .append("\"")
                        .append(occupationValue)
                        .append("\"")
                        .append(",")
                        .append("\"professional_group\":")
                        .append("\"")
                        .append(professionalGroupValue)
                        .append("\"")
                        .append(",")
                        .append("\"designation\":")
                        .append("\"")
                        .append(designationValue)
                        .append("\"")
                        .append(",")
                        .append("\"institute\":")
                        .append("\"")
                        .append(occupationInstitutionValue)
                        .append("\"")
                        .append(",")
                        .append("\"job_permission\":")
                        .append("\"")
                        .append("")
                        .append("\"")
                        .append(",")
                        .append("\"prayer\":")
                        .append("\"")
                        .append(religionValue)
                        .append("\"")
                        .append(",")
                        .append("\"fast\":")
                        .append("\"")
                        .append(rojaValue)
                        .append("\"")
                        .append(",")
                        .append("\"hijab\":")
                        .append("\"")
                        .append(hijabValue)
                        .append("\"")
                        .append(",")
                        .append("\"physical_disability\":")
                        .append("\"")
                        .append(disableValue)
                        .append("\"")
                        .append(",")
                        .append("\"physical_disability_description\":")
                        .append("\"")
                        .append((disableDescEdit.getVisibility()==View.VISIBLE)?disableDescEdit.getText().toString().trim():"")
                        .append("\"")
                        .append(",")
                        .append("\"is_smoking\":")
                        .append("\"")
                        .append(smokeValue)
                        .append("\"")
                        .append(",")
                        .append("\"height_ft\":")
                        .append(feetValue)
                        .append(",")
                        .append("\"height_inc\":")
                        .append(inchValue)
                        .append(",")
                        .append("\"skin_color\":")
                        .append("\"")
                        .append(skinValue)
                        .append("\"")
                        .append(",")
                        .append("\"blood_group\":")
                        .append("\"")
                        .append(bloodValue)
                        .append("\"")
                        .append(",")
                        .append("\"weight\":")
                        .append(weightValue)
                        .append(",")
                        .append("\"house_in_dhaka\":")
                        .append("\"")
                        .append(houseValue)
                        .append("\"")
                        .append(",")
                        .append("\"educations_information\":")
                        .append("[")
                        .append("{")
                        .append("\"name\":")
                        .append(degreeValue)
                        .append(",")
                        .append("\"subject\":")
                        .append("\"")
                        .append(subjectValue)
                        .append("\"")
                        .append(",")
                        .append("\"institution\":")
                        .append("\"")
                        .append(institutionValue)
                        .append("\"")
                        .append(",")
                        .append("\"year\":")
                        .append("\"")
                        .append("")
                        .append("\"")
                        .append("}")
                        .append("]")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(4)
                        .append("}")
                        .toString();

                // Toast.makeText(RegistrationPersonalInformation.this,response,Toast.LENGTH_LONG).show();

                Log.i("response", response);

              new RegistrationPersonalInformation.SendPersonalInfo().execute(response, Utils.SEND_INFO);

            }
        });


        maritalStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 1;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        educationalStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 2;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        professonalalStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 3;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        religiousStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 4;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        rojaStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 5;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        disableStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 6;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        smokeStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 7;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );
        professionalGroupStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 8;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        houseLinearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 9;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        hijabStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 10;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );
////////////////////////////////////////////////////
        heightStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 11;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        weightStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 12;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        bloodGroupStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 13;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        skinColorStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPopUp = 14;
                        startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
                    }
                }
        );

        childNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPopUp = 15;
                startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
            }
        });

        stayStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPopUp = 16;
                startActivity(new Intent(RegistrationPersonalInformation.this, PopUpPersonalInfo.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (RegistrationPersonalInformation.selectedPopUp == 1) {
            if (marriage > 0) {
                marriageTV.setText(maritalStatusName[marriage - 1]);
                maritalStatusValue = maritalStatusConstant.get(marriage - 1);
                if(sharePref.get_data("gender").equalsIgnoreCase("female") && marriage > 1 )
                {
                    childNumber.setVisibility(View.VISIBLE);
                }else{
                    childNumber.setVisibility(View.GONE);
                    stayStatus.setVisibility(View.GONE);
                    if(childTV.getText().toString().length() > 0)
                        childTV.setText("");
                    if(stayTV.getText().toString().length() > 0)
                        stayTV.setText("");
                }
            }

        } else if (RegistrationPersonalInformation.selectedPopUp == 2) {
            if (education > 0) {
                educationTV.setText(educationName[education - 1]);
                degreeValue = educationConstant.get(education - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 3) {
            if (profession > 0) {
                professonTV.setText(occupationName[profession - 1]);
                occupationValue = occupationConstant.get(profession - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 4) {
            if (religion > 0) {
                religionTV.setText(religionName[religion - 1]);
                religionValue = religionConstant.get(religion - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 5) {
            if (roja > 0) {
                rojaTV.setText(rojaName[roja - 1]);
                rojaValue = rojaConstant.get(roja - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 6) {
            if (disable > 0) {
                disableTV.setText(disableName[disable - 1]);
                disableValue = disableConstant.get(disable - 1);
                if(disable > 1) {
                    disableDescEdit.setVisibility(View.VISIBLE);
                    limitTV.setVisibility(View.VISIBLE);
                }
                else {
                    disableDescEdit.setVisibility(View.GONE);
                    limitTV.setVisibility(View.GONE);
                }
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 7) {
            if (smoke > 0) {
                smokeTV.setText(smokeName[smoke - 1]);
                smokeValue = smokeConstant.get(smoke - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 8) {
            if (professonalGroup > 0) {
                professionalGroupTV.setText(professonalGroupName[professonalGroup - 1]);
                professionalGroupValue = professonalGroupConstant.get(professonalGroup - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 9) {
            if (house > 0) {
                houseTV.setText(houseName[house - 1]);
                houseValue = houseConstant.get(house - 1);
            }
        } else if (RegistrationPersonalInformation.selectedPopUp == 10) {
            if (hijab > 0) {
                hijabTV.setText(hijabName[hijab - 1]);
                hijabValue = hijabConstant.get(hijab - 1);
            }
        }///////////////////////////////////////////////////////////
        else if (RegistrationPersonalInformation.selectedPopUp == 11) {
            if (height > 0) {
                heightTV.setText(heightName[height - 1]);
//                heightValue = heightConstant.get(height - 1);
                feetValue = ((height-1)/12 + 4) + "";
                inchValue = ((height-1) % 12) + "";
//                Log.e("height", feetValue + " feet " + inchValue + " inch");
            }
        }
        else if (RegistrationPersonalInformation.selectedPopUp == 12) {
            if (weight > 0) {
                weightTV.setText(weightName[weight - 1]);
                weightValue = weightConstant.get(weight - 1);
            }
        }
        else if (RegistrationPersonalInformation.selectedPopUp == 13) {
            if (blood_group > 0) {
                bloodTV.setText(bloodGroupName[blood_group - 1]);
                bloodValue = bloodGroupConstant.get(blood_group - 1);
            }
        }
        else if (RegistrationPersonalInformation.selectedPopUp == 14) {
            if (skin_color > 0) {
                skinTV.setText(skinColorName[skin_color - 1]);
                skinValue = skinColorConstant.get(skin_color - 1);
            }
        }else if (RegistrationPersonalInformation.selectedPopUp == 15) {
            if (child_number > 0) {
                childTV.setText(childName[child_number - 1]);
                childValue = childConstant.get(child_number - 1);
                if(sharePref.get_data("gender").equalsIgnoreCase("female") && child_number > 1 )
                {
                    stayStatus.setVisibility(View.VISIBLE);
                }else{
                    stayStatus.setVisibility(View.GONE);
                    if(stayTV.getText().toString().length() > 0)
                        stayTV.setText("");
                }
            }
        }else if (RegistrationPersonalInformation.selectedPopUp == 16) {
            if (stay_status > 0) {
                stayTV.setText(stayStausArrayName[stay_status - 1]);
                stayStatusValue = stayStausArrayConstant.get(stay_status - 1);
            }
        }
        //////////////////////////////////////////////////////////////

    }

    class SendPersonalInfo extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response","postexe: "+ s);
            if (progress.isShowing())
                progress.dismiss();
            if (s == null) {
                Utils.ShowAlert(RegistrationPersonalInformation.this, getString(R.string.no_internet_connection));
            } else {
                try {
//                    progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.has("errors")) {
//                        if (progress.isShowing())
//                            progress.dismiss();
                        String error = jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationPersonalInformation.this, error, Toast.LENGTH_LONG).show();
                    } else {
                        //  Toast.makeText(RegistrationPersonalInformation.this, "SendPersonalInfo else", Toast.LENGTH_LONG).show();
                        new RegistrationPersonalInformation.FetchConstant().execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            //final String token = sharePref.get_data("registration_token");
            final String token = sharePref.get_data("token");

            Log.e("Response", strings[0]);
            Log.e("Response", "SendPersonalInfo url: " + strings[1] + " " + token);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, strings[0]);
            Request request = new Request.Builder()
                    .url(strings[1])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(body)
                    .build();
            Response response = null;
            String responseString = null;
            try {
                response = client.newCall(request).execute();
                responseString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }


    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progress.isShowing())
                progress.dismiss();
            if (s == null) {
                Utils.ShowAlert(RegistrationPersonalInformation.this, getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing())
                    progress.dismiss();*/
                clearStaticData();
                Log.i("constantval",s+"");
                //Toast.makeText(getBaseContext(), "fetch constant else", Toast.LENGTH_LONG).show();
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);

                Intent signupIntent;
                signupIntent = new Intent(RegistrationPersonalInformation.this,
                        RegistrationFamilyInfoFirstPage.class);
                signupIntent.putExtra("constants", s);
                signupIntent.putExtra("isSignUp", true);
                startActivity(signupIntent);
//                finish();
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
            // Login.currentMobileSignupStep += 1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep)
                    .url(Utils.STEP_CONSTANT_FETCH + 5)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new GetPreviousStepFetchConstant().execute();
    }


    public class GetPreviousStepFetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 3)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 3);
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (s == null) {


                Utils.ShowAlert(RegistrationPersonalInformation.this, getString(R.string.no_internet_connection));
            } else {
                /*if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                clearStaticData();
                if(isSignUp) {
                    finish();
                }else {
                    Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
                    startActivity(new Intent(RegistrationPersonalInformation.this,
                            ImageUpload.class).
                            putExtra("constants", s));
                    finish();
                }
            }
        }
    }


    private void clearStaticData() {
        professonalGroupArray.clear();
        Arrays.fill(professonalGroupName, null);
        professonalGroupConstant.clear();
        Arrays.fill(professonalGroupConstantValue, null);

        occupationArray.clear();
        Arrays.fill(occupationName, null);
        occupationConstant.clear();
        Arrays.fill(occupationConstantValue, null);

        marriageArray.clear();
        Arrays.fill(maritalStatusName, null);
        maritalStatusConstant.clear();
        Arrays.fill(maritalStatusConstantValue, null);

        religionArray.clear();
        Arrays.fill(religionName, null);
        religionConstant.clear();
        Arrays.fill(religionConstantValue, null);

        educationArray.clear();
        Arrays.fill(educationName, null);
        educationConstant.clear();
        Arrays.fill(educationConstantValue, null);

        rojaArray.clear();
        Arrays.fill(rojaName, null);
        rojaConstant.clear();
        Arrays.fill(rojaConstantValue, null);

        hijabArray.clear();
        Arrays.fill(hijabName, null);
        hijabConstant.clear();
        Arrays.fill(hijabConstantValue, null);

        disableArray.clear();
        Arrays.fill(disableName, null);
        disableConstant.clear();
        Arrays.fill(disableConstantValue, null);

        smokeArray.clear();
        Arrays.fill(smokeName, null);
        smokeConstant.clear();
        Arrays.fill(smokeConstantValue, null);

        houseArray.clear();
        Arrays.fill(houseName, null);
        houseConstant.clear();
        Arrays.fill(houseConstantValue, null);

        heightArray.clear();
        Arrays.fill(heightName, null);
        heightConstant.clear();
        Arrays.fill(heightConstantValue, null);

        weightArray.clear();
        Arrays.fill(weightName, null);
        weightConstant.clear();
        Arrays.fill(weightConstantValue, null);

        bloodGroupArray.clear();
        Arrays.fill(bloodGroupName, null);
        bloodGroupConstant.clear();
        Arrays.fill(bloodGroupConstantValue, null);

        skinColorArray.clear();
        Arrays.fill(skinColorName, null);
        skinColorConstant.clear();
        Arrays.fill(skinColorConstantValue, null);

        selectedPopUp = 0;

        marriage = -1;
        education = -1;
        profession = -1;
        religion = -1;
        roja = -1;
        disable = -1;
        smoke = -1;
        professonalGroup = -1;
        house = -1;
        hijab = -1;
        height = -1;
        weight = -1;
        blood_group = -1;
        skin_color = -1;

    }
}
