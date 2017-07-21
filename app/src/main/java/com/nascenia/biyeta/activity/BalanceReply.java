package com.nascenia.biyeta.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;

/**
 * Created by god father on 3/22/2017.
 */

public class BalanceReply extends CustomActionBarActivity {

    private Tracker mTracker;
    private AnalyticsApplication application;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_reply);
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
        mTracker.setScreenName(getString(R.string.balance_reply_google_analytics));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
