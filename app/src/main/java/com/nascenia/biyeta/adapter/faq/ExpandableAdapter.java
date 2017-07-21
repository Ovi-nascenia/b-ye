package com.nascenia.biyeta.adapter.faq;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.nascenia.biyeta.R;


import java.util.List;


public  class ExpandableAdapter extends ExpandableRecyclerAdapter<ParentObj, String, ParentHolder, ChildHolder> {
    /**
     * Primary constructor. Sets up {@link #mParentList} and {@link #mFlatItemList}.
     * <p>
     * Any changes to {@link #mParentList} should be made on the original instance, and notified via
     * {@link #notifyParentInserted(int)}
     * {@link #notifyParentRemoved(int)}
     * {@link #notifyParentChanged(int)}
     * {@link #notifyParentRangeInserted(int, int)}
     * {@link #notifyChildInserted(int, int)}
     * {@link #notifyChildRemoved(int, int)}
     * {@link #notifyChildChanged(int, int)}
     * methods and not the notify methods of RecyclerView.Adapter.
     *
     * @param parentList List of all parents to be displayed in the RecyclerView that this
     * adapter is linked to
     */


    private List<ParentObj> parentList;

    public ExpandableAdapter(@NonNull List<ParentObj> parentList) {
        super(parentList);

        this.parentList = parentList;
    }

    @NonNull
    @Override
    public ParentHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = LayoutInflater.from(parentViewGroup.getContext()).inflate(R.layout.list_group_faq, parentViewGroup, false);

        return new ParentHolder(view);
    }

    @NonNull
    @Override
    public ChildHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(childViewGroup.getContext()).inflate(R.layout.list_item_faq, childViewGroup, false);

        return new ChildHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentHolder parentViewHolder, int parentPosition, @NonNull ParentObj parent) {
        parentViewHolder.mTitleTV.setText(parent.getmTitle());

    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildHolder childViewHolder, int parentPosition, int childPosition, @NonNull String child) {
//        childViewHolder.mItemTV.setText(child);
//
//        if (childPosition > 0){
//            childViewHolder.bullet.setVisibility(View.VISIBLE);
//            Log.d("bullet", " :"+childPosition);
//        }

        if (this.parentList.get(parentPosition).getmChildItemList().size() > 1) {
            childViewHolder.bullet.setVisibility(View.VISIBLE);


        }

        childViewHolder.mItemTV.setText(child);

    }
}
