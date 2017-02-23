package com.nascenia.biyeta.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.model.UserProfile;
import com.nascenia.biyeta.model.UserProfileChild;

/**
 * Created by saiful on 2/7/17.
 */

public class UserProfileActivity extends AppCompatActivity {


    private RecyclerView userProfileInfoRecyclerView;
    private ArrayList<UserProfileChild> userProfileChildArrayList;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // setSampleDataonlist();

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

}
