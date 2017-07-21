package com.nascenia.biyeta.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.nascenia.biyeta.activity.OwnUserProfileActivity;
import com.nascenia.biyeta.model.UserProfileParent;


import com.nascenia.biyeta.R;

/**
 * Created by saiful on 2/12/17.
 */

public class ParentItemViewHolder extends ParentViewHolder {


    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */

    public TextView parentItemTitleTextView;
    public ImageView parentListItemExpandImageView;
    public ImageView editItemBtn;
    public LinearLayout lnIndicator1;
    public LinearLayout lnIndicator2;

    public static Context holderContext;

    public ParentItemViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        parentItemTitleTextView = (TextView) itemView.findViewById(R.id.parentListItemTitleTextView);
        parentListItemExpandImageView = (ImageView) itemView.findViewById(R.id.parentListItemExpandImage);
        editItemBtn = (ImageView) itemView.findViewById(R.id.editItemBtn);
        lnIndicator1 = (LinearLayout) itemView.findViewById(R.id.indicator1);
        lnIndicator2 = (LinearLayout) itemView.findViewById(R.id.indicator2);
        holderContext = context;


        parentListItemExpandImageView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                parentItemTitleTextView.getParent().requestChildFocus(parentItemTitleTextView,parentItemTitleTextView);
                if (isExpanded()) {
                    setExpandAction();
                } else {
                    setCollapseAction();
                }
            }
        });


        editItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (isExpanded()) {
                    setExpandAction();
                } else {
                    setCollapseAction();
                }*/


            }
        });

    }

    private void setCollapseAction() {
        expandView();
        lnIndicator1.setVisibility(View.VISIBLE);
        lnIndicator1.setBackgroundColor(ContextCompat.getColor(holderContext, R.color.with_expand_color));
//        lnIndicator2.setVisibility(View.GONE);
        parentListItemExpandImageView.setImageResource(R.drawable.up_arrow);


    }

    private void setExpandAction() {
        collapseView();
        lnIndicator1.setVisibility(View.GONE);
//        lnIndicator2.setVisibility(View.VISIBLE);
//        lnIndicator2.setBackgroundColor(ContextCompat.getColor(holderContext, R.color.without_expand_color));
        parentListItemExpandImageView.setImageResource(R.drawable.down_arrow);


    }

    public void bind(UserProfileParent userProfileParent) {
        parentItemTitleTextView.setText(userProfileParent.getTitleParentname());
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }
}
