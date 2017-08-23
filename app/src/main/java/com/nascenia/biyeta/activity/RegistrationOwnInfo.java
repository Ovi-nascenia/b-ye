package com.nascenia.biyeta.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.nascenia.biyeta.R;

public class RegistrationOwnInfo extends AppCompatActivity {
    LinearLayout castReligion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_own_info);
        castReligion = (LinearLayout) findViewById(R.id.castReligion);
    }
}
