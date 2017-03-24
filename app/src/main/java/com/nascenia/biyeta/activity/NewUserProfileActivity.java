package com.nascenia.biyeta.activity;

import android.content.Context;
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
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
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

    private TextView userProfileDescriptionText, userNameTextView;

    private ImageView profileViewerPersonImageView;

    private ArrayList<MatchUserChoice> communicationArrayList = new ArrayList<MatchUserChoice>();
    private ArrayList<MatchUserChoice> otherInformationArrayList;

    private ArrayList<UserProfileChild> parentChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileChild> brotherChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileChild> sisterChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileChild> childItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileChild> otherCHildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> mainparentItemList = new ArrayList<UserProfileParent>();
    private int familyMemberCounter;


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
                            setDataonOtherInfoRecylerView(userProfile);

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted())) {

                                setDataonFamilyMemberInfoRecylerView(userProfile);

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().isAccepted())) {


                                setDataonFamilyMemberInfoRecylerView(userProfile);
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


    private void addDataOnCommunincationRecylerView(UserProfile userProfile) {


        String presentAddress = "";
        String permanentAddress = "";


        if (userProfile.getProfile().getAddress() != null) {

            if (!(checkNullField(userProfile.getProfile().getAddress().getPresentAddress().getAddress())
                    .equals(""))) {
                presentAddress = presentAddress + checkNullField(
                        userProfile.getProfile().getAddress().getPresentAddress().getAddress());

            }

            if (!(checkNullField(userProfile.getProfile().getAddress().getPresentAddress().getCountry())
                    .equals(""))) {
                presentAddress = presentAddress + checkNullField(
                        userProfile.getProfile().getAddress().getPresentAddress().getCountry());

            }

            //add present address
            if (!presentAddress.equals("")) {
                communicationArrayList.add(new MatchUserChoice("বর্তমান ঠিকানা",
                        presentAddress));

            }


            if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getAddress())
                    .equals(""))) {


                permanentAddress = permanentAddress + checkNullField(userProfile.getProfile().getAddress().
                        getPermanentAddress().getAddress());

            }


            if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getCountry()).equals(""))) {


                permanentAddress = permanentAddress +
                        checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getCountry());


            }

            //add permanent address
            if (permanentAddress != null) {

                communicationArrayList.add(new MatchUserChoice("স্থায়ী ঠিকানা", permanentAddress));
            }


            if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getDistrict()).equals(""))) {

                communicationArrayList.add(new MatchUserChoice("জেলা",
                        userProfile.getProfile().getAddress().getPermanentAddress().getDistrict()));

            }


            if (!(checkNullField(userProfile.getProfile().getAddress().getPermanentAddress().getCountry()).equals(""))) {

                communicationArrayList.add(new MatchUserChoice("দেশ",
                        userProfile.getProfile().getAddress().getPermanentAddress().getCountry()));

            }


            if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMobileNo()).equals(""))) {

                communicationArrayList.add(new MatchUserChoice("ফোন নম্বর",
                        userProfile.getProfile().getPersonalInformation().getMobileNo()));

            }


            communicationInfoRecylerview.setAdapter(new OtherInfoRecylerViewAdapter(
                    getBaseContext(), communicationArrayList));
        }
    }


    private void addDataonMatchUserChoiceRecyclerView(UserProfile userProfile) {


        if (userProfile.getProfile().getMatchingAttributes() != null) {


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
    }


    private void setDataonFamilyMemberInfoRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getFamilyMembers() != null) {

            //add father information
            if (userProfile.getProfile().getFamilyMembers().getFather() != null) {

                parentChildItemList.add(new UserProfileChild("বাবা",
                        checkNullField(userProfile.getProfile().getFamilyMembers().getFather()
                                .getName())
                                + checkNullField(userProfile.getProfile().getFamilyMembers()
                                .getFather().getOccupation())
                                + checkNullField(userProfile.getProfile().getFamilyMembers()
                                .getFather().getDesignation())
                                + checkNullField(userProfile.getProfile().getFamilyMembers()
                                .getFather().getInstitute())
                ));

            }


            //add mother information
            if (userProfile.getProfile().getFamilyMembers().getFather() != null) {

                parentChildItemList.add(new UserProfileChild("মা",
                        checkNullField(userProfile.getProfile().getFamilyMembers().getMother()
                                .getName())
                                + checkNullField(userProfile.getProfile().getFamilyMembers().getMother()
                                .getOccupation())
                                + checkNullField(userProfile.getProfile().getFamilyMembers().getMother()
                                .getDesignation())
                                + checkNullField(userProfile.getProfile().getFamilyMembers().getMother()
                                .getInstitute())
                ));

            }

            //add sister information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfSisters() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getSisters().size(); i++) {

                    familyMemberCounter = i + 1;
                    sisterChildItemList.add(new UserProfileChild(
                            "বোন " + Utils.convertEnglishDigittoBangla(familyMemberCounter),

                            checkNullField(userProfile.getProfile().getFamilyMembers().getSisters().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getSisters().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getSisters().get(i).getInstitute())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getMaritalStatus())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getSisters().get(i).getSpouseName())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getSisters().get(i).getSpouseInstitue())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getSpouseDesignation())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getSpouseOccupation())

                    ));
                }

            }

            //add brother information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfSisters() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++) {

                    familyMemberCounter = i + 1;
                    brotherChildItemList.add(new UserProfileChild(
                            "ভাই " + Utils.convertEnglishDigittoBangla(familyMemberCounter),

                            checkNullField(userProfile.getProfile().getFamilyMembers().getBrothers().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getBrothers().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getBrothers().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getBrothers().get(i).getInstitute())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getBrothers().get(i).getMaritalStatus())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getBrothers().get(i).getSpouseName())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getBrothers().get(i).getSpouseInstitue())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getBrothers().get(i).getSpouseDesignation())

                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getBrothers().get(i).getSpouseOccupation())

                    ));
                }


            }


            //add child information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                    userProfile.getProfile().getFamilyMembers().isChildLivesWithYou()) {

                childItemList.add(new UserProfileChild("সন্তান",
                        Utils.convertEnglishDigittoBangla(
                                userProfile.getProfile().getFamilyMembers().getNumberOfChild())
                                + " জন সন্তান, তার সাথে থাকে"));

            } else if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                    !(userProfile.getProfile().getFamilyMembers().isChildLivesWithYou())) {

                childItemList.add(new UserProfileChild("সন্তান",
                        Utils.convertEnglishDigittoBangla(
                                userProfile.getProfile().getFamilyMembers().getNumberOfChild())
                                + " জন সন্তান, তার সাথে থাকে না"));
            }


            //add dada information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfDada() > 0) {


                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getDadas().size(); i++) {

                    otherCHildItemList.add(new UserProfileChild(
                            "দাদা", checkNullField(userProfile.getProfile().getFamilyMembers().getDadas().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getDadas().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getDadas().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getDadas().get(i).getInstitute())

                    ));
                }


            }


            //add kaka information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfKaka() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKakas().size(); i++) {

                    familyMemberCounter = i + 1;
                    otherCHildItemList.add(new UserProfileChild(
                            "চাচা " + Utils.convertEnglishDigittoBangla(familyMemberCounter),
                            checkNullField(userProfile.getProfile().getFamilyMembers().getKakas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getKakas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKakas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKakas().get(i).getInstitute())

                    ));
                }


            }


            //add mama information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfMama() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getMamas().size(); i++) {

                    familyMemberCounter = i + 1;
                    otherCHildItemList.add(new UserProfileChild(
                            "মামা " + Utils.convertEnglishDigittoBangla(familyMemberCounter),

                            checkNullField(userProfile.getProfile().getFamilyMembers().getMamas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getMamas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getMamas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getMamas().get(i).getInstitute())

                    ));
                }


            }


            //add fufa information


            if (userProfile.getProfile().getFamilyMembers().getNumberOfFufa() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getFufas().size(); i++) {

                    familyMemberCounter = i + 1;
                    otherCHildItemList.add(new UserProfileChild(
                            "ফুপা " + Utils.convertEnglishDigittoBangla(familyMemberCounter),

                            checkNullField(userProfile.getProfile().getFamilyMembers().getFufas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getFufas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getFufas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getFufas().get(i).getInstitute())

                    ));
                }


            }


            //add nana information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfNana() > 0) {

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getNanas().size(); i++) {

                    otherCHildItemList.add(new UserProfileChild(
                            "নানা", checkNullField(userProfile.getProfile().getFamilyMembers().getNanas().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getNanas().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getNanas().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getNanas().get(i).getInstitute())

                    ));
                }


            }


            //add khalu information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfKhalu() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKhalus().size(); i++) {

                    familyMemberCounter = i + 1;
                    otherCHildItemList.add(new UserProfileChild(
                            "খালু " + Utils.convertEnglishDigittoBangla(familyMemberCounter),

                            checkNullField(userProfile.getProfile().getFamilyMembers().getKhalus().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getKhalus().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKhalus().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKhalus().get(i).getInstitute())

                    ));
                }


            }


        }

        // add child list data to parent list


        //add parentchildlist data to mainparentlist
        if (parentChildItemList.size() > 0) {
            mainparentItemList.add(new UserProfileParent("বাবা-মা", parentChildItemList));

        }


        //add sisterchildlist data to mainparentlist
        if (sisterChildItemList.size() > 0) {
            mainparentItemList.add(new UserProfileParent(
                    "বোন(" + Utils.convertEnglishDigittoBangla(sisterChildItemList.size()) + ")"
                    , sisterChildItemList));

        } else {

            sisterChildItemList.add(new UserProfileChild("বোন", "কোন বোন নেই"));
            mainparentItemList.add(new UserProfileParent("বোন", sisterChildItemList));

        }


        //add brotherchildlist data to mainparentlist
        if (brotherChildItemList.size() > 0) {
            mainparentItemList.add(new UserProfileParent(
                    "ভাই(" + Utils.convertEnglishDigittoBangla(brotherChildItemList.size()) + ")"
                    , brotherChildItemList));

        } else {

            brotherChildItemList.add(new UserProfileChild("ভাই", "কোন ভাই নেই"));
            mainparentItemList.add(new UserProfileParent("ভাই", brotherChildItemList));

        }


        //add childlist data to mainparentlist
        if (childItemList.size() > 0) {

            mainparentItemList.add(new UserProfileParent("সন্তান"
                    , childItemList));

        } else {
            childItemList.add(new UserProfileChild("সন্তান", "কোন সন্তান নেই"));
            mainparentItemList.add(new UserProfileParent("সন্তান", childItemList));

        }


        if (otherCHildItemList.size() > 0) {

            mainparentItemList.add(new UserProfileParent(
                    "অন্যান্য(" + Utils.convertEnglishDigittoBangla(otherCHildItemList.size()) + ")"
                    , otherCHildItemList));
        }

        familyMemberInfoRecylerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                mainparentItemList,
                false));

    }

    private void setDataonOtherInfoRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getOtherInformation() != null) {

            otherInformationArrayList = new ArrayList<MatchUserChoice>();

            if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()).equals(""))) {

                otherInformationArrayList.add(new MatchUserChoice("Fasting",
                        checkNullField(userProfile.getProfile().getOtherInformation().getFasting())));

            }


            if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()).equals(""))) {

                otherInformationArrayList.add(new MatchUserChoice("Prayer",
                        checkNullField(userProfile.getProfile().getOtherInformation().getPrayer())));

            }


            if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                    & (!(checkNullField(userProfile.getProfile().getOtherInformation().getJobAfterMarriage()).equals("")))) {


                otherInformationArrayList.add(new MatchUserChoice("Job After Marrige",
                        checkNullField(userProfile.getProfile().getOtherInformation().getJobAfterMarriage())));
            }


            if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                    & (!(checkNullField(userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


                otherInformationArrayList.add(new MatchUserChoice("Hijab",
                        checkNullField(userProfile.getProfile().getOtherInformation().getHijab())));
            }

            if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))

                    & (!(checkNullField(userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


                otherInformationArrayList.add(new MatchUserChoice("Own House",
                        checkNullField(userProfile.getProfile().getOtherInformation().getOwnHouse())));
            }


            otherInfoRecylerView.setAdapter(new OtherInfoRecylerViewAdapter(
                    getBaseContext(), otherInformationArrayList));
        }
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
