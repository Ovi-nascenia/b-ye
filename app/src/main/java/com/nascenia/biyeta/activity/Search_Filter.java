package com.nascenia.biyeta.activity;

/**
 * Created by user on 1/10/2017.
 */

import android.app.Activity;
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

    private ArrayList<String> skin_lebel, age_lebel, health_lebel, education_lebel, professional_lebel, occupation_lebel;

    public ArrayList<String> age_lebels;
    MyGridView gridView;
    MyGridView gridView_occupation, gridView_location;
    private final OkHttpClient client = new OkHttpClient();


    public static ArrayList<Integer> locationGridItemCheckedCheckBoxPositionList;
    public static ArrayList<Integer> professionGridItemCheckedCheckBoxPositionList;
    public static ArrayList<Integer> occupationGridItemCheckedCheckBoxPositionList;


    String[] skin_status = new String[]{
            "শ্যামলা",
            "উজ্জ্বল শ্যামলা",
            "ফর্সা",
            "অনেক ফর্সা"
    };

    String[] health_status = new String[]{
            "স্লিম ",
            "স্বাস্থ্যবান",
            "বেশ স্বাস্থ্যব"
    };

    String[] education_status = new String[]{

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
        set_up_id();

        age_lebel = new ArrayList<>();
        health_lebel = new ArrayList<>();
        education_lebel = new ArrayList<>();
        professional_lebel = new ArrayList<>();
        occupation_lebel = new ArrayList<>();
        skin_lebel = new ArrayList<>();

        for (int i = 18; i <= 50; i++)
            age_lebel.add(i + "");

        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 13; j++)
                health_lebel.add(i + "'" + j + "\"");


        }

        textView = (TextView) findViewById(R.id.level);

        new Get_Data().execute();
        set_rangeView_lebel();

    }

    private void set_rangeView_lebel() {

        rangeView_age.setLabelFontSize(25);
        rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_age.setLabelColor(Color.TRANSPARENT);
        rangeView_age.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_age.setActiveThumbLabelColor(Color.BLACK);
        rangeView_age.setTickRadius(0.0f);

        rangeView_age.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);
                rangeView_age.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_age.setActiveThumbLabelColor(Color.BLACK);

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);
                rangeView_age.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_age.setActiveThumbLabelColor(Color.BLACK);
            }
        });

        rangeView_age.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return age_lebel.get(i);
            }
        });

        rangeView_height.setLabelFontSize(25);
        rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_height.setLabelColor(Color.TRANSPARENT);
        rangeView_height.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_height.setActiveThumbLabelColor(Color.BLACK);
        rangeView_height.setTickRadius(0.0f);


        rangeView_height.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);
                rangeView_height.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_height.setActiveThumbLabelColor(Color.BLACK);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);
                rangeView_height.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_height.setActiveThumbLabelColor(Color.BLACK);
            }
        });
        rangeView_height.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return health_lebel.get(i);
            }
        });

        rangeView_color.setLabelFontSize(14);
        rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_color.setLabelColor(Color.TRANSPARENT);
        rangeView_color.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_color.setActiveThumbLabelColor(Color.BLACK);
        rangeView_color.setTickRadius(0.0f);

        rangeView_color.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_color.setActiveThumbLabelColor(Color.BLACK);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_color.setActiveThumbLabelColor(Color.BLACK);

            }
        });
        rangeView_color.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return skin_status[i];
            }
        });

        rangeView_health.setLabelFontSize(17);
        rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_health.setLabelColor(Color.TRANSPARENT);
        rangeView_health.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_health.setActiveThumbLabelColor(Color.BLACK);
        rangeView_health.setTickRadius(0.0f);


        rangeView_health.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {


                rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_health.setLabelColor(Color.TRANSPARENT);
                rangeView_health.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_health.setActiveThumbLabelColor(Color.BLACK);

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_health.setLabelColor(Color.TRANSPARENT);
                rangeView_health.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_health.setActiveThumbLabelColor(Color.BLACK);

            }
        });

        rangeView_health.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return health_status[i];
            }
        });

        rangeView_education.setLabelFontSize(18);
        rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_education.setLabelColor(Color.TRANSPARENT);
        rangeView_education.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_education.setActiveThumbLabelColor(Color.BLACK);
        rangeView_education.setTickRadius(0.0f);


        rangeView_education.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_education.setActiveThumbLabelColor(Color.BLACK);

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedLabelColor(Color.TRANSPARENT);
                rangeView_education.setActiveThumbLabelColor(Color.BLACK);

            }
        });

        rangeView_education.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return education_status[i];
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

    void set_up_id() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        gridView_occupation = (MyGridView) findViewById(R.id.occupation_grid);
        gridView_location = (MyGridView) findViewById(R.id.location_grid);
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


                new Get_Data1().execute();


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

    class Get_Data1 extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Search_Filter.this, ch, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(Search_Filter.this);
            final String token = sharePref.get_data("token");

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, res);
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
            for (int i = 0; i < new JSONArray(occupation).length(); i++) {
                Log.e("TAQ", new JSONArray(occupation).get(i).toString());
                is_occupation.put(new JSONArray(occupation).get(i).toString(), true);
            }

            ArrayList<String> profession_list = new ArrayList<>(), profession_number = new ArrayList<>();
            ArrayList<String> occupation_list = new ArrayList<>();
            ArrayList<Boolean> is_checked_occupation = new ArrayList<>();
            Iterator<String> iter = occupation_options.keys();
            while (iter.hasNext()) {

                String key = iter.next();
                try {
                    String value = (String) occupation_options.get(key);

                    Log.e("TAQ", value + "      " + key);
                    occupation_list.add(value);
                    // is_occupation.put(key,false);
                    try {
                        if (is_occupation.get(key))
                            is_checked_occupation.add(true);

                        else
                            is_checked_occupation.add(false);
                    } catch (NullPointerException n) {
                        is_checked_occupation.add(false);
                    }

                } catch (Exception e) {

                }
            }


            ArrayList<String> location_choose = new ArrayList<>();
            ArrayList<Boolean> is_checked_location = new ArrayList<>();
            ArrayList<String> all_location = new ArrayList<>();


            for (int i = 0; i < location.length(); i++) {
                Log.e("fuck", location.getString(i));
                location_choose.add(location.getString(i));
            }

            for (int i = 1; i < 65; i++) {
                //Log.e("fuck",location_option.getString(i+""));
                all_location.add(location_option.getString(i + "").toString());
                if (location_choose.contains(i + "")) {
                    Log.e("true", i + "");
                    is_checked_location.add(true);
                } else
                    is_checked_location.add(false);
            }

            Profession_Adapter profession_adapter1 = new Profession_Adapter(getApplicationContext(), all_location, is_checked_location, "LOCATION");
            gridView_location.setAdapter(profession_adapter1);


            ArrayList<Boolean> is_checked = new ArrayList<>();
            for (int i = 0; i < professional_options.length(); i++) {

                profession_list.add(professional_options.getString((100 + i) + ""));
                profession_number.add(100 + i + "");
                is_checked.add(false);

            }
            for (int i = 0; i < new JSONArray(professional_group).length(); i++) {
                Log.e("come", new JSONArray(professional_group).get(i) + "");
                int num = Integer.parseInt(String.valueOf(new JSONArray(professional_group).get(i)));
                num = num - 100;
                is_checked.add(num, true);
            }

            Profession_Adapter profession_adapter2 = new Profession_Adapter(getApplicationContext(), occupation_list, is_checked_occupation, "OCCUPATION");
            gridView_occupation.setAdapter(profession_adapter2);


            Profession_Adapter profession_adapter = new Profession_Adapter(getApplicationContext(), profession_list, is_checked, "PROFESSION");
            gridView.setAdapter(profession_adapter);


           /* gridView_occupation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });*/


            for (int i = 0; i < health_options.length(); i++) {
                health_lebel.add(health_options.getString("" + i));
            }
            for (int i = 0; i < education_options.length(); i++) {
                education_lebel.add(education_options.getString("" + i));
            }
//            for (int i=0;i<professional_options.length();i++)
//            {
//                professional_lebel.add(professional_options.getString(""+i));
//            }

            for (int i = 0; i < education_options.length(); i++) {
                education_lebel.add(education_options.getString("" + i));
            }


            ///setup range view


            int start1 = age_lebel.indexOf(new JSONArray(age).getString(0));
            int end1 = age_lebel.indexOf(new JSONArray(age).getString(1));
            rangeView_age.setStart(start1);
            rangeView_age.setEnd(end1);
            int i = Integer.parseInt(new JSONArray(height).getString(0));
            int res = i / 12;
            String ch = res + "'" + i % 12 + "\"";
            int start = health_lebel.indexOf(ch);
            rangeView_height.setStart(start);
            int j = Integer.parseInt(new JSONArray(height).getString(1));
            int res1 = j / 12;
            String ch1 = res1 + "'" + j % 12 + "\"";
            int end = health_lebel.indexOf(ch1);
            rangeView_height.setEnd(end);
            rangeView_color.setStart(Integer.parseInt(new JSONArray(skin).getString(0)));
            rangeView_color.setEnd(Integer.parseInt(new JSONArray(skin).getString(1)));
            rangeView_health.setStart(Integer.parseInt(new JSONArray(health).getString(0)));
            rangeView_health.setEnd(Integer.parseInt(new JSONArray(health).getString(1)));
            rangeView_education.setStart(Integer.parseInt(new JSONArray(education).getString(0)));
            rangeView_education.setEnd(Integer.parseInt(new JSONArray(education).getString(1)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}