package biyeta.nas.biyeta.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import biyeta.nas.biyeta.Model.Profile;
import biyeta.nas.biyeta.Adapter.Profile_Adapter;
import biyeta.nas.biyeta.R;
import biyeta.nas.biyeta.Search_Filter;

/**
 * Created by user on 1/5/2017.
 */

public class Search extends Fragment {

    RecyclerView recyclerView;

    Button search_btn;
    Profile_Adapter mProfile_adapter;
    private List<Profile> movieList = new ArrayList<>();

    public Search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("come","search");

        View v = inflater.inflate(R.layout.search, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.profile_list);
        search_btn=(Button)v.findViewById(R.id.search_btn);
        mProfile_adapter = new Profile_Adapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mProfile_adapter);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Search_Filter.class));
            }
        });

        prepareMovieData();


        return v;

    }

    void prepareMovieData()
    {
        Profile profile = new Profile("Mad Max: Fury Road");
        movieList.add(profile);

        profile = new Profile("Inside Out");
        movieList.add(profile);

        profile = new Profile("Inside Out");
        movieList.add(profile);

        profile = new Profile("Inside Out");
        movieList.add(profile);

        profile = new Profile("Inside Out");
        movieList.add(profile);

        profile = new Profile("Inside Out");
        movieList.add(profile);

        profile = new Profile("Inside Out");
        movieList.add(profile);





        mProfile_adapter.notifyDataSetChanged();
    }
}
