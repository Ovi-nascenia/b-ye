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
 */

public class Match extends Fragment {
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
        return v;

    }
}
