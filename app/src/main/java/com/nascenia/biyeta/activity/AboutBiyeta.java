package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.PricingAdapter;

/**
 * Created by god father on 3/27/2017.
 */

public class AboutBiyeta extends CustomActionBarActivity {
    private Tracker mTracker;
    private AnalyticsApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_biyeta);
//        setContentView(R.layout.pricing_layout);
        setUpToolBar(getString(R.string.about_title),this);
//        GridView gridView = (GridView)findViewById(R.id.gv_pricing_plan);
//        gridView.setAdapter(new PricingAdapter(this));

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Google Analytics*/
        mTracker.setScreenName(getString(R.string.about_title));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }
}
