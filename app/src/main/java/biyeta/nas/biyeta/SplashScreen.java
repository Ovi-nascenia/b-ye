package biyeta.nas.biyeta;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.imageview);
        //

        Glide.with(this)
                .load(R.drawable.splash_screen)
                .into(imageView);
        new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, LoginTest.class);
                startActivity(i);
                ///Kill the current activity
                finish();
            }
         }, SPLASH_TIME_OUT);
    }
}
