package com.nascenia.biyeta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nascenia.biyeta.R;

/**
 * Created by god father on 2/27/2017.
 */

public class AboutBiyeta extends Fragment {

    public AboutBiyeta() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.about_biyeta, null);
        return v;

    }
}
