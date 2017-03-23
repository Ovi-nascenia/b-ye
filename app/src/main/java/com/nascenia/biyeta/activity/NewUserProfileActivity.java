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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.adapter.MatchUserChoiceAdapter;
import com.nascenia.biyeta.adapter.OtherInfoRecylerViewAdapter;
import com.nascenia.biyeta.fragment.ProfileImageFirstFragment;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.SendRequestFragmentView;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;

/**
 * Created by saiful on 3/3/17.
 */


public class NewUserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private ImageView indicatorImage1, indicatorImage2, indicatorImage3, userProfileImage;

    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView,
            familyMemberInfoRecylerView, communicationInfoRecylerview;


    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();

    private TextView userProfileDescriptionText, userNameTextView;

    private ImageView profileViewerPersonImageView;

    private ArrayList<MatchUserChoice> communicationArrayList = new ArrayList<MatchUserChoice>();


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

        userProfileDescriptionText = (TextView) findViewById(R.id.userProfileDescriptionText);
        profileViewerPersonImageView = (ImageView) findViewById(R.id.viewer_image);

        userProfileImage = (ImageView) findViewById(R.id.user_profile_image);
        userNameTextView = (TextView) findViewById(R.id.user_name);

        userNameTextView.setText(getIntent().getExtras().getString("user_name"));

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

                                Glide.with(getBaseContext())
                                        .load(Utils.Base_URL +
                                                userProfile.getProfile().getPersonalInformation().getImage()
                                                        .getProfilePicture())
                                        .into(userProfileImage);

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

                            addDataonGeneralInfoRecylerViewItem(userProfile);
                            addDataonMatchUserChoiceRecyclerView(userProfile);


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted())) {


                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile,
                                        familyMemberInfoRecylerView);

                            }

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().isAccepted())) {


                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile,
                                        familyMemberInfoRecylerView);


                                addDataOnCommunincationRecylerView(userProfile);


                            }


                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void addDataOnCommunincationRecylerView(UserProfile userProfile) {


        String presentAddress = "";

        if (!(checkNullField(userProfile.getProfile().getAddress().getPresentAddress().getAddress())
                .equals(""))) {
            presentAddress = presentAddress + userProfile.getProfile().getAddress().getPresentAddress().getAddress();

        }

        if (!(checkNullField(userProfile.getProfile().getAddress().getPresentAddress().getAddress())
                .equals(""))) {
            presentAddress = presentAddress + userProfile.getProfile().getAddress().getPresentAddress().getCountry();

        }


        communicationArrayList.add(new MatchUserChoice("present address",
                checkNullField(presentAddress)));


        if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getAddress()).equals(""))) {

            communicationArrayList.add(new MatchUserChoice("permanet address",
                    userProfile.getProfile().getAddress().getPermanentAddress().getAddress()));

        }


        if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getDistrict()).equals(""))) {

            communicationArrayList.add(new MatchUserChoice("district",
                    userProfile.getProfile().getAddress().getPermanentAddress().getAddress()));

        }

        if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getCountry()).equals(""))) {

            communicationArrayList.add(new MatchUserChoice("country",
                    userProfile.getProfile().getAddress().getPermanentAddress().getCountry()));

        }


        communicationInfoRecylerview.setAdapter(new OtherInfoRecylerViewAdapter(
                getBaseContext(), communicationArrayList));

    }


    private void addDataonMatchUserChoiceRecyclerView(UserProfile userProfile) {

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHomeTown())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("home town"
                    , userProfile.getProfile().getMatchingAttributes().getHomeTown()));

        }


        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getAge())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("age"
                    , userProfile.getProfile().getMatchingAttributes().getAge()));

        }
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHeight())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("height"
                    , userProfile.getProfile().getMatchingAttributes().getHeight()));

        }
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getSkinColor())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("skin color"
                    , userProfile.getProfile().getMatchingAttributes().getSkinColor()));

        }
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHealth())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("health"
                    , userProfile.getProfile().getMatchingAttributes().getHealth()));

        }
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getTitleEducationalQualification())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("educational qualification"
                    , userProfile.getProfile().getMatchingAttributes().getTitleEducationalQualification()));

        }

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getTitleOwnHouse())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("own house"
                    , userProfile.getProfile().getMatchingAttributes().getTitleOwnHouse()));

        }
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getTitleOccupation())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("occupation"
                    , userProfile.getProfile().getMatchingAttributes().getTitleOccupation()));

        }


        matchUserChoiceRecyclerView.setAdapter(new MatchUserChoiceAdapter(getBaseContext(), matchUserChoiceArrayList));

    }


    private String checkNullField(String value) {


        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return value + ",";
        }

    }

    private void addDataonGeneralInfoRecylerViewItem(UserProfile userProfile) {


        generalInformationArrayList.add(new GeneralInformation(
                Utils.convertEnglishDigittoBangla(
                        userProfile.getProfile().getPersonalInformation().getAge()) + " বছর," +
                        Utils.convertEnglishDigittoBangla(userProfile.getProfile().getPersonalInformation().getHeightFt())
                        + "'" +
                        Utils.convertEnglishDigittoBangla(userProfile.getProfile().getPersonalInformation().getHeightInc())
                        + "\"," +
                        userProfile.getProfile().getProfileReligion().getReligion()
                        + "(" +
                        userProfile.getProfile().getProfileReligion().getCast()
                        + ")"

                , R.drawable.per));

        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getCountry())).equals("")) {

            generalInformationArrayList.add(new GeneralInformation(

                    "বর্তমান অবস্থান-" + checkNullField(
                            userProfile.getProfile().getProfileLivingIn().getCountry())
                    , R.drawable.pla));

        }


        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())).equals("")) {

            generalInformationArrayList.add(new GeneralInformation(

                    "দেশের বাড়ি-" + checkNullField(
                            userProfile.getProfile().getProfileLivingIn().getLocation()), R.drawable.hom));

        }


        if (!(checkNullField(userProfile.getProfile().getProfession().getProfessionalGroup())).equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getProfession().getProfessionalGroup())
                    , R.drawable.pro));
        }


        String education = "";

        if (userProfile.getProfile().getEducationInformation().size() > 0) {

            for (int i = 0; i < userProfile.getProfile().getEducationInformation().size(); i++) {

                EducationInformation educationInformation = userProfile.getProfile().
                        getEducationInformation().get(i);


                education = education + checkNullField(educationInformation.getHighestDegree()) +
                        checkNullField(educationInformation.getInstitution());


            }

            generalInformationArrayList.add(new GeneralInformation(

                    education, R.drawable.edu));
        }


        if ((!(checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor()) +
                checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals(""))
                & (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))
                ) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor())
                            +
                            checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())
                    , R.drawable.hel2));

        }


        if ((!(checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor()) +
                checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals(""))
                & (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))
                ) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor())
                            +
                            checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())
                    , R.drawable.hel));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()))
                .equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus())
                    , R.drawable.mar));
        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()) +
                checkNullField(userProfile.getProfile().getOtherInformation().getFasting())).equals("")) {

            String prayer = "";
            String fast = "";


            if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()))
                    .equals("")) {

                prayer = userProfile.getProfile().getOtherInformation().getPrayer() + " নামায পরেন";
            }

            if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()))
                    .equals("")) {

                fast = userProfile.getProfile().getOtherInformation().getFasting() + " রোজা রাখেন";
            }


            generalInformationArrayList.add(new GeneralInformation(

                    prayer + "," + fast
                    , R.drawable.pra));

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
