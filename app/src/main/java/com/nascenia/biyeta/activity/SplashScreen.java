package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

//import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.appdata.FAQData;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.service.OnClearFromRecentService;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

//import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    private Tracker mTracker;
    private AnalyticsApplication application;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();
       // startService(new Intent(getBaseContext(), OnClearFromRecentService.class));

        //firebase token sender code

        Utils.calculateHashKey(this,"com.nascenia.biyeta");
        /*Fabric.with(this, new Crashlytics());

        throw new NullPointerException();*/

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

                Log.e("Token : ", sharePref.get_data("token"));

                /// login  && mobile  verification unsuccessful
                ///or the first time appp open
                if (sharePref.get_data("token").equals("key")||
                        sharePref.get_data("token").equals(null) ||
                        (sharePref.get_data("mobile_verified").equals("false")||
                                sharePref.get_data("mobile_verified").equals("key"))) {

                   //


                    ///permission comment kora ache upore

                    Intent loginIntent = new Intent(SplashScreen.this, Login.class);
                    startActivity(loginIntent);


                    //Intent mobileVerificationIntent = new Intent(SplashScreen.this, RegistrationFirstActivity.class);
                    //startActivity(mobileVerificationIntent);
                    ///Kill the current activity
                    finish();
                }

                /// login  successfull and  mobile  verification unsuccessful
                else if ( !sharePref.get_data("token").equals("key") &&
                        sharePref.get_data("mobile_verified").equals("false") ){
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

        //FCM
/*
        try{
            //FirebaseInstanceId.getInstance().getToken();
        }
        catch (Exception e){

            Log.i("Error",e.getMessage().toString());
            e.printStackTrace();
        }
        //System.out.println("MainActivity.onCreate: " + token);
*/

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
        mTracker.setScreenName("Splash Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
