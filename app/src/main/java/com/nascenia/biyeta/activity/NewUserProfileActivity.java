package com.nascenia.biyeta.activity;

import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.adapter.MatchUserChoiceAdapter;
import com.nascenia.biyeta.adapter.OtherInfoRecylerViewAdapter;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.BioDataRequestFragment;
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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saiful on 3/3/17.
 */


public class NewUserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private ImageView indicatorImage1, indicatorImage2, indicatorImage3, userProfileImage,
            cancelImageView, acceptImageView;

    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView,
            familyMemberInfoRecylerView, communicationInfoRecylerview,
            otherInfoRecylerView;


    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();

    private TextView userProfileDescriptionText, userNameTextView, familyInfoTagTextView,
            communicationTagTextview, otherInfoTagTextview, cancelTextView, acceptTextView;

    private ImageView profileViewerPersonImageView, editUserProfileImageView;

    private CardView familyCardView, communicationCarview, otherInfoCardView;

    private Button finalResultButton;

    private RelativeLayout requestSendButtonsLayout;
    private UserProfile userProfile;

    /*
    *
    * 0 for profile request(bio-data request)
    * 1 for communication request
    *
    * */
    private int clickbleButtonIdentifier;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_profile);

        initView();


        /*if (getIntent().getExtras().getBoolean("PROFILE_EDIT_OPTION")) {

            editUserProfileImageView.setVisibility(View.VISIBLE);
        }*/

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

        editUserProfileImageView = (ImageView) findViewById(R.id.edit_profile_image);


        cancelImageView = (ImageView) findViewById(R.id.cancel_imageview);
        acceptImageView = (ImageView) findViewById(R.id.accept_imageview);

        cancelImageView.setOnClickListener(this);
        acceptImageView.setOnClickListener(this);

        cancelTextView = (TextView) findViewById(R.id.cancel_textview);
        acceptTextView = (TextView) findViewById(R.id.accept_textview);

        requestSendButtonsLayout = (RelativeLayout) findViewById(R.id.request_send_buttons_layout);
        finalResultButton = (Button) findViewById(R.id.finalResultBtn);
        finalResultButton.setOnClickListener(this);

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
                    userProfile = new Gson().fromJson(responseValue, UserProfile.class);
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
                                        .load(Utils.Base_URL + userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture())
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


                            //biodata request

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id"))) &&
                                    (userProfile.getProfile().getRequestStatus().isSent()) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (!userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);

                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setEnabled(false);

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id"))) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);

                                finalResultButton.setText(userProfile.getProfile().getRequestStatus().getMessage());
                                finalResultButton.setEnabled(false);

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("profile request") &&
                                    (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id"))) &&
                                    (!userProfile.getProfile().getRequestStatus().isAccepted()) &&
                                    (!userProfile.getProfile().getRequestStatus().isRejected())
                                    ) {


                                familyInfoTagTextView.setVisibility(View.VISIBLE);
                                familyCardView.setVisibility(View.VISIBLE);
                                SendRequestFragmentView.setDataonFamilyMemberInfoRecylerView(getBaseContext(),
                                        userProfile, familyMemberInfoRecylerView);

                                clickbleButtonIdentifier = 0;

                                finalResultButton.setVisibility(View.GONE);
                                requestSendButtonsLayout.setVisibility(View.VISIBLE);

                            }

                            //communication request

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id"))) &&
                                    (userProfile.getProfile().getRequestStatus().getCommunicationRequestId() == null)
                                    ) {

                                finalResultButton.setEnabled(true);
                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setText("যোগাযোগের জন্য অনুরোধ করুন");

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id"))) &&
                                    (userProfile.getProfile().getRequestStatus().getCommunicationRequestId() != null)
                                    ) {

                                finalResultButton.setEnabled(false);
                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setText("যোগাযোগের জন্য অনুরোধ send korse");

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id"))) &&
                                    (userProfile.getProfile().getRequestStatus().getCommunicationRequestId() != null)
                                    ) {

                                finalResultButton.setVisibility(View.GONE);
                                requestSendButtonsLayout.setVisibility(View.VISIBLE);
                            }

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().isAccepted())) {


                                //send message btn and phone call btn
                            }

                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().isRejected()) &&
                                    (userProfile.getProfile().getRequestStatus().getSender() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id")))) {

                                finalResultButton.setEnabled(false);
                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setText("যোগাযোগের জন্য অনুরোধ করুন already send");

                            }


                            if (userProfile.getProfile().getRequestStatus().getName().
                                    equals("communication request") &&
                                    (userProfile.getProfile().getRequestStatus().isRejected()) &&
                                    (userProfile.getProfile().getRequestStatus().getReceiver() ==
                                            Integer.parseInt(getIntent().getExtras().getString("id")))) {

                                finalResultButton.setEnabled(true);
                                finalResultButton.setVisibility(View.VISIBLE);
                                finalResultButton.setText("যোগাযোগের জন্য অনুরোধ করুন");

                            }


                            /*if (userProfile.getProfile().getRequestStatus().getName().
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


                            }*/


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

    @Override
    public void onClick(View v) {


        //00 means profile request->যোগাযোগের জন্য অনুরোধ করুন
        //01 means profile request->ুরো বায়োডাটা দেখার অনুরোধ করুন

        if ((v.getId() == R.id.accept_imageview) &&
                (clickbleButtonIdentifier == 0)) {
            finalResultButton.setVisibility(View.VISIBLE);
            finalResultButton.setEnabled(true);
            finalResultButton.setText("যোগাযোগের জন্য অনুরোধ করুন");
            finalResultButton.setTag(00);
            requestSendButtonsLayout.setVisibility(View.GONE);

            new SendRequestTask().execute(" http://test.biyeta.com/api/v1/profile_requests/" +
                    userProfile.getProfile().getRequestStatus().getProfileRequestId() + "/accept");

        } else if ((v.getId() == R.id.cancel_imageview) &&
                (clickbleButtonIdentifier == 0)) {
            finalResultButton.setVisibility(View.VISIBLE);
            finalResultButton.setEnabled(true);
            finalResultButton.setText("পুরো বায়োডাটা দেখার অনুরোধ করুন");
            finalResultButton.setTag(01);
            requestSendButtonsLayout.setVisibility(View.GONE);

            new SendRequestTask().execute(" http://test.biyeta.com/api/v1/profile_requests/" +
                    userProfile.getProfile().getRequestStatus().getProfileRequestId() + "/reject");
        } else if (v.getTag() == 00) {

            NetWorkOperation.postData(getBaseContext(),
                    "http://test.biyeta.com/api/v1/communication_requests",
                    userProfile.getProfile().getPersonalInformation().getId() + "");

        } else if (v.getTag() == 01) {

            NetWorkOperation.CreateProfileReqeust(getBaseContext(),
                    "http://test.biyeta.com/api/v1/profiles/" +
                            userProfile.getProfile().getPersonalInformation().getId()
                            + "/profile_request");

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


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {


            try {

/*
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urls[0])
                        .addHeader("Authorization", "Token token=" + token)
                        .get()
                        .build();
                responseStatus = client.newCall(request).execute();

                return responseStatus.body().string();*/

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("asynctaskdata", e.getMessage());
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("FFFFFF", s);

        }
    }

}
