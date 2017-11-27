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

public class RegistrationPersonalInformation extends AppCompatActivity {

    String maritalStatusValue, degreeValue, subjectValue, institutionValue, occupationValue, professionalGroupValue, designationValue, occupationInstitutionValue, religionValue, rojaValue, disableValue, smokeValue, hijabValue;
    EditText subjectText, institutionText, designationText, occupationInstitutionText;
    LinearLayout maritalStatus,educationalStatus,professonalalStatus,religiousStatus,rojaStatus,disableStatus,smokeStatus,professionalGroupStatus;
    TextView marriageTV,educationTV,professonTV,religionTV,rojaTV,disableTV,smokeTV,professionalGroupTV;
    Button next;
    ImageView back;

    String constant;
    JSONObject marriageObject, educationObject, occupationObject, professionalGroupObject, religionObject, rojaObject, hijabObject, disableObject, smokeObject;


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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constant");

        try {
            JSONObject jsonObject = new JSONObject(constant);

            if(Login.gender == "female"){
                marriageObject = jsonObject.getJSONObject("marital_status_constant_for_male");
            }
            else if(Login.gender == "male"){
                marriageObject = jsonObject.getJSONObject("marital_status_constant_for_female");
            }
            educationObject = jsonObject.getJSONObject("education_constant");
            occupationObject = jsonObject.getJSONObject("occupation_constant");
            professionalGroupObject = jsonObject.getJSONObject("professional_group_constant");
            religionObject= jsonObject.getJSONObject("prayer_options_female");
            rojaObject = jsonObject.getJSONObject("fasting");
            hijabObject = jsonObject.getJSONObject("hijab_options");
            disableObject = jsonObject.getJSONObject("physical_disability_options");
            smokeObject = jsonObject.getJSONObject("smoking_options");


            for(int i=0;i<marriageObject.length();i++)
            {
                maritalStatusConstant.add(marriageObject.names().getString(i));
                marriageArray.add((String) marriageObject.get(marriageObject.names().getString(i)));
            }

            maritalStatusName = marriageArray.toArray(maritalStatusName);

            for(int i=0;i<educationObject.length();i++)
            {
                educationConstant.add(educationObject.names().getString(i));
                educationArray.add((String) educationObject.get(educationObject.names().getString(i)));
            }

            educationName = educationArray.toArray(educationName);

            for(int i=0;i<occupationObject.length();i++)
            {
                occupationConstant.add(occupationObject.names().getString(i));
                occupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            occupationName = occupationArray.toArray(occupationName);

            for(int i=0;i<professionalGroupObject.length();i++)
            {
                professonalGroupConstant.add(professionalGroupObject.names().getString(i));
                professonalGroupArray.add((String) professionalGroupObject.get(professionalGroupObject.names().getString(i)));
            }

            professonalGroupName = professonalGroupArray.toArray(professonalGroupName);

            for(int i=0;i<religionObject.length();i++)
            {
                religionConstant.add(religionObject.names().getString(i));
                religionArray.add((String) religionObject.get(religionObject.names().getString(i)));
            }

            religionName = religionArray.toArray(religionName);

            for(int i=0;i<rojaObject.length();i++)
            {
                rojaConstant.add(rojaObject.names().getString(i));
                rojaArray.add((String) rojaObject.get(rojaObject.names().getString(i)));
            }

            rojaName = rojaArray.toArray(rojaName);

            for(int i=0;i<hijabObject.length();i++)
            {
                hijabConstant.add(hijabObject.names().getString(i));
                hijabArray.add((String) hijabObject.get(hijabObject.names().getString(i)));
            }

            hijabName = hijabArray.toArray(hijabName);

            for(int i=0;i<disableObject.length();i++)
            {
                disableConstant.add(disableObject.names().getString(i));
                disableArray.add((String) disableObject.get(disableObject.names().getString(i)));
            }

            disableName = disableArray.toArray(disableName);

            for(int i=0;i<smokeObject.length();i++)
            {
                smokeConstant.add(smokeObject.names().getString(i));
                smokeArray.add((String) smokeObject.get(smokeObject.names().getString(i)));
            }

            smokeName = smokeArray.toArray(smokeName);


        }catch (JSONException e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_registration_personal_information);

        subjectText = (EditText) findViewById(R.id.subject_text);
        institutionText = (EditText) findViewById(R.id.institution_text);
        designationText = (EditText) findViewById(R.id.designation_text);
        occupationInstitutionText = (EditText) findViewById(R.id.occupation_institution_text);


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
                new Intent(RegistrationPersonalInformation.this,Login.class);
                finish();
            }
        });

        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                designationValue = designationText.getText().toString();
                occupationInstitutionValue = occupationInstitutionText.getText().toString();
                institutionValue = institutionText.getText().toString();
                subjectValue = subjectText.getText().toString();


                String response = new StringBuilder().append("{")
                        .append("\"marital_status\":")
                        .append("\"")
                        .append(maritalStatusValue)
                        .append("\"")
                        .append(",")
                        .append("\"is_child_with\":")
                        .append("\"")
                        .append(0)
                        .append("\"")
                        .append(",")
                        .append("\"child\":")
                        .append("\"")
                        .append(0)
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
                        .append("")
                        .append("\"")
                        .append(",")
                        .append("\"is_smoking\":")
                        .append(smokeValue)
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
                        .append(7)
                        .append("}")
                        .toString();

                Toast.makeText(RegistrationPersonalInformation.this,response,Toast.LENGTH_LONG).show();

                new RegistrationPersonalInformation.SendPersonalInfo().execute(response, Utils.SEND_INFO);

            }
        });


        maritalStatus.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
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
                marriageTV.setText(maritalStatusName[marriage-1]);
                maritalStatusValue = maritalStatusConstant.get(marriage-1);
            }

        }else if(RegistrationPersonalInformation.selectedPopUp == 2){
            if(education>0)
            {
                educationTV.setText(educationName[education-1]);
                degreeValue = educationConstant.get(education-1);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 3){
            if(profession>0)
            {
                professonTV.setText(occupationName[profession-1]);
                occupationValue = occupationConstant.get(profession-1);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 4){
            if(religion>0)
            {
                religionTV.setText(religionName[religion-1]);
                religionValue = religionConstant.get(religion-1);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 5){
            if(roja>0)
            {
                rojaTV.setText(rojaName[roja-1]);
                rojaValue = rojaConstant.get(roja-1);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 6){
            if(disable>0)
            {
                disableTV.setText(disableName[disable-1]);
                disableValue = disableConstant.get(disable-1);
            }
        }else if(RegistrationPersonalInformation.selectedPopUp == 7){
            if(smoke>0)
            {
                smokeTV.setText(smokeName[smoke-1]);
                smokeValue = smokeConstant.get(smoke-1);
            }
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 8){
            if(professonalGroup>0)
            {
                professionalGroupTV.setText(professonalGroupName[professonalGroup-1]);
                professionalGroupValue = professonalGroupConstant.get(professonalGroup-1);
            }
        }

    }

    class SendPersonalInfo extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(RegistrationPersonalInformation.this);;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            if ( !progress.isShowing() )
                progress.show();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s == null){
                progress.cancel();
                Utils.ShowAlert(RegistrationPersonalInformation.this, getString(R.string.no_internet_connection));
            }
            else{
                try {
                    progress.cancel();
                    JSONObject jsonObject=new JSONObject(s);
                    Log.e("Response",s);
                    if(jsonObject.has("errors")){
                        jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationPersonalInformation.this, "error", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(RegistrationPersonalInformation.this,Login.class);
                        startActivity(intent);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings){
            SharePref sharePref = new SharePref(RegistrationPersonalInformation.this);
            final String token = sharePref.get_data("registration_token");

            Log.e("Test", strings[0]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,strings[0] );
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
            }catch (IOException e){
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }
}
