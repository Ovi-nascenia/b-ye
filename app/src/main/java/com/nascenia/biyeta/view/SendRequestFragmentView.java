package com.nascenia.biyeta.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.NewUserProfileActivity;
import com.nascenia.biyeta.activity.SendRequestActivity;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/**
 * Created by saiful on 3/13/17.
 */

public class SendRequestFragmentView {


    public static String responseValue = "";

    private static MyCallback<Boolean> mCallback;

    public static String fetchUserProfileDetailsResponse(final String url, final Context context) {


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = new ResourceProvider(context).fetchGetResponse(url);
                    ResponseBody responseBody = response.body();
                    responseValue = responseBody.string();
                    Log.i("bio", "onmethod" + responseValue + " ");
                    responseBody.close();
                    //  final UserProfile userProfile = new Gson().fromJson(responseValue, UserProfile.class);

                    if (responseValue != null) {
                        mCallback.onComplete(true); // will call onComplete() on MyActivity once the job is done
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
*/


        return responseValue;

    }


    public static void setUserDetailsInfo(UserProfile userProfile, TextView view) {

        if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
            view.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
        }

    }


    public static void setUserImage(UserProfile userProfile, View view) {

    }


    public static void setDataonGeneralInfoRecylerView(UserProfile userProfile, View view) {

    }

    public static void setDataonMatchUserChoiceRecylerView(UserProfile userProfile, View view) {

    }


}
