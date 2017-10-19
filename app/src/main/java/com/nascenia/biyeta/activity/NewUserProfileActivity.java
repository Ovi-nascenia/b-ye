package com.nascenia.biyeta.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.ViewPagerAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.ProfileImageFirstFragment;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.CustomStopScrollingRecylerLayoutManager;
import com.nascenia.biyeta.view.SendRequestFragmentView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import static com.nascenia.biyeta.R.id.details;
import static com.nascenia.biyeta.R.id.emoIconImage;

/**
 * Created by saiful on 3/3/17.
 *
 */


public class NewUserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Tracker mTracker;
    private AnalyticsApplication application;
    private Toolbar toolbar;
    private ViewPager old;
    private ViewPager viewPager;
    public static String message;
    private PagerAdapter adapter;


    private LinearLayout sliderDotsPanel;
    private  int dotscount;
    private ImageView[] dots;

    private ImageView indicatorImage1, indicatorImage2, indicatorImage3, userProfileImage,
            cancelImageView, acceptImageView, emoIconImageView, favoriteImageView,
            mobileCheckIconImageView, fbCheckIconImageView, mailCheckIconImageView;

    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView,
            familyMemberInfoRecylerView, communicationInfoRecylerview,
            otherInfoRecylerView;


    private TextView userProfileDescriptionText, userNameTextView, familyInfoTagTextView,
            communicationTagTextview, otherInfoTagTextview, cancelTextView, acceptTextView,
            userProfileDescriptionTextViewTag, generalInfoTagTextview, matchUserChoiceTextViewTag,
            sendEmoIconTextTag;

    private ImageView profileViewerPersonImageView, selfImageView;

    private CardView familyCardView, communicationCarview, otherInfoCardView, userProfileDescriptionCardview,
            generalInfoCarView, matchUserChoiceCardView;

    private Button finalResultButton;

    private LinearLayout requestSendButtonsLayout;

    private SharePref sharePref;
    private final int REQUEST_PHONE_CALL = 100;

    private LinearLayout layoutSendSmiley, smileyandVerificationLayout, detilsInfoLayout;
    private UserProfile userProfile;

    private ProgressDialog progressDialog;

    private RelativeLayout bottomRelativeLayout;
    private CoordinatorLayout coordnatelayout;
    private NestedScrollView nestedScrollView;

    private int favoriteId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_profile);

        favoriteId = Integer.parseInt(getIntent().getStringExtra("id"));

        if (ContextCompat.checkSelfPermission(NewUserProfileActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewUserProfileActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        }

        sharePref = new SharePref(NewUserProfileActivity.this);

        initView();


        /*if (getIntent().getExtras().getBoolean("PROFILE_EDIT_OPTION")) {

            editUserProfileImageView.setVisibility(View.VISIBLE);
        }*/

        old.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        old.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


        if (Utils.isOnline(getApplicationContext())) {

            fetchUserProfileDetails(Utils.PROFILES_URL +
                    getIntent().getExtras().getString("id"));
//            Log.i("profileId", getIntent().getExtras().getString("id"));

        } else {
            Utils.ShowAlert(NewUserProfileActivity.this, getString(R.string.no_internet_connection));
            finish();
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
//        Log.e("new user profile", getIntent().hasExtra("user_name")?getIntent().getExtras().getString("user_name") + "'s Profile" : userProfile.getProfile().getPersonalInformation().getDisplayName() + "'s Profile");

    }

    @Override
    public void finish() {
        if(userProfile!=null)
        {
            if (!userProfile.getProfile().isIsFavorite()) {
                Intent intent = new Intent();

                intent.putExtra("returnId", favoriteId);
                Log.d("fav_id", favoriteId + "");
                setResult(Activity.RESULT_OK, intent);

            }
        }


        super.finish();
    }

    public void backBtnAction(View v){
        finish();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void initView() {

        sliderDotsPanel = (LinearLayout) findViewById(R.id.sliderDots);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scrollview);


        detilsInfoLayout = (LinearLayout) findViewById(R.id.detils_info_layout);
        nestedScrollView.smoothScrollBy(0, detilsInfoLayout.getTop());

        bottomRelativeLayout = (RelativeLayout) findViewById(R.id.r1);
        coordnatelayout = (CoordinatorLayout) findViewById(R.id.coordnatelayout);

        userProfileDescriptionTextViewTag = (TextView) findViewById(R.id.user_profile_description_textView_tag);
        userProfileDescriptionCardview = (CardView) findViewById(R.id.user_profile_description_cardview);

        generalInfoTagTextview = (TextView) findViewById(R.id.generalInfoTagTextview);
        generalInfoCarView = (CardView) findViewById(R.id.generalInfoCarView);

        matchUserChoiceTextViewTag = (TextView) findViewById(R.id.matchUserChoiceTextViewTag);
        matchUserChoiceCardView = (CardView) findViewById(R.id.matchUserChoiceCardView);


        progressDialog = new ProgressDialog(NewUserProfileActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(true);

        smileyandVerificationLayout = (LinearLayout) findViewById(R.id.smileyandVerificationLayout);


        sendEmoIconTextTag = (TextView) findViewById(R.id.sendEmoIconTextTag);
        layoutSendSmiley = (LinearLayout) findViewById(R.id.layoutSendSmiley);
        emoIconImageView = (ImageView) findViewById(emoIconImage);
        favoriteImageView = (ImageView) findViewById(R.id.likeImage);
        layoutSendSmiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userProfile.getProfile().isIsSmileSent()) {

                    NetWorkOperation.sendFavoriteUnFavoriteandSmileRequest(NewUserProfileActivity.this,
                            Utils.SEND_SMILE_URL,
                            userProfile.getProfile().getPersonalInformation().getId() + "",
                            "Authorization",
                            "Token token=" + sharePref.get_data("token"),
                            userProfile,
                            favoriteImageView,
                            layoutSendSmiley,
                            emoIconImageView,
                            sendEmoIconTextTag,
                            Utils.SMILEY_BUTTON_PRESS_TAG,
                            application,
                            mTracker);


/*
                    layoutSendSmiley.setEnabled(false);
                    emoIconImageView.setImageResource(R.drawable.red_smile);
                    sendEmoIconTextTag.setText(getResources().getString(R.string.after_send_smile_text));
*/


                }

                application.setEvent("Action", "Click", "Smiley Sent", mTracker);
            }
        });


        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userProfile.getProfile().isIsFavorite()) {

                    NetWorkOperation.sendFavoriteUnFavoriteandSmileRequest(NewUserProfileActivity.this,
                            Utils.FAVORITE_URL,
                            userProfile.getProfile().getPersonalInformation().getId() + "",
                            "Authorization",
                            "Token token=" + sharePref.get_data("token"),
                            userProfile,
                            favoriteImageView,
                            layoutSendSmiley,
                            emoIconImageView,
                            sendEmoIconTextTag,
                            Utils.FAVORITE_BUTTON_PRESS_TAG,
                            application,
                            mTracker);


                    application.setEvent("Action", "Click", "Favorite Clicked", mTracker);


                    /*//favoriteImageView.setEnabled(false);
                    userProfile.getProfile().setIsFavorite(true);
                    favoriteImageView.setImageResource(R.drawable.red_favorite);*/

                } else {



                    NetWorkOperation.sendFavoriteUnFavoriteandSmileRequest(NewUserProfileActivity.this,
                            Utils.UNFAVORITE_URL,
                            userProfile.getProfile().getPersonalInformation().getId() + "",
                            "Authorization",
                            "Token token=" + sharePref.get_data("token"),
                            userProfile,
                            favoriteImageView,
                            layoutSendSmiley,
                            emoIconImageView,
                            sendEmoIconTextTag,
                            Utils.UNFAVORITE_BUTTON_PRESS_TAG,
                            application,
                            mTracker);

                   /* userProfile.getProfile().setIsFavorite(false);
                    favoriteImageView.setImageResource(R.drawable.favorite);*/
                    application.setEvent("Action", "Click", "Unfavorite Clicked", mTracker);
                }

            }
        });


        mobileCheckIconImageView = (ImageView) findViewById(R.id.mobile_check_icon);
        fbCheckIconImageView = (ImageView) findViewById(R.id.fb_check_icon);
        mailCheckIconImageView = (ImageView) findViewById(R.id.mail_check_icon);

        old = (ViewPager) findViewById(R.id.viewpager);
        generalInfoRecyclerView = (RecyclerView) findViewById(R.id.user_general_info_recycler_view);
        generalInfoRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));
        matchUserChoiceRecyclerView = (RecyclerView) findViewById(R.id.match_user_choice_recyclerView);
        matchUserChoiceRecyclerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));
        familyMemberInfoRecylerView = (RecyclerView) findViewById(R.id.family_info_recylerview);
        familyMemberInfoRecylerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));
        communicationInfoRecylerview = (RecyclerView) findViewById(R.id.communication_info_recylerview);
        communicationInfoRecylerview.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));
        otherInfoRecylerView = (RecyclerView) findViewById(R.id.other_info_recylerview);
        otherInfoRecylerView.setLayoutManager(new CustomStopScrollingRecylerLayoutManager(this));

        userProfileDescriptionText = (TextView) findViewById(R.id.userProfileDescriptionText);
        profileViewerPersonImageView = (ImageView) findViewById(R.id.viewer_image);

        userProfileImage = (ImageView) findViewById(R.id.user_profile_image);
        userNameTextView = (TextView) findViewById(R.id.user_name);

//        userNameTextView.setText(getIntent().getExtras().getString("user_name"));

        familyInfoTagTextView = (TextView) findViewById(R.id.family_info_tag);
        familyCardView = (CardView) findViewById(R.id.family_info_recylerview_card);

        communicationTagTextview = (TextView) findViewById(R.id.communication_tag_textview);
        communicationCarview = (CardView) findViewById(R.id.communication_card_layout);

        otherInfoTagTextview = (TextView) findViewById(R.id.other_info_tag);
        otherInfoCardView = (CardView) findViewById(R.id.other_info_cardview);


        selfImageView = (ImageView) findViewById(R.id.self_image);


        cancelImageView = (ImageView) findViewById(R.id.cancel_imageview);
        acceptImageView = (ImageView) findViewById(R.id.accept_imageview);

        cancelImageView.setOnClickListener(this);
        acceptImageView.setOnClickListener(this);

        cancelTextView = (TextView) findViewById(R.id.cancel_textview);
        acceptTextView = (TextView) findViewById(R.id.accept_textview);

        requestSendButtonsLayout = (LinearLayout) findViewById(R.id.request_send_buttons_layout);
        finalResultButton = (Button) findViewById(R.id.finalResultBtn);
        finalResultButton.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        indicatorImage1 = (ImageView) findViewById(R.id.page1);
        indicatorImage2 = (ImageView) findViewById(R.id.page2);
        indicatorImage3 = (ImageView) findViewById(R.id.page3);



    }

    private void fetchUserProfileDetails(final String url) {

        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = new ResourceProvider(NewUserProfileActivity.this).fetchGetResponse(url);
                    ResponseBody responseBody = response.body();
                    final String responseValue = responseBody.string();
//                    Log.i("profileresponsevalue", responseValue);
                    responseBody.close();
                    userProfile = new Gson().fromJson(responseValue, UserProfile.class);
                    NewUserProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            coordnatelayout.setVisibility(View.VISIBLE);
                            bottomRelativeLayout.setVisibility(View.VISIBLE);

                            smileyandVerificationLayout.setVisibility(View.VISIBLE);

                            //setVerification image
                            SendRequestFragmentView.setVerificationIcon(userProfile,
                                    mobileCheckIconImageView,
                                    fbCheckIconImageView,
                                    mailCheckIconImageView);


                            if (userProfile.getProfile().isIsFavorite()) {

                                //favoriteImageView.setEnabled(false);
                                favoriteImageView.setImageResource(R.drawable.red_favorite);

                            }

                            else if (userProfile.getProfile().getPersonalInformation().getDisplayName() != null) {

                                //favoriteImageView.setEnabled(false);
                                if(userProfile.getProfile().getPersonalInformation().getRealName()==null){

                                    userNameTextView.setText(userProfile.getProfile().getPersonalInformation().getDisplayName());

                                }

                                else if(userProfile.getProfile().getPersonalInformation().getRealName()!=null)
                                    userNameTextView.setText(userProfile.getProfile().getPersonalInformation().getRealName());
                                if (userProfile.getProfile().getPersonalInformation().getImage() != null) {
                                    if (userProfile.getProfile().getPersonalInformation().getImage().getOther().size() > 1) {
                                        dotscount = userProfile.getProfile().getPersonalInformation().getImage().getOther().size();
                                        dots = new ImageView[dotscount];

                                        for (int i = 0; i < dotscount; i++) {
                                            dots[i] = new ImageView(getApplicationContext());
                                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            params.setMargins(6, 4, 6, 4);
                                            sliderDotsPanel.addView(dots[i], params);
                                            dots[i].getLayoutParams().height = 16;
                                            dots[i].getLayoutParams().width = 16;
                                        }

                                        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));
                                        if(viewPager!=null)
                                        {
                                            viewPager.addOnPageChangeListener(

                                                    new ViewPager.OnPageChangeListener() {
                                                        private int fromPosition = 0,previousState=0;
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
                                                            if(state==0 && previousState!=2&&fromPosition==dotscount-1)
                                                            {
                                                                viewPager.setCurrentItem(0,true);
                                                                return;
                                                            }
                                                            else if(state==0 && previousState!=2&&fromPosition==0)
                                                            {
                                                                viewPager.setCurrentItem(dotscount-1,true);
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

                            mTracker.setScreenName(userProfile.getProfile().getPersonalInformation().getDisplayName() + "'s Profile");
//        Log.i("user_name", getIntent().getExtras().getString("user_name"));
                            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                            if (userProfile.getProfile().isIsSmileSent()) {

                                layoutSendSmiley.setEnabled(false);
                                emoIconImageView.setImageResource(R.drawable.red_smile);
                                sendEmoIconTextTag.setText(getResources().getString(R.string.after_send_smile_text));
                            }


                            if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {

                                userProfileDescriptionTextViewTag.setVisibility(View.VISIBLE);
                                userProfileDescriptionCardview.setVisibility(View.VISIBLE);
                                userProfileDescriptionText.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
                            }


                            if (userProfile.getProfile().getPersonalInformation().getImage() != null) {
//

                                Picasso.with(NewUserProfileActivity.this)
                                        .load(Utils.Base_URL + userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture())
                                        .into(userProfileImage, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                userProfileImage.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Utils.scaleImage(NewUserProfileActivity.this, 2f, userProfileImage);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });

                                Glide.with(NewUserProfileActivity.this)
                                        .load(Utils.Base_URL +
                                                userProfile.getProfile().getPersonalInformation().
                                                        getImage().getProfilePicture())
                                        .into(profileViewerPersonImageView);


                            } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                                    (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))) {
                                userProfileImage.setImageResource(R.drawable.profile_icon_male);
                                profileViewerPersonImageView.setImageResource(R.drawable.profile_icon_male);
                            } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                                    (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))) {
                                userProfileImage.setImageResource(R.drawable.profile_icon_female);
                                profileViewerPersonImageView.setImageResource(R.drawable.profile_icon_female);
                            } else {
                            }


                            generalInfoTagTextview.setVisibility(View.VISIBLE);
                            generalInfoCarView.setVisibility(View.VISIBLE);
                            SendRequestFragmentView.setDataonGeneralInfoRecylerView(
                                    getBaseContext(), userProfile, generalInfoRecyclerView);


                            Glide.with(getBaseContext())
                                    .load(Utils.Base_URL +
                                            sharePref.get_data("profile_picture"))
                                    .into(selfImageView);


                            matchUserChoiceTextViewTag.setVisibility(View.VISIBLE);
                            matchUserChoiceCardView.setVisibility(View.VISIBLE);
                            SendRequestFragmentView.setDataonMatchUserChoiceRecylerView(getBaseContext(),
                                    userProfile, matchUserChoiceRecyclerView);


                            if (userProfile.getProfile().getOtherInformation() != null) {

                                /*otherInfoTagTextview.setVisibility(View.VISIBLE);
                                otherInfoCardView.setVisibility(View.VISIBLE);
                                */
                                SendRequestFragmentView.setDataonOtherInfoRecylerView(getBaseContext(),
                                        userProfile,
                                        otherInfoRecylerView,
                                        otherInfoTagTextview,
                                        otherInfoCardView);

                            }

                            /****************Different state base user profile  below**************/


                            //biodata request for appuser

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                            null) &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            null)) {


                                // Toast.makeText(getBaseContext(), "No Match Found", Toast.LENGTH_LONG).show();
                                finalResultButton.setTag(Utils.sendBiodataRequest);

                            } else if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(sharePref.get_data("user_id"))) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (!userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                               /* familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);*/


                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setEnabled(false);

                            } else if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(sharePref.get_data("user_id"))) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                                /*familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);*/

                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setEnabled(false);

                            } else if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(sharePref.get_data("user_id"))) &&
                                    (userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (!userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);

                                finalResultButton.setEnabled(true);
                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setTag(Utils.sendCommunicationRequest);

                            }


                            //biodata request from other


                            else if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                            Integer.parseInt(sharePref.get_data("user_id"))) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (!userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                                /*familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);*/
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.profile_request_message),
                                        Toast.LENGTH_LONG).show();

                                finalResultButton.setVisibility(View.GONE);
                                requestSendButtonsLayout.setVisibility(View.VISIBLE);

                                acceptImageView.setTag(Utils.profileRequestAccept);
                                cancelImageView.setTag(Utils.profileRequestCancel);


                            } else if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                            Integer.parseInt(sharePref.get_data("user_id"))) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {

                                //abaro profile request send r kaj hobe
                                //Toast.makeText(getBaseContext(), " abaro profile request send r kaj hobe", Toast.LENGTH_LONG).show();

                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setEnabled(true);
                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setTag(Utils.sendBiodataRequest);

                            }


                            //communication request


                            //no communication connection between sender and reciver
                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().getCommunicationRequestId() == null)
                                    ) {

                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);

                                finalResultButton.setEnabled(true);
                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setTag(Utils.sendCommunicationRequest);

                            } else {
                                //sender =appuser
                                if (userProfile.getProfile().getRequestStatus().getName().
                                        equals("communication request") &&
                                        (userProfile.getProfile().getRequestStatus().getSender() ==
                                                Integer.parseInt(sharePref.get_data("user_id"))) &&
                                        (userProfile.getProfile().getRequestStatus().isAccepted())
                                        ) {


                                    familyInfoTagTextView.setVisibility(View.VISIBLE);
                                    familyCardView.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                            userProfile, familyMemberInfoRecylerView);


                                    communicationTagTextview.setVisibility(View.VISIBLE);
                                    communicationCarview.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataOnCommunincationRecylerView(getBaseContext(),
                                            userProfile, communicationInfoRecylerview);


                                    //send message btn and phone call btn
                                    finalResultButton.setVisibility(View.GONE);
                                    requestSendButtonsLayout.setVisibility(View.VISIBLE);
                                    acceptImageView.setImageResource(R.drawable.envelope_icon);
                                    cancelImageView.setImageResource(R.drawable.phone_icon);
                                    acceptTextView.setText(getResources().getString(R.string.make_message));
                                    cancelTextView.setText(getResources().getString(R.string.make_call));

                                    acceptImageView.setTag(Utils.sendmessage);
                                    cancelImageView.setTag(Utils.call);
                                } else if (userProfile.getProfile().getRequestStatus().getName().
                                        equals("communication request") &&
                                        (userProfile.getProfile().getRequestStatus().getSender() ==
                                                Integer.parseInt(sharePref.get_data("user_id"))) &&
                                        (userProfile.getProfile().getRequestStatus().isRejected())) {

                                    // Toast.makeText(getBaseContext(), "apnar communication request reject hoise", Toast.LENGTH_LONG).show();
                                    //set message : apnar communication request reject hoise,btn action off
                                    finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                    finalResultButton.setEnabled(false);


                                    familyInfoTagTextView.setVisibility(View.VISIBLE);
                                    familyCardView.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                            userProfile, familyMemberInfoRecylerView);

                                } else if (userProfile.getProfile().getRequestStatus().getName().
                                        equals("communication request") &&
                                        (userProfile.getProfile().getRequestStatus().getSender() ==
                                                Integer.parseInt(sharePref.get_data("user_id"))) &&
                                        (!userProfile.getProfile().getRequestStatus().isAccepted()
                                                && (!userProfile.getProfile().getRequestStatus().isRejected()))) {


                                    //set message : already communication resquest send,btn action off
                                    //  Toast.makeText(getBaseContext(), " already communication resquest send", Toast.LENGTH_LONG).show();

                                    familyInfoTagTextView.setVisibility(View.VISIBLE);
                                    familyCardView.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                            userProfile, familyMemberInfoRecylerView);


                                    finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                    finalResultButton.setEnabled(false);


                                }

                                //reciver = appuser
                                else if (userProfile.getProfile().getRequestStatus().getName().
                                        equals("communication request") &&
                                        (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                                Integer.parseInt(sharePref.get_data("user_id"))) &&
                                        (userProfile.getProfile().getRequestStatus().isAccepted())
                                        ) {

                                    //send message btn and phone call btn
                                    finalResultButton.setVisibility(View.GONE);
                                    requestSendButtonsLayout.setVisibility(View.VISIBLE);
                                    acceptImageView.setImageResource(R.drawable.envelope_icon);
                                    cancelImageView.setImageResource(R.drawable.phone_icon);
                                    acceptTextView.setText(getResources().getString(R.string.make_message));
                                    cancelTextView.setText(getResources().getString(R.string.make_call));

                                    acceptImageView.setTag(Utils.sendmessage);
                                    cancelImageView.setTag(Utils.call);


                                    familyInfoTagTextView.setVisibility(View.VISIBLE);
                                    familyCardView.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                            userProfile, familyMemberInfoRecylerView);


                                    communicationTagTextview.setVisibility(View.VISIBLE);
                                    communicationCarview.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataOnCommunincationRecylerView(getBaseContext(),
                                            userProfile, communicationInfoRecylerview);

                                } else if (userProfile.getProfile().getRequestStatus().getName().
                                        equals("communication request") &&
                                        (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                                Integer.parseInt(sharePref.get_data("user_id"))) &&
                                        (userProfile.getProfile().getRequestStatus().isRejected())) {


                                    //send communication request again
                                    // Toast.makeText(getBaseContext(), " send communication request again", Toast.LENGTH_LONG).show();


                                    familyInfoTagTextView.setVisibility(View.VISIBLE);
                                    familyCardView.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                            userProfile, familyMemberInfoRecylerView);

                                    finalResultButton.setEnabled(true);
                                    finalResultButton.setVisibility(View.VISIBLE);
                                    finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                    finalResultButton.setTag(Utils.sendCommunicationRequest);


                                } else if (userProfile.getProfile().getRequestStatus().getName().
                                        equals("communication request") &&
                                        (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                                Integer.parseInt(sharePref.get_data("user_id"))) &&
                                        (!userProfile.getProfile().getRequestStatus().isAccepted()
                                                && (!userProfile.getProfile().getRequestStatus().isRejected()))) {

                                    familyInfoTagTextView.setVisibility(View.VISIBLE);
                                    familyCardView.setVisibility(View.VISIBLE);
                                    SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                            userProfile, familyMemberInfoRecylerView);

                                    Toast.makeText(getApplicationContext(),
                                            getResources().getString(R.string.communication_request_message),
                                            Toast.LENGTH_LONG).show();


                                    finalResultButton.setVisibility(View.GONE);
                                    requestSendButtonsLayout.setVisibility(View.VISIBLE);

                                    acceptImageView.setTag(Utils.commRequestAccept);
                                    cancelImageView.setTag(Utils.commRequestCancel);
                                }


                            }
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });




                //added by masum
                    viewPager = (ViewPager) findViewById(R.id.pager);

                    if (userProfile.getProfile().getPersonalInformation().getImage().getOther().size()>1){
                        userProfileImage.setVisibility(View.INVISIBLE);
                        adapter = new ViewPagerAdapter(NewUserProfileActivity.this,
                                userProfile.getProfile().getPersonalInformation().getImage().getOther());
                        viewPager.setAdapter(adapter);

                    }
                    else{
                        userProfileImage.setVisibility(View.VISIBLE);
                    }

                ////


                } catch (Exception e) {
                    e.printStackTrace();
//                    application.trackEception(e, "fetchUserProfileDetails", "NewUserProfileActivity", e.getMessage().toString(), mTracker);
                }
            }
        }).start();

    }


    @Override
    public void onClick(View v) {


        if (v.getTag().equals(Utils.profileRequestAccept)) {

            new SendRequestTask().execute(Utils.PROFILE_REQUEST_URL +
                            userProfile.getProfile().getRequestStatus().getProfileRequestId() + "/accept",
                    getResources().getString(R.string.send_communication_request_text),
                    Utils.sendCommunicationRequest,
                    Utils.profileRequestAccept);

            application.setEvent("Action", "Click", userProfile.getProfile().getRequestStatus().getMessage(), mTracker);

        } else if (v.getTag().equals(Utils.profileRequestCancel)) {


            new SendRequestTask().execute(Utils.PROFILE_REQUEST_URL +
                            userProfile.getProfile().getRequestStatus().getProfileRequestId() + "/reject",
                    getResources().getString(R.string.biodata_request_text),
                    Utils.sendBiodataRequest,
                    Utils.profileRequestCancel);

            application.setEvent("Action", "Click", userProfile.getProfile().getRequestStatus().getMessage(), mTracker);

        } else if (v.getTag().equals(Utils.sendCommunicationRequest)) {

            NetWorkOperation.createCommunicationReqeust(NewUserProfileActivity.this,
                    Utils.COMMUNICATION_REQUEST_URL,
                    userProfile.getProfile().getPersonalInformation().getId() + "",
                    finalResultButton,
                    getResources().getString(R.string.after_sending_communication_request_text), application, mTracker);

            application.setEvent("Action", "Click", userProfile.getProfile().getRequestStatus().getMessage(), mTracker);


        } else if (v.getTag().equals(Utils.sendBiodataRequest)) {


            NetWorkOperation.createProfileReqeust(NewUserProfileActivity.this,
                    Utils.PROFILES_URL + userProfile.getProfile().getPersonalInformation().getId()
                            + "/profile_request",
                    finalResultButton,
                    getResources().getString(R.string.after_sending_biodata_request_text), application, mTracker);

            application.setEvent("Action", "Click", userProfile.getProfile().getRequestStatus().getMessage(), mTracker);


        } else if (v.getTag().equals(Utils.commRequestAccept)) {


            new SendRequestTask().execute(Utils.COMMUNICATION_REQUEST_URL +
                            userProfile.getProfile().getRequestStatus().getCommunicationRequestId()
                            + "/accept",
                    "",
                    "",
                    Utils.MESSAGE_CALL_BLOCK);

            reload();

            application.setEvent("Action", "Click", "Communication Request Accepted", mTracker);


        } else if (v.getTag().equals(Utils.commRequestCancel)) {


            new SendRequestTask().execute(Utils.COMMUNICATION_REQUEST_URL +
                            userProfile.getProfile().getRequestStatus().getCommunicationRequestId() + "/reject",
                    getResources().getString(R.string.send_communication_request_text),
                    Utils.sendCommunicationRequest,
                    Utils.commRequestCancel);

            application.setEvent("Action", "Click", userProfile.getProfile().getRequestStatus().getMessage(), mTracker);


        } else if (v.getTag().equals(Utils.sendmessage)) {

            startActivity(new Intent(NewUserProfileActivity.this, InboxSingleChat.class)
                    .putExtra("sender_id", userProfile.getProfile().getRequestStatus().getSender())
                    .putExtra("receiver_id", userProfile.getProfile().getRequestStatus().getReceiver())
                    .putExtra("current_user", Integer.parseInt(sharePref.get_data("user_id")))
                    .putExtra("userName", userProfile.getProfile().getPersonalInformation().getDisplayName())
            );

            application.setEvent("Action", "Click", "Send Message", mTracker);


        } else if (v.getTag().equals(Utils.call)) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
                    userProfile.getProfile().getPersonalInformation().getMobileNo()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling


                if (ContextCompat.checkSelfPermission(NewUserProfileActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewUserProfileActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                }

                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                /*  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                          int[] grantResults)*/
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            application.setEvent("Action", "Click", "Phone Called", mTracker);
            TelephonyManager telMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            int simState = telMgr.getSimState();
            if(simState==TelephonyManager.SIM_STATE_ABSENT) {
                Toast.makeText(this, "সিম কার্ড নেই", Toast.LENGTH_LONG).show();
            }
            else if(simState==TelephonyManager.SIM_STATE_UNKNOWN)
            {
                Toast.makeText(this,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
            }
            else
                startActivity(intent);
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


    private class SendRequestTask extends AsyncTask<String, Void, String> {

        String btnText;
        String btnTag;
        String userResponseCase;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            btnText = urls[1];
            btnTag = urls[2];
            userResponseCase = urls[3];

            Response response;
            SharePref sharePref = new SharePref(getBaseContext());
            String token = sharePref.get_data("token");
            try {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urls[0])
                        .addHeader("Authorization", "Token token=" + token)
                        .get()
                        .build();
                response = client.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
//                Log.i("asynctaskdata", e.getMessage());
                //application.trackEception(e, "SendRequestTask/doInBackground", "NewUserProfileActivity", e.getMessage().toString(), mTracker);
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            try {

                if (s == null) {
                    Utils.ShowAlert(NewUserProfileActivity.this, getResources().getString(R.string.no_internet_connection));
                } else {

                    JSONObject jsonObject = new JSONObject(s);

                    if (userResponseCase.equals(Utils.profileRequestAccept) ||
                            userResponseCase.equals(Utils.profileRequestCancel) ||
                            userResponseCase.equals(Utils.commRequestCancel)) {


                        requestSendButtonsLayout.setVisibility(View.GONE);
                        finalResultButton.setVisibility(View.VISIBLE);
                        finalResultButton.setEnabled(true);
                        finalResultButton.setTag(btnTag);
                        finalResultButton.setText(btnText);


                        if (jsonObject.has("message")) {
                            Toast.makeText(getApplicationContext(),
                                    jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"),
                                    Toast.LENGTH_LONG).show();

                            if(jsonObject.getJSONArray("message").getJSONObject(0).getString("profile_request_already_rejected")!=null||jsonObject.getJSONArray("message").getJSONObject(0).getString("profile_request_already_accepted")!=null)
                            {
                                finish();
                                startActivity(getIntent());
                            }

                        }


                    } else if (userResponseCase.equals(Utils.MESSAGE_CALL_BLOCK)) {


                        acceptImageView.setImageResource(R.drawable.envelope_icon);
                        cancelImageView.setImageResource(R.drawable.phone_icon);
                        acceptTextView.setText(getResources().getString(R.string.make_message));
                        cancelTextView.setText(getResources().getString(R.string.make_call));

                        acceptImageView.setTag(Utils.sendmessage);
                        cancelImageView.setTag(Utils.call);

                        if (jsonObject.has("message")) {
                            Toast.makeText(getApplicationContext(),
                                    jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"),
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
//                        Log.i("casetest", "no case match");
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "SendRequestTask/onPostExecute", "NewUserProfileActivity", e.getMessage().toString(), mTracker);
            }



            try {
                if ((progressDialog != null) && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                progressDialog = null;
            }

        }
    }

}
