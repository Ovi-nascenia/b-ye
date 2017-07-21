package com.nascenia.biyeta.adapter.faq;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.model.Parent;


import java.util.ArrayList;
import java.util.List;



public class ParentObj implements Parent<String> {

   private ArrayList<String> mChildItemList;
    private String mTitle;

    public ArrayList<String> getmChildItemList() {
        return mChildItemList;
    }

    public void setmChildItemList(ArrayList<String> mChildItemList) {
        this.mChildItemList = mChildItemList;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public ParentObj(String title, ArrayList<String> childItemList) {
        this.mChildItemList = childItemList;
        mTitle=title;
    }

    @Override
    public List<String> getChildList() {
        return mChildItemList;
    }


    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
