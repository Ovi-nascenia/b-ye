package com.nascenia.biyeta.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
    private ArrayList<UserProfileChild> userProfileChildArrayList;
    private final OkHttpClient client = new OkHttpClient();
    String id;

    private Toolbar toolbar;

    private ArrayList<UserProfileChild> childItemList;
    private ArrayList<UserProfile> userProfilesList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        id = getIntent().getExtras().getString("id");

        loadUserData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userProfilesList = new ArrayList<UserProfile>();

      /*  UserProfile ageProfile = new UserProfile("Age", Arrays.asList(new UserProfileChild("age", "23 year"), new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9"),
                new UserProfileChild("Height", "4.9")));

        ArrayList<UserProfileChild> list = new ArrayList<UserProfileChild>();
        list.add(new UserProfileChild("education", "Hsc"));
        //  UserProfile educationProfile = new UserProfile("Education", Arrays.asList(new UserProfileChild("education", "Hsc")));
        UserProfile educationProfile = new UserProfile("Education", list);

        UserProfile professionProfile = new UserProfile("Profession", Arrays.asList(new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee"),
                new UserProfileChild("profession", "Employee")));
        List<UserProfile> userProfilesList = Arrays.asList(ageProfile, educationProfile, professionProfile);*/


        userProfileInfoRecyclerView = (RecyclerView) findViewById(R.id.user_details_recycler_view);
        userProfileInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        //  userProfileInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(), userProfilesList));


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


    public void loadUserData() {
        new GetData().execute();
    }


    class GetData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String responseJson) {
            super.onPostExecute(responseJson);
            //  Toast.makeText(UserProfileActivity.this, responseJson, Toast.LENGTH_SHORT).show();

            try {
                //JSONObject jsonObject = new JSONObject(responseJson);
                JSONObject parentJsonObj = new JSONObject(responseJson).getJSONObject("profile");

                // JSONObject personalInformationObj = parentJsonObj.getJSONObject("personal_information");
                //JSONObject professionObj = parentJsonObj.getJSONObject("profession");


                Iterator iterator = parentJsonObj.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = parentJsonObj.getString(key);

                    //  get id from  issue
                    //String _pubKey = issue.optString("id");
                    //Log.i("jsonvalue", " key-> " + key + " " + value);


                    Object json = new JSONTokener(value).nextValue();
                    if (json instanceof JSONObject) {
                        //  Log.i("jsonvalue", "obj  " + key + "  " + value);
                        prepareRecylerViewItemData(parentJsonObj.getJSONObject(key), key);
                    } else if (json instanceof JSONArray) {
                        // Log.i("jsonvalue", "array " + key + "  " + value);
                        prepareRecylerViewItemData(parentJsonObj.getJSONArray(key), key);

                    } else {
                        // Log.i("jsonvalue", key + "  " + value);
                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            userProfileInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(), userProfilesList));


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
                    .url(Constant.BASE_URL + SUB_URL + 316)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                /*JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();*/
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

            //childItemList.add(new UserProfileChild(Constant.profileItemBanglaName(childKey), childValue));
            childItemList.add(new UserProfileChild(childKey, childValue));


        }


        //userProfilesList.add(new UserProfile(Constant.profileItemBanglaName(key), childItemList));
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

            // do some stuff....
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
