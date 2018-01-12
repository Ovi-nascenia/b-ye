package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

public class RegistrationFamilyInfoFirstPage extends AppCompatActivity {

    public static ArrayList<String> professonalGroupArray = new ArrayList<String>();
    public static String[] professonalGroupName = new String[professonalGroupArray.size()];
    public static ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    public static String[] professonalGroupConstantValue = new String[professonalGroupArray.size()];

    public static ArrayList<String> occupationArray = new ArrayList<String>();
    public static String[] occupationName = new String[occupationArray.size()];
    public static ArrayList<String> occupationConstant = new ArrayList<String>();
    public static String[] occupationConstantValue = new String[occupationArray.size()];
    LinearLayout professionFatherStatus, professionMotherStatus, professionalGroupFatherStatus, professionalGroupMotherStatus;

    TextView professionFatherTV, professionMotherTV, professionalGroupFatherTV, professionalGroupMotherTV;

    EditText nameFather, designationFather, institutionFather;

    EditText nameMother, designationMother, institutionMother;

    Button next;

    ImageView back;

    String constant, occupationOfFather = "", occupationOfMother = "", professionOfFather = "", professionOfMother = "";

    JSONObject professionalGroupObject, occupationObject;

    public static int professionFather = -1;
    public static int professionMother = -1;
    public static int professionalGroupFather = -1;
    public static int professionalGroupMother = -1;

    public static int selectedPopUp = 0;

    TextView professionStatusLabelFather, professionStatusLabelMother;

    public static ArrayList<String> fatherOccupationArray = new ArrayList<String>();
    public static String[] fatherOccupationName = new String[occupationArray.size()];
    public static ArrayList<String> fatherOccupationConstant = new ArrayList<String>();

    ProgressDialog progress;
    OkHttpClient client;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_family_info_first_page);

        sharePref = new SharePref(RegistrationFamilyInfoFirstPage.this);

        progress = new ProgressDialog(RegistrationFamilyInfoFirstPage.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        client = new OkHttpClient();

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");

        try {
            JSONObject jsonObject = new JSONObject(constant);

            occupationObject = jsonObject.getJSONObject("occupation_constant");
            professionalGroupObject = jsonObject.getJSONObject("professional_group_constant");

            Log.i("occupation", occupationObject.toString());

            for (int i = 0; i < occupationObject.length(); i++) {
                occupationConstant.add(occupationObject.names().getString(i));
                occupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            occupationName = occupationArray.toArray(occupationName);



            for (int i = 0; i < occupationObject.length() - 1; i++) {
                fatherOccupationConstant.add(occupationObject.names().getString(i));
                fatherOccupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            fatherOccupationName = fatherOccupationArray.toArray(fatherOccupationName);


            for (int i = 0; i < professionalGroupObject.length(); i++) {
                professonalGroupConstant.add(professionalGroupObject.names().getString(i));
                professonalGroupArray.add((String) professionalGroupObject.get(professionalGroupObject.names().getString(i)));
            }

            professonalGroupName = professonalGroupArray.toArray(professonalGroupName);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        nameFather = (EditText) findViewById(R.id.name_father);
        designationFather = (EditText) findViewById(R.id.designation_father);
        institutionFather = (EditText) findViewById(R.id.institution_father);

        nameMother = (EditText) findViewById(R.id.name_mother);
        designationMother = (EditText) findViewById(R.id.designation_mother);
        institutionMother = (EditText) findViewById(R.id.institution_mother);

        professionStatusLabelFather = (TextView) findViewById(R.id.profession_status_label_father);
        professionFatherStatus = (LinearLayout) findViewById(R.id.professonalalStatusFather);

        professionMotherStatus = (LinearLayout) findViewById(R.id.professonalalStatusMother);
        professionalGroupFatherStatus = (LinearLayout) findViewById(R.id.professonalGroupStatusFather);
        professionalGroupMotherStatus = (LinearLayout) findViewById(R.id.professonalGroupStatusMother);
        professionStatusLabelMother = (TextView) findViewById(R.id.profession_status_label_mother);


        professionFatherTV = (TextView) findViewById(R.id.profession_text_view_father);
        professionalGroupFatherTV = (TextView) findViewById(R.id.profession_group_text_view_father);


        professionMotherTV = (TextView) findViewById(R.id.profession_text_view_mother);
        professionalGroupMotherTV = (TextView) findViewById(R.id.profession_group_text_view_mother);

        professionFatherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 1;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class));
            }
        });

        professionalGroupFatherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 2;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class));
            }
        });

        professionMotherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 3;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class));
            }
        });
        professionalGroupMotherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 4;
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this, PopUpFamilyInfoFirstPage.class));
            }
        });
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameFather.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.write_your_father_name), Toast.LENGTH_LONG).show();
                    professionStatusLabelFather.getParent().
                            requestChildFocus(professionStatusLabelFather, professionStatusLabelFather);
                    return;
                }

                if (occupationOfFather.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.choose_your_father_occupation), Toast.LENGTH_LONG).show();
                    professionStatusLabelFather.getParent().
                            requestChildFocus(professionStatusLabelFather, professionStatusLabelFather);
                    return;
                }


                if (occupationOfMother.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.choose_your_mother_occupation), Toast.LENGTH_LONG).show();
                    professionStatusLabelMother.getParent().
                            requestChildFocus(professionStatusLabelMother, professionStatusLabelMother);
                    return;
                }


                String response = new StringBuilder()
                        .append("{")
                        .append("\"family_members_mother_father\":")
                        .append("[")
                        .append("{")
                        .append("\"name\":")
                        .append("\"")
                        .append(nameFather.getText().toString())
                        .append("\"")
                        .append(",")
                        .append("\"relation\":")
                        .append("\"")
                        .append("1")
                        .append("\"")
                        .append(",")
                        .append("\"occupation\":")
                        .append("\"")
                        .append(occupationOfFather)
                        .append("\"")
                        .append(",")
                        .append("\"professional_group\":")
                        .append("\"")
                        .append(professionOfFather)
                        .append("\"")
                        .append(",")
                        .append("\"designation\":")
                        .append("\"")
                        .append(designationFather.getText().toString())
                        .append("\"")
                        .append(",")
                        .append("\"institute\":")
                        .append("\"")
                        .append(institutionFather.getText().toString())
                        .append("\"")
                        .append("}")
                        .append(",")
                        .append("{")
                        .append("\"name\":")
                        .append("\"")
                        .append(nameMother.getText().toString())
                        .append("\"")
                        .append(",")
                        .append("\"relation\":")
                        .append("\"")
                        .append("2")
                        .append("\"")
                        .append(",")
                        .append("\"occupation\":")
                        .append("\"")
                        .append(occupationOfMother)
                        .append("\"")
                        .append(",")
                        .append("\"professional_group\":")
                        .append("\"")
                        .append(professionOfMother)
                        .append("\"")
                        .append(",")
                        .append("\"designation\":")
                        .append("\"")
                        .append(designationMother.getText().toString())
                        .append("\"")
                        .append(",")
                        .append("\"institute\":")
                        .append("\"")
                        .append(institutionMother.getText().toString())
                        .append("\"")
                        .append("}")
                        .append("]")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(8)
                        .append("}")
                        .toString();
                Log.i("response: ", response);
                new RegistrationFamilyInfoFirstPage.SendFamilyInfo().execute(response, Utils.SEND_INFO);

            }
        });

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new Intent(RegistrationFamilyInfoFirstPage.this,Login.class);
                finish();*/
                new GetPreviousStepFetchConstant().execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RegistrationFamilyInfoFirstPage.selectedPopUp == 1) {
            if (professionFather > 0) {
                professionFatherTV.setText(fatherOccupationName[professionFather - 1]);
                occupationOfFather = fatherOccupationConstant.get(professionFather - 1);
            }

        } else if (RegistrationFamilyInfoFirstPage.selectedPopUp == 2) {
            if (professionalGroupFather > 0) {
                professionalGroupFatherTV.setText(professonalGroupName[professionalGroupFather - 1]);
                professionOfFather = professonalGroupConstant.get(professionalGroupFather - 1);
            }
        } else if (RegistrationFamilyInfoFirstPage.selectedPopUp == 3) {
            if (professionMother > 0) {
                professionMotherTV.setText(occupationName[professionMother - 1]);
                occupationOfMother = occupationConstant.get(professionMother - 1);
            }
        } else if (RegistrationFamilyInfoFirstPage.selectedPopUp == 4) {
            if (professionalGroupMother > 0) {
                professionalGroupMotherTV.setText(professonalGroupName[professionalGroupMother - 1]);
                professionOfMother = professonalGroupConstant.get(professionalGroupMother - 1);
            }
        }
    }


    class SendFamilyInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                if (progress.isShowing())
                    progress.dismiss();

                Utils.ShowAlert(RegistrationFamilyInfoFirstPage.this, getString(R.string.no_internet_connection));
            } else {
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
                        if (progress.isShowing())
                            progress.dismiss();

                        String error = jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationFamilyInfoFirstPage.this, error, Toast.LENGTH_LONG).show();
                    } else {
                        //Intent intent = new Intent(RegistrationFamilyInfoFirstPage.this,Login.class);
                        //startActivity(intent);
                        new RegistrationFamilyInfoFirstPage.FetchConstant().execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(RegistrationFamilyInfoFirstPage.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

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
            if (s == null) {
                if (progress.isShowing())
                    progress.dismiss();

                Utils.ShowAlert(RegistrationFamilyInfoFirstPage.this, getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing())
                    progress.dismiss();*/
                clearStaticData();
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent signupIntent;
                signupIntent = new Intent(RegistrationFamilyInfoFirstPage.this,
                        RegistrationFamilyInfoSecondPage.class);
                signupIntent.putExtra("constants", s);
                startActivity(signupIntent);
                finish();
            }


        }

        @Override
        protected String doInBackground(String... parameters) {
            //  Login.currentMobileSignupStep+=1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep )
                    .url(Utils.STEP_CONSTANT_FETCH + 9)
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
                    .url(Utils.STEP_CONSTANT_FETCH + 7)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 7);
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
            if (s == null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }

                Utils.ShowAlert(RegistrationFamilyInfoFirstPage.this, getString(R.string.no_internet_connection));
            } else {
                /*if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                clearStaticData();
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                startActivity(new Intent(RegistrationFamilyInfoFirstPage.this,
                        RegistrationPersonalInformation.class).
                        putExtra("constants", s));
                finish();
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


        professionFather = -1;
        professionMother = -1;
        professionalGroupFather = -1;
        professionalGroupMother = -1;

        selectedPopUp = 0;
        fatherOccupationArray.clear();
        Arrays.fill(fatherOccupationName, null);
        fatherOccupationConstant.clear();


    }
}
