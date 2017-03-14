package com.nascenia.biyeta.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.GeneralInformationAdapter;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.newuserprofile.EducationInformation;
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
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList;


    public static void fetchUserProfileDetailsResponse(final String url,
                                                       final Context context,
                                                       final MyCallback<Boolean> mCallback) {


        ResponseThread responseThread = new ResponseThread(url, context, mCallback);
        Thread response = new Thread(responseThread);
        response.start();


        /*  dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(true);
        dialog.show();



      new Thread(new Runnable() {
            public void run() {
                try {
                    Response response = new ResourceProvider((Activity) context).fetchGetResponse(url);
                    ResponseBody responseBody = response.body();
                    final String responseValue = responseBody.string();
                    Log.i("responsedata", "response value: " + responseValue + " ");
                    responseBody.close();
                    //   final UserProfile userProfile = new Gson().fromJson(responseValue, UserProfile.class);

                    //   stopProgressDialog(dialog,context);

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("taskon", "run");

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }

                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(context, "Data reteriving problem", Toast.LENGTH_LONG).show();
                    //stopProgressDialog(dialog,context);
                }

            }
        }).start();*/

        // return responseValue;

    }


    public static class ResponseThread implements Runnable {

        private MyCallback<Boolean> callback;
        private String url;
        private Context context;


        public ResponseThread(String url, Context context, MyCallback<Boolean> mCallback) {
            this.url = url;
            this.callback = mCallback;
            this.context = context;
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
                //  final UserProfile userProfile = new Gson().fromJson(responseValue, UserProfile.class);


                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("taskon", "run");

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


    public static void setUserDetailsInfo(UserProfile userProfile, TextView view) {

        Log.i("userdetails", "user" + userProfile + " " + userProfile.getProfile().getPersonalInformation().getAboutYourself() + "");

        if (userProfile.getProfile().getPersonalInformation().getAboutYourself() != null) {
            view.setText(userProfile.getProfile().getPersonalInformation().getAboutYourself());
        }

    }


    public static void setUserImage(UserProfile userProfile, View view) {

    }


    public static void setDataonGeneralInfoRecylerView(Context activity, UserProfile userProfile, RecyclerView view) {

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


                education = education + checkNullField(educationInformation.getName()) +
                        checkNullField(educationInformation.getInstitution()) +
                        checkNullField(educationInformation.getPassingYear()) +
                        checkNullField(educationInformation.getSubject()) + ",";


            }
        }

         /*if (!(checkNullField(userProfile.getProfile().getEducationInformation().getHighestDegree()) +
        checkNullField(userProfile.getProfile().getEducationInformation().getInstitution())).
        equals("")){


            generalInformationArrayList.add(new GeneralInformation(

                    checkNullField(userProfile.getProfile().getEducationInformation().getHighestDegree())
                            + "," +
                            checkNullField(userProfile.getProfile().getEducationInformation().getInstitution())
                    , R.drawable.edu));

        }*/


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


    }

    public static void setDataonMatchUserChoiceRecylerView(UserProfile userProfile, View view) {

    }


    private static String checkNullField(String value) {


        if (value == null) {
            return "";
        } else {
            return value;
        }

    }

}
