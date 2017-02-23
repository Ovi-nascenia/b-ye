package com.nascenia.biyeta.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.nascenia.biyeta.model.UserProfile;


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
                if (isExpanded()) {
                    collapseView();
                    lnIndicator1.setVisibility(View.GONE);
                    lnIndicator2.setVisibility(View.VISIBLE);
                    lnIndicator2.setBackgroundColor(ContextCompat.getColor(holderContext, R.color.without_expand_color));
                    parentListItemExpandImageView.setImageResource(R.drawable.down_arrow);
                } else {
                    expandView();
                    lnIndicator1.setVisibility(View.VISIBLE);
                    lnIndicator1.setBackgroundColor(ContextCompat.getColor(holderContext, R.color.with_expand_color));
                    lnIndicator2.setVisibility(View.GONE);
                    parentListItemExpandImageView.setImageResource(R.drawable.up_arrow);
                }
            }
        });


    }

    public void bind(UserProfile userProfile) {
        parentItemTitleTextView.setText(userProfile.getTitleParentname());
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }
}
