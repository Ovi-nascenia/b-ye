package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.nascenia.biyeta.R;

/**
 * Created by god father on 3/21/2017.
 */

public class RequestSentFromMe extends CustomActionBarActivity {


    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolBar(getString(R.string.sent_request),this);
        setContentView(R.layout.request_sent_from_me);
        setUpId();


    }

    void setUpId()
    {
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }
}
