package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.adapter.ViewPagerAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.CalenderBanglaInfo;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.CustomStopScrollingRecylerLayoutManager;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import static com.nascenia.biyeta.view.SendRequestFragmentView.checkNullField;

/**
 * Created by saiful on 4/2/17.
 */

public class OwnUserProfileActivity extends AppCompatActivity{
    int page_position = 0;
    private Tracker mTracker;
    private AnalyticsApplication application;

    private RecyclerView personalInfoRecylerView;
    private RecyclerView educationRecylerView;
    private RecyclerView professionRecyclerView;
    private RecyclerView parentsRecyclerView;
    private RecyclerView brotherRecyclerView;
    private RecyclerView sisterRecyclerView;
    private RecyclerView childRecyclerView;
    private RecyclerView otherRelativeInfoRecyclerView;
    private RecyclerView otherInformationRecyclerView;
    private LinearLayout sliderDotsPanel;
    private  int dotscount;
    private ImageView[] dots;

    private UserProfile userProfile;

    // private int familyMemberCounter;

    private ImageView userProfileImage;

    private TextView userProfileDescriptionText;


    private ArrayList<UserProfileChild> personalInfoChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> personalInfoChildItemHeader = new ArrayList<UserProfileParent>();

    private ArrayList<UserProfileChild> educationalInfoChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> educationalInfoChildItemHeader = new ArrayList<UserProfileParent>();

    private ArrayList<UserProfileChild> professionChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> professionChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> parentChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> parentChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> brothersChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> brothersChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> sistersChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> sistersChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> childsChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> childsChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> otherRelativeChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> otherRelativeChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> otherInfoChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> otherInfoChildItemHeader = new ArrayList<UserProfileParent>();


    private ProgressDialog progressDialog;

    private CoordinatorLayout coordnatelayout;

    private TextView userNameTextView;

    private CardView otherRelativeCardview, childCardView, otherInfoCardview;
    private String res;

    private SharePref sharePref;


    private ViewPager viewPager;
    private PagerAdapter adapter;
    int[] image;
    String[] proPics = null;
    private UserProfileExpenadlbeAdapter upea;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_profile_layout);


        sharePref = new SharePref(OwnUserProfileActivity.this);
        initView();


        if (Utils.isOnline(getApplicationContext())) {

            fetchUserProfileInfo();

        } else {
            Utils.ShowAlert(OwnUserProfileActivity.this, getString(R.string.no_internet_connection));
            //finish();
        }

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Google Analytics*/
        mTracker.setScreenName(getIntent().getExtras().getString("user_name")+ "'s Profile");
//        Log.i("user_name", getIntent().getExtras().getString("user_name")+ "'s Profile");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void fetchUserProfileInfo() {
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Response response = new ResourceProvider(OwnUserProfileActivity.this).
                            fetchGetResponse(Utils.APPUSER_OWN_PROFILE_VIEW_URL);
                    ResponseBody responseBody = response.body();
                    final String responseValue = responseBody.string();
                    res = responseValue;
                    Log.i("ownresponsevalue", responseValue);
                    responseBody.close();
                    userProfile = new Gson().fromJson(responseValue, UserProfile.class);


                    OwnUserProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            coordnatelayout.setVisibility(View.VISIBLE);

                            if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
                                userProfileDescriptionText.setText(userProfile.getProfile().
                                        getPersonalInformation().getAboutYourself());


                                if (userProfile.getProfile().getPersonalInformation().getImage() != null) {
                                    if(userProfile.getProfile().getPersonalInformation().getImage().getOther().size() >1) {

                                        dotscount = userProfile.getProfile().getPersonalInformation().getImage().getOther().size();
                                        dots = new ImageView[dotscount];

                                        for (int i = 0; i < dotscount; i++) {
                                            dots[i] = new ImageView(getApplicationContext());
                                            if (i == 0)
                                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));
                                            else
                                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            params.setMargins(6, 4, 6, 4);
                                            sliderDotsPanel.addView(dots[i], params);
                                            dots[i].getLayoutParams().height = 16;
                                            dots[i].getLayoutParams().width = 16;
                                        }

                                        if (viewPager != null){
                                            viewPager.addOnPageChangeListener(
                                                    new ViewPager.OnPageChangeListener() {
                                                        private int fromPosition = 0, previousState = 0;

                                                        @Override
                                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                            fromPosition = position;
                                                        }

                                                        @Override
                                                        public void onPageSelected(int position) {
                                                            for (int i = 0; i < dotscount; i++) {
                                                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
                                                            }
                                                            dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

                                                        }

                                                        @Override
                                                        public void onPageScrollStateChanged(int state) {
                                                            // if(fromPosition+1==dotscount && state == 1)
                                                            // {
                                                            //     viewPager.setCurrentItem(0,false);
                                                            //}

                                                        /*if (state == ViewPager.SCROLL_STATE_IDLE) {
                                                            int pageCount = dotscount;

                                                            if (fromPosition == 0){
                                                                viewPager.setCurrentItem(pageCount-1,false);
                                                            } else if (fromPosition == pageCount-1){
                                                                viewPager.setCurrentItem(1,false);
                                                            }
                                                        }*/


                                                            if (state == 0 && previousState != 2 && fromPosition == dotscount - 1) {
                                                                viewPager.setCurrentItem(0, true);
                                                                return;
                                                            } else if (state == 0 && previousState != 2 && fromPosition == 0) {
                                                                viewPager.setCurrentItem(dotscount - 1, true);
                                                                return;
                                                            }

                                                            previousState = state;

                                                        }

                                                    }
                                            );
                                    }

                                    }

                                }



                            }

                            userNameTextView.setText(getIntent().getExtras().getString("user_name"));
                            setUserOwnImage(userProfile);
                            setDataOnPersonalInfoRecylerView(userProfile);
                            setDataOnEducationalRecylerView(userProfile);
                            setDataOnProfessionRecylerView(userProfile);
                            setDataOnrFamilyMemberRecylerView(userProfile);
                            setDataOnOtherInfoRecylerView(userProfile);


                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                        }
                    });

                    image = new int[] { R.drawable.accept_icon, R.drawable.accept_icon,
                            R.drawable.accept_icon};

                    viewPager = (ViewPager) findViewById(R.id.pager);
                    if(userProfile.getProfile().getPersonalInformation().getImage()!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userProfile.getProfile().getPersonalInformation().getImage().getOther().size()
                                        > 1) {
                                    userProfileImage.setVisibility(View.INVISIBLE);
                                    adapter = new ViewPagerAdapter(OwnUserProfileActivity.this,
                                            userProfile.getProfile().getPersonalInformation()
                                                    .getImage()
                                                    .getOther());
                                    viewPager.setAdapter(adapter);

                                } else {
                                    userProfileImage.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    //Log.i("errormsg", e.getMessage().toString() + " " + res);
                    e.printStackTrace();
//                    application.trackEception(e, "fetchUserProfileInfo", "OwnUserProfileActivity", e.getMessage().toString(), mTracker);
                }




            }


        }).start();



    }



    private void setUserOwnImage(UserProfile userProfile) {
        //Log.i("ownprofileimage", userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture().toString());

        if (userProfile.getProfile().getPersonalInformation().getImage() != null) {
//            Log.i("ownprofileimage", Utils.Base_URL + userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture());

            Picasso.with(OwnUserProfileActivity.this)
                    .load(Utils.Base_URL + userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture())
                    .into(userProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            userProfileImage.post(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.scaleImage(OwnUserProfileActivity.this, 2f, userProfileImage);
                                }
                            });
                        }

                        @Override
                        public void onError() {
                        }
                    });


        } else {

            if (!sharePref.get_data("profile_picture").equals("key")) {
                Glide.with(OwnUserProfileActivity.this)
                        .load(Utils.Base_URL + sharePref.get_data("profile_picture"))
                        .into(userProfileImage);
            } else {
                if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                        (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))) {
                    userProfileImage.setImageResource(R.drawable.profile_icon_male);
                    userProfileImage.setImageResource(R.drawable.profile_icon_male);
                } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                        (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))) {
                    userProfileImage.setImageResource(R.drawable.profile_icon_female);
                    userProfileImage.setImageResource(R.drawable.profile_icon_female);
                } else {
//                    Log.i("image", "nothing found");
                }
            }
        }


    }

    private void setDataOnOtherInfoRecylerView(UserProfile userProfile) {

         /*..................................other inormation block start.........................................*/


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()).equals(""))) {

            otherInfoChildItemList.add(new UserProfileChild(getResources().getString(R.string.fast_text),
                    userProfile.getProfile().getOtherInformation().getFasting()));

        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()).equals(""))) {

            otherInfoChildItemList.add(new UserProfileChild(getResources().getString(R.string.prayet_text),
                    userProfile.getProfile().getOtherInformation().getPrayer()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getJobAfterMarriage()).equals("")))) {


            otherInfoChildItemList.add(new UserProfileChild(getResources().getString(R.string.after_marrige_job_text),
                    userProfile.getProfile().getOtherInformation().getJobAfterMarriage()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


            otherInfoChildItemList.add(new UserProfileChild(getResources().getString(R.string.hijab_text),
                    userProfile.getProfile().getOtherInformation().getHijab()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getOwnHouse()).equals("")))) {


            otherInfoChildItemList.add(new UserProfileChild(getResources().getString(R.string.own_house_text),
                    userProfile.getProfile().getOtherInformation().getOwnHouse()));

        }


        if (otherInfoChildItemList.size() > 0) {
            otherInfoChildItemHeader.add(new UserProfileParent(
                    getResources().getString(R.string.other_question_ans_text)
                    , otherInfoChildItemList));

            otherInformationRecyclerView.scrollToPosition(otherInfoChildItemList.size()-1);
            otherInformationRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                    otherInfoChildItemHeader,
                    true));
        } else {
            otherInfoCardview.setVisibility(View.GONE);
        }

            /*..................................other inormation block end.........................................*/

    }

    private void setDataOnProfessionRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getProfession() != null) {

            if (!(checkNullField(userProfile.getProfile().getProfession().getProfessionalGroup())).equals("")) {

                professionChildItemList.add(new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.professional_group_text)),
                        userProfile.getProfile().getProfession().getProfessionalGroup()));

            }


            if (!(checkNullField(userProfile.getProfile().getProfession().getOccupation())).equals("")) {

                professionChildItemList.add(new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.occupation_text)),
                        userProfile.getProfile().getProfession().getOccupation()));

            }


            if (!(checkNullField(userProfile.getProfile().getProfession().getDesignation())).equals("")) {

                professionChildItemList.add(new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.designation_text)),
                        userProfile.getProfile().getProfession().getDesignation()));

            }


            if (!(checkNullField(userProfile.getProfile().getProfession().getInstitute())).equals("")) {

                professionChildItemList.add(new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.institute_text)),
                        userProfile.getProfile().getProfession().getInstitute()));

            }


            if (professionChildItemList.size() > 0) {

                professionChildItemHeader.add(new UserProfileParent(
                        getResources().getString(R.string.profession_text), professionChildItemList));
                professionRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        professionChildItemHeader,
                        true));

            }
        }


    }

    private void setDataOnEducationalRecylerView(UserProfile userProfile) {

        String education = "";

        if (userProfile.getProfile().getEducationInformation().size() > 0) {


            for (int i = 0; i < userProfile.getProfile().getEducationInformation().size(); i++) {

                EducationInformation educationInformation = userProfile.getProfile().
                        getEducationInformation().get(i);


                if (educationInformation.getPassingYear() != null) {


                    education = education + checkNullField(educationInformation.getName()) +
                            checkNullField(educationInformation.getInstitution()) +
                            checkNullField(educationInformation.getSubject()) +
                            Utils.convertEnglishYearDigittoBangla(
                                    educationInformation.getPassingYear()) + " : ";


                } else {
                    education = education + checkNullField(educationInformation.getName()) +
                            checkNullField(educationInformation.getInstitution()) +
                            checkNullField(educationInformation.getSubject()) + " : ";


                }

            }


            educationalInfoChildItemList.add(new UserProfileChild("শিক্ষা", education));


            if (educationalInfoChildItemList.size() > 0) {

                educationalInfoChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.education_male), educationalInfoChildItemList));
                educationRecylerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        educationalInfoChildItemHeader,
                        true));

            }


        }


    }

    private void setDataOnPersonalInfoRecylerView(UserProfile userProfile) {


//        personalInfoRecylerView.removeAllViews();
        //add personal Information
        if(personalInfoChildItemList.size() > 0 && personalInfoChildItemList.get(0).getTitle().equalsIgnoreCase(getResources().getString(R.string.age))) {
            personalInfoChildItemList.set(0,
                    new UserProfileChild(getResources().getString(R.string.age),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getAge()) +
                                    " " + getResources().getString(R.string.year) + ","

                    ));
        }else{
            personalInfoChildItemList.add(0,
                    new UserProfileChild(getResources().getString(R.string.age),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getAge()) +
                                    " " + getResources().getString(R.string.year) + ","

                    ));
        }

        if(personalInfoChildItemList.size() > 1 && personalInfoChildItemList.get(1).getTitle().equalsIgnoreCase(getResources().getString(R.string.height))) {
            personalInfoChildItemList.set(1,
                    new UserProfileChild(getResources().getString(R.string.height),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getHeightFt())
                                    + "'" +
                                    Utils.convertEnglishDigittoBangla(
                                            userProfile.getProfile().getPersonalInformation().getHeightInc())
                                    + "\""
                    ));
        }else{
            personalInfoChildItemList.add(1,
                    new UserProfileChild(getResources().getString(R.string.height),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getHeightFt())
                                    + "'" +
                                    Utils.convertEnglishDigittoBangla(
                                            userProfile.getProfile().getPersonalInformation().getHeightInc())
                                    + "\""
                    ));
        }

        if(personalInfoChildItemList.size() > 2 && personalInfoChildItemList.get(2).getTitle().equalsIgnoreCase(getResources().getString(R.string.religion_text))) {
            personalInfoChildItemList.set(2,
                    new UserProfileChild(getResources().getString(R.string.religion_text),
                            userProfile.getProfile().getProfileReligion().getReligion()
                    ));
        }else {
            personalInfoChildItemList.add(2,
                    new UserProfileChild(getResources().getString(R.string.religion_text),
                            userProfile.getProfile().getProfileReligion().getReligion()
                    ));
        }

        if( userProfile.getProfile().getProfileReligion().getCast()!=null) {
            if(personalInfoChildItemList.size() > 3 && personalInfoChildItemList.get(3).getTitle().equalsIgnoreCase(getResources().getString(R.string.cast_text))) {
                personalInfoChildItemList.set(3,
                        new UserProfileChild(getResources().getString(R.string.cast_text),
                                userProfile.getProfile().getProfileReligion().getCast()
                        ));
            }else{
                personalInfoChildItemList.add(3,
                        new UserProfileChild(getResources().getString(R.string.cast_text),
                                userProfile.getProfile().getProfileReligion().getCast()
                        ));
            }
        }

        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getCountry())).equals("")) {
            personalInfoChildItemList.add(4, new UserProfileChild(getResources().getString(R.string.present_loaction_text),
                    userProfile.getProfile().getProfileLivingIn().getCountry()
            ));
        }


        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())).equals("")) {

            personalInfoChildItemList.add(5, new UserProfileChild(getResources().getString(R.string.home_town),
                    userProfile.getProfile().getProfileLivingIn().getLocation()
            ));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor())).equals("")) {
            if(personalInfoChildItemList.size() > 6 && personalInfoChildItemList.get(6).getTitle().equalsIgnoreCase(getResources().getString(R.string.skin_color))) {

                personalInfoChildItemList.set(6,
                        new UserProfileChild(getResources().getString(R.string.skin_color),
                                userProfile.getProfile().getPersonalInformation().getSkinColor()
                        ));
            }else{
                personalInfoChildItemList.add(6,
                        new UserProfileChild(getResources().getString(R.string.skin_color),
                                userProfile.getProfile().getPersonalInformation().getSkinColor()
                        ));
            }

        }

        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals("")) {
            if(personalInfoChildItemList.size() > 7 && personalInfoChildItemList.get(7).getTitle().equalsIgnoreCase(getResources().getString(R.string.body))) {
                personalInfoChildItemList.set(7,
                    new UserProfileChild(getResources().getString(R.string.body),
                            userProfile.getProfile().getPersonalInformation().getWeight()
                    ));
            }else{
                personalInfoChildItemList.add(7,
                    new UserProfileChild(getResources().getString(R.string.body),
                            userProfile.getProfile().getPersonalInformation().getWeight()
                    ));
            }

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()))
                .equals("")) {
            if(personalInfoChildItemList.size() > 8 && personalInfoChildItemList.get(8).getTitle().equalsIgnoreCase(getResources().getString(R.string.marital_status))) {
                personalInfoChildItemList.set(8,
                    new UserProfileChild(getResources().getString(R.string.marital_status),
                            userProfile.getProfile().getPersonalInformation().getMaritalStatus()
                    ));
            }else{
                personalInfoChildItemList.add(8,
                        new UserProfileChild(getResources().getString(R.string.marital_status),
                                userProfile.getProfile().getPersonalInformation().getMaritalStatus()
                        ));
            }

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getBloodGroup()))
                .equals("")) {
            if(personalInfoChildItemList.size() > 9 && personalInfoChildItemList.get(9).getTitle().equalsIgnoreCase(getResources().getString(R.string.blood_group_text))) {
                personalInfoChildItemList.set(9,
                    new UserProfileChild(getResources().getString(R.string.blood_group_text),
                            userProfile.getProfile().getPersonalInformation().getBloodGroup()
                    ));
            }else{
                personalInfoChildItemList.add(9,
                        new UserProfileChild(getResources().getString(R.string.blood_group_text),
                                userProfile.getProfile().getPersonalInformation().getBloodGroup()
                        ));
            }

        }

        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getDisabilities()))
                .equals("")) {


            personalInfoChildItemList.add(10, new UserProfileChild(getResources().getString(R.string.disabilities_text),

                    userProfile.getProfile().getPersonalInformation().getDisabilities() + ", " +
                            checkNullField(userProfile.getProfile().getPersonalInformation().getDisabilitiesDescription())
            ));
        }

        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER)) &&
                (!(checkNullField(userProfile.getProfile().getPersonalInformation().getSmoking()))
                        .equals(""))
                ) {
            if(personalInfoChildItemList.size() > 11 && personalInfoChildItemList.get(11).getTitle().equalsIgnoreCase(getResources().getString(R.string.smoking_text))) {
                personalInfoChildItemList.set(11,
                        new UserProfileChild(getResources().getString(R.string.smoking_text),
                                userProfile.getProfile().getPersonalInformation().getSmoking()));
            }else{
                personalInfoChildItemList.add(11,
                        new UserProfileChild(getResources().getString(R.string.smoking_text),
                                userProfile.getProfile().getPersonalInformation().getSmoking()));
            }

        }

        //add personal Child Item list to parent list
        if (personalInfoChildItemList.size() > 0) {
            if(personalInfoChildItemHeader.size() ==0)
                personalInfoChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.personal_info), personalInfoChildItemList));

            personalInfoRecylerView.removeAllViews();
            upea = new UserProfileExpenadlbeAdapter(this,
                    personalInfoChildItemHeader,
                    true);
            personalInfoRecylerView.setAdapter(upea);
            upea.expandParent(0);
//            upea.notifyDataSetChanged();
        }


    }

    private void setDataOnrFamilyMemberRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getFamilyMembers() != null) {

        /*..................................parents block start.........................................*/
            //add father information
            if (userProfile.getProfile().getFamilyMembers().getFather() != null) {

                parentChildItemList.add(new UserProfileChild(getResources().getString(R.string.father_text),
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
            if (userProfile.getProfile().getFamilyMembers().getMother() != null) {

                parentChildItemList.add(new UserProfileChild(getResources().getString(R.string.mother_text),
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


            //add parentchildlist data to mainparentlist
            if (parentChildItemList.size() > 0) {
                parentChildItemHeader.add(new UserProfileParent(
                        getResources().getString(R.string.father_text) + "-" +
                                getResources().getString(R.string.mother_text),
                        parentChildItemList));

                parentsRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        parentChildItemHeader,
                        true));

            }

             /*..................................parents block end.........................................*/



             /*..................................brothers block start.........................................*/

            //add brother information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfBrothers() > 0) {

                // familyMemberCounter = 0;
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++) {
                    //   familyMemberCounter = i + 1;

                    brothersChildItemList.add(new UserProfileChild(
                            //getResources().getString(R.string.brother_text) + Utils.convertEnglishDigittoBangla(familyMemberCounter),
                            getResources().getString(R.string.brother_text),
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

            //add brotherchildlist data to mainparentlist
            if (brothersChildItemList.size() > 0) {
                brothersChildItemHeader.add(new UserProfileParent(
                        getResources().getString(R.string.brother_text) + "("
                                + Utils.convertEnglishDigittoBangla(brothersChildItemList.size()) + ")"
                        , brothersChildItemList));

                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        brothersChildItemHeader,
                        true));

            } else {

                brothersChildItemList.add(new UserProfileChild(getResources().getString(R.string.brother_text)
                        , getResources().getString(R.string.no_brother_text)));
                brothersChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.brother_text)
                        , brothersChildItemList));
                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        brothersChildItemHeader,
                        true));

            }

            /*..................................brothers block end.........................................*/


            /*..................................sisters block start.........................................*/

            if (userProfile.getProfile().getFamilyMembers().getNumberOfSisters() > 0) {

                //familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getSisters().size(); i++) {

                    //familyMemberCounter = i + 1;
                    sistersChildItemList.add(new UserProfileChild(
                            /*getResources().getString(R.string.sister_text)
                                    + Utils.convertEnglishDigittoBangla(familyMemberCounter),*/

                            getResources().getString(R.string.sister_text),
                            checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getSisters().get(i).getDesignation())
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

            //add sisterchildlist data to mainparentlist
            if (sistersChildItemList.size() > 0) {
                sistersChildItemHeader.add(new UserProfileParent(
                        getResources().getString(R.string.sister_text) + "("
                                + Utils.convertEnglishDigittoBangla(sistersChildItemList.size()) + ")"
                        , sistersChildItemList));

                sisterRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        sistersChildItemHeader,
                        true));

            } else {

                sistersChildItemList.add(new UserProfileChild(
                        getResources().getString(R.string.sister_text),
                        getResources().getString(R.string.no_sister_text)));
                sistersChildItemHeader.add(new UserProfileParent(
                        getResources().getString(R.string.sister_text), sistersChildItemList));
                sisterRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        sistersChildItemHeader,
                        true));

            }
            /*..................................sisters block end.........................................*/


            /*..................................childs block start.........................................*/


            //add child information
            if (userProfile.getProfile().getPersonalInformation().getMaritalStatus().
                    equals(getResources().getString(R.string.unmarried_text))) {

                childCardView.setVisibility(View.GONE);

            } else if (userProfile.getProfile().getPersonalInformation().getMaritalStatus().
                    equals(getResources().getString(R.string.married_text))) {

                if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                        userProfile.getProfile().getFamilyMembers().isChildLivesWithYou()) {

                    childsChildItemList.add(new UserProfileChild(getResources().getString(R.string.child_text),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getFamilyMembers().getNumberOfChild())
                                    + " জন সন্তান, সাথে থাকে"));

                } else if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                        !(userProfile.getProfile().getFamilyMembers().isChildLivesWithYou())) {

                    childsChildItemList.add(new UserProfileChild(getResources().getString(R.string.child_text),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getFamilyMembers().getNumberOfChild())
                                    + " জন সন্তান, সাথে থাকে না"));
                } else {
//                    Log.i("childstatus", "nothing found");
                }


                //add childlist data to mainparentlist
                if (childsChildItemList.size() > 0) {

                    childsChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.child_text)
                            , childsChildItemList));

                    childRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                            childsChildItemHeader,
                            true));
                } else {
                    childsChildItemList.add(new UserProfileChild(getResources().getString(R.string.child_text), getResources().getString(R.string.no_child_text)));
                    childsChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.child_text), childsChildItemList));

                    childRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                            childsChildItemHeader,
                            true));

                }


            } else {
                childCardView.setVisibility(View.GONE);
            }




            /*..................................childs block end.........................................*/




            /*..................................other relative block start.........................................*/


            //add dada information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfDada() > 0) {


                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getDadas().size(); i++) {

                    otherRelativeChildItemList.add(new UserProfileChild(
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

                //   familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKakas().size(); i++) {

                    //  familyMemberCounter = i + 1;
                    otherRelativeChildItemList.add(new UserProfileChild(
                            //"চাচা " + Utils.convertEnglishDigittoBangla(i + 1),
                            "চাচা ",
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

                // familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getMamas().size(); i++) {

                    // familyMemberCounter = i + 1;
                    otherRelativeChildItemList.add(new UserProfileChild(
                            //  "মামা " + Utils.convertEnglishDigittoBangla(familyMemberCounter),
                            "মামা ",
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

                //familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getFufas().size(); i++) {

                    //  familyMemberCounter = i + 1;
                    otherRelativeChildItemList.add(new UserProfileChild(
                            //"ফুপা " + Utils.convertEnglishDigittoBangla(familyMemberCounter),
                            "ফুপা ",
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

                    otherRelativeChildItemList.add(new UserProfileChild(
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

                // familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKhalus().size(); i++) {

                    //   familyMemberCounter = i + 1;
                    otherRelativeChildItemList.add(new UserProfileChild(
                            //        "খালু " + Utils.convertEnglishDigittoBangla(familyMemberCounter),
                            "খালু ",

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


            if (otherRelativeChildItemList.size() > 0) {

                otherRelativeChildItemHeader.add(new UserProfileParent(
                        "অন্যান্য(" + Utils.convertEnglishDigittoBangla(otherRelativeChildItemList.size()) + ")"
                        , otherRelativeChildItemList));

                otherRelativeInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        otherRelativeChildItemHeader,
                        true));
            } else {
                otherRelativeCardview.setVisibility(View.GONE);
            }


            /*..................................other relative block end.........................................*/


        }


    }

    private void initView() {
        sliderDotsPanel = (LinearLayout) findViewById(R.id.sliderDots);


       /* educationCardView = (CardView) findViewById(R.id.education_cardview);
        professionCardview = (CardView) findViewById(R.id.profession_cardview);
        brotherCardview = (CardView) findViewById(R.id.brother_cardview);
        sisterCardview = (CardView) findViewById(R.id.sister_cardview);*/
        otherRelativeCardview = (CardView) findViewById(R.id.other_relative_cardview);
        childCardView = (CardView) findViewById(R.id.child_cardView);
        otherInfoCardview = (CardView) findViewById(R.id.other_info_cardview);


        progressDialog = new ProgressDialog(OwnUserProfileActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(true);

        coordnatelayout = (CoordinatorLayout) findViewById(R.id.coordnatelayout);


        userProfileDescriptionText = (TextView) findViewById(R.id.userProfileDescriptionText);

        userProfileImage = (ImageView) findViewById(R.id.user_profile_image);
        userNameTextView = (TextView) findViewById(R.id.user_name);

        personalInfoRecylerView = (RecyclerView) findViewById(R.id.user_general_info_recycler_view);
        personalInfoRecylerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        educationRecylerView = (RecyclerView) findViewById(R.id.education_recylerView);
        educationRecylerView.setLayoutManager(new LinearLayoutManager(this));

        professionRecyclerView = (RecyclerView) findViewById(R.id.profession_recylerView);
        professionRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        parentsRecyclerView = (RecyclerView) findViewById(R.id.parents_recylerView);
        parentsRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        brotherRecyclerView = (RecyclerView) findViewById(R.id.brother_recylerView);
        brotherRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        sisterRecyclerView = (RecyclerView) findViewById(R.id.sister_recylerView);
        sisterRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        childRecyclerView = (RecyclerView) findViewById(R.id.child_recylerView);
        childRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        otherRelativeInfoRecyclerView = (RecyclerView) findViewById(R.id.other_relative_recylerView);
        otherRelativeInfoRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        otherInformationRecyclerView = (RecyclerView) findViewById(R.id.other_info_recylerView);
        otherInformationRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));
        otherInformationRecyclerView.getParent().requestChildFocus(otherInformationRecyclerView,otherInformationRecyclerView);
    }

    public void backBtnAction(View v) {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == 2) {

                if (data != null && !data.getStringExtra("DATE_OF_BIRTH").equalsIgnoreCase(
                        "reject")) {
//                dateOfBirthEditext.setText(data.getStringExtra("DATE_OF_BIRTH"));
                    Log.d("age: ", data.getStringExtra("DATE_OF_BIRTH"));
                    String age = data.getStringExtra("DATE_OF_BIRTH");
                    String[] age1 = age.split("/");
                    String convertedEnglishDateFromBanglaDate = Integer.parseInt(
                            CalenderBanglaInfo.getDigitEnglishFromBangla(age1[0])) + "";

                    String convertedEnglishMonthFromBanglaMonth = Integer.parseInt(
                            CalenderBanglaInfo.getDigitEnglishFromBangla(age1[1])) + "";

                    String convertedEglishYearFromBanglaYear = Integer.parseInt(
                            CalenderBanglaInfo.getDigitEnglishFromBangla(age1[2])) + "";

                    userProfile.getProfile().getPersonalInformation().setAge(
                            Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(
                                    convertedEglishYearFromBanglaYear));
                    setDataOnPersonalInfoRecylerView(userProfile);
                }
            } else if (requestCode == 3) {
                if (data != null && data.hasExtra("height")) {
                    if (data.getIntExtra("height", 0) > 0) {
                        int height = data.getIntExtra("height", 0);
                        int feetValue = ((height - 1) / 12 + 4);
                        int inchValue = ((height - 1) % 12);
                        ArrayList<String> heightArray = new ArrayList<String>();
                        String[] heightName = new String[heightArray.size()];

                        for (int i = 4; i < 8; i++) {           //height from 4ft to 7ft 11inch
                            for (int j = 0; j < 12; j++)
                                heightArray.add(Utils.convertEnglishDigittoBangla(i) + " ফিট "
                                        + Utils.convertEnglishDigittoBangla(j) + " ইঞ্চি");
                        }

                        heightName = heightArray.toArray(heightName);

                        userProfile.getProfile().getPersonalInformation().setHeightFt(feetValue);
                        userProfile.getProfile().getPersonalInformation().setHeightInc(inchValue);
                        setDataOnPersonalInfoRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 4) {
                String religionValue = sharePref.get_data("religion") + "";
                String castValue = sharePref.get_data("cast") + "";
                String otherReligion = sharePref.get_data("other_religion") + "";
                String otherCast = sharePref.get_data("other_cast") + "";
                if (otherReligion.length() > 0) {
                    userProfile.getProfile().getProfileReligion().setReligion((otherReligion));
                } else {
                    userProfile.getProfile().getProfileReligion().setReligion(
                            data.getStringExtra("religion"));
                }
                if (otherCast.length() > 0) {
                    userProfile.getProfile().getProfileReligion().setCast(otherCast);
                } else {
                    userProfile.getProfile().getProfileReligion().setCast(
                            data.getStringExtra("cast"));
                }

                setDataOnPersonalInfoRecylerView(userProfile);
            }else if (requestCode == 5) {
                userProfile.getProfile().getPersonalInformation().setSkinColor(
                            data.getStringExtra("skin_color_data"));

                setDataOnPersonalInfoRecylerView(userProfile);
            }else if (requestCode == 6) {
                if (data != null && data.hasExtra("body_value")) {
                    if (data.getIntExtra("body_value", 0) > 0) {
                        int weight = data.getIntExtra("body_value", 0);
                        userProfile.getProfile().getPersonalInformation().setWeight(Utils.convertEnglishDigittoBangla(weight + 29) + " কেজি");
                        setDataOnPersonalInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 7) {
                if (data != null && data.hasExtra("marital_status_data")) {
                    if (data.hasExtra("marital_status_data")) {
                        userProfile.getProfile().getPersonalInformation().setMaritalStatus(data.getStringExtra("marital_status_data"));
                        setDataOnPersonalInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 8) {
                if (data != null && data.hasExtra("blood_group_data")) {
                    if (data.hasExtra("blood_group_data")) {
                        userProfile.getProfile().getPersonalInformation().setBloodGroup(data.getStringExtra("blood_group_data"));
                        setDataOnPersonalInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 9) {
                if (data != null && data.hasExtra("smoke_data")) {
                    if (data.hasExtra("smoke_data")) {
                        userProfile.getProfile().getPersonalInformation().setSmoking(data.getStringExtra("smoke_data"));
                        setDataOnPersonalInfoRecylerView(userProfile);
                    }
                }
            }
        }
    }
}
