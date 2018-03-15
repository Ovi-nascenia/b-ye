package com.nascenia.biyeta.NetWorkOperation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.FavoriteActivity;
import com.nascenia.biyeta.activity.NewUserProfileActivity;
import com.nascenia.biyeta.activity.WebViewPayment;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.Match;
import com.nascenia.biyeta.model.newuserprofile.Image;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by god father on 3/27/2017.
 */

public class NetWorkOperation {


    public static OkHttpClient client = new OkHttpClient();
    static Context context;
    private static ProgressDialog progressDialog;



    //this method is used for sending communication request to the server
    public static void createCommunicationReqeust(Context context,
                                                  String url,
                                                  String userId,
                                                  Button finalResultButton,
                                                  String msg, AnalyticsApplication application, Tracker mTracker) {
        NetWorkOperation.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(true);

        new SendConnectionRequest(finalResultButton, application, mTracker).execute(url, userId, msg);


    }

    //this method is used for sending biodata request to the server
    public static void createProfileReqeust(Context context, String url, Button finalResultButton, String msg,
                                            AnalyticsApplication application, Tracker mTracker) {

        NetWorkOperation.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(true);

        new CreateProfileRequestTask(finalResultButton, application, mTracker).execute(url, msg);
    }

    // this mehtod is used only for sending favorite,unvfavorite and smile response to the server
    public static void sendFavoriteUnFavoriteandSmileRequest(Context context,
                                                             String url,
                                                             String profileId,
                                                             String header,
                                                             String hederParam,
                                                             UserProfile userProfile,
                                                             ImageView favoriteImageView,
                                                             LinearLayout layoutSendSmiley,
                                                             ImageView emoIconImageView,
                                                             TextView sendEmoIconTextTag,
                                                             int caseIdentifierTagValue,
                                                             AnalyticsApplication application,
                                                             Tracker mTracker) {
        NetWorkOperation.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(true);


        new PostTask(userProfile,
                favoriteImageView,
                layoutSendSmiley,
                emoIconImageView,
                sendEmoIconTextTag,
                caseIdentifierTagValue,
                application,
                mTracker).execute(url,
                profileId,
                header,
                hederParam
        );


    }

    static class SendConnectionRequest extends AsyncTask<String, String, String> {


        private Button finalResultButton;
        private Tracker mTracker;
        private AnalyticsApplication application;
        // private String msg;

        public SendConnectionRequest(Button finalResultButton, AnalyticsApplication application, Tracker mTracker) {
            this.finalResultButton = finalResultButton;
            this.application = application;
            this.mTracker = mTracker;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            //   msg = strings[2];
            Integer id = Integer.parseInt(strings[1]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", id + "")
                    .add("new_pricing_plan", true + "")
                    .build();


            Response response;
            SharePref sharePref = new SharePref(NetWorkOperation.context);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
//                application.trackEception(e, "SendConnectionRequest/doInBackground", "NetworkOperation", e.getMessage().toString(), mTracker);
                return null;

            }


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) Utils.ShowAlert(context, "ইন্টারনেট সংযোগ নেই");
            else {
//                Log.i("response", s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("message")) {
                        Match.pause_is_called = 2;
                        Toast.makeText(context, jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"), Toast.LENGTH_LONG).show();
                        NewUserProfileActivity.message = jsonObject.getJSONArray("message").getJSONObject(0).getString("detail");
                        this.finalResultButton.setText(jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"));
                        this.finalResultButton.setEnabled(false);
                    } else if (jsonObject.has("error")) {
                        JSONObject errorObj = jsonObject.getJSONArray("error").getJSONObject(0);
                        if (errorObj.has("show_pricing_plan")) {
                            if (errorObj.getBoolean("show_pricing_plan")) {
//                                Toast.makeText(context, jsonObject.getJSONArray(
//                                        "error").getJSONObject(0).getString("detail"),
//                                        Toast.LENGTH_LONG).show();

                                Utils.showPricingPlanDialog(context, jsonObject.getJSONArray("error").getJSONObject(0).getString("detail"));
//                                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//                                alertBuilder.setCancelable(true);
////                                alertBuilder.setTitle(R.string.account_recharge_title);
//                                alertBuilder.setMessage(jsonObject.getJSONArray("error").getJSONObject(0).getString("detail"));
//                                alertBuilder.setPositiveButton(R.string.see_details,
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                Intent myIntent = new Intent(context,
//                                                        WebViewPayment.class);
//                                                if (context instanceof NewUserProfileActivity) {
//                                                    ((NewUserProfileActivity) context).startActivityForResult(
//                                                            myIntent, Utils.UPGRADE_REQUEST_CODE);
//                                                }else if (context instanceof FavoriteActivity) {
//                                                    ((FavoriteActivity) context).startActivityForResult(
//                                                            myIntent, Utils.UPGRADE_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                alertBuilder.setNegativeButton(R.string.not_now,
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                final AlertDialog alert = alertBuilder.create();
//                                alert.setOnShowListener( new DialogInterface.OnShowListener() {
//                                    @Override
//                                    public void onShow(DialogInterface arg0) {
//                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                                    }
//                                });
//                                alert.show();
                            }
                        }else {
                            Toast.makeText(context, jsonObject.getJSONArray("error").getJSONObject(
                                    0).getString("detail"), Toast.LENGTH_LONG).show();
                            this.finalResultButton.setText(jsonObject.getJSONArray(
                                    "message").getJSONObject(0).getString("detail"));
                            this.finalResultButton.setEnabled(false);
                        }
                    }else {
                        this.finalResultButton.setEnabled(false);
                        this.finalResultButton.setText("Error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    application.trackEception(e, "SendConnectionRequest/onPostExecute", "NetworkOperation", e.getMessage().toString(), mTracker);
                }


            }

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }


    }

    static class CreateProfileRequestTask extends AsyncTask<String, String, String> {


        private Button finalResultButton;
        private String msg;
        private Tracker mTracker;
        private AnalyticsApplication application;

        public CreateProfileRequestTask(Button finalResultButton, AnalyticsApplication application, Tracker mTracker) {

            this.finalResultButton = finalResultButton;
            this.application = application;
            this.mTracker = mTracker;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();

        }


        @Override
        protected String doInBackground(String... strings) {
//            Log.i("profileresponse", strings[0]);
            msg = strings[1];

            Response response;
            SharePref sharePref = new SharePref(NetWorkOperation.context);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .addHeader("new-pricing-plan", true + "")
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
//                application.trackEception(e, "CreateProfileRequestTask/doInBackground", "NetworkOperation", e.getMessage().toString(), mTracker);
                return null;

            }


        }


        @Override
        protected void onPostExecute(String s) {
            if (s == null)
                Utils.ShowAlert(context, context.getResources().getString(R.string.no_internet_connection));
            else {
//                Log.i("response", s);

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.has("message")) {
                        Toast.makeText(context, jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"), Toast.LENGTH_LONG).show();
                        this.finalResultButton.setText(jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"));
                        this.finalResultButton.setEnabled(false);
                    }else if (jsonObject.has("error")) {
                        JSONObject errorObj = jsonObject.getJSONArray("error").getJSONObject(0);
                        if(errorObj.has("show_pricing_plan")){
                            if(errorObj.getBoolean("show_pricing_plan")){
//                                Toast.makeText(context, jsonObject.getJSONArray("error").getJSONObject(0).getString("detail"), Toast.LENGTH_LONG).show();
//                                this.finalResultButton.setText(jsonObject.getJSONArray(
//                                        "message").getJSONObject(0).getString("detail"));
//                                this.finalResultButton.setEnabled(false);


                                Utils.showPricingPlanDialog(context, jsonObject.getJSONArray("error").getJSONObject(0).getString("detail"));
//                                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//                                alertBuilder.setCancelable(true);
////                                alertBuilder.setTitle(R.string.account_recharge_title);
//                                alertBuilder.setMessage(jsonObject.getJSONArray("error").getJSONObject(0).getString("detail"));
//                                alertBuilder.setPositiveButton(R.string.see_details,
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                Intent myIntent = new Intent(context,
//                                                        WebViewPayment.class);
//                                                if (context instanceof NewUserProfileActivity) {
//                                                    ((NewUserProfileActivity) context).startActivityForResult(
//                                                            myIntent, Utils.UPGRADE_REQUEST_CODE);
//                                                }else if (context instanceof FavoriteActivity) {
//                                                    ((FavoriteActivity) context).startActivityForResult(
//                                                            myIntent, Utils.UPGRADE_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                alertBuilder.setNegativeButton(R.string.not_now,
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                final AlertDialog alert = alertBuilder.create();
//                                alert.setOnShowListener( new DialogInterface.OnShowListener() {
//                                    @Override
//                                    public void onShow(DialogInterface arg0) {
//                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
////                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(context.getResources().getColor(R.color.title_gray));
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
////                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//                                    }
//                                });
//
//                                alert.show();
                            }
                        }else {
                            Toast.makeText(context, jsonObject.getJSONArray("error").getJSONObject(
                                    0).getString("detail"), Toast.LENGTH_LONG).show();
                            this.finalResultButton.setText(jsonObject.getJSONArray(
                                    "message").getJSONObject(0).getString("detail"));
                            this.finalResultButton.setEnabled(false);
                        }
                    } else {
                        this.finalResultButton.setEnabled(false);
                        this.finalResultButton.setText("Error");
                    }
                } catch (JSONException e) {
//                    application.trackEception(e, "CreateProfileRequestTask/onPostExecute", "NetworkOperation", e.getMessage().toString(), mTracker);
                    e.printStackTrace();
                }


            }

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    }

//    public static void showPricingPlanDialog(final Context context, String message){
//        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//        alertBuilder.setCancelable(true);
////                                alertBuilder.setTitle(R.string.account_recharge_title);
//        alertBuilder.setMessage(message);
//        alertBuilder.setPositiveButton(R.string.see_details,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent myIntent = new Intent(context,
//                                WebViewPayment.class);
//                        if (context instanceof NewUserProfileActivity) {
//                            ((NewUserProfileActivity) context).startActivityForResult(
//                                    myIntent, Utils.UPGRADE_REQUEST_CODE);
//                        }else if (context instanceof FavoriteActivity) {
//                            ((FavoriteActivity) context).startActivityForResult(
//                                    myIntent, Utils.UPGRADE_REQUEST_CODE);
//                        }
//                    }
//                });
//        alertBuilder.setNegativeButton(R.string.not_now,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        final AlertDialog alert = alertBuilder.create();
//        alert.setOnShowListener( new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
////                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(context.getResources().getColor(R.color.title_gray));
//                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
////                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//            }
//        });
//
//        alert.show();
//    }

    public static class loadAccountBalance extends AsyncTask<String, String, String> {

        Context context;
        public loadAccountBalance(Context context, AnalyticsApplication application,
                Tracker tracker) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(this.context);
            progressDialog.setMessage(this.context.getResources().getString(R.string.progress_dialog_message));
            progressDialog.setCancelable(true);
        }

        @Override
        protected void onPostExecute(String s) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try{
                super.onPostExecute(s);
                Log.e("testtt", s);
            }catch(Exception e)
            {
                Utils.ShowAlert(context, context.getResources().getString(R.string.no_internet_connection));
            }

            if (s == null) {
                Utils.ShowAlert(context, context.getResources().getString(R.string.no_internet_connection));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("text")) {
                        showBalanceMessage(jsonObject.getString("text"),context);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(context);
            String token = sharePref.get_data("token");
            Request request = null;
            request = new Request.Builder()
                    .url(Utils.Base_URL+"/api/v1/payments/balance")
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoadAccoutBalance/doInBackground", "PaymentActivity", e.getMessage().toString(), mTracker);
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoadAccoutBalance/doInBackground", "PaymentActivity", e.getMessage().toString(), mTracker);
            }
            return null;
        }
    }

    private static void showBalanceMessage(String text, Context context) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(context.getResources().getString(R.string.success_title));
        alertBuilder.setMessage(text);
        alertBuilder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private static class PostTask extends AsyncTask<String, String, String> {

        UserProfile userProfile;
        ImageView favoriteImageView;
        LinearLayout layoutSendSmiley;
        ImageView emoIconImageView;
        TextView sendEmoIconTextTag;
        int caseIdentifierTagValue;
        JSONObject jsonObject;

        private Tracker mTracker;
        private AnalyticsApplication application;

        public PostTask(UserProfile userProfile,
                        ImageView favoriteImageView,
                        LinearLayout layoutSendSmiley,
                        ImageView emoIconImageView,
                        TextView sendEmoIconTextTag,
                        int caseIdentifierTagValue,
                        AnalyticsApplication application,
                        Tracker mTracker) {

            this.userProfile = userProfile;
            this.favoriteImageView = favoriteImageView;
            this.layoutSendSmiley = layoutSendSmiley;
            this.emoIconImageView = emoIconImageView;
            this.sendEmoIconTextTag = sendEmoIconTextTag;
            this.caseIdentifierTagValue = caseIdentifierTagValue;
            this.application = application;
            this.mTracker = mTracker;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Response response;

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", params[1])
                    .build();

            Request request = new Request.Builder()
                    .url(params[0])
                    .addHeader(params[2], params[3])
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e) {
//                application.trackEception(e, "PostTask/doInBackground", "NetworkOperation", e.getMessage().toString(), mTracker);

                return null;

            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }


            try {

                if (s == null) {
                    Utils.ShowAlert(context, context.getResources().getString(R.string.no_internet_connection));
                } else {

                    jsonObject = new JSONObject(s);

                    if (this.caseIdentifierTagValue == Utils.SMILEY_BUTTON_PRESS_TAG) {
                        Toast.makeText(context, context.getResources().getString(R.string.send_smiley_message), Toast.LENGTH_LONG).show();
                        this.layoutSendSmiley.setEnabled(false);
                        this.emoIconImageView.setImageResource(R.drawable.red_smile);
                        this.sendEmoIconTextTag.setText(context.getResources().getString(R.string.after_send_smile_text));

                    } else if (this.caseIdentifierTagValue == Utils.FAVORITE_BUTTON_PRESS_TAG) {
                        Toast.makeText(context, context.getResources().getString(R.string.favorite_message), Toast.LENGTH_LONG).show();
                        this.userProfile.getProfile().setIsFavorite(true);
                        this.favoriteImageView.setImageResource(R.drawable.red_favorite);

                    } else if (this.caseIdentifierTagValue == Utils.UNFAVORITE_BUTTON_PRESS_TAG) {
                        Toast.makeText(context, context.getResources().getString(R.string.unfavorite_message), Toast.LENGTH_LONG).show();
                        this.userProfile.getProfile().setIsFavorite(false);
                        this.favoriteImageView.setImageResource(R.drawable.grey_fav);

                    } else{
                        //                        Log.i("test", "no case match");
                    }

                }


            } catch (Exception e) {

//                application.trackEception(e, "PostTask/onPostExecute", "NetworkOperation", e.getMessage().toString(), mTracker);
//                Log.i("test", e.getMessage().toString());

            }

            jsonObject = null;


        }
    }
}
