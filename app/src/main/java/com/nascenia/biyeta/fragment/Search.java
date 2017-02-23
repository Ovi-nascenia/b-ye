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

import com.nascenia.biyeta.activity.UserProfileActivity;
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
import com.nascenia.biyeta.activity.Profile_View;
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
    private List<com.nascenia.biyeta.model.Profile> profile_list = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();

    public Search() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

                        startActivity(new Intent(getActivity(), UserProfileActivity.class));
                    }
                })
        );


        //  prepareMovieData();


        return v;

    }

    public void showDialog(Context activity, String display_name, String location) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.custom_dialog);


        TextView display = (TextView) dialog.findViewById(R.id.display_name);
        TextView loc = (TextView) dialog.findViewById(R.id.details);
        display.setText(display_name);
        loc.setText(location);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Profile_View.class));

            }
        });
        ImageView cross_image = (ImageView) dialog.findViewById(R.id.imgClose);
        cross_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

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
                for (int i = 0; i < jsonObject.getJSONArray("profiles").length(); i++)

                {
                    String id = jsonObject.getJSONArray("profiles").getJSONObject(i).getString("display_name");
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


                    com.nascenia.biyeta.model.Profile profile = new com.nascenia.biyeta.model.Profile(id, age, height_ft, height_inc, display_name, occupation, professional_group, skin_color, location, health, image);

                    profile_list.add(profile);
                    mProfile_adapter.notifyDataSetChanged();


                    //Log.e("fuck",jsonObject.getJSONArray("profiles").getJSONObject(i).getString("display_name"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();


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
