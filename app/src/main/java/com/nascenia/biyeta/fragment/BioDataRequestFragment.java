package com.nascenia.biyeta.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nascenia.biyeta.R;

/**
 * Created by saiful on 3/10/17.
 */

public class BioDataRequestFragment extends Fragment {


    private View _baseView;

    private ImageView cancelImageView, waitImageView, acceptImageView;
    private TextView cancelTextView, waitTextView, acceptTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        _baseView = inflater.inflate(R.layout.fragment_communication_request, container, false);
        initView();


        return _baseView;
    }

    private void initView() {

        cancelImageView = (ImageView) _baseView.findViewById(R.id.cancel_imageview);
        waitImageView = (ImageView) _baseView.findViewById(R.id.wait_imageview);
        acceptImageView = (ImageView) _baseView.findViewById(R.id.accept_imageview);

        cancelTextView = (TextView) _baseView.findViewById(R.id.cancel_textview);
        waitTextView = (TextView) _baseView.findViewById(R.id.wait_textview);
        acceptTextView = (TextView) _baseView.findViewById(R.id.accept_textview);

        waitImageView.setVisibility(View.GONE);
        waitTextView.setVisibility(View.GONE);

        setViewMargins(getActivity(),
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT),
                45,
                0,
                0,
                0,
                cancelImageView);


        RelativeLayout.LayoutParams cancelTextViewParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        cancelTextViewParam.addRule(RelativeLayout.BELOW, R.id.cancel_imageview);


        setViewMargins(getActivity(), cancelTextViewParam,
                45,
                0,
                0,
                0,
                cancelTextView);


        //.....................................//

        RelativeLayout.LayoutParams acceptImageViewParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        acceptImageViewParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        acceptImageViewParam.addRule(RelativeLayout.CENTER_HORIZONTAL);


        setViewMargins(getActivity(),
                acceptImageViewParam,
                0,
                0,
                65,
                0,
                acceptImageView);


        RelativeLayout.LayoutParams acceptTextViewParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        acceptTextViewParam.addRule(RelativeLayout.BELOW, R.id.accept_imageview);
        acceptTextViewParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


        setViewMargins(getActivity(), acceptTextViewParam,
                0,
                0,
                65,
                0,
                acceptTextView);


    }

    public void setViewMargins(Context con, ViewGroup.LayoutParams params,
                               int left, int top, int right, int bottom, View view) {

        final float scale = con.getResources().getDisplayMetrics().density;
        // convert the DP into pixel
        int pixel_left = (int) (left * scale + 0.5f);
        int pixel_top = (int) (top * scale + 0.5f);
        int pixel_right = (int) (right * scale + 0.5f);
        int pixel_bottom = (int) (bottom * scale + 0.5f);

        ViewGroup.MarginLayoutParams s = (ViewGroup.MarginLayoutParams) params;
        s.setMargins(pixel_left, pixel_top, pixel_right, pixel_bottom);

        view.setLayoutParams(params);
    }

}
