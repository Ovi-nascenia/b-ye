package biyeta.nas.biyeta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

import biyeta.nas.biyeta.Adapter.UserProfileRecyclerAdapter;
import biyeta.nas.biyeta.Model.UserProfileInfo;

/**
 * Created by saiful on 2/7/17.
 */

public class UserProfileActivity extends AppCompatActivity {


    private RecyclerView userProfileInfoRecyclerView;
    private ArrayList<UserProfileInfo> userProfileInfoArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userProfileInfoRecyclerView = (RecyclerView) findViewById(R.id.user_details_recycler_view);


        userProfileInfoArrayList = new ArrayList<>();
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));
        userProfileInfoArrayList.add(new UserProfileInfo("age", "23 year"));


        userProfileInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        userProfileInfoRecyclerView.setAdapter(new UserProfileRecyclerAdapter(getBaseContext(), userProfileInfoArrayList));


    }

}
