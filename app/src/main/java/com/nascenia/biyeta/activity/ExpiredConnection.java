package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.BiodatarequestFromMe;
import com.nascenia.biyeta.adapter.ExpireListAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.fragment.RecyclerItemClickListener;
import com.nascenia.biyeta.model.ExpireList.ExpireProfile;
import com.nascenia.biyeta.model.biodata.profile.BiodataProfile;
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
 * Created by god father on 3/23/2017.
 */

public class ExpiredConnection extends CustomActionBarActivity {
    RecyclerView recyclerView;

    public static int position;

    private Tracker mTracker;
    private AnalyticsApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expire_connection);
        setUpToolBar(getString(R.string.expire_request), this);
        setUpId();
        //new LoadData().execute(Utils.Base_URL+"/api/v1/requests/expired_communication_requests");

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        new LoadData().execute(Utils.Base_URL+"/api/v1/requests/expired_communication_requests");

        /*Google Analytics*/
        mTracker.setScreenName(getString(R.string.expire_request));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    void setUpId() {
        recyclerView = (RecyclerView) findViewById(R.id.expire_list);
        findViewById(R.id.close_expire_connection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.expire_message_section).setVisibility(View.GONE);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {




            }
        }));


    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }


    ExpireProfile response;
    ExpireListAdapter expireListAdapter;
    OkHttpClient client = new OkHttpClient();

    class LoadData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          //  Toast.makeText(ExpiredConnection.this, s, Toast.LENGTH_SHORT).show();
            if (null == s) {
                //Toast.makeText(ExpiredConnection.this, "Empty", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    if (!jsonObject.has("message")) {
                        Gson gson = new Gson();
                        InputStream is = new ByteArrayInputStream(s.getBytes());
                        InputStreamReader isr = new InputStreamReader(is);
                        response = gson.fromJson(isr, ExpireProfile.class);

                        expireListAdapter = new ExpireListAdapter(response, R.layout.list_item_expire) {
                            @Override
                            public void onClickSmile(int id, int postion) {
                                Toast.makeText(ExpiredConnection.this, "Sent Connection", Toast.LENGTH_SHORT).show();
                                position = postion;

                                new SendConnection().execute(Utils.Base_URL+"/api/v1/communication_requests", id + "", position + "");
                                //  Toast.makeText(RequestSentFromMe.this, id + " ", Toast.LENGTH_SHORT).show();
                                application.setEvent("Action", "Click", getResources().getString(R.string.connection_again), mTracker);
                            }

                            @Override
                            public void profileLoad(int id,String userName) {
                                Intent intent = new Intent(ExpiredConnection.this, NewUserProfileActivity.class);
                                intent.putExtra("id",id + "");
                                intent.putExtra("user_name", userName);
                                intent.putExtra("PROFILE_EDIT_OPTION", false);
                                startActivity(intent);
                            }

                        };
                        recyclerView.setAdapter(expireListAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ExpiredConnection.this));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        findViewById(R.id.empty_message).setVisibility(View.GONE);
                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
//                    application.trackEception(e, "LoadData/onPostExecute", "ExpiredConnection", e.getMessage().toString(), mTracker);
                }

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urlList) {

            String url = urlList[0];

            Request request;
            Response response;
            SharePref sharePref = new SharePref(ExpiredConnection.this);
            String token = sharePref.get_data("token");

            request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                return jsonData;

            } catch (Exception e) {
//                application.trackEception(e, "LoadData/onPreExecute", "ExpiredConnection", e.getMessage().toString(), mTracker);
                return null;

            }


        }
    }

    class SendConnection extends AsyncTask<String, String, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("message")) {
                     response.getProfiles().remove(position);
                     expireListAdapter.notifyDataSetChanged();
                }else if (jsonObject.has("error")) {
                    JSONObject errorObj = jsonObject.getJSONArray("error").getJSONObject(0);
                    if (errorObj.has("show_pricing_plan")) {
                        if (errorObj.getBoolean("show_pricing_plan")) {
                            Toast.makeText(ExpiredConnection.this, jsonObject.getJSONArray(
                                    "error").getJSONObject(0).getString("detail"),
                                    Toast.LENGTH_LONG).show();

                            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                                    ExpiredConnection.this);
                            alertBuilder.setCancelable(true);
//                            alertBuilder.setTitle(R.string.account_recharge_title);
                            alertBuilder.setMessage(jsonObject.getJSONArray("error").getJSONObject(0).getString("detail"));
                            alertBuilder.setPositiveButton(R.string.see_details,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent myIntent = new Intent(ExpiredConnection.this,
                                                    WebViewPayment.class);
                                            startActivityForResult(myIntent,
                                                    Utils.UPGRADE_REQUEST_CODE);
                                        }
                                    });
                            alertBuilder.setNegativeButton(R.string.not_now,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            final AlertDialog alert = alertBuilder.create();
                            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface arg0) {
                                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                                }
                            });
                            alert.show();
                        }
                    } else {
                        Toast.makeText(ExpiredConnection.this, jsonObject.getJSONArray(
                                "error").getJSONObject(
                                0).getString("detail"), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "SendConnection/onPostExecute", "ExpiredConnection", e.getMessage().toString(), mTracker);
            }

        }

        @Override
        protected String doInBackground(String... strings) {

          //  Log.e(" id", strings[1]);

            Integer id = Integer.parseInt(strings[1]);
            Integer position = Integer.parseInt(strings[2]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", id + "")
                    .add("new_pricing_plan", true + "")
                    .build();


            Response response;
            SharePref sharePref = new SharePref(ExpiredConnection.this);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                Log.e("jsonData", jsonData);
                return jsonData;

            } catch (Exception e) {
                Log.e("jsonData", e.toString());
//                application.trackEception(e, "SendConnection/doInBackground", "ExpiredConnection", e.getMessage().toString(), mTracker);
                return null;

            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == Utils.UPGRADE_REQUEST_CODE){
                new NetWorkOperation.loadAccountBalance(ExpiredConnection.this, application, mTracker).execute();
//                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ExpiredConnection.this);
//                alertBuilder.setCancelable(true);
//                alertBuilder.setTitle("Upgrade successful");
//                alertBuilder.setMessage("You have upgraded your account successfully");
//                alertBuilder.setPositiveButton(android.R.string.yes,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//                            }
//                        });
//                AlertDialog alert = alertBuilder.create();
//                alert.show();
            }
        }
    }

}
