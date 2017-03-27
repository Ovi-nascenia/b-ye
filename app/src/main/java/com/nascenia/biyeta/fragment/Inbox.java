package com.nascenia.biyeta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.ExpiredConnection;
import com.nascenia.biyeta.activity.InboxListView;
import com.nascenia.biyeta.activity.RequestSentFromMe;

/**
 * Created by user on 1/5/2017.
 *//////lll

public class Inbox extends Fragment implements View.OnClickListener{

    public Inbox() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("oncreate","InboxOnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.e("come","inbox");
        View v = inflater.inflate(R.layout.inbox, null);
        v.findViewById(R.id.tv_expire).setOnClickListener(this);
        v.findViewById(R.id.tv_inbox).setOnClickListener(this);
        v.findViewById(R.id.tv_sent_request).setOnClickListener(this);
        v.findViewById(R.id.tv_sent_request_from_me).setOnClickListener(this);


        return v;

    }


    @Override
    public void onClick(View view) {

        int id=view.getId();
        switch (id)
        {
            case R.id.tv_sent_request:
                break;

            case R.id.tv_inbox:
                startActivity(new Intent(getContext(), InboxListView.class));
             break;

            case R.id.tv_expire:
                startActivity(new Intent(getContext(), ExpiredConnection.class));
                break;

            case  R.id.tv_sent_request_from_me:
                startActivity(new Intent(getContext(), RequestSentFromMe.class));
                break;


        }
    }
}
