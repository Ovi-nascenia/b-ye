package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import org.json.JSONException;
import org.json.JSONObject;

public class MobileVarification extends AppCompatActivity{
    ImageView back;
    private IntlPhoneInput phoneInputView;
    SharePref sharePref;
    OkHttpClient client;
    Button sendVerificationCode, verify;
    String verificationId;
    TextView resendCode;
    EditText verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_varification);
        verify = (Button) findViewById(R.id.verify);
        back = (ImageView) findViewById(R.id.backPreviousActivityImage);

        resendCode = (TextView) findViewById(R.id.text3);
        verificationCode = (EditText) findViewById(R.id.verification_code);

        phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        phoneInputView.setEmptyDefault("BD");

        sharePref = new SharePref(MobileVarification.this);
        client = new OkHttpClient();

        sendVerificationCode = (Button) findViewById(R.id.sendVerificationCode);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new MobileVarification.VerifyCode().execute(verificationId,verificationCode.getText().toString(),sharePref.get_data("mobile_number"));
                    }
                }
        );
        sendVerificationCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new MobileVarification.VerificationCodeSend().execute(phoneInputView.getText().toString());
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new MobileVarification.VerificationCodeResend().execute(sharePref.get_data("mobile_number"),sharePref.get_data("verification_id"));
            }
        });

    }


    public class VerificationCodeSend extends AsyncTask<String, String, String>{
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Toast.makeText(MobileVarification.this,s,Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObject = new JSONObject(s);
                jsonObject.getJSONObject("mobile_verification_information").getString("mobile_number");
                verificationId = jsonObject.getJSONObject("mobile_verification_information").getString("verification_id");
                sharePref.set_data("verification_id", jsonObject.getJSONObject("mobile_verification_information").getString("verification_id"));
                sharePref.set_data("mobile_number",jsonObject.getJSONObject("mobile_verification_information").getString("mobile_number"));
                Toast.makeText(MobileVarification.this, s, Toast.LENGTH_LONG).show();
                jsonObject.getJSONObject("mobile_verification_information").getString("server_time");
                jsonObject.getJSONObject("mobile_verification_information").getString("again_retry_time");
                jsonObject.getJSONObject("mobile_verification_information").getString("try_count");
                jsonObject.getJSONObject("mobile_verification_information").getString("verification_code");
                sendVerificationCode.setVisibility(View.GONE);

            } catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... parameters){
            String phnNumber = parameters[0];
            String token = sharePref.get_data("registration_token");
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("mobile_number", phnNumber)///sent the team passcode
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.VERIFICATION_CODE_SEND_URL)
                    .addHeader("Authorization", "Token token=" + "915af2c12b0d4c4a1c73ec6d16ec37d3")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch(Exception e){
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }



    public class VerificationCodeResend extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Toast.makeText(MobileVarification.this,s,Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObject = new JSONObject(s);
                jsonObject.getJSONObject("mobile_verification_information").getString("mobile_number");
                verificationId = jsonObject.getJSONObject("mobile_verification_information").getString("verification_id");
                Toast.makeText(MobileVarification.this, jsonObject.getJSONObject("mobile_verification_information").getString("verification_id"), Toast.LENGTH_LONG).show();
                int serverTime = Integer.parseInt(jsonObject.getJSONObject("mobile_verification_information").getString("server_time"));
                int againRetryTime =Integer.parseInt(jsonObject.getJSONObject("mobile_verification_information").getString("again_retry_time"));

                if(againRetryTime > serverTime){
                    Toast.makeText(MobileVarification.this,"cannot try now",Toast.LENGTH_LONG).show();
                }
                jsonObject.getJSONObject("mobile_verification_information").getString("try_count");
                jsonObject.getJSONObject("mobile_verification_information").getString("verification_code");
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... parameters){
            String phnNumber = parameters[0];
            String verification_id = parameters[1];
            String token = sharePref.get_data("registration_token");

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("mobile_number", sharePref.get_data("mobile_number"))
                    .add("verification_id",sharePref.get_data("verification_id"))
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.VERIFICATION_CODE_RESEND_URL)
                    .addHeader("Authorization", "Token token=" + "915af2c12b0d4c4a1c73ec6d16ec37d3")
                    .post(requestBody)
                    .build();

            try{
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            }catch(Exception e){
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    public class VerifyCode extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Toast.makeText(MobileVarification.this,s,Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                jsonObject.getJSONObject("message").getString("detail");
                if(jsonObject.has("message")){
                    new MobileVarification.FetchConstant().execute(jsonObject.getString("current_mobile_sign_up_step"));
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... parameters){
            String verify_id = parameters[0];
            String verification_code = parameters[1];
            String mobile_number = parameters[2];
            String token = sharePref.get_data("registration_token");


            RequestBody requestBody = new FormEncodingBuilder()
                    .add("verification_code",verification_code)
                    .add("mobile_number", sharePref.get_data("mobile_number"))
                    .add("verification_id",sharePref.get_data("verification_id"))
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.VERIFICATION_CODE_VERIFY_URL)
                    .addHeader("Authorization", "Token token=" + "915af2c12b0d4c4a1c73ec6d16ec37d3")
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();

                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent nextActivity = new Intent(MobileVarification.this, RegistrationOwnInfo.class);
            nextActivity.putExtra("constants",s);
            startActivity(nextActivity);
        }

        @Override
        protected String doInBackground(String... parameters){

            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + parameters[0])
                    .build();

            try{
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
