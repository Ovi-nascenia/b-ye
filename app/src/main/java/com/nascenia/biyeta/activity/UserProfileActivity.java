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
import java.util.Iterator;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by saiful on 2/7/17.
 */

public class UserProfileActivity extends AppCompatActivity {

    private static final String SUB_URL = "profiles/";


    private RecyclerView userProfileInfoRecyclerView;
    TextView aboutMeTextView;
    private ArrayList<UserProfileChild> userProfileChildArrayList;
    private final OkHttpClient client = new OkHttpClient();
    String id;

    private Toolbar toolbar;

    private ArrayList<UserProfileChild> childItemList;
    private ArrayList<UserProfile> userProfilesList;
    HashMap<String,String> hashMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        id = getIntent().getExtras().getString("id");

        loadUserData();
        hashMap=new HashMap<>();
        aboutMeTextView=(TextView)findViewById(R.id.userProfileDescriptionText);
        setTitle(getIntent().getExtras().getString("user_name"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userProfilesList = new ArrayList<UserProfile>();



        userProfileInfoRecyclerView = (RecyclerView) findViewById(R.id.user_details_recycler_view);
        userProfileInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    }




    public void loadUserData() {
        new GetData().execute();
    }

    ArrayList<PartialProfileItemModel> listPartialProfile;
    class GetData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String responseJson) {
            super.onPostExecute(responseJson);
            //  Toast.makeText(UserProfileActivity.this, responseJson, Toast.LENGTH_SHORT).show();

            try {
                JSONObject parentJsonObj = new JSONObject(responseJson).getJSONObject("profile");
                boolean valuee=parentJsonObj.getJSONObject("request_status").getBoolean("accepted");
                if (!valuee)
                {
                    listPartialProfile=new ArrayList<>();
                    Toast.makeText(UserProfileActivity.this,"load partial profile"+valuee,Toast.LENGTH_SHORT).show();


                    aboutMeTextView.setText(parentJsonObj.getJSONObject("personal_information").getString("about_yourself"));
                    hashMap.put(getString(R.string.profession_text),parentJsonObj.getJSONObject("profession").getString("occupation"));
                    hashMap.put(getString(R.string.present_loaction_text),parentJsonObj.getJSONObject("profile_living_in").getString("country"));
                    hashMap.put(getString(R.string.home_town),parentJsonObj.getJSONObject("profile_living_in").getString("location"));
                    hashMap.put(getString(R.string.height_text),parentJsonObj.getJSONObject("personal_information").getString("height_ft")+"' "+parentJsonObj.getJSONObject("personal_information").getString("height_inc")+"''");
                    hashMap.put(getString(R.string.religion_text),parentJsonObj.getJSONObject("profile_religion").getString("religion"));
                    hashMap.put(getString(R.string.degree_name_text),parentJsonObj.getJSONObject("education_information").getString("highest_degree"));

                    for ( String key : hashMap.keySet() ) {

                        PartialProfileItemModel partialProfileItemModel=new PartialProfileItemModel(key,hashMap.get(key));
                        listPartialProfile.add(partialProfileItemModel);
                    }

                    PartialProfileViewAdapter partialProfileViewAdapter=new PartialProfileViewAdapter(listPartialProfile);
                    userProfileInfoRecyclerView.setAdapter(partialProfileViewAdapter);

                }
                else {

                    Toast.makeText(UserProfileActivity.this,"load full profile"+valuee,Toast.LENGTH_SHORT).show();

                    Iterator iterator = parentJsonObj.keys();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String value = parentJsonObj.getString(key);


                        Object json = new JSONTokener(value).nextValue();
                        if (json instanceof JSONObject) {
                            prepareRecylerViewItemData(parentJsonObj.getJSONObject(key), key);
                        } else if (json instanceof JSONArray) {
                            prepareRecylerViewItemData(parentJsonObj.getJSONArray(key), key);

                        } else {
                        }


                    }
                    userProfileInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(), userProfilesList));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }





        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... url) {
        Response response;
            SharePref sharePref = new SharePref(UserProfileActivity.this);
            String token = sharePref.get_data("token");
            Request request = null;

            request = new Request.Builder()
                    .url(Constant.BASE_URL + SUB_URL + id)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
            }

            return null;
        }
    }

    private void prepareRecylerViewItemData(JSONObject childJsonObject, String key) throws Exception {
        childItemList = new ArrayList<UserProfileChild>();

        Iterator iterator = childJsonObject.keys();
        while (iterator.hasNext()) {
            String childKey = (String) iterator.next();
            String childValue = childJsonObject.getString(childKey);

              childItemList.add(new UserProfileChild(childKey, childValue));


        }


         userProfilesList.add(new UserProfile(key, childItemList));

    }

    private void prepareRecylerViewItemData(JSONArray childJsonArray, String key) throws Exception {
        childItemList = new ArrayList<UserProfileChild>();
        String childItemName = "";
        String childNodeValue = "";

        switch (key) {
            case "family_members":
                childItemName = "relation";
                break;
            case "education_information":
                childItemName = "education";
                break;
        }


        for (int n = 0; n < childJsonArray.length(); n++) {
            JSONObject object = childJsonArray.getJSONObject(n);

           for (Iterator<String> iter = object.keys(); iter.hasNext(); ) {
                String childKey = iter.next();

                childNodeValue += object.getString(childKey);
                Log.i("jsonarraykey", childKey);
            }
            childItemList.add(new UserProfileChild(childItemName, childNodeValue));
            childNodeValue = "";

            Log.i("jsonarraykey", "-------------------------");
        }

        userProfilesList.add(new UserProfile(key, childItemList));
    }

}