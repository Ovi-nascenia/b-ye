package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.ChatListAdapter;
import com.nascenia.biyeta.adapter.InboxListAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.model.InboxAllThreads.Example;
import com.nascenia.biyeta.model.conversation.ChatHead;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by god father on 3/13/2017.
 */

public class InboxSingleChat extends AppCompatActivity {


   public static  int sender_id,recevier_id;
    ListView recyclerView;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_inbox_conversation_list);
         sender_id=intent.getIntExtra("sender_id",4);
         recevier_id=intent.getIntExtra("receiver_id",4);


        Log.e("come",recevier_id+"");

         setUpId();
         new LoadMessageThread().execute();






    }

    void setUpId()
    {
        recyclerView=(ListView) findViewById(R.id.person_inbox_list);
    }


    class LoadMessageThread extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            s="{\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"id\": 189,\n" +
                    "      \"user_id\": 2769,\n" +
                    "      \"receiver\": 2953,\n" +
                    "      \"text\": \"sent through api\",\n" +
                    "      \"created_at\": \"2017-03-13T06:51:19.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 188,\n" +
                    "      \"user_id\": 2769,\n" +
                    "      \"receiver\": 2953,\n" +
                    "      \"text\": \"sent through api\",\n" +
                    "      \"created_at\": \"2017-03-10T08:56:06.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 187,\n" +
                    "      \"user_id\": 2769,\n" +
                    "      \"receiver\": 2953,\n" +
                    "      \"text\": \"whyyyyyyyyyyyyyyyyy\",\n" +
                    "      \"created_at\": \"2017-03-10T08:54:06.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 186,\n" +
                    "      \"user_id\": 2953,\n" +
                    "      \"receiver\": 2769,\n" +
                    "      \"text\": \"whey\",\n" +
                    "      \"created_at\": \"2017-03-10T08:53:41.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 185,\n" +
                    "      \"user_id\": 2769,\n" +
                    "      \"receiver\": 2953,\n" +
                    "      \"text\": \"deleted ur msg :(\",\n" +
                    "      \"created_at\": \"2017-03-10T08:44:28.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 184,\n" +
                    "      \"user_id\": 2953,\n" +
                    "      \"receiver\": 2769,\n" +
                    "      \"text\": \"I am a boy\",\n" +
                    "      \"created_at\": \"2017-03-10T08:39:39.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 183,\n" +
                    "      \"user_id\": 2769,\n" +
                    "      \"receiver\": 2953,\n" +
                    "      \"text\": \"hello girl test 7\",\n" +
                    "      \"created_at\": \"2017-03-10T08:33:34.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 181,\n" +
                    "      \"user_id\": 2769,\n" +
                    "      \"receiver\": 2953,\n" +
                    "      \"text\": \"yo boy072\",\n" +
                    "      \"created_at\": \"2017-03-10T08:18:41.000Z\",\n" +
                    "      \"is_seen\": true\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"total_message\": 1\n" +
                    "}\n";

            Gson gson = new Gson();
            InputStream is = new ByteArrayInputStream(s.getBytes());
            InputStreamReader isr = new InputStreamReader(is);
            ChatHead response = gson.fromJson(isr, ChatHead.class);
            ChatListAdapter inboxListAdapter=new ChatListAdapter(InboxSingleChat.this,response);
            recyclerView.setAdapter(inboxListAdapter);
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
                    .url(Constant.BASE_URL+"messages")
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
