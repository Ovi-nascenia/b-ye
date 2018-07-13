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
import android.widget.LinearLayout;
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

        return new ChildItemViewHolder(childView, false);
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