package com.nascenia.biyeta.IntigrationGoogleAnalytics;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.R;

/**
 * Created by nascenia on 5/23/17.
 */

public class AnalyticsApplication extends Application {
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */

    public static Context getContext()
    {
        return mContext;
    }

    synchronized public Tracker getDefaultTracker() {
        sAnalytics = GoogleAnalytics.getInstance(this);
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
//            sTracker = sAnalytics.newTracker(R.xml.app_tracker);
//            sTracker.enableExceptionReporting(true);
//            sTracker.enableAutoActivityTracking(true);
        }

        return sTracker;
    }

    public void setEvent(String category, String action, String label,Tracker mTracker){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());

    }

    public void trackEception(Exception e, String getExceptionMethod, String getExceptionLocation, String exceptionMessage, Tracker mTracker){
        if (e != null){
            mTracker = getDefaultTracker();
            mTracker.send(new HitBuilders.ExceptionBuilder().setDescription(
                    getExceptionMethod + " : " + getExceptionLocation +" : "+ exceptionMessage)
                    .setFatal(false)
                    .build()
            );
        }
    }


}
