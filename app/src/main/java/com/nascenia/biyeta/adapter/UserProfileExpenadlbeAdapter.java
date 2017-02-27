package com.nascenia.biyeta.adapter;

/**
 * Created by saiful on 2/23/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import com.nascenia.biyeta.model.UserProfile;
import com.nascenia.biyeta.model.UserProfileChild;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.view.ChildItemViewHolder;
import com.nascenia.biyeta.view.ParentItemViewHolder;

/**
 * Created by saiful on 2/12/17.
 */

public class UserProfileExpenadlbeAdapter extends ExpandableRecyclerAdapter<UserProfile, UserProfileChild, ParentItemViewHolder, ChildItemViewHolder> {


    private LayoutInflater mInflater;

    private List<UserProfile> userProfilesList;
    private Context baseContext;

    public UserProfileExpenadlbeAdapter(Context baseContext, List<UserProfile> userProfilesList) {
        super(userProfilesList);
        mInflater = LayoutInflater.from(baseContext);
        this.userProfilesList = userProfilesList;
        this.baseContext = baseContext;

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
    public void onBindParentViewHolder(@NonNull ParentItemViewHolder parentViewHolder, int parentPosition, @NonNull UserProfile parent) {

        parentViewHolder.bind(parent);
        if (parentPosition == (userProfilesList.size() - 1)) {
            parentViewHolder.lnIndicator2.setVisibility(View.GONE);
        }

   parentViewHolder.lnIndicator1.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onBindChildViewHolder(@NonNull ChildItemViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull UserProfileChild child) {


        childViewHolder.bind(child);
        if (childPosition == (userProfilesList.get(parentPosition).getChildList().size() - 1)) {

            childViewHolder.itemDividerLayout.setVisibility(View.VISIBLE);
        } else
            childViewHolder.itemDividerLayout.setVisibility(View.GONE);

    }
}