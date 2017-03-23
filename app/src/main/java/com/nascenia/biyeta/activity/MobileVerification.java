package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;

import com.nascenia.biyeta.R;


/**
 * Created by god father on 3/23/2017.
 */

public class MobileVerification extends CustomActionBarActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_verification);
    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }
}
