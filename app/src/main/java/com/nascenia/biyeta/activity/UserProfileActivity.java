package com.nascenia.biyeta.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.PartialProfileViewAdapter;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.model.PartialProfileItemModel;
import com.nascenia.biyeta.model.UserProfile;
import com.nascenia.biyeta.model.UserProfileChild;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saiful on 2/7/17.
 */

public class UserProfileActivity extends AppCompatActivity {

    private static final String SUB_URL="profiles/";


    private RecyclerView userProfileInfoRecyclerView;
    private ArrayList<UserProfileChild> userProfileChildArrayList;
    private final OkHttpClient client = new OkHttpClient();
    HashMap<String,String> hashMap;
    String id;
    TextView aboutMeTextView;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        id=getIntent().getExtras().getString("id");
        hashMap=new HashMap<>();
        aboutMeTextView=(TextView)findViewById(R.id.userProfileDescriptionText);
        setTitle(getIntent().getExtras().getString("user_name"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });




        loadUserData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserProfile ageProfile = new UserProfile("Age", Arrays.asList(new UserProfileChild("age", "23 year"), new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9")));


        UserProfile educationProfile = new UserProfile("Education", Arrays.asList(new UserProfileChild("education", "Hsc")));
        UserProfile professionProfile = new UserProfile("Profession", Arrays.asList(new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee")));
        List<UserProfile> userProfilesList = Arrays.asList(ageProfile, educationProfile, professionProfile);
        // List<UserProfile> userProfilesList = Arrays.asList(ageProfile);

       userProfileInfoRecyclerView = (RecyclerView) findViewById(R.id.user_details_recycler_view);
         userProfileInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        userProfileInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(), userProfilesList));


    }

    private void setSampleDataonlist() {


        userProfileChildArrayList = new ArrayList<>();
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));
        userProfileChildArrayList.add(new UserProfileChild("age", "23 year"));

    }


    public  void  loadUserData()
    {
       new GetData().execute();
    }

    ArrayList<PartialProfileItemModel> listPartialProfile;


    class GetData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
          //  findViewById(R.id.pbProcessing).setVisibility(View.GONE);


            try {
                JSONObject jsonObject=new JSONObject(res).getJSONObject("profile");
                Log.e("ob",jsonObject.toString());
                boolean value=jsonObject.getJSONObject("request_status").getBoolean("accepted");
                if (value)
                {
                    Toast.makeText(UserProfileActivity.this,"load full profile",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    listPartialProfile=new ArrayList<>();
                    Toast.makeText(UserProfileActivity.this,"load partial profile",Toast.LENGTH_SHORT).show();


                    aboutMeTextView.setText(jsonObject.getJSONObject("personal_information").getString("about_yourself"));
                    hashMap.put(getString(R.string.profession_text),jsonObject.getJSONObject("profession").getString("occupation"));
                    hashMap.put(getString(R.string.present_loaction_text),jsonObject.getJSONObject("profile_living_in").getString("country"));
                    hashMap.put(getString(R.string.home_town),jsonObject.getJSONObject("profile_living_in").getString("location"));
                    hashMap.put(getString(R.string.height_text),jsonObject.getJSONObject("personal_information").getString("height_ft")+"' "+jsonObject.getJSONObject("personal_information").getString("height_inc")+"''");
                    hashMap.put(getString(R.string.religion_text),jsonObject.getJSONObject("profile_religion").getString("religion"));
                    hashMap.put(getString(R.string.degree_name_text),jsonObject.getJSONObject("education_information").getString("highest_degree"));

                    for ( String key : hashMap.keySet() ) {

                        PartialProfileItemModel partialProfileItemModel=new PartialProfileItemModel(key,hashMap.get(key));
                        listPartialProfile.add(partialProfileItemModel);
                    }

                    PartialProfileViewAdapter partialProfileViewAdapter=new PartialProfileViewAdapter(listPartialProfile);
                    userProfileInfoRecyclerView.setAdapter(partialProfileViewAdapter);





                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //    Toast.makeText(UserProfileActivity.this,res,Toast.LENGTH_SHORT).show();




        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... url) {

            //get data from url
            Response response;
            SharePref sharePref = new SharePref(UserProfileActivity.this);
            String token = sharePref.get_data("token");
            Request request = null;

                request = new Request.Builder()
                        .url(Constant.BASE_URL+SUB_URL+id)
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

}
