package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.nascenia.biyeta.model.newuserprofile.Brother;
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
    private ImageView img_edit_details;

    private UserProfile userProfile;

    // private int familyMemberCounter;

    private ImageView userProfileImage;

    private EditText userProfileDescriptionText;


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
    private UserProfileExpenadlbeAdapter upea, upga, upoa, upedua, uppa, upb, ups;


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

//        img_edit_details.setTag("");
//        img_edit_details.setImageResource(R.drawable.editicon);
//        userProfileDescriptionText.setEnabled(false);
//        hideSoftKeyboard(userProfileDescriptionText);
//        userProfileDescriptionText.clearFocus();
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
                            setDataOnFamilyMemberRecylerView(userProfile);
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


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()).equals("")) &&
        (otherInfoChildItemList.size() > 0 && otherInfoChildItemList.get(0).getTitle().equalsIgnoreCase(getResources().getString(R.string.fast_text)))){

            otherInfoChildItemList.set(0, new UserProfileChild(getResources().getString(R.string.fast_text),
                    userProfile.getProfile().getOtherInformation().getFasting()));

        }else{
            otherInfoChildItemList.add(0, new UserProfileChild(getResources().getString(R.string.fast_text),
                    userProfile.getProfile().getOtherInformation().getFasting()));

        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()).equals(""))&&
                (otherInfoChildItemList.size() > 1 && otherInfoChildItemList.get(1).getTitle().equalsIgnoreCase(getResources().getString(R.string.prayet_text)))) {

            otherInfoChildItemList.set(1, new UserProfileChild(getResources().getString(R.string.prayet_text),
                    userProfile.getProfile().getOtherInformation().getPrayer()));

        }else{
            otherInfoChildItemList.add(1, new UserProfileChild(getResources().getString(R.string.prayet_text),
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
                && (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getOwnHouse()).equals("")))&&
                (otherInfoChildItemList.size() > 2 && otherInfoChildItemList.get(2).getTitle().equalsIgnoreCase(getResources().getString(R.string.own_house_text)))) {

            otherInfoChildItemList.set(2, new UserProfileChild(getResources().getString(R.string.own_house_text),
                    userProfile.getProfile().getProfileLivingIn().getOwnHouse()));

        }else{
            otherInfoChildItemList.add(2, new UserProfileChild(getResources().getString(R.string.own_house_text),
                    userProfile.getProfile().getProfileLivingIn().getOwnHouse()));

        }


//        if (otherInfoChildItemList.size() > 0) {
//            otherInfoChildItemHeader.add(new UserProfileParent(
//                    getResources().getString(R.string.other_question_ans_text)
//                    , otherInfoChildItemList));
//
//            otherInformationRecyclerView.scrollToPosition(otherInfoChildItemList.size()-1);
//            otherInformationRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
//                    otherInfoChildItemHeader,
//                    true));
//        } else {
//            otherInfoCardview.setVisibility(View.GONE);
//        }

        if (otherInfoChildItemList.size() > 0) {
            if(otherInfoChildItemHeader.size() ==0)
                otherInfoChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.other_question_ans_text), otherInfoChildItemList));

            otherInformationRecyclerView.removeAllViews();
            upoa = new UserProfileExpenadlbeAdapter(this,
                    otherInfoChildItemHeader,
                    true);
            otherInformationRecyclerView.setAdapter(upoa);
            upoa.expandParent(0);
//            upea.notifyDataSetChanged();
        }

            /*..................................other inormation block end.........................................*/

    }

    private void setDataOnProfessionRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getProfession() != null) {

            if(professionChildItemList.size() > 0 && professionChildItemList.get(0).getTitle().equalsIgnoreCase(Utils.setBanglaProfileTitle(getResources().getString(R.string.professional_group_text)))){
                professionChildItemList.set(0, new UserProfileChild(Utils.setBanglaProfileTitle(
                    getResources().getString(R.string.professional_group_text)),
                    userProfile.getProfile().getProfession().getProfessionalGroup()));
            }else{
                professionChildItemList.add(0, new UserProfileChild(Utils.setBanglaProfileTitle(
                    getResources().getString(R.string.professional_group_text)),
                    userProfile.getProfile().getProfession().getProfessionalGroup()));
            }

            if(professionChildItemList.size() > 1 && professionChildItemList.get(1).getTitle().equalsIgnoreCase(getResources().getString(R.string.profession_text))) {
                professionChildItemList.set(1, new UserProfileChild(Utils.setBanglaProfileTitle(
                    getResources().getString(R.string.occupation_text)),
                    userProfile.getProfile().getProfession().getOccupation()));
            }else{
                professionChildItemList.add(1, new UserProfileChild(getResources().getString(R.string.profession_text),
                    userProfile.getProfile().getProfession().getOccupation()));
            }


            if(professionChildItemList.size() > 2 && professionChildItemList.get(2).getTitle().equalsIgnoreCase(Utils.setBanglaProfileTitle(getResources().getString(R.string.designation_text)))){
                professionChildItemList.set(2,new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.designation_text)),
                        userProfile.getProfile().getProfession().getDesignation()));
            }else{
                professionChildItemList.add(2,new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.designation_text)),
                        userProfile.getProfile().getProfession().getDesignation()));
            }


            if(professionChildItemList.size() > 3 && professionChildItemList.get(3).getTitle().equalsIgnoreCase(Utils.setBanglaProfileTitle(getResources().getString(R.string.institute_text)))){
                professionChildItemList.set(3, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.institute_text)),
                        userProfile.getProfile().getProfession().getInstitute()));
            }else{
                professionChildItemList.add(3, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.institute_text)),
                        userProfile.getProfile().getProfession().getInstitute()));
            }


            if (professionChildItemList.size() > 0) {

                if(professionChildItemHeader.size() ==0){
                    professionChildItemHeader.add(new UserProfileParent(
                            getResources().getString(R.string.profession_text),
                            professionChildItemList));
                    upga = new UserProfileExpenadlbeAdapter(this,
                            professionChildItemHeader,
                            true);
                    professionRecyclerView.setAdapter(upga);
                }else{
                    personalInfoRecylerView.removeAllViews();
                    upga = new UserProfileExpenadlbeAdapter(this,
                            professionChildItemHeader,
                            true);
                    professionRecyclerView.setAdapter(upga);
                    upga.notifyDataSetChanged();
                    upga.expandParent(0);
                }
            }
            //add personal Child Item list to parent list
//            if (personalInfoChildItemList.size() > 0) {
//                if(personalInfoChildItemHeader.size() ==0)
//                    personalInfoChildItemHeader.add(new UserProfileParent(getResources().getString(R.string.personal_info), personalInfoChildItemList));
//
//                personalInfoRecylerView.removeAllViews();
//                upea = new UserProfileExpenadlbeAdapter(this,
//                        personalInfoChildItemHeader,
//                        true);
//                personalInfoRecylerView.setAdapter(upea);
//                upea.expandParent(0);
////            upea.notifyDataSetChanged();
//            }
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

            if(educationalInfoChildItemList.size()> 0) {
                educationalInfoChildItemList.set(0, new UserProfileChild("শিক্ষা", education));
            }else{
                educationalInfoChildItemList.add(0, new UserProfileChild("শিক্ষা", education));
            }

            if (educationalInfoChildItemList.size() > 0) {
                educationRecylerView.removeAllViews();
                if(educationalInfoChildItemHeader.size() > 0) {
                    educationalInfoChildItemHeader.set(0,
                            new UserProfileParent(getResources().getString(R.string.education_male),
                                    educationalInfoChildItemList));
                    upedua = new UserProfileExpenadlbeAdapter(this,
                            educationalInfoChildItemHeader,
                            true);
                    upedua.expandParent(0);
                }else {
                    educationalInfoChildItemHeader.add(0,
                            new UserProfileParent(getResources().getString(R.string.education_male),
                                    educationalInfoChildItemList));
                    upedua = new UserProfileExpenadlbeAdapter(this,
                            educationalInfoChildItemHeader,
                            true);
                }
                educationRecylerView.setAdapter(upedua);

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

            if(personalInfoChildItemList.size() > 10 && personalInfoChildItemList.get(10).getTitle().equalsIgnoreCase(getResources().getString(R.string.disabilities_text))) {

                personalInfoChildItemList.set(10,
                        new UserProfileChild(getResources().getString(R.string.disabilities_text),

                                userProfile.getProfile().getPersonalInformation().getDisabilities()
                                        + ", " +
                                        checkNullField(
                                                userProfile.getProfile().getPersonalInformation().getDisabilitiesDescription())
                        ));
            }else {
                personalInfoChildItemList.add(10,
                        new UserProfileChild(getResources().getString(R.string.disabilities_text),

                                userProfile.getProfile().getPersonalInformation().getDisabilities()
                                        + ", " +
                                        checkNullField(
                                                userProfile.getProfile().getPersonalInformation().getDisabilitiesDescription())

                        ));
            }
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

    private void setDataOnFamilyMemberRecylerView(UserProfile userProfile) {

        if (userProfile.getProfile().getFamilyMembers() != null) {
        /*..................................parents block start.........................................*/
            //add father information
            if (userProfile.getProfile().getFamilyMembers().getFather() != null) {

                if(parentChildItemList.size()>0) {
                    parentChildItemList.set(0,
                            new UserProfileChild(getResources().getString(R.string.father_text),
                                    checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getFather()
                                                    .getName())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getFather().getOccupation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getFather().getDesignation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getFather().getInstitute())
                            ));
                }else{
                    parentChildItemList.add(0,
                            new UserProfileChild(getResources().getString(R.string.father_text),
                                    checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getFather()
                                                    .getName())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getFather().getOccupation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getFather().getDesignation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getFather().getInstitute())
                            ));
                }

            }


            //add mother information
            if (userProfile.getProfile().getFamilyMembers().getMother() != null) {
                if(parentChildItemList.size()>1) {
                    parentChildItemList.set(1,
                            new UserProfileChild(getResources().getString(R.string.mother_text),
                                    checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getName())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getOccupation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getDesignation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getInstitute())
                            ));
                }else{
                    parentChildItemList.add(1,
                            new UserProfileChild(getResources().getString(R.string.mother_text),
                                    checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getName())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getOccupation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getDesignation())
                                            + checkNullField(
                                            userProfile.getProfile().getFamilyMembers().getMother()
                                                    .getInstitute())
                            ));
                }

            }


            //add parentchildlist data to mainparentlist
            if (parentChildItemList.size() > 0) {
                if(parentChildItemHeader.size()>0) {
                    parentChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.father_text) + "-" +
                                    getResources().getString(R.string.mother_text),
                            parentChildItemList));

                    uppa = new UserProfileExpenadlbeAdapter(this,
                            parentChildItemHeader,
                            true);
                    uppa.expandParent(0);
                }else{
                    parentChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.father_text) + "-" +
                                    getResources().getString(R.string.mother_text),
                            parentChildItemList));
                    uppa = new UserProfileExpenadlbeAdapter(this,
                            parentChildItemHeader,
                            true);
                }
                parentsRecyclerView.removeAllViews();
                parentsRecyclerView.setAdapter(uppa);

            }

             /*..................................parents block end.........................................*/



             /*..................................brothers block start.........................................*/

            //add brother information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfBrothers() > 0) {

                // familyMemberCounter = 0;
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++) {
                    UserProfileChild upc = new UserProfileChild(
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

                    );
                    upc.setId(userProfile.getProfile().getFamilyMembers().getBrothers().
                            get(i).getId());
//                    if(brothersChildItemList.contains(upc)){
                    int index = getBroIndex(upc.getId());
                    if(index >=0 && brothersChildItemList.size() > 0){
                        brothersChildItemList.set(index, upc);
                    }else {
                        brothersChildItemList.add(i, upc);
                    }
                }


            }

            //add brotherchildlist data to mainparentlist
            if (brothersChildItemList.size() > 0) {
//                brothersChildItemHeader.add(new UserProfileParent(
//                        getResources().getString(R.string.brother_text) + "("
//                                + Utils.convertEnglishDigittoBangla(brothersChildItemList.size()) + ")"
//                        , brothersChildItemList));
//
//                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
//                        brothersChildItemHeader,
//                        true));
                if(brothersChildItemHeader.size()>0) {
                    brothersChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.brother_text) + "("
                                    + Utils.convertEnglishDigittoBangla(brothersChildItemList.size()) + ")",
                            brothersChildItemList));

                    upb = new UserProfileExpenadlbeAdapter(this,
                            brothersChildItemHeader,
                            true);
                    upb.expandParent(0);
                }else{
                    brothersChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.brother_text) + "("
                                + Utils.convertEnglishDigittoBangla(brothersChildItemList.size()) + ")",
                            brothersChildItemList));
                    upb = new UserProfileExpenadlbeAdapter(this,
                            brothersChildItemHeader,
                            true);
                }
                brotherRecyclerView.removeAllViews();
                brotherRecyclerView.setAdapter(upb);

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
                    UserProfileChild upc = (new UserProfileChild(
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
                    upc.setId(userProfile.getProfile().getFamilyMembers().getSisters().
                            get(i).getId());
//                    if(brothersChildItemList.contains(upc)){
                    int index = getSisIndex(upc.getId());
                    if(index >=0 && sistersChildItemList.size() > 0){
                        sistersChildItemList.set(index, upc);
                    }else {
                        sistersChildItemList.add(i, upc);
                    }
                }

            }

            //add sisterchildlist data to mainparentlist
            if (sistersChildItemList.size() > 0) {
                if(sistersChildItemHeader.size()>0) {
                    sistersChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.sister_text) + "("
                                    + Utils.convertEnglishDigittoBangla(sistersChildItemList.size()) + ")",
                            sistersChildItemList));

                    ups = new UserProfileExpenadlbeAdapter(this,
                            sistersChildItemHeader,
                            true);
                    ups.expandParent(0);
                }else{
                    sistersChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.sister_text) + "("
                                    + Utils.convertEnglishDigittoBangla(sistersChildItemList.size()) + ")",
                            sistersChildItemList));
                    ups = new UserProfileExpenadlbeAdapter(this,
                            sistersChildItemHeader,
                            true);
                }
                sisterRecyclerView.removeAllViews();
                sisterRecyclerView.setAdapter(ups);

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

                    UserProfileChild upc = new UserProfileChild("দাদা", checkNullField(userProfile.getProfile().getFamilyMembers().getDadas().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getRelation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getDadas().
                            get(i).getId());
                    int index = getDadaIndex(upc.getId());
                    if(index >=0 && otherRelativeChildItemList.size() > 0){
                        otherRelativeChildItemList.set(index, upc);
                    }else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }
            }


            //add kaka information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfKaka() > 0) {
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKakas().size(); i++) {

                    UserProfileChild upc = new UserProfileChild("চাচা", checkNullField(userProfile.getProfile().getFamilyMembers().getKakas().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getRelation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getKakas().
                            get(i).getId());
                    int index = getKakaIndex(upc.getId());
                    if(index >=0 && otherRelativeChildItemList.size() > 0){
                        otherRelativeChildItemList.set(index, upc);
                    }else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }
            }

            //add mama information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfMama() > 0) {

                // familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getMamas().size(); i++) {

                    UserProfileChild upc = new UserProfileChild("মামা", checkNullField(userProfile.getProfile().getFamilyMembers().getMamas().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getRelation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getMamas().
                            get(i).getId());
                    int index = getMamaIndex(upc.getId());
                    if(index >=0 && otherRelativeChildItemList.size() > 0){
                        otherRelativeChildItemList.set(index, upc);
                    }else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }
            }


            //add fufa information


            if (userProfile.getProfile().getFamilyMembers().getNumberOfFufa() > 0) {

                //familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getFufas().size(); i++) {
                    UserProfileChild upc = new UserProfileChild("ফুপা", checkNullField(userProfile.getProfile().getFamilyMembers().getFufas().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getRelation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getInstitute())

                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getFufas().
                            get(i).getId());
                    int index = getFupaIndex(upc.getId());
                    if(index >=0 && otherRelativeChildItemList.size() > 0){
                        otherRelativeChildItemList.set(index, upc);
                    }else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }


            }


            //add nana information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfNana() > 0) {

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getNanas().size(); i++) {

                    UserProfileChild upc = new UserProfileChild("নানা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getNanas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getOthers().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getOthers().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getOthers().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getOthers().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getNanas().
                            get(i).getId());
                    int index = getNanaIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }
            }


            //add khalu information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfKhalu() > 0) {

                // familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKhalus().size(); i++) {

                    UserProfileChild upc = new UserProfileChild("খালু", checkNullField(userProfile.getProfile().getFamilyMembers().getKhalus().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getRelation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getInstitute())

                    );
//                    otherRelativeChildItemList.add(upc);

                    upc.setId(userProfile.getProfile().getFamilyMembers().getKhalus().
                            get(i).getId());
//                    if(brothersChildItemList.contains(upc)){
                    int index = getKhaluIndex(upc.getId());
                    if(index >=0 && otherRelativeChildItemList.size() > 0){
                        otherRelativeChildItemList.set(index, upc);
                    }else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }
            }

            if (userProfile.getProfile().getFamilyMembers().getNumberOfOthers() > 0) {
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getOthers().size(); i++) {
                    UserProfileChild upc = new UserProfileChild(
                            getResources().getString(R.string.other), checkNullField(userProfile.getProfile().getFamilyMembers().getOthers().
                            get(i).getName())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getRelation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers().
                            getOthers().get(i).getOccupation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getDesignation())
                            + checkNullField(userProfile.getProfile().getFamilyMembers()
                            .getOthers().get(i).getInstitute())

                    );
//                    otherRelativeChildItemList.add(upc);

                    upc.setId(userProfile.getProfile().getFamilyMembers().getOthers().
                            get(i).getId());
//                    if(brothersChildItemList.contains(upc)){
                    int index = getOtherIndex(upc.getId());
                    if(index >=0 && otherRelativeChildItemList.size() > 0){
                        otherRelativeChildItemList.set(index, upc);
                    }else {
                        otherRelativeChildItemList.add(i, upc);
                    }
                }

                if (otherRelativeChildItemList.size() > 0) {
                    if(otherRelativeChildItemHeader.size()>0) {
                        otherRelativeChildItemHeader.set(0, new UserProfileParent(
                                getResources().getString(R.string.other) + "("
                                        + Utils.convertEnglishDigittoBangla(otherRelativeChildItemList.size()) + ")",
                                otherRelativeChildItemList));

                        ups = new UserProfileExpenadlbeAdapter(this,
                                otherRelativeChildItemHeader,
                                true);
                        ups.expandParent(0);
                    }else{
                        otherRelativeChildItemHeader.add(0, new UserProfileParent(
                                getResources().getString(R.string.other) + "("
                                        + Utils.convertEnglishDigittoBangla(otherRelativeChildItemList.size()) + ")",
                                otherRelativeChildItemList));
                        ups = new UserProfileExpenadlbeAdapter(this,
                                otherRelativeChildItemHeader,
                                true);
                    }
                    otherRelativeInfoRecyclerView.removeAllViews();
                    otherRelativeInfoRecyclerView.setAdapter(ups);

                } else {
                    otherRelativeCardview.setVisibility(View.GONE);


//                    otherRelativeChildItemList.add(new UserProfileChild(
//                            getResources().getString(R.string.other),
//                            Utils.convertEnglishDigittoBangla(otherRelativeChildItemList.size())));
//                    otherRelativeChildItemHeader.add(new UserProfileParent(
//                            getResources().getString(R.string.other), otherRelativeChildItemList));
//                    otherRelativeInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
//                            otherRelativeChildItemHeader,
//                            true));

                }

//                if (otherRelativeChildItemList.size() > 0) {
//
//                    otherRelativeChildItemHeader.add(new UserProfileParent(
//                            "অন্যান্য(" + Utils.convertEnglishDigittoBangla(otherRelativeChildItemList.size()) + ")"
//                            , otherRelativeChildItemList));
//
//                    otherRelativeInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
//                            otherRelativeChildItemHeader,
//                            true));
//                } else {
//                    otherRelativeCardview.setVisibility(View.GONE);
//                }
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


        userProfileDescriptionText = (EditText) findViewById(R.id.userProfileDescriptionText);
        img_edit_details = findViewById(R.id.img_edit_details);

        img_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(img_edit_details.getTag() == null)
                    enableEditing(userProfileDescriptionText, img_edit_details);
            }
        });

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

    private void enableEditing(final EditText descView, final ImageView img_edit) {
//        descView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean isFocused) {
//                if (isFocused) {
//                    img_edit.setTag("enabled");
//                    img_edit.setImageResource(R.drawable.accept_icon);
//                    descView.setEnabled(true);
////                    descView.requestFocus();
//                    descView.setSelection(descView.getText().length());
//                } else {
//                    img_edit.setTag("");
//                    img_edit.setImageResource(R.drawable.editicon);
//                    descView.setEnabled(false);
//                    hideSoftKeyboard(descView);
////                    descView.clearFocus();
//                }
//            }
//        });
        if (img_edit.getTag() == null || img_edit.getTag().equals("")) {
            img_edit.setTag("enabled");
            img_edit.setImageResource(R.drawable.accept_icon);
            descView.setEnabled(true);
            descView.requestFocus();
            descView.setSelection(descView.getText().length());
        } else {
            img_edit.setTag("");
            img_edit.setImageResource(R.drawable.editicon);
            descView.setEnabled(false);
            hideSoftKeyboard(descView);
            descView.clearFocus();
        }
    }

    private void hideSoftKeyboard (EditText view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
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
            }else if (requestCode == 10) {
                if (data != null && data.hasExtra("disable_data")) {
                    if (data.hasExtra("disable_data")) {
                        userProfile.getProfile().getPersonalInformation().setDisabilities(data.getStringExtra("disable_data"));
                        userProfile.getProfile().getPersonalInformation().setDisabilitiesDescription(data.getStringExtra("disable_desc_data"));
                        setDataOnPersonalInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 11) {
                if (data != null && data.hasExtra("professional_group_data")) {
                    if (data.hasExtra("professional_group_data")) {
                        userProfile.getProfile().getProfession().setProfessionalGroup(data.getStringExtra("professional_group_data"));
                        setDataOnProfessionRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 12) {
                if (data != null && data.hasExtra("profession_data")) {
                    if (data.hasExtra("profession_data")) {
                        userProfile.getProfile().getProfession().setOccupation(data.getStringExtra("profession_data"));
                        setDataOnProfessionRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 13) {
                if (data != null && data.hasExtra("fasting_data")) {
                    if (data.hasExtra("fasting_data")) {
                        userProfile.getProfile().getOtherInformation().setFasting(data.getStringExtra("fasting_data"));
                        setDataOnOtherInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 14) {
                if (data != null && data.hasExtra("prayer_data")) {
                    if (data.hasExtra("prayer_data")) {
                        userProfile.getProfile().getOtherInformation().setPrayer(data.getStringExtra("prayer_data"));
                        setDataOnOtherInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 15) {
                if (data != null && data.hasExtra("own_house_data")) {
                    if (data.hasExtra("own_house_data")) {
                        userProfile.getProfile().getProfileLivingIn().setOwnHouse(data.getStringExtra("own_house_data"));
                        setDataOnOtherInfoRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 16) {
                if (data != null && data.hasExtra("education_data")) {
                    if (data.hasExtra("education_data")) {
                        EducationInformation ei = userProfile.getProfile().getEducationInformation().get(0);
                        ei.setHighestDegree(data.getStringExtra("education_data"));
                        ei.setSubject(data.getStringExtra("subject"));
                        ei.setInstitution(data.getStringExtra("institute"));
                        userProfile.getProfile().getEducationInformation().set(0, ei);
                        setDataOnEducationalRecylerView(userProfile);
                    }
                }
            }else if (requestCode == 17) {
                if (data != null && data.hasExtra("profession_data")) {
                    if (data.hasExtra("profession_data")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setOccupation(data.getStringExtra("profession_data"));
                    }
                    if (data.hasExtra("professional_group_data") && data.hasExtra("professional_group_data")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setOccupation(userProfile.getProfile().getFamilyMembers().getFather().getOccupation() + (data.getStringExtra("professional_group_data").length()>0?" (" + data.getStringExtra("professional_group_data") + ")" : ""));
                    }
                    if (data.hasExtra("name")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setName(data.getStringExtra("name"));
                    }
                    if (data.hasExtra("designation")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setDesignation(data.getStringExtra("designation"));
                    }
                    if (data.hasExtra("institute")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setInstitute(data.getStringExtra("institute"));
                    }

                    setDataOnFamilyMemberRecylerView(userProfile);
                }
            }else if (requestCode == 18) {
                if (data != null && data.hasExtra("profession_data")) {
                    if (data.hasExtra("profession_data")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setOccupation(data.getStringExtra("profession_data"));
                    }
                    if (data.hasExtra("professional_group_data")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setOccupation(userProfile.getProfile().getFamilyMembers().getMother().getOccupation() + (data.getStringExtra("professional_group_data").length()>0?" (" + data.getStringExtra("professional_group_data") + ")":""));
                    }
                    if (data.hasExtra("name")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setName(data.getStringExtra("name"));
                    }
                    if (data.hasExtra("designation")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setDesignation(data.getStringExtra("designation"));
                    }
                    if (data.hasExtra("institute")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setInstitute(data.getStringExtra("institute"));
                    }

                    setDataOnFamilyMemberRecylerView(userProfile);
                }
            }else if (requestCode == 19) {
                if(data!=null) {

                    if(data.hasExtra("name_brother")){
                        int id = data.getIntExtra("id", 0);
                        int broIndex = getBroIndex(id);
                        if(broIndex>=0) {
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setName(data.getStringExtra("name_brother"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setAge(data.getIntExtra("age_brother", 0));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setOccupation(data.getStringExtra("profession_brother_data"));
//                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setProfessionalGroupData(data.getStringExtra("professional_group_brother_data"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setDesignation(data.getStringExtra("designation_brother"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setInstitute(data.getStringExtra("institute_brother"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setMaritalStatus(data.getStringExtra("marital_status_brother_data"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setSpouseName(data.getStringExtra("name_brother_spouse"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setSpouseOccupation(data.getStringExtra("profession_brother_spouse_data"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setSpouseDesignation(data.getStringExtra("designation_brother_spouse"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setSpouseInstitue(data.getStringExtra("institute_brother_spouse"));
                        }
                    }
//                    intent.putExtra("profession_brother_value", brotherOcupationValue);
//                    intent.putExtra("professional_group_brother_data",
//                            brotherProfessionalGroup.getText().toString());
//                    intent.putExtra("professional_group_brother_value",
//                            brotherProfessionalGroupValue);
//                    intent.putExtra("marital_status_brother_value", brotherMaritalStatusValue);
//                    intent.putExtra("profession_brother_spouse_value", brotherOcupationSpouseValue);
//                    intent.putExtra("professional_group_brother_spouse_data",
//                            brotherProfessionalGroupSpouse.getText().toString());
//                    intent.putExtra("professional_group_brother_spouse_value",
//                            brotherProfessionalGroupValue);
//                    intent.putExtra("institute_brother_spouse",
//                            institutionBrotherSpouse.getText().toString());
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            }else if (requestCode == 20) {
                if(data!=null) {

                    if(data.hasExtra("name_brother")){
                        int id = data.getIntExtra("id", 0);
                        int broIndex = getSisIndex(id);
                        if(broIndex>=0) {
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    broIndex).setName(data.getStringExtra("name_brother"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setAge(data.getIntExtra("age_brother", 0));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setOccupation(data.getStringExtra("profession_brother_data"));
//                            userProfile.getProfile().getFamilyMembers().getBrothers().get(broIndex).setProfessionalGroupData(data.getStringExtra("professional_group_brother_data"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setDesignation(data.getStringExtra("designation_brother"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setInstitute(data.getStringExtra("institute_brother"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setMaritalStatus(data.getStringExtra("marital_status_brother_data"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseName(data.getStringExtra("name_brother_spouse"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseOccupation(data.getStringExtra("profession_brother_spouse_data"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseDesignation(data.getStringExtra("designation_brother_spouse"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseInstitue(data.getStringExtra("institute_brother_spouse"));
                        }
                    }
//                    intent.putExtra("profession_brother_value", brotherOcupationValue);
//                    intent.putExtra("professional_group_brother_data",
//                            brotherProfessionalGroup.getText().toString());
//                    intent.putExtra("professional_group_brother_value",
//                            brotherProfessionalGroupValue);
//                    intent.putExtra("marital_status_brother_value", brotherMaritalStatusValue);
//                    intent.putExtra("profession_brother_spouse_value", brotherOcupationSpouseValue);
//                    intent.putExtra("professional_group_brother_spouse_data",
//                            brotherProfessionalGroupSpouse.getText().toString());
//                    intent.putExtra("professional_group_brother_spouse_value",
//                            brotherProfessionalGroupValue);
//                    intent.putExtra("institute_brother_spouse",
//                            institutionBrotherSpouse.getText().toString());
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            }else if (requestCode == 21) {
                if(data!=null) {

                    if(data.hasExtra("name_other")){
                        int id = data.getIntExtra("id", 0);
                        int otherIndex = getOtherIndex(id);
                        if(otherIndex>=0) {
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setName(data.getStringExtra("name_other"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(otherIndex).setOccupation(data.getStringExtra("profession_other_data"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(otherIndex).setRelation(data.getStringExtra("relation_data"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(otherIndex).setDesignation(data.getStringExtra("designation_other"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(otherIndex).setInstitute(data.getStringExtra("institute_other"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setMaritalStatus(data.getStringExtra("marital_status_brother_data"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseName(data.getStringExtra("name_brother_spouse"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseOccupation(data.getStringExtra("profession_brother_spouse_data"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseDesignation(data.getStringExtra("designation_brother_spouse"));
//                            userProfile.getProfile().getFamilyMembers().getSisters().get(broIndex).setSpouseInstitue(data.getStringExtra("institute_brother_spouse"));
                        }
                    }
//                    intent.putExtra("profession_brother_value", brotherOcupationValue);
//                    intent.putExtra("professional_group_brother_data",
//                            brotherProfessionalGroup.getText().toString());
//                    intent.putExtra("professional_group_brother_value",
//                            brotherProfessionalGroupValue);
//                    intent.putExtra("marital_status_brother_value", brotherMaritalStatusValue);
//                    intent.putExtra("profession_brother_spouse_value", brotherOcupationSpouseValue);
//                    intent.putExtra("professional_group_brother_spouse_data",
//                            brotherProfessionalGroupSpouse.getText().toString());
//                    intent.putExtra("professional_group_brother_spouse_value",
//                            brotherProfessionalGroupValue);
//                    intent.putExtra("institute_brother_spouse",
//                            institutionBrotherSpouse.getText().toString());
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            }
        }
    }

    private int getKakaIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getKakas().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getKakas().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getMamaIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getMamas().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getMamas().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getDadaIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getDadas().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getDadas().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getNanaIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getNanas().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getNanas().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getFupaIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getFufas().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getFufas().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getKhaluIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getKhalus().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getKhalus().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getOtherIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getOthers().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getOthers().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getBroIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getBrothers().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getSisIndex(int id) {
        for(int i = 0; i<userProfile.getProfile().getFamilyMembers().getSisters().size(); i++){
            if(userProfile.getProfile().getFamilyMembers().getSisters().get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getPosition(UserProfile userProfile) {
        return  0;
    }
}
