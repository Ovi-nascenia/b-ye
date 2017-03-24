package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.adapter.MatchUserChoiceAdapter;
import com.nascenia.biyeta.adapter.OtherInfoRecylerViewAdapter;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.ProfileImageFirstFragment;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.SendRequestFragmentView;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saiful on 3/3/17.
 */


public class NewUserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private ImageView indicatorImage1, indicatorImage2, indicatorImage3, userProfileImage;

    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView,
            familyMemberInfoRecylerView, communicationInfoRecylerview,
            otherInfoRecylerView;


    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();

    private TextView userProfileDescriptionText, userNameTextView, familyInfoTagTextView,
            communicationTagTextview, otherInfoTagTextview;

    private ImageView profileViewerPersonImageView;

    private CardView familyCardView, communicationCarview, otherInfoCardView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_profile);

        initView();


        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                switch (position) {
                    case 0:
                        indicatorImage1.setVisibility(View.VISIBLE);

                        break;
                    case 1:
                        indicatorImage2.setVisibility(View.VISIBLE);

                        break;

                    case 2:
                        indicatorImage3.setVisibility(View.VISIBLE);

                        break;


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (Utils.isOnline(getBaseContext())) {

            fetchUserProfileDetails("http://test.biyeta.com/api/v1/profiles/" +
                    getIntent().getExtras().getString("id"));

           /* fetchUserProfileDetails("http://test.biyeta.com/api/v1/profiles/" +
                    "400");*/


        } else {
            Utils.ShowAlert(getBaseContext(), "please check your internet connection");
        }


    }

    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        generalInfoRecyclerView = (RecyclerView) findViewById(R.id.user_general_info_recycler_view);
        generalInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchUserChoiceRecyclerView = (RecyclerView) findViewById(R.id.match_user_choice_recyclerView);
        matchUserChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        familyMemberInfoRecylerView = (RecyclerView) findViewById(R.id.family_info_recylerview);
        familyMemberInfoRecylerView.setLayoutManager(new LinearLayoutManager(this));
        communicationInfoRecylerview = (RecyclerView) findViewById(R.id.communication_info_recylerview);
        communicationInfoRecylerview.setLayoutManager(new LinearLayoutManager(this));
        otherInfoRecylerView = (RecyclerView) findViewById(R.id.other_info_recylerview);
        otherInfoRecylerView.setLayoutManager(new LinearLayoutManager(this));

        userProfileDescriptionText = (TextView) findViewById(R.id.userProfileDescriptionText);
        profileViewerPersonImageView = (ImageView) findViewById(R.id.viewer_image);

        userProfileImage = (ImageView) findViewById(R.id.user_profile_image);
        userNameTextView = (TextView) findViewById(R.id.user_name);

        userNameTextView.setText(getIntent().getExtras().getString("user_name"));

        familyInfoTagTextView = (TextView) findViewById(R.id.family_info_tag);
        familyCardView = (CardView) findViewById(R.id.family_info_recylerview_card);

        communicationTagTextview = (TextView) findViewById(R.id.communication_tag_textview);
        communicationCarview = (CardView) findViewById(R.id.communication_card_layout);

        otherInfoTagTextview = (TextView) findViewById(R.id.other_info_tag);
        otherInfoCardView = (CardView) findViewById(R.id.other_info_cardview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        indicatorImage1 = (ImageView) findViewById(R.id.page1);
        indicatorImage2 = (ImageView) findViewById(R.id.page2);
        indicatorImage3 = (ImageView) findViewById(R.id.page3);

    }

    private void fetchUserProfileDetails(final String url) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = new ResourceProvider(NewUserProfileActivity.this).fetchGetResponse(url);
                    ResponseBody responseBody = response.body();
                    final String responseValue = responseBody.string();
                    Log.i("responsedata", "response value: " + responseValue + " ");
                    responseBody.close();
                    final UserProfile userProfile = new Gson().fromJson(responseValue, UserProfile.class);
                    NewUserProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //setupRecyclerView(moviePage);
                            //
                            //Toast.makeText(getApplicationContext(), userProfile.toString() + "", Toast.LENGTH_LONG).show();
                            //Log.i("responsedata", "totaldata: " + userProfile.getProfile().getPersonalInformation().getGender());


                            if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
                                userProfileDescriptionText.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
                            }


                            if (userProfile.getProfile().getPersonalInformation().getImage() != null) {

                                //Loading image from below url into imageView
                                Log.i("imageurluser", Utils.Base_URL +
                                        userProfile.getProfile().getPersonalInformation().getImage()
                                                .getProfilePicture());

//                                Glide.with(getBaseContext())
//                                        .load(Utils.Base_URL +
//                                                userProfile.getProfile().getPersonalInformation().getImage()
//                                                        .getProfilePicture())
//                                        .into(userProfileImage);
                                Picasso.with(NewUserProfileActivity.this)
                                        .load(Utils.Base_URL +  userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture())
                                        .into(userProfileImage, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                userProfileImage.post(new Runnable() {
                                              @Override
                                              public void run() {
                                                  Utils.scaleImage(NewUserProfileActivity.this, 1.2f, userProfileImage);
                                              }
                                              });
                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });

                                Glide.with(getBaseContext())
                                        .load(Utils.Base_URL +
                                                userProfile.getProfile().getPersonalInformation().
                                                        getImage().getProfilePicture())
                                        .into(profileViewerPersonImageView);


                            } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                                    (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))) {
                                userProfileImage.setImageResource(R.drawable.hel2);
                                profileViewerPersonImageView.setImageResource(R.drawable.hel2);
                            } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                                    (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))) {
                                userProfileImage.setImageResource(R.drawable.hel);
                                profileViewerPersonImageView.setImageResource(R.drawable.hel);
                            } else {
                            }

//                            Utils.scaleImage(NewUserProfileActivity.this, (float)1.2, userProfileImage);
/*
                            addDataonGeneralInfoRecylerViewItem(userProfile);
                            addDataonMatchUserChoiceRecyclerView(userProfile);
                            setDataonOtherInfoRecylerView(userProfile);
*/

                            SendRequestFragmentView.setDataonGeneralInfoRecylerView(
                                    getBaseContext(), userProfile, generalInfoRecyclerView);

                            SendRequestFragmentView.setDataonMatchUserChoiceRecylerView(getBaseContext(),
                                    userProfile, matchUserChoiceRecyclerView);


                            if (userProfile.getProfile().getOtherInformation() != null) {

                                otherInfoTagTextview.setVisibility(View.VISIBLE);
                                otherInfoCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonOtherInfoRecylerView(getBaseContext(), userProfile,
                                        otherInfoRecylerView);

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted())) {

                                //setDataonFamilyMemberInfoRecylerView(userProfile);

                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().isAccepted())) {


                                // setDataonFamilyMemberInfoRecylerView(userProfile);
                                //setDataOnCommunincationRecylerView(userProfile);
                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);

                                communicationTagTextview.setVisibility(View.VISIBLE);
                                communicationCarview.setVisibility(View.VISIBLE);

                                SendRequestFragmentView.setDataOnCommunincationRecylerView(getBaseContext(),
                                        userProfile, communicationInfoRecylerview);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);


                            }


                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private String checkNullField(String value) {


        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return value + ",";
        }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return ProfileImageFirstFragment.newInstance("1", R.drawable.b_icon);
                case 1:
                    return ProfileImageFirstFragment.newInstance("2", R.drawable.check_icon);
                case 2:
                    return ProfileImageFirstFragment.newInstance("3", R.drawable.facebook);

            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
