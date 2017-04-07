package com.nascenia.biyeta.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nascenia.biyeta.R;

import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.Favourite;
import com.nascenia.biyeta.fragment.Inbox;
import com.nascenia.biyeta.fragment.Match;
import com.nascenia.biyeta.fragment.Search;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class HomeScreen extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_PHONE_CALL = 100;
    static Context context;
    DrawerLayout drawerLayout;


    private View actionBarView;
    private String responseValue = null;
    private SharePref sharePref;

    private ImageView searchImageView, matchImageView, fevImageView, inboxImageView,
            profileImageView, menuProfileImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        context = this;

        sharePref = new SharePref(HomeScreen.this);

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        searchImageView.setOnClickListener(this);
        matchImageView.setOnClickListener(this);
        fevImageView.setOnClickListener(this);
        inboxImageView.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        Log.e("name", sharePref.get_data("display_name"));

        View header = navigationView.getHeaderView(0);
        TextView display_name = (TextView) header.findViewById(R.id.displayname);
        display_name.setText(sharePref.get_data("display_name"));
        menuProfileImgView = (ImageView) header.findViewById(R.id.img_profile);

        //set profile image on drawer
        if (sharePref.get_data("profile_picture").equals("key") &&
                (sharePref.get_data("gender").equals(Utils.MALE_GENDER))
                ) {
            menuProfileImgView.setImageResource(R.drawable.profile_icon_male);

        } else if (sharePref.get_data("profile_picture").equals("key") &&
                (sharePref.get_data("gender").equals(Utils.FEMALE_GENDER))
                ) {
            menuProfileImgView.setImageResource(R.drawable.profile_icon_female);
        } else if (!sharePref.get_data("profile_picture").equals("key")) {
            Picasso.with(this)
                    .load("http://test.biyeta.com" + sharePref.get_data("profile_picture"))
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
            Log.i("image", "no image found");
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

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_profile:
                        startActivity(new Intent(HomeScreen.this, OwnUserProfileActivity.class).
                                putExtra("id", sharePref.get_data("user_id")).
                                putExtra("user_name", sharePref.get_data("display_name"))
                                .putExtra("PROFILE_EDIT_OPTION", true)
                        );
                        break;


                    case R.id.nav_balance:
                        startActivity(new Intent(HomeScreen.this, PaymentActivity.class));

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
                        sharePref.set_data("token", "key");
                        startActivity(new Intent(HomeScreen.this, Login.class));
                        finish();

                        break;

                    default:
                        break;
                }


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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "OnResume");
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
                }
                break;


        }

    }


}