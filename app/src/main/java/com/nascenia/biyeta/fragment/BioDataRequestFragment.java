package com.nascenia.biyeta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nascenia.biyeta.R;

/**
 * Created by saiful on 3/10/17.
 */

public class BioDataRequestFragment extends Fragment {


    private View _baseView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_biodata_request, container, false);

        return _baseView;
    }

}
