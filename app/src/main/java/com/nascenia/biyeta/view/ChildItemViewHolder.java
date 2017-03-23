package com.nascenia.biyeta.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.nascenia.biyeta.model.UserProfileChild;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;

/**
 * Created by saiful on 2/12/17.
 */

public class ChildItemViewHolder extends ChildViewHolder {
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */

    TextView titleTextView;
    EditText titleResultTextView;
    public LinearLayout itemDividerLayout;


    public ChildItemViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        titleResultTextView = (EditText) itemView.findViewById(R.id.titleResultTextView);
        titleResultTextView.setKeyListener(null);
        itemDividerLayout = (LinearLayout) itemView.findViewById(R.id.divider);
    }


    public void bind(UserProfileChild userProfileChild) {

        titleTextView.setText(userProfileChild.getTitle());
        titleResultTextView.setText(Utils.formatString(userProfileChild.getTitleResult()));


    }
}
