package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.BiodataProfileAdapter;
import com.nascenia.biyeta.adapter.BiodatarequestFromMe;
import com.nascenia.biyeta.adapter.CommunicationRequestFromMeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.BioDataRequestFragment;
import com.nascenia.biyeta.fragment.CommunicationRequestFragment;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;
import com.nascenia.biyeta.model.communication_request_from_me.CommuncationRequestFromMeModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by god father on 3/21/2017.
 */

public class RequestSentFromMe extends CustomActionBarActivity {


    static int biodataNotificationCount = 0, connectionNotification = 0;
    static int communicationNotificationCount = 0;
    public final OkHttpClient client = new OkHttpClient();
    int position = 0;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView notificationNumberLeft, notificationNumberRight;
    private TabLayout tabLayout;
    private View tabItemView1, tabItemView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_sent_from_me);
        setUpToolBar(getString(R.string.your_sent_request), this);

        setUpId();

        new LoadBioDataConnection().execute(" http://test.biyeta.com/api/v1/requests/sent_profile_requests");


    }

    void setUpId() {
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar1);
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        setCustomLayoutOnTabItem();

    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }

    private void setCustomLayoutOnTabItem() {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tabItemView1 = inflater.inflate(R.layout.custom_tabbar_with_notification, null);

        tabLayout.getTabAt(0).setCustomView(tabItemView1);
        TextView tabTextTitleLeft = (TextView) tabItemView1.findViewById(R.id.text_tab);
        notificationNumberLeft = (TextView) tabItemView1.findViewById(R.id.badge_notification_4);
        tabTextTitleLeft.setText(getString(R.string.biodata));

        tabItemView2 = inflater.inflate(R.layout.custom_tabbar_with_notification, null);
        tabLayout.getTabAt(1).setCustomView(tabItemView2);
        notificationNumberLeft = (TextView) tabItemView1.findViewById(R.id.badge_notification_4);
        tabTextTitleLeft.setText(getString(R.string.biodata));

        TextView tabTextTitleRight = (TextView) tabItemView2.findViewById(R.id.text_tab);
        notificationNumberRight = (TextView) tabItemView2.findViewById(R.id.badge_notification_4);
        tabTextTitleRight.setText(getString(R.string.connection));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {

                    position = 0;
                    new LoadBioDataConnection().execute(" http://test.biyeta.com/api/v1/requests/sent_profile_requests");
                    //replaceFragment(new BioDataRequestFragment());
                } else {
                    position = 1;

                    new LoadBioDataConnection().execute("http://test.biyeta.com/api/v1/requests/sent_communication_requests");
                    //replaceFragment(new CommunicationRequestFragment());
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


    class LoadBioDataConnection extends AsyncTask<String, String, String> {

        Gson gson = new Gson();

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (position == 0) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (null == jsonObject.get("message")) {

                    }
                    else
                    {
                        findViewById(R.id.no_message).setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    Log.e("FUCK",e.toString());

                    progressBar.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    InputStream is = new ByteArrayInputStream(s.getBytes());
                    InputStreamReader isr = new InputStreamReader(is);
                    BiodataProfile response = gson.fromJson(isr, BiodataProfile.class);

                    BiodatarequestFromMe inboxListAdapter = new BiodatarequestFromMe(response, R.layout.biodata_request_from_me) {
                        @Override
                        public void onClickSmile(int id) {
                            Toast.makeText(RequestSentFromMe.this, id + " ", Toast.LENGTH_SHORT).show();
                        }
                    };
                    recyclerView.setAdapter(inboxListAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RequestSentFromMe.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    for (int i = 0; i < response.getProfiles().size(); i++) {
                        if (response.getProfiles().get(i).getRequestStatus().getAccepted() == false && response.getProfiles().get(i).getRequestStatus().getRejected() == false) {
                        } else
                            biodataNotificationCount++;
                    }

                    notificationNumberLeft.setText(biodataNotificationCount + "");
                    findViewById(R.id.no_message).setVisibility(View.GONE);

                }

            } else {


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (null == jsonObject.getJSONArray("message")) {
                        InputStream is = new ByteArrayInputStream(s.getBytes());
                        InputStreamReader isr = new InputStreamReader(is);
                        CommuncationRequestFromMeModel response = gson.fromJson(isr, CommuncationRequestFromMeModel.class);

                        CommunicationRequestFromMeAdapter communicationRequestFromMeAdapter = new CommunicationRequestFromMeAdapter(response, R.layout.communication_request_sent_from_me_item);
                        recyclerView.setAdapter(communicationRequestFromMeAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RequestSentFromMe.this));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        findViewById(R.id.no_message).setVisibility(View.GONE);
                    } else {

                        findViewById(R.id.no_message).setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                for (int i = 0; i < response.getProfiles().size(); i++) {
//                    if (response.getProfiles().get(i).getRequestStatus().getAccepted() == false && response.getProfiles().get(i).getRequestStatus().getRejected() == false) {
//                    } else
//                        connectionNotification++;
//                }

                notificationNumberRight.setText(connectionNotification + "");


            }


        }

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            Response response;
            SharePref sharePref = new SharePref(RequestSentFromMe.this);
            String token = sharePref.get_data("token");
            Request request = null;

            request = new Request.Builder()
                    .url(url)
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
}
