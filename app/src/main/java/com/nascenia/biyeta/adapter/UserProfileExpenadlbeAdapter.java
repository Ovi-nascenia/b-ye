package com.nascenia.biyeta.adapter;

/**
 * Created by saiful on 2/23/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import com.nascenia.biyeta.activity.BrotherEditActivity;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.activity.BirthDatePickerPopUpActivity;
import com.nascenia.biyeta.activity.OthersEditActivity;
import com.nascenia.biyeta.activity.OwnUserProfileActivity;
import com.nascenia.biyeta.activity.PopUpCastReligion;
import com.nascenia.biyeta.activity.PopUpPersonalInfo;
import com.nascenia.biyeta.activity.PopupEducationEdit;
import com.nascenia.biyeta.activity.PopupParentsEdit;
import com.nascenia.biyeta.activity.RegistrationUserAddressInformation;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saiful on 2/12/17.
 */

public class UserProfileExpenadlbeAdapter extends
        ExpandableRecyclerAdapter<UserProfileParent, UserProfileChild, ParentItemViewHolder,
                ChildItemViewHolder> {


    private LayoutInflater mInflater;

    private List<UserProfileParent> userProfilesListParent;
    private static Context baseContext;
    private boolean isProfileEditOptionEnable;
    private final int DATE_OF_BIRTH_REQUEST_CODE = 2;
    private final int HEIGHT_REQUEST_CODE = 3;
    private final int RELIGION_REQUEST_CODE = 4;
    private final int SKIN_REQUEST_CODE = 5;
    private final int BODY_REQUEST_CODE = 6;
    private final int MARITAL_STATUS_REQUEST_CODE = 7;
    private final int BLOOD_GROUP_REQUEST_CODE = 8;
    private final int SMOKE_REQUEST_CODE = 9;
    private final int DISABLE_REQUEST_CODE = 10;
    private final int PROFESSIONAL_GROUP_REQUEST_CODE = 11;
    private final int PROFESSION_REQUEST_CODE = 12;
    private final int FASTING_REQUEST_CODE = 13;
    private final int PRAYER_REQUEST_CODE = 14;
    private final int OWN_HOUSE_REQUEST_CODE = 15;
    private final int EDUCATION_REQUEST_CODE = 16;
    private final int FATHER_REQUEST_CODE = 17;
    private final int MOTHER_REQUEST_CODE = 18;
    private final int BROTHER_REQUEST_CODE = 19;
    private final int SISTER_REQUEST_CODE = 20;
    private final int OTHER_REQUEST_CODE = 21;
    private final int DADA_REQUEST_CODE = 22;
    private final int PRESENT_ADDRESS_REQUEST_CODE = 23;
    private final int PERMANENT_ADDRESS_REQUEST_CODE = 24;
    private final int PRESENT_LOCATION_REQUEST_CODE = 25;
    private final int HOME_TOWN_REQUEST_CODE = 26;

    private int childId;


    /*public static ArrayList<Boolean> parentPositionList = new ArrayList<>();*/


    public UserProfileExpenadlbeAdapter(Context baseContext,
            List<UserProfileParent> userProfilesListParent, boolean isProfileEditOptionEnable) {
        super(userProfilesListParent);
        mInflater = LayoutInflater.from(baseContext);
        this.userProfilesListParent = userProfilesListParent;
        this.baseContext = baseContext;
        this.isProfileEditOptionEnable = isProfileEditOptionEnable;
    }


    @NonNull
    @Override
    public ParentItemViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup,
            int viewType) {
        View parentView = mInflater.inflate(R.layout.list_profile_details_parent_item,
                parentViewGroup, false);
        final ParentItemViewHolder parentItem = new ParentItemViewHolder(parentView, baseContext);
        parentView.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        parentItem.parentItemTitleTextView.getParent().requestChildFocus(
                                parentItem.parentItemTitleTextView,
                                parentItem.parentItemTitleTextView);
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
    public ChildItemViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup,
            int viewType) {
        View childView = mInflater.inflate(R.layout.profile_details_child_item, childViewGroup,
                false);
        final TextView titleTextView = childView.findViewById(R.id.titleTextView);
        final EditText descView = childView.findViewById(R.id.titleResultTextView);
        final ImageView img_edit = childView.findViewById(R.id.img_edit);
        final boolean editEnabled = false;
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.age))) {
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(
                            new Intent(baseContext, BirthDatePickerPopUpActivity.class),
                            DATE_OF_BIRTH_REQUEST_CODE);

                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.height))) {
                    Intent intent = new Intent(baseContext, PopUpPersonalInfo.class);
                    intent.putExtra("data", "height");
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            HEIGHT_REQUEST_CODE);
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.skin_color))) {
                    new GetPersonalInfoStepFetchConstant("skin", SKIN_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.body))) {
                    new GetPersonalInfoStepFetchConstant("body", BODY_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.marital_status))) {
                    new GetPersonalInfoStepFetchConstant("marital_status",
                            MARITAL_STATUS_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.blood_group_text))) {
                    new GetPersonalInfoStepFetchConstant("blood_group",
                            BLOOD_GROUP_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.smoking_text))) {
                    new GetPersonalInfoStepFetchConstant("smoke", SMOKE_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.religion_text))) {
                    new GetReligionStepFetchConstant().execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.present_loaction_text))) {
                    new GetAddressStepFetchConstant(PRESENT_LOCATION_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.home_town))) {
                    new GetAddressStepFetchConstant(HOME_TOWN_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.present_address_text))) {
                    new GetAddressStepFetchConstant(PRESENT_ADDRESS_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.permanent_address_text))) {
                    new GetAddressStepFetchConstant(PERMANENT_ADDRESS_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.disabilities_text))) {
                    new GetPersonalInfoStepFetchConstant("disable", DISABLE_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.cast_text))) {
                    new GetReligionStepFetchConstant().execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        Utils.setBanglaProfileTitle(baseContext.getResources().getString(
                                R.string.professional_group_text)))) {
                    new GetPersonalInfoStepFetchConstant("professional_group",
                            PROFESSIONAL_GROUP_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.profession_text))) {
                    new GetPersonalInfoStepFetchConstant("profession",
                            PROFESSION_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        Utils.setBanglaProfileTitle(
                                baseContext.getResources().getString(R.string.designation_text)))) {
                    ((OwnUserProfileActivity) baseContext).getUpdatedText(
                            Utils.setBanglaProfileTitle(
                                    baseContext.getResources().getString(
                                            R.string.designation_text)),
                            descView.getText().toString(), Utils.setBanglaProfileTitle(
                                    baseContext.getResources().getString(
                                            R.string.designation_text)), descView, Utils.PROFILE,
                            Utils.DESIGNATION);

                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        Utils.setBanglaProfileTitle(
                                baseContext.getResources().getString(R.string.institute_text)))) {
                    enableEditing(descView, baseContext.getString(R.string.institute_text), img_edit,true, Utils.INSTITUTION);
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.fast_text))) {
                    new GetPersonalInfoStepFetchConstant("fasting", FASTING_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.prayet_text))) {
                    new GetPersonalInfoStepFetchConstant("prayer", PRAYER_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.own_house_text))) {
                    new GetPersonalInfoStepFetchConstant("own_house",
                            OWN_HOUSE_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.education))) {
                    new GetPersonalInfoStepFetchConstant("education",
                            EDUCATION_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.father_text))) {
                    new GetFamilyInfoStepFetchConstant("father", FATHER_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.mother_text))) {
                    new GetFamilyInfoStepFetchConstant("mother", MOTHER_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.brother_text))) {
                    new GetPersonalInfoStepFetchConstant("brother", BROTHER_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.sister_text))) {
                    new GetPersonalInfoStepFetchConstant("sister", SISTER_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.other))) {
                    new FetchOthersConstant("other", img_edit.getTag(),
                            OTHER_REQUEST_CODE).execute();
                } else if (titleTextView.getText().toString().equalsIgnoreCase(
                        baseContext.getResources().getString(R.string.dada))) {
                    new FetchOthersConstant("dada", img_edit.getTag(), DADA_REQUEST_CODE).execute();
                }
            }
        });
        return new ChildItemViewHolder(childView, false);
    }

    private void enableEditing(final EditText descView, String hint,
            final ImageView img_edit, boolean isFocused, final String key) {

        if (isFocused) {
            descView.setEnabled(true);
            descView.setSelection(descView.getText().length());
            img_edit.setVisibility(View.VISIBLE);
            img_edit.setTag("");
            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobjPro = new JSONObject();
                    JSONObject jobj = new JSONObject();
                    try {
                        jobjPro.put(key, descView.getText().toString());
                        jobj.put(Utils.PROFILE, jobjPro);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateDetails(jobjPro);
                    img_edit.setTag("");
                    img_edit.setVisibility(View.INVISIBLE);
                    hideSoftKeyboard(descView);
                    descView.clearFocus();
                    descView.setFocusableInTouchMode(false);
                    descView.setFocusable(false);
                }
            });
        } else {
            img_edit.setTag("");
            img_edit.setVisibility(View.INVISIBLE);
        }
        if (img_edit.getTag() == null || img_edit.getTag().equals("")) {
            img_edit.setVisibility(View.VISIBLE);
            img_edit.setTag("enabled");
            img_edit.setImageResource(R.drawable.accept_icon);
            descView.setFocusableInTouchMode(true);
            descView.setFocusable(true);
            descView.requestFocus();
            descView.setSelection(descView.getText().length());
        }
    }

    public static void hideSoftKeyboard(EditText view) {
        InputMethodManager imm = (InputMethodManager) baseContext.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
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

        childViewHolder.img_edit.setTag(child.getId());
        childViewHolder.bind(child);
//        childId = childViewHolder.getId();
        childId = child.getId();
/*
        if (parentPositionList.get(parentPosition)) {
            childViewHolder.titleResultTextView.setEnabled(true);
        } else {
            childViewHolder.titleResultTextView.setEnabled(false);
        }*/


       /* if (childPosition == (userProfilesListParent.get(parentPosition).getChildList().size()
       - 1)) {

//            childViewHolder.itemDividerLayout.setVisibility(View.VISIBLE);
        } else
            childViewHolder.itemDividerLayout.setVisibility(View.GONE);*/


        if (childPosition == (userProfilesListParent.get(parentPosition).getChildList().size()
                - 1)) {
            childViewHolder.itemDividerLayout.setVisibility(View.GONE);
        } else {
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
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("religion_step_data", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext,
                        baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Intent intent = new Intent(baseContext, PopUpCastReligion.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", "religion");
                ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                        RELIGION_REQUEST_CODE);

            }
        }

    }

    private class GetPersonalInfoStepFetchConstant extends AsyncTask<String, String, String> {
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
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext,
                        baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent intent = new Intent(baseContext, PopUpPersonalInfo.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                if (reqType.equalsIgnoreCase("education")) {
                    intent = new Intent(baseContext, PopupEducationEdit.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else if (reqType.equalsIgnoreCase("father") || reqType.equalsIgnoreCase(
                        "mother")) {
                    intent = new Intent(baseContext, PopupParentsEdit.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else if (reqType.equalsIgnoreCase("brother") || reqType.equalsIgnoreCase(
                        "sister")) {
                    intent = new Intent(baseContext, BrotherEditActivity.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    intent.putExtra("id", childId);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else if (reqType.equalsIgnoreCase("other")) {
                    intent = new Intent(baseContext, OthersEditActivity.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    intent.putExtra("id", childId);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else {
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                }
            }
        }
    }

    private class GetFamilyInfoStepFetchConstant extends AsyncTask<String, String, String> {
        String reqType = "";
        int req_code;

        public GetFamilyInfoStepFetchConstant(String strType, int req_code) {
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
                    .url(Utils.STEP_CONSTANT_FETCH + 5)
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
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext,
                        baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent intent = new Intent(baseContext, PopUpPersonalInfo.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                if (reqType.equalsIgnoreCase("father") || reqType.equalsIgnoreCase(
                        "mother")) {
                    intent = new Intent(baseContext, PopupParentsEdit.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else if (reqType.equalsIgnoreCase("brother") || reqType.equalsIgnoreCase(
                        "sister")) {
                    intent = new Intent(baseContext, BrotherEditActivity.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    intent.putExtra("id", childId);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else if (reqType.equalsIgnoreCase("other")) {
                    intent = new Intent(baseContext, OthersEditActivity.class);
                    intent.putExtra("constants", s);
                    intent.putExtra("data", reqType);
                    intent.putExtra("id", childId);
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                } else {
                    ((OwnUserProfileActivity) baseContext).startActivityForResult(intent,
                            req_code);
                }
            }
        }
    }

    private class GetAddressStepFetchConstant extends AsyncTask<String, String, String> {

        int reqCode;

        public GetAddressStepFetchConstant(int request_code) {
            this.reqCode = request_code;
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
                    .url(Utils.STEP_CONSTANT_FETCH + 6)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 6);
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (s == null) {
                Utils.ShowAlert(baseContext,
                        baseContext.getResources().getString(R.string.no_internet_connection));
            } else {
                Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
                Intent intent = new Intent(baseContext, RegistrationUserAddressInformation.class);
                Bundle bundle = new Bundle();
                bundle.putString("constants", s);
                bundle.putString("data", "address");
                bundle.putSerializable("personal_info",
                        ((OwnUserProfileActivity) baseContext).getPersonalInfo());
                bundle.putSerializable("profile_living_in",
                        ((OwnUserProfileActivity) baseContext).getProfileLivingIn());
                bundle.putSerializable("address",
                        ((OwnUserProfileActivity) baseContext).getAddress());
                intent.putExtras(bundle);
                ((OwnUserProfileActivity) baseContext).startActivityForResult(intent, reqCode);
            }
        }
    }

    public class FetchOthersConstant extends AsyncTask<String, String, String> {

        String reqType = "";
        int req_code;
        int id;

        public FetchOthersConstant(String strType, Object id, int req_code) {
            this.reqType = strType;
            this.req_code = req_code;
            this.id = (Integer) id;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            if (progress.isShowing())
//                progress.dismiss();
            if (s == null) {
                Utils.ShowAlert(baseContext,
                        baseContext.getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing())
                    progress.dismiss();*/
//                clearStaticData();
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent intent;
                intent = new Intent(baseContext, OthersEditActivity.class);
                intent.putExtra("constants", s);
                intent.putExtra("data", reqType);
                intent.putExtra("id", id);
                ((OwnUserProfileActivity) baseContext).startActivityForResult(intent, req_code);
//                finish();
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
            //  Login.currentMobileSignupStep+=1;
            SharePref sharePref = new SharePref(baseContext);
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep )
                    .url(Utils.STEP_CONSTANT_FETCH + 6)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e
// .getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void updateDetails(JSONObject jsonObject) {
        new NetWorkOperation.updateProfileData(baseContext).execute(jsonObject.toString());
    }

    public void getUpdatedText(String title, String msg, String hint, final TextView textView,
            String parent, String key) {
        final View view = LayoutInflater.from(baseContext).inflate(R.layout.custom_edit, null);
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(baseContext);
        alertBuilder.setView(view);
        alertBuilder.setCancelable(true);
        final EditText editText = view.findViewById(R.id.etText);
//        final TextInputLayout til = view.findViewById(R.id.til1);
        TextView tv_title = view.findViewById(R.id.title);
        TextView tv_ok = view.findViewById(R.id.okBtn);
        TextView tv_no = view.findViewById(R.id.noBtn);
        tv_title.setText(title);
        final AlertDialog alert = alertBuilder.create();
        try {
            editText.setText(msg);
            editText.setHint(hint);

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobjPro = new JSONObject();
                    JSONObject jobj = new JSONObject();
                    try {
                        jobjPro.put(Utils.DESIGNATION, editText.getText().toString());
                        jobj.put(Utils.PROFILE, jobjPro);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    updateDetails(alert, jobjPro);
                }
            });
            tv_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();
                }
            });
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}