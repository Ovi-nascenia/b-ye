package com.nascenia.biyeta.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nascenia.biyeta.activity.UserProfileActivity;
import com.nascenia.biyeta.model.*;
import com.nascenia.biyeta.model.Profile;
import com.nascenia.biyeta.resonse.ProfileResponse;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nascenia.biyeta.appdata.SharePref;

import com.nascenia.biyeta.adapter.Profile_Adapter;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.Search_Filter;

/**
 * Created by user on 1/5/2017.
 */

public class Search extends Fragment {

    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    int flag = 1;
    Snackbar snackbar;
    static int pageNumber = 0;
    Button searchButton;
    Profile_Adapter profileAdapter;
    private List<com.nascenia.biyeta.model.Profile> profile_list = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();


    public Search() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.search, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.profile_list);
        searchButton = (Button) v.findViewById(R.id.searchButton);
        try {
            new Get_Data().execute();
        }catch (Exception e){
            Utils.ShowAlert(getContext(),"Check Internet Connection");

        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(profileAdapter);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.RelativeLayoutLeftButton);


        //onItem click listener in Recycler view




        //  prepareMovieData();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Search_Filter.class));
            }
        });
        return v;

    }


    List<Profile> listProfile;
    //fetch data from
    class Get_Data extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            relativeLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (flag != 1) snackbar.dismiss();
            try {

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    Gson gson=new Gson();
                    ProfileResponse profileResponse=gson.fromJson(res,ProfileResponse.class);
                    listProfile=profileResponse.profiles;
                    pageNumber=profileResponse.totalPage;
                    profileAdapter = new Profile_Adapter(listProfile) {
                        @Override
                        public void load() {
                            Toast.makeText(getContext(),pageNumber+" "+flag,Toast.LENGTH_SHORT).show();

                            flag++;
                            if (flag <= pageNumber) {
                                snackbar = Snackbar
                                        .make(recyclerView, "Loading..", Snackbar.LENGTH_INDEFINITE);
                                Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) snackbar.getView();
                                snack_view.addView(new ProgressBar(getContext()));
                                snackbar.show();
                                try {
                                    new Get_Data().execute();
                                }catch (Exception e){
                                    Utils.ShowAlert(getContext(),"Check Internet Connection");

                                }
                            } else {

                            }

                        }
                    };
                    recyclerView.setAdapter(profileAdapter);



                } catch (JSONException e) {

                    Utils.ShowAlert(getContext(), "Check Internet Connection");
                }
            }
            catch (Exception e) {

                //other Exception Like Internet Exception
                Utils.ShowAlert(getContext(),"Check Internet Connection");
            }
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            // showDialog(getContext(),profile_list.get(position).getDisplay_name(),profile_list.get(position).getLocation());
                            Toast.makeText(getContext(),listProfile.get(position).displayName+" "+flag,Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                            intent.putExtra("id", listProfile.get(position).id);
                            intent.putExtra("user_name", listProfile.get(position).displayName);
                            intent.putExtra("PROFILE_EDIT_OPTION", false);
                            startActivity(intent);
                        }
                    })
            );
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... url) {

            //get data from url
            Response response;
            SharePref sharePref = new SharePref(getContext());
            String token = sharePref.get_data("token");
            Request request = null;
            if (flag != 1) {
                request = new Request.Builder()
                        .url("http://test.biyeta.com/api/v1/search/results?page=" + flag)
                        .addHeader("Authorization", "Token token=" + token)
                        .build();
            } else {

                request = new Request.Builder()
                        .url("http://test.biyeta.com/api/v1/search/results")
                        .addHeader("Authorization", "Token token=" + token)
                        .build();
            }

            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
