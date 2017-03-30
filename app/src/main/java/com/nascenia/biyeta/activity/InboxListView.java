package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.InboxListAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.model.InboxAllThreads.Example;
import com.nascenia.biyeta.model.InboxAllThreads.Inbox;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by god father on 3/13/2017.
 */

public class InboxListView extends CustomActionBarActivity {
    public final String INBOX_SUB_URL = "messages";
    private final OkHttpClient client = new OkHttpClient();
    ProgressBar progBar;
    TextView text;
    Handler mHandler = new Handler();
    int mProgressStatus = 0;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_view);
        setUpId();
        setUpToolBar("Inbox", this);
        if (Utils.isOnline(InboxListView.this))
            new LoadMessageThread().execute();
        else
            Utils.ShowAlert(InboxListView.this, "Check Internet Connection");


    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }

    void setUpId() {
        recyclerView = (RecyclerView) findViewById(R.id.all_message_thread);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Message");


    }

    class LoadMessageThread extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            InputStream is = new ByteArrayInputStream(s.getBytes());
            InputStreamReader isr = new InputStreamReader(is);
            Example response = gson.fromJson(isr, Example.class);


            InboxListAdapter inboxListAdapter = new InboxListAdapter(response, R.layout.inbox_item);
            recyclerView.setAdapter(inboxListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(InboxListView.this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());



        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(InboxListView.this);
            String token = sharePref.get_data("token");
            Request request = null;


            request = new Request.Builder()
                    .url(Constant.BASE_URL + INBOX_SUB_URL)
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
