package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.constant.Constant;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


import com.nascenia.biyeta.adapter.Profession_Adapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.view.MyGridView;

import me.bendik.simplerangeview.SimpleRangeView;

import static me.bendik.simplerangeview.SimpleRangeView.*;

public class Search_Filter extends AppCompatActivity implements OnClickListener {


    SimpleRangeView rangeView_age, rangeView_height, rangeView_color, rangeView_education, rangeView_health;
    TextView textView;
    Button button;

    private ArrayList<String> skin_lebel, age_lebel, heightLebel, education_lebel, professional_lebel, occupation_lebel;


    MyGridView gridView;
    MyGridView gridViewOccupation, gridViewLocation;
    private final OkHttpClient client = new OkHttpClient();


    public static ArrayList<Integer> locationGridItemCheckedCheckBoxPositionList = new ArrayList<Integer>();
    public static ArrayList<Integer> professionGridItemCheckedCheckBoxPositionList = new ArrayList<Integer>();
    public static ArrayList<Integer> occupationGridItemCheckedCheckBoxPositionList = new ArrayList<Integer>();
    public static ArrayList<Integer> currentStatusList = new ArrayList<Integer>();
    public static ArrayList<Integer> maritalStatusList = new ArrayList<Integer>();
    public static ArrayList<Integer> religionCastList = new ArrayList<Integer>();


    private static int minAgeRangePos;
    private static int maxAgeRangePos;
    private static int minHeightRangePos;
    private static int maxHeightRangePos;
    private static int minSkinRangePos;
    private static int maxSkingRangePos;
    private int minHealthRangePos;
    private int maxHealthRangePos;
    private int minEducationRangePos;
    private int maxEducationRangePos;


    String[] skinStatus = new String[]{
            "শ্যামলা",
            "উজ্জ্বল শ্যামলা",
            "ফর্সা",
            "অনেক ফর্সা"
    };

    String[] healthStatus = new String[]{
            "স্লিম ",
            "স্বাস্থ্যবান",
            "বেশ স্বাস্থ্যব"
    };

    String[] educationStatus = new String[]{

            "মাধ্যমিক",
            "উচ্চ-মাধ্যমিক পড়ছি ",
            "উচ্চ-মাধ্যমিক/ডিপ্লোমা ",
            "ব্যাচেলর পড়ছি",
            "ব্যাচেলর",
            "মাস্টার্স পড়ছি",
            "মাস্টার্স",
            "ডক্টরেট পড়ছি",
            "ডক্টরেট"
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_search);
        setUpId();
        initializeVariable();
        new Get_Data().execute();
        set_rangeView_lebel();

    }


    void initializeVariable() {
        locationGridItemCheckedCheckBoxPositionList.clear();
        occupationGridItemCheckedCheckBoxPositionList.clear();
        professionGridItemCheckedCheckBoxPositionList.clear();
        locationId.clear();
        professionId.clear();
        occupationId.clear();



        currentStatusList.clear();
        religionCastList.clear();
        maritalStatusList.clear();

        age_lebel = new ArrayList<>();
        heightLebel = new ArrayList<>();
        education_lebel = new ArrayList<>();
        professional_lebel = new ArrayList<>();
        occupation_lebel = new ArrayList<>();
        skin_lebel = new ArrayList<>();

        for (int i = 18; i <= 50; i++)
            age_lebel.add(i + "");

        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 13; j++)
                heightLebel.add(i + "'" + j + "\"");


        }

        currentStatusList.add(0);
        currentStatusList.add(1);

        maritalStatusList.add(1);
        maritalStatusList.add(2);
        maritalStatusList.add(3);
        maritalStatusList.add(4);


        religionCastList.add(1);
        religionCastList.add(2);
        religionCastList.add(3);
    }


    public static  ArrayList<Integer> occupationId=new ArrayList<>();
    public static  ArrayList<Integer> professionId=new ArrayList<>();
    public static ArrayList<Integer> locationId=new ArrayList<>();

    private void set_rangeView_lebel() {

        rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_age.setLabelColor(Color.TRANSPARENT);

        rangeView_age.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_age.setOnTrackRangeListener(new OnTrackRangeListener() {
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

        rangeView_age.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return age_lebel.get(i);
            }
        });





        rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_height.setLabelColor(Color.TRANSPARENT);

        rangeView_height.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_height.setOnTrackRangeListener(new OnTrackRangeListener() {
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
        rangeView_height.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return heightLebel.get(i);
            }
        });
        rangeView_color.setLabelFontSize(14);
        rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_color.setLabelColor(Color.TRANSPARENT);

        rangeView_color.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_color.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                minSkinRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                maxSkingRangePos = i;

            }
        });
        rangeView_color.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return skinStatus[i];
            }
        });
        rangeView_education.setLabelFontSize(14);
        rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_education.setLabelColor(Color.TRANSPARENT);
        rangeView_education.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_education.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {


                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                minEducationRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                maxEducationRangePos = i;
            }
        });

        rangeView_education.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return educationStatus[i];
            }
        });
        rangeView_health.setLabelFontSize(14);
        rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_health.setLabelColor(Color.TRANSPARENT);

        rangeView_health.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_health.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {


                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                minHealthRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                maxHealthRangePos = i;
            }
        });

        rangeView_health.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return healthStatus[i];
            }
        });
    }

    String res =
            "\n" +
                    "{\n" +
                    "  \"search\": {\n" +
                    "    \"age_range\": \"9;18\",\n" +
                    "    \"height_range\": \"10;27\",\n" +
                    "    \"skin_color\": \"0;3\",\n" +
                    "    \"health_range\": \"0;2\",\n" +
                    "    \"education_range\": \"0;8\",\n" +
                    "    \"occupation_range\": [\"5\",\"7\",\"1\" ],\n" +
                    "    \"profession_grp_range\": [\"101\", \"102\", \"104\", \"105\", \"108\", \"110\", \"112\" ],\n" +
                    "    \"current_status\": [ \"0\", \"1\" ],\n" +
                    "    \"division_status\": [\"18\", \"19\", \"20\", \"21\", \"23\", \"24\", \"25\", \"26\", \"28\", \"29\", \"31\",  \"32\",\"34\",\"27\",\"22\", \"30\", \"33\", \"7\", \"8\", \"9\", \"10\",\"11\", \"12\", \"13\", \"14\", \"15\", \"16\", \"17\", \"35\", \"36\", \"37\", \"38\", \"39\", \"40\", \"41\",  \"42\", \"43\", \"44\", \"45\", \"46\", \"49\", \"52\",  \"54\", \"55\", \"57\", \"59\",\"1\",\"2\",\"3\", \"4\",\"5\",\"6\",\"61\", \"62\",\"63\",\"64\",\"47\",\"48\",\"50\",\"51\",\"53\",\"56\",\"58\",\"60\"],\n" +
                    "    \"marital_status\": [ \"1\", \"2\", \"3\", \"4\" ],\n" +
                    "    \"religion_cast\": [ \"1\", \"2\", \"3\" ]\n" +
                    "  }\n" +
                    "}\n" +
                    "\n";
    String ch = "";


    int covertIntoInc(String height) {
        String ch[] = height.split("\'");
        int inc = (Integer.parseInt(ch[0]) * 12 + Integer.parseInt(ch[1].replace("\"", ""))) - 48;
        return inc;
    }

    public String porcessJSon() {


        int startHeightInc = covertIntoInc(heightLebel.get(minHeightRangePos));
        int endHeightInc = covertIntoInc(heightLebel.get(maxHeightRangePos));
        Log.e("Ovi Age", +minHeightRangePos + "* " + startHeightInc + " " + endHeightInc + "**" + maxHeightRangePos);


        String response = new StringBuilder().append("{")
                .append("\"search\": {")
                .append("\"age_range\": ")
                .append("\"")
                .append(Integer.parseInt(age_lebel.get(minAgeRangePos)) - 18)
                .append(";")
                .append(Integer.parseInt(age_lebel.get(maxAgeRangePos)) - 18)
                .append("\",")

                .append("\"height_range\": ")
                .append("\"")
                .append(startHeightInc)
                .append(";")
                .append(endHeightInc)
                .append("\",")

                .append("\"skin_color\": ")
                .append("\"")
                .append(minSkinRangePos)
                .append(";")
                .append(maxSkingRangePos)
                .append("\",")

                .append("\"health_range\": ")
                .append("\"")
                .append(minHealthRangePos)
                .append(";")
                .append(maxHealthRangePos)
                .append("\",")

                .append("\"education_range\": ")
                .append("\"")
                .append(minEducationRangePos)
                .append(";")
                .append(maxEducationRangePos)
                .append("\",")

                .append("\"occupation_range\": ")
                .append("[\"")
                .append(android.text.TextUtils.join(
                        "\",\"",
                        occupationGridItemCheckedCheckBoxPositionList
                ))
                .append("\"],")

                .append("\"profession_grp_range\": ")
                .append("[\"")
                .append(android.text.TextUtils.join(
                        "\",\"",
                        professionGridItemCheckedCheckBoxPositionList
                ))
                .append("\"],")


                .append("\"current_status\": ")
                .append("[\"")
                .append(android.text.TextUtils.join(
                        "\",\"",
                        currentStatusList
                ))
                .append("\"],")


                .append("\"division_status\": ")
                .append("[\"")
                .append(android.text.TextUtils.join(
                        "\",\"",
                        locationGridItemCheckedCheckBoxPositionList
                ))
                .append("\"],")


                .append("\"marital_status\": ")
                .append("[\"")
                .append(android.text.TextUtils.join(
                        "\",\"",
                        maritalStatusList
                ))
                .append("\"],")


                .append("\"religion_cast\": ")
                .append("[\"")
                .append(android.text.TextUtils.join(
                        "\",\"",
                        religionCastList
                ))
                .append("\"]")
                .append("}}")

                .toString();

        return response;


    }

    public  static String reponse="";


    void setUpId() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(R.string.search);
        rangeView_age = (SimpleRangeView) findViewById(R.id.age_lebel);
        rangeView_height = (SimpleRangeView) findViewById(R.id.height_lebel);
        rangeView_color = (SimpleRangeView) findViewById(R.id.color_lebel);
        rangeView_education = (SimpleRangeView) findViewById(R.id.education_lebel);
        rangeView_health = (SimpleRangeView) findViewById(R.id.health_lebel);
        gridView = (MyGridView) findViewById(R.id.profession_grid);
        gridViewOccupation = (MyGridView) findViewById(R.id.occupation_grid);
        gridViewLocation = (MyGridView) findViewById(R.id.location_grid);
        findViewById(R.id.age_).setOnClickListener(this);
        findViewById(R.id.height_).setOnClickListener(this);
        findViewById(R.id.education_).setOnClickListener(this);
        findViewById(R.id.profession_).setOnClickListener(this);
        findViewById(R.id.occupation_).setOnClickListener(this);
        findViewById(R.id.color_).setOnClickListener(this);
        findViewById(R.id.body_).setOnClickListener(this);
        findViewById(R.id.location_).setOnClickListener(this);


        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new GetResult().execute();
                //porcessJSon();

            }


        });

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.age_:
                if (findViewById(R.id.age_container).getVisibility() == VISIBLE)
                    findViewById(R.id.age_container).setVisibility(GONE);
                else
                    findViewById(R.id.age_container).setVisibility(VISIBLE);
                break;
            case R.id.body_:
                if (findViewById(R.id.body_container).getVisibility() == VISIBLE)
                    findViewById(R.id.body_container).setVisibility(GONE);
                else
                    findViewById(R.id.body_container).setVisibility(VISIBLE);
                break;
            case R.id.color_:
                if (findViewById(R.id.color_container).getVisibility() == VISIBLE)
                    findViewById(R.id.color_container).setVisibility(GONE);
                else
                    findViewById(R.id.color_container).setVisibility(VISIBLE);
                break;
            case R.id.height_:
                if (findViewById(R.id.height_container).getVisibility() == VISIBLE)
                    findViewById(R.id.height_container).setVisibility(GONE);
                else
                    findViewById(R.id.height_container).setVisibility(VISIBLE);
                break;
            case R.id.education_:
                if (findViewById(R.id.education_container).getVisibility() == VISIBLE)
                    findViewById(R.id.education_container).setVisibility(GONE);
                else
                    findViewById(R.id.education_container).setVisibility(VISIBLE);
                break;

            case R.id.profession_:
                if (findViewById(R.id.profession_grid).getVisibility() == VISIBLE)
                    findViewById(R.id.profession_grid).setVisibility(GONE);
                else
                    findViewById(R.id.profession_grid).setVisibility(VISIBLE);
                break;
            case R.id.occupation_:
                if (findViewById(R.id.occupation_grid).getVisibility() == VISIBLE)
                    findViewById(R.id.occupation_grid).setVisibility(GONE);
                else
                    findViewById(R.id.occupation_grid).setVisibility(VISIBLE);
                break;

            case R.id.location_:
                if (findViewById(R.id.location_grid).getVisibility() == VISIBLE)
                    findViewById(R.id.location_grid).setVisibility(GONE);
                else
                    findViewById(R.id.location_grid).setVisibility(VISIBLE);
                break;

        }
    }

    class GetResult extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            reponse=ch;
            Toast.makeText(Search_Filter.this, ch, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(Search_Filter.this);
            final String token = sharePref.get_data("token");

            Log.e("Ovi Test", porcessJSon());

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, porcessJSon());
            Request request = new Request.Builder()
                    .url("http://test.biyeta.com/api/v1/search/filtered-results")
                    .addHeader("Authorization", "Token token=" + token)
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                ch = response.body().string();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return ch;
        }
    }


    //fetch data from
    class Get_Data extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            parse_data(res);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... url) {

            //get data from url
            Response response;
            SharePref sharePref = new SharePref(Search_Filter.this);
            String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Constant.BASE_URL + "search/user-preference")
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    JSONObject skin_color, health_options, education_options, professional_options, occupation_options;
    JSONArray location;
    JSONObject location_option;

    ArrayList<String> age_list = new ArrayList<>();

    void parse_data(String result) {
        String id, age, height, skin, health, marital_status, education, occupation, professional_group, user_id, gender;
        try {
            JSONObject jsonObject = new JSONObject(result);
            id = jsonObject.getJSONObject("preference").getString("id");
            age = jsonObject.getJSONObject("preference").getString("age");
            height = jsonObject.getJSONObject("preference").getString("height");
            skin = jsonObject.getJSONObject("preference").getString("skin");
            health = jsonObject.getJSONObject("preference").getString("health");
            marital_status = jsonObject.getJSONObject("preference").getString("marital_status");
            education = jsonObject.getJSONObject("preference").getString("education");
            occupation = jsonObject.getJSONObject("preference").getString("occupation");
            professional_group = jsonObject.getJSONObject("preference").getString("professional_group");
            user_id = jsonObject.getJSONObject("preference").getString("user_id");
            gender = jsonObject.getJSONObject("preference").getString("gender");

            skin_color = jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("skin_color");
            health_options = jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("skin_color");
            education_options = jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("education_options");
            professional_options = jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("professional_options");
            occupation_options = jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("occupation_options");

            location_option = jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("location_options");
            location = jsonObject.getJSONObject("preference").getJSONArray("location");

            HashMap<String, Boolean> is_occupation = new HashMap<>();


            for (int i = 0; i < skin_color.length(); i++) {
                skin_lebel.add(skin_color.getString("" + i));
            }


            ArrayList<String> profession_list = new ArrayList<>(), profession_number = new ArrayList<>();
            //   ArrayList<Boolean> is_checked_occupation = new ArrayList<>();


            ArrayList<String> occupation_list = new ArrayList<>();
            ArrayList<Boolean> is_checked_occupation = new ArrayList<>();


            //Load occupation data
            ArrayList<Integer> occuptationSelected = new ArrayList<>();
            for (int i = 0; i < new JSONArray(occupation).length(); i++) {

                int num = Integer.parseInt(String.valueOf(new JSONArray(occupation).get(i)));
                occuptationSelected.add(num);
                occupationGridItemCheckedCheckBoxPositionList.add(num);
            }

            JSONObject issueObj = occupation_options;

            Iterator iterator = issueObj.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                occupationId.add(Integer.parseInt(key));
                String issue = issueObj.getString(key);
                occupation_list.add(issue);
                if (occuptationSelected.contains(Integer.parseInt(key)))
                    is_checked_occupation.add(true);
                else
                    is_checked_occupation.add(false);
            }

            ///set Occupation Adapter
            Profession_Adapter occupation_adapter = new Profession_Adapter(getApplicationContext(), occupation_list, is_checked_occupation, "OCCUPATION");
            gridViewOccupation.setAdapter(occupation_adapter);


            ///Load Location data
            ArrayList<String> location_choose = new ArrayList<>();
            ArrayList<Boolean> is_checked_location = new ArrayList<>();
            ArrayList<String> all_location = new ArrayList<>();


            for (int i = 0; i < location.length(); i++) {
                locationGridItemCheckedCheckBoxPositionList.add(Integer.parseInt(location.getString(i)));
                location_choose.add(location.getString(i));

            }

            for (int i = 1; i < 65; i++) {
                //Log.e("fuck",location_option.getString(i+""));
                locationId.add(i);
                all_location.add(location_option.getString(i + "").toString());
                if (location_choose.contains(i + "")) {
                    Log.e("true", i + "");
                    is_checked_location.add(true);
                } else
                    is_checked_location.add(false);
            }

            Profession_Adapter locationAdapter = new Profession_Adapter(getApplicationContext(), all_location, is_checked_location, "LOCATION");
            gridViewLocation.setAdapter(locationAdapter);


            ///load the professional group data
            ArrayList<String> professionSelected=new ArrayList<>();

            ArrayList<Boolean> isCheckedProfession = new ArrayList<>();

            for (int i = 0; i < new JSONArray(professional_group).length(); i++) {

                int num = Integer.parseInt(String.valueOf(new JSONArray(professional_group).get(i)));
                professionId.add(num);
                professionSelected.add(num+"");
                professionGridItemCheckedCheckBoxPositionList.add(num);
            }

            JSONObject profObj = professional_options;

            Iterator iter = profObj.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                professionId.add(Integer.parseInt(key));
                String issue = profObj.getString(key);
                profession_list.add(issue);
                if (professionSelected.contains(Integer.parseInt(key)+""))
                    isCheckedProfession.add(true);
                else
                    isCheckedProfession.add(false);
            }





//            for (int i = 0; i < professional_options.length(); i++) {
//
//
//                profession_list.add(professional_options.getString((100 + i) + ""));
//                profession_number.add(100 + i + "");
//
//                professionId.add(100+i);
//
//                is_checked.add(false);
//
//            }
//            for (int i = 0; i < new JSONArray(professional_group).length(); i++) {
//                Log.e("come", new JSONArray(professional_group).get(i) + "");
//                int num = Integer.parseInt(String.valueOf(new JSONArray(professional_group).get(i)));
//                professionGridItemCheckedCheckBoxPositionList.add(100 + i);
//                num = num - 100;
//                is_checked.add(num, true);
//
//            }


            Profession_Adapter profession_adapter = new Profession_Adapter(getApplicationContext(), profession_list, isCheckedProfession, "PROFESSION");
            gridView.setAdapter(profession_adapter);


            for (int i = 0; i < health_options.length(); i++) {
                heightLebel.add(health_options.getString("" + i));
            }
            for (int i = 0; i < education_options.length(); i++) {
                education_lebel.add(education_options.getString("" + i));
            }


            for (int i = 0; i < education_options.length(); i++) {
                education_lebel.add(education_options.getString("" + i));
            }


            ///initialize age range view
            int startAgePosition = age_lebel.indexOf(new JSONArray(age).getString(0));
            int endAgePosition = age_lebel.indexOf(new JSONArray(age).getString(1));
            minAgeRangePos = startAgePosition;
            maxAgeRangePos = endAgePosition;
            rangeView_age.setStart(startAgePosition);
            rangeView_age.setEnd(endAgePosition);


            ///initialize height range view
            int i = Integer.parseInt(new JSONArray(height).getString(0));
            int res = i / 12;
            String ch = res + "'" + i % 12 + "\"";
            int startHeightPosition = heightLebel.indexOf(ch);
            rangeView_height.setStart(startHeightPosition);
            int j = Integer.parseInt(new JSONArray(height).getString(1));
            int res1 = j / 12;
            String ch1 = res1 + "'" + j % 12 + "\"";
            int endHeightPosition = heightLebel.indexOf(ch1);
            rangeView_height.setEnd(endHeightPosition);
            minHeightRangePos = startHeightPosition;
            maxHeightRangePos = endHeightPosition;

            //initialize color range bar view
            rangeView_color.setStart(Integer.parseInt(new JSONArray(skin).getString(0)));
            rangeView_color.setEnd(Integer.parseInt(new JSONArray(skin).getString(1)));
            minSkinRangePos = Integer.parseInt(new JSONArray(skin).getString(0));
            maxSkingRangePos = Integer.parseInt(new JSONArray(skin).getString(1));


            //initialize health range bar
            rangeView_health.setStart(Integer.parseInt(new JSONArray(health).getString(0)));
            rangeView_health.setEnd(Integer.parseInt(new JSONArray(health).getString(1)));
            minHealthRangePos = Integer.parseInt(new JSONArray(health).getString(0));
            maxHealthRangePos = Integer.parseInt(new JSONArray(health).getString(1));


            //initialize education range bar
            rangeView_education.setStart(Integer.parseInt(new JSONArray(education).getString(0)));
            rangeView_education.setEnd(Integer.parseInt(new JSONArray(education).getString(1)));
            minEducationRangePos = Integer.parseInt(new JSONArray(education).getString(0));
            maxEducationRangePos = Integer.parseInt(new JSONArray(education).getString(1));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}