package com.nascenia.biyeta.fragment;

import android.os.AsyncTask;
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
import com.nascenia.biyeta.activity.SendRequestActivity;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.GeneralInformation;
import com.nascenia.biyeta.model.MatchUserChoice;
import com.nascenia.biyeta.model.RequestSenderIds;
import com.nascenia.biyeta.utils.MyCallback;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.SendRequestFragmentView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saiful on 3/10/17.
 */

public class CommunicationRequestFragment extends Fragment implements MyCallback<Boolean>, View.OnClickListener {


    private View _baseView;

    private ImageView userProfileImage;
    private ImageView cancelImageView, waitImageView, acceptImageView;
    private RecyclerView generalInfoRecyclerView, matchUserChoiceRecyclerView, otherInfoRecylerView,
            familyMemberInfoRecylerView;
    private ArrayList<GeneralInformation> generalInformationArrayList = new ArrayList<GeneralInformation>();
    private ArrayList<MatchUserChoice> matchUserChoiceArrayList = new ArrayList<MatchUserChoice>();
    private TextView userProfileDescriptionText, communicatiodataNotificationCounterTextview;
    ;
    private ImageView profileViewerPersonImageView;


    private String url = "http://test.biyeta.com/api/v1/profiles/420";
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_communication_request, container, false);


        if (getArguments().getSerializable("REQUEST_RESPONSE_OBJ") != null) {
            requestSenderIds = (RequestSenderIds) getArguments().getSerializable("REQUEST_RESPONSE_OBJ");
            communicationRequestSenderIdsList = requestSenderIds.getRequests().getCommunicationRequestSenderIds();

        }


        initView();

        return _baseView;
    }

    private void initView() {


        communicatiodataNotificationCounterTextview = (TextView) getActivity().
                findViewById(R.id.communication_notification_textview);

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

        if (CommunicationRequestFragment.communicationRequestSenderIdsList.size() > 0) {

            setRequestView(CommunicationRequestFragment.communicationRequestSenderIdsList.get(0));
        } else {
            Utils.ShowAlert(getActivity(), "No more reqeust left");
        }


    }


    private void setRequestView(int id) {

        currentId = id;
        SendRequestFragmentView.fetchUserProfileDetailsResponse(
                url + id,
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

        if (result && clickableButtonIdentifier == 1 && id != null) {

            new CommunicationRequestFragment.SendResponseTask().execute(
                    " http://test.biyeta.com/api/v1/profile_requests/" +
                            id + "/accept");

        } else if (result && clickableButtonIdentifier == 0 && id != null) {

            new CommunicationRequestFragment.SendResponseTask().execute(
                    " http://test.biyeta.com/api/v1/profile_requests/" +
                            id + "/reject");


        } else if (result && clickableButtonIdentifier == 555 && id != null) {

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
                Utils.ShowAlert(getActivity(), "No more reqeust left");
            }
        } else {
            Utils.ShowAlert(getActivity(), "No more reqeust ");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.accept_imageview:
                processResponse(1);


                break;
            case R.id.cancel_imageview:
                processResponse(0);

                break;
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
