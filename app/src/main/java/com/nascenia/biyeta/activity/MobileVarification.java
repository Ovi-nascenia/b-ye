package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class MobileVarification extends AppCompatActivity {
    ImageView back;
    private IntlPhoneInput phoneInputView;
    int nextCounter = 0;
    SharePref sharePref;
    OkHttpClient client;
    Button sendVerificationCode, verify;
    String verificationId;
    TextView resendCode, login;
    EditText verificationCode;
    LinearLayout beforeSendingVerificationCode, afterSendingVerificationCode;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_varification);
        verify = (Button) findViewById(R.id.verify);
        back = (ImageView) findViewById(R.id.backPreviousActivityImage);

        progressDialog = new ProgressDialog(MobileVarification.this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(false);

        resendCode = (TextView) findViewById(R.id.text3);
        verificationCode = (EditText) findViewById(R.id.verification_code);
        verificationCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (nextCounter == 5) {
                        //Utils.ShowAlert(MobileVarification.this,"");
                        verificationCode.setEnabled(false);
                    } else {
                        nextCounter++;
                        new VerifyCode().
                                execute(verificationId, verificationCode.getText().toString(),
                                        sharePref.get_data("mobile_number"));
                    }
                    return true;
                }
                return false;
            }
        });

        phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        phoneInputView.setEmptyDefault("BD");

        sharePref = new SharePref(MobileVarification.this);
        client = new OkHttpClient();

        sendVerificationCode = (Button) findViewById(R.id.sendVerificationCode);


        beforeSendingVerificationCode = (LinearLayout) findViewById(R.id.before_sending_varification_code);
        afterSendingVerificationCode = (LinearLayout) findViewById(R.id.after_sending_verification_code);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileVarification.this, Login.class));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nextCounter == 5) {
                            //Utils.ShowAlert(MobileVarification.this,"");
                            verificationCode.setEnabled(false);
                        } else {
                            if (!verificationCode.getText().toString().isEmpty()) {
                                nextCounter++;
                                new VerifyCode().
                                        execute(verificationId, verificationCode.getText().toString(),
                                                sharePref.get_data("mobile_number"));
                            } else {
                                Toast.makeText(getBaseContext(), getString(R.string.write_varification_code_message),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
        );
        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneInputView.getText() == null) {
                    phoneInputView.requestFocus();
                    Utils.ShowAlert(MobileVarification.this, "মোবাইল নাম্বার দিন");
                } else if (!phoneInputView.isValid()) {
                    phoneInputView.requestFocus();
                    Utils.ShowAlert(MobileVarification.this, "মোবাইল নাম্বারটি সঠিক নয়");
                } else {
                    new VerificationCodeSend().execute(phoneInputView.getText().toString());
                }

            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activate the editext

                new VerificationCodeResend().
                        execute(sharePref.get_data("mobile_number"), sharePref.get_data("verification_id"));
            }
        });

        beforeSendingVerificationCode.setVisibility(View.VISIBLE);
        afterSendingVerificationCode.setVisibility(View.GONE);

    }


    public class VerificationCodeSend extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Utils.ShowAlert(MobileVarification.this, getString(R.string.no_internet_connection));
            } else {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                beforeSendingVerificationCode.setVisibility(View.GONE);
                afterSendingVerificationCode.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("mobilersp",jsonObject.toString());
                    jsonObject.getJSONObject("mobile_verification_information").getString("mobile_number");
                    verificationId = jsonObject.getJSONObject("mobile_verification_information").getString("verification_id");
                    sharePref.set_data("verification_id", jsonObject.getJSONObject("mobile_verification_information").getString("verification_id"));
                    sharePref.set_data("mobile_number", jsonObject.getJSONObject("mobile_verification_information").getString("mobile_number"));
                    //  Toast.makeText(MobileVarification.this, s, Toast.LENGTH_LONG).show();
                    Log.i("verificationdata", s);

                    jsonObject.getJSONObject("mobile_verification_information").getString("server_time");
                    jsonObject.getJSONObject("mobile_verification_information").getString("again_retry_time");
                    jsonObject.getJSONObject("mobile_verification_information").getString("try_count");
                    jsonObject.getJSONObject("mobile_verification_information").getString("verification_code");
                    sendVerificationCode.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... parameters) {
            String phnNumber = parameters[0];
            String token = sharePref.get_data("registration_token");
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("mobile_number", phnNumber)///sent the team passcode
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.VERIFICATION_CODE_SEND_URL)
                    .addHeader("Authorization", "Token token=" + token)
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
            progressDialog.show();
        }
    }


    public class VerificationCodeResend extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Utils.ShowAlert(MobileVarification.this, getString(R.string.no_internet_connection));
            } else {
                try {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(s);
                    jsonObject.getJSONObject("mobile_verification_information").getString("mobile_number");
                    verificationId = jsonObject.getJSONObject("mobile_verification_information").getString("verification_id");
                    //Toast.makeText(MobileVarification.this, jsonObject.getJSONObject("mobile_verification_information").getString("verification_id"), Toast.LENGTH_LONG).show();
                    int serverTime = Integer.parseInt(jsonObject.getJSONObject("mobile_verification_information").getString("server_time"));
                    int againRetryTime = Integer.parseInt(jsonObject.getJSONObject("mobile_verification_information").getString("again_retry_time"));

                    if (againRetryTime > serverTime) {
                        int ms = againRetryTime - serverTime;
                        int SECOND = 1;
                        String text = "";
                        int MINUTE = 60 * SECOND;
                        int HOUR = 60 * MINUTE;
                        if (ms > HOUR) {
                            text += Utils.convertEnglishYearDigittoBangla(ms / HOUR) + " ঘণ্টা ";
                            ms %= HOUR;
                        }
                        if (ms > MINUTE) {
                            text += Utils.convertEnglishYearDigittoBangla(ms / MINUTE) + " মিনিট ";
                            ms %= MINUTE;
                        }
                        Utils.ShowAlert(MobileVarification.this, text + "পর পুনরায় ভেরিফিকেশন কোড পাঠাতে পারবেন।");
                    } else {
                        nextCounter = 0;
                        verificationCode.setEnabled(true);
                        Utils.ShowAlert(MobileVarification.this, "ভেরিফিকেশন কোড পাঠানো হয়েছে।");
                    }
                    jsonObject.getJSONObject("mobile_verification_information").getString("try_count");
                    jsonObject.getJSONObject("mobile_verification_information").getString("verification_code");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
            String phnNumber = parameters[0];
            String verification_id = parameters[1];
            String token = sharePref.get_data("registration_token");

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("mobile_number", sharePref.get_data("mobile_number"))
                    .add("verification_id", sharePref.get_data("verification_id"))
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.VERIFICATION_CODE_RESEND_URL)
                    .addHeader("Authorization", "Token token=" + token)
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
            progressDialog.show();
        }
    }


    public class VerifyCode extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (s == null) {
                Utils.ShowAlert(MobileVarification.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("mobilersp: ",jsonObject.toString());
                    if (jsonObject.has("message")) {
                        String details = jsonObject.getJSONObject("message").getString("detail");
                       Toast.makeText(getBaseContext(), details, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MobileVarification.this,Login.class));
                        finish();
                        // new MobileVarification.FetchConstant().execute(jsonObject.getString("current_mobile_sign_up_step"));
                    } else if (jsonObject.has("errors")) {
                        Utils.ShowAlert(MobileVarification.this, "কোডটি ভুল হয়েছে।\nসঠিক কোড প্রদান করুন।");
                        //if(jsonObject.getJSONObject("errors").getString("detail").equals("'verification code did not match")){
                        //}

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... parameters) {
            String verify_id = parameters[0];
            String verification_code = parameters[1];
            String mobile_number = parameters[2];
            String token = sharePref.get_data("registration_token");


            RequestBody requestBody = new FormEncodingBuilder()
                    .add("verification_code", verification_code)
                    .add("mobile_number", sharePref.get_data("mobile_number"))
                    .add("verification_id", sharePref.get_data("verification_id"))
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.VERIFICATION_CODE_VERIFY_URL)
                    .addHeader("Authorization", "Token token=" + token)
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
            progressDialog.show();
        }
    }


    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent nextActivity = new Intent(MobileVarification.this, RegistrationOwnInfo.class);
            nextActivity.putExtra("constants", s);
            startActivity(nextActivity);
        }

        @Override
        protected String doInBackground(String... parameters) {

            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + parameters[0])
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
}
