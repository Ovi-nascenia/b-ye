package com.nascenia.biyeta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nascenia.biyeta.R;

/**
 * Created by saiful on 3/3/17.
 */

public class ProfileImageFirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile_image, container, false);

        /*TextView tv = (TextView) v.findViewById(R.id.title);
        tv.setText(getArguments().getString("text"));
*/
        ImageView imageView = (ImageView) v.findViewById(R.id.img);

        return v;
    }

    public static ProfileImageFirstFragment newInstance(String text, int image) {

        ProfileImageFirstFragment f = new ProfileImageFirstFragment();
        Bundle b = new Bundle();
        b.putString("text", text);
        b.putInt("img", image);

        f.setArguments(b);

        return f;
    }


}
