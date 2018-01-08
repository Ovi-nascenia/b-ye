package com.nascenia.biyeta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nascenia.biyeta.R;

public class FacebookRegistrationDataInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_registration_data_input);
        String email = getIntent().getStringExtra("email");
    }
}
