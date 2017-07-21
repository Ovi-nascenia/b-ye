package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.ChatListAdapter;
import com.nascenia.biyeta.adapter.InboxListAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.model.InboxAllThreads.Example;
import com.nascenia.biyeta.model.InboxAllThreads.Inbox;
import com.nascenia.biyeta.model.conversation.ChatHead;
import com.nascenia.biyeta.model.conversation.Message;
import com.nascenia.biyeta.model.conversation.TempMessage;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by god father on 3/13/2017.
 */

public class InboxSingleChat extends CustomActionBarActivity {


    private Tracker mTracker;
    private AnalyticsApplication application;

    public static int sender_id, recevier_id, current_user_id;
    public static List<Message> listMessage = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    public ArrayList<Integer> messageId;
    public int flag = 1;
    ListView recyclerView;
    EditText editTextMesaageField;
    String userName;
    ChatHead response;
    String messageText;
    CountDownTimer countDownTimer;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ChatListAdapter inboxListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_inbox_conversation_list);


        sender_id = intent.getIntExtra("sender_id", 4);
        recevier_id = intent.getIntExtra("receiver_id", 4);
        current_user_id = intent.getIntExtra("current_user", 4);
        userName = intent.getStringExtra("userName");
        messageId = new ArrayList<>();
        setUpToolBar(userName, this);


        Log.e("come", recevier_id + " " + current_user_id);

        setUpId();
        new LoadMessageThread().execute();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (response!=null&&flag < response.getTotalMessage()) {
                    flag++;
                    new LoadMessageThread().execute();
//                    Toast.makeText(InboxSingleChat.this, "load more data", Toast.LENGTH_SHORT).show();
                } else
                    mSwipeRefreshLayout.setRefreshing(false);

            }
        });
        countDownTimer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                new FetchMessage().execute();
                start();// here, when your CountDownTimer has finished , we start it again :)
            }
        };
        countDownTimer.start();
        editTextMesaageField = (EditText) findViewById(R.id.inputMsg);


        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageText = editTextMesaageField.getText().toString();


                Log.e("InBoxText", editTextMesaageField.getText().toString());
                TempMessage message = new TempMessage(editTextMesaageField.getText().toString(), "3-2-2017");
                editTextMesaageField.setText("");
                new SendMessage().execute();

                application.setEvent("Action", "Click", "Message sent to " + userName, mTracker);

            }
        });

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
        //  countDownTimer.cancel();

        /*Google Analytics*/
        mTracker.setScreenName(getString(R.string.contact_title));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /// countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = 0;
        countDownTimer.cancel();
    }

    void setUpId() {
        recyclerView = (ListView) findViewById(R.id.person_inbox_list);
    }

    class LoadMessageThread extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            findViewById(R.id.progressbar_message).setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            Log.e("MessageHistory", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("no_results")) {
                    recyclerView.setVisibility(View.GONE);
                    findViewById(R.id.empty_message).setVisibility(View.VISIBLE);

                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    findViewById(R.id.empty_message).setVisibility(View.GONE);
                    Gson gson = new Gson();
                    InputStream is = new ByteArrayInputStream(s.getBytes());
                    InputStreamReader isr = new InputStreamReader(is);
                    response = gson.fromJson(isr, ChatHead.class);

                    try {


                        for (int i = 0; i < response.getMessages().size(); i++)
                            messageId.add(response.getMessages().get(i).getId());
                        if (flag == 1) {

                            listMessage = response.getMessages();
                            inboxListAdapter = new ChatListAdapter(InboxSingleChat.this, listMessage) {
                                @Override
                                public void load() {
                                    // Toast.makeText(InboxSingleChat.this,"load more data",Toast.LENGTH_SHORT).show();
                                }
                            };
                            recyclerView.setAdapter(inboxListAdapter);

                        } else
                            for (int i = response.getMessages().size() - 1, j = 0; i >= 0 && j < response.getMessages().size(); i--, j++) {
                                listMessage.add(j, response.getMessages().get(i));
                                inboxListAdapter.notifyDataSetChanged();
                                recyclerView.smoothScrollToPosition(0);

                            }

//            recyclerView.setLayoutManager(new LinearLayoutManager(InboxSingleChat.this));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());new


                    } catch (Exception e) {
                        Toast.makeText(InboxSingleChat.this, "কোন মেসেজ নাই", Toast.LENGTH_SHORT).show();
//                        application.trackEception(e, "LoadMessageThread/onPostExecute", "InboxSingleChat", e.getMessage().toString(), mTracker);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoadMessageThread/onPostExecute", "InboxSingleChat", e.getMessage().toString(), mTracker);
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(InboxSingleChat.this);
            String token = sharePref.get_data("token");
            Request request = null;


            if (flag > 1) {
                request = new Request.Builder()
                        .url(Utils.Base_URL + "/api/v1/messages/get_conversation?sender_id=" + sender_id + "&receiver_id=" + recevier_id + "&page=" + flag)
                        .addHeader("Authorization", "Token token=" + token)
                        .build();
            } else

                request = new Request.Builder()
                        .url(Utils.Base_URL + "/api/v1/messages/get_conversation?sender_id=" + sender_id + "&receiver_id=" + recevier_id)
                        .addHeader("Authorization", "Token token=" + token)
                        .build();


            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoadMessageThread/doInBackground", "InboxSingleChat", e.getMessage().toString(), mTracker);
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoadMessageThread/doInBackground", "InboxSingleChat", e.getMessage().toString(), mTracker);
            }

            return null;
        }
    }

    class SendMessage extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(InboxSingleChat.this, s, Toast.LENGTH_SHORT).show();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(InboxSingleChat.this);
            String token = sharePref.get_data("token");
            Request request = null;

            MultipartBuilder mBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
            int se = sender_id;
            mBuilder.addFormDataPart("message[sender_id]", current_user_id + "");
            if (recevier_id != current_user_id)
                mBuilder.addFormDataPart("message[receiver_id]", recevier_id + "");
            else
                mBuilder.addFormDataPart("message[receiver_id]", sender_id + "");

            mBuilder.addFormDataPart("message[text]", messageText);

            RequestBody requestBody = mBuilder.build();
            Request request1 = new Request.Builder()
                    .url(Utils.Base_URL + "/api/v1/messages")
                    .post(requestBody)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();


            try {
                response = client.newCall(request1).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "SendMessage/doInBackground", "InboxSingleChat", e.getMessage().toString(), mTracker);
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "SendMessage/doInBackground", "InboxSingleChat", e.getMessage().toString(), mTracker);
            }

            return null;
        }
    }


    class FetchMessage extends AsyncTask<String, String, String> {

        ArrayList<Integer> newMessage = new ArrayList<>();

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mSwipeRefreshLayout.setRefreshing(false);

            Log.e("fii", s + "sss");

            Gson gson = new Gson();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("no_results")) {

                } else {
                    try {


                        InputStream is = new ByteArrayInputStream(s.getBytes());
                        InputStreamReader isr = new InputStreamReader(is);
                        response = gson.fromJson(isr, ChatHead.class);

                        if (null == listMessage || listMessage.size() == 0) {
                            new LoadMessageThread().execute();


                        }

                        for (int i = response.getMessages().size() - 1; i >= 0; i--) {
                            if (!messageId.contains(response.getMessages().get(i).getId())) {
                                messageId.add(response.getMessages().get(i).getId());
                                Message message = response.getMessages().get(i);
                                listMessage.add(message);
                                inboxListAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {


                        if (null == listMessage || listMessage.size() == 0) {
                            new LoadMessageThread().execute();
                        }
                        //   Toast.makeText(InboxSingleChat.this, "Server Disconnected", Toast.LENGTH_SHORT).show();
                        //  countDownTimer.cancel();
//                        application.trackEception(e, "FetchMessage/onPostExecute", "InboxSingleChat", e.getMessage().toString(), mTracker);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "FetchMessage/onPostExecute", "InboxSingleChat", e.getMessage().toString(), mTracker);
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(InboxSingleChat.this);
            String token = sharePref.get_data("token");
            Request request = null;


            request = new Request.Builder()
                    .url(Utils.Base_URL + "/api/v1/messages/get_conversation?sender_id=" + sender_id + "&receiver_id=" + recevier_id)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();


            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "FetchMessage/doInBackground", "InboxSingleChat", e.getMessage().toString(), mTracker);
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "FetchMessage/doInBackground", "InboxSingleChat", e.getMessage().toString(), mTracker);
            }

            return null;
        }
    }

}
