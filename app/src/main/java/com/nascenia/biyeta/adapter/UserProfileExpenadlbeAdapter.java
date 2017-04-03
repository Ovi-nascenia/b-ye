package com.nascenia.biyeta.adapter;

/**
 * Created by saiful on 2/23/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.nascenia.biyeta.model.UserProfileParent;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.view.ChildItemViewHolder;
import com.nascenia.biyeta.view.ParentItemViewHolder;

/**
 * Created by saiful on 2/12/17.
 */

public class UserProfileExpenadlbeAdapter extends ExpandableRecyclerAdapter<UserProfileParent, UserProfileChild, ParentItemViewHolder, ChildItemViewHolder> {


    private LayoutInflater mInflater;

    private List<UserProfileParent> userProfilesListParent;
    private Context baseContext;
    private boolean isProfileEditOptionEnable;

    private ChildItemViewHolder childViewHolder;

    public static ArrayList<Boolean> parentPositionList = new ArrayList<>();


    public UserProfileExpenadlbeAdapter(Context baseContext, List<UserProfileParent> userProfilesListParent, boolean isProfileEditOptionEnable) {
        super(userProfilesListParent);
        mInflater = LayoutInflater.from(baseContext);
        this.userProfilesListParent = userProfilesListParent;
        this.baseContext = baseContext;
        this.isProfileEditOptionEnable = isProfileEditOptionEnable;

        for (int i = 0; i < userProfilesListParent.size(); i++) {

            parentPositionList.add(false);

        }

    }


    @NonNull
    @Override
    public ParentItemViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View parentView = mInflater.inflate(R.layout.list_profile_details_parent_item, parentViewGroup, false);
        return new ParentItemViewHolder(parentView, baseContext);
    }

    @NonNull
    @Override
    public ChildItemViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View childView = mInflater.inflate(R.layout.profile_details_child_item, childViewGroup, false);
        return new ChildItemViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull final ParentItemViewHolder parentViewHolder, final int parentPosition, @NonNull UserProfileParent parent) {

        parentViewHolder.bind(parent);
        if (parentPosition == (userProfilesListParent.size() - 1)) {
            parentViewHolder.lnIndicator2.setVisibility(View.GONE);
        }

        parentViewHolder.lnIndicator1.setVisibility(View.INVISIBLE);

        /*if (this.profileEditOption.equals("OWN_PROFILE")) {
            parentViewHolder.editItemBtn.setVisibility(View.VISIBLE);
        }else {
            parentViewHolder.editItemBtn.setVisibility(View.VISIBLE);
        }*/


        if (this.isProfileEditOptionEnable) {
            parentViewHolder.editItemBtn.setVisibility(View.VISIBLE);
        }


        parentViewHolder.editItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(baseContext, "editing is on", Toast.LENGTH_SHORT).show();
                parentPositionList.set(parentPosition, true);
                notifyParentChanged(parentPosition);


            }
        });

    }


    @Override
    public void onBindChildViewHolder(@NonNull final ChildItemViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull UserProfileChild child) {

        childViewHolder.bind(child);
        this.childViewHolder = childViewHolder;

        Log.i("editext", childViewHolder.titleResultTextView.toString());

        if (parentPositionList.get(parentPosition)) {
            childViewHolder.titleResultTextView.setEnabled(true);
        } else {
            childViewHolder.titleResultTextView.setEnabled(false);
        }


        if (childPosition == (userProfilesListParent.get(parentPosition).getChildList().size() - 1)) {

//            childViewHolder.itemDividerLayout.setVisibility(View.VISIBLE);
        } else
            childViewHolder.itemDividerLayout.setVisibility(View.GONE);


    }
}