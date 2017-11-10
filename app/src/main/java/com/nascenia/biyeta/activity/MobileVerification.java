package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okio.BufferedSink;


/**
 * Created by god father on 3/23/2017.
 */

public class MobileVerification extends CustomActionBarActivity {
    private Tracker mTracker;
    private AnalyticsApplication application;

    private Button submitButton;
    private IntlPhoneInput phoneNumber;
    private Response responseStatus;
    private TextView code_textView, resend_textView;
    private EditText verification_code_editText;
    private int verification_id, try_count;
    private String server_time, retry_time;
    private long retry_time_diff;

    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_verification);
        submitButton = (Button) findViewById(R.id.code_submit_button1);
        phoneNumber = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        phoneNumber.setEmptyDefault("BD");
        code_textView = (TextView) findViewById(R.id.message_code_textView2);
        resend_textView = (TextView) findViewById(R.id.again_varifycode_textView3);
        verification_code_editText = (EditText) findViewById(R.id.verifycode_editText);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(verification_code_editText.getVisibility() == View.VISIBLE)
                    requestVerificationCode(verification_code_editText.getText().toString());
                else
                    requestVerificationCode("send");
            }
        });
        resend_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(try_count<=3)
                    requestVerificationCode("resend");
                else
                    Utils.ShowAlert(MobileVerification.this, "Please retry after " + retry_time_diff + "hour(s)");//replace with bangla message
            }
        });

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Google Analytics*/
        mTracker.setScreenName("মোবাইলে নাম্বার ভেরিফাই");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void requestVerificationCode(String task) {
        if (isValidPhoneNumber(phoneNumber.getNumber())) {
            SharePref sharePref = new SharePref(MobileVerification.this);
            new verificationCodeRequest().execute(phoneNumber.getNumber(), sharePref.get_data("token"), task);
        }
        else {
           Toast.makeText(MobileVerification.this,"Enter a valid Mobile Number",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private class verificationCodeRequest extends AsyncTask<String,String,String>
    {
        //null, code, resend
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody;
                Request request;
                String url = "";
                if(urls[2].equalsIgnoreCase("send"))   //send verification code
                {
                    formBody = new FormEncodingBuilder()
                            .add("mobile_number", urls[0])
                            .build();
                    url = Utils.Base_URL+"/api/v1/mobile_verifications";
                }
                else if(urls[2].equalsIgnoreCase("resend"))     //resend verification code
                {
                    formBody = new FormEncodingBuilder()
                            .add("mobile_number", urls[0])
                            .add("verification_id", verification_id + "")
                            .build();
                    url = Utils.Base_URL+"/api/v1/mobile_verifications/resend_code";
                }
                else {                                         //verify mobile
                    formBody = new FormEncodingBuilder()
                            .add("verification_code", urls[2])
                            .add("verification_id", verification_id + "")
                            .build();
                   url = Utils.Base_URL+"/api/v1/mobile_verifications/verify_mobile";
                }

                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", "Token token=" + urls[1])
                        .post(formBody)
                        .build();

                responseStatus = client.newCall(request).execute();

                return responseStatus.body().string();

            } catch (Exception e) {
                e.printStackTrace();
//                Log.i("asynctaskdata", e.getMessage());
//                application.trackEception(e, "verificationCodeRequest/doInBackground", "MobileVerification", e.getMessage().toString(), mTracker);
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("verification",s);
            JSONObject responseObj;
            try {
                responseObj = new JSONObject(s);
                if(responseObj.has("detail")) { //send verification code
                    String message = responseObj.getJSONObject("detail").getString("message");
                    verification_id = responseObj.getJSONObject("detail").getInt("verification_id");
//                    Utils.ShowAlert(MobileVerification.this, message);
                    Toast.makeText(MobileVerification.this, message, Toast.LENGTH_SHORT).show();
                }
                else if(responseObj.has("mobile_verification_information")) // resend verification code
                {
                    try_count = responseObj.getJSONObject("mobile_verification_information").getInt("try_count");
                    verification_id = responseObj.getJSONObject("mobile_verification_information").getInt("verification_id");
                    server_time = responseObj.getJSONObject("mobile_verification_information").getString("server_time");
                    retry_time = responseObj.getJSONObject("mobile_verification_information").getString("again_retry_time");
                    retry_time_diff = (Long.parseLong(retry_time) - Long.parseLong(server_time))/(60*60); //convert to hours
                    if(try_count >= 3) {
                        Utils.ShowAlert(MobileVerification.this, R.string.please_retry_after+ retry_time_diff + " hour(s)");
                        return;
                    }
                }
                else  //mobile verification
                {
                    if(responseObj.has("message")) { //send verification code
                        String message = responseObj.getJSONObject("message").getString("detail");
                        //Utils.ShowAlert(MobileVerification.this, message);
                        Toast.makeText(MobileVerification.this, message, Toast.LENGTH_SHORT).show();
                        SharePref sharePref=new SharePref(MobileVerification.this);
                        sharePref.set_data("mobile_verified","true");
                        startActivity(new Intent(MobileVerification.this, HomeScreen.class));
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "verificationCodeRequest/onPostExecute", "MobileVerification", e.getMessage().toString(), mTracker);
            }
            submitButton.setText(R.string.code_submit_button);
            code_textView.setVisibility(View.VISIBLE);
            resend_textView.setVisibility(View.VISIBLE);
            verification_code_editText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }
}
