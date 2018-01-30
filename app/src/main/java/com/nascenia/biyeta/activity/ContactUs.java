package com.nascenia.biyeta.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.JsonObject;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by god father on 3/22/2017.
 */

public class ContactUs extends CustomActionBarActivity {

    private Tracker mTracker;
    private AnalyticsApplication application;
    EditText email, phoneNumber, userName, message;
    Button sendButton;
    Response response;
    OkHttpClient okHttpClient = new OkHttpClient();
    TextView help;

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_page);
        setUpToolBar(getString(R.string.contact_title), this);
        setUpId();

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());
        help = findViewById(R.id.help_number);
        Linkify.addLinks(help, Linkify.WEB_URLS);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + "+8809666778779"));
                if (ActivityCompat.checkSelfPermission(ContactUs.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[]
                    // permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the
                    // documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Google Analytics*/
        mTracker.setScreenName("Contact Us : "+getString(R.string.contact_title));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    void setUpId() {
        phoneNumber = (EditText) findViewById(R.id.phone_number_contact_us);
        email = (EditText) findViewById(R.id.email_address_contact_us);
        userName = (EditText) findViewById(R.id.user_name_contact_us);
        message = (EditText) findViewById(R.id.message_contact_us);


        sendButton = (Button) findViewById(R.id.send_button_contact_us);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidEmail(email.getText().toString())) {
                    email.setError("Invalid email");
                }

                if (!isValidCellPhone(phoneNumber.getText().toString())) {
                    phoneNumber.setError("Invalid Phone Number");
                }
                if (userName.getText().toString().trim().equals(""))
                    userName.setError("Enter a user name");
                if (message.getText().toString().trim().equals(""))
                    message.setError("Enter Message");

                if (isValidEmail(email.getText().toString()) && isValidCellPhone(phoneNumber.getText().toString())
                        && !message.getText().toString().trim().equals("") && !userName.getText().toString().trim().equals("")) {
                    new SendData().execute(userName.getText().toString(), email.getText().toString(), phoneNumber.getText().toString(), message.getText().toString());
                }


            }
        });


    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }

    public boolean isValidCellPhone(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public class SendData extends AsyncTask<String, String, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Success", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("message")) {
                    Toast.makeText(ContactUs.this, jsonObject.getJSONObject("message").get("detail").toString(), Toast.LENGTH_LONG).show();
                    phoneNumber.setText("");
                    message.setText("");
                    email.setText("");
                    userName.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "onPostExecute", "ContactUs", e.getMessage().toString(), mTracker);
            }


        }

        @Override
        protected String doInBackground(String... dataSet) {
            String userName = dataSet[0];
            String email = dataSet[1];
            String phoneNumber = dataSet[2];
            String message = dataSet[3];

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("name", userName)
                    .add("contact_no", phoneNumber)
                    .add("email", email)
                    .add("message", message)
                    .build();


            SharePref sharePref = new SharePref(ContactUs.this);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(Utils.Base_URL+"/api/v1/contacts")
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = okHttpClient.newCall(request).execute();
                String jsonData = response.body().string();
                Log.e("Contact Us", response.toString());
                return jsonData;

            } catch (Exception e) {
                Log.e("Contact Us", e.toString());
               // application.trackEception(e, "SendData/doInBackground", "ContactUs", e.getMessage().toString(), mTracker);
                return null;

            }


        }
    }
}
