package com.nascenia.biyeta.NetWorkOperation;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
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
                                String userId, Button finalResultButton, String msg) {
        NetWorkOperation.context = context;

        //list position  for update list dynamically according to response
        new SendConnectionRequest(finalResultButton).execute(url, userId, msg);


    }

    public static void CreateProfileReqeust(Context context, String url, Button finalResultButton, String msg) {

        NetWorkOperation.context = context;
        new CreateProfileRequestTask(finalResultButton).execute(url, msg);
    }

    static class SendConnectionRequest extends AsyncTask<String, String, String> {


        private Button finalResultButton;
        private String msg;

        public SendConnectionRequest(Button finalResultButton) {
            this.finalResultButton = finalResultButton;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("response", s);

            this.finalResultButton.setEnabled(false);
            this.finalResultButton.setText(msg);

        }

        @Override
        protected String doInBackground(String... strings) {

            msg = strings[2];
            Integer id = Integer.parseInt(strings[1]);
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


        private Button finalResultButton;
        private String msg;

        public CreateProfileRequestTask(Button finalResultButton) {

            this.finalResultButton = finalResultButton;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           /* try {
                String s1 = new JSONObject(s).getJSONArray("message").getJSONObject(0).getString("detail");
                Log.i("responseresult: ", s1);
                Toast.makeText(NetWorkOperation.context, s1, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            this.finalResultButton.setEnabled(false);
            this.finalResultButton.setText(msg);

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("profileresponse", strings[0]);
            msg = strings[1];

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
