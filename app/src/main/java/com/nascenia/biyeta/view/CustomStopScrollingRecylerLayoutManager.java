package com.nascenia.biyeta.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by saiful on 5/9/17.
 */

public class CustomStopScrollingRecylerLayoutManager extends LinearLayoutManager {


    private boolean isScrollEnabled = false;

    public CustomStopScrollingRecylerLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }

}
