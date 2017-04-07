package com.nascenia.biyeta.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.ExpiredConnection;
import com.nascenia.biyeta.activity.HomeScreen;
import com.nascenia.biyeta.activity.InboxListView;
import com.nascenia.biyeta.activity.OwnUserProfileActivity;
import com.nascenia.biyeta.activity.RequestSentFromMe;
import com.nascenia.biyeta.activity.SendRequestActivity;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

/**
 * Created by user on 1/5/2017.
 *//////lll

public class Inbox extends Fragment implements View.OnClickListener {

    public Inbox() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("oncreate", "InboxOnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.e("come", "inbox");
        View v = inflater.inflate(R.layout.inbox, null);
        v.findViewById(R.id.tv_expire).setOnClickListener(this);
        v.findViewById(R.id.tv_inbox).setOnClickListener(this);
        v.findViewById(R.id.tv_sent_request).setOnClickListener(this);
        v.findViewById(R.id.tv_sent_request_from_me).setOnClickListener(this);


        return v;

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_sent_request:
                new LoadReqeustSenderIdsTask().execute();
                break;

            case R.id.tv_inbox:
                startActivity(new Intent(getContext(), InboxListView.class));
                break;

            case R.id.tv_expire:
                startActivity(new Intent(getContext(), ExpiredConnection.class));
                break;

            case R.id.tv_sent_request_from_me:
                startActivity(new Intent(getContext(), RequestSentFromMe.class));
                break;


        }
    }

    private String responseValue = null;

    class LoadReqeustSenderIdsTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            try {

                Response response = new ResourceProvider((Activity) getContext()).fetchGetResponse(
                        Utils.REQUEST_SENDER_IDS_URL);
                ResponseBody responseBody = response.body();
                responseValue = responseBody.string();
                responseBody.close();


            } catch (Exception e) {
            }

            return responseValue;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                if (responseValue != null && !new JSONObject(s).has("message")) {

                    //Toast.makeText(getBaseContext(), responseValue, Toast.LENGTH_LONG).show();
                    if (BioDataRequestFragment.profileRequestSenderIdsList != null) {
                        BioDataRequestFragment.profileRequestSenderIdsList.clear();
                    }


                    if (CommunicationRequestFragment.communicationRequestSenderIdsList != null) {
                        CommunicationRequestFragment.communicationRequestSenderIdsList.clear();
                    }


                    SendRequestActivity.biodataRequestCounter = 0;
                    SendRequestActivity.communicationRequestCounter = 0;


                    startActivity(new Intent(getContext(), SendRequestActivity.class).
                            putExtra("REQUEST_RESPONSE_DATA",
                                    responseValue));
                    RequestSenderIds requestSenderIds = new Gson().fromJson(responseValue, RequestSenderIds.class);


                } else if (responseValue == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.ShowAlert(getActivity(), getString(R.string.no_internet_connection));
                            //getActivity().finish();
                        }
                    });

                } else {
                    Utils.ShowAlert(getActivity(), "আপনাকে পাঠানো কোন অনুরোধ নেই");
                }

            } catch (Exception e) {
            }


        }
    }
}
