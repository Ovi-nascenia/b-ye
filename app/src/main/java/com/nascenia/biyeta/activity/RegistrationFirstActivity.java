package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.nascenia.biyeta.utils.CalenderBanglaInfo;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RegistrationFirstActivity extends AppCompatActivity {

    SharePref sharePref;
    private Button male, female;
    //private IntlPhoneInput phoneInputView;
    Button buttonRegistration, buttonFacebookLogin;
    TextView buttonLogin;
    CallbackManager callbackManager;
    EditText email_edit_text, password_edit_text, name_edit_text, display_name_edit_text;
    OkHttpClient client;

    private String signInProviderGivenUserId = "";

    private static int genderValue = -1;

    private String email;
    private String realName;
    private String displayName;
    private String searchingFor;
    private String createdBy;
    private String uid;
    private String provider;
    private String profilePic;
    private String gender;
    private String birthday;
    private String mobileNumber;

    private ScrollView parentScrollView;

    private ProgressDialog progressDialog;

    private EditText dateOfBirthEditext;

    private final int DATE_OF_BIRTH_REQUEST_CODE = 2;

    private static final int facebook_request_code = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_first);

        client = new OkHttpClient();
        male = (Button) findViewById(R.id.man);
        female = (Button) findViewById(R.id.woman);
        callbackManager = CallbackManager.Factory.create();

        progressDialog = new ProgressDialog(RegistrationFirstActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(false);

        parentScrollView = (ScrollView) findViewById(R.id.scrollView);

        email_edit_text = (EditText) findViewById(R.id.email);

        password_edit_text = (EditText) findViewById(R.id.password);

        name_edit_text = (EditText) findViewById(R.id.name);

        display_name_edit_text = (EditText) findViewById(R.id.display_name);

        email_edit_text = (EditText) findViewById(R.id.email);
        //phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        //phoneInputView.setEmptyDefault("BD");

        dateOfBirthEditext = findViewById(R.id.date_Of_birth_editext);
        dateOfBirthEditext.setKeyListener(null);

        dateOfBirthEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(RegistrationFirstActivity.this, BirthDatePickerPopUpActivity.class),
                        DATE_OF_BIRTH_REQUEST_CODE);
            }
        });

        dateOfBirthEditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    startActivityForResult(
                            new Intent(RegistrationFirstActivity.this, BirthDatePickerPopUpActivity.class),
                            DATE_OF_BIRTH_REQUEST_CODE);
                }
            }
        });


        buttonLogin = (TextView) findViewById(R.id.login);

        sharePref = new SharePref(RegistrationFirstActivity.this);

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
                startActivity(new Intent(RegistrationFirstActivity.this, Login.class));

            }
        });

        buttonRegistration = (Button) findViewById(R.id.register_button);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
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


        dateOfBirthEditext.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAccount();
                    return true;
                }
                return false;
            }
        });
    }

    private void createAccount() {

        String gender = null;
        if (genderValue == 0) {
            gender = "female";
        } else if (genderValue == 1) {
            gender = "male";
        }
        sharePref.set_data("gender", gender);

        if (gender == null) {
            //Utils.ShowAlert(RegistrationFirstActivity.this, "পাত্র/পাত্রী নির্বাচন করুন");
            Toast.makeText(getBaseContext(), "পাত্র/পাত্রী নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        }

        if (email_edit_text.getText().length() == 0) {
            email_edit_text.requestFocus();
            email_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার ইমেইল অ্যাড্রেসটি পূরণ করুন", Toast.LENGTH_LONG).show();
            //  Utils.ShowAlert(RegistrationFirstActivity.this, "ইমেইল অ্যাড্রেস পূরণ করুন");
            return;
        }
        if (!Utils.isValidEmailAddress(email_edit_text.getText().toString())) {
            email_edit_text.requestFocus();
            email_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার ইমেইলটি সঠিক নয়", Toast.LENGTH_LONG).show();
            // Utils.ShowAlert(RegistrationFirstActivity.this, "ইমেইল টি সঠিক নয়");
            return;
        }
        if (password_edit_text.getText().length() == 0) {
            password_edit_text.requestFocus();
            password_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "পাসওয়ার্ড পূরণ করুন", Toast.LENGTH_LONG).show();
            //  Utils.ShowAlert(RegistrationFirstActivity.this, "পাসওয়ার্ড পূরণ করুন");
            return;
        }
        if (password_edit_text.getText().length() < 4) {
            password_edit_text.requestFocus();
            password_edit_text.setSelection(password_edit_text.getText().length());
            Toast.makeText(getBaseContext(), "পাসওয়ার্ড ন্যূনতম ৪ অক্ষর এর হতে হবে", Toast.LENGTH_LONG).show();
            // Utils.ShowAlert(RegistrationFirstActivity.this, "পাসওয়ার্ড ন্যূনতম ৮ অক্ষর এর হতে হবে");
            return;
        }
        if (name_edit_text.getText().length() == 0) {
            name_edit_text.requestFocus();
            name_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার নাম পূরণ করুন", Toast.LENGTH_LONG).show();
            // Utils.ShowAlert(RegistrationFirstActivity.this, "নাম পূরণ করুন");
            return;
        }
        if (display_name_edit_text.getText().length() == 0) {
            display_name_edit_text.requestFocus();
            display_name_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার ডিসপ্লে নাম পূরণ করুন", Toast.LENGTH_LONG).show();
            // Utils.ShowAlert(RegistrationFirstActivity.this, "ডিসপ্লে নাম পূরণ করুন");
            return;
        }

        if (dateOfBirthEditext.getText().length() == 0) {
            dateOfBirthEditext.requestFocus();
            dateOfBirthEditext.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার জন্ম তারিখ নির্বাচন করুন", Toast.LENGTH_LONG).show();
            // Utils.ShowAlert(RegistrationFirstActivity.this, "ডিসপ্লে নাম পূরণ করুন");
            return;
        }


        new RegistrationFirstActivity.RegistretionBasicInfoTask()
                .execute(email_edit_text.getText().toString(),
                        password_edit_text.getText().toString(),
                        name_edit_text.getText().toString(),
                        display_name_edit_text.getText().toString(),
                        "",
                        gender.equalsIgnoreCase("male")?"female":"male",
                        "own",
                        dateOfBirthEditext.getText().toString());


    }

    private void setScrollPosition(final int focus) {

        parentScrollView.getViewTreeObserver().
                addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                parentScrollView.post(new Runnable() {
                                    public void run() {
                                        parentScrollView.fullScroll(focus);
                                    }
                                });
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
                                    uid = loginResult.getAccessToken().getUserId();
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

                LoginManager.getInstance().logOut();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

//                Log.i("error", error.getMessage().toString());

            }
        });
    }


    private void showFacebookSignInResultDataOnView(JSONObject object) throws Exception {

        String user_id = object.getString("id");
        signInProviderGivenUserId = user_id;

      /*  passwordTextInputLayout.setVisibility(View.GONE);
        confirmPasswordTextInputLayout.setVisibility(View.GONE);*/

        if (object.has("email")) {
            email = object.getString("email");
        }

        if (object.has("first_name")) {
            realName = object.getString("name");
        }
//        if (object.has("last_name")) {
//            realName = realName + " " + object.getString("last_name");
////            displayName = object.getString("last_name");
//
//        }

        if (object.has("gender")) {
            gender = object.getString("gender");
            if (gender.equals("male")) {
                searchingFor = "female";
                sharePref.set_data("gender", "male");
            } else if (gender.equals("female")) {
                searchingFor = "male";
                sharePref.set_data("gender", "female");
            }
        }

        if (object.has("birthday")) {
            birthday = object.getString("birthday");
        }

        if(email==null || email.length() <=0 || realName==null || realName.length() <=0 || displayName==null
                || displayName.length()<=0 || birthday == null || birthday.length() <= 0) {

            Intent intent = new Intent(RegistrationFirstActivity.this,
                    FacebookRegistrationDataInput.class);
            intent.putExtra("email", email);
            intent.putExtra("first_name", realName);
            intent.putExtra("display_name", displayName);
            intent.putExtra("birthday", birthday);
            intent.putExtra("created_by", createdBy);
            intent.putExtra("uid", uid);
            intent.putExtra("provider", provider);
            startActivityForResult(intent, facebook_request_code);
        }else {
            callFacebookAccountCreate();

        }
    }

    private void callFacebookAccountCreate() {
        provider = "facebook";
        profilePic = "";
        createdBy = "own";
        mobileNumber = "";

        String fbSignUp = new StringBuilder().append("{")
                .append("\"facebook_auth\":")
                .append("{")
                .append("\"email\":")
                .append("\"")
                .append(email)
                .append("\"")
                .append(",")
                .append("\"real_name\":")
                .append("\"")
                .append(realName)
                .append("\"")
                .append(",")
                .append("\"display_name\":")
                .append("\"")
                .append(displayName)
                .append("\"")
                .append(",")
                .append("\"mobile_number\":")
                .append("\"")
                .append("")
                .append("\"")
                .append(",")
                .append("\"searching_for\":")
                .append("\"")
                .append(searchingFor)
                .append("\"")
                .append(",")
                .append("\"created_by\":")
                .append("\"")
                .append(createdBy)
                .append("\"")
                .append(",")
                .append("\"uid\":")
                .append("\"")
                .append(uid)
                .append("\"")
                .append(",")
                .append("\"provider\":")
                .append("\"")
                .append(provider)
                .append("\"")
                .append(",")
                .append("\"profile_pic\":")
                .append("\"")
                .append("")
                .append("\"")
                .append(",")
                .append("\"dateofbirth\":")
                .append("\"")
                .append(birthday)
                .append("\"")
                .append(",")
                .append("\"gender\":")
                .append("\"")
                .append(gender.equalsIgnoreCase("female")?"male":"female")
                .append("\"")
                .append("}")
                .append("}").toString();
        Log.e("json", fbSignUp);
        new RegistrationFirstActivity.FbRegistration().execute(fbSignUp, Utils.FB_SIGNUP);
    }


//    private void setRegistrationDataWithFacebook(JSONObject object) {
//        try{
//        if(object.has("email")) {
//            email_edit_text.setText(object.getString("email"));
//            email_edit_text.setEnabled(false);
//        }
//        else
//            email_edit_text.setEnabled(true);
//
//        if(object.has("email")) {
//            email_edit_text.setText(object.getString("email"));
//            email_edit_text.setEnabled(false);
//        }
//        else
//            email_edit_text.setEnabled(true);
//            password_edit_text, name_edit_text, display_name_edit_text
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            //String message = data.getStringExtra("DATE_OF_BIRTH");

            if (data != null && !data.getStringExtra("DATE_OF_BIRTH").equalsIgnoreCase("reject")) {
                dateOfBirthEditext.setText(data.getStringExtra("DATE_OF_BIRTH"));

                /*String[] dateOfbirth = message.split("/");
                String date = Integer.parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfbirth[0])) + "";
                String month = Integer.parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfbirth[1])) + "";
                String year = Integer.parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfbirth[2])) + "";

                Log.i("resultdata: ", message + " " + date + "/" + month + "/"+ year);*/
            }
        } else if(requestCode == facebook_request_code)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                email = data.getStringExtra("email");
                realName = data.getStringExtra("first_name");
                displayName = data.getStringExtra("display_name");
                birthday = data.getStringExtra("birthday");
                callFacebookAccountCreate();
            }
        }
    }

    public class RegistretionBasicInfoTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(RegistrationFirstActivity.this, s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);

            //Log.e("LoginData", s);
            if (s == null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Utils.ShowAlert(RegistrationFirstActivity.this, getString(R.string.no_internet_connection));
            } else {

                try {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //convert string to json object
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Token", s);
                    if (jsonObject.has("errors")) {
                        Utils.ShowAlert(RegistrationFirstActivity.this, jsonObject.getJSONObject("errors").getString("detail"));
                        email_edit_text.requestFocus();
                    } else {
                        //sharePref.set_data("registration_token",jsonObject.getString("auth_token"));
                        sharePref.set_data("token", jsonObject.getString("auth_token"));
//                        sharePref.set_data("gender", gender);
                        Intent mobileVerification = new Intent(RegistrationFirstActivity.this, MobileVarification.class);
                        startActivity(mobileVerification);
                        finish();
                    }


                } catch (Exception e) {

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
            String dateOfBirth[] = parameters[7].split("/");

            String convertedEnglishDateFromBanglaDate = Integer.parseInt(
                    CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfBirth[0])) + "";

            String convertedEnglishMonthFromBanglaMonth = Integer.parseInt(
                    CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfBirth[1])) + "";

            String convertedEglishYearFromBanglaYear = Integer.parseInt(
                    CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfBirth[2])) + "";

            Log.i("resultdata: ", convertedEglishYearFromBanglaYear + "/" +
                    convertedEnglishMonthFromBanglaMonth + "/" +  convertedEnglishDateFromBanglaDate);


            RequestBody requestBody = new FormEncodingBuilder()
                    .add("email", email)///sent the team passcode
                    .add("password", password)
                    .add("real_name", realName) //FCM token
                    .add("display_name", displayName)
                    .add("mobile_number", mobileNumber)
                    .add("searching_for", searchingFor)
                    .add("created_by", createdBy)
                    .add("dateofbirth", convertedEglishYearFromBanglaYear + "/" +
                            convertedEnglishMonthFromBanglaMonth + "/" + convertedEnglishDateFromBanglaDate)
                    .build();


            Request request = new Request.Builder()
                    .url(Utils.REGISTRATION_FIRST_PAGE_URL)
                    .post(requestBody)
                    .build();
//            Log.i("regis", Utils.REGISTRATION_FIRST_PAGE_URL+" "+email+" "+password+" "+realName+" "+displayName);

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


    class FbRegistration extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(RegistrationFirstActivity.this);
        ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            if (!progress.isShowing())
                progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {
                progress.cancel();
                Utils.ShowAlert(RegistrationFirstActivity.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
//                        jsonObject.getJSONObject("errors").getString("detail");
                        // Toast.makeText(RegistrationFirstActivity.this, jsonObject.getJSONObject("errors").getString("detail"), Toast.LENGTH_LONG).show();
                        Utils.ShowAlert(RegistrationFirstActivity.this, jsonObject.getJSONObject("errors").getString("detail"));
                    } else {
                        sharePref.set_data("token", jsonObject.getString("auth_token"));
                        Intent intent = new Intent(RegistrationFirstActivity.this, MobileVarification.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(RegistrationFirstActivity.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, strings[0]);
            Request request = new Request.Builder()
                    .url(strings[1])
                    .post(body)
                    .build();
            Response response = null;
            String responseString = null;
            try {
                response = client.newCall(request).execute();
                responseString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }
}
