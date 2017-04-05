package com.nascenia.biyeta.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.SendRequestActivity;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.SendRequestFragmentView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

import static com.nascenia.biyeta.R.id.emoIconImage;

/**
 * Created by saiful on 3/10/17.
 */

public class CommunicationRequestFragment extends Fragment implements MyCallback<Boolean>, View.OnClickListener {


    private View _baseView;

    RelativeLayout relativeLayoutFullFrame;
    TextView noListAvailable;


    private ImageView userProfileImage, cancelImageView, waitImageView, acceptImageView, selfImageView,
            favoriteImageView, emoIconImageView, mobileCheckIconImageView,
            fbCheckIconImageView, mailCheckIconImageView;

    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView, otherInfoRecylerView,
            familyMemberInfoRecylerView;
    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();
    private TextView userProfileDescriptionText, communicatiodataNotificationCounterTextview,
            userNameTextView;

    private ImageView profileViewerPersonImageView;


    // private String url = "http://test.biyeta.com/api/v1/profiles/420";
    private RequestSenderIds requestSenderIds;
    public static List<Integer> communicationRequestSenderIdsList = null;

    private int currentId;
    private int urlResponseId;

    /*this button identifiy which button is clicked like accept or reject btn
    *
    * if this variable value is set 1, that means allow btn clicked
    * if this variable value is set 0, that means reject btn clicked
    *if this variable value is set 4, that means onstart method called
    *
    * */
    private int clickableButtonIdentifier = 555, id;
    private String token;
    private SharePref sharePref;

    private Response responseStatus;

    private LinearLayout layoutSendSmiley;
    private UserProfile userProfile;

    private RelativeLayout bottomRelativeLayout;
    private CoordinatorLayout coordnatelayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_communication_request, container, false);
        sharePref = new SharePref(getActivity());
        token = sharePref.get_data("token");

        if (getArguments().getSerializable("REQUEST_RESPONSE_OBJ") != null) {
            requestSenderIds = (RequestSenderIds) getArguments().getSerializable("REQUEST_RESPONSE_OBJ");
            communicationRequestSenderIdsList = requestSenderIds.getRequests().getCommunicationRequestSenderIds();

        }


        initView();
        relativeLayoutFullFrame = (RelativeLayout) _baseView.findViewById(R.id.communtication_request_layout);
        noListAvailable = (TextView) _baseView.findViewById(R.id.no_data);

        relativeLayoutFullFrame.setVisibility(View.VISIBLE);
        noListAvailable.setVisibility(View.GONE);


        return _baseView;
    }

    private void initView() {


        communicatiodataNotificationCounterTextview = (TextView) getActivity().
                findViewById(R.id.communication_notification_textview);


        bottomRelativeLayout = (RelativeLayout) _baseView.findViewById(R.id.r1);
        coordnatelayout = (CoordinatorLayout) _baseView.findViewById(R.id.coordnatelayout);

        layoutSendSmiley = (LinearLayout) _baseView.findViewById(R.id.layoutSendSmiley);
        emoIconImageView = (ImageView) _baseView.findViewById(emoIconImage);
        favoriteImageView = (ImageView) _baseView.findViewById(R.id.likeImage);


        layoutSendSmiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userProfile.getProfile().isIsSmileSent()) {

                    NetWorkOperation.postMethod(getActivity(),
                            Utils.SEND_SMILE_URL,
                            userProfile.getProfile().getPersonalInformation().getId() + "",
                            "Authorization",
                            "Token token=" + sharePref.get_data("token"));
                    layoutSendSmiley.setEnabled(false);
                    emoIconImageView.setImageResource(R.drawable.red_smile);


                }
            }
        });


        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userProfile.getProfile().isIsFavorite()) {

                    NetWorkOperation.postMethod(getActivity(),
                            Utils.FAVORITE_URL,
                            userProfile.getProfile().getPersonalInformation().getId() + "",
                            "Authorization",
                            "Token token=" + sharePref.get_data("token"));
                    favoriteImageView.setEnabled(false);
                    favoriteImageView.setImageResource(R.drawable.red_favorite);
                }

            }
        });


        mobileCheckIconImageView = (ImageView) _baseView.findViewById(R.id.mobile_check_icon);
        fbCheckIconImageView = (ImageView) _baseView.findViewById(R.id.fb_check_icon);
        mailCheckIconImageView = (ImageView) _baseView.findViewById(R.id.mail_check_icon);

        generalInfoRecyclerView = (RecyclerView) _baseView.findViewById(R.id.user_general_info_recycler_view);
        generalInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        matchUserChoiceRecyclerView = (RecyclerView) _baseView.findViewById(R.id.match_user_choice_recyclerView);
        matchUserChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        otherInfoRecylerView = (RecyclerView) _baseView.findViewById(R.id.other_info_recylerview);
        otherInfoRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        familyMemberInfoRecylerView = (RecyclerView) _baseView.findViewById(R.id.family_info_recylerview);
        familyMemberInfoRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userProfileDescriptionText = (TextView) _baseView.findViewById(R.id.userProfileDescriptionText);

        profileViewerPersonImageView = (ImageView) _baseView.findViewById(R.id.viewer_image);
        userProfileImage = (ImageView) _baseView.findViewById(R.id.user_profile_image);
        selfImageView = (ImageView) _baseView.findViewById(R.id.self_image);

        Glide.with(getActivity())
                .load(Utils.Base_URL +
                        sharePref.get_data("profile_picture"))
                .into(selfImageView);

        userNameTextView = (TextView) _baseView.findViewById(R.id.user_name);

        cancelImageView = (ImageView) _baseView.findViewById(R.id.cancel_imageview);
        waitImageView = (ImageView) _baseView.findViewById(R.id.wait_imageview);
        acceptImageView = (ImageView) _baseView.findViewById(R.id.accept_imageview);


    }


    @Override
    public void onStart() {
        super.onStart();


        initView();

        if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() > 0) {

            setRequestView(CommunicationRequestFragment.communicationRequestSenderIdsList.get(0));
        } else {

            relativeLayoutFullFrame.setVisibility(View.GONE);
            noListAvailable.setVisibility(View.VISIBLE);

        }


    }


    private void setRequestView(int id) {

        currentId = id;
        new SendRequestFragmentView() {
            @Override
            public void loadNextProfile(int clickBtnId, int userProfileRequestId) {


                if (clickBtnId == 1) {

                    new CommunicationRequestFragment.SendResponseTask().execute(
                            Utils.COMMUNICATION_REQUEST_URL +
                                    userProfileRequestId + "/accept");


                    Log.i("requestList", userProfileRequestId + " accept ");

                } else if (clickBtnId == 0) {


                    new CommunicationRequestFragment.SendResponseTask().execute(
                            Utils.COMMUNICATION_REQUEST_URL +
                                    userProfileRequestId + "/reject");

                    Log.i("requestList", userProfileRequestId + " reject");


                }
                processResponse(clickBtnId);

            }
        }.fetchUserProfileDetailsResponse(
                Utils.PROFILES_URL + id,
                getActivity(),
                this,
                userProfileDescriptionText,
                generalInfoRecyclerView,
                matchUserChoiceRecyclerView,
                otherInfoRecylerView,
                profileViewerPersonImageView,
                userProfileImage,
                familyMemberInfoRecylerView,
                Utils.COMMUNICATION_REQUEST_FRAGEMNT_CLASS,
                userNameTextView,
                coordnatelayout,
                bottomRelativeLayout,
                acceptImageView,
                cancelImageView

        );


    }

    @Override
    public void onComplete(Boolean result, Integer id, final UserProfile userProfile) {

        this.userProfile = userProfile;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //setVerification image
                SendRequestFragmentView.setVerificationIcon(userProfile,
                        mobileCheckIconImageView,
                        fbCheckIconImageView,
                        mailCheckIconImageView);
            }
        });


        if (this.userProfile.getProfile().isIsFavorite()) {


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    favoriteImageView.setEnabled(false);
                    favoriteImageView.setImageResource(R.drawable.red_favorite);
                }
            });

        }

        if (this.userProfile.getProfile().isIsSmileSent()) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layoutSendSmiley.setEnabled(false);
                    emoIconImageView.setImageResource(R.drawable.red_smile);
                }
            });


        }


    }


    private void processResponse(int btnClickIdentifier) {
        SendRequestActivity.communicationRequestCounter--;

        if (!CommunicationRequestFragment.communicationRequestSenderIdsList.isEmpty()) {
            communicatiodataNotificationCounterTextview.setText(
                    SendRequestActivity.communicationRequestCounter + "");

            if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() == 1)
                urlResponseId = CommunicationRequestFragment.communicationRequestSenderIdsList.get(0);


            if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() > 0) {
                urlResponseId = currentId;
                clickableButtonIdentifier = btnClickIdentifier;
                if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() > 1) {
                    CommunicationRequestFragment.communicationRequestSenderIdsList.remove(0);
                    setRequestView(CommunicationRequestFragment.communicationRequestSenderIdsList.get(0));

                } else if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() == 1) {
                    id = CommunicationRequestFragment.communicationRequestSenderIdsList.get(0);

                    CommunicationRequestFragment.communicationRequestSenderIdsList.remove(0);
                    setRequestView(id);
                }
            } else {
                setRequestView(currentId);
                Utils.ShowAlert(getActivity(), "আপনার আর কোন অনুরোধ নেই");
            }
        } else {
            Utils.ShowAlert(getActivity(), "আপনার আর কোন অনুরোধ নেই");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wait_imageview:
                waitButtonAction(id);


                break;
        }
    }

    private void waitButtonAction(int btnClickIdentifier) {

        SendRequestActivity.communicationRequestCounter--;

        if (!CommunicationRequestFragment.communicationRequestSenderIdsList.isEmpty()) {
            communicatiodataNotificationCounterTextview.setText(
                    SendRequestActivity.communicationRequestCounter + "");

            if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() == 1)
                urlResponseId = CommunicationRequestFragment.communicationRequestSenderIdsList.get(0);


            if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() > 0) {
                urlResponseId = currentId;
                clickableButtonIdentifier = btnClickIdentifier;
                if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() > 1) {
                    CommunicationRequestFragment.communicationRequestSenderIdsList.remove(0);
                    CommunicationRequestFragment.communicationRequestSenderIdsList.add(urlResponseId);

                    setRequestView(CommunicationRequestFragment.communicationRequestSenderIdsList.get(0));

                } else if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() == 1) {
                    id = CommunicationRequestFragment.communicationRequestSenderIdsList.get(0);

                    CommunicationRequestFragment.communicationRequestSenderIdsList.remove(0);
                    CommunicationRequestFragment.communicationRequestSenderIdsList.add(id);
                    setRequestView(id);
                }
            } else {
                setRequestView(currentId);
                Utils.ShowAlert(getActivity(), "No more reqeust left");
            }
        } else {
            Utils.ShowAlert(getActivity(), "No more reqeust ");
        }

    }

    private class SendResponseTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

           /* Log.i("asynctaskdataFFFFFF", urls[0]);
            Log.i("asynctaskdataFFFFFF", profileRequestSenderIdsList.toString());*/


            try {


                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urls[0])
                        .addHeader("Authorization", "Token token=" + token)
                        .get()
                        .build();
                responseStatus = client.newCall(request).execute();

                return responseStatus.body().string();

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
