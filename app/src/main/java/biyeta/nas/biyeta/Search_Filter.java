package biyeta.nas.biyeta;

/**
 * Created by user on 1/10/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import biyeta.nas.biyeta.Adapter.Profession_Adapter;
import biyeta.nas.biyeta.AppData.SharePref;
import biyeta.nas.biyeta.Constant.Constant;
import biyeta.nas.biyeta.Fragment.Search;
import biyeta.nas.biyeta.Model.Profile;
import biyeta.nas.biyeta.View.MyGridView;
import me.bendik.simplerangeview.SimpleRangeView;

import static me.bendik.simplerangeview.SimpleRangeView.*;

public class Search_Filter extends Activity  {


    SimpleRangeView rangeView_age,rangeView_height,rangeView_color,rangeView_education;
    EditText editStart;
    EditText editEnd;
    TextView textView;
    private String[] labels = new String[] {"L1","L2","L3","L4","L5","L6","L7","L8","L9","L10","L11"};

    private ArrayList<String> skin_lebel,age_lebel,health_lebel,education_lebel,professional_lebel,occupation_lebel;

    public ArrayList<String> age_lebels;
    MyGridView gridView;
    private final OkHttpClient client = new OkHttpClient();

    String[] skin_status=new String[]{
             "শ্যামলা",
            "উজ্জ্বল শ্যামলা",
             "ফর্সা",
             "অনেক ফর্সা"
    };

    String[] education_status=new String[]{

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
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_search);
        set_up_id();

        age_lebel=new ArrayList<>();
        health_lebel=new ArrayList<>();
        education_lebel=new ArrayList<>();
        professional_lebel=new ArrayList<>();
        occupation_lebel=new ArrayList<>();
        skin_lebel=new ArrayList<>();

        for (int i=18;i<=50;i++)
            age_lebel.add(i+"");

        for (int i=4;i<8;i++)
        {
            for (int j=0;j<13;j++)
                health_lebel.add(i+"'"+j+"\"");


        }

        textView=(TextView)findViewById(R.id.level);

        new Get_Data().execute();



       // fixedRangeView = (SimpleRangeView) findViewById(R.id.fixed_rangeview);
//        rangeView.setOnRangeLabelsListener(this);
//        rangeView.setOnTrackRangeListener(this);









//        rangeView.setActiveLineColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveThumbColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveLabelColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveThumbLabelColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveFocusThumbColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveFocusThumbAlpha(0.26f);



        //        rangeView.setActiveThumbColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveLabelColor(getResources().getColor(R.color.colorAccent));
      //  rangeView.setActiveThumbLabelColor(getResources().getColor(R.color.colorAccent));
     //   rangeView.setActiveFocusThumbColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveFocusThumbAlpha(0.26f);

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

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);

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

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);

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
        rangeView_color.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {


                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);

            }
        });

        rangeView_color.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return skin_status[i];
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

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);

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

    void set_up_id()
    {
        rangeView_age=(SimpleRangeView)findViewById(R.id.age_lebel);
        rangeView_height=(SimpleRangeView)findViewById(R.id.height_lebel);
        rangeView_color=(SimpleRangeView)findViewById(R.id.color_lebel);
        rangeView_education=(SimpleRangeView)findViewById(R.id.education_lebel);

        gridView=(MyGridView)findViewById(R.id.profession_grid);
    }

    //fetch data from
    class Get_Data extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            parse_data(res);
            ///Toast.makeText(Search_Filter.this,res,Toast.LENGTH_SHORT).show();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... url) {

            //get data from url
            Response response;
            SharePref sharePref=new SharePref(Search_Filter.this);
            String token=sharePref.get_data("token");
            Request request = new Request.Builder()
                        .url(Constant.BASE_URL+"search/user-preference")
                        .addHeader("Authorization", "Token token=" + token)
                        .build();
            try {
                response=client.newCall(request).execute();
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


    JSONObject skin_color,health_options,education_options,professional_options,occupation_options;

    ArrayList<String> age_list=new ArrayList<>();
    void parse_data(String result)
    {
        String id,age,height,skin,health,marital_status,education,occupation,professional_group,user_id,gender;
        try {
            JSONObject jsonObject=new JSONObject(result);
            id=jsonObject.getJSONObject("preference").getString("id");
            age=jsonObject.getJSONObject("preference").getString("age");
            height=jsonObject.getJSONObject("preference").getString("height");
            skin=jsonObject.getJSONObject("preference").getString("skin");
            health=jsonObject.getJSONObject("preference").getString("health");
            marital_status=jsonObject.getJSONObject("preference").getString("marital_status");
            education=jsonObject.getJSONObject("preference").getString("education");
            occupation=jsonObject.getJSONObject("preference").getString("occupation");
            professional_group=jsonObject.getJSONObject("preference").getString("professional_group");
            user_id=jsonObject.getJSONObject("preference").getString("user_id");
            gender=jsonObject.getJSONObject("preference").getString("gender");

            skin_color=jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("skin_color");
            health_options=jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("skin_color");
            education_options=jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("education_options");
            professional_options=jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("professional_options");
            occupation_options=jsonObject.getJSONObject("preference").getJSONObject("preference_constants").getJSONObject("occupation_options");



            for (int i=0;i<skin_color.length();i++)
            {
                Log.e("fuck",skin_color.getString(""+i));
                skin_lebel.add(skin_color.getString(""+i));
            }
            ArrayList<String> profession_list=new ArrayList<>(),profession_number=new ArrayList<>();
            ArrayList<Boolean> is_checked=new ArrayList<>();
            for (int i=0;i<professional_options.length();i++)
            {

                profession_list.add(professional_options.getString((100+i)+""));
                profession_number.add(100+i+"");
                is_checked.add(false);

            }
            for (int i=0;i<new JSONArray(professional_group).length();i++)
            {
                Log.e("come",new JSONArray(professional_group).get(i)+"");
                int num= Integer.parseInt(String.valueOf(new JSONArray(professional_group).get(i)));
                num=num-100;
                is_checked.add(num,true);
            }

            Profession_Adapter profession_adapter=new Profession_Adapter(getApplicationContext(),profession_list,is_checked);
            gridView.setAdapter(profession_adapter);





            for (int i=0;i<health_options.length();i++)
            {
                health_lebel.add(health_options.getString(""+i));
            }
            for (int i=0;i<education_options.length();i++)
            {
                education_lebel.add(education_options.getString(""+i));
            }
//            for (int i=0;i<professional_options.length();i++)
//            {
//                professional_lebel.add(professional_options.getString(""+i));
//            }

            for (int i=0;i<education_options.length();i++)
            {
                education_lebel.add(education_options.getString(""+i));
            }


            ///setup range view


            int start1=age_lebel.indexOf(new JSONArray(age).getString(0));
            int end1=age_lebel.indexOf(new JSONArray(age).getString(1));
            rangeView_age.setStart(start1);
            rangeView_age.setEnd(end1);
            int i= Integer.parseInt(new JSONArray(height).getString(0));
            int res=i/12;
            String ch=res+"'"+i%12+"\"";
            int start=health_lebel.indexOf(ch);
            rangeView_height.setStart(start);
            int j= Integer.parseInt(new JSONArray(height).getString(1));
            int res1=j/12;
            String ch1=res1+"'"+j%12+"\"";
            int end=health_lebel.indexOf(ch1);
            rangeView_height.setEnd(end);
            rangeView_color.setStart(Integer.parseInt(new JSONArray(skin).getString(0)));
            rangeView_color.setEnd(Integer.parseInt(new JSONArray(skin).getString(1)));


            rangeView_education.setStart(Integer.parseInt(new JSONArray(education).getString(0)));
            rangeView_education.setEnd(Integer.parseInt(new JSONArray(education).getString(1)));



            Log.e("fuck",Integer.parseInt(new JSONArray(skin).getString(0))+"        "+Integer.parseInt(new JSONArray(skin).getString(1)));













        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}