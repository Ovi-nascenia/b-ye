package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.nascenia.biyeta.appdata.FAQData;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SplashScreen extends AppCompatActivity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageview);
        Glide.with(this)
                .load(R.drawable.splash_screen)
                .into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity
                //
                SharePref sharePref = new SharePref(SplashScreen.this);

                /// login  && mobile  verification unsuccessful
                ///or the first time appp open
                if (sharePref.get_data("token").equals("key") ||(sharePref.get_data("mobile_verified").equals("false")||sharePref.get_data("mobile_verified").equals("key"))) {

                   //
                    Intent loginIntent = new Intent(SplashScreen.this, Login.class);
                    startActivity(loginIntent);
                    ///Kill the current activity
                    finish();
                }

                /// login  successfull and  mobile  verification unsuccessful
                else if ( !sharePref.get_data("token").equals("key") && sharePref.get_data("mobile_verified").equals("false") ){
                    Intent mobileVerificationIntent = new Intent(SplashScreen.this, MobileVerification.class);
                    startActivity(mobileVerificationIntent);
                    finish();
                }

                //login and mobile verification is true/
                else {
                    Intent homeIntent = new Intent(SplashScreen.this, HomeScreen.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, Constant.SPLASH_TIMEOUT);
    }
}
