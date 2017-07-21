package com.nascenia.biyeta.adapter.faq;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.nascenia.biyeta.R;


public class ChildHolder extends ChildViewHolder {


    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TextView mItemTV;
    public TextView bullet;
    public ChildHolder(@NonNull View itemView) {
        super(itemView);
        mItemTV= (TextView) itemView.findViewById(R.id.expandedListItem);
        bullet = (TextView) itemView.findViewById(R.id.bullet_point);

    }

}
