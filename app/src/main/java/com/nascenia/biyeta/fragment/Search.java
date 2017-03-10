package com.nascenia.biyeta.fragment;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nascenia.biyeta.activity.UserProfileActivity;
import com.nascenia.biyeta.model.OldProfile;
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
    static int page_number = 0;
    Button search_btn;
    Profile_Adapter mProfile_adapter;
    private List<OldProfile> profile_list = new ArrayList<>();
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
        search_btn = (Button) v.findViewById(R.id.search_btn);
        mProfile_adapter = new Profile_Adapter(profile_list) {
            @Override
            public void load() {

                flag++;
                if (flag <= page_number) {
                    snackbar = Snackbar
                            .make(recyclerView, "Loading..", Snackbar.LENGTH_INDEFINITE);
                    Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) snackbar.getView();
                    snack_view.addView(new ProgressBar(getContext()));
                    snackbar.show();

                    snackbar.show();
                    new Get_Data().execute();
                } else {

                }

            }
        };
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mProfile_adapter);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.RelativeLayoutLeftButton);
        //relativeLayout.setVisibility(View.GONE);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Search_Filter.class));
            }
        });
        new Get_Data().execute();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        // showDialog(getContext(),profile_list.get(position).getDisplay_name(),profile_list.get(position).getLocation());

                        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                        intent.putExtra("id", profile_list.get(position).getId());
                        intent.putExtra("user_name", profile_list.get(position).getDisplay_name());
                        intent.putExtra("PROFILE_EDIT_OPTION", false);
                        startActivity(intent);
                    }
                })
        );

        return v;

    }

    @Override
    public void onPause() {
        super.onPause();
       // Toast.makeText(getContext(),"pause",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
       // Toast.makeText(getContext(),"resume",Toast.LENGTH_SHORT).show();
        if (!Search_Filter.reponse.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(Search_Filter.reponse);
                loadDataFromResponse(jsonObject);
                Search_Filter.reponse="";
            } catch (JSONException e) {
                Utils.ShowAlert(getContext(), "Eoor");
            }
        }

    }

    //fetch data from
    class Get_Data extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            relativeLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            // Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();

            if (flag != 1) snackbar.dismiss();
            //  relativeLayout.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(res);
                page_number = jsonObject.getInt("total_page");
                loadDataFromResponse(jsonObject);


                    //Log.e("fuck",jsonObject.getJSONArray("profiles").getJSONObject(i).getString("display_name"));


            } catch (JSONException e) {
                e.printStackTrace();
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

    void  loadDataFromResponse(JSONObject jsonObject)
    {
        try {
            profile_list.clear();
            mProfile_adapter.notifyDataSetChanged();

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
                // String marital_status=jsonObject.getJSONArray("profiles").getJSONObject(i).getString("marital_status");
                String health = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("health");
                //String religion=jsonObject.getJSONArray("profiles").getJSONObject(i).getString("religion");
                //String cast=jsonObject.getJSONArray("profiles").getJSONObject(i).getString("cast");
                String location = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("location");
                String image = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("image");


                OldProfile profile = new OldProfile(id, age, height_ft, height_inc, display_name, occupation, professional_group, skin_color, location, health, image);

                profile_list.add(profile);
                mProfile_adapter.notifyDataSetChanged();
            }
        }
        catch (JSONException e)
        {
            Utils.ShowAlert(getContext(),"No Result");
        }
    }


}
