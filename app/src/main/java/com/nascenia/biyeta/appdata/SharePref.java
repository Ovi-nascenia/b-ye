package com.nascenia.biyeta.appdata;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by god father on 1/24/2017.
 */

public class SharePref extends Application {


    public static final String USER_PREFERENCE="user_preference";
    SharedPreferences sharedPreferences = null;

    public SharePref(Context context)
    {
        if(context!=null)
            sharedPreferences  = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);

    }


   public void set_data(String key,String value)
    {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

   public String get_data(String key)
    {
            return sharedPreferences.getString(key,"key");

    }
}
