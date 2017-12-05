package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.RemoveBrotherItemCallBack;
import com.nascenia.biyeta.utils.RemoveSisterItemCallBack;
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

public class RegistrationFamilyInfoSecondPage extends AppCompatActivity implements RemoveBrotherItemCallBack,
        RemoveSisterItemCallBack {
    OkHttpClient client;

    LinearLayout brotherCountLayout, sisterCountLayout;

    String responseBrother = "";
    String responseSister = "";
    String responseOther = "";
    int brotherCount, sisterCount = 0, otherCount = 0;
    private List<Integer> brotherList = new ArrayList<>();
    private List<Integer> sisterList = new ArrayList<>();
    private List<Integer> otherList = new ArrayList<>();
    public static RecyclerView recyclerViewBrother, recyclerViewSister, recyclerViewOther;

    public static String constant;

    public static TextView brotherNumber, sisterNumber;

    public static int numberOfBrother = 0, numberOfSister = 0;

    LinearLayout buttonBrother, buttonSister, buttonOther;
    Button buttonNext;
    BrotherViewAdapter brotherViewAdapter;
    SisterViewAdapter sisterViewAdapter;
    OtherViewAdapter otherViewAdapter;
    public static int selectedPopUp = 0;
    ImageView back;

    private RelativeLayout brotherInfoDetailsLayout, sisterInfoDetailsLayout;
    private TextView brotherNubmerTitleTextView,sisterNumberTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        // Toast.makeText(RegistrationFamilyInfoSecondPage.this,constant,Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_registration_family_info_second_page);
        client = new OkHttpClient();

        brotherNubmerTitleTextView= (TextView) findViewById(R.id.profession_status_label_brother_count);
        brotherCountLayout = (LinearLayout) findViewById(R.id.count_brother);
        brotherNumber = (TextView) findViewById(R.id.count_text_view_brother);
        brotherInfoDetailsLayout = (RelativeLayout) findViewById(R.id.brother_info_details_layout);


        brotherNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getBaseContext(), "textchange", Toast.LENGTH_LONG).show();

            }

            @Override
            public void afterTextChanged(Editable s) {

                setBrotherRecylerView();
            }
        });

        sisterNumberTitleTextView = (TextView) findViewById(R.id.profession_status_label_sister_count);
        sisterCountLayout = (LinearLayout) findViewById(R.id.count_sister);
        sisterNumber = (TextView) findViewById(R.id.count_text_view_sister);
        sisterInfoDetailsLayout = (RelativeLayout) findViewById(R.id.sister_info_details_layout);

        sisterNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setSisterRecylerView();
            }
        });

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Intent(RegistrationFamilyInfoSecondPage.this, Login.class);
                finish();
            }
        });

        buttonNext = (Button) findViewById(R.id.next);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewBrother.getChildCount() > 0) {
                    for (int i = 0; i < recyclerViewBrother.getChildCount(); i++) {
                        if (recyclerViewBrother.findViewHolderForLayoutPosition(i) instanceof BrotherViewAdapter.MyViewHolder) {
                            BrotherViewAdapter.MyViewHolder holder = (BrotherViewAdapter.MyViewHolder) recyclerViewBrother.findViewHolderForLayoutPosition(i);
                            String response = new StringBuilder().append("{")
                                    .append("\"sibling_type\":")
                                    .append("\"")
                                    .append(1)
                                    .append("\"")
                                    .append(",")
                                    .append("\"name\":")
                                    .append("\"")
                                    .append(holder.nameBrother.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"age\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.ageArrayBrother.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"occupation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.occupationArrayBrother.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArrayBrother.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"designation\":")
                                    .append("\"")
                                    .append(holder.designationBrother.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"institute\":")
                                    .append("\"")
                                    .append(holder.institutionBrother.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"marital_status\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.maritalStatusArrayBrother.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"spouse\":")
                                    .append("\"")
                                    .append(holder.nameBrotherSpouse.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_occupation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.occupationArrayBrotherSpouse.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArrayBrotherSpouse.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_designation\":")
                                    .append("\"")
                                    .append(holder.designationBrotherSpouse.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_institute\":")
                                    .append("\"")
                                    .append(holder.institutionBrotherSpouse.getText().toString())
                                    .append("\"")
                                    .append("}")
                                    .append(",")
                                    .toString();

                            responseBrother = responseBrother + response;
                            if(responseBrother.isEmpty()){
                                Toast.makeText(getBaseContext(),
                                        getString(R.string.one_brother_info_message),
                                        Toast.LENGTH_LONG).show();
                            }else if(responseBrother.isEmpty() && recyclerViewBrother.getChildCount() > 1){
                                Toast.makeText(getBaseContext(),
                                        getString(R.string.atleast_one_brother_info_message),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else if(brotherNumber.getText().toString().
                        equalsIgnoreCase(getString(R.string.brother_sister_number_textview_hint))) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.brother_nubmer_choise_message),
                            Toast.LENGTH_LONG).show();
                    brotherNubmerTitleTextView.getParent()
                            .requestChildFocus(brotherNubmerTitleTextView,brotherNubmerTitleTextView);
                    return;
                }

                if (recyclerViewSister.getChildCount() > 0) {
                    for (int i = 0; i < recyclerViewSister.getChildCount(); i++) {
                        if (recyclerViewSister.findViewHolderForLayoutPosition(i) instanceof SisterViewAdapter.MyViewHolder) {
                            SisterViewAdapter.MyViewHolder holder = (SisterViewAdapter.MyViewHolder) recyclerViewSister.findViewHolderForLayoutPosition(i);
                            String response = new StringBuilder().append("{")
                                    .append("\"sibling_type\":")
                                    .append("\"")
                                    .append(2)
                                    .append("\"")
                                    .append(",")
                                    .append("\"name\":")
                                    .append("\"")
                                    .append(holder.nameSister.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"age\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.ageArraySister.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"occupation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.occupationArraySister.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArraySister.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"designation\":")
                                    .append("\"")
                                    .append(holder.designationSister.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"institute\":")
                                    .append("\"")
                                    .append(holder.institutionSister.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"marital_status\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.maritalStatusArraySister.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"spouse\":")
                                    .append("\"")
                                    .append("")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_occupation\":")
                                    .append("\"")
                                    .append("")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_professional_group\":")
                                    .append("\"")
                                    .append("")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_designation\":")
                                    .append("\"")
                                    .append("")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_institute\":")
                                    .append("\"")
                                    .append("")
                                    .append("\"")
                                    .append("}")
                                    .append(",")
                                    .toString();

                            responseSister = responseSister + response;
                        }
                    }
                } else if(sisterNumber.getText().toString().
                        equalsIgnoreCase(getString(R.string.brother_sister_number_textview_hint))) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.sister_nubmer_choise_message),
                            Toast.LENGTH_LONG).show();
                    sisterNumberTitleTextView.getParent()
                            .requestChildFocus(sisterNumberTitleTextView,sisterNumberTitleTextView);
                    return;
                }


                if (recyclerViewOther.getChildCount() > 0) {
                    for (int i = 0; i < recyclerViewOther.getChildCount(); i++) {
                        if (recyclerViewOther.findViewHolderForLayoutPosition(i) instanceof OtherViewAdapter.MyViewHolder) {
                            OtherViewAdapter.MyViewHolder holder = (OtherViewAdapter.MyViewHolder) recyclerViewOther.findViewHolderForLayoutPosition(i);
                            String response = new StringBuilder().append("{")
                                    .append("\"name\":")
                                    .append("\"")
                                    .append(holder.nameOther.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"age\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.ageArrayOther.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"occupation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.occupationArrayOther.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArrayOther.get(i))
                                    .append("\"")
                                    .append(",")
                                    .append("\"designation\":")
                                    .append("\"")
                                    .append(holder.designationOther.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"institute\":")
                                    .append("\"")
                                    .append(holder.institutionOther.getText().toString())
                                    .append("\"")
                                    .append("}")
                                    .append(",")
                                    .toString();

                            responseOther = responseOther + response;
                        }
                    }
                }

                Log.i("finalvalue: ", "brother-> " + responseBrother);
                Log.i("finalvalue: ", "sister-> " + responseSister);
                Log.i("finalvalue: ", "other-> " + responseOther);

                // new RegistrationFamilyInfoSecondPage.SendFamilyInfo().execute(Utils.SEND_INFO);
            }
        });

        brotherCountLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectedPopUp = 1;
                Intent setIntent = new Intent(RegistrationFamilyInfoSecondPage.this, PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                startActivity(setIntent);
            }
        });

        sisterCountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 2;
                Intent setIntent = new Intent(RegistrationFamilyInfoSecondPage.this, PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant", RegistrationFamilyInfoSecondPage.constant);
                startActivity(setIntent);
            }
        });


        recyclerViewBrother = (RecyclerView) findViewById(R.id.recycler_view);
        brotherViewAdapter = new BrotherViewAdapter(brotherList, RegistrationFamilyInfoSecondPage.this, this);

        buttonBrother = (LinearLayout) findViewById(R.id.button);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBrother.setLayoutManager(mLayoutManager);
        recyclerViewBrother.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBrother.setAdapter(brotherViewAdapter);

        buttonBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                brotherCount++;
                brotherViewAdapter.add(brotherCount, brotherViewAdapter.listSize());

                if (brotherCount == RegistrationFamilyInfoSecondPage.numberOfBrother) {
                    buttonBrother.setVisibility(View.INVISIBLE);
                }
            }
        });

        //prepareBrotherData();


        sisterViewAdapter = new SisterViewAdapter(sisterList, RegistrationFamilyInfoSecondPage.this, this);

        recyclerViewSister = (RecyclerView) findViewById(R.id.recycler_view_sister);

        buttonSister = (LinearLayout) findViewById(R.id.button_sister);


        RecyclerView.LayoutManager sisterLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSister.setLayoutManager(sisterLayoutManager);
        recyclerViewSister.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSister.setAdapter(sisterViewAdapter);

        buttonSister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sisterCount++;
                sisterViewAdapter.add(sisterCount, sisterViewAdapter.listSize());

                if (sisterCount == RegistrationFamilyInfoSecondPage.numberOfSister) {
                    buttonSister.setVisibility(View.INVISIBLE);
                }
            }
        });

        //prepareSisterData();


        otherViewAdapter = new OtherViewAdapter(otherList, RegistrationFamilyInfoSecondPage.this);

        recyclerViewOther = (RecyclerView) findViewById(R.id.recycler_view_other);

        buttonOther = (LinearLayout) findViewById(R.id.button_other);

        RecyclerView.LayoutManager otherLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOther.setLayoutManager(otherLayoutManager);
        recyclerViewOther.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOther.setAdapter(otherViewAdapter);

        buttonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherViewAdapter.add(otherCount, otherViewAdapter.listSize());
                otherCount++;
            }
        });


        prepareOtherData();


    }

    private void setSisterRecylerView() {
        sisterCount = 0;

        sisterList.clear();
        sisterViewAdapter.notifyDataSetChanged();

        if (RegistrationFamilyInfoSecondPage.numberOfSister > 0) {

            sisterInfoDetailsLayout.setVisibility(View.VISIBLE);
            sisterCount = 1;
            prepareSisterData();

            if (RegistrationFamilyInfoSecondPage.numberOfSister > 1) {
                buttonSister.setVisibility(View.VISIBLE);
            }
        } else {

            sisterInfoDetailsLayout.setVisibility(View.GONE);
        }

    }

    private void setBrotherRecylerView() {

        brotherCount = 0;

        brotherList.clear();
        brotherViewAdapter.notifyDataSetChanged();

        if (RegistrationFamilyInfoSecondPage.numberOfBrother > 0) {

            brotherInfoDetailsLayout.setVisibility(View.VISIBLE);
            brotherCount = 1;
            prepareBrotherData();

            if (RegistrationFamilyInfoSecondPage.numberOfBrother > 1) {
                buttonBrother.setVisibility(View.VISIBLE);
            }
        } else {

            brotherInfoDetailsLayout.setVisibility(View.GONE);
        }

    }

    private void prepareBrotherData() {
        brotherList.add(brotherCount);
        // brotherCount++;
        brotherViewAdapter.notifyDataSetChanged();
    }

    private void prepareSisterData() {
        sisterList.add(sisterCount);
        // sisterCount++;
        sisterViewAdapter.notifyDataSetChanged();
    }

    private void prepareOtherData() {
        otherList.add(otherCount);
        otherCount++;
        otherViewAdapter.notifyDataSetChanged();
    }


    public String JSONResponse() {
        String brotherSisterResponseInfo;
        brotherSisterResponseInfo = responseBrother + responseSister;
        brotherSisterResponseInfo = brotherSisterResponseInfo.substring(0, brotherSisterResponseInfo.length() - 1);

        String otherResponseInfo = responseOther;
        otherResponseInfo = otherResponseInfo.substring(0, otherResponseInfo.length() - 1);
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

    @Override
    public void removeBrotherItem() {
        brotherCount--;
        if (buttonBrother.getVisibility() == View.INVISIBLE) {
            buttonBrother.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeSisterItem() {
        sisterCount--;
        if (buttonSister.getVisibility() == View.INVISIBLE) {
            buttonSister.setVisibility(View.VISIBLE);
        }

    }

    class SendFamilyInfo extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(RegistrationFamilyInfoSecondPage.this);
        ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            if (!progress.isShowing())
                progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                progress.cancel();
                Utils.ShowAlert(RegistrationFamilyInfoSecondPage.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
                        jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationFamilyInfoSecondPage.this, "error", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(RegistrationFamilyInfoSecondPage.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(RegistrationFamilyInfoSecondPage.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", JSONResponse());

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

            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }


}
