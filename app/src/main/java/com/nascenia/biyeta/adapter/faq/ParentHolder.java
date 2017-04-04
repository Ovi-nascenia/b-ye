package com.nascenia.biyeta.adapter.faq;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.nascenia.biyeta.R;


public class ParentHolder extends ParentViewHolder {

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TextView mTitleTV;
    public ParentHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTV= (TextView) itemView.findViewById(R.id.listTitle);
    }
}
