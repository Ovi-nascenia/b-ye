package com.nascenia.biyeta.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.adapter.MatchUserChoiceAdapter;
import com.nascenia.biyeta.adapter.OtherInfoRecylerViewAdapter;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.OtherInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;


/**
 * Created by saiful on 3/13/17.
 */

public class SendRequestFragmentView {


    public static String responseValue;

    private static MyCallback<Boolean> mCallback;
    static ProgressDialog progressBar;
    private static int progressBarStatus;
    private static Handler progressBarHandler = new Handler();
    private static long fileSize;

    private static ProgressDialog dialog;

    private static ArrayList<GeneralInformation> generalInformationArrayList;
    private static ArrayList<MatchUserChoice> matchUserChoiceArrayList;
    private static ArrayList<MatchUserChoice> otherInformationArrayList;


    private static ArrayList<UserProfileChild> parentChildItemList, brotherChildItemList,
            sisterChildItemList, childItemList, otherCHildItemList;


    private static ArrayList<UserProfileParent> mainparentItemList;


    public static void fetchUserProfileDetailsResponse(final String url,
                                                       final Context context,
                                                       final MyCallback<Boolean> mCallback,
                                                       TextView userProfileDescriptionText,
                                                       RecyclerView generalInfoRecyclerView,
                                                       RecyclerView matchUserChoiceRecyclerView,
                                                       RecyclerView otherInfoRecylerView,
                                                       ImageView profileViewerPersonImageView,
                                                       ImageView userProfileImage,
                                                       RecyclerView familyMemberInfoRecylerView) {


        parentChildItemList = new ArrayList<UserProfileChild>();
        brotherChildItemList = new ArrayList<UserProfileChild>();
        sisterChildItemList = new ArrayList<UserProfileChild>();
        childItemList = new ArrayList<UserProfileChild>();
        otherCHildItemList = new ArrayList<UserProfileChild>();

        mainparentItemList = new ArrayList<UserProfileParent>();

        ResponseThread responseThread = new ResponseThread(url,
                context,
                mCallback,
                userProfileDescriptionText,
                generalInfoRecyclerView,
                matchUserChoiceRecyclerView,
                otherInfoRecylerView,
                profileViewerPersonImageView,
                userProfileImage,
                familyMemberInfoRecylerView);
        Thread response = new Thread(responseThread);
        response.start();


    }


    public static class ResponseThread implements Runnable {

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


        public ResponseThread(String url,
                              Context context,
                              MyCallback<Boolean> callback,
                              TextView userProfileDescriptionText,
                              RecyclerView generalInfoRecyclerView,
                              RecyclerView matchUserChoiceRecyclerView,
                              RecyclerView otherInfoRecylerView,
                              ImageView profileViewerPersonImageView,
                              ImageView userProfileImage,
                              RecyclerView familyMemberInfoRecylerView) {


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

                        setUserDetailsInfo(userProfile, userProfileDescriptionText);
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


                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (callback != null) {
                    callback.onComplete(true); // will call onComplete() on MyActivity once the job is done
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private static void setDataonOtherInfoRecylerView(Context context,
                                                      UserProfile userProfile,
                                                      RecyclerView otherInfoRecylerView) {
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
                context, otherInformationArrayList));

    }

    public static void setUserDetailsInfo(UserProfile userProfile, TextView view) {


        if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
            view.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
        }

    }


    public static void setUserImage(UserProfile userProfile, View view) {

    }

    private static void setDataonFamilyMemberInfoRecylerView(Context context,
                                                             UserProfile userProfile,
                                                             RecyclerView familyMemberInfoRecylerView) {


        if (userProfile.getProfile().getFamilyMembers().size() > 0) {


            for (int i = 0; i < userProfile.getProfile().getFamilyMembers().size(); i++) {


                //set parent item
                if (userProfile.getProfile().getFamilyMembers().get(i).getRelation()
                        .equals("বাবা")) {

                    parentChildItemList.add(new UserProfileChild("বাবা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getName())
                                    + ","
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getOccupation())
                                    + ","
                                   /* + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getDesignation())
                                    + ","*/
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getInstitute())
                    ));
                }

                if (userProfile.getProfile().getFamilyMembers().get(i).getRelation()
                        .equals("মা")) {

                    parentChildItemList.add(new UserProfileChild("মা",
                            checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getName())
                                    + ","
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getOccupation())
                                    + ","
                                    /*+ checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getDesignation())
                                    + ","*/
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getInstitute())
                    ));


                }

                if (userProfile.getProfile().getFamilyMembers().get(i).getRelation()
                        .equals("ভাই")) {

                    brotherChildItemList.add(new UserProfileChild("ভাই",
                            checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getName())
                                    + ","
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getOccupation())
                                    + ","
                                   /* + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getDesignation())
                                    + ","*/
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getInstitute())
                    ));


                }


                if (userProfile.getProfile().getFamilyMembers().get(i).getRelation()
                        .equals("বোন")) {

                    brotherChildItemList.add(new UserProfileChild("বোন",
                            checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getName())
                                    + ","
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getOccupation())
                                    + ","
                                   /* + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getDesignation())
                                    + ","*/
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getInstitute())
                    ));


                }

                if (userProfile.getProfile().getFamilyMembers().get(i).getRelation()
                        .equals("সন্তান")) {

                    brotherChildItemList.add(new UserProfileChild("সন্তান",
                            checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getName())
                                    + ","
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getOccupation())
                                    + ","
                                    /*+ checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getDesignation())
                                    + ","*/
                                    + checkNullField(userProfile.getProfile().getFamilyMembers().get(i).
                                    getInstitute())
                    ));

                }


            }

        }


        if (parentChildItemList.size() > 0) {
            mainparentItemList.add(new UserProfileParent("বাবা-মা", parentChildItemList));

        }

        if (brotherChildItemList.size() > 0) {
            mainparentItemList.add(new UserProfileParent("ভাই(" + brotherChildItemList.size() + ")"
                    , brotherChildItemList));

        } else {

            brotherChildItemList.add(new UserProfileChild("ভাই", "nai"));
            mainparentItemList.add(new UserProfileParent("ভাই", brotherChildItemList));


        }

        if (sisterChildItemList.size() > 0) {
            mainparentItemList.add(new UserProfileParent("বোন(" + sisterChildItemList.size() + ")"
                    , sisterChildItemList));

        } else {

            sisterChildItemList.add(new UserProfileChild("bon", "nai"));
            mainparentItemList.add(new UserProfileParent("বোন", sisterChildItemList));

        }

        if (childItemList.size() > 0) {

            mainparentItemList.add(new UserProfileParent("সন্তান(" + childItemList.size() + ")"
                    , childItemList));

        } else {
            childItemList.add(new UserProfileChild("সন্তান", "nai"));
            mainparentItemList.add(new UserProfileParent("সন্তান", childItemList));

        }

        familyMemberInfoRecylerView.setAdapter(new UserProfileExpenadlbeAdapter(context,
                mainparentItemList,
                false));

    }


    public static void setDataonGeneralInfoRecylerView(Context activity, UserProfile userProfile, RecyclerView view) {
        Log.i("userdetails", "recyler method");


        generalInformationArrayList = new ArrayList<GeneralInformation>();

        generalInformationArrayList.add(new GeneralInformation(
                Utils.convertEnglishDigittoBangla(
                        userProfile.getProfile().getPersonalInformation().getAge()) + " বছর"
                        + "," +
                        Utils.convertEnglishDigittoBangla(userProfile.getProfile().getPersonalInformation().getHeightFt())
                        + "'" +
                        Utils.convertEnglishDigittoBangla(userProfile.getProfile().getPersonalInformation().getHeightInc())
                        + "\""
                        + "," +
                        checkNullField(userProfile.getProfile().getProfileReligion().getReligion())
                        + "(" +
                        checkNullField(userProfile.getProfile().getProfileReligion().getCast())
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
                            checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())
                    )

                    , R.drawable.hom));

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

                    education = education + checkNullField(educationInformation.getName()) + "," +
                            checkNullField(educationInformation.getInstitution()) + "," +
                            checkNullField(Utils.convertEnglishYearDigittoBangla(
                                    educationInformation.getPassingYear())) + "," +
                            checkNullField(educationInformation.getSubject()) + ":";
                    Log.i("edu", education);

                } else {
                    education = education + checkNullField(educationInformation.getName()) + "," +
                            checkNullField(educationInformation.getInstitution()) + "," +
                            checkNullField(educationInformation.getSubject()) + ":";

                    Log.i("edu", education);

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
                            + "," +
                            checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())
                    , R.drawable.hel2));

        }


        if ((!(checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor()) +
                checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals(""))
                & (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))
                ) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor())
                            + "," +
                            checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())
                    , R.drawable.hel));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()))
                .equals("")) {


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus())
                    , R.drawable.mar));
        }


       /* if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()) +
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

        }*/


        view.setAdapter(new GeneralInformationAdapter(
                activity, generalInformationArrayList));
        Log.i("userdetails", view.toString() + " " + generalInformationArrayList.size());

    }

    public static void setDataonMatchUserChoiceRecylerView(Context context,
                                                           UserProfile userProfile,
                                                           RecyclerView view) {


        matchUserChoiceArrayList = new ArrayList<>();


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
        if (!(checkNullField(userProfile.getProfile().getMatchingAttributes().getHealth())).equals("")) {
            matchUserChoiceArrayList.add(new MatchUserChoice("marital status"
                    , userProfile.getProfile().getMatchingAttributes().getMaritalStatus()));

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


        view.setAdapter(new MatchUserChoiceAdapter(context, matchUserChoiceArrayList));

    }


    private static String checkNullField(String value) {

        if (value == null) {
            return "";
        } else {
            return value;
        }

    }


}
