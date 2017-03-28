package com.nascenia.biyeta.NetWorkOperation;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nascenia.biyeta.appdata.SharePref;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by god father on 3/27/2017.
 */

public class NetWorkOperation {
    public static OkHttpClient client = new OkHttpClient();
    static Context context;

    public static void postData(Context context, String url,
                                String userId) {
        NetWorkOperation.context = context;

        //list position  for update list dynamically according to response
        new SendConnectionRequest().execute(url, userId);


    }

    public static void CreateProfileReqeust(Context context, String url) {

        NetWorkOperation.context = context;
        new CreateProfileRequestTask().execute(url);
    }

    static class SendConnectionRequest extends AsyncTask<String, String, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("response", s);

        }

        @Override
        protected String doInBackground(String... strings) {


            Integer id = Integer.parseInt(strings[1]);
            Integer position = Integer.parseInt(strings[2]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", id + "")
                    .build();


            Response response;
            SharePref sharePref = new SharePref(NetWorkOperation.context);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
                return null;

            }


        }
    }

    static class CreateProfileRequestTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                String s1 = new JSONObject(s).getJSONArray("message").getJSONObject(0).getString("detail");
                Log.i("responseresult: ", s1);
                Toast.makeText(NetWorkOperation.context, s1, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("profileresponse", strings[0]);

            Response response;
            SharePref sharePref = new SharePref(NetWorkOperation.context);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
                return null;

            }


        }
    }

}
