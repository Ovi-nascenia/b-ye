package com.nascenia.biyeta.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.nascenia.biyeta.activity.InboxListView;
import com.nascenia.biyeta.activity.Search_Filter;
import com.nascenia.biyeta.activity.UserProfileActivity;
import com.nascenia.biyeta.adapter.CommunicationAdapter;
import com.nascenia.biyeta.adapter.InboxListAdapter;
import com.nascenia.biyeta.adapter.Match_Adapter;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.model.InboxAllThreads.Example;
import com.nascenia.biyeta.model.SearchProfileModel;
import com.nascenia.biyeta.model.communication.profile.CommunicationProfile;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by user on 1/5/2017.
 */

public class Match extends Fragment implements View.OnClickListener{



    TextView biodata;
    TextView connection;
    RecyclerView recyclerView;

    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("come", "Match");
        View v = inflater.inflate(R.layout.match, null);

        biodata=(TextView)v.findViewById(R.id.biodata);
        biodata.setOnClickListener(this);

        connection=(TextView)v.findViewById(R.id.connection);
        connection.setOnClickListener(this);

        recyclerView=(RecyclerView)v.findViewById(R.id.communication_profile_list);
        return v;

    }


    @Override
    public void onClick(View view) {

        int id=view.getId();

        switch (id)
        {
            case  R.id.biodata:
                biodata.setTextColor(Color.WHITE);
                biodata.setBackgroundResource(R.color.colorAccent);

                connection.setTextColor(Color.BLACK);
                connection.setBackgroundColor(Color.GRAY);
                recyclerView.setAdapter(null);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                break;

            case R.id.connection:


                connection.setTextColor(Color.WHITE);
                connection.setBackgroundResource(R.color.colorAccent);

                biodata.setTextColor(Color.BLACK);
                biodata.setBackgroundColor(Color.GRAY);
                new LoadConnection().execute(" http://test.biyeta.com/api/v1/communication_requests");

                break;
        }

    }

    private final OkHttpClient client = new OkHttpClient();
    class LoadConnection extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            InputStream is = new ByteArrayInputStream(s.getBytes());
            InputStreamReader isr = new InputStreamReader(is);
            CommunicationProfile response = gson.fromJson(isr, CommunicationProfile.class);

            CommunicationAdapter inboxListAdapter=new CommunicationAdapter(response,R.layout.common_user_profile_item);
            recyclerView.setAdapter(inboxListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(getContext());
            String token = sharePref.get_data("token");
            Request request = null;

            request = new Request.Builder()
                    .url(strings[0])
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
