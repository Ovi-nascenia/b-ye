package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;

public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_thanks);

        TextView tvThanks = findViewById(R.id.tv_thanks);
        SharePref sharePref = new SharePref(ThanksActivity.this);
        if(sharePref.get_data("gender").equalsIgnoreCase("male"))
            tvThanks.setText(getString(R.string.thanks_msg_male));
        else
            tvThanks.setText(getString(R.string.thanks_msg_female));

        final String constants = getIntent().getStringExtra("constants");
        Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + constants);
        Button btnOk = findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent;
                signupIntent = new Intent(ThanksActivity.this,
                        RegistrationChoiceSelectionFirstPage.class);
                signupIntent.putExtra("constants", constants);
                signupIntent.putExtra("isSignUp", true);
                startActivity(signupIntent);
                finish();
            }
        });

    }
}
