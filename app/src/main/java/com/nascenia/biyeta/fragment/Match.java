package com.nascenia.biyeta.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.nascenia.biyeta.adapter.Match_Adapter;
import com.nascenia.biyeta.adapter.Profile_Adapter;
import com.nascenia.biyeta.R;

/**
 * Created by user on 1/5/2017.
 */

public class Match extends Fragment {


    RecyclerView recyclerView;
    Match_Adapter mMatch_adapter;
    RelativeLayout relativeLayout;
    private List<com.nascenia.biyeta.model.Profile> movieList = new ArrayList<>();

    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("come", "Match");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.match, null);



        //  prepareMovieData();
        return v;

    }



}
