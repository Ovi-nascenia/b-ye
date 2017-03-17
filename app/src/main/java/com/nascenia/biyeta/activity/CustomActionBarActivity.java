package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nascenia.biyeta.R;

/**
 * Created by god father on 3/17/2017.
 */

public abstract class CustomActionBarActivity extends AppCompatActivity {

    void setUpToolBar(String title, final Context context)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        View v =getSupportActionBar().getCustomView();
        ImageView button =(ImageView)v.findViewById(R.id.back);
        TextView textView=(TextView) v.findViewById(R.id.title_custom_toolbar);
        textView.setText(title);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                activity.finish();
            }
        });
    }

}
