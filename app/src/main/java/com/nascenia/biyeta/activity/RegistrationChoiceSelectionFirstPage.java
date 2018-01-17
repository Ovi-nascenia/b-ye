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
import android.widget.ImageView;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.bendik.simplerangeview.SimpleRangeView;

public class RegistrationChoiceSelectionFirstPage extends AppCompatActivity {
    OkHttpClient client;

    SimpleRangeView rangeView_age, rangeView_height, rangeView_color, rangeView_education, rangeView_health;

    private ArrayList<String> age_lebel, heightLebel;

    private int minAgeRangePos = 0;
    private int maxAgeRangePos = 7;

    private int minHeightRangePos = 10;
    private int maxHeightRangePos = 19;

    private int minSkinColorRangePos = 0;
    private int maxSkinColorRangePos = 3;

    private int minHealthPos = 0;
    private int maxHealthPos = 2;

    private int minEducationPos = 2;
    private int maxEducationPos = 6;

    Map<Integer, String> skinColor = new HashMap<Integer, String>();

    Map<Integer, String> health = new HashMap<Integer, String>();

    Map<Integer, String> education = new HashMap<Integer, String>();

    String constant;

    Button next;

    ImageView back;

    int currentStep;

    private ProgressDialog progress;

    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice_selection_first_page);

        sharePref = new SharePref(RegistrationChoiceSelectionFirstPage.this);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        client = new OkHttpClient();

        progress = new ProgressDialog(RegistrationChoiceSelectionFirstPage.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        currentStep = 4;

        try {
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject skinColorObject = jsonObject.getJSONObject("skin_color_constant");
            JSONObject healthObject = jsonObject.getJSONObject("health_constant");
            JSONObject educationObject = jsonObject.getJSONObject("education_constant");

            for (int i = 0; i < skinColorObject.length(); i++) {
                skinColor.put(Integer.parseInt(skinColorObject.names().getString(i)), (String) skinColorObject.get(skinColorObject.names().getString(i)));
            }

            for (int i = 0; i < healthObject.length(); i++) {
                health.put(Integer.parseInt(healthObject.names().getString(i)), (String) healthObject.get(healthObject.names().getString(i)));
            }

            for (int i = 0; i < educationObject.length(); i++) {
                education.put(Integer.parseInt(educationObject.names().getString(i)), (String) educationObject.get(educationObject.names().getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        rangeView_age = (SimpleRangeView) findViewById(R.id.age);
        rangeView_height = (SimpleRangeView) findViewById(R.id.height);
        rangeView_color = (SimpleRangeView) findViewById(R.id.color);
        rangeView_health = (SimpleRangeView) findViewById(R.id.shape);
        rangeView_education = (SimpleRangeView) findViewById(R.id.education);


        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new Intent(RegistrationChoiceSelectionFirstPage.this,Login.class);
                finish();*/
                new GetPreviousStepFetchConstant().execute();
            }
        });
        age_lebel = new ArrayList<>();
        heightLebel = new ArrayList<>();

        next = (Button) findViewById(R.id.next);

        for (int i = 18; i <= 50; i++)
            age_lebel.add(i + "");


        for (int i = 4; i <= 7; i++) {
            for (int j = 0; j < 12; j++) {
                heightLebel.add(i + "'" + j + "\"");
            }
        }

        rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_age.setLabelColor(Color.TRANSPARENT);

        rangeView_age.setFixedLabelColor(Color.TRANSPARENT);

        /*rangeView_age.setStart(0);
        rangeView_age.setEnd(12);
*/
        /*
        if (sharePref.get_data("gender").equalsIgnoreCase("female")) {

            minAgeRangePos = 4;
            maxAgeRangePos = 12;
            rangeView_age.setStart(minAgeRangePos);
            rangeView_age.setEnd(maxAgeRangePos);

            minHeightRangePos = 15;
            maxHeightRangePos = 20;
            rangeView_height.setStart(minHeightRangePos);
            rangeView_height.setEnd(maxHeightRangePos);

            minEducationPos=4;
            maxEducationPos=8;
            rangeView_education.setStart(minEducationPos);
            rangeView_education.setEnd(maxEducationPos);

        } else {

            minAgeRangePos = 4;
            maxAgeRangePos = 12;
            rangeView_age.setStart(minAgeRangePos);
            rangeView_age.setEnd(maxAgeRangePos);

            minHeightRangePos = 10;
            maxHeightRangePos = 15;
            rangeView_height.setStart(minHeightRangePos);
            rangeView_height.setEnd(maxHeightRangePos);

            minEducationPos=2;
            maxEducationPos=4;
            rangeView_education.setStart(minEducationPos);
            rangeView_education.setEnd(maxEducationPos);


        }*/

        rangeView_age.setStart(0);
        rangeView_age.setEnd(age_lebel.size());
        rangeView_height.setStart(0);
        rangeView_height.setEnd(heightLebel.size());
        rangeView_education.setStart(0);
        rangeView_education.setEnd(education.size());

        rangeView_age.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);
                minAgeRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);
                maxAgeRangePos = i;
            }
        });

        rangeView_age.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return age_lebel.get(i);
            }
        });

       /* rangeView_age.setStart(0);
       // rangeView_age.setEnd(7);
        rangeView_age.setEnd(12);*/


        rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_color.setLabelColor(Color.TRANSPARENT);

        rangeView_color.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_color.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                minSkinColorRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                maxSkinColorRangePos = i;
            }
        });

        rangeView_color.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return skinColor.get(i);
            }
        });
        rangeView_color.setStart(0);
        rangeView_color.setEnd(3);


        rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_height.setLabelColor(Color.TRANSPARENT);

        rangeView_height.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_height.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);
                minHeightRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);
                maxHeightRangePos = i;
            }
        });

        rangeView_height.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return heightLebel.get(i);
            }
        });
        /*rangeView_height.setStart(10);
        rangeView_height.setEnd(19);*/


        rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_health.setLabelColor(Color.TRANSPARENT);

        rangeView_health.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_health.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_health.setLabelColor(Color.TRANSPARENT);
                minHealthPos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_health.setLabelColor(Color.TRANSPARENT);
                maxHealthPos = i;
            }
        });

        rangeView_health.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return health.get(i);
            }
        });

        rangeView_health.setStart(0);
        //rangeView_health.setEnd(1);
        rangeView_health.setEnd(2);


        rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_education.setLabelColor(Color.TRANSPARENT);

        rangeView_education.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_education.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                minEducationPos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                maxEducationPos = i;
            }
        });

        rangeView_education.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return education.get(i);
            }
        });
      /*  rangeView_education.setStart(2);
        rangeView_education.setEnd(4);*/


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String total = new StringBuilder().append("{")
                        .append("\"age\":")
                        .append("\"")
                        .append(minAgeRangePos)
                        .append(";")
                        .append(maxAgeRangePos)
                        .append("\"")
                        .append(",")
                        .append("\"height\":")
                        .append("\"")
                        .append(minHeightRangePos)
                        .append(";")
                        .append(maxHeightRangePos)
                        .append("\"")
                        .append(",")
                        .append("\"skin\":")
                        .append("\"")
                        .append(minSkinColorRangePos)
                        .append(";")
                        .append(maxSkinColorRangePos)
                        .append("\"")
                        .append(",")
                        .append("\"health\":")
                        .append("\"")
                        .append(minHealthPos)
                        .append(";")
                        .append(maxHealthPos)
                        .append("\"")
                        .append(",")
                        .append("\"education\":")
                        .append("\"")
                        .append(minEducationPos)
                        .append(";")
                        .append(maxEducationPos)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(currentStep)
                        .append("}")
                        .toString();
                Log.i("prefresponse", total);
                // Toast.makeText(RegistrationChoiceSelectionFirstPage.this, total, Toast.LENGTH_LONG).show();
                new RegistrationChoiceSelectionFirstPage.SendChoiceInfo().execute(total);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new GetPreviousStepFetchConstant().execute();
    }

    class SendChoiceInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
            // progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                if (progress.isShowing())
                    progress.dismiss();
                Utils.ShowAlert(RegistrationChoiceSelectionFirstPage.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    // progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
                        if (progress.isShowing())
                            progress.dismiss();

                        String error = jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationChoiceSelectionFirstPage.this,
                                error + " ", Toast.LENGTH_LONG).show();
                    } else {
                        new RegistrationChoiceSelectionFirstPage.FetchConstant().execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(RegistrationChoiceSelectionFirstPage.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, strings[0]);
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
                Utils.ShowAlert(RegistrationChoiceSelectionFirstPage.this, getString(R.string.no_internet_connection));
            } else {
                /*if(progress.isShowing())
                    progress.dismiss();*/
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent signupIntent;
                signupIntent = new Intent(RegistrationChoiceSelectionFirstPage.this,
                        RegistrationChoiceSelectionSecondPage.class);
                signupIntent.putExtra("constants", s);
                startActivity(signupIntent);
                finish();
            }


        }

        @Override
        protected String doInBackground(String... parameters) {
            //Login.currentMobileSignupStep+=1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep )
                    .url(Utils.STEP_CONSTANT_FETCH + 5)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
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
        protected void onPreExecute() {
            super.onPreExecute();
        }
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

                Utils.ShowAlert(RegistrationChoiceSelectionFirstPage.this,
                        getString(R.string.no_internet_connection));
            } else {
                /*if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);

                startActivity(new Intent(RegistrationChoiceSelectionFirstPage.this, ImageUpload.class).
                        putExtra("constants", s));
                finish();
            }
        }
    }
}
