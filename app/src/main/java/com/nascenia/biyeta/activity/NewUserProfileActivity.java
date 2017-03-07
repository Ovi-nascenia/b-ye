package com.nascenia.biyeta.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.adapter.MatchUserChoiceAdapter;
import com.nascenia.biyeta.fragment.ProfileImageFirstFragment;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saiful on 3/3/17.
 */


public class NewUserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private ImageView indicatorImage1, indicatorImage2, indicatorImage3;

    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView;


    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();

    private TextView userProfileDescriptionText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_profile);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        generalInfoRecyclerView = (RecyclerView) findViewById(R.id.user_general_info_recycler_view);
        generalInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchUserChoiceRecyclerView = (RecyclerView) findViewById(R.id.match_user_choice_recyclerView);
        matchUserChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userProfileDescriptionText = (TextView) findViewById(R.id.userProfileDescriptionText);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        indicatorImage1 = (ImageView) findViewById(R.id.page1);
        indicatorImage2 = (ImageView) findViewById(R.id.page2);
        indicatorImage3 = (ImageView) findViewById(R.id.page3);


    /*    generalInformationArrayList.add(new GeneralInformation("country,dhaka"));
        generalInformationArrayList.add(new GeneralInformation("country,dhaka"));
        generalInformationArrayList.add(new GeneralInformation("country,dhaka"));


        matchUserChoiceArrayList.add(new MatchUserChoice("country name", "Bangladesh"));
        matchUserChoiceArrayList.add(new MatchUserChoice("Division name", "Barishal"));
        matchUserChoiceArrayList.add(new MatchUserChoice("District name", "Patuakhali"));

        // Toast.makeText(getBaseContext(), generalInformationArrayList.size() + " " + matchUserChoiceArrayList.size(), Toast.LENGTH_SHORT).show();
        generalInfoRecyclerView.setAdapter(new GeneralInformationAdapter(getBaseContext(), generalInformationArrayList));
        matchUserChoiceRecyclerView.setAdapter(new MatchUserChoiceAdapter(getBaseContext(), matchUserChoiceArrayList));*/


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

        fetchUserProfileDetails("http://test.biyeta.com/api/v1/profiles/296");

        // Log.i("bangla", "value " + Utils.convertEnglishDigittoBangla(11));

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

                            //Toast.makeText(getApplicationContext(), userProfile.toString() + "", Toast.LENGTH_LONG).show();
                            // Log.i("responsedata", "totaldata: " + userProfile.getProfile().getEducationInformation().getInstitution());

                            if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
                                userProfileDescriptionText.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
                            }

                            addDataonGeneralInfoRecylerViewItem(userProfile);
                            addDataonMatchUserChoiceRecyclerView(userProfile);


                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void addDataonMatchUserChoiceRecyclerView(UserProfile userProfile) {

        matchUserChoiceArrayList.add(new MatchUserChoice("home town"
                , userProfile.getProfile().getMatchingAttributes().getHomeTown()));
        matchUserChoiceArrayList.add(new MatchUserChoice("age"
                , userProfile.getProfile().getMatchingAttributes().getAge()));
        matchUserChoiceArrayList.add(new MatchUserChoice("height"
                , userProfile.getProfile().getMatchingAttributes().getHeight()));
        matchUserChoiceArrayList.add(new MatchUserChoice("skin color"
                , userProfile.getProfile().getMatchingAttributes().getSkinColor()));
        matchUserChoiceArrayList.add(new MatchUserChoice("health"
                , userProfile.getProfile().getMatchingAttributes().getHealth()));
        matchUserChoiceArrayList.add(new MatchUserChoice("marital status"
                , userProfile.getProfile().getMatchingAttributes().getMaritalStatus()));
        matchUserChoiceArrayList.add(new MatchUserChoice("educational qualification"
                , userProfile.getProfile().getMatchingAttributes().getTitleEducationalQualification()));
        matchUserChoiceArrayList.add(new MatchUserChoice("own house"
                , userProfile.getProfile().getMatchingAttributes().getTitleOwnHouse()));
        matchUserChoiceArrayList.add(new MatchUserChoice("occupation"
                , userProfile.getProfile().getMatchingAttributes().getTitleOccupation()));
        matchUserChoiceRecyclerView.setAdapter(new MatchUserChoiceAdapter(getBaseContext(), matchUserChoiceArrayList));

    }


    private String checkNullField(String value) {


        if (value == null) {
            return "";
        } else {
            return value;
        }

    }

    private void addDataonGeneralInfoRecylerViewItem(UserProfile userProfile) {


        generalInformationArrayList.add(new GeneralInformation(
                userProfile.getProfile().getPersonalInformation().getAge()
                        + "" +
                        userProfile.getProfile().getPersonalInformation().getHeightFt()
                        + "" +
                        userProfile.getProfile().getPersonalInformation().getHeightInc()));


        if (!(checkNullField(userProfile.getProfile().getProfileReligion().getReligion())
                + checkNullField(userProfile.getProfile().getProfileReligion().getCast())).equals("")) {


            generalInformationArrayList.add(new GeneralInformation(
                    checkNullField(userProfile.getProfile().getProfileReligion().getReligion()) + "" +
                            checkNullField(userProfile.getProfile().getProfileReligion().getCast())
            ));

        }


        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())
                + checkNullField(userProfile.getProfile().getProfileLivingIn().getCountry())).equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())
                            + checkNullField(userProfile.getProfile().getProfileLivingIn().getCountry())

            ));

        }


        if (!(checkNullField(userProfile.getProfile().getProfession().getProfessionalGroup())).equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getProfession().getProfessionalGroup())
            ));
        }


        if (!(checkNullField(userProfile.getProfile().getEducationInformation().getHighestDegree()) +
                checkNullField(userProfile.getProfile().getEducationInformation().getInstitution())).equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getEducationInformation().getHighestDegree()) + "" +
                            checkNullField(userProfile.getProfile().getEducationInformation().getInstitution())
            ));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor()) +
                checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals("")) {

            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor())
                            + "" +
                            checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())
            ));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()))
                .equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()
                    )

            ));
        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()) +
                checkNullField(userProfile.getProfile().getOtherInformation().getFasting())).equals("")) {

            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()) + "" +
                            checkNullField(userProfile.getProfile().getOtherInformation().getFasting())

            ));

        }


        generalInfoRecyclerView.setAdapter(new GeneralInformationAdapter(
                getBaseContext(), generalInformationArrayList));


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
