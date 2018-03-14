package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.favorite.FavoriteAdapter;
import com.nascenia.biyeta.favorite.FavoriteProfile;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by nascenia on 4/20/17.
 */

public class FavoriteActivity extends CustomActionBarActivity {

    private RecyclerView favoriteRecyclerView;
    private FavoriteAdapter favoriteAdapter;
    ProgressBar progressBar;
    public Button biodataRequest;
    public Button call, message;
    public Button accept, cancel;
//    private Button actionButton;

    ArrayList<FavoriteProfile> responseObjectFavorite = new ArrayList();
    //    public List<Profile> mFavoriteProfileList = new ArrayList<>();
    FavoriteProfile favoriteResponse;

    private final OkHttpClient client = new OkHttpClient();

    private static final int REQUEST_CODE = 999;

    private Tracker mTracker;
    private AnalyticsApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);
        setUpToolBar(getString(R.string.favorites), this);

        favoriteRecyclerView = (RecyclerView) findViewById(R.id.recylerview_favorite);
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar1);
        biodataRequest = (Button) findViewById(R.id.request_biodata_fav);
        call = (Button) findViewById(R.id.phone);
        message = (Button) findViewById(R.id.message);
        accept = (Button) findViewById(R.id.acceptBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);

        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());

//        actionButton= (Button) findViewById(R.id.request_biodata_fav);
        new LoadFavoriteDataConnection().execute(Utils.Base_URL+"/api/v1/favorites");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                if (data.hasExtra("returnId")) {
                    int id = data.getIntExtra("returnId", -1);
                    for (int i = 0; i < favoriteResponse.getProfiles().size(); i++) {
                        if (favoriteResponse.getProfiles().get(i).getId() == id) {
                            favoriteResponse.getProfiles().remove(i);
                            break;
                        }
                    }
                }
                favoriteAdapter.notifyDataSetChanged();
            }else if(requestCode == Utils.UPGRADE_REQUEST_CODE){
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FavoriteActivity.this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Upgrade successful");
                alertBuilder.setMessage("You have upgraded your account successfully");
                alertBuilder.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        }
    }


    class LoadFavoriteDataConnection extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = null;
            Response response;
            SharePref sharePref = new SharePref(FavoriteActivity.this);
            String token = sharePref.get_data("token");
            Log.v("JsonUrl", params[0]);

            request = new Request.Builder()
                    .url(params[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
//                201fcfd3eef297b88c25d4402caf7af6
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
//                JSONObject jsonObject = new JSONObject(jsonData);
                Log.d("Response-data", jsonData);
                return jsonData;
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "LoadFavoriteDataConnection/doInBackground", "FavoriteActivity", e.getMessage().toString(), mTracker);
                return null;
            }
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);
            if (s == null) {
                Utils.ShowAlert(FavoriteActivity.this, getString(R.string.no_internet_connection));
            } else {
                JSONObject jsonObject = null;
                try {
                    // jsonObject = new JSONObject(s);
                    Gson gson = new Gson();
                    InputStream is = new ByteArrayInputStream(s.getBytes());
                    InputStreamReader isr = new InputStreamReader(is);
                    favoriteResponse = gson.fromJson(isr, FavoriteProfile.class);

//                    Log.i("totalfavprofile", favoriteResponse.getProfiles().size() + "");

                    favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, favoriteResponse, application, mTracker) {
                        @Override
                        public void onClickProfile(int id, int position) {
                            Intent intent = new Intent(getBaseContext(), NewUserProfileActivity.class);
                            intent.putExtra("id", favoriteResponse.getProfiles().get(position).getId() + "");
                            if(favoriteResponse.getProfiles().get(position).getRealName()=="null")
                                intent.putExtra("user_name", favoriteResponse.getProfiles().get(position).getDisplayName());

                            else if(favoriteResponse.getProfiles().get(position).getRealName()!="null")
                                intent.putExtra("user_name", favoriteResponse.getProfiles().get(position).getRealName());
                            intent.putExtra("PROFILE_EDIT_OPTION", false);
                            startActivityForResult(intent, REQUEST_CODE);
//                            startActivity(intent);
                        }
                    };
                    favoriteRecyclerView.setAdapter(favoriteAdapter);
                    favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
                    favoriteAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
//                    application.trackEception(e, "LoadFavoriteDataConnection/onPostExecute", "FavoriteActivity", e.getMessage().toString(), mTracker);
                }

            }
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        /*Google Analytics*/
        mTracker.setScreenName(getString(R.string.favorites));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }



    @Override
    public void setUpToolBar(String title, Context context){
        super.setUpToolBar(title, context);
    }


    public void callFavoriteAPI(){
        new LoadFavoriteDataConnection().execute(Utils.Base_URL+"/api/v1/favorites");
    }

}
