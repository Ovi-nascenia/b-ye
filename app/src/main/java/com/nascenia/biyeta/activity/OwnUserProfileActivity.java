package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.UserProfileExpenadlbeAdapter;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.service.ResourceProvider;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.nascenia.biyeta.view.SendRequestFragmentView.checkNullField;

/**
 * Created by saiful on 4/2/17.
 */

public class OwnUserProfileActivity extends AppCompatActivity {

    private RecyclerView personalInfoRecylerView;
    private RecyclerView educationRecylerView;
    private RecyclerView professionRecyclerView;
    private RecyclerView parentsRecyclerView;
    private RecyclerView brotherRecyclerView;
    private RecyclerView sisterRecyclerView;
    private RecyclerView childRecyclerView;
    private RecyclerView otherRelativeInfoRecyclerView;
    private RecyclerView otherInformationRecyclerView;

    private UserProfile userProfile;

    private int familyMemberCounter;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_profile_layout);


        initView();

        fetchUserProfileInfo();
    }

    private void fetchUserProfileInfo() {
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Response response = new ResourceProvider(OwnUserProfileActivity.this).
                            fetchGetResponse("http://test.biyeta.com/api/v1/profiles/view");
                    ResponseBody responseBody = response.body();
                    final String responseValue = responseBody.string();
                    Log.i("ownresponsevalue", responseValue);
                    responseBody.close();
                    userProfile = new Gson().fromJson(responseValue, UserProfile.class);


                    OwnUserProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
                                userProfileDescriptionText.setText(userProfile.getProfile().
                                        getPersonalInformation().getAboutYourself());
                            }


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
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }

    private void setUserOwnImage(UserProfile userProfile) {


        if (userProfile.getProfile().getPersonalInformation().getImage() != null) {


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


        } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))) {
            userProfileImage.setImageResource(R.drawable.profile_icon_male);

        } else if ((userProfile.getProfile().getPersonalInformation().getImage() == null) &
                (userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))) {
            userProfileImage.setImageResource(R.drawable.profile_icon_female);

        } else {
        }


    }

    private void setDataOnOtherInfoRecylerView(UserProfile userProfile) {

         /*..................................other inormation block start.........................................*/


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getFasting()).equals(""))) {

            otherInfoChildItemList.add(new UserProfileChild("রোজা রাখেন?",
                    userProfile.getProfile().getOtherInformation().getFasting()));

        }


        if (!(checkNullField(userProfile.getProfile().getOtherInformation().getPrayer()).equals(""))) {

            otherInfoChildItemList.add(new UserProfileChild("নামাজ পড়েন?",
                    userProfile.getProfile().getOtherInformation().getPrayer()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getJobAfterMarriage()).equals("")))) {


            otherInfoChildItemList.add(new UserProfileChild("বিয়ের পরে চাকরি?",
                    userProfile.getProfile().getOtherInformation().getJobAfterMarriage()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.FEMALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getHijab()).equals("")))) {


            otherInfoChildItemList.add(new UserProfileChild("হিজাব পড়েন?",
                    userProfile.getProfile().getOtherInformation().getHijab()));

        }


        if ((userProfile.getProfile().getPersonalInformation().getGender().equals(Utils.MALE_GENDER))

                & (!(checkNullField(userProfile.getProfile().getOtherInformation().getOwnHouse()).equals("")))) {


            otherInfoChildItemList.add(new UserProfileChild("নিজের বাসা আছে?",
                    userProfile.getProfile().getOtherInformation().getOwnHouse()));

        }


        if (otherInfoChildItemList.size() > 0) {
            otherInfoChildItemHeader.add(new UserProfileParent(
                    "বিবিধ প্রশ্নের উত্তর"
                    , otherInfoChildItemList));

            otherInformationRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                    otherInfoChildItemHeader,
                    true));

        }

            /*..................................other inormation block end.........................................*/

    }

    private void setDataOnProfessionRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getProfession() != null &&
                !(checkNullField(userProfile.getProfile().getProfession().getProfessionalGroup())).equals("")) {

            professionChildItemList.add(new UserProfileChild("professional_group",
                    userProfile.getProfile().getProfession().getProfessionalGroup()));

        }


        if (userProfile.getProfile().getProfession() != null &&
                !(checkNullField(userProfile.getProfile().getProfession().getOccupation())).equals("")) {

            professionChildItemList.add(new UserProfileChild("occupation",
                    userProfile.getProfile().getProfession().getOccupation()));

        }


        if (userProfile.getProfile().getProfession() != null &&
                !(checkNullField(userProfile.getProfile().getProfession().getDesignation())).equals("")) {

            professionChildItemList.add(new UserProfileChild("designation",
                    userProfile.getProfile().getProfession().getDesignation()));

        }


        if (userProfile.getProfile().getProfession() != null &&
                !(checkNullField(userProfile.getProfile().getProfession().getInstitute())).equals("")) {

            professionChildItemList.add(new UserProfileChild("institute",
                    userProfile.getProfile().getProfession().getInstitute()));

        }


        if (professionChildItemList.size() > 0) {

            professionChildItemHeader.add(new UserProfileParent("পেশা", professionChildItemList));
            professionRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                    professionChildItemHeader,
                    true));

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

                educationalInfoChildItemHeader.add(new UserProfileParent("শিক্ষাগত যোগ্যতা", educationalInfoChildItemList));
                educationRecylerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        educationalInfoChildItemHeader,
                        true));

            }


        }


    }

    private void setDataOnPersonalInfoRecylerView(UserProfile userProfile) {

        //add personal Information
        personalInfoChildItemList.add(new UserProfileChild("age",
                Utils.convertEnglishDigittoBangla(
                        userProfile.getProfile().getPersonalInformation().getAge()) + " বছর,"

        ));

        personalInfoChildItemList.add(new UserProfileChild("height",
                Utils.convertEnglishDigittoBangla(userProfile.getProfile().getPersonalInformation().getHeightFt())
                        + "'" +
                        Utils.convertEnglishDigittoBangla(userProfile.getProfile().getPersonalInformation().getHeightInc())
                        + "\""
        ));


        personalInfoChildItemList.add(new UserProfileChild("Religion",
                userProfile.getProfile().getProfileReligion().getReligion()
        ));

        personalInfoChildItemList.add(new UserProfileChild("Cast",
                userProfile.getProfile().getProfileReligion().getCast()
        ));


        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getCountry())).equals("")) {


            personalInfoChildItemList.add(new UserProfileChild("বর্তমান অবস্থান",
                    userProfile.getProfile().getProfileLivingIn().getCountry()
            ));
        }


        if (!(checkNullField(userProfile.getProfile().getProfileLivingIn().getLocation())).equals("")) {

            personalInfoChildItemList.add(new UserProfileChild("দেশের বাড়ি",
                    userProfile.getProfile().getProfileLivingIn().getLocation()
            ));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getSkinColor())).equals("")) {

            personalInfoChildItemList.add(new UserProfileChild("গায়ের রং",
                    userProfile.getProfile().getPersonalInformation().getSkinColor()
            ));

        }

        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getWeight())).equals("")) {

            personalInfoChildItemList.add(new UserProfileChild("উচ্চতা",
                    userProfile.getProfile().getPersonalInformation().getWeight()
            ));

        }


        if (!(checkNullField(userProfile.getProfile().getPersonalInformation().getMaritalStatus()))
                .equals("")) {

            personalInfoChildItemList.add(new UserProfileChild("বৈবাহিক অবস্থা",
                    userProfile.getProfile().getPersonalInformation().getMaritalStatus()
            ));

        }


        //add personal Child Item list to parent list
        if (personalInfoChildItemList.size() > 0) {
            personalInfoChildItemHeader.add(new UserProfileParent("ব্যাক্তিগত তথ্য", personalInfoChildItemList));

            personalInfoRecylerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                    personalInfoChildItemHeader,
                    true));
        }


    }

    private void setDataOnrFamilyMemberRecylerView(UserProfile userProfile) {


        if (userProfile.getProfile().getFamilyMembers() != null) {

        /*..................................parents block start.........................................*/
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


            //add parentchildlist data to mainparentlist
            if (parentChildItemList.size() > 0) {
                parentChildItemHeader.add(new UserProfileParent("বাবা-মা", parentChildItemList));

                parentsRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        parentChildItemHeader,
                        true));

            }

             /*..................................parents block end.........................................*/



             /*..................................brothers block start.........................................*/

            //add brother information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfBrothers() > 0) {

                familyMemberCounter = 0;
                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getBrothers().size(); i++) {
                    familyMemberCounter = i + 1;

                    brothersChildItemList.add(new UserProfileChild(
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

            //add brotherchildlist data to mainparentlist
            if (brothersChildItemList.size() > 0) {
                brothersChildItemHeader.add(new UserProfileParent(
                        "ভাই(" + Utils.convertEnglishDigittoBangla(brothersChildItemList.size()) + ")"
                        , brothersChildItemList));

                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        brothersChildItemHeader,
                        true));

            } else {

                brothersChildItemList.add(new UserProfileChild("ভাই", "কোন ভাই নেই"));
                brothersChildItemHeader.add(new UserProfileParent("ভাই", brothersChildItemList));
                brotherRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        brothersChildItemHeader,
                        true));

            }

            /*..................................brothers block end.........................................*/


            /*..................................sisters block start.........................................*/

            if (userProfile.getProfile().getFamilyMembers().getNumberOfSisters() > 0) {

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getSisters().size(); i++) {

                    familyMemberCounter = i + 1;
                    sistersChildItemList.add(new UserProfileChild(
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

            //add sisterchildlist data to mainparentlist
            if (sistersChildItemList.size() > 0) {
                sistersChildItemHeader.add(new UserProfileParent(
                        "বোন(" + Utils.convertEnglishDigittoBangla(sistersChildItemList.size()) + ")"
                        , sistersChildItemList));

                sisterRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        sistersChildItemHeader,
                        true));

            } else {

                sistersChildItemList.add(new UserProfileChild("বোন", "কোন বোন নেই"));
                sistersChildItemHeader.add(new UserProfileParent("বোন", sistersChildItemList));
                sisterRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        sistersChildItemHeader,
                        true));

            }
            /*..................................sisters block end.........................................*/


            /*..................................childs block start.........................................*/

            //add child information
            if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                    userProfile.getProfile().getFamilyMembers().isChildLivesWithYou()) {

                childsChildItemList.add(new UserProfileChild("সন্তান",
                        Utils.convertEnglishDigittoBangla(
                                userProfile.getProfile().getFamilyMembers().getNumberOfChild())
                                + " জন সন্তান, তার সাথে থাকে"));

            } else if (userProfile.getProfile().getFamilyMembers().getNumberOfChild() > 0 &&
                    !(userProfile.getProfile().getFamilyMembers().isChildLivesWithYou())) {

                childsChildItemList.add(new UserProfileChild("সন্তান",
                        Utils.convertEnglishDigittoBangla(
                                userProfile.getProfile().getFamilyMembers().getNumberOfChild())
                                + " জন সন্তান, তার সাথে থাকে না"));
            }


            //add childlist data to mainparentlist
            if (childsChildItemList.size() > 0) {

                childsChildItemHeader.add(new UserProfileParent("সন্তান"
                        , childsChildItemList));

                childRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        childsChildItemHeader,
                        true));
            } else {
                childsChildItemList.add(new UserProfileChild("সন্তান", "কোন সন্তান নেই"));
                childsChildItemHeader.add(new UserProfileParent("সন্তান", childsChildItemList));

                childRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        childsChildItemHeader,
                        true));

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

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKakas().size(); i++) {

                    familyMemberCounter = i + 1;
                    otherRelativeChildItemList.add(new UserProfileChild(
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
                    otherRelativeChildItemList.add(new UserProfileChild(
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
                    otherRelativeChildItemList.add(new UserProfileChild(
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

                familyMemberCounter = 0;

                for (int i = 0; i < userProfile.getProfile().getFamilyMembers().getKhalus().size(); i++) {

                    familyMemberCounter = i + 1;
                    otherRelativeChildItemList.add(new UserProfileChild(
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


            if (otherRelativeChildItemList.size() > 0) {

                otherRelativeChildItemHeader.add(new UserProfileParent(
                        "অন্যান্য(" + Utils.convertEnglishDigittoBangla(otherRelativeChildItemList.size()) + ")"
                        , otherRelativeChildItemList));

                otherRelativeInfoRecyclerView.setAdapter(new UserProfileExpenadlbeAdapter(getBaseContext(),
                        otherRelativeChildItemHeader,
                        true));
            }


            /*..................................other relative block end.........................................*/


        }


    }

    private void initView() {


        progressDialog = new ProgressDialog(OwnUserProfileActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);

        userProfileDescriptionText = (TextView) findViewById(R.id.userProfileDescriptionText);

        userProfileImage = (ImageView) findViewById(R.id.user_profile_image);


        personalInfoRecylerView = (RecyclerView) findViewById(R.id.user_general_info_recycler_view);
        personalInfoRecylerView.setLayoutManager(new LinearLayoutManager(this));

        educationRecylerView = (RecyclerView) findViewById(R.id.education_recylerView);
        educationRecylerView.setLayoutManager(new LinearLayoutManager(this));

        professionRecyclerView = (RecyclerView) findViewById(R.id.profession_recylerView);
        professionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        parentsRecyclerView = (RecyclerView) findViewById(R.id.parents_recylerView);
        parentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        brotherRecyclerView = (RecyclerView) findViewById(R.id.brother_recylerView);
        brotherRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sisterRecyclerView = (RecyclerView) findViewById(R.id.sister_recylerView);
        sisterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        childRecyclerView = (RecyclerView) findViewById(R.id.child_recylerView);
        childRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        otherRelativeInfoRecyclerView = (RecyclerView) findViewById(R.id.other_relative_recylerView);
        otherRelativeInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        otherInformationRecyclerView = (RecyclerView) findViewById(R.id.other_info_recylerView);
        otherInformationRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void backBtnAction(View v) {
        finish();
    }
}
