package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.fragment.BioDataRequestFragment;
import com.nascenia.biyeta.fragment.CommunicationRequestFragment;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;

public class SendRequestActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private LinearLayout container;

    private View tabItemView1, tabItemView2;
    private Bundle bundle;
    private RequestSenderIds requestSenderIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        container = (LinearLayout) findViewById(R.id.fragment_container);


        //create tabs title
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());


        try {

            if (getIntent().getStringExtra("REQUEST_RESPONSE_DATA") != null) {
                requestSenderIds = new Gson().fromJson(
                        getIntent().getStringExtra("REQUEST_RESPONSE_DATA"), RequestSenderIds.class);

            }
        } catch (Exception e) {

        }

        bundle = new Bundle();
        bundle.putSerializable("REQUEST_RESPONSE_OBJ", requestSenderIds);

        setCustomLayoutOnTabItem();

        //replace default fragment
        replaceFragment(new BioDataRequestFragment());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    replaceFragment(new BioDataRequestFragment());
                } else {
                    replaceFragment(new CommunicationRequestFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setCustomLayoutOnTabItem() {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //biodata tab view
        tabItemView1 = inflater.inflate(R.layout.custom_tab_item1, null);
        TextView biodataNotificationCounterTextview = (TextView) tabItemView1.
                findViewById(R.id.biodata_notification_textview);

        if (requestSenderIds.getRequests().getProfileRequestCount() > 0) {
            biodataNotificationCounterTextview.setVisibility(View.VISIBLE);
            biodataNotificationCounterTextview.setText(
                    requestSenderIds.getRequests().getProfileRequestCount() + "");

        }

        tabLayout.getTabAt(0).setCustomView(tabItemView1);


        //communication tab view
        tabItemView2 = inflater.inflate(R.layout.custom_tab_item2, null);
        TextView communicationNotificationCounterTextview = (TextView) tabItemView2.
                findViewById(R.id.communication_notification_textview);

        if (requestSenderIds.getRequests().getCommunicationRequestCount() > 0) {
            communicationNotificationCounterTextview.setVisibility(View.VISIBLE);
            communicationNotificationCounterTextview.setText(
                    requestSenderIds.getRequests().getCommunicationRequestCount() + "");
        }
        tabLayout.getTabAt(1).setCustomView(tabItemView2);


    }


    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

}
