package biyeta.nas.biyeta;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import biyeta.nas.biyeta.AppData.SharePref;
import biyeta.nas.biyeta.Constant.Constant;


public class SplashScreen extends AppCompatActivity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.imageview);
        Glide.with(this)
                .load(R.drawable.splash_screen)
                .into(imageView);
        new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity

             SharePref sharePref=new SharePref(SplashScreen.this);
            if (sharePref.get_data("token").equals("key")) {
                Intent i = new Intent(SplashScreen.this, LoginTest.class);
                startActivity(i);
                ///Kill the current activity
                finish();
            }
            else
            {
                Intent i = new Intent(SplashScreen.this, HomeScreen.class);
                startActivity(i);
                ///Kill the current activity
                finish();
            }
            }
         }, Constant.SPLASH_TIMEOUT);
    }
}
