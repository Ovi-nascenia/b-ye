package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.Manifest;
import com.nascenia.biyeta.fcm.MyFirebaseInstanceIDService;
import com.nascenia.biyeta.model.InboxAllThreads.Example;
import com.nascenia.biyeta.model.loginInfromation.LoginInformation;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by user on 1/5/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Tracker mTracker;
    private AnalyticsApplication application;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private String user_name=null;
    //get the password from Edit Text
    private String password=null;
    String imei = null;
    //icon image
    //big image load through glide
    ImageView icon;
    //facebook login button
    LoginButton buttonFacebookLogin;
    CallbackManager callbackManager;
    //for open new account
    private LinearLayout new_account;
    private EditText etPassword, etUserName;
    private Button buttonSubmit;
    private ProgressBar progressBar;
    private  Context context = this;

    ///sub url
    String SUB_URL = "sign-in";
    OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the Okhttp
        client = new OkHttpClient();
        setContentView(R.layout.login);


        //hide action bar
        getSupportActionBar().hide();


//is off right now by masum
       // new RegistrationTokenSending().execute();


        //set all id//
        set_id();

        ///load large icon with glide
        Glide.with(this)
                .load(R.drawable.icon_content)
                .into(icon);


        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());

    }

    void set_id() {


        new_account = (LinearLayout) findViewById(R.id.new_accunt_test);
        new_account.setOnClickListener(this);

        etUserName = (EditText) findViewById(R.id.login_email);
        etPassword = (EditText) findViewById(R.id.login_password);

        buttonSubmit = (Button) findViewById(R.id.login_submit);
        buttonSubmit.setOnClickListener(this);

        buttonFacebookLogin = (LoginButton) findViewById(R.id.login_button);
        buttonFacebookLogin.setBackgroundResource(R.drawable.facebook_icon);
        buttonFacebookLogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        //buttonFacebookLogin.setOnClickListener(this);

        buttonFacebookLogin.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();


        buttonFacebookLogin.setText("ফেসবুকের সাহায্যে লগইন করুন");
        buttonFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.e("LoginOvi", loginResult.getAccessToken().toString());


                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String uid = loginResult.getAccessToken().getUserId();
                                    String email = object.getString("email");

                                    //send data from facebook to our server
                                    new LoginByFacebook().execute(Utils.FACEBOOK_LOGIN_URL, uid, "facebook", email);
                                    Log.e("FacebookData", email + " " + loginResult.getAccessToken().getToken() + "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
//                                    application.trackEception(e, "GraphRequest/onCompleted", "Login", e.getMessage().toString(), mTracker);
                                }
                                // 01/31/1980 format
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Utils.ShowAlert(Login.this, getString(R.string.no_internet_connection));
            }
        });


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        icon = (ImageView) findViewById(R.id.icon_view);

        etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 0 || event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (!etPassword.getText().toString().trim().equals(""))
                        checkValidation();
                    //Toast.makeText(Login.this,"handle now",Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) Login.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Login.this.getCurrentFocus().getWindowToken(), 0);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.login_button:


                loginWithFacebook();


                ///call facebook api for login
                break;
            case R.id.login_submit:

                checkValidation();

                break;
            case R.id.new_accunt_test:
                //open a link in a brawer

                String url = "http://www.biyeta.com/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));
                break;

        }


    }

    void checkValidation() {

        //get the user name from Edit text
        user_name = etUserName.getText().toString();
        //get the password from Edit Text
        password = etPassword.getText().toString();

        ///check the user_name and password is empty
        if (user_name.trim().equals("") || password.trim().equals("")) {
            //  Toast.makeText(Login.this,"Fill the both field",Toast.LENGTH_SHORT).show();
            Utils.ShowAlert(Login.this, "ইমেইল এবং পাসওয়ার্ড  পূরণ করুন");


        }
        //excute  the network operation
        //
        else {
            if (!Utils.isOnline(Login.this)) {
                Utils.ShowAlert(Login.this, getString(R.string.no_internet_connection));
            } else
                checkConnection();

        }
    }

    public void loginWithFacebook() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        buttonSubmit.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        /*Google Analytics*/
        mTracker.setScreenName("লগইন");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public class LoginByFacebook extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("facebook login", s);


            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("message")) {
                    Utils.ShowAlert(Login.this, jsonObject.getJSONObject("message").get("detail").toString());
                    LoginManager.getInstance().logOut();
                    buttonFacebookLogin.setText("ফেসবুকের সাহায্যে লগইন করুন");
                } else {
                    Gson gson = new Gson();
                    InputStream is = new ByteArrayInputStream(s.getBytes());
                    InputStreamReader isr = new InputStreamReader(is);
                    LoginInformation response = gson.fromJson(isr, LoginInformation.class);

                    if (response.getLoginInformation().getProfileComplete()) {
                        //insert the token in Sharepreference

                        try {


                            SharePref sharePref = new SharePref(Login.this);


                            sharePref.set_data("token", response.getLoginInformation().getAuthToken());
                            sharePref.set_data("user_id", response.getLoginInformation().getCurrentUserSignedIn() + "");
                            sharePref.set_data("profile_picture", response.getLoginInformation().getProfilePicture());
                            sharePref.set_data("gender", response.getLoginInformation().getGender());
                            sharePref.set_data("display_name", response.getLoginInformation().getDisplayName());
                            sharePref.set_data("mobile_verified", response.getLoginInformation().getMobileVerified() + "");


                            //check display name

                            // check the mobile verify screen

                            if (response.getLoginInformation().getMobileVerified()) {
                                startActivity(new Intent(Login.this, HomeScreen.class));
                                finish();
                            } else {

                                startActivity(new Intent(Login.this, MobileVerification.class));
                                // finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Utils.ShowAlert(Login.this, "আপনার ইমেইল অথবা পাসওয়ার্ড সঠিক নয়");
                            buttonSubmit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
//                            application.trackEception(e, "LoginByFacebook/onPostExecute", "Login", e.getMessage().toString(), mTracker);
                        }
                    } else {
                        Utils.ShowAlert(Login.this, getString(R.string.incomplete_profile_message));
                        LoginManager.getInstance().logOut();
                        buttonFacebookLogin.setText("ফেসবুকের সাহায্যে লগইন করুন");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginByFacebook/onPostExecute", "Login", e.getMessage().toString(), mTracker);
            }
        }

        @Override
        protected String doInBackground(String... parameters) {


            RequestBody requestBody = new FormEncodingBuilder()
                    .add("uid", parameters[1])
                    .add("provider", parameters[2])
                    .add("email", parameters[3])
                    .build();


            Request request = new Request.Builder().url(parameters[0]).post(requestBody).build();
            String responseString = null;
            try {
                Response response = client.newCall(request).execute();
                responseString = response.body().string();
//                return responseString;
            } catch (Exception e) {

                e.printStackTrace();
//                application.trackEception(e, "LoginByFacebook/doInBackground", "Login", e.getMessage().toString(), mTracker);
//                return null;
            }

            return responseString;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imei=Utils.deviceIMEI(context);
                    new LoginRequest().execute(user_name, password);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Login.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public  void checkConnection()
    {
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
            Log.e("version", String.valueOf(Build.VERSION.SDK_INT));

            if (ContextCompat.checkSelfPermission(Login.this,
                    android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Login.this,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
            else{
                imei=Utils.deviceIMEI(context);
                new LoginRequest().execute(user_name, password);
            }

    }

    //
    private class LoginRequest extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String password = params[1];
            String regtoken= FirebaseInstanceId.getInstance().getToken();
            Log.e("back", id + "---" + password + "---"+ regtoken);




            String s = String.valueOf(Build.VERSION.SDK_INT);


            RequestBody requestBody = new FormEncodingBuilder()
                    .add("user_login[email]", id)///sent the team passcode
                    .add("user_login[password]", password)
                    .add("user_login[registration_token]",regtoken) //FCM token
                    .add("user_login[imei]",imei)
                    .build();

            //   //imei of device


            Request request = new Request.Builder()
                    .url(Constant.BASE_URL + SUB_URL)
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Log.e("LoginData", s);
            if (s == null) {
                Utils.ShowAlert(Login.this, getString(R.string.no_internet_connection));
                buttonSubmit.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {

                try {
                    //convert string to json object
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Token",s);
                    if (jsonObject.has("errors")) {
                        Utils.ShowAlert(Login.this, jsonObject.getJSONArray("errors").getJSONObject(0).getString("detail"));
                        buttonSubmit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else if (Boolean.parseBoolean(jsonObject.getJSONObject("login_information").getString("is_complete"))==false) {
                        //getString("display_name").equals("null")
                        buttonSubmit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Utils.ShowAlert(Login.this, getString(R.string.incomplete_profile_message));
                    }

                   else if (Boolean.parseBoolean(jsonObject.getJSONObject("login_information").
                            getString("is_complete")) == true) {

                        Gson gson = new Gson();
                        InputStream is = new ByteArrayInputStream(s.getBytes());
                        InputStreamReader isr = new InputStreamReader(is);
                        LoginInformation response = gson.fromJson(isr, LoginInformation.class);


                        //insert the token in Sharepreference

                        try {


                            SharePref sharePref = new SharePref(Login.this);

                            sharePref.set_data("token", response.getLoginInformation().getAuthToken());
                            sharePref.set_data("user_id", response.getLoginInformation().getCurrentUserSignedIn() + "");
                            sharePref.set_data("profile_picture", response.getLoginInformation().getProfilePicture());
                            sharePref.set_data("gender", response.getLoginInformation().getGender());
                            sharePref.set_data("display_name", response.getLoginInformation().getDisplayName());
                            sharePref.set_data("mobile_verified", response.getLoginInformation().getMobileVerified() + "");


                            // check the mobile verify screen

                            if (response.getLoginInformation().getMobileVerified()) {
                                startActivity(new Intent(Login.this, HomeScreen.class));
                                finish();
                            } else {

                                startActivity(new Intent(Login.this, MobileVerification.class));
                                // finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Utils.ShowAlert(Login.this, "আপনার ইমেইল অথবা পাসওয়ার্ড সঠিক নয়");
                            buttonSubmit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
//                            application.trackEception(e, "LoginRequest/onPostExecute", "Login", e.getMessage().toString(), mTracker);
                        }
                    } else {
                        buttonSubmit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Utils.ShowAlert(Login.this, getString(R.string.incomplete_profile_message));
                    }


                } catch (JSONException e) {
                    Log.e("error", "JSON error");
                    e.printStackTrace();
                    Utils.ShowAlert(Login.this, "আপনার ইমেইল অথবা পাসওয়ার্ড সঠিক নয়");
                    buttonSubmit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
//                    application.trackEception(e, "LoginRequest/onPostExecute", "Login", e.getMessage().toString(), mTracker);

                }
            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            buttonSubmit.setVisibility(View.GONE);

        }
    }





    private class RegistrationTokenSending extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String token= FirebaseInstanceId.getInstance().getToken();
            System.out.println("MainActivity.onCreate: " + token);

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("registration_token", token)///sent the team passcode
                    .build();

            Request request = new Request.Builder()
                    .url(Utils.REGISTRATION_TOKEN_SENDER_URL )
                    .post(requestBody)
                    .build();


            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                System.out.println(responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected void onPreExecute() {

        }
    }

}

