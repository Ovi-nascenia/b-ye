package biyeta.nas.biyeta.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import biyeta.nas.biyeta.R;

/**
 * Created by user on 1/5/2017.
 *//////lll

public class Inbox extends Fragment {

    public Inbox() {
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

        Log.e("come","inbox");
        View v = inflater.inflate(R.layout.filter_search, null);

        return v;

    }
}
