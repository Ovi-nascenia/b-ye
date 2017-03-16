package com.nascenia.biyeta.activity;

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

import com.google.gson.Gson;
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

public class InboxSingleChat extends AppCompatActivity {


    public static  int sender_id,recevier_id,current_user_id;
    ListView recyclerView;
    EditText editTextMesaageField;
    String userName;


    public ArrayList<Integer> messageId;

    public    int flag=1;
    ChatHead response;
    String messageText;
    CountDownTimer countDownTimer;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_inbox_conversation_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
      //  getSupportActionBar().set(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);




        sender_id = intent.getIntExtra("sender_id", 4);
        recevier_id = intent.getIntExtra("receiver_id", 4);
        current_user_id=intent.getIntExtra("current_user",4);
        userName=intent.getStringExtra("userName");
        messageId=new ArrayList<>();
        setTitle(userName);


        Log.e("come", recevier_id + " "+current_user_id);

        setUpId();
        new LoadMessageThread().execute();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (flag < response.getTotalMessage())
                {
                    flag++;
                    new LoadMessageThread().execute();
                    Toast.makeText(InboxSingleChat.this,"load more data",Toast.LENGTH_SHORT).show();
                }
                else
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
        editTextMesaageField=(EditText)findViewById(R.id.inputMsg);



        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageText=editTextMesaageField.getText().toString();


                Log.e("fuck",editTextMesaageField.getText().toString());
                TempMessage message=new TempMessage(editTextMesaageField.getText().toString(),"3-2-2017");
                editTextMesaageField.setText("");
                new SendMessage().execute();



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  countDownTimer.cancel();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
       /// countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag=0;
        countDownTimer.cancel();
    }

    void setUpId()
    {
        recyclerView=(ListView) findViewById(R.id.person_inbox_list);
    }

    public static List<Message> listMessage=new ArrayList<>();
    ChatListAdapter inboxListAdapter;

    class LoadMessageThread extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mSwipeRefreshLayout.setRefreshing(false);

            Log.e("fii", s + "sss");

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
                Toast.makeText(InboxSingleChat.this, "No Message", Toast.LENGTH_SHORT).show();
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


            if (flag >1)
            {
                request = new Request.Builder()
                        .url("http://test.biyeta.com/api/v1/messages/get_conversation?sender_id="+sender_id+"&receiver_id="+recevier_id+"&page="+flag)
                        .addHeader("Authorization", "Token token=" + token)
                        .build();
            }


            else

                 request = new Request.Builder()
                    .url("http://test.biyeta.com/api/v1/messages/get_conversation?sender_id="+sender_id+"&receiver_id="+recevier_id)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();




            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class SendMessage extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           Toast.makeText(InboxSingleChat.this,s,Toast.LENGTH_SHORT).show();


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
            int se=sender_id;
            mBuilder.addFormDataPart("message[sender_id]" , current_user_id+"");
            if (recevier_id!=current_user_id)
            mBuilder.addFormDataPart("message[receiver_id]" ,recevier_id+"");
            else
                mBuilder.addFormDataPart("message[receiver_id]" ,sender_id+"");

            mBuilder.addFormDataPart("message[text]", messageText);

            RequestBody  requestBody = mBuilder.build();
            Request request1 = new Request.Builder()
                    .url("http://test.biyeta.com/api/v1/messages")
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    class FetchMessage extends AsyncTask<String, String, String> {

        ArrayList<Integer> newMessage=new ArrayList<>();

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mSwipeRefreshLayout.setRefreshing(false);

            Log.e("fii",s+"sss");

            Gson gson = new Gson();
            try {


                InputStream is = new ByteArrayInputStream(s.getBytes());
                InputStreamReader isr = new InputStreamReader(is);
                response = gson.fromJson(isr, ChatHead.class);
                for (int i = response.getMessages().size() - 1; i >= 0; i--) {
                    if (!messageId.contains(response.getMessages().get(i).getId())) {
                        messageId.add(response.getMessages().get(i).getId());
                        Message message = response.getMessages().get(i);
                        listMessage.add(message);
                        inboxListAdapter.notifyDataSetChanged();
                    }
                }
            }catch (Exception e)
            {
                Toast.makeText(InboxSingleChat.this,"Server Disconnected",Toast.LENGTH_SHORT).show();
                countDownTimer.cancel();
            }


//            recyclerView.setLayoutManager(new LinearLayoutManager(InboxSingleChat.this));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());new


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
                        .url("http://test.biyeta.com/api/v1/messages/get_conversation?sender_id="+sender_id+"&receiver_id="+recevier_id)
                        .addHeader("Authorization", "Token token=" + token)
                        .build();




            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
