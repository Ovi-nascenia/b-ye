package com.nascenia.biyeta.adapter;

/**
 * Created by saiful on 2/23/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.nascenia.biyeta.activity.BirthDatePickerPopUpActivity;
import com.nascenia.biyeta.activity.OwnUserProfileActivity;
import com.nascenia.biyeta.activity.PopUpCastReligion;
import com.nascenia.biyeta.activity.PopUpPersonalInfo;
import com.nascenia.biyeta.activity.RegistrationFamilyInfoFirstPage;
import com.nascenia.biyeta.activity.RegistrationUserAddressInformation;
import com.nascenia.biyeta.activity.UserProfileActivity;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;
import com.nascenia.biyeta.view.ChildItemViewHolder;
import com.nascenia.biyeta.view.ParentItemViewHolder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by saiful on 2/12/17.
 */

public class UserProfileExpenadlbeAdapter extends ExpandableRecyclerAdapter<UserProfileParent, UserProfileChild, ParentItemViewHolder, ChildItemViewHolder> {


    private LayoutInflater mInflater;

    private List<UserProfileParent> userProfilesListParent;
    private Context baseContext;
    private boolean isProfileEditOptionEnable;
    private final int DATE_OF_BIRTH_REQUEST_CODE = 2;
    private final int HEIGHT_REQUEST_CODE = 3;
    private final int RELIGION_REQUEST_CODE = 4;
    private final int SKIN_REQUEST_CODE = 5;
    private final int BODY_REQUEST_CODE = 6;
    private final int MARITAL_STATUS_REQUEST_CODE = 7;
    private final int BLOOD_GROUP_REQUEST_CODE = 8;
    private final int SMOKE_REQUEST_CODE = 9;

    /*public static ArrayList<Boolean> parentPositionList = new ArrayList<>();*/


    public UserProfileExpenadlbeAdapter(Context baseContext, List<UserProfileParent> userProfilesListParent, boolean isProfileEditOptionEnable) {
        super(userProfilesListParent);
        mInflater = LayoutInflater.from(baseContext);
        this.userProfilesListParent = userProfilesListParent;
        this.baseContext = baseContext;
        this.isProfileEditOptionEnable = isProfileEditOptionEnable;
    }


    @NonNull
    @Override
    public ParentItemViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View parentView = mInflater.inflate(R.layout.list_profile_details_parent_item, parentViewGroup, false);
        final ParentItemViewHolder parentItem = new ParentItemViewHolder(parentView, baseContext);
        parentView.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        parentItem.parentItemTitleTextView.getParent().requestChildFocus(parentItem.parentItemTitleTextView,parentItem.parentItemTitleTextView);
                        if (parentItem.isExpanded()) {
                            parentItem.setExpandAction();
                        } else {
                            parentItem.setCollapseAction();
                        }
                    }
                }
        );

        return parentItem;

    }

    @NonNull
    @Override
    public ChildItemViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View childView = mInflater.inflate(R.layout.profile_details_child_item, childViewGroup, false);
        final TextView titleTextView = childView.findViewById(R.id.titleTextView);
        ImageView img_edit = childView.findViewById(R.id.img_edit);
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.age))){
                    Toast.makeText(baseContext, "age", Toast.LENGTH_SHORT).show();
                    ((OwnUserProfileActivity)baseContext).startActivityForResult(
                            new Intent(baseContext, BirthDatePickerPopUpActivity.class),
                            DATE_OF_BIRTH_REQUEST_CODE);

                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.height))){
                    Toast.makeText(baseContext, "height", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(baseContext, PopUpPersonalInfo.class);
                    intent.putExtra("data", "height");
                    ((OwnUserProfileActivity)baseContext).startActivityForResult(intent,
                            HEIGHT_REQUEST_CODE);
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.skin_color))){
                    Toast.makeText(baseContext, "skin", Toast.LENGTH_SHORT).show();
                    new GetPersonalInfoStepFetchConstant("skin", SKIN_REQUEST_CODE).execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.body))){
                    Toast.makeText(baseContext, "body", Toast.LENGTH_SHORT).show();
                    new GetPersonalInfoStepFetchConstant("body", BODY_REQUEST_CODE).execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.marital_status))){
                    Toast.makeText(baseContext, "marital status", Toast.LENGTH_SHORT).show();
                    new GetPersonalInfoStepFetchConstant("marital_status", MARITAL_STATUS_REQUEST_CODE).execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.blood_group_text))){
                    Toast.makeText(baseContext, "blood group", Toast.LENGTH_SHORT).show();
                    new GetPersonalInfoStepFetchConstant("blood_group", BLOOD_GROUP_REQUEST_CODE).execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.smoking_text))){
                    Toast.makeText(baseContext, "smoke", Toast.LENGTH_SHORT).show();
                    new GetPersonalInfoStepFetchConstant("smoke", SMOKE_REQUEST_CODE).execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.religion_text))){
                    Toast.makeText(baseContext, "religion", Toast.LENGTH_SHORT).show();
                    new GetReligionStepFetchConstant().execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.present_loaction_text))){
                    Toast.makeText(baseContext, "present address", Toast.LENGTH_SHORT).show();
                    new GetAddressStepFetchConstant().execute();
                }else if(titleTextView.getText().toString().equalsIgnoreCase(baseContext.getResources().getString(R.string.cast_text))){
                    Toast.makeText(baseContext, "cast", Toast.LENGTH_SHORT).show();
                    new GetReligionStepFetchConstant().execute();
                }
            }
        });
        return new ChildItemViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull final ParentItemViewHolder parentViewHolder,
                                       final int parentPosition,
                                       @NonNull UserProfileParent parent) {

        parentViewHolder.bind(parent);
       /* if (parentPosition == (userProfilesListParent.size() - 1)) {
            parentViewHolder.lnIndicator2.setVisibility(View.GONE);
        }

        parentViewHolder.lnIndicator1.setVisibility(View.INVISIBLE);*/



      /*  if (this.isProfileEditOptionEnable) {
            parentViewHolder.editItemBtn.setVisibility(View.VISIBLE);
        }*/




    }


    @Override
    public void onBindChildViewHolder(@NonNull final ChildItemViewHolder childViewHolder,
                                      int parentPosition,
                                      int childPosition,
                                      @NonNull UserProfileChild child) {

        childViewHolder.bind(child);
/*
        if (parentPositionList.get(parentPosition)) {
            childViewHolder.titleResultTextView.setEnabled(true);
        } else {
            childViewHolder.titleResultTextView.setEnabled(false);
        }*/


       /* if (childPosition == (userProfilesListParent.get(parentPosition).getChildList().size() - 1)) {

//            childViewHolder.itemDividerLayout.setVisibility(View.VISIBLE);
        } else
            childViewHolder.itemDividerLayout.setVisibility(View.GONE);*/


        if (childPosition == (userProfilesListParent.get(parentPosition).getChildList().size() - 1))
            childViewHolder.itemDividerLayout.setVisibility(View.GONE);
        else
        {
            childViewHolder.itemDividerLayout.setVisibility(View.VISIBLE);
        }



    }

    public class GetReligionStepFetchConstant extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(baseContext);
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 2)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 2);
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("religion_step_data", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext, baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Intent intent = new Intent(baseContext, PopUpCastReligion.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", "religion");
                ((OwnUserProfileActivity)baseContext).startActivityForResult(intent,
                        RELIGION_REQUEST_CODE);

            }
        }

    }

    private class GetPersonalInfoStepFetchConstant  extends AsyncTask<String, String, String> {
        String reqType = "";
        int req_code;

        public GetPersonalInfoStepFetchConstant(String strType, int req_code) {
            this.reqType = strType;
            this.req_code = req_code;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(baseContext);
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 4)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 4);
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e("profile_edit", responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext, baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent intent = new Intent(baseContext, PopUpPersonalInfo.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                ((OwnUserProfileActivity)baseContext).startActivityForResult(intent,
                        req_code);
            }
        }
    }

    private class GetAddressStepFetchConstant  extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(baseContext);
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 5)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 5);
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext, baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
                Intent intent = new Intent(baseContext, RegistrationUserAddressInformation.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", "address");
                ((OwnUserProfileActivity)baseContext).startActivityForResult(intent, 1001);
            }
        }

    }
}