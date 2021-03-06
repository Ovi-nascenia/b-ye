package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expire_connection);
        setUpToolBar(getString(R.string.expire_request), this);
        setUpId();
        new LoadData().execute(Utils.Base_URL+"/api/v1/requests/expired_communication_requests");


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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

          //  Log.e(" id", strings[1]);

            Integer id = Integer.parseInt(strings[1]);
            Integer position = Integer.parseInt(strings[2]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", id + "")
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
                return null;

            }


        }
    }

}
