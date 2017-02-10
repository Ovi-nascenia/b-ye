package biyeta.nas.biyeta.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import biyeta.nas.biyeta.appData.SharePref;
import biyeta.nas.biyeta.constant.Constant;
import biyeta.nas.biyeta.R;
import biyeta.nas.biyeta.utils.Utils;

/**
 * Created by user on 1/5/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {


    ImageView icon;
    LinearLayout new_account;
    EditText et_password,et_user_name;
    Button b_submit,b_facebook_login;
    ProgressBar progressBar;

    ///sub url
    String sub_url="sign-in";


    OkHttpClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize the Okhttp
        client=new OkHttpClient();




        setContentView(R.layout.login);

        if (!Utils.isOnline(Login.this))
        {
            Utils.ShowAlert(Login.this,"No Internet");
        }


        //hide action bar
        getSupportActionBar().hide();

        //set all id//
        set_id();

        ///load large icon with glide
        Glide.with(this)
                .load(R.drawable.icon_content)
                .into(icon);

    }

    void set_id()
    {


        new_account=(LinearLayout)findViewById(R.id.new_accunt_test);
        new_account.setOnClickListener(this);

        et_user_name=(EditText)findViewById(R.id.login_email);
        et_password=(EditText)findViewById(R.id.login_password);

        b_submit=(Button)findViewById(R.id.login_submit);
        b_submit.setOnClickListener(this);

        b_facebook_login=(Button)findViewById(R.id.fb_button);
        b_facebook_login.setOnClickListener(this);

        progressBar=(ProgressBar)findViewById(R.id.progressbar);


        icon=(ImageView)findViewById(R.id.icon_view) ;
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        switch (id)
        {
            case R.id.fb_button:



                ///call facebook api for login
                break;
            case R.id.login_submit:

                //get the user name from Edit text
                String user_name=et_user_name.getText().toString();
                //get the password from Edit Text
                String password=et_password.getText().toString();

                ///check the user_name and password is empty
                if(user_name.trim().equals("")||password.trim().equals(""))
                {
                  //  Toast.makeText(Login.this,"Fill the both field",Toast.LENGTH_SHORT).show();
                    Utils.ShowAlert(Login.this,"Fill the both field");
                }
                //excute  the network operation
                //
                else
                {

                    new LoginRequest().execute(user_name, password);
                }
                ///connect the server for chffecking username and password
                //call a asytask for network operation



               // Toast.makeText(Login.this,"Cool",Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(Login.this,HomeScreen.class));

                break;
            case R.id.new_accunt_test:
                //open a link in a brawer

                String url ="http://www.biyeta.com/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));
                break;

        }



    }




    //
    private class LoginRequest extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String password=params[1];
            Log.e("back",id+"---"+password);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("user_login[email]", id)///sent the team passcode
                    .add("user_login[password]", password)
                    .build();


            Request request = new Request.Builder()
                    .url(Constant.BASE_URL+sub_url)
                    .post(requestBody)
                    .build();

          /*  Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Accept", "application/vnd.tenfour-v1, application/json")

                    .post(requestBody)
                    .build();*/
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e("back",responseString);
                response.body().close();

                return responseString;
                // do whatever you need to do with responseString
            }
            catch (Exception e) {
                e.printStackTrace();
            }


            return "false";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //convert string to json object
                JSONObject jsonObject=new JSONObject(s);


                String token=jsonObject.get("auth_token").toString();
                SharePref sharePref=new SharePref(Login.this);
                sharePref.set_data("token",token);
                //insert the token in Sharepreference


                startActivity(new Intent(Login.this,HomeScreen.class));
                finish();


            } catch (JSONException e) {
                Log.e("error","JSON error");
                e.printStackTrace();
                Utils.ShowAlert(Login.this,"Wrong Input");
                b_submit.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            b_submit.setVisibility(View.GONE);

        }
    }





}

