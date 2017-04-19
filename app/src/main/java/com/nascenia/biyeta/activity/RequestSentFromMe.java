package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.Intent;
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
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.BiodataProfileAdapter;
import com.nascenia.biyeta.adapter.BiodatarequestFromMe;
import com.nascenia.biyeta.adapter.CommunicationRequestFromMeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.BioDataRequestFragment;
import com.nascenia.biyeta.fragment.CommunicationRequestFragment;
import com.nascenia.biyeta.fragment.RecyclerItemClickListener;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;
import com.nascenia.biyeta.model.communication_request_from_me.CommuncationRequestFromMeModel;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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

        new LoadBioDataConnection().execute(Utils.Base_URL + "/api/v1/requests/sent_profile_requests");


    }

    void setUpId() {
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar1);
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        setCustomLayoutOnTabItem();


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (tabLayout.getSelectedTabPosition() == 0) {


                } else {


                }


            }
        }));

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
                    new LoadBioDataConnection().execute(Utils.Base_URL + "/api/v1/requests/sent_profile_requests");
                    //replaceFragment(new BioDataRequestFragment());
                } else {
                    position = 1;

                    new LoadBioDataConnection().execute(Utils.Base_URL + "/api/v1/requests/sent_communication_requests");
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


    BiodatarequestFromMe biodataFromMeAdapter;
    BiodataProfile biodataResponse;
    CommuncationRequestFromMeModel communicationResponse;
    CommunicationRequestFromMeAdapter communicationRequestFromMeAdapter;


    class LoadBioDataConnection extends AsyncTask<String, String, String> {

        Gson gson = new Gson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ////Toast.makeText(RequestSentFromMe.this,s,//Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            if (s == null) {
                Utils.ShowAlert(RequestSentFromMe.this, getString(R.string.no_internet_connection));
            } else {

                if (position == 0) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        if (jsonObject.has("message")) {
                            //Toast.makeText(RequestSentFromMe.this,"null",//Toast.LENGTH_SHORT).show();
                            findViewById(R.id.no_message).setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.no_message).setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Gson gson = new Gson();
                            InputStream is = new ByteArrayInputStream(s.getBytes());
                            InputStreamReader isr = new InputStreamReader(is);
                            biodataResponse = gson.fromJson(isr, BiodataProfile.class);

                            biodataFromMeAdapter = new BiodatarequestFromMe(biodataResponse, R.layout.biodata_request_from_me) {
                                @Override
                                public void onClickSmile(int id, int position) {

                                    new SendSmile().execute(Utils.Base_URL + "/api/v1/smiles", id + "", position + "");

                                    //Toast.makeText(RequestSentFromMe.this, id + " ", //Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onClickItem(int id, int position) {
                                    Intent intent = new Intent(RequestSentFromMe.this, NewUserProfileActivity.class);
                                    intent.putExtra("id", biodataResponse.getProfiles().get(position).getId() + "");
                                    intent.putExtra("user_name", biodataResponse.getProfiles().get(position).getDisplayName());
                                    intent.putExtra("PROFILE_EDIT_OPTION", false);
                                    startActivity(intent);

                                }


                            };
                            recyclerView.setAdapter(biodataFromMeAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(RequestSentFromMe.this));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            for (int i = 0; i < biodataResponse.getProfiles().size(); i++) {
                                if (biodataResponse.getProfiles().get(i).getRequestStatus().getAccepted() == false && biodataResponse.getProfiles().get(i).getRequestStatus().getRejected() == false) {
                                } else
                                    biodataNotificationCount++;
                            }

                            notificationNumberLeft.setText(
                                    Utils.convertEnglishYearDigittoBangla(biodataNotificationCount) + "");
                            findViewById(R.id.no_message).setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {


                    }

                } else {


                    try {
                        JSONObject jsonObject = new JSONObject(s);


                        if (jsonObject.has("message")) {
                            //Toast.makeText(RequestSentFromMe.this,"null",//Toast.LENGTH_SHORT).show();
                            findViewById(R.id.no_message).setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.no_message).setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            //Toast.makeText(RequestSentFromMe.this,"Not null",//Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RequestSentFromMe.this, s, //Toast.LENGTH_SHORT).show();
                            InputStream is = new ByteArrayInputStream(s.getBytes());
                            InputStreamReader isr = new InputStreamReader(is);
                            communicationResponse = gson.fromJson(isr, CommuncationRequestFromMeModel.class);

                            communicationRequestFromMeAdapter = new CommunicationRequestFromMeAdapter(communicationResponse, R.layout.communication_request_sent_from_me_item) {
                                @Override
                                public void onMakeConnection(int id, int position) {

                                    new SendConnection().execute(Utils.Base_URL + "/api/v1/communication_requests", id + "", position + "");

                                }

                                @Override
                                public void onClickProfile(int id, int position) {

                                    Intent intent = new Intent(RequestSentFromMe.this, NewUserProfileActivity.class);
                                    intent.putExtra("id", communicationResponse.getProfiles().get(position).getId() + "");
                                    intent.putExtra("user_name", communicationResponse.getProfiles().get(position).getDisplayName());
                                    intent.putExtra("PROFILE_EDIT_OPTION", false);
                                    startActivity(intent);

                                }
                            };
                            recyclerView.setAdapter(communicationRequestFromMeAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(RequestSentFromMe.this));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            findViewById(R.id.no_message).setVisibility(View.GONE);
                        }


                    } catch (JSONException e) {

                    }


//                for (int i = 0; i < response.getProfiles().size(); i++) {
//                    if (response.getProfiles().get(i).getRequestStatus().getAccepted() == false && response.getProfiles().get(i).getRequestStatus().getRejected() == false) {
//                    } else
//                        connectionNotification++;
//                }

                    notificationNumberRight.setText(
                            Utils.convertEnglishYearDigittoBangla(connectionNotification) + "");


                }
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


    public class SendSmile extends AsyncTask<String, String, String> {


        int position;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("SmileResponse", s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.has("message")) {
                    biodataResponse.getProfiles().get(position).setSmileSent(true);
                    biodataFromMeAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            Log.e("Smile", strings[0] + " " + strings[1]);
            position = Integer.parseInt(strings[2]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", strings[1])
                    .build();


            Response response;
            SharePref sharePref = new SharePref(RequestSentFromMe.this);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
                return null;

            }
        }
    }


    class SendConnection extends AsyncTask<String, String, String> {

        int listposition;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("message")) {
                    ///  Toast.makeText(RequestSentFromMe.this, " sent", Toast.LENGTH_SHORT).show();
                    communicationResponse.getProfiles().get(listposition).getRequestStatus().setExpired(false);
                    communicationResponse.getProfiles().get(listposition).getRequestStatus().setMessage("আপনি যোগাযোগের  অনুরোধ  করেছেন");

                    communicationRequestFromMeAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            //  Log.e(" id", strings[1]);

            Integer id = Integer.parseInt(strings[1]);
            listposition = Integer.parseInt(strings[2]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", id + "")
                    .build();


            Response response;
            SharePref sharePref = new SharePref(RequestSentFromMe.this);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
                return null;

            }


        }
    }


}
