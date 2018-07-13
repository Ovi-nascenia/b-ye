package com.nascenia.biyeta.view;

import android.support.annotation.NonNull;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

    public TextView titleTextView;
    public EditText titleResultEditText;
    public ImageView img_edit;
    public LinearLayout itemDividerLayout;
    public int id;


    public ChildItemViewHolder(@NonNull View itemView, boolean isEditable) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        titleResultEditText = (EditText) itemView.findViewById(R.id.titleResultEditText);
        titleResultEditText.setEnabled(false);
        itemDividerLayout = (LinearLayout) itemView.findViewById(R.id.divider);
        img_edit = itemView.findViewById(R.id.img_edit);
    }


    public void bind(UserProfileChild userProfileChild) {

        titleTextView.setText(userProfileChild.getTitle());
        titleResultEditText.setText(Utils.formatString(userProfileChild.getTitleResult())
                .replace(",", ", ")
        );
        if(titleTextView.getText().toString().equalsIgnoreCase("ভাই")) {
            id = userProfileChild.getId();
        }else if(titleTextView.getText().toString().equalsIgnoreCase("বোন")) {
            id = userProfileChild.getId();
        }else if(titleTextView.getText().toString().equalsIgnoreCase("অন্যান্য")) {
            id = userProfileChild.getId();
        }else if(titleTextView.getText().toString().equalsIgnoreCase("খালু")) {
            id = userProfileChild.getId();
        }else if(titleTextView.getText().toString().equalsIgnoreCase("ফুপা")) {
            id = userProfileChild.getId();
        }else if(titleTextView.getText().toString().equalsIgnoreCase("দাদা")) {
            id = userProfileChild.getId();
        }

    }

    public int getId(){
        return id;
    }
}
