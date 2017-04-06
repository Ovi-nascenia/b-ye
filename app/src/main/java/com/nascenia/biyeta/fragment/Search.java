package com.nascenia.biyeta.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.activity.NewUserProfileActivity;
import com.nascenia.biyeta.activity.UserProfileActivity;
import com.nascenia.biyeta.model.SearchProfileModel;
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

    private static int totalPageNumber = 0;


    private final OkHttpClient client = new OkHttpClient();
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    //used for paging track
    private int flag = 1;
    private TextView emptyText;
    private Snackbar snackbar;
    private Button searchButton;
    private Profile_Adapter mProfile_adapter;
    private List<SearchProfileModel> profileList = new ArrayList<>();


    public Search() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //inflate a view
        View v = inflater.inflate(R.layout.search, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.profile_list);
        searchButton = (Button) v.findViewById(R.id.search_btn);
        emptyText = (TextView) v.findViewById(R.id.empty_list);


        mProfile_adapter = new Profile_Adapter(profileList) {
            @Override
            public void load() {


                flag++;
                if (flag <= totalPageNumber && Search_Filter.reponse.equals("")) {
                    snackbar = Snackbar
                            .make(recyclerView, "Loading..", Snackbar.LENGTH_INDEFINITE);
                    Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) snackbar.getView();
                    snack_view.addView(new ProgressBar(getContext()));
                    snackbar.show();


                    if (Utils.isOnline(getContext()))
                        new GetData().execute();
                    else
                        Utils.ShowAlert(getContext(), getString(R.string.no_internet_connection));
                } else {

                }

            }
        };
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mProfile_adapter);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.RelativeLayoutLeftButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonObjects.clear();

                if (Utils.isOnline(getContext()))
                  startActivity(new Intent(getContext(), Search_Filter.class));
                else
                    Utils.ShowInternetConnectionError(getContext());
            }
        });


        if (Utils.isOnline(getContext()))
            new GetData().execute();
        else
            Utils.ShowAlert(getContext(), getString(R.string.no_internet_connection));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("username", profileList.get(position).getDisplay_name() + " " +
                                profileList.get(position).getId());
                        if(Utils.isOnline(getActivity())) {
                            Intent intent = new Intent(getActivity(), NewUserProfileActivity.class);
                            intent.putExtra("id", profileList.get(position).getId());
                            intent.putExtra("user_name",
                                    profileList.get(position).getDisplay_name());
                            intent.putExtra("PROFILE_EDIT_OPTION", false);
                            startActivity(intent);
                        }
                        else
                        {
                            Utils.ShowInternetConnectionError(getActivity());
                        }
                    }
                })
        );

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Search_Filter.reponse = "";
        emptyText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        jsonObjects.clear();
        profileList.clear();
        mProfile_adapter.notifyDataSetChanged();


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public  static ArrayList<JSONObject> jsonObjects=new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        if ( !jsonObjects.isEmpty()) {
         //    try {

                Log.e("fuck","fuck");
                emptyText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mProfile_adapter);
                Log.e("Search_Filter", jsonObjects.size()+"");
                //clear the previous list item
                profileList.clear();
                mProfile_adapter.notifyDataSetChanged();
          //    JSONObject jsonObject = new JSONObject(Search_Filter.reponse);
                for (int i=0;i<jsonObjects.size();i++) {

                    loadDataFromResponse(jsonObjects.get(i));
                }

//            } catch (JSONException e) {
//                Utils.ShowAlert(getContext(), "Error");
//            }
        }


    }

    void loadDataFromResponse(JSONObject jsonObject) {
        try {


            if (jsonObject.has("no_results")) {
                emptyText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                emptyText.setText(jsonObject.getJSONArray("no_results").getJSONObject(0).getString("detail"));
            }
            else {
                emptyText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonObject.getJSONArray("profiles").length(); i++)

                {


                    String id = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("id");
                    String age = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("age");
                    String height_ft = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("height_ft");
                    String height_inc = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("height_inc");
                    String display_name = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("display_name");
                    String occupation = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("occupation");
                    String professional_group = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("professional_group");
                    String skin_color = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("skin_color");
                    String health = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("health");
                    String location = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("location");
                    String image = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("image");
                    SearchProfileModel profile = new SearchProfileModel(id, age, height_ft, height_inc, display_name, occupation, professional_group, skin_color, location, health, image);

                     profileList.add(profile);
                     mProfile_adapter.notifyDataSetChanged();
                     relativeLayout.setVisibility(View.GONE);

                }
            }
        } catch (JSONException e) {
             Utils.ShowInternetConnectionError(getContext());


            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    //fetch data from
    class GetData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);

            if (res == null) Utils.ShowAlert(getContext(), getString(R.string.no_internet_connection));
            else {

                Log.e("SearchResponse", res);
                relativeLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (flag != 1) snackbar.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.has("no_results")) {
                        emptyText.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        emptyText.setText(jsonObject.getJSONArray("no_results").getJSONObject(0).getString("detail"));
                    } else {


                        totalPageNumber = jsonObject.getInt("total_page");
                        loadDataFromResponse(jsonObject);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException ei) {
                    Utils.ShowAlert(getContext(), getString(R.string.no_internet_connection));
                }
            }
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
