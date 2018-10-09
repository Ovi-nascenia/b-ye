package com.nascenia.biyeta.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;

import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.constant.Constant;
import com.nascenia.biyeta.fragment.Favourite;
import com.nascenia.biyeta.fragment.Inbox;
import com.nascenia.biyeta.fragment.Match;
import com.nascenia.biyeta.fragment.Search;
import com.nascenia.biyeta.model.loginInfromation.LoginInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    OkHttpClient client;
    private Tracker mTracker;
    private AnalyticsApplication application;

    private static final int REQUEST_PHONE_CALL = 100;
    static Context context;
    DrawerLayout drawerLayout;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    private View actionBarView;
    private String responseValue = null;
    private SharePref sharePref;
    String SUB_URL = "sign-out";

    private ImageView searchImageView, matchImageView, fevImageView, inboxImageView,
            profileImageView, menuProfileImgView;
    NavigationView navigationView;
    private boolean isSignUp = false, need_upgrade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        context = this;
        client = new OkHttpClient();

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());


        sharePref = new SharePref(HomeScreen.this);
//        String constants = getIntent().getStringExtra("constants");

        initIdAndActionBar();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentParentViewGroup, new Search())
                .commit();
        searchImageView.setColorFilter(Color.WHITE);
        matchImageView.setColorFilter(Color.GRAY);
        inboxImageView.setColorFilter(Color.GRAY);
        fevImageView.setColorFilter(Color.GRAY);
        profileImageView.setColorFilter(Color.GRAY);

        if (ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeScreen.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        }

        isSignUp = getIntent().getBooleanExtra("isSignUp", false);

//        UserProfile userProfile = UserProfile.

        //FCM
        //String token= FirebaseInstanceId.getInstance().getToken();
        //System.out.println("MainActivity.onCreate: " + token);

    }

    void initIdAndActionBar() {
        actionBarView = getLayoutInflater().inflate(R.layout.activity_main_actionbar_item, null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(actionBarView);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        searchImageView = (ImageView) actionBarView.findViewById(R.id.search);
        matchImageView = (ImageView) actionBarView.findViewById(R.id.match);
        fevImageView = (ImageView) actionBarView.findViewById(R.id.favorite);
        inboxImageView = (ImageView) actionBarView.findViewById(R.id.inbox);
        profileImageView = (ImageView) actionBarView.findViewById(R.id.profile);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        searchImageView.setOnClickListener(this);
        matchImageView.setOnClickListener(this);
        fevImageView.setOnClickListener(this);
        inboxImageView.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        Log.e("name", sharePref.get_data("real_name"));

        View header = navigationView.getHeaderView(0);
        TextView display_name = (TextView) header.findViewById(R.id.displayname);
        display_name.setText(sharePref.get_data("real_name"));
        menuProfileImgView = (ImageView) header.findViewById(R.id.img_profile);

        //set profile image on drawer
        if (sharePref.get_data("profile_picture").equals("key") &&
                (sharePref.get_data("gender").equalsIgnoreCase(Utils.MALE_GENDER))
                ) {
            menuProfileImgView.setImageResource(R.drawable.profile_icon_male);

        } else if (sharePref.get_data("profile_picture").equals("key") &&
                (sharePref.get_data("gender").equalsIgnoreCase(Utils.FEMALE_GENDER))
                ) {
            menuProfileImgView.setImageResource(R.drawable.profile_icon_female);
        } else if (!sharePref.get_data("profile_picture").equals("key")) {
            Picasso.with(this)
                    .load(Utils.Base_URL + sharePref.get_data("profile_picture"))
                    .into(menuProfileImgView, new Callback() {
                        @Override
                        public void onSuccess() {
//                  menuProfileImgView.post(new Runnable() {
//                  @Override
//                  public void run() {
//                      Utils.scaleImage(HomeScreen.this, 1.2f, menuProfileImgView);
//                  }
//                  });
                        }

                        @Override
                        public void onError() {
                        }
                    });


        } else {
//            Log.i("image", "no image found");
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                // update highlighted item in the navigation menu
                //    menuItem.setChecked(true);
                //set false other item

                //

                if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.closeDrawer(Gravity.RIGHT);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                    int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_profile:

                                startActivity(new Intent(HomeScreen.this, OwnUserProfileActivity.class).
                                        putExtra("id", sharePref.get_data("user_id")).
                                        putExtra("user_name", sharePref.get_data("real_name"))
                                        .putExtra("PROFILE_EDIT_OPTION", true)
                                );


                                break;


                            case R.id.nav_balance:

                                     if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
                                         drawerLayout.closeDrawer(Gravity.RIGHT);
                                     if(need_upgrade) {
                                         callWebView();
                                     }else {
                                         startActivity(new Intent(HomeScreen.this,
                                                 PaymentActivity.class));
                                     }



                                break;

                            case R.id.nav_about_us:

                                startActivity(new Intent(HomeScreen.this, AboutBiyeta.class));
                                break;
                            case R.id.nav_connection:
                                startActivity(new Intent(HomeScreen.this, ContactUs.class));


                                break;

                            case R.id.nav_policy:
                                startActivity(new Intent(HomeScreen.this, PrivacyPolicy.class));
                                break;


                            case R.id.nav_faq:
                                startActivity(new Intent(HomeScreen.this, FAQActivity.class));
                                break;
                            case R.id.nav_termsofuse:

                                startActivity(new Intent(HomeScreen.this, TermOfUse.class));

                                break;
                            case R.id.nav_logout:
                                //SharePref sharePref = new SharePref(HomeScreen.this);

                                try {
                                    LoginManager.getInstance().logOut();
                                }catch (Exception ex)
                                {
                                    ex.printStackTrace();
        //                            application.trackEception(ex, "setNavigationItemSelectedListener/logOut", "HomeScreen", ex.getMessage().toString(), mTracker);
                                }


                                if (!Utils.isOnline(HomeScreen.this)) {
                                    Utils.ShowAlert(HomeScreen.this, getString(R.string.no_internet_connection));
                                } else
                                {
                                    new HomeScreen.LogoutRequest().execute();
                                }





                                break;

                            default:
                                break;
                        }


                    }
                },200);



                return true;
            }


        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
            ActivityCompat.finishAffinity(HomeScreen.this);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "OnResume");

        /*Google Analytics*/
        mTracker.setScreenName(getString(R.string.title_activity_home_screen));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onClick(View view) {

        searchImageView.setColorFilter(Color.GRAY);
        matchImageView.setColorFilter(Color.GRAY);
        inboxImageView.setColorFilter(Color.GRAY);
        fevImageView.setColorFilter(Color.GRAY);
        profileImageView.setColorFilter(Color.GRAY);
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);


        int id = view.getId();
        switch (id) {
            case R.id.search:
                searchImageView.setColorFilter(Color.WHITE);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Search())
                        .commit();
                break;
            case R.id.match:
                matchImageView.setColorFilter(Color.WHITE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Match())
                        .commit();
                break;
            case R.id.favorite:

                fevImageView.setColorFilter(Color.WHITE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Favourite())
                        .commit();
                break;
            case R.id.inbox:
                fevImageView.setColorFilter(Color.WHITE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Inbox())
                        .commit();
//
                inboxImageView.setColorFilter(Color.WHITE);
//
//                Dialog dialog = new Dialog(HomeScreen.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(true);
//                dialog.setContentView(R.layout.inbox);
//                dialog.findViewById(R.id.tv_sent_request).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new LoadReqeustSenderIdsTask().execute();
//                    }
//                });
//
//
//                dialog.findViewById(R.id.tv_sent_request_from_me).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        startActivity(new Intent(HomeScreen.this, RequestSentFromMe.class));
//                    }
//                });
//
//                dialog.findViewById(R.id.tv_inbox).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        startActivity(new Intent(HomeScreen.this, InboxListView.class));
//                    }
//                });
//                DisplayMetrics displaymetrics = new DisplayMetrics();
//                this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                int width = (int) ((int) displaymetrics.widthPixels * 0.8);
//               // int height = (int) ((int) displaymetrics.heightPixels * 0.4);
//                dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//                dialog.show();
                break;
            case R.id.profile:
                profileImageView.setColorFilter(Color.WHITE);
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    if (!sharePref.get_data("profile_picture").equals("key")) {
                        Picasso.with(this)
                                .load(Utils.Base_URL + sharePref.get_data("profile_picture"))
                                .into(menuProfileImgView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });
                    }
                    try {
                        new LoadAccoutBalance().execute("");
                    }catch (Exception ex){

                    }
                }
                break;


        }

    }

    private class LogoutRequest extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String token = sharePref.get_data("token");
            String imei = null;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeScreen.this,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
                } else {
                    imei = Utils.deviceIMEI(context);
                }
            }
            else{
                imei = Utils.deviceIMEI(context);
            }




            RequestBody requestBody = new FormEncodingBuilder()
                    .add("user_login[imei]",imei)
                    .build();

            //   //imei of device


            Request request = new Request.Builder()
                    .url(Constant.BASE_URL + SUB_URL)
                    .delete(requestBody)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e("Logout", responseString);
                response.body().close();

                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sharePref.set_data("token", "key");
            Intent intent = new Intent(getApplicationContext(), Login.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }

        @Override
        protected void onPreExecute() {
        }
    }

    public class LoadAccoutBalance extends AsyncTask<String, String, String> {
        ProgressDialog progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(HomeScreen.this);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();

        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(String s) {
            if (progressBar.isShowing()) {

                progressBar.dismiss();
            }

            try{
                super.onPostExecute(s);
                Log.e("testtt", s);
            }catch(Exception e)
            {
                Utils.ShowAlert(HomeScreen.this, getString(R.string.no_internet_connection));
                return;
            }

            if (s == null) {
                Utils.ShowAlert(HomeScreen.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.has("upgrade_user")) {
                        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_balance);
//                        menuItem.getActionView().setBackgroundColor(R.color.colorPrimary);
                        System.out.println("here");

                        if(jsonObject.getBoolean("upgrade_user")){
                            menuItem.setTitle(R.string.balance_recharge_menu);
                            need_upgrade = true;
                        }else{
                            menuItem.setTitle(R.string.payment_plan);
                            need_upgrade = false;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(HomeScreen.this);
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

    private void callWebView() {
        Intent myIntent = new Intent(HomeScreen.this,
                WebViewPayment.class);
        startActivityForResult(myIntent, Utils.UPGRADE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == Utils.UPGRADE_REQUEST_CODE) {
                new NetWorkOperation.loadAccountBalance(HomeScreen.this, application, mTracker).execute();
            }
        }
    }

}