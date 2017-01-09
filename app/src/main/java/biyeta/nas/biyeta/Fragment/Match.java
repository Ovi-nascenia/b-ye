package biyeta.nas.biyeta.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import biyeta.nas.biyeta.Adapter.Match_Adapter;
import biyeta.nas.biyeta.Adapter.Profile_Adapter;
import biyeta.nas.biyeta.Model.*;
import biyeta.nas.biyeta.Model.Profile;
import biyeta.nas.biyeta.R;

/**
 * Created by user on 1/5/2017.
 */

public class Match extends Fragment {


    RecyclerView recyclerView;
    Match_Adapter mMatch_adapter;
    private List<Profile> movieList = new ArrayList<>();
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
        Log.e("come","Match");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.match, null);
        recyclerView=(RecyclerView)v.findViewById(R.id.match_list);
        mMatch_adapter = new Match_Adapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMatch_adapter);

        prepareMovieData();
        return v;

    }
    void prepareMovieData()
    {
        biyeta.nas.biyeta.Model.Profile profile = new biyeta.nas.biyeta.Model.Profile("Mad Max: Fury Road");
        movieList.add(profile);

        profile = new biyeta.nas.biyeta.Model.Profile("Inside Out");
        movieList.add(profile);

        profile = new biyeta.nas.biyeta.Model.Profile("Inside Out");
        movieList.add(profile);

        profile = new biyeta.nas.biyeta.Model.Profile("Inside Out");
        movieList.add(profile);

        profile = new biyeta.nas.biyeta.Model.Profile("Inside Out");
        movieList.add(profile);

        profile = new biyeta.nas.biyeta.Model.Profile("Inside Out");
        movieList.add(profile);

        profile = new biyeta.nas.biyeta.Model.Profile("Inside Out");
        movieList.add(profile);





        mMatch_adapter.notifyDataSetChanged();
    }
}
