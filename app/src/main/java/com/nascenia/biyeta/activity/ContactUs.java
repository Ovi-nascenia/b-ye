package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nascenia.biyeta.R;

/**
 * Created by god father on 3/22/2017.
 */

public class ContactUs extends CustomActionBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_page);
        setUpToolBar(getString(R.string.contact_title),this);
    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }
}
