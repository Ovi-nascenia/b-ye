package com.nascenia.biyeta.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
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
    String id;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        id=getIntent().getExtras().getString("id");




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
        userProfileInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(), userProfilesList));


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


    class GetData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            Toast.makeText(UserProfileActivity.this,res,Toast.LENGTH_SHORT).show();




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
