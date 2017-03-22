package com.nascenia.biyeta.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.model.newuserprofile.RequestStatus;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.view.SendRequestFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saiful on 3/10/17.
 */

public class BioDataRequestFragment extends Fragment implements MyCallback<Boolean> {


    private View _baseView;

    private ImageView cancelImageView, waitImageView, acceptImageView;
    private TextView cancelTextView, waitTextView, acceptTextView, communicationTagTextView;
    private CardView communicationCardLayout;

    private ImageView userProfileImage;
    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView, otherInfoRecylerView,
            familyMemberInfoRecylerView;
    private TextView userProfileDescriptionText;
    private ImageView profileViewerPersonImageView;


    private String url = "http://test.biyeta.com/api/v1/profiles/420";

    public static List<Integer> profileRequestSenderIds = new ArrayList<Integer>();

    Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_communication_request, container, false);

        bundle = getArguments();

        if (bundle.getSerializable("REQUEST_RESPONSE_OBJ") != null) {


        }

        return _baseView;
    }


    private void initView() {

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
        Log.i("userdetails", generalInfoRecyclerView.toString());
        SendRequestFragmentView.fetchUserProfileDetailsResponse(
                url,
                getActivity(),
                this,
                userProfileDescriptionText,
                generalInfoRecyclerView,
                matchUserChoiceRecyclerView,
                otherInfoRecylerView,
                profileViewerPersonImageView,
                userProfileImage,
                familyMemberInfoRecylerView
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
    public void onComplete(Boolean result) {

    }
}