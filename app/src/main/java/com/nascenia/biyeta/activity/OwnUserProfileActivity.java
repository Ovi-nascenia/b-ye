package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.adapter.ViewPagerAdapter;
import com.nascenia.biyeta.appdata.RecyclerItemClickListener;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.newuserprofile.Address;
import com.nascenia.biyeta.model.newuserprofile.Brother;
import com.nascenia.biyeta.model.newuserprofile.Dada;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.Father;
import com.nascenia.biyeta.model.newuserprofile.Mother;
import com.nascenia.biyeta.model.newuserprofile.Other;
import com.nascenia.biyeta.model.newuserprofile.PersonalInformation;
import com.nascenia.biyeta.model.newuserprofile.ProfileLivingIn;
import com.nascenia.biyeta.model.newuserprofile.Sister;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.CalenderBanglaInfo;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.CustomStopScrollingRecylerLayoutManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import static com.nascenia.biyeta.view.SendRequestFragmentView.checkNullField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saiful on 4/2/17.
 */

public class OwnUserProfileActivity extends AppCompatActivity {
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
    private RecyclerView communicationRecyclerView;
    private LinearLayout sliderDotsPanel;
    private int dotscount;
    private ImageView[] dots;
    private ImageView img_edit_details;
    private ImageView img_edit_accept;

    private UserProfile userProfile;

    // private int familyMemberCounter;

    private ImageView userProfileImage;

    private CardView userProfileDescriptionCardView;
    private EditText userProfileDescriptionText;


    private ArrayList<UserProfileChild> personalInfoChildItemList =
            new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> personalInfoChildItemHeader =
            new ArrayList<UserProfileParent>();

    private ArrayList<UserProfileChild> educationalInfoChildItemList =
            new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> educationalInfoChildItemHeader =
            new ArrayList<UserProfileParent>();

    private ArrayList<UserProfileChild> professionChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> professionChildItemHeader =
            new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> parentChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> parentChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> brothersChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> brothersChildItemHeader =
            new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> sistersChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> sistersChildItemHeader =
            new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> childsChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> childsChildItemHeader = new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> otherRelativeChildItemList =
            new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> otherRelativeChildItemHeader =
            new ArrayList<UserProfileParent>();


    private ArrayList<UserProfileChild> otherInfoChildItemList = new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> otherInfoChildItemHeader =
            new ArrayList<UserProfileParent>();

    private ArrayList<UserProfileChild> communicationChildItemList =
            new ArrayList<UserProfileChild>();
    private ArrayList<UserProfileParent> communicationChildItemHeader =
            new ArrayList<UserProfileParent>();


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
    private UserProfileExpenadlbeAdapter upea, upga, upoa, upedua, uppa, upb, ups, upadd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_profile_layout);


        sharePref = new SharePref(OwnUserProfileActivity.this);
        initView();


        if (Utils.isOnline(getApplicationContext())) {

            fetchUserProfileInfo();

        } else {
            Utils.ShowAlert(OwnUserProfileActivity.this,
                    getString(R.string.no_internet_connection));
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
        mTracker.setScreenName(getIntent().getExtras().getString("user_name") + "'s Profile");
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

                            if (userProfile.getProfile().getPersonalInformation().getAboutYourself()
                                    != null) {
                                userProfileDescriptionText.setText(userProfile.getProfile().
                                        getPersonalInformation().getAboutYourself());


                                if (userProfile.getProfile().getPersonalInformation().getImage()
                                        != null) {
                                    if (userProfile.getProfile().getPersonalInformation()
                                            .getImage().getOther().size()
                                            > 1) {

                                        dotscount =
                                                userProfile.getProfile().getPersonalInformation()
                                                        .getImage().getOther().size();
                                        dots = new ImageView[dotscount];

                                        for (int i = 0; i < dotscount; i++) {
                                            dots[i] = new ImageView(getApplicationContext());
                                            if (i == 0) {
                                                dots[i].setImageDrawable(ContextCompat.getDrawable(
                                                        getApplicationContext(),
                                                        R.drawable.selected_dot));
                                            } else {
                                                dots[i].setImageDrawable(ContextCompat.getDrawable(
                                                        getApplicationContext(),
                                                        R.drawable.default_dot));
                                            }
                                            LinearLayout.LayoutParams params =
                                                    new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                            params.setMargins(6, 4, 6, 4);
                                            sliderDotsPanel.addView(dots[i], params);
                                            dots[i].getLayoutParams().height = 16;
                                            dots[i].getLayoutParams().width = 16;
                                        }

                                        if (viewPager != null) {
                                            viewPager.addOnPageChangeListener(
                                                    new ViewPager.OnPageChangeListener() {
                                                        private int fromPosition = 0,
                                                                previousState = 0;

                                                        @Override
                                                        public void onPageScrolled(int position,
                                                                float positionOffset,
                                                                int positionOffsetPixels) {
                                                            fromPosition = position;
                                                        }

                                                        @Override
                                                        public void onPageSelected(int position) {
                                                            for (int i = 0; i < dotscount; i++) {
                                                                dots[i].setImageDrawable(
                                                                        ContextCompat.getDrawable(
                                                                                getApplicationContext(),
                                                                                R.drawable
                                                                                        .default_dot));
                                                            }
                                                            dots[position].setImageDrawable(
                                                                    ContextCompat.getDrawable(
                                                                            getApplicationContext(),
                                                                            R.drawable
                                                                                    .selected_dot));

                                                        }

                                                        @Override
                                                        public void onPageScrollStateChanged(
                                                                int state) {
                                                            // if(fromPosition+1==dotscount &&
                                                            // state == 1)
                                                            // {
                                                            //     viewPager.setCurrentItem(0,
                                                            // false);
                                                            //}

                                                            /*if (state == ViewPager
                                                        .SCROLL_STATE_IDLE) {
                                                            int pageCount = dotscount;

                                                            if (fromPosition == 0){
                                                                viewPager.setCurrentItem
                                                                (pageCount-1,false);
                                                            } else if (fromPosition == pageCount-1){
                                                                viewPager.setCurrentItem(1,false);
                                                            }
                                                        }*/


                                                            if (state == 0 && previousState != 2
                                                                    && fromPosition
                                                                    == dotscount - 1) {
                                                                viewPager.setCurrentItem(0, true);
                                                                return;
                                                            } else if (state == 0
                                                                    && previousState != 2
                                                                    && fromPosition == 0) {
                                                                viewPager.setCurrentItem(
                                                                        dotscount - 1, true);
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

                            userNameTextView.setText(
                                    getIntent().getExtras().getString("user_name"));
                            setUserOwnImage(userProfile);
                            setDataOnPersonalInfoRecylerView(userProfile, -1);
                            setDataOnEducationalRecylerView(userProfile);
                            setDataOnProfessionRecylerView(userProfile);
                            setDataOnFamilyMemberRecylerView(userProfile);
                            setDataOnOtherInfoRecylerView(userProfile);
                            setDataOnCommunicationRecylerView(userProfile);


                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                        }
                    });

                    image = new int[]{R.drawable.accept_icon, R.drawable.accept_icon,
                            R.drawable.accept_icon};

                    viewPager = (ViewPager) findViewById(R.id.pager);
                    if (userProfile.getProfile().getPersonalInformation().getImage() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userProfile.getProfile().getPersonalInformation().getImage()
                                        .getOther().size()
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
//                    application.trackEception(e, "fetchUserProfileInfo",
// "OwnUserProfileActivity", e.getMessage().toString(), mTracker);
                }


            }


        }).start();


    }


    private void setUserOwnImage(UserProfile userProfile) {
        //Log.i("ownprofileimage", userProfile.getProfile().getPersonalInformation().getImage()
        // .getProfilePicture().toString());

        if (userProfile.getProfile().getPersonalInformation().getImage() != null) {
//            Log.i("ownprofileimage", Utils.Base_URL + userProfile.getProfile()
// .getPersonalInformation().getImage().getProfilePicture());

            Picasso.with(OwnUserProfileActivity.this)
                    .load(Utils.Base_URL
                            + userProfile.getProfile().getPersonalInformation().getImage()
                            .getProfilePicture())
                    .into(userProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            userProfileImage.post(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.scaleImage(OwnUserProfileActivity.this, 2f,
                                            userProfileImage);
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
                        (userProfile.getProfile().getPersonalInformation().getGender().equals(
                                Utils.MALE_GENDER))) {
                    userProfileImage.setImageResource(R.drawable.profile_icon_male);
                    userProfileImage.setImageResource(R.drawable.profile_icon_male);
                } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                        (userProfile.getProfile().getPersonalInformation().getGender().equals(
                                Utils.FEMALE_GENDER))) {
                    userProfileImage.setImageResource(R.drawable.profile_icon_female);
                    userProfileImage.setImageResource(R.drawable.profile_icon_female);
                } else {
//                    Log.i("image", "nothing found");
                }
            }
        }


    }

    private void setDataOnOtherInfoRecylerView(UserProfile userProfile) {

        /*..................................other inormation block
        start.........................................*/


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()).equals(
                "")) &&
                (otherInfoChildItemList.size() > 0 && otherInfoChildItemList.get(
                        0).getTitle().equalsIgnoreCase(
                        getResources().getString(R.string.fast_text)))) {

            otherInfoChildItemList.set(0,
                    new UserProfileChild(getResources().getString(R.string.fast_text),
                            userProfile.getProfile().getOtherInformation().getFasting()));

        } else {
            otherInfoChildItemList.add(0,
                    new UserProfileChild(getResources().getString(R.string.fast_text),
                            userProfile.getProfile().getOtherInformation().getFasting()));

        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()).equals(""))
                &&
                (otherInfoChildItemList.size() > 1 && otherInfoChildItemList.get(
                        1).getTitle().equalsIgnoreCase(
                        getResources().getString(R.string.prayet_text)))) {

            otherInfoChildItemList.set(1,
                    new UserProfileChild(getResources().getString(R.string.prayet_text),
                            userProfile.getProfile().getOtherInformation().getPrayer()));

        } else {
            otherInfoChildItemList.add(1,
                    new UserProfileChild(getResources().getString(R.string.prayet_text),
                            userProfile.getProfile().getOtherInformation().getPrayer()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(
                Utils.FEMALE_GENDER))

                & (!(checkNullField(
                userProfile.getProfile().getOtherInformation().getJobAfterMarriage()).equals(
                "")))) {


            otherInfoChildItemList.add(
                    new UserProfileChild(getResources().getString(R.string.after_marrige_job_text),
                            userProfile.getProfile().getOtherInformation().getJobAfterMarriage()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(
                Utils.FEMALE_GENDER))

                & (!(checkNullField(
                userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


            otherInfoChildItemList.add(
                    new UserProfileChild(getResources().getString(R.string.hijab_text),
                            userProfile.getProfile().getOtherInformation().getHijab()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(
                Utils.MALE_GENDER))
                && (!(checkNullField(
                userProfile.getProfile().getProfileLivingIn().getOwnHouse()).equals(""))) &&
                (otherInfoChildItemList.size() > 2 && otherInfoChildItemList.get(
                        2).getTitle().equalsIgnoreCase(
                        getResources().getString(R.string.own_house_text)))) {

            otherInfoChildItemList.set(2,
                    new UserProfileChild(getResources().getString(R.string.own_house_text),
                            userProfile.getProfile().getProfileLivingIn().getOwnHouse()));

        } else {
            otherInfoChildItemList.add(2,
                    new UserProfileChild(getResources().getString(R.string.own_house_text),
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
            if (otherInfoChildItemHeader.size() == 0) {
                otherInfoChildItemHeader.add(new UserProfileParent(
                        getResources().getString(R.string.other_question_ans_text),
                        otherInfoChildItemList));

                otherInformationRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(this, otherInformationRecyclerView,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        updateAction(view, position);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        return;
                                    }
                                })
                );
            }

            otherInformationRecyclerView.removeAllViews();
            upoa = new UserProfileExpenadlbeAdapter(this,
                    otherInfoChildItemHeader,
                    true);
            otherInformationRecyclerView.setAdapter(upoa);
            upoa.expandParent(0);
//            upea.notifyDataSetChanged();
        }

        /*..................................other inormation block
        end.........................................*/

    }

    private void setDataOnProfessionRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getProfession() != null) {
            if (professionChildItemList.size() > 0 && professionChildItemList.get(
                    0).getTitle().equalsIgnoreCase(Utils.setBanglaProfileTitle(
                    getResources().getString(R.string.professional_group_text)))) {
                professionChildItemList.set(0, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.professional_group_text)),
                        userProfile.getProfile().getProfession().getProfessionalGroup()));
            } else {
                professionChildItemList.add(0, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.professional_group_text)),
                        userProfile.getProfile().getProfession().getProfessionalGroup()));
            }

            if (professionChildItemList.size() > 1 && professionChildItemList.get(
                    1).getTitle().equalsIgnoreCase(
                    getResources().getString(R.string.profession_text))) {
                professionChildItemList.set(1, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.occupation_text)),
                        userProfile.getProfile().getProfession().getOccupation()));
            } else {
                professionChildItemList.add(1,
                        new UserProfileChild(getResources().getString(R.string.profession_text),
                                userProfile.getProfile().getProfession().getOccupation()));
            }


            if (professionChildItemList.size() > 2 && professionChildItemList.get(
                    2).getTitle().equalsIgnoreCase(Utils.setBanglaProfileTitle(
                    getResources().getString(R.string.designation_text)))) {
                professionChildItemList.set(2, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.designation_text)),
                        userProfile.getProfile().getProfession().getDesignation()));
            } else {
                professionChildItemList.add(2, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.designation_text)),
                        userProfile.getProfile().getProfession().getDesignation()));
            }


            if (professionChildItemList.size() > 3 && professionChildItemList.get(
                    3).getTitle().equalsIgnoreCase(Utils.setBanglaProfileTitle(
                    getResources().getString(R.string.institute_text)))) {
                professionChildItemList.set(3, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.institute_text)),
                        userProfile.getProfile().getProfession().getInstitute()));
            } else {
                professionChildItemList.add(3, new UserProfileChild(Utils.setBanglaProfileTitle(
                        getResources().getString(R.string.institute_text)),
                        userProfile.getProfile().getProfession().getInstitute()));
            }


            if (professionChildItemList.size() > 0) {

                if (professionChildItemHeader.size() == 0) {
                    professionChildItemHeader.add(new UserProfileParent(
                            getResources().getString(R.string.profession_text),
                            professionChildItemList));
                    upga = new UserProfileExpenadlbeAdapter(this,
                            professionChildItemHeader,
                            true);
                    professionRecyclerView.setAdapter(upga);

                    professionRecyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, professionRecyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );
                } else {
                    personalInfoRecylerView.removeAllViews();
                    upga = new UserProfileExpenadlbeAdapter(this,
                            professionChildItemHeader,
                            true);
                    professionRecyclerView.setAdapter(upga);
                    upga.notifyDataSetChanged();
                    upga.expandParent(0);
                }
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

            if (educationalInfoChildItemList.size() > 0) {
                educationalInfoChildItemList.set(0, new UserProfileChild("শিক্ষা", education));
            } else {
                educationalInfoChildItemList.add(0, new UserProfileChild("শিক্ষা", education));
            }

            if (educationalInfoChildItemList.size() > 0) {
                educationRecylerView.removeAllViews();
                if (educationalInfoChildItemHeader.size() > 0) {
                    educationalInfoChildItemHeader.set(0,
                            new UserProfileParent(getResources().getString(R.string.education_male),
                                    educationalInfoChildItemList));
                    upedua = new UserProfileExpenadlbeAdapter(this,
                            educationalInfoChildItemHeader,
                            true);
                    upedua.expandParent(0);
                } else {
                    educationalInfoChildItemHeader.add(0,
                            new UserProfileParent(getResources().getString(R.string.education_male),
                                    educationalInfoChildItemList));
                    upedua = new UserProfileExpenadlbeAdapter(this,
                            educationalInfoChildItemHeader,
                            true);
                    educationRecylerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, educationRecylerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );
                }
                educationRecylerView.setAdapter(upedua);

            }

        }
    }

    private void setDataOnPersonalInfoRecylerView(UserProfile userProfile, int updateIndex) {

        if (personalInfoChildItemList.size() > 0 && personalInfoChildItemList.get(
                0).getTitle().equalsIgnoreCase(getResources().getString(R.string.age))
                && updateIndex == 0) {
            personalInfoChildItemList.set(0,
                    new UserProfileChild(getResources().getString(R.string.age),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getAge()) +
                                    " " + getResources().getString(R.string.year) + ","

                    ));
        } else if (updateIndex == -1) {
            personalInfoChildItemList.add(0,
                    new UserProfileChild(getResources().getString(R.string.age),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getAge()) +
                                    " " + getResources().getString(R.string.year) + ","

                    ));
        }

        if (personalInfoChildItemList.size() > 1 && personalInfoChildItemList.get(
                1).getTitle().equalsIgnoreCase(getResources().getString(R.string.height))
                && updateIndex == 1) {
            personalInfoChildItemList.set(1,
                    new UserProfileChild(getResources().getString(R.string.height),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getHeightFt())
                                    + "'" +
                                    Utils.convertEnglishDigittoBangla(
                                            userProfile.getProfile().getPersonalInformation()
                                                    .getHeightInc())
                                    + "\""
                    ));
        } else if (updateIndex == -1) {
            personalInfoChildItemList.add(1,
                    new UserProfileChild(getResources().getString(R.string.height),
                            Utils.convertEnglishDigittoBangla(
                                    userProfile.getProfile().getPersonalInformation().getHeightFt())
                                    + "'" +
                                    Utils.convertEnglishDigittoBangla(
                                            userProfile.getProfile().getPersonalInformation()
                                                    .getHeightInc())
                                    + "\""
                    ));
        }

        if (personalInfoChildItemList.size() > 2 && personalInfoChildItemList.get(
                2).getTitle().equalsIgnoreCase(getResources().getString(R.string.religion_text))
                && updateIndex == 2) {
            personalInfoChildItemList.set(2,
                    new UserProfileChild(getResources().getString(R.string.religion_text),
                            userProfile.getProfile().getProfileReligion().getReligion()
                    ));
        } else if (updateIndex == -1) {
            personalInfoChildItemList.add(2,
                    new UserProfileChild(getResources().getString(R.string.religion_text),
                            userProfile.getProfile().getProfileReligion().getReligion()
                    ));
        }

        if (userProfile.getProfile().getProfileReligion().getCast() != null) {
            if (personalInfoChildItemList.size() > 3 && personalInfoChildItemList.get(
                    3).getTitle().equalsIgnoreCase(getResources().getString(R.string.cast_text))
                    && updateIndex == 2) {
                personalInfoChildItemList.set(3,
                        new UserProfileChild(getResources().getString(R.string.cast_text),
                                userProfile.getProfile().getProfileReligion().getCast()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(3,
                        new UserProfileChild(getResources().getString(R.string.cast_text),
                                userProfile.getProfile().getProfileReligion().getCast()
                        ));
            }
        }

        if (!(checkNullField(
                userProfile.getProfile().getAddress().getPresentAddress().getCountry())).equals(
                "")) {
            if (personalInfoChildItemList.size() > 4 && personalInfoChildItemList.get(
                    4).getTitle().equalsIgnoreCase(
                    getResources().getString(R.string.present_loaction_text))
                    && updateIndex == 22) {
                personalInfoChildItemList.set(4,
                        new UserProfileChild(
                                getResources().getString(R.string.present_loaction_text),
                                userProfile.getProfile().getAddress().getPresentAddress()
                                        .getCountry()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(4,
                        new UserProfileChild(
                                getResources().getString(R.string.present_loaction_text),
                                userProfile.getProfile().getAddress().getPresentAddress()
                                        .getCountry()
                        ));
            }
        }


        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())).equals(
                "")) {
            if (personalInfoChildItemList.size() > 5 && personalInfoChildItemList.get(
                    5).getTitle().equalsIgnoreCase(getResources().getString(R.string.home_town))
                    && updateIndex == 22) {
                personalInfoChildItemList.set(5,
                        new UserProfileChild(getResources().getString(R.string.home_town),
                                userProfile.getProfile().getProfileLivingIn().getLocation()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(5,
                        new UserProfileChild(getResources().getString(R.string.home_town),
                                userProfile.getProfile().getProfileLivingIn().getLocation()
                        ));
            }
        }


        if (!(checkNullField(
                userProfile.getProfile().getPersonalInformation().getSkinColor())).equals("")) {
            if (personalInfoChildItemList.size() > 6 && personalInfoChildItemList.get(
                    6).getTitle().equalsIgnoreCase(getResources().getString(R.string.skin_color))
                    && updateIndex == 6) {

                personalInfoChildItemList.set(6,
                        new UserProfileChild(getResources().getString(R.string.skin_color),
                                userProfile.getProfile().getPersonalInformation().getSkinColor()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(6,
                        new UserProfileChild(getResources().getString(R.string.skin_color),
                                userProfile.getProfile().getPersonalInformation().getSkinColor()
                        ));
            }

        }

        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals(
                "")) {
            if (personalInfoChildItemList.size() > 7 && personalInfoChildItemList.get(
                    7).getTitle().equalsIgnoreCase(getResources().getString(R.string.body))
                    && updateIndex == 7) {
                personalInfoChildItemList.set(7,
                        new UserProfileChild(getResources().getString(R.string.body),
                                userProfile.getProfile().getPersonalInformation().getWeight()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(7,
                        new UserProfileChild(getResources().getString(R.string.body),
                                userProfile.getProfile().getPersonalInformation().getWeight()
                        ));
            }

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()))
                .equals("")) {
            if (personalInfoChildItemList.size() > 8 && personalInfoChildItemList.get(
                    8).getTitle().equalsIgnoreCase(
                    getResources().getString(R.string.marital_status)) && updateIndex == 8) {
                personalInfoChildItemList.set(8,
                        new UserProfileChild(getResources().getString(R.string.marital_status),
                                userProfile.getProfile().getPersonalInformation().getMaritalStatus()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(8,
                        new UserProfileChild(getResources().getString(R.string.marital_status),
                                userProfile.getProfile().getPersonalInformation().getMaritalStatus()
                        ));
            }

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getBloodGroup()))
                .equals("")) {
            if (personalInfoChildItemList.size() > 9 && personalInfoChildItemList.get(
                    9).getTitle().equalsIgnoreCase(
                    getResources().getString(R.string.blood_group_text)) && updateIndex == 9) {
                personalInfoChildItemList.set(9,
                        new UserProfileChild(getResources().getString(R.string.blood_group_text),
                                userProfile.getProfile().getPersonalInformation().getBloodGroup()
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(9,
                        new UserProfileChild(getResources().getString(R.string.blood_group_text),
                                userProfile.getProfile().getPersonalInformation().getBloodGroup()
                        ));
            }

        }

        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getDisabilities()))
                .equals("")) {

            if (personalInfoChildItemList.size() > 10 && personalInfoChildItemList.get(
                    10).getTitle().equalsIgnoreCase(
                    getResources().getString(R.string.disabilities_text)) && updateIndex == 10) {

                personalInfoChildItemList.set(10,
                        new UserProfileChild(getResources().getString(R.string.disabilities_text),

                                userProfile.getProfile().getPersonalInformation().getDisabilities()
                                        + ", " +
                                        checkNullField(
                                                userProfile.getProfile().getPersonalInformation()
                                                        .getDisabilitiesDescription())
                        ));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(10,
                        new UserProfileChild(getResources().getString(R.string.disabilities_text),

                                userProfile.getProfile().getPersonalInformation().getDisabilities()
                                        + ", " +
                                        checkNullField(
                                                userProfile.getProfile().getPersonalInformation()
                                                        .getDisabilitiesDescription())

                        ));
            }
        }

        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(
                Utils.MALE_GENDER)) &&
                (!(checkNullField(userProfile.getProfile().getPersonalInformation().getSmoking()))
                        .equals(""))
                ) {
            if (personalInfoChildItemList.size() > 11 && personalInfoChildItemList.get(
                    11).getTitle().equalsIgnoreCase(
                    getResources().getString(R.string.smoking_text)) && updateIndex == 11) {
                personalInfoChildItemList.set(11,
                        new UserProfileChild(getResources().getString(R.string.smoking_text),
                                userProfile.getProfile().getPersonalInformation().getSmoking()));
            } else if (updateIndex == -1) {
                personalInfoChildItemList.add(11,
                        new UserProfileChild(getResources().getString(R.string.smoking_text),
                                userProfile.getProfile().getPersonalInformation().getSmoking()));
            }

        }

        //add personal Child Item list to parent list
        if (personalInfoChildItemList.size() > 0) {
            if (personalInfoChildItemHeader.size() == 0) {
                personalInfoChildItemHeader.add(
                        new UserProfileParent(getResources().getString(R.string.personal_info),
                                personalInfoChildItemList));
                personalInfoRecylerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(this, personalInfoRecylerView,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        updateAction(view, position);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        return;
                                    }
                                })
                );
            }

            personalInfoRecylerView.removeAllViews();
            upea = new UserProfileExpenadlbeAdapter(this,
                    personalInfoChildItemHeader,
                    true);
            personalInfoRecylerView.setAdapter(upea);
            upea.expandParent(0);
//            upea.notifyDataSetChanged();
        }
    }

    private void updateAction(View view, int position) {

        final TextView titleTextView = view.findViewById(R.id.titleTextView);
        final EditText descView = view.findViewById(R.id.titleResultEditText);
        final ImageView img_edit = view.findViewById(R.id.img_edit);
        final boolean editEnabled = false;
        if (titleTextView != null) {

            if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.age))) {
                startActivityForResult(
                        new Intent(this, BirthDatePickerPopUpActivity.class),
                        Utils.DATE_OF_BIRTH_REQUEST_CODE);

            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.height))) {
                Intent intent = new Intent(this, PopUpPersonalInfo.class);
                intent.putExtra("data", "height");
                startActivityForResult(intent,
                        Utils.HEIGHT_REQUEST_CODE);
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.skin_color))) {
                new GetPersonalInfoStepFetchConstant(this, "skin",
                        position, Utils.SKIN_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.body))) {
                new GetPersonalInfoStepFetchConstant(this, "body",
                        position, Utils.BODY_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.marital_status))) {
                new GetPersonalInfoStepFetchConstant(this, "marital_status",
                        position, Utils.MARITAL_STATUS_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.blood_group_text))) {
                new GetPersonalInfoStepFetchConstant(this, "blood_group",
                        position, Utils.BLOOD_GROUP_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.smoking_text))) {
                new GetPersonalInfoStepFetchConstant(this, "smoke",
                        position, Utils.SMOKE_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.religion_text))) {
                new GetReligionStepFetchConstant(this).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.present_loaction_text))) {
                new GetAddressStepFetchConstant(this,
                        Utils.PRESENT_LOCATION_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.home_town))) {
                new GetAddressStepFetchConstant(this,
                        Utils.HOME_TOWN_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.present_address_text))) {
                new GetAddressStepFetchConstant(this,
                        Utils.PRESENT_ADDRESS_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.permanent_address_text))) {
                new GetAddressStepFetchConstant(this,
                        Utils.PERMANENT_ADDRESS_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.disabilities_text))) {
                new GetPersonalInfoStepFetchConstant(this, "disable",
                        position, Utils.DISABLE_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.cast_text))) {
                new GetReligionStepFetchConstant(this).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    Utils.setBanglaProfileTitle(getResources().getString(
                            R.string.professional_group_text)))) {
                new GetPersonalInfoStepFetchConstant(this, "professional_group",
                        position, Utils.PROFESSIONAL_GROUP_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.profession_text))) {
                new GetPersonalInfoStepFetchConstant(this, "profession",
                        position, Utils.PROFESSION_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    Utils.setBanglaProfileTitle(
                            getResources().getString(R.string.designation_text)))) {
                enableEditing(descView, getResources().getString(
                        R.string.designation_text), img_edit, true, Utils.DESIGNATION);

            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    Utils.setBanglaProfileTitle(
                            getResources().getString(R.string.institute_text)))) {
                enableEditing(descView, getResources().getString(
                        R.string.institute_text), img_edit, true, Utils.INSTITUTION);
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.fast_text))) {
                new GetPersonalInfoStepFetchConstant(this, "fasting",
                        position, Utils.FASTING_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.prayet_text))) {
                new GetPersonalInfoStepFetchConstant(this, "prayer",
                        position, Utils.PRAYER_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.own_house_text))) {
                new GetPersonalInfoStepFetchConstant(this, "own_house",
                        position, Utils.OWN_HOUSE_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.education))) {
                new GetPersonalInfoStepFetchConstant(this, "education",
                        position, Utils.EDUCATION_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.father_text))) {
                new GetFamilyInfoStepFetchConstant(this, "father",
                        Utils.FATHER_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.mother_text))) {
                new GetFamilyInfoStepFetchConstant(this, "mother",
                        Utils.MOTHER_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.brother_text))) {
                new GetPersonalInfoStepFetchConstant(this, "brother", position,
                        Utils.BROTHER_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.sister_text))) {
                new GetPersonalInfoStepFetchConstant(this, "sister", position,
                        Utils.SISTER_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.other))) {
                new FetchOthersConstant(this, "other", img_edit.getTag(),
                        Utils.OTHER_REQUEST_CODE).execute();
            } else if (titleTextView.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.dada))) {
                new FetchOthersConstant(this, "dada", img_edit.getTag(),
                        Utils.DADA_REQUEST_CODE).execute();
            }
        } else {
            return;
        }
    }

    private void enableEditing(final EditText descView, String hint,
            final ImageView img_edit, boolean isFocused, final String key) {

        if (isFocused) {
            descView.setEnabled(true);
            descView.setSelection(descView.getText().length());
            img_edit.setVisibility(View.VISIBLE);
            img_edit.setTag("");
            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobjPro = new JSONObject();
                    JSONObject jobj = new JSONObject();
                    try {
                        jobjPro.put(key, descView.getText().toString());
                        jobj.put(Utils.PROFILE, jobjPro);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateDetails(jobjPro);
                    img_edit.setTag("");
                    img_edit.setVisibility(View.INVISIBLE);
                    hideSoftKeyboard(descView);
                    descView.clearFocus();
                    descView.setFocusableInTouchMode(false);
                    descView.setFocusable(false);
                }
            });
        } else {
            img_edit.setTag("");
            img_edit.setVisibility(View.INVISIBLE);
        }
        if (img_edit.getTag() == null || img_edit.getTag().equals("")) {
            img_edit.setVisibility(View.VISIBLE);
            img_edit.setTag("enabled");
            img_edit.setImageResource(R.drawable.accept_icon);
            descView.setFocusableInTouchMode(true);
            descView.setFocusable(true);
            descView.requestFocus();
            descView.setSelection(descView.getText().length());
        }
    }

    private void setDataOnFamilyMemberRecylerView(UserProfile userProfile) {

        if (userProfile.getProfile().getFamilyMembers() != null) {
            /*..................................parents block
            start.........................................*/
            //add father information
            if (userProfile.getProfile().getFamilyMembers().getFather() != null) {

                if (parentChildItemList.size() > 0) {
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
                } else {
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
                if (parentChildItemList.size() > 1) {
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
                } else {
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
                if (parentChildItemHeader.size() > 0) {
                    parentChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.father_text) + "-" +
                                    getResources().getString(R.string.mother_text),
                            parentChildItemList));

                    uppa = new UserProfileExpenadlbeAdapter(this,
                            parentChildItemHeader,
                            true);
                    uppa.expandParent(0);
                } else {
                    parentChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.father_text) + "-" +
                                    getResources().getString(R.string.mother_text),
                            parentChildItemList));
                    uppa = new UserProfileExpenadlbeAdapter(this,
                            parentChildItemHeader,
                            true);

                    parentsRecyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, parentsRecyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );
                }
                parentsRecyclerView.removeAllViews();
                parentsRecyclerView.setAdapter(uppa);

            }

            /*..................................parents block
            end.........................................*/



            /*..................................brothers block
            start.........................................*/

            //add brother information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfBrothers() > 0) {

                // familyMemberCounter = 0;
                for (int i = 0;
                        i < userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++) {
                    UserProfileChild upc = new UserProfileChild(
                            //getResources().getString(R.string.brother_text) + Utils
                            // .convertEnglishDigittoBangla(familyMemberCounter),
                            getResources().getString(R.string.brother_text),
                            checkNullField(
                                    userProfile.getProfile().getFamilyMembers().getBrothers().
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
                    if (index >= 0 && brothersChildItemList.size() > 0
                            && i < brothersChildItemList.size()) {
                        brothersChildItemList.set(index, upc);
                    } else {
                        brothersChildItemList.add(i, upc);
                    }
                }


            }

            //add brotherchildlist data to mainparentlist
            if (brothersChildItemList.size() > 0) {
//                brothersChildItemHeader.add(new UserProfileParent(
//                        getResources().getString(R.string.brother_text) + "("
//                                + Utils.convertEnglishDigittoBangla(brothersChildItemList.size
// ()) + ")"
//                        , brothersChildItemList));
//
//                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
//                        brothersChildItemHeader,
//                        true));
                if (brothersChildItemHeader.size() > 0) {
                    brothersChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.brother_text) + "("
                                    + Utils.convertEnglishDigittoBangla(
                                    brothersChildItemList.size()) + ")",
                            brothersChildItemList));

                    upb = new UserProfileExpenadlbeAdapter(this,
                            brothersChildItemHeader,
                            true);
                    upb.expandParent(0);
                } else {
                    brothersChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.brother_text) + "("
                                    + Utils.convertEnglishDigittoBangla(
                                    brothersChildItemList.size()) + ")",
                            brothersChildItemList));
                    upb = new UserProfileExpenadlbeAdapter(this,
                            brothersChildItemHeader,
                            true);

                    brotherRecyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, brotherRecyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );
                }
                brotherRecyclerView.removeAllViews();
                brotherRecyclerView.setAdapter(upb);

            } else {

                brothersChildItemList.add(
                        new UserProfileChild(getResources().getString(R.string.brother_text)
                                , getResources().getString(R.string.no_brother_text)));
                brothersChildItemHeader.add(
                        new UserProfileParent(getResources().getString(R.string.brother_text)
                                , brothersChildItemList));
                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                        brothersChildItemHeader,
                        true));
            }

            /*..................................brothers block
            end.........................................*/


            /*..................................sisters block
            start.........................................*/

            if (userProfile.getProfile().getFamilyMembers().getNumberOfSisters() > 0) {

                //familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getSisters().size();
                        i++) {

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
                    if (index >= 0 && sistersChildItemList.size() > 0
                            && i < sistersChildItemList.size()) {
                        sistersChildItemList.set(index, upc);
                    } else {
                        sistersChildItemList.add(i, upc);
                    }
                }

            }

            //add sisterchildlist data to mainparentlist
            if (sistersChildItemList.size() > 0) {
                if (sistersChildItemHeader.size() > 0) {
                    sistersChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.sister_text) + "("
                                    + Utils.convertEnglishDigittoBangla(sistersChildItemList.size())
                                    + ")",
                            sistersChildItemList));

                    ups = new UserProfileExpenadlbeAdapter(this,
                            sistersChildItemHeader,
                            true);
                    ups.expandParent(0);
                } else {
                    sistersChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.sister_text) + "("
                                    + Utils.convertEnglishDigittoBangla(sistersChildItemList.size())
                                    + ")",
                            sistersChildItemList));
                    ups = new UserProfileExpenadlbeAdapter(this,
                            sistersChildItemHeader,
                            true);

                    sisterRecyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, sisterRecyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );
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

            /*..................................sisters block
            end.........................................*/


            /*..................................childs block
            start.........................................*/


            //add child information
            if (userProfile.getProfile().getPersonalInformation().getMaritalStatus().
                    equals(getResources().getString(R.string.unmarried_text))) {

                childCardView.setVisibility(View.GONE);

            } else if (userProfile.getProfile().getPersonalInformation().getMaritalStatus().
                    equals(getResources().getString(R.string.married_text))) {

                if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                        userProfile.getProfile().getFamilyMembers().isChildLivesWithYou()) {

                    childsChildItemList.add(
                            new UserProfileChild(getResources().getString(R.string.child_text),
                                    Utils.convertEnglishDigittoBangla(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getNumberOfChild())
                                            + " জন সন্তান, সাথে থাকে"));

                } else if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                        !(userProfile.getProfile().getFamilyMembers().isChildLivesWithYou())) {

                    childsChildItemList.add(
                            new UserProfileChild(getResources().getString(R.string.child_text),
                                    Utils.convertEnglishDigittoBangla(
                                            userProfile.getProfile().getFamilyMembers()
                                                    .getNumberOfChild())
                                            + " জন সন্তান, সাথে থাকে না"));
                } else {
//                    Log.i("childstatus", "nothing found");
                }


                //add childlist data to mainparentlist
                if (childsChildItemList.size() > 0) {

                    childsChildItemHeader.add(
                            new UserProfileParent(getResources().getString(R.string.child_text)
                                    , childsChildItemList));

                    childRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                            childsChildItemHeader,
                            true));
                } else {
                    childsChildItemList.add(
                            new UserProfileChild(getResources().getString(R.string.child_text),
                                    getResources().getString(R.string.no_child_text)));
                    childsChildItemHeader.add(
                            new UserProfileParent(getResources().getString(R.string.child_text),
                                    childsChildItemList));

                    childRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(this,
                            childsChildItemHeader,
                            true));

                    childRecyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, childRecyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );

                }

            } else {
                childCardView.setVisibility(View.GONE);
            }




            /*..................................childs block
            end.........................................*/




            /*..................................other relative block
            start.........................................*/


            //add dada information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfDada() > 0) {
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getDadas().size();
                        i++) {

                    UserProfileChild upc = new UserProfileChild("দাদা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getDadas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getDadas().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getDadas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getDadas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getDadas().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getDadas().
                            get(i).getId());
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }
            }


            //add kaka information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfKaka() > 0) {
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKakas().size();
                        i++) {

                    UserProfileChild upc = new UserProfileChild("চাচা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getKakas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getKakas().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getKakas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKakas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKakas().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getKakas().
                            get(i).getId());
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }
            }

            //add mama information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfMama() > 0) {

                // familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getMamas().size();
                        i++) {

                    UserProfileChild upc = new UserProfileChild("মামা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getMamas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getMamas().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getMamas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getMamas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getMamas().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getMamas().
                            get(i).getId());
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }
            }


            //add fufa information


            if (userProfile.getProfile().getFamilyMembers().getNumberOfFufa() > 0) {

                //familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getFufas().size();
                        i++) {
                    UserProfileChild upc = new UserProfileChild("ফুপা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getFufas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getFufas().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getFufas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getFufas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getFufas().get(i).getInstitute())

                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getFufas().
                            get(i).getId());
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }


            }


            //add nana information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfNana() > 0) {

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getNanas().size();
                        i++) {

                    UserProfileChild upc = new UserProfileChild("নানা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getNanas().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getNanas().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getNanas().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getNanas().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getNanas().get(i).getInstitute())
                    );

                    upc.setId(userProfile.getProfile().getFamilyMembers().getNanas().
                            get(i).getId());
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }
            }


            //add khalu information

            if (userProfile.getProfile().getFamilyMembers().getNumberOfKhalu() > 0) {

                // familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKhalus().size();
                        i++) {

                    UserProfileChild upc = new UserProfileChild("খালু",
                            checkNullField(userProfile.getProfile().getFamilyMembers().getKhalus().
                                    get(i).getName())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getKhalus().get(i).getRelation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().
                                    getKhalus().get(i).getOccupation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKhalus().get(i).getDesignation())
                                    + checkNullField(userProfile.getProfile().getFamilyMembers()
                                    .getKhalus().get(i).getInstitute())

                    );
//                    otherRelativeChildItemList.add(upc);

                    upc.setId(userProfile.getProfile().getFamilyMembers().getKhalus().
                            get(i).getId());
//                    if(brothersChildItemList.contains(upc)){
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }
            }

            if (userProfile.getProfile().getFamilyMembers().getNumberOfOthers() > 0) {
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getOthers().size();
                        i++) {
                    UserProfileChild upc = new UserProfileChild(
                            getResources().getString(R.string.other),
                            checkNullField(userProfile.getProfile().getFamilyMembers().getOthers().
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
                    int index = getRelativeChildOtherIndex(upc.getId());
                    if (index >= 0 && otherRelativeChildItemList.size() > 0) {
                        otherRelativeChildItemList.set(index, upc);
                    } else {
                        otherRelativeChildItemList.add(upc);
                    }
                }

                if (otherRelativeChildItemList.size() > 0) {
                    if (otherRelativeChildItemHeader.size() > 0) {
                        otherRelativeChildItemHeader.set(0, new UserProfileParent(
                                getResources().getString(R.string.other) + "("
                                        + Utils.convertEnglishDigittoBangla(
                                        otherRelativeChildItemList.size()) + ")",
                                otherRelativeChildItemList));

                        ups = new UserProfileExpenadlbeAdapter(this,
                                otherRelativeChildItemHeader,
                                true);
                        ups.expandParent(0);
                    } else {
                        otherRelativeChildItemHeader.add(0, new UserProfileParent(
                                getResources().getString(R.string.other) + "("
                                        + Utils.convertEnglishDigittoBangla(
                                        otherRelativeChildItemList.size()) + ")",
                                otherRelativeChildItemList));
                        ups = new UserProfileExpenadlbeAdapter(this,
                                otherRelativeChildItemHeader,
                                true);

                        otherRelativeInfoRecyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(this, otherRelativeInfoRecyclerView,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                updateAction(view, position);
                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {
                                                return;
                                            }
                                        })
                        );

                    }
                    otherRelativeInfoRecyclerView.removeAllViews();
                    otherRelativeInfoRecyclerView.setAdapter(ups);

                } else {
                    otherRelativeCardview.setVisibility(View.GONE);
                }
            }

            /*..................................other relative block
            end.........................................*/
        }
    }

    private void setDataOnCommunicationRecylerView(UserProfile userProfile) {

        if (userProfile.getProfile().getAddress() != null) {
            /*..................................parents block
            start.........................................*/
            //add present address information
            if (userProfile.getProfile().getAddress().getPresentAddress() != null) {

                if (communicationChildItemList.size() > 0) {
                    communicationChildItemList.set(0,
                            new UserProfileChild(
                                    getResources().getString(R.string.present_address_text),
                                    checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPresentAddress()
                                                    .getAddress())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPresentAddress().getDistrict())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPresentAddress().getCountry())
                            ));
                } else {
                    communicationChildItemList.add(0,
                            new UserProfileChild(
                                    getResources().getString(R.string.present_address_text),
                                    checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPresentAddress()
                                                    .getAddress())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPresentAddress().getDistrict())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPresentAddress().getCountry())
                            ));
                }

            }


            //add permanent address information
            if (userProfile.getProfile().getAddress().getPermanentAddress() != null) {
                if (communicationChildItemList.size() > 1) {
                    communicationChildItemList.set(1,
                            new UserProfileChild(
                                    getResources().getString(R.string.permanent_address_text),
                                    checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPermanentAddress()
                                                    .getAddress())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPermanentAddress().getDistrict())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPermanentAddress().getCountry())
                            ));
                } else {
                    communicationChildItemList.add(1,
                            new UserProfileChild(
                                    getResources().getString(R.string.permanent_address_text),
                                    checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPermanentAddress()
                                                    .getAddress())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPermanentAddress().getDistrict())
                                            + checkNullField(
                                            userProfile.getProfile().getAddress()
                                                    .getPermanentAddress().getCountry())
                            ));
                }

            }


            //add communicationchildlist data to mainparentlist
            if (communicationChildItemList.size() > 0) {
                if (communicationChildItemHeader.size() > 0) {
                    communicationChildItemHeader.set(0, new UserProfileParent(
                            getResources().getString(R.string.communication_text),
                            communicationChildItemList));

                    upadd = new UserProfileExpenadlbeAdapter(this,
                            communicationChildItemHeader,
                            true);
                    upadd.expandParent(0);
                } else {
                    communicationChildItemHeader.add(0, new UserProfileParent(
                            getResources().getString(R.string.communication_text),
                            communicationChildItemList));
                    upadd = new UserProfileExpenadlbeAdapter(this,
                            communicationChildItemHeader,
                            true);

                    communicationRecyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(this, communicationRecyclerView,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            updateAction(view, position);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            return;
                                        }
                                    })
                    );
                }
                communicationRecyclerView.removeAllViews();
                communicationRecyclerView.setAdapter(upadd);

            }
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

        userProfileDescriptionCardView = findViewById(R.id.userProfileDescriptionCardView);
        userProfileDescriptionText = (EditText) findViewById(R.id.userProfileDescriptionText);
//        hideSoftKeyboard(userProfileDescriptionText);
        img_edit_details = findViewById(R.id.img_edit_details);
        img_edit_accept = findViewById(R.id.img_accept);


//        userProfileDescriptionCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String hint = "আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্র-পক্ষ আগ্রহী হয়।";
//                if (sharePref.get_data("gender").equals("male")) {
//                    hint = "আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্রী-পক্ষ আগ্রহী হয়।";
//                }
//                userProfileDescriptionText.setHint(hint);
//                if (img_edit_details.getTag() == null) {
//                    enableEditing(userProfileDescriptionText, img_edit_details, true);
//                }
//
////                getUpdatedText("আপনার সম্পর্কে লিখুন", userProfileDescriptionText.getText()
//// .toString(), hint);
//            }
//        });
//
//        userProfileDescriptionText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean focus) {
//                System.out.println(focus);
//            }
//        });

        /*userProfileDescriptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hint = "আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্র-পক্ষ আগ্রহী হয়।";
                if (sharePref.get_data("gender").equals("male")) {
                    hint = "আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্রী-পক্ষ আগ্রহী হয়।";
                }
                userProfileDescriptionText.setHint(hint);
                if (img_edit_details.getTag() == null) {
                    enableEditing(userProfileDescriptionText, img_edit_details, true);
                }

//                getUpdatedText("আপনার সম্পর্কে লিখুন", userProfileDescriptionText.getText()
// .toString(), hint);
            }
        });*/

        userProfileDescriptionText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String hint = "আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্র-পক্ষ আগ্রহী হয়।";
                if (sharePref.get_data("gender").equals("male")) {
                    hint = "আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্রী-পক্ষ আগ্রহী হয়।";
                }
                userProfileDescriptionText.setHint(hint);
                if (img_edit_details.getTag() == null) {
                    enableEditing(userProfileDescriptionText, img_edit_details, true);
                }
                return false;
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

        otherRelativeInfoRecyclerView = (RecyclerView) findViewById(
                R.id.other_relative_recylerView);
        otherRelativeInfoRecyclerView.setLayoutManager(
                new CustomStopScrollingRecylerLayoutManager(this));

        communicationRecyclerView = (RecyclerView) findViewById(R.id.communication_recylerView);
        communicationRecyclerView.setLayoutManager(
                new CustomStopScrollingRecylerLayoutManager(this));

        otherInformationRecyclerView = (RecyclerView) findViewById(R.id.other_info_recylerView);
        otherInformationRecyclerView.setLayoutManager(
                new CustomStopScrollingRecylerLayoutManager(this));
        otherInformationRecyclerView.getParent().requestChildFocus(otherInformationRecyclerView,
                otherInformationRecyclerView);
    }

    private void enableEditing(final EditText descView, final ImageView img_edit,
            boolean isFocused) {
//        descView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean isFocused) {
        if (isFocused) {
//                    img_edit.setTag("enabled");
//                    img_edit.setImageResource(R.drawable.accept_icon);

            descView.setEnabled(true);
//                    descView.requestFocus();
//                    descView.setSelection(descView.getText().length());
            img_edit_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobjPro = new JSONObject();
                    JSONObject jobj = new JSONObject();
                    try {
                        jobj.put(Utils.ABOUT_YOURSELF,
                                userProfileDescriptionText.getText().toString());
                        jobjPro.put(Utils.PROFILE, jobj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateDetails(jobjPro);
                    img_edit_accept.setTag("");
                    img_edit_accept.setVisibility(View.GONE);
                    hideSoftKeyboard(descView);
                    descView.clearFocus();
                    descView.setFocusableInTouchMode(false);
                    descView.setFocusable(false);
//                            descView.setFocusable(false);
//                            descView.clearFocus();
                }
            });
        } else {
            img_edit_accept.setTag("");
//                    hideSoftKeyboard(descView);
        }
        if (img_edit_accept.getTag() == null || img_edit_accept.getTag().equals("")) {
            img_edit_accept.setVisibility(View.VISIBLE);
            img_edit_accept.setTag("enabled");
            img_edit_accept.setImageResource(R.drawable.accept_icon);
//            descView.setEnabled(true);
            descView.setFocusableInTouchMode(true);
            descView.setFocusable(true);
            descView.requestFocus();
//            descView.setSelection(descView.getText().length());
        }
//        else {
//            img_edit_accept.setTag("");
//            img_edit_accept.setVisibility(View.GONE);
//            descView.setEnabled(false);
//            hideSoftKeyboard(descView);
//            descView.clearFocus();
//        }
    }

    private void hideSoftKeyboard(EditText view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public void backBtnAction(View v) {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
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

                    updateDetails(Utils.PROFILE, Utils.DATE_OF_BIRTH,
                            convertedEnglishDateFromBanglaDate + "/"
                                    + convertedEnglishMonthFromBanglaMonth + "/"
                                    + convertedEglishYearFromBanglaYear);
                    userProfile.getProfile().getPersonalInformation().setAge(
                            Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(
                                    convertedEglishYearFromBanglaYear));
                    setDataOnPersonalInfoRecylerView(userProfile, 0);
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
                            for (int j = 0; j < 12; j++) {
                                heightArray.add(Utils.convertEnglishDigittoBangla(i) + " ফিট "
                                        + Utils.convertEnglishDigittoBangla(j) + " ইঞ্চি");
                            }
                        }

                        heightName = heightArray.toArray(heightName);

                        userProfile.getProfile().getPersonalInformation().setHeightFt(feetValue);
                        userProfile.getProfile().getPersonalInformation().setHeightInc(inchValue);
                        JSONObject jobjPro = new JSONObject();
                        JSONObject jobj = new JSONObject();
                        try {
                            jobj.put(Utils.HEIGHT_FEET, feetValue);
                            jobj.put(Utils.HEIGHT_INCH, inchValue);
                            jobjPro.put(Utils.PROFILE, jobj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateDetails(jobjPro);
                        setDataOnPersonalInfoRecylerView(userProfile, 1);
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

                JSONObject jobjPro = new JSONObject();
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put(Utils.RELIGION, religionValue);
                    jobj.put(Utils.CAST, castValue);
                    jobj.put(Utils.OTHER_RELIGION, otherReligion);
                    jobj.put(Utils.OTHER_CAST, otherCast);
                    jobjPro.put(Utils.PROFILE, jobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateDetails(jobjPro);

                setDataOnPersonalInfoRecylerView(userProfile, 2);
            } else if (requestCode == 5) {
                userProfile.getProfile().getPersonalInformation().setSkinColor(
                        data.getStringExtra("skin_color_data"));
                JSONObject jobjPro = new JSONObject();
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put(Utils.SKIN_COLOR, data.getIntExtra("skin_color_value", 0));
                    jobjPro.put(Utils.PROFILE, jobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateDetails(jobjPro);
                setDataOnPersonalInfoRecylerView(userProfile, 6);
            } else if (requestCode == 6) {
                if (data != null && data.hasExtra("body_value")) {
                    if (data.getIntExtra("body_value", 0) > 0) {
                        int weight = data.getIntExtra("body_value", 0);
                        userProfile.getProfile().getPersonalInformation().setWeight(
                                Utils.convertEnglishDigittoBangla(weight + 29) + " কেজি");
                        JSONObject jobjPro = new JSONObject();
                        JSONObject jobj = new JSONObject();
                        try {
                            jobj.put(Utils.WEIGHT, data.getIntExtra("body_value", 0));
                            jobjPro.put(Utils.PROFILE, jobj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateDetails(jobjPro);
                        setDataOnPersonalInfoRecylerView(userProfile, 7);
                    }
                }
            } else if (requestCode == 7) {
                if (data != null && data.hasExtra("marital_status_data")) {
                    if (data.hasExtra("marital_status_data")) {
                        userProfile.getProfile().getPersonalInformation().setMaritalStatus(
                                data.getStringExtra("marital_status_data"));
                        updateDetails(Utils.PROFILE, Utils.MARITAL_STATUS,
                                data.getIntExtra("marital_status_value", 0) + "");
                        setDataOnPersonalInfoRecylerView(userProfile, 8);
                    }
                }
            } else if (requestCode == 8) {
                if (data != null && data.hasExtra("blood_group_data")) {
                    if (data.hasExtra("blood_group_data")) {
                        userProfile.getProfile().getPersonalInformation().setBloodGroup(
                                data.getStringExtra("blood_group_data"));
                        updateDetails(Utils.PROFILE, Utils.BLOOD_GROUP,
                                data.getStringExtra("blood_group_value"));
                        setDataOnPersonalInfoRecylerView(userProfile, 9);
                    }
                }
            } else if (requestCode == 9) {
                if (data != null && data.hasExtra("smoke_data")) {
                    if (data.hasExtra("smoke_data")) {
                        userProfile.getProfile().getPersonalInformation().setSmoking(
                                data.getStringExtra("smoke_data"));
                        updateDetails(Utils.PROFILE, Utils.SMOKE,
                                data.getIntExtra("smoke_value", 0) + "");
                        setDataOnPersonalInfoRecylerView(userProfile, 11);
                    }
                }
            } else if (requestCode == 10) {
                if (data != null && data.hasExtra("disable_data")) {
                    if (data.hasExtra("disable_data")) {
                        userProfile.getProfile().getPersonalInformation().setDisabilities(
                                data.getStringExtra("disable_data"));
                        userProfile.getProfile().getPersonalInformation()
                                .setDisabilitiesDescription(
                                        data.getStringExtra("disable_desc_data"));
                        JSONObject jobjPro = new JSONObject();
                        JSONObject jobj = new JSONObject();
                        try {
                            jobj.put(Utils.DISABILITY, data.getIntExtra("disable_value", 0));
                            jobj.put(Utils.DISABILITY_DESC,
                                    data.getStringExtra("disable_desc_data"));
                            jobjPro.put(Utils.PROFILE, jobj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateDetails(jobjPro);
                        setDataOnPersonalInfoRecylerView(userProfile, 10);
                    }
                }
            } else if (requestCode == 11) {
                if (data != null && data.hasExtra("professional_group_data")) {
                    if (data.hasExtra("professional_group_data")) {
                        userProfile.getProfile().getProfession().setProfessionalGroup(
                                data.getStringExtra("professional_group_data"));
                        updateDetails(Utils.PROFILE, Utils.PROFESSIONAL_GROUP,
                                data.getStringExtra("professional_group_value"));
                        setDataOnProfessionRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 12) {
                if (data != null && data.hasExtra("profession_data")) {
                    if (data.hasExtra("profession_data")) {
                        userProfile.getProfile().getProfession().setOccupation(
                                data.getStringExtra("profession_data"));
                        updateDetails(Utils.PROFILE, Utils.OCCUPATION,
                                data.getStringExtra("profession_value"));
                        setDataOnProfessionRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 13) {
                if (data != null && data.hasExtra("fasting_data")) {
                    if (data.hasExtra("fasting_data")) {
                        userProfile.getProfile().getOtherInformation().setFasting(
                                data.getStringExtra("fasting_data"));
                        updateDetails(Utils.PROFILE, Utils.FAST,
                                data.getStringExtra("fasting_value"));
                        setDataOnOtherInfoRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 14) {
                if (data != null && data.hasExtra("prayer_data")) {
                    if (data.hasExtra("prayer_data")) {
                        userProfile.getProfile().getOtherInformation().setPrayer(
                                data.getStringExtra("prayer_data"));
                        updateDetails(Utils.PROFILE, Utils.PRAYER,
                                data.getStringExtra("prayer_value"));
                        setDataOnOtherInfoRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 15) {
                if (data != null && data.hasExtra("own_house_data")) {
                    if (data.hasExtra("own_house_data")) {
                        userProfile.getProfile().getProfileLivingIn().setOwnHouse(
                                data.getStringExtra("own_house_data"));
                        updateDetails(Utils.PROFILE, Utils.OWN_HOUSE,
                                data.getStringExtra("own_house_value"));
                        setDataOnOtherInfoRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 16) {
                if (data != null && data.hasExtra("education_data")) {
                    if (data.hasExtra("education_data")) {
                        EducationInformation ei =
                                userProfile.getProfile().getEducationInformation().get(0);
                        ei.setName(data.getStringExtra("education_data"));
                        ei.setSubject(data.getStringExtra("subject"));
                        ei.setInstitution(data.getStringExtra("institute"));
                        JSONObject jobj_pro = new JSONObject();
                        JSONObject jobj_edu = new JSONObject();
                        JSONObject jobj_no = new JSONObject();
                        JSONObject jobj_first = new JSONObject();
                        try {
                            jobj_no.put(Utils.NAME, data.getStringExtra("education_value"));
                            jobj_no.put(Utils.SUBJECT, data.getStringExtra("subject"));
                            jobj_no.put(Utils.INSTITUTION, data.getStringExtra("institute"));
                            jobj_no.put(Utils.YEAR, "2001");
                            jobj_no.put(Utils.ID, ei.getId() + "");
                            jobj_first.put("0", jobj_no);
                            jobj_edu.put(Utils.EDUCATION_ATTR, jobj_first);
                            jobj_pro.put(Utils.PROFILE, jobj_edu);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateDetails(jobj_pro);
                        userProfile.getProfile().getEducationInformation().set(0, ei);
                        setDataOnEducationalRecylerView(userProfile);
                    }
                }
            } else if (requestCode == 17) {
                if (data != null && data.hasExtra("profession_data")) {
                    if (data.hasExtra("profession_data")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setOccupation(
                                data.getStringExtra("profession_data"));
                    }
                    if (data.hasExtra("professional_group_data") && data.hasExtra(
                            "professional_group_data")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setOccupation(
                                userProfile.getProfile().getFamilyMembers().getFather()
                                        .getOccupation()
                                        + (data.getStringExtra("professional_group_data").length()
                                        > 0 ? " (" + data.getStringExtra("professional_group_data")
                                        + ")" : ""));
                    }
                    if (data.hasExtra("name")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setName(
                                data.getStringExtra("name"));
                    }
                    if (data.hasExtra("designation")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setDesignation(
                                data.getStringExtra("designation"));
                    }
                    if (data.hasExtra("institute")) {
                        userProfile.getProfile().getFamilyMembers().getFather().setInstitute(
                                data.getStringExtra("institute"));
                    }

                    Father father = userProfile.getProfile().getFamilyMembers().getFather();
                    JSONObject jobj_pro = new JSONObject();
                    JSONArray jobj_family_members = new JSONArray();
                    JSONObject jobj_family_member = new JSONObject();
                    JSONObject jobj_father = new JSONObject();
                    try {
                        jobj_father.put(Utils.OCCUPATION, data.getStringExtra("profession_value"));
                        jobj_father.put(Utils.PROFESSIONAL_GROUP,
                                data.getStringExtra("professional_group_value"));
                        jobj_father.put(Utils.NAME, data.getStringExtra("name"));
                        jobj_father.put(Utils.DESIGNATION, data.getStringExtra("designation"));
                        jobj_father.put(Utils.INSTITUTE, data.getStringExtra("institute"));
                        jobj_father.put(Utils.ID, father.getId());
                        jobj_father.put(Utils.RELATION,
                                father.getRelationId());  //father relation value is 1
                        jobj_family_members.put(jobj_father);
                        jobj_family_member.put(Utils.FAMILY_MEMBER_ATTRIBUTE, jobj_family_members);
                        jobj_pro.put(Utils.PROFILE, jobj_family_member);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateDetails(jobj_pro);
                    setDataOnFamilyMemberRecylerView(userProfile);
                }
            } else if (requestCode == 18) {
                if (data != null && data.hasExtra("profession_data")) {
                    if (data.hasExtra("profession_data")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setOccupation(
                                data.getStringExtra("profession_data"));
                    }
                    if (data.hasExtra("professional_group_data")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setOccupation(
                                userProfile.getProfile().getFamilyMembers().getMother()
                                        .getOccupation()
                                        + (data.getStringExtra("professional_group_data").length()
                                        > 0 ? " (" + data.getStringExtra("professional_group_data")
                                        + ")" : ""));
                    }
                    if (data.hasExtra("name")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setName(
                                data.getStringExtra("name"));
                    }
                    if (data.hasExtra("designation")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setDesignation(
                                data.getStringExtra("designation"));
                    }
                    if (data.hasExtra("institute")) {
                        userProfile.getProfile().getFamilyMembers().getMother().setInstitute(
                                data.getStringExtra("institute"));
                    }

                    Mother mother = userProfile.getProfile().getFamilyMembers().getMother();
                    JSONObject jobj_pro = new JSONObject();
                    JSONArray jobj_family_members = new JSONArray();
                    JSONObject jobj_family_member = new JSONObject();
                    JSONObject jobj_mother = new JSONObject();
                    try {
                        jobj_mother.put(Utils.OCCUPATION, data.getStringExtra("profession_value"));
                        jobj_mother.put(Utils.PROFESSIONAL_GROUP,
                                data.getStringExtra("professional_group_value"));
                        jobj_mother.put(Utils.NAME, data.getStringExtra("name"));
                        jobj_mother.put(Utils.DESIGNATION, data.getStringExtra("designation"));
                        jobj_mother.put(Utils.INSTITUTE, data.getStringExtra("institute"));
                        jobj_mother.put(Utils.ID, mother.getId());
                        jobj_mother.put(Utils.RELATION,
                                mother.getRelationId());  //mother relation value is 2
                        jobj_family_members.put(jobj_mother);
                        jobj_family_member.put(Utils.FAMILY_MEMBER_ATTRIBUTE, jobj_family_members);
                        jobj_pro.put(Utils.PROFILE, jobj_family_member);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateDetails(jobj_pro);
                    setDataOnFamilyMemberRecylerView(userProfile);
                }
            } else if (requestCode == 19) {
                if (data != null) {

                    if (data.hasExtra("name")) {
                        int id = data.getIntExtra("id", 0);
                        int broIndex = getBroIndex(id);
                        if (broIndex >= 0) {
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setName(data.getStringExtra("name"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setAge(Integer.parseInt(data.getStringExtra("age")));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setOccupation(data.getStringExtra("profession_data"));
//                            userProfile.getProfile().getFamilyMembers().getBrothers().get
// (broIndex).setRelationId(data.getIntExtra("relation_id", 0));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setDesignation(data.getStringExtra("designation"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setInstitute(data.getStringExtra("institute"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setMaritalStatus(
                                    data.getStringExtra("marital_status_data"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setSpouseName(data.getStringExtra("name_spouse"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setSpouseOccupation(
                                    data.getStringExtra("profession_spouse_data"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setSpouseDesignation(
                                    data.getStringExtra("designation_spouse"));
                            userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                    broIndex).setSpouseInstitue(
                                    data.getStringExtra("institute_spouse"));
                        }

//                        "siblings_attributes": [
//                        {
//                            "sibling_type": "2",
//                                "name": "SWDE",
//                                "age": "3",
//                                "occupation": "5",
//                                "professional_group": "102",
//                                "designation": "ASDFG",
//                                "institute": "QWER",
//                                "marital_status": "1",
//                                "spouse": "",
//                                "s_occupation": "",
//                                "s_professional_group": "",
//                                "s_designation": "",
//                                "s_institute": "",
//                                "id": ""
//                        },
                        Brother brother =
                                userProfile.getProfile().getFamilyMembers().getBrothers().get(
                                        broIndex);
                        JSONObject jobj_pro = new JSONObject();
                        JSONArray jobj_siblings = new JSONArray();
                        JSONObject jobj_sibling = new JSONObject();
                        JSONObject jobj_brother = new JSONObject();
                        try {
                            jobj_brother.put(Utils.OCCUPATION,
                                    data.getStringExtra("profession_value"));
                            jobj_brother.put(Utils.PROFESSIONAL_GROUP,
                                    data.getStringExtra("professional_group_value"));
                            jobj_brother.put(Utils.AGE, data.getStringExtra("age"));
                            jobj_brother.put(Utils.NAME, data.getStringExtra("name"));
                            jobj_brother.put(Utils.OCCUPATION,
                                    data.getStringExtra("profession_value"));
                            jobj_brother.put(Utils.DESIGNATION, data.getStringExtra("designation"));
                            jobj_brother.put(Utils.INSTITUTE, data.getStringExtra("institute"));
                            jobj_brother.put(Utils.MARITAL_STATUS,
                                    data.getIntExtra("marital_status_value", 0));
                            jobj_brother.put(Utils.SPOUSE, data.getStringExtra("name_spouse"));
                            jobj_brother.put(Utils.SPOUSE_OCCUPATION,
                                    data.getStringExtra("profession_spouse_value"));
                            jobj_brother.put(Utils.SPOUSE_PROFESSIONAL_GROUP,
                                    data.getStringExtra("professional_group_spouse_value"));
                            jobj_brother.put(Utils.SPOUSE_DESIGNTION,
                                    data.getStringExtra("designation_spouse"));
                            jobj_brother.put(Utils.SPOUSE_INSTITUTE,
                                    data.getStringExtra("institute_spouse"));
                            jobj_brother.put(Utils.ID, brother.getId());
                            jobj_brother.put(Utils.SIBLING_TYPE,
                                    brother.getRelationId());  //brother sibling type value is 1
                            jobj_siblings.put(jobj_brother);
                            jobj_sibling.put(Utils.SIBLING_ATTR, jobj_siblings);
                            jobj_pro.put(Utils.PROFILE, jobj_sibling);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jobj_pro.toString());
                        updateDetails(jobj_pro);
                    }

                }
                setDataOnFamilyMemberRecylerView(userProfile);
            } else if (requestCode == 20) {
                if (data != null) {

                    if (data.hasExtra("name")) {
                        int id = data.getIntExtra("id", 0);
                        int sisIndex = getSisIndex(id);
                        if (sisIndex >= 0) {
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    sisIndex).setName(data.getStringExtra("name"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    sisIndex).setAge(data.getIntExtra("age", 0));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    sisIndex).setOccupation(data.getStringExtra("profession_data"));
//                            userProfile.getProfile().getFamilyMembers().getBrothers().get
// (broIndex).setProfessionalGroupData(data.getStringExtra("professional_group_brother_data"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    sisIndex).setDesignation(data.getStringExtra("designation"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    sisIndex).setInstitute(data.getStringExtra("institute"));
                            userProfile.getProfile().getFamilyMembers().getSisters().get(
                                    sisIndex).setMaritalStatus(
                                    data.getStringExtra("marital_status_data"));
                        }
                        Sister sister =
                                userProfile.getProfile().getFamilyMembers().getSisters().get(
                                        sisIndex);
                        JSONObject jobj_pro = new JSONObject();
                        JSONArray jobj_siblings = new JSONArray();
                        JSONObject jobj_sibling = new JSONObject();
                        JSONObject jobj_sister = new JSONObject();
                        try {
                            jobj_sister.put(Utils.OCCUPATION,
                                    data.getStringExtra("profession_value"));
                            jobj_sister.put(Utils.PROFESSIONAL_GROUP,
                                    data.getStringExtra("professional_group_value"));
                            jobj_sister.put(Utils.AGE, data.getStringExtra("age"));
                            jobj_sister.put(Utils.NAME, data.getStringExtra("name"));
                            jobj_sister.put(Utils.OCCUPATION,
                                    data.getStringExtra("profession_value"));
                            jobj_sister.put(Utils.DESIGNATION, data.getStringExtra("designation"));
                            jobj_sister.put(Utils.INSTITUTE, data.getStringExtra("institute"));
                            jobj_sister.put(Utils.MARITAL_STATUS,
                                    data.getIntExtra("marital_status_value", 0));
                            jobj_sister.put(Utils.SPOUSE, data.getStringExtra("name_spouse"));
                            jobj_sister.put(Utils.SPOUSE_OCCUPATION,
                                    data.getStringExtra("profession_spouse_value"));
                            jobj_sister.put(Utils.SPOUSE_PROFESSIONAL_GROUP,
                                    data.getStringExtra("professional_group_spouse_value"));
                            jobj_sister.put(Utils.SPOUSE_DESIGNTION,
                                    data.getStringExtra("designation_spouse"));
                            jobj_sister.put(Utils.SPOUSE_INSTITUTE,
                                    data.getStringExtra("institute_spouse"));
                            jobj_sister.put(Utils.ID, sister.getId());
                            jobj_sister.put(Utils.SIBLING_TYPE,
                                    sister.getRelationId());  //brother sibling type value is 1
                            jobj_siblings.put(jobj_sister);
                            jobj_sibling.put(Utils.SIBLING_ATTR, jobj_siblings);
                            jobj_pro.put(Utils.PROFILE, jobj_sibling);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jobj_pro.toString());
                        updateDetails(jobj_pro);
                    }
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            } else if (requestCode == 21) {
                if (data != null) {

                    if (data.hasExtra("name")) {
                        int id = data.getIntExtra("id", 0);
                        int otherIndex = getOtherIndex(id);
                        if (otherIndex >= 0) {
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setName(data.getStringExtra("name"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setRelation(data.getStringExtra("relation"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setOccupation(
                                    data.getStringExtra("profession_data"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setRelation(data.getStringExtra("relation_data"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setDesignation(data.getStringExtra("designation"));
                            userProfile.getProfile().getFamilyMembers().getOthers().get(
                                    otherIndex).setInstitute(data.getStringExtra("institute"));
                        }
                        Other other = userProfile.getProfile().getFamilyMembers().getOthers().get(
                                otherIndex);
                        JSONObject jobj_pro = new JSONObject();
                        JSONArray jobj_others = new JSONArray();
                        JSONObject jobj_other_ = new JSONObject();
                        JSONObject jobj_other = new JSONObject();
                        try {
                            jobj_other.put(Utils.PROFESSIONAL_GROUP,
                                    data.getIntExtra("professional_group_value", 0));
                            jobj_other.put(Utils.AGE, data.getStringExtra("age"));
                            jobj_other.put(Utils.NAME, data.getStringExtra("name"));
                            jobj_other.put(Utils.OCCUPATION,
                                    data.getIntExtra("profession_value", 0));
                            jobj_other.put(Utils.DESIGNATION, data.getStringExtra("designation"));
                            jobj_other.put(Utils.INSTITUTE, data.getStringExtra("institute"));
                            jobj_other.put(Utils.ID, other.getId());
                            jobj_other.put(Utils.RELATION,
                                    other.getRelation());  //brother sibling type value is 1
                            jobj_others.put(jobj_other);
                            jobj_other_.put(Utils.FAMILY_MEMBER_ATTRIBUTE, jobj_others);
                            jobj_pro.put(Utils.PROFILE, jobj_other_);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jobj_pro.toString());
                        updateDetails(jobj_pro);
                    }
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            } else if (requestCode == 22) {
                if (data != null) {

                    if (data.hasExtra("name")) {
                        int id = data.getIntExtra("id", 0);
                        int dadaIndex = getDadaIndex(id);
                        if (dadaIndex >= 0) {
                            userProfile.getProfile().getFamilyMembers().getDadas().get(
                                    dadaIndex).setName(data.getStringExtra("name"));
                            userProfile.getProfile().getFamilyMembers().getDadas().get(
                                    dadaIndex).setRelation(data.getStringExtra("relation"));
                            userProfile.getProfile().getFamilyMembers().getDadas().get(
                                    dadaIndex).setOccupation(
                                    data.getStringExtra("profession_data"));
                            userProfile.getProfile().getFamilyMembers().getDadas().get(
                                    dadaIndex).setRelation(data.getStringExtra("relation_data"));
                            userProfile.getProfile().getFamilyMembers().getDadas().get(
                                    dadaIndex).setDesignation(data.getStringExtra("designation"));
                            userProfile.getProfile().getFamilyMembers().getDadas().get(
                                    dadaIndex).setInstitute(data.getStringExtra("institute"));
                        }
                        Dada dada = userProfile.getProfile().getFamilyMembers().getDadas().get(
                                dadaIndex);
                        JSONObject jobj_pro = new JSONObject();
                        JSONArray jobj_others = new JSONArray();
                        JSONObject jobj_other_ = new JSONObject();
                        JSONObject jobj_other = new JSONObject();
                        try {
                            jobj_other.put(Utils.PROFESSIONAL_GROUP,
                                    data.getIntExtra("professional_group_value", 0));
                            jobj_other.put(Utils.AGE, data.getStringExtra("age"));
                            jobj_other.put(Utils.NAME, data.getStringExtra("name"));
                            jobj_other.put(Utils.OCCUPATION,
                                    data.getIntExtra("profession_value", 0));
                            jobj_other.put(Utils.DESIGNATION, data.getStringExtra("designation"));
                            jobj_other.put(Utils.INSTITUTE, data.getStringExtra("institute"));
                            jobj_other.put(Utils.ID, dada.getId());
                            jobj_other.put(Utils.RELATION, data.getIntExtra("relation_value",
                                    0));  //brother sibling type value is 1
                            jobj_others.put(jobj_other);
                            jobj_other_.put(Utils.FAMILY_MEMBER_ATTRIBUTE, jobj_others);
                            jobj_pro.put(Utils.PROFILE, jobj_other_);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jobj_pro.toString());
                        updateDetails(jobj_pro);
                    }
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            } else if (requestCode == 23 || requestCode == 24 || requestCode == 25
                    || requestCode == 26) {
                if (data != null) {
                    if (data.hasExtra("home_town_data")) {
                        userProfile.getProfile().getProfileLivingIn().setLocation(
                                data.getStringExtra("home_town_value"));
                        userProfile.getProfile().getAddress().getPresentAddress().setAddress(
                                data.getStringExtra("present_address"));
                        userProfile.getProfile().getAddress().getPresentAddress().setCountry(
                                data.getStringExtra("present_country_data"));
                        if (data.getStringExtra("present_country_data").equalsIgnoreCase(
                                "bangladesh")) {
                            userProfile.getProfile().getAddress().getPresentAddress().setDistrict(
                                    data.getStringExtra("present_district_data"));
                        } else {
                            userProfile.getProfile().getAddress().getPresentAddress().setDistrict(
                                    "");
                        }
                        userProfile.getProfile().getAddress().getPermanentAddress().setAddress(
                                data.getStringExtra("permanent_address"));
                        userProfile.getProfile().getAddress().getPermanentAddress().setCountry(
                                data.getStringExtra("permanent_country_data"));
                        if (data.getStringExtra("permanent_country_data").equalsIgnoreCase(
                                "bangladesh")) {

                            userProfile.getProfile().getAddress().getPermanentAddress().setDistrict(
                                    data.getStringExtra("permanent_district_data"));
                        } else {
                            userProfile.getProfile().getAddress().getPermanentAddress().setDistrict(
                                    "");
                        }


                        if (data.getStringExtra("residence").equalsIgnoreCase("BD")) {
                            userProfile.getProfile().getProfileLivingIn().setCountry(
                                    getString(R.string.bangladesh));
                        } else {
                            userProfile.getProfile().getProfileLivingIn().setCountry(
                                    data.getStringExtra("present_country_data"));
                        }
                        JSONObject jobj_pro = new JSONObject();
                        JSONObject jobj_all = new JSONObject();
                        JSONObject jobj_home_town = new JSONObject();
                        JSONObject jobj_residence = new JSONObject();
                        JSONObject jobj_address_attr = new JSONObject();
                        JSONObject jobj_address = new JSONObject();
                        JSONObject jobj_present_address = new JSONObject();
                        JSONObject jobj_permanent_address = new JSONObject();
                        try {
                            jobj_home_town.put(Utils.HOME_TOWN,
                                    data.getStringExtra("home_town_data"));
                            jobj_residence.put(Utils.RESIDENCE, data.getStringExtra("residence"));
                            jobj_present_address.put(Utils.ID,
                                    userProfile.getProfile().getAddress().getPresentAddress()
                                            .getId()
                                            + "");
                            jobj_present_address.put(Utils.ADDRESS_TYPE, "1");
                            jobj_present_address.put(Utils.ADDRESS,
                                    data.getStringExtra("present_address"));
                            jobj_present_address.put(Utils.DISTRICT,
                                    data.getStringExtra("present_district_value"));
                            jobj_present_address.put(Utils.COUNTRY,
                                    data.getStringExtra("present_country_value"));
                            jobj_address.put("0", jobj_present_address);
                            jobj_permanent_address.put(Utils.ID,
                                    userProfile.getProfile().getAddress().getPermanentAddress()
                                            .getId()
                                            + "");
                            jobj_permanent_address.put(Utils.ADDRESS_TYPE, "2");
                            jobj_permanent_address.put(Utils.ADDRESS,
                                    data.getStringExtra("permanent_address"));
                            jobj_permanent_address.put(Utils.DISTRICT,
                                    data.getStringExtra("permanent_district_value"));
                            jobj_permanent_address.put(Utils.COUNTRY,
                                    data.getStringExtra("permanent_country_value"));
                            jobj_address.put("1", jobj_permanent_address);
                            jobj_address_attr.put(Utils.ADDRESSES_ATTRIBUTES, jobj_address);
                            jobj_address_attr.put(Utils.HOME_TOWN,
                                    data.getStringExtra("home_town_data"));
                            jobj_address_attr.put(Utils.RESIDENCE,
                                    data.getStringExtra("residence"));
                            jobj_pro.put(Utils.PROFILE, jobj_address_attr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateDetails(jobj_pro);
                        setDataOnPersonalInfoRecylerView(userProfile, 22);
                        setDataOnCommunicationRecylerView(userProfile);
                    }
                }

                setDataOnFamilyMemberRecylerView(userProfile);
            }
        }
    }

    private int getKakaIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKakas().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getKakas().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getMamaIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getMamas().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getMamas().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getDadaIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getDadas().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getDadas().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getNanaIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getNanas().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getNanas().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getFupaIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getFufas().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getFufas().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getKhaluIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKhalus().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getKhalus().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getOtherIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getOthers().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getOthers().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getRelativeChildOtherIndex(int id) {
        for (int i = 0; i < otherRelativeChildItemList.size(); i++) {
            if (otherRelativeChildItemList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getBroIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getBrothers().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getSisIndex(int id) {
        for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getSisters().size(); i++) {
            if (userProfile.getProfile().getFamilyMembers().getSisters().get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getPosition(UserProfile userProfile) {
        return 0;
    }

    public void getUpdatedText(String title, String msg, String hint) {
        final View view = LayoutInflater.from(this).inflate(R.layout.custom_edit, null);
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(view);
        alertBuilder.setCancelable(true);
        final EditText editText = view.findViewById(R.id.etText);
        TextView tv_title = view.findViewById(R.id.title);
        TextView tv_ok = view.findViewById(R.id.okBtn);
        TextView tv_no = view.findViewById(R.id.noBtn);
        tv_title.setText(title);
        final AlertDialog alert = alertBuilder.create();
        try {
            editText.setText(msg);
            editText.setHint(hint);

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateDetails(alert, editText.getText().toString());
                }
            });
            tv_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();
                }
            });
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDetails(AlertDialog alert, String s) {
        userProfileDescriptionText.setText(s);
        JSONObject jobjPro = new JSONObject();
        JSONObject jobj = new JSONObject();
        try {
            jobjPro.put(Utils.ABOUT_YOURSELF, s);
            jobj.put(Utils.PROFILE, jobjPro);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new NetWorkOperation.updateProfileData(OwnUserProfileActivity.this).execute(
                jobj.toString());
        alert.dismiss();
    }

    private void updateDetails(String parent, String key, String value) {
        JSONObject jobjPro = new JSONObject();
        JSONObject jobj = new JSONObject();
        try {
            jobjPro.put(key, value);
            jobj.put(parent, jobjPro);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new NetWorkOperation.updateProfileData(OwnUserProfileActivity.this).execute(
                jobj.toString());
    }

    private void updateDetails(JSONObject jsonObject) {
        new NetWorkOperation.updateProfileData(OwnUserProfileActivity.this).execute(
                jsonObject.toString());
    }

    public void getUpdatedText(final String title, final String msg, final String hint,
            final TextView textView, String parent, String key) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                final View view = LayoutInflater.from(OwnUserProfileActivity.this).inflate(
                        R.layout.custom_edit, null);
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        OwnUserProfileActivity.this);
                alertBuilder.setView(view);
                alertBuilder.setCancelable(true);
                final EditText editText = view.findViewById(R.id.etText);
//        final TextInputLayout til = view.findViewById(R.id.til1);
                TextView tv_title = view.findViewById(R.id.title);
                TextView tv_ok = view.findViewById(R.id.okBtn);
                TextView tv_no = view.findViewById(R.id.noBtn);
                tv_title.setText(title);
                final AlertDialog alert = alertBuilder.create();
                try {
                    editText.setText(msg);
                    editText.setHint(hint);

                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JSONObject jobjPro = new JSONObject();
                            JSONObject jobj = new JSONObject();
                            try {
                                jobjPro.put(Utils.DESIGNATION, editText.getText().toString());
                                jobj.put(Utils.PROFILE, jobjPro);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            updateDetails(alert, jobjPro.toString());
                        }
                    });
                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.dismiss();
                        }
                    });
                    alert.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ProfileLivingIn getProfileLivingIn() {
        return userProfile.getProfile().getProfileLivingIn();
    }

    public Address getAddress() {
        return userProfile.getProfile().getAddress();
    }

    public PersonalInformation getPersonalInfo() {
        return userProfile.getProfile().getPersonalInformation();
    }

    public Brother getBrother(int position) {
        return userProfile.getProfile().getFamilyMembers().getBrothers().get(position - 1);
    }

    public Sister getSister(int position) {
        return userProfile.getProfile().getFamilyMembers().getSisters().get(position - 1);
    }

    public Father getFather() {
        return userProfile.getProfile().getFamilyMembers().getFather();
    }

    public Mother getMother() {
        return userProfile.getProfile().getFamilyMembers().getMother();
    }
}

class GetReligionStepFetchConstant extends AsyncTask<String, String, String> {

    Context mContext;

    public GetReligionStepFetchConstant(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        SharePref sharePref = new SharePref(mContext);
        final String token = sharePref.get_data("token");
        Request request = new Request.Builder()
                .url(Utils.STEP_CONSTANT_FETCH + 2)
                .addHeader("Authorization", "Token token=" + token)
                .build();

        Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 2);
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e(Utils.LOGIN_DEBUG, responseString);
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
        Log.i("religion_step_data", s + "");

        if (s == null) {
            Utils.ShowAlert(mContext,
                    mContext.getResources().getString(R.string.no_internet_connection));
        } else {
            Intent intent = new Intent(mContext, PopUpCastReligion.class);
            intent.putExtra("constants", s);
            intent.putExtra("data", "religion");
            ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                    Utils.RELIGION_REQUEST_CODE);

        }
    }

}

class GetPersonalInfoStepFetchConstant extends AsyncTask<String, String, String> {
    String reqType = "";
    int req_code, position;
    Context mContext;


    public GetPersonalInfoStepFetchConstant(Context context, String strType, int position,
            int req_code) {
        this.reqType = strType;
        this.req_code = req_code;
        mContext = context;
        this.position = position;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        SharePref sharePref = new SharePref(mContext);
        final String token = sharePref.get_data("token");
        Request request = new Request.Builder()
                .url(Utils.STEP_CONSTANT_FETCH + 4)
                .addHeader("Authorization", "Token token=" + token)
                .build();

        Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 4);
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e("profile_edit", responseString);
            response.body().close();
            return responseString;
        } catch (Exception e) {
            e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("urldata", s + "");
        if (s == null) {
            Utils.ShowAlert(mContext,
                    mContext.getResources().getString(R.string.no_internet_connection));
        } else {
            Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
            Intent intent = new Intent(mContext, PopUpPersonalInfo.class);
            intent.putExtra("constants", s);
            intent.putExtra("data", reqType);
            if (reqType.equalsIgnoreCase("education")) {
                intent = new Intent(mContext, PopupEducationEdit.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("father")) {
                intent = new Intent(mContext, PopupParentsEdit.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("father", ((OwnUserProfileActivity) mContext).getFather());
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("mother")) {
                intent = new Intent(mContext, PopupParentsEdit.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("mother", ((OwnUserProfileActivity) mContext).getMother());
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("brother")) {
                intent = new Intent(mContext, BrotherEditActivity.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("brother_info",
                        ((OwnUserProfileActivity) mContext).getBrother(position));
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("sister")) {
                intent = new Intent(mContext, BrotherEditActivity.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("sister_info",
                        ((OwnUserProfileActivity) mContext).getSister(position));
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("other")) {
                intent = new Intent(mContext, OthersEditActivity.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else {
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            }
        }
    }
}

class GetFamilyInfoStepFetchConstant extends AsyncTask<String, String, String> {
    String reqType = "";
    int req_code;
    Context mContext;

    public GetFamilyInfoStepFetchConstant(Context context, String strType, int req_code) {
        this.reqType = strType;
        this.req_code = req_code;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        SharePref sharePref = new SharePref(mContext);
        final String token = sharePref.get_data("token");
        Request request = new Request.Builder()
                .url(Utils.STEP_CONSTANT_FETCH + 5)
                .addHeader("Authorization", "Token token=" + token)
                .build();

        Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 4);
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e("profile_edit", responseString);
            response.body().close();
            return responseString;
        } catch (Exception e) {
            e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("urldata", s + "");
        if (s == null) {
            Utils.ShowAlert(mContext,
                    mContext.getResources().getString(R.string.no_internet_connection));
        } else {
            Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
            Intent intent = new Intent(mContext, PopUpPersonalInfo.class);
            intent.putExtra("constants", s);
            intent.putExtra("data", reqType);
            if (reqType.equalsIgnoreCase("father")) {
                intent = new Intent(mContext, PopupParentsEdit.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("father", ((OwnUserProfileActivity) mContext).getFather());
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("mother")) {
                intent = new Intent(mContext, PopupParentsEdit.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("mother", ((OwnUserProfileActivity) mContext).getMother());
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("brother") || reqType.equalsIgnoreCase(
                    "sister")) {
                intent = new Intent(mContext, BrotherEditActivity.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else if (reqType.equalsIgnoreCase("other")) {
                intent = new Intent(mContext, OthersEditActivity.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            } else {
                ((OwnUserProfileActivity) mContext).startActivityForResult(intent,
                        req_code);
            }
        }
    }
}

class GetAddressStepFetchConstant extends AsyncTask<String, String, String> {

    int reqCode;
    Context mContext;

    public GetAddressStepFetchConstant(Context context, int request_code) {
        this.reqCode = request_code;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        SharePref sharePref = new SharePref(mContext);
        final String token = sharePref.get_data("token");
        Request request = new Request.Builder()
                .url(Utils.STEP_CONSTANT_FETCH + 6)
                .addHeader("Authorization", "Token token=" + token)
                .build();

        Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 6);
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e(Utils.LOGIN_DEBUG, responseString);
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
        Log.i("urldata", s + "");
        if (s == null) {
            Utils.ShowAlert(mContext, mContext
                    .getResources().getString(R.string.no_internet_connection));
        } else {
            Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
            Intent intent = new Intent(mContext, RegistrationUserAddressInformation.class);
            Bundle bundle = new Bundle();
            bundle.putString("constants", s);
            bundle.putString("data", "address");
            bundle.putSerializable("personal_info",
                    ((OwnUserProfileActivity) mContext).getPersonalInfo());
            bundle.putSerializable("profile_living_in",
                    ((OwnUserProfileActivity) mContext).getProfileLivingIn());
            bundle.putSerializable("address",
                    ((OwnUserProfileActivity) mContext).getAddress());
            intent.putExtras(bundle);
            ((OwnUserProfileActivity) mContext).startActivityForResult(intent, reqCode);
        }
    }
}

class FetchOthersConstant extends AsyncTask<String, String, String> {

    String reqType = "";
    int req_code;
    int id;
    Context mContext;

    public FetchOthersConstant(Context context, String strType, Object id, int req_code) {
        this.reqType = strType;
        this.req_code = req_code;
        this.id = (Integer) id;
        this.mContext = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            Utils.ShowAlert(mContext,
                    mContext.getString(R.string.no_internet_connection));
        } else {
            Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
            Intent intent;
            intent = new Intent(mContext, OthersEditActivity.class);
            intent.putExtra("constants", s);
            intent.putExtra("data", reqType);
            intent.putExtra("id", id);
            ((OwnUserProfileActivity) mContext).startActivityForResult(intent, req_code);
        }
    }

    @Override
    protected String doInBackground(String... parameters) {
        SharePref sharePref = new SharePref(mContext);
        final String token = sharePref.get_data("token");
        Request request = new Request.Builder()
                .url(Utils.STEP_CONSTANT_FETCH + 6)
                .addHeader("Authorization", "Token token=" + token)
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Log.e(Utils.DEBUG, responseString);
            response.body().close();
            return responseString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
