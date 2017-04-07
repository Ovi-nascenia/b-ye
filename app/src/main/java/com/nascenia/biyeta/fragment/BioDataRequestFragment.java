package com.nascenia.biyeta.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.nascenia.biyeta.activity.HomeScreen;
import com.nascenia.biyeta.activity.NewUserProfileActivity;
import com.nascenia.biyeta.activity.SendRequestActivity;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.model.newuserprofile.RequestStatus;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.SendRequestFragmentView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.nascenia.biyeta.R.id.emoIconImage;

/**
 * Created by saiful on 3/10/17.
 */

public class BioDataRequestFragment extends Fragment implements MyCallback<Boolean> {


    private View _baseView;

    private TextView noListAvailable;

    private ImageView cancelImageView, waitImageView, acceptImageView, emoIconImageView,
            mobileCheckIconImageView, fbCheckIconImageView, mailCheckIconImageView;
    private TextView cancelTextView, waitTextView, acceptTextView, communicationTagTextView;
    private CardView communicationCardLayout;

    private ImageView userProfileImage, selfImageView, favoriteImageView;
    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView, otherInfoRecylerView,
            familyMemberInfoRecylerView;
    private TextView userProfileDescriptionText, userNameTextView;
    private ImageView profileViewerPersonImageView;


    public static List<Integer> profileRequestSenderIdsList = null;

    private RequestSenderIds requestSenderIds;
    private TextView biodataNotificationCounterTextview;

    private int currentId;
    private int urlResponseId;

    /*this button identifiy which button is clicked like accept or reject btn
    *
    * if this variable value is set 1, that means allow btn clicked
    * if this variable value is set 0, that means reject btn clicked
    *if this variable value is set 4, that means onstart method called
    *
    * */
    private int clickableButtonIdentifier = 555;
    private String token;
    private SharePref sharePref;
    private int id;

    private Response responseStatus;

    private LinearLayout layoutSendSmiley;
    private UserProfile userProfile;

    private RelativeLayout bottomRelativeLayout;
    private CoordinatorLayout coordnatelayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_communication_request, container, false);

        sharePref = new SharePref(getActivity());
        token = sharePref.get_data("token");

        if (getArguments().getSerializable("REQUEST_RESPONSE_OBJ") != null) {
            requestSenderIds = (RequestSenderIds) getArguments().getSerializable("REQUEST_RESPONSE_OBJ");
            profileRequestSenderIdsList = requestSenderIds.getRequests().getProfileRequestSenderIds();

        }

        noListAvailable = (TextView) _baseView.findViewById(R.id.no_data);
        noListAvailable.setVisibility(View.GONE);
        return _baseView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {

        biodataNotificationCounterTextview = (TextView) getActivity().findViewById(
                R.id.biodata_notification_textview);

        bottomRelativeLayout = (RelativeLayout) _baseView.findViewById(R.id.r1);
        coordnatelayout = (CoordinatorLayout) _baseView.findViewById(R.id.coordnatelayout);

        layoutSendSmiley = (LinearLayout) _baseView.findViewById(R.id.layoutSendSmiley);
        emoIconImageView = (ImageView) _baseView.findViewById(emoIconImage);
        favoriteImageView = (ImageView) _baseView.findViewById(R.id.likeImage);
        layoutSendSmiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), userProfile.getProfile().isIsSmileSent() + "", Toast.LENGTH_LONG).show();

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
                    //favoriteImageView.setEnabled(false);
                    userProfile.getProfile().setIsFavorite(true);
                    favoriteImageView.setImageResource(R.drawable.red_favorite);
                } else {

                    NetWorkOperation.postMethod(getActivity(),
                            Utils.UNFAVORITE_URL,
                            userProfile.getProfile().getPersonalInformation().getId() + "",
                            "Authorization",
                            "Token token=" + sharePref.get_data("token"));

                    userProfile.getProfile().setIsFavorite(false);
                    favoriteImageView.setImageResource(R.drawable.favorite);


                }

            }
        });

        mobileCheckIconImageView = (ImageView) _baseView.findViewById(R.id.mobile_check_icon);
        fbCheckIconImageView = (ImageView) _baseView.findViewById(R.id.fb_check_icon);
        mailCheckIconImageView = (ImageView) _baseView.findViewById(R.id.mail_check_icon);


        communicationTagTextView = (TextView) _baseView.findViewById(R.id.communication_tag_textview);
        communicationTagTextView.setVisibility(View.GONE);
        communicationCardLayout = (CardView) _baseView.findViewById(R.id.communication_card_layout);
        communicationCardLayout.setVisibility(View.GONE);


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


        cancelTextView = (TextView) _baseView.findViewById(R.id.cancel_textview);
        waitTextView = (TextView) _baseView.findViewById(R.id.wait_textview);
        acceptTextView = (TextView) _baseView.findViewById(R.id.accept_textview);

        waitImageView.setVisibility(View.GONE);
        waitTextView.setVisibility(View.GONE);

        setViewMargins(getActivity(),
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT),
                45,
                0,
                0,
                0,
                cancelImageView);


        RelativeLayout.LayoutParams cancelTextViewParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        cancelTextViewParam.addRule(RelativeLayout.BELOW, R.id.cancel_imageview);


        setViewMargins(getActivity(), cancelTextViewParam,
                45,
                0,
                0,
                0,
                cancelTextView);


        //.....................................//

        RelativeLayout.LayoutParams acceptImageViewParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        acceptImageViewParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        acceptImageViewParam.addRule(RelativeLayout.CENTER_HORIZONTAL);


        setViewMargins(getActivity(),
                acceptImageViewParam,
                0,
                0,
                65,
                0,
                acceptImageView);


        RelativeLayout.LayoutParams acceptTextViewParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        acceptTextViewParam.addRule(RelativeLayout.BELOW, R.id.accept_imageview);
        acceptTextViewParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


        setViewMargins(getActivity(), acceptTextViewParam,
                0,
                0,
                65,
                0,
                acceptTextView);


    }

    @Override
    public void onStart() {
        super.onStart();

        initView();

        if (BioDataRequestFragment.profileRequestSenderIdsList.size() > 0) {

            setRequestView(BioDataRequestFragment.profileRequestSenderIdsList.get(0));
        } else {

            noListAvailable.setVisibility(View.VISIBLE);
        }


    }


    private void setRequestView(int id) {

        currentId = id;

        new SendRequestFragmentView() {
            @Override
            public void loadNextProfile(int clickBtnId, int userProfileRequestId) {


                if (clickBtnId == 1) {

                    new SendResponseTask().execute(Utils.PROFILE_REQUEST_URL +
                            userProfileRequestId + "/accept");
                    processResponse(clickBtnId);

                } else if (clickBtnId == 0) {


                    new SendResponseTask().execute(Utils.PROFILE_REQUEST_URL +
                            userProfileRequestId + "/reject");

                    processResponse(clickBtnId);
                }


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
                Utils.BIODATA_REQUEST_FRAGEMNT_CLASS,
                userNameTextView,
                coordnatelayout,
                bottomRelativeLayout,
                acceptImageView,
                cancelImageView
        );


    }

    public void setViewMargins(Context con, ViewGroup.LayoutParams params,
                               int left, int top, int right, int bottom, View view) {

        final float scale = con.getResources().getDisplayMetrics().density;
        // convert the DP into pixel
        int pixel_left = (int) (left * scale + 0.5f);
        int pixel_top = (int) (top * scale + 0.5f);
        int pixel_right = (int) (right * scale + 0.5f);
        int pixel_bottom = (int) (bottom * scale + 0.5f);

        ViewGroup.MarginLayoutParams s = (ViewGroup.MarginLayoutParams) params;
        s.setMargins(pixel_left, pixel_top, pixel_right, pixel_bottom);

        view.setLayoutParams(params);
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
                    //favoriteImageView.setEnabled(false);
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
        SendRequestActivity.biodataRequestCounter--;

        if (!BioDataRequestFragment.profileRequestSenderIdsList.isEmpty()) {

            biodataNotificationCounterTextview.setText(
                    SendRequestActivity.biodataRequestCounter + "");

            /*if (BioDataRequestFragment.profileRequestSenderIdsList.size() == 1)
                urlResponseId = BioDataRequestFragment.profileRequestSenderIdsList.get(0);*/


            if (BioDataRequestFragment.profileRequestSenderIdsList.size() > 0) {
                urlResponseId = currentId;
                clickableButtonIdentifier = btnClickIdentifier;
                if (BioDataRequestFragment.profileRequestSenderIdsList.size() > 1) {
                    BioDataRequestFragment.profileRequestSenderIdsList.remove(0);
                    setRequestView(BioDataRequestFragment.profileRequestSenderIdsList.get(0));

                } else if (BioDataRequestFragment.profileRequestSenderIdsList.size() == 1) {

                    id = BioDataRequestFragment.profileRequestSenderIdsList.get(0);
                    BioDataRequestFragment.profileRequestSenderIdsList.remove(0);

                    coordnatelayout.setVisibility(View.INVISIBLE);
                    bottomRelativeLayout.setVisibility(View.INVISIBLE);
                    noListAvailable.setVisibility(View.VISIBLE);
                    Utils.ShowAlert(getActivity(), "আপনার আর কোন অনুরোধ নেই");
                    //setRequestView(id);
                }
            } else {
                // setRequestView(currentId);
                coordnatelayout.setVisibility(View.INVISIBLE);
                bottomRelativeLayout.setVisibility(View.INVISIBLE);
                noListAvailable.setVisibility(View.VISIBLE);
                Utils.ShowAlert(getActivity(), "আপনার আর কোন অনুরোধ নেই");
            }
        } else {
            coordnatelayout.setVisibility(View.INVISIBLE);
            bottomRelativeLayout.setVisibility(View.INVISIBLE);
            noListAvailable.setVisibility(View.VISIBLE);
            Utils.ShowAlert(getActivity(), "আপনার আর কোন অনুরোধ নেই");
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
            try {
                JSONObject headObj = new JSONObject(s);

                Toast.makeText(getActivity(),
                        headObj.getJSONArray("message").getJSONObject(0).getString("detail"),
                        Toast.LENGTH_LONG).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
