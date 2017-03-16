package com.nascenia.biyeta.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.nascenia.biyeta.R;

import com.nascenia.biyeta.fragment.Blog;
import com.nascenia.biyeta.fragment.Favourite;
import com.nascenia.biyeta.fragment.Inbox;
import com.nascenia.biyeta.fragment.Match;
import com.nascenia.biyeta.fragment.Search;

import org.json.JSONObject;

import java.util.ArrayList;


public class HomeScreen extends AppCompatActivity implements View.OnClickListener {


    static Context context;
    DrawerLayout drawerLayout;


    private View actionBarView;


    private ImageView searchImageView, matchImageView, fevImageView, inboxImageView, profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        context = this;


        initIdAndActionBar();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentParentViewGroup, new Search())
                .commit();
        searchImageView.setColorFilter(Color.WHITE);
        matchImageView.setColorFilter(Color.GRAY);
        inboxImageView.setColorFilter(Color.GRAY);
        fevImageView.setColorFilter(Color.GRAY);
        profileImageView.setColorFilter(Color.GRAY);


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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                // update highlighted item in the navigation menu
                menuItem.setChecked(true);
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.closeDrawer(Gravity.RIGHT);


                if (menuItem.getItemId() == R.id.nac_blog) {
                    Toast.makeText(HomeScreen.this, "item 2", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentParentViewGroup, new Blog())
                            .commit();


                }

                if (menuItem.getItemId() == R.id.nav_profile) {


                   /* Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra("id", "316");
                    intent.putExtra("user_name", "Saiful");
                    intent.putExtra("PROFILE_EDIT_OPTION", true);
                    startActivity(intent);*/


                    startActivity(new Intent(getBaseContext(), NewUserProfileActivity.class));
                }

                return true;
            }
        });


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

                Dialog dialog = new Dialog(HomeScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.inbox);
                dialog.findViewById(R.id.tv_inbox).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(HomeScreen.this, InboxListView.class));
                    }
                });
                DisplayMetrics displaymetrics = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = (int) ((int) displaymetrics.widthPixels * 0.7);
                int height = (int) ((int) displaymetrics.heightPixels * 0.32);
                dialog.getWindow().setLayout(width, height);
                dialog.show();
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