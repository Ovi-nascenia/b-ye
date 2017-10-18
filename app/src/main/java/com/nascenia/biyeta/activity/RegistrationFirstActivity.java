package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.Utility;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.model.loginInfromation.LoginInformation;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RegistrationFirstActivity extends AppCompatActivity {

    SharePref sharePref;
    private Button male,female;
    private IntlPhoneInput phoneInputView;
    Button buttonRegistration, buttonFacebookLogin, buttonLogin;
    CallbackManager callbackManager;
    EditText email_edit_text, password_edit_text, name_edit_text, display_name_edit_text;
    OkHttpClient client;

    private String provider = "";
    private String signInProviderGivenUserId = "";

    private static int genderValue = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_first);
        client = new OkHttpClient();
        male = (Button) findViewById(R.id.man);
        female = (Button) findViewById(R.id.woman);
        callbackManager = CallbackManager.Factory.create();

        email_edit_text = (EditText) findViewById(R.id.email);

        password_edit_text = (EditText) findViewById(R.id.password);

        name_edit_text = (EditText) findViewById(R.id.name);

        display_name_edit_text = (EditText) findViewById(R.id.display_name);

        email_edit_text = (EditText) findViewById(R.id.email);
        phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        phoneInputView.setEmptyDefault("BD");

        buttonLogin = (Button) findViewById(R.id.login);

         sharePref= new SharePref(RegistrationFirstActivity.this);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //male.setHeight(35);
                //female.setHeight(35);
                male.setBackgroundColor(getResources().getColor(R.color.back_varify_text_view_1));
                male.setTextColor(getResources().getColor(R.color.white));
                female.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
                female.setTextColor(getResources().getColor(R.color.back_varify_text_view_1));
                genderValue = 0;
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // male.setHeight(35);
                female.setHeight(35);
                female.setBackgroundColor(getResources().getColor(R.color.back_varify_text_view_1));
                female.setTextColor(getResources().getColor(R.color.white));
                male.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
                male.setTextColor(getResources().getColor(R.color.back_varify_text_view_1));
                genderValue = 1;
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationFirstActivity.this, Login.class ));

            }
        });

        buttonRegistration = (Button) findViewById(R.id.register_button);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender = null;
                if(genderValue==0){
                    gender = "male";
                }
                else if(genderValue==1){
                    gender = "female";
                }
                Toast.makeText(RegistrationFirstActivity.this, gender,Toast.LENGTH_LONG).show();

                if(gender == null)
                {
                    Utils.ShowAlert(RegistrationFirstActivity.this,"পাত্র/পাত্রী নির্বাচন করুন");
                }
                else if(email_edit_text.getText().length()==0){
                    email_edit_text.requestFocus();
                    email_edit_text.setSelection(0);
                    Utils.ShowAlert(RegistrationFirstActivity.this,"ইমেইল অ্যাড্রেস পূরণ করুন");
                }
                else if(password_edit_text.getText().length()==0){
                    password_edit_text.requestFocus();
                    password_edit_text.setSelection(0);
                    Utils.ShowAlert(RegistrationFirstActivity.this,"পাসওয়ার্ড পূরণ করুন");
                }
                else if(name_edit_text.getText().length()==0){
                    name_edit_text.requestFocus();
                    name_edit_text.setSelection(0);
                    Utils.ShowAlert(RegistrationFirstActivity.this,"নাম পূরণ করুন");
                }
                else if(display_name_edit_text.getText().length()==0){
                    display_name_edit_text.requestFocus();
                    Utils.ShowAlert(RegistrationFirstActivity.this,"ডিসপ্লে নাম পূরণ করুন");
                    display_name_edit_text.setSelection(0);
                }
                else if(phoneInputView.getText().length()==0){
                    phoneInputView.requestFocus();
                    Utils.ShowAlert(RegistrationFirstActivity.this,"মোবাইল নাম্বার দিন");
                }
                else if(!Utils.isValidEmailAddress(email_edit_text.getText().toString())){
                    email_edit_text.requestFocus();
                    email_edit_text.setSelection(0);
                    Utils.ShowAlert(RegistrationFirstActivity.this,"ইমেইল টি সঠিক নয়");
                }
                else if(password_edit_text.getText().length()<8)
                {
                    password_edit_text.requestFocus();
                    password_edit_text.setSelection(password_edit_text.getText().length());
                    Utils.ShowAlert(RegistrationFirstActivity.this,"পাসওয়ার্ড ন্যূনতম ৮ অক্ষর এর হতে হবে");
                }
                else if(!phoneInputView.isValid()){
                    phoneInputView.requestFocus();
                    Utils.ShowAlert(RegistrationFirstActivity.this,"মোবাইল নাম্বারটি সঠিক নয়");
                }
                else{
                    new RegistrationFirstActivity.RegistretionBasicInfoTask().execute(email_edit_text.getText().toString(), password_edit_text.getText().toString(),name_edit_text.getText().toString(),display_name_edit_text.getText().toString(),phoneInputView.getText().toString(),gender,"own");
                }

            }
        });

        buttonFacebookLogin = (Button) findViewById(R.id.login_button);
        buttonFacebookLogin.setBackgroundResource(R.drawable.facebook_icon);
        buttonFacebookLogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        buttonFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFbSignup();
            }
        });


    }


    private void onFbSignup() {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String uid = loginResult.getAccessToken().getUserId();
                                    showFacebookSignInResultDataOnView(object);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,link,birthday,first_name,last_name,gender");
                request.setParameters(parameters);
                request.executeAsync();

                //isFacebookLoginComplete = true;
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                Log.i("error", error.getMessage().toString());

            }
        });
    }


    private void showFacebookSignInResultDataOnView(JSONObject object) throws Exception {

        String user_id = object.getString("id");
        signInProviderGivenUserId = user_id;

      /*  passwordTextInputLayout.setVisibility(View.GONE);
        confirmPasswordTextInputLayout.setVisibility(View.GONE);*/

        if (object.has("email")) {

        }
        if (object.has("last_name")) {
            Toast.makeText(this,"done",Toast.LENGTH_LONG).show();
        }
        if (object.has("first_name")) {

        }

        if (object.has("gender")) {

        }
        if (object.has("birthday")) {

        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public class RegistretionBasicInfoTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(RegistrationFirstActivity.this, s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);

            //Log.e("LoginData", s);
            if (s == null) {
                Utils.ShowAlert(RegistrationFirstActivity.this, getString(R.string.no_internet_connection));
            } else {

                try {
                    //convert string to json object
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Token", s);
                    if (jsonObject.has("errors")) {
                        Utils.ShowAlert(RegistrationFirstActivity.this, jsonObject.getJSONObject("errors").getString("detail"));
                        email_edit_text.requestFocus();

                    }
                    else{
                        sharePref.set_data("registration_token",jsonObject.getString("auth_token") );
                        Intent mobileVerification = new Intent(RegistrationFirstActivity.this, MobileVarification.class);
                        startActivity(mobileVerification);
                    }

                } catch (Exception e){

                }
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
            String email = parameters[0];
            String password = parameters[1];
            String realName = parameters[2];
            String displayName = parameters[3];
            String mobileNumber = parameters[4];
            String searchingFor = parameters[5];
            String createdBy = parameters[6];


            RequestBody requestBody = new FormEncodingBuilder()
                    .add("email", email)///sent the team passcode
                    .add("password", password)
                    .add("real_name",realName) //FCM token
                    .add("display_name",displayName)
                    .add("mobile_number",mobileNumber)
                    .add("searching_for",searchingFor)
                    .add("created_by",createdBy)
                    .build();



            Request request = new Request.Builder()
                    .url(Utils.REGISTRATION_FIRST_PAGE_URL)
                    .post(requestBody)
                    .build();
            Log.i("regis", Utils.REGISTRATION_FIRST_PAGE_URL+" "+email+" "+password+" "+realName+" "+displayName);

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
