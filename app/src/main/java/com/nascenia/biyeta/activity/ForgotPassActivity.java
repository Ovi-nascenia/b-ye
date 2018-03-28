package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class ForgotPassActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnReset;
    private EditText edtEmail;
    String email;
    private OkHttpClient client;
    private ProgressDialog mProgressDialog;

    private Tracker mTracker;
    private AnalyticsApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnReset = findViewById(R.id.btn_reset);
        edtEmail = findViewById(R.id.email);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidationAndSendReq();
            }
        });

        edtEmail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkValidationAndSendReq();
                    return true;
                }
                return false;
            }
        });

        mProgressDialog = new ProgressDialog(ForgotPassActivity.this);
        mProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_message));
        mProgressDialog.setCancelable(false);

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());

        client = new OkHttpClient();
    }

    private void checkValidationAndSendReq() {
        email = edtEmail.getText().toString();

        if (email.trim().equals("")) {
            Utils.ShowAlert(ForgotPassActivity.this, "ইমেইল পূরণ করুন");
        }else {
            if (!Utils.isOnline(ForgotPassActivity.this)) {
                Utils.ShowAlert(ForgotPassActivity.this, getString(R.string.no_internet_connection));
            } else {
                new resetPassRequest(application, mTracker).execute(email);
            }
        }
    }

    public void backBtnAction(View v) {
        finish();
    }

    private class resetPassRequest extends AsyncTask<String, String, String> {

        public resetPassRequest(AnalyticsApplication application,
                Tracker tracker) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            try{
                super.onPostExecute(s);
                Log.e("testtt", s);
            }catch(Exception e)
            {
                Utils.ShowAlert(ForgotPassActivity.this, getResources().getString(R.string.no_internet_connection));
            }

            if (s == null) {
                Utils.ShowAlert(ForgotPassActivity.this, getResources().getString(R.string.no_internet_connection));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("message")) {
                        Utils.ShowAlert(ForgotPassActivity.this,
                                jsonObject.getJSONObject("message").getString("detail"));
                    }else if (jsonObject.has("errors")) {
                        Utils.ShowAlert(ForgotPassActivity.this,
                                jsonObject.getJSONObject("errors").getString("detail"));
                    }else{
                        Utils.ShowAlert(ForgotPassActivity.this, getResources().getString(R.string.no_internet_connection));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Utils.ShowAlert(ForgotPassActivity.this, getResources().getString(R.string.no_internet_connection));
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("user[email]", strings[0])
                    .add("Content-Type", "multipart/form-data")
                    .build();
            Request request = null;
            request = new Request.Builder()
                    .url(Utils.PASS_RESET)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject jobj = new JSONObject(jsonData);
                return jobj.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
