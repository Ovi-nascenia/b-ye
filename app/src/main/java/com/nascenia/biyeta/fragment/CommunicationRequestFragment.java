package com.nascenia.biyeta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.view.SendRequestFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saiful on 3/10/17.
 */

public class CommunicationRequestFragment extends Fragment implements MyCallback<Boolean> {


    private View _baseView;

    private ImageView userProfileImage;
    private ImageView cancelImageView, waitImageView, acceptImageView;
    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView, otherInfoRecylerView,
            familyMemberInfoRecylerView;
    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();
    private TextView userProfileDescriptionText;
    private ImageView profileViewerPersonImageView;


    private String url = "http://test.biyeta.com/api/v1/profiles/420";
    private RequestSenderIds requestSenderIds;
    public static List<Integer> communicationRequestSenderIdsList = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_communication_request, container, false);


        if (getArguments().getSerializable("REQUEST_RESPONSE_OBJ") != null) {
            requestSenderIds = (RequestSenderIds) getArguments().getSerializable("REQUEST_RESPONSE_OBJ");
            communicationRequestSenderIdsList = requestSenderIds.getRequests().getCommunicationRequestSenderIds();

        }


        initView();

        return _baseView;
    }

    private void initView() {


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


    }


    @Override
    public void onStart() {
        super.onStart();


        initView();

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

    @Override
    public void onComplete(Boolean result, Integer id) {

    }
}
