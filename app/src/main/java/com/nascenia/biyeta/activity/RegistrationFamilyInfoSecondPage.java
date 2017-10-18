package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.fragment.Search;
import com.nascenia.biyeta.model.newuserprofile.Brother;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.nascenia.biyeta.adapter.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegistrationFamilyInfoSecondPage extends AppCompatActivity {
    OkHttpClient client;

    LinearLayout brotherCountLayout,sisterCountLayout;

    int brotherCount = 0, sisterCount = 0, otherCount = 0;
    private List<Integer> brotherList = new ArrayList<>();
    private List<Integer> sisterList = new ArrayList<>();
    private List<Integer> otherList = new ArrayList<>();
    private RecyclerView recyclerViewBrother,recyclerViewSister,recyclerViewOther;

    public static String constant;

    public static TextView brotherNumber, sisterNumber;

    public static int numberOfBrother = 0, numberOfSister = 0;

    Button buttonBrother, buttonSister, buttonOther, buttonNext;
    BrotherViewAdapter brotherViewAdapter;
    SisterViewAdapter sisterViewAdapter;
    OtherViewAdapter otherViewAdapter;
    public static int selectedPopUp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        constant = intent.getStringExtra("constant");
       // Toast.makeText(RegistrationFamilyInfoSecondPage.this,constant,Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_registration_family_info_second_page);
        client = new OkHttpClient();

        brotherCountLayout = (LinearLayout) findViewById(R.id.count_brother);
        brotherNumber = (TextView) findViewById(R.id.count_text_view_brother);

        sisterCountLayout = (LinearLayout) findViewById(R.id.count_sister);
        sisterNumber = (TextView) findViewById(R.id.count_text_view_sister);

        buttonNext = (Button) findViewById(R.id.next);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegistrationFamilyInfoSecondPage.SendFamilyInfo().execute(Utils.SEND_INFO);
            }
        });

        brotherCountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 1;
                Intent setIntent = new Intent(RegistrationFamilyInfoSecondPage.this,PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                startActivity(setIntent);
            }
        });

        sisterCountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 2;
                Intent setIntent = new Intent(RegistrationFamilyInfoSecondPage.this,PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant",RegistrationFamilyInfoSecondPage.constant);
                startActivity(setIntent);
            }
        });


        brotherViewAdapter = new BrotherViewAdapter(brotherList,RegistrationFamilyInfoSecondPage.this);

        recyclerViewBrother = (RecyclerView) findViewById(R.id.recycler_view);

        buttonBrother = (Button) findViewById(R.id.button);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBrother.setLayoutManager(mLayoutManager);
        recyclerViewBrother.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBrother.setAdapter(brotherViewAdapter);

        buttonBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brotherViewAdapter.add(brotherCount,brotherViewAdapter.listSize());
                brotherCount++;
            }
        });

        prepareBrotherData();




        sisterViewAdapter = new SisterViewAdapter(sisterList,RegistrationFamilyInfoSecondPage.this);

        recyclerViewSister = (RecyclerView) findViewById(R.id.recycler_view_sister);

        buttonSister = (Button) findViewById(R.id.button_sister);

        RecyclerView.LayoutManager sisterLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSister.setLayoutManager(sisterLayoutManager);
        recyclerViewSister.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSister.setAdapter(sisterViewAdapter);

        buttonSister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sisterViewAdapter.add(sisterCount,sisterViewAdapter.listSize());
                sisterCount++;
            }
        });

        prepareSisterData();



        otherViewAdapter = new OtherViewAdapter(otherList,RegistrationFamilyInfoSecondPage.this);

        recyclerViewOther = (RecyclerView) findViewById(R.id.recycler_view_other);

        buttonOther = (Button) findViewById(R.id.button_other);

        RecyclerView.LayoutManager otherLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOther.setLayoutManager(otherLayoutManager);
        recyclerViewOther.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOther.setAdapter(otherViewAdapter);

        buttonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherViewAdapter.add(otherCount,otherViewAdapter.listSize());
                otherCount++;
            }
        });

        prepareOtherData();




    }


    private void prepareBrotherData() {
        brotherList.add(brotherCount);
        brotherCount++;
        brotherViewAdapter.notifyDataSetChanged();
    }

    private void prepareSisterData() {
        sisterList.add(sisterCount);
        sisterCount++;
        sisterViewAdapter.notifyDataSetChanged();
    }

    private void prepareOtherData() {
        otherList.add(otherCount);
        otherCount++;
        otherViewAdapter.notifyDataSetChanged();
    }


    public String JSONResponse(){
        String brotherSisterResponseInfo;
        brotherSisterResponseInfo = BrotherViewAdapter.responseBrother + SisterViewAdapter.responseSister;
        brotherSisterResponseInfo = brotherSisterResponseInfo.substring(0, brotherSisterResponseInfo.length() - 1);

        String otherResponseInfo = OtherViewAdapter.responseOther;
        otherResponseInfo = otherResponseInfo.substring(0,otherResponseInfo.length() - 1);
        String response = new StringBuilder().append("{")
                .append("\"total_brothers\":")
                .append(numberOfBrother)
                .append(",")
                .append("\"total_sisters\":")
                .append(numberOfSister)
                .append(",")
                .append("\"brother_sister_information\":")
                .append("[")
                .append(brotherSisterResponseInfo)
                .append("]")
                .append(",")
                .append("\"other_family_members\":")
                .append("[")
                .append(otherResponseInfo)
                .append("]")
                .append(",")
                .append("\"current_mobile_sign_up_step\":")
                .append(Login.currentPageRegistration)
                .append("}")
                .toString();
        return response;
    }

    class SendFamilyInfo extends AsyncTask<String, String, String>{
        ProgressDialog progress = new ProgressDialog(RegistrationFamilyInfoSecondPage.this);;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            if ( !progress.isShowing() )
                progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                progress.cancel();
                JSONObject jsonObject=new JSONObject(s);
                Log.e("Response",s);
                if(jsonObject.has("errors"))
                {
                    Toast.makeText(RegistrationFamilyInfoSecondPage.this, "error", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(RegistrationFamilyInfoSecondPage.this,Login.class);
                    startActivity(intent);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings){
            SharePref sharePref = new SharePref(RegistrationFamilyInfoSecondPage.this);
            final String token = sharePref.get_data("registration_token");

            Log.e("Ovi Test", JSONResponse());

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, JSONResponse());
            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(body)
                    .build();
            Response response = null;
            String responseString = null;
            try {
                response = client.newCall(request).execute();
                responseString = response.body().string();

            }catch (IOException e){
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }




}