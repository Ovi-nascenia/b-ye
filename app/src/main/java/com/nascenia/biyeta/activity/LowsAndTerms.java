package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nascenia.biyeta.R;

/**
 * Created by god father on 3/17/2017.
 */

public class LowsAndTerms extends CustomActionBarActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        setUpToolBar("hello",this);

    }


    @Override
    void setUpToolBar(String title,Context context) {
        super.setUpToolBar(title, context);
    }
}
