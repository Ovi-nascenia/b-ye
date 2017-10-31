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

    SimpleRangeView rangeView_age, rangeView_height, rangeView_color, rangeView_education, rangeView_health;

    private ArrayList<String> age_lebel, heightLebel;

    private int minAgeRangePos = 0;
    private int maxAgeRangePos = 7;

    private int minHeightRangePos = 10;
    private int maxHeightRangePos = 19;

    private int minSkinColorRangePos = 0;
    private int maxSkinColorRangePos = 3;

    private int minHealthPos = 0;
    private int maxHealthPos = 1;

    private int minEducationPos = 2;
    private int maxEducationPos = 6;

    Map<Integer, String> skinColor = new HashMap<Integer, String>();

    Map<Integer, String> health = new HashMap<Integer, String>();

    Map<Integer, String> education = new HashMap<Integer, String>();

    String constant;

    Button next;

    ImageView back;

    int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        constant = intent.getStringExtra("constant");

        currentStep = 4;


        try {
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject skinColorObject = jsonObject.getJSONObject("skin_color_constant");
            JSONObject healthObject = jsonObject.getJSONObject("health_constant");
            JSONObject educationObject = jsonObject.getJSONObject("education_constant");

            for(int i=0;i<skinColorObject.length();i++)
            {
                skinColor.put(Integer.parseInt(skinColorObject.names().getString(i)),(String) skinColorObject.get(skinColorObject.names().getString(i)));
            }

            for(int i=0;i<healthObject.length();i++)
            {
                health.put(Integer.parseInt(healthObject.names().getString(i)),(String) healthObject.get(healthObject.names().getString(i)));
            }

            for(int i=0;i<educationObject.length();i++)
            {
                education.put(Integer.parseInt(educationObject.names().getString(i)),(String) educationObject.get(educationObject.names().getString(i)));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_registration_choice_selection_first_page);
        rangeView_age = (SimpleRangeView) findViewById(R.id.age);
        rangeView_height = (SimpleRangeView) findViewById(R.id.height);
        rangeView_color = (SimpleRangeView) findViewById(R.id.color);
        rangeView_health = (SimpleRangeView) findViewById(R.id.shape);
        rangeView_education = (SimpleRangeView) findViewById(R.id.education);

        age_lebel = new ArrayList<>();
        heightLebel = new ArrayList<>();

        next = (Button) findViewById(R.id.next);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        for (int i = 18; i <= 50; i++)
            age_lebel.add(i + "");


        for (int i = 4; i <= 7; i++){
            for (int j = 0; j < 12; j++){
                heightLebel.add(i + "'" + j + "\"");
            }
        }

        rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_age.setLabelColor(Color.TRANSPARENT);

        rangeView_age.setFixedLabelColor(Color.TRANSPARENT);
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

        rangeView_age.setStart(0);
        rangeView_age.setEnd(7);



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
        rangeView_height.setStart(10);
        rangeView_height.setEnd(19);



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
        rangeView_health.setEnd(1);



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
        rangeView_education.setStart(2);
        rangeView_education.setEnd(6);


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

                Toast.makeText(RegistrationChoiceSelectionFirstPage.this, total, Toast.LENGTH_LONG).show();
                new RegistrationChoiceSelectionFirstPage.SendChoiceInfo().execute(total);
            }
        });

    }


    class SendChoiceInfo extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(RegistrationChoiceSelectionFirstPage.this);
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
            try {
                progress.cancel();
                JSONObject jsonObject=new JSONObject(s);
                Log.e("Response",s);
                if(jsonObject.has("errors"))
                {
                    jsonObject.getJSONObject("errors").getString("detail");
                    Toast.makeText(RegistrationChoiceSelectionFirstPage.this, "error", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(RegistrationChoiceSelectionFirstPage.this,Login.class);
                    startActivity(intent);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings){
            SharePref sharePref = new SharePref(RegistrationChoiceSelectionFirstPage.this);
            final String token = sharePref.get_data("registration_token");

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
}
