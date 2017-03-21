package com.nascenia.biyeta.service;

import android.app.Activity;
import android.util.Log;

import com.nascenia.biyeta.activity.UserProfileActivity;
import com.nascenia.biyeta.appdata.SharePref;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by saiful on 3/6/17.
 */

public class ResourceProvider {


    private Activity context;
    private String response = null;

    private SharePref sharePref;
    private String token;

    public ResourceProvider(Activity context) {
        this.context = context;
        sharePref = new SharePref(this.context);
        token = sharePref.get_data("token");
    }


    public Response fetchGetResponse(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Token token=" + "264bb7cc85a36044378561b2338a3990")
                .build();
/*
        request = new Request.Builder()
                .url(Constant.BASE_URL + SUB_URL + id)
                .addHeader("Authorization", "Token token=" + token)
                .build();*/


        Response response = okHttpClient.newCall(request).execute();
        Log.i("responsedata", request.url().toString());
        return response;
    }


}
