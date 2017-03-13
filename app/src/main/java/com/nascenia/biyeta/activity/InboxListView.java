package com.nascenia.biyeta.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class InboxListView extends AppCompatActivity {
    public final String INBOX_SUB_URL = "messages";
    private RecyclerView recyclerView;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_view);
        setUpId();
        if (Utils.isOnline(InboxListView.this))
            new LoadMessageThread().execute();
        else
            Utils.ShowAlert(InboxListView.this, "Check Internet Connection");



    }

    void setUpId() {
        recyclerView = (RecyclerView) findViewById(R.id.all_message_thread);

    }


    class LoadMessageThread extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            s="{\n" +
                    "  \"inbox\": [\n" +
                    "    {\n" +
                    "      \"sender_name\": \"Test53\",\n" +
                    "      \"sender_image\": null,\n" +
                    "      \"message\": {\n" +
                    "        \"id\": 168,\n" +
                    "        \"user_id\": 2820,\n" +
                    "        \"receiver\": 2769,\n" +
                    "        \"text\": \"hey test 53 wassup new msg\",\n" +
                    "        \"created_at\": \"2017-03-09T07:15:44.000Z\",\n" +
                    "        \"updated_at\": \"2017-03-09T07:15:44.000Z\",\n" +
                    "        \"is_seen\": false\n" +
                    "      },\n" +
                    "      \"unread\": 3\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"sender_name\": \"Boy072\",\n" +
                    "      \"sender_image\": null,\n" +
                    "      \"message\": {\n" +
                    "        \"id\": 188,\n" +
                    "        \"user_id\": 2769,\n" +
                    "        \"receiver\": 2953,\n" +
                    "        \"text\": \"sent through api\",\n" +
                    "        \"created_at\": \"2017-03-10T08:56:06.000Z\",\n" +
                    "        \"updated_at\": \"2017-03-10T08:56:06.000Z\",\n" +
                    "        \"is_seen\": true\n" +
                    "      },\n" +
                    "      \"unread\": 0\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n";


            Gson gson = new Gson();
            InputStream is = new ByteArrayInputStream(s.getBytes());
            InputStreamReader isr = new InputStreamReader(is);
            Example response = gson.fromJson(isr, Example.class);
            InboxListAdapter inboxListAdapter=new InboxListAdapter(response,R.layout.inbox_item);
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
                    .url(Constant.BASE_URL+INBOX_SUB_URL)
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
    ProgressBar progBar;
    TextView text;
    Handler mHandler = new Handler();
    int mProgressStatus=0;
}
