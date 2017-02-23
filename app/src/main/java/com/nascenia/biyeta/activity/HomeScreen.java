package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nascenia.biyeta.R;

import com.nascenia.biyeta.fragment.Favourite;
import com.nascenia.biyeta.fragment.Match;
import com.nascenia.biyeta.fragment.Search;


public class HomeScreen extends AppCompatActivity {


    static Context context;
    DrawerLayout drawerLayout;

    private View actionBarView;


    private ImageView searchImageView, matchImageView, fevImageView, inboxImageView, profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        context = this;

        actionBarView = getLayoutInflater().inflate(R.layout.activity_main_actionbar_item, null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(actionBarView);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        searchImageView = (ImageView) actionBarView.findViewById(R.id.search);
        matchImageView = (ImageView) actionBarView.findViewById(R.id.match);
        fevImageView = (ImageView) actionBarView.findViewById(R.id.favorite);
        // inboxImageView = (ImageView) actionBarView.findViewById(R.id.inbox);
        profileImageView = (ImageView) actionBarView.findViewById(R.id.profile);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentParentViewGroup, new Search())
                .commit();


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Search())
                        .commit();
            }
        });


        fevImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Favourite())
                        .commit();

            }
        });


        matchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, new Match())
                        .commit();

            }
        });

    }


}