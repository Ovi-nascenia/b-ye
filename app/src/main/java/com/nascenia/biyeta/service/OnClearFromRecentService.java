package com.nascenia.biyeta.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nascenia.biyeta.activity.SplashScreen;
import com.nascenia.biyeta.appdata.SharePref;

/**
 * Created by saiful on 12/13/17.
 */

public class OnClearFromRecentService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        SharePref sharePref = new SharePref(this);
        sharePref.set_data("token", "key");

    }
}
