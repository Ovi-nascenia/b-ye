package com.nascenia.biyeta.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nascenia.biyeta.R;

import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.adapter.MatchUserChoiceAdapter;
import com.nascenia.biyeta.adapter.OtherInfoRecylerViewAdapter;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.BioDataRequestFragment;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by saiful on 3/13/17.
 */

public abstract class SendRequestFragmentView {


    public static String responseValue;

    public abstract void loadNextProfile(int clickBtnId, int userProfileRequestId);


    /*
    *
    * this variable identifies which class reqeust for view show
    *
    * if 0 set to this variable,then it means biodata request fragment class call
    * if 1 set to this variable,then it means communication request fragment class call
    *
    * */


    private static ProgressDialog dialog;

    private static ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private static ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();
    private static ArrayList<MatchUserChoice> otherInformationArrayList = new ArrayList<MatchUserChoice>();
    private static ArrayList<MatchUserChoice> communicationArrayList = new ArrayList<MatchUserChoice>();


    private static ArrayList<UserProfileChild> parentChildItemList = new ArrayList<UserProfileChild>();
    private static ArrayList<UserProfileChild> brotherChildItemList = new ArrayList<UserProfileChild>();
    private static ArrayList<UserProfileChild> sisterChildItemList = new ArrayList<UserProfileChild>();
    private static ArrayList<UserProfileChild> childItemList = new ArrayList<UserProfileChild>();
    private static ArrayList<UserProfileChild> otherCHildItemList = new ArrayList<UserProfileChild>();
    private static ArrayList<UserProfileParent> mainparentItemList = new ArrayList<UserProfileParent>();
    private static int familyMemberCounter;
    ;


    public void fetchUserProfileDetailsResponse(final String url,
                                                final Context context,
                                                final MyCallback<Boolean> mCallback,
                                                TextView userProfileDescriptionText,
                                                RecyclerView generalInfoRecyclerView,
                                                RecyclerView matchUserChoiceRecyclerView,
                                                RecyclerView otherInfoRecylerView,
                                                ImageView profileViewerPersonImageView,
                                                ImageView userProfileImage,
                                                RecyclerView familyMemberInfoRecylerView,
                                                int viewRequestClassname,
                                                TextView userNameTextView,
                                                CoordinatorLayout coordinatorLayout,
                                                RelativeLayout bottomRelativeLayout,
                                                ImageView acceptImageView,
                                                ImageView rejectImageView) {

        //Toast.makeText(context, "fetchmethod", Toast.LENGTH_LONG).show();
        Log.i("btnreaction", "fetchmethod");
        //generalInformationArrayList.clear();
        //  matchUserChoiceArrayList.clear();
        //  otherInformationArrayList.clear();

      /*  parentChildItemList.clear();
        brotherChildItemList.clear();
        sisterChildItemList.clear();
        childItemList.clear();
        otherCHildItemList.clear();


        mainparentItemList.clear();*/


        ResponseThread responseThread = new ResponseThread(url,
                context,
                mCallback,
                userProfileDescriptionText,
                generalInfoRecyclerView,
                matchUserChoiceRecyclerView,
                otherInfoRecylerView,
                profileViewerPersonImageView,
                userProfileImage,
                familyMemberInfoRecylerView,
                viewRequestClassname,
                userNameTextView,
                coordinatorLayout,
                bottomRelativeLayout,
                acceptImageView,
                rejectImageView);
        Thread response = new Thread(responseThread);
        response.start();


    }


    public class ResponseThread implements Runnable {

        private MyCallback<Boolean> callback;
        private String url;
        private Context context;
        private TextView userProfileDescriptionText;
        private RecyclerView otherInfoRecylerView;
        private RecyclerView matchUserChoiceRecyclerView;
        private RecyclerView generalInfoRecyclerView;
        private ImageView profileViewerPersonImageView;
        private ImageView userProfileImage;
        private RecyclerView familyMemberInfoRecylerView;
        private int viewRequestClassname;
        private TextView userNameTextView;
        private CoordinatorLayout coordinatorLayout;
        private RelativeLayout bottomRelativeLayout;
        private ImageView acceptImageView;
        private ImageView rejectImageView;

        public ResponseThread(String url,
                              Context context,
                              MyCallback<Boolean> callback,
                              TextView userProfileDescriptionText,
                              RecyclerView generalInfoRecyclerView,
                              RecyclerView matchUserChoiceRecyclerView,
                              RecyclerView otherInfoRecylerView,
                              ImageView profileViewerPersonImageView,
                              ImageView userProfileImage,
                              RecyclerView familyMemberInfoRecylerView,
                              int viewRequestClassname,
                              TextView userNameTextView,
                              CoordinatorLayout coordinatorLayout,
                              RelativeLayout bottomRelativeLayout,
                              ImageView acceptImageView,
                              ImageView rejectImageView) {


            this.callback = callback;
            this.url = url;
            this.context = context;
            this.userProfileDescriptionText = userProfileDescriptionText;
            this.generalInfoRecyclerView = generalInfoRecyclerView;
            this.matchUserChoiceRecyclerView = matchUserChoiceRecyclerView;
            this.otherInfoRecylerView = otherInfoRecylerView;
            this.profileViewerPersonImageView = profileViewerPersonImageView;
            this.userProfileImage = userProfileImage;
            this.familyMemberInfoRecylerView = familyMemberInfoRecylerView;
            this.viewRequestClassname = viewRequestClassname;
            this.userNameTextView = userNameTextView;
            this.coordinatorLayout = coordinatorLayout;
            this.bottomRelativeLayout = bottomRelativeLayout;
            this.acceptImageView = acceptImageView;
            this.rejectImageView = rejectImageView;

            dialog = new ProgressDialog(context);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(true);
            dialog.show();
            responseValue = null;
        }

        @Override
        public void run() {

            try {
                Response response = new ResourceProvider((Activity) context).fetchGetResponse(url);
                ResponseBody responseBody = response.body();
                responseValue = responseBody.string();
                Log.i("threaddata", "onmethod" + responseValue + " ");
                responseBody.close();
                final UserProfile userProfile = new Gson().fromJson(responseValue, UserProfile.class);

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("taskon", "run");

                        coordinatorLayout.setVisibility(View.VISIBLE);
                        bottomRelativeLayout.setVisibility(View.VISIBLE);

                        userNameTextView.setText(userProfile.getProfile().
                                getPersonalInformation().getDisplayName());

                        setUserDetailsInfo(userProfile, userProfileDescriptionText);
                        setUserImage(context,
                                userProfile,
                                profileViewerPersonImageView,
                                userProfileImage);

                        setDataonGeneralInfoRecylerView(context,
                                userProfile,
                                generalInfoRecyclerView);
                        setDataonMatchUserChoiceRecylerView(context,
                                userProfile,
                                matchUserChoiceRecyclerView);
                        setDataonFamilyMemberInfoRecylerView(context,
                                userProfile,
                                familyMemberInfoRecylerView);
                        setDataonOtherInfoRecylerView(context,
                                userProfile, otherInfoRecylerView);

                        acceptImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Toast.makeText(context, "accept btn fetch", Toast.LENGTH_LONG).show();
                                Log.i("btnreaction", "accept btn fetch");

                                if (viewRequestClassname == Utils.BIODATA_REQUEST_FRAGEMNT_CLASS) {
                                    loadNextProfile(1, userProfile.getProfile().getRequestStatus().getProfileRequestId());
                                } else if (viewRequestClassname == Utils.COMMUNICATION_REQUEST_FRAGEMNT_CLASS) {
                                    loadNextProfile(1, userProfile.getProfile().getRequestStatus().getCommunicationRequestId());
                                } else {
                                    Log.i("classdata", "No Data recived");
                                }
                            }
                        });


                        rejectImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(context, "rej btn fetch", Toast.LENGTH_LONG).show();

                                if (viewRequestClassname == Utils.BIODATA_REQUEST_FRAGEMNT_CLASS) {
                                    loadNextProfile(0, userProfile.getProfile().getRequestStatus().getProfileRequestId());
                                } else if (viewRequestClassname == Utils.COMMUNICATION_REQUEST_FRAGEMNT_CLASS) {
                                    loadNextProfile(0, userProfile.getProfile().getRequestStatus().getCommunicationRequestId());
                                } else {
                                    Log.i("classdata", "No Data recived");
                                }
                            }
                        });


                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (callback != null && this.viewRequestClassname ==
                        Utils.BIODATA_REQUEST_FRAGEMNT_CLASS && userProfile != null) {
                    // will call onComplete() on BiodataRequestFragment once the job is done
                    callback.onComplete(true,
                            userProfile.getProfile().getRequestStatus().getProfileRequestId(),
                            userProfile);
                } else if (callback != null && this.viewRequestClassname ==
                        Utils.COMMUNICATION_REQUEST_FRAGEMNT_CLASS && userProfile != null) {
                    // will call onComplete() on CommunicationRequestClass once the job is done
                    callback.onComplete(true,
                            userProfile.getProfile().getRequestStatus().getCommunicationRequestId(),
                            userProfile);
                }


            } catch (Exception e) {
                Log.i("threaddata", "problem " + e.getMessage());
                e.printStackTrace();
            }

        }


    }

    public static void setDataonOtherInfoRecylerView(Context context,
                                                     UserProfile userProfile,
                                                     RecyclerView otherInfoRecylerView) {

        otherInformationArrayList.clear();

        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()).equals(""))) {

            otherInformationArrayList.add(new MatchUserChoice("রোজা রাখেন?",
                    checkNullField(userProfile.getProfile().getOtherInformation().getFasting())));

        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()).equals(""))) {

            otherInformationArrayList.add(new MatchUserChoice("নামাজ পড়েন?",
                    checkNullField(userProfile.getProfile().getOtherInformation().getPrayer())));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getJobAfterMarriage()).equals("")))) {


            otherInformationArrayList.add(new MatchUserChoice("বিয়ের পরে চাকরি?",
                    checkNullField(userProfile.getProfile().getOtherInformation().getJobAfterMarriage())));
        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


            otherInformationArrayList.add(new MatchUserChoice("হিজাব পড়েন?",
                    checkNullField(userProfile.getProfile().getOtherInformation().getHijab())));
        }

        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


            otherInformationArrayList.add(new MatchUserChoice("নিজের বাসা আছে?",
                    checkNullField(userProfile.getProfile().getOtherInformation().getOwnHouse())));
        }


        otherInfoRecylerView.setAdapter(new OtherInfoRecylerViewAdapter(
                context, otherInformationArrayList));

    }

    public static void setUserDetailsInfo(UserProfile userProfile, TextView view) {


        if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
            view.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
        }

    }


    public static void setUserImage(final Context context,
                                    final UserProfile userProfile,
                                    final ImageView profileViewerPersonImageView,
                                    final ImageView userProfileImage) {


        if (userProfile.getProfile().getPersonalInformation().getImage() != null) {

            Picasso.with(context)
                    .load(Utils.Base_URL + userProfile.getProfile().getPersonalInformation().getImage().getProfilePicture())
                    .into(userProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            userProfileImage.post(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.scaleImage(context, 1.2f, userProfileImage);
                                }
                            });
                        }

                        @Override
                        public void onError() {
                        }
                    });


            Glide.with(context)
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


    }

    public static void setDataonFamilyMemberInfoRecylerView(Context context,
                                                            UserProfile userProfile,
                                                            RecyclerView familyMemberInfoRecylerView) {


        parentChildItemList.clear();
        brotherChildItemList.clear();
        sisterChildItemList.clear();
        childItemList.clear();
        otherCHildItemList.clear();


        mainparentItemList.clear();
        familyMemberCounter = 0;

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
            if (userProfile.getProfile().getFamilyMembers().getMother() != null) {

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

            //add brother information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfBrothers() > 0) {

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

        familyMemberInfoRecylerView.setAdapter(new UserProfileExpenadlbeAdapter(context,
                mainparentItemList,
                false));

    }


    public static void setDataonGeneralInfoRecylerView(Context activity, UserProfile userProfile, RecyclerView view) {
        Log.i("userdetails", "recyler method");


     /*   generalInformationArrayList = new ArrayList<GeneralInformation>();*/
        generalInformationArrayList.clear();

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


        view.setAdapter(new GeneralInformationAdapter(
                activity, generalInformationArrayList));

    }

    public static void setDataonMatchUserChoiceRecylerView(Context context,
                                                           UserProfile userProfile,
                                                           RecyclerView view) {


        matchUserChoiceArrayList.clear();


        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHomeTown())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("home town")
                    , userProfile.getProfile().getMatchingAttributes().getHomeTown()));

        }


        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getAge())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("age")
                    , userProfile.getProfile().getMatchingAttributes().getAge()));

        }

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHeight())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("height")
                    , userProfile.getProfile().getMatchingAttributes().getHeight()));

        }

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getSkinColor())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("skin color")
                    , userProfile.getProfile().getMatchingAttributes().getSkinColor()));

        }

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHealth())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("health")
                    , userProfile.getProfile().getMatchingAttributes().getHealth()));

        }
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getMaritalStatus())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("marital status")
                    , userProfile.getProfile().getMatchingAttributes().getMaritalStatus()));

        }


        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getTitleEducationalQualification())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("educational qualification")
                    , userProfile.getProfile().getMatchingAttributes().getTitleEducationalQualification()));

        }

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getTitleOwnHouse())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("own house")
                    , userProfile.getProfile().getMatchingAttributes().getTitleOwnHouse()));

        }

        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getTitleOccupation())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice(Utils.setBanglaProfileTitle("occupation")
                    , userProfile.getProfile().getMatchingAttributes().getTitleOccupation()));

        }


        view.setAdapter(new MatchUserChoiceAdapter(context, matchUserChoiceArrayList));

    }


    public static void setDataOnCommunincationRecylerView(Context context,
                                                          UserProfile userProfile,
                                                          RecyclerView communicationInfoRecylerview) {


        communicationArrayList.clear();

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
                    context, communicationArrayList));
        }
    }

    public static String checkNullField(String value) {


        if (value == null || value.isEmpty()) {
            return "";
        } else {
            return value + ",";
        }

    }


    public static void setVerificationIcon(UserProfile userProfile,
                                           ImageView mobileIcon,
                                           ImageView fbIcon,
                                           ImageView mailIcon) {


        if (userProfile.getProfile().getVerifications() != null &&
                userProfile.getProfile().getVerifications().isMobile()) {
            mobileIcon.setVisibility(View.VISIBLE);
        }

        if (userProfile.getProfile().getVerifications() != null &&
                userProfile.getProfile().getVerifications().isFacebook()) {
            fbIcon.setVisibility(View.VISIBLE);
        }

        if (userProfile.getProfile().getVerifications() != null &&
                userProfile.getProfile().getVerifications().isEmail()) {
            mailIcon.setVisibility(View.VISIBLE);
        }


    }


}
