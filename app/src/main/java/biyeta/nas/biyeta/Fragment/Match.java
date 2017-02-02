package biyeta.nas.biyeta.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    RelativeLayout relativeLayout;
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
        relativeLayout=(RelativeLayout)v.findViewById(R.id.RelativeLayoutLeftButton) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMatch_adapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        showDialog(getContext(),movieList.get(position).getDisplay_name(),movieList.get(position).getLocation());

                    }
                })
        );


        new Get_Data().execute();

      //  prepareMovieData();
        return v;

    }

    public void showDialog(Context activity, String display_name,String location){
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.custom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView display=(TextView)dialog.findViewById(R.id.display_name);
        TextView loc=(TextView)dialog.findViewById(R.id.details);
        display.setText(display_name);
        loc.setText(location);
        dialog.setCancelable(false);

    //    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
      //  text.setText(msg);

        //Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();

    }

    class Get_Data extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            relativeLayout.setVisibility(View.GONE);


        }

        @Override
        protected String doInBackground(String... url) {
            return null;

        }
    }
}
