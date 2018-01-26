package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.RemoveBrotherItemCallBack;
import com.nascenia.biyeta.utils.RemoveOtherRelationItemCallBack;
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
        RemoveSisterItemCallBack, RemoveOtherRelationItemCallBack {
    OkHttpClient client;

    LinearLayout brotherCountLayout, sisterCountLayout;

    String responseBrother = "";
    String responseSister = "";
    String responseOther = "";
    int brotherCount = 0, sisterCount = 0, otherCount = 0;
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
    private TextView brotherNubmerTitleTextView, sisterNumberTitleTextView;

    ProgressDialog progress;
    private SharePref sharePref;
    private boolean isSignUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_family_info_second_page);

        sharePref = new SharePref(RegistrationFamilyInfoSecondPage.this);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        isSignUp = getIntent().getBooleanExtra("isSignUp", false);
        // Toast.makeText(RegistrationFamilyInfoSecondPage.this,constant,Toast.LENGTH_LONG).show();
        client = new OkHttpClient();

        progress = new ProgressDialog(RegistrationFamilyInfoSecondPage.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);


        brotherNubmerTitleTextView = (TextView) findViewById(R.id.profession_status_label_brother_count);
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
               /* new Intent(RegistrationFamilyInfoSecondPage.this, Login.class);
                finish();*/

                new GetPreviousStepFetchConstant().execute();
            }
        });

        buttonNext = (Button) findViewById(R.id.next);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                responseBrother = "";
                responseSister = "";
                responseOther = "";

                if (brotherNumber.getText().toString().length() == 0) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.brother_nubmer_choise_message),
                            Toast.LENGTH_LONG).show();
                    brotherNubmerTitleTextView.getParent()
                            .requestChildFocus(brotherNubmerTitleTextView, brotherNubmerTitleTextView);
                    return;
                }

                if (sisterNumber.getText().toString().length() == 0) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.sister_nubmer_choise_message),
                            Toast.LENGTH_LONG).show();
                    sisterNumberTitleTextView.getParent()
                            .requestChildFocus(sisterNumberTitleTextView, sisterNumberTitleTextView);
                    return;
                }

                if (RegistrationFamilyInfoSecondPage.numberOfBrother > 0) {

                    for (int i = 0; i < RegistrationFamilyInfoSecondPage.numberOfBrother; i++) {

                        if (recyclerViewBrother.findViewHolderForLayoutPosition(i) instanceof BrotherViewAdapter.MyViewHolder) {
                            BrotherViewAdapter.MyViewHolder holder = (BrotherViewAdapter.MyViewHolder)
                                    recyclerViewBrother.findViewHolderForLayoutPosition(i);

                            Log.i("brothernumberval", holder.nameBrother.getText().toString() + " " +
                                    PopUpFamilyInfoSecondPage.ageArrayBrother.get(i) + " " +
                                    PopUpFamilyInfoSecondPage.occupationArrayBrother.get(i) + "");

                            if (!isUserFillAllSiblingMandatoryField("ভাইয়ের",
                                    holder.nameBrother.getText().toString(),
                                    PopUpFamilyInfoSecondPage.ageArrayBrother.get(i),
                                    PopUpFamilyInfoSecondPage.occupationArrayBrother.get(i),
                                    i)) {
                                brotherNubmerTitleTextView.getParent()
                                        .requestChildFocus(brotherNubmerTitleTextView, brotherNubmerTitleTextView);
                                return;
                            }

                            String response = new StringBuilder()
                                    .append("{")
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
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArrayBrother.get(i)!=null?PopUpFamilyInfoSecondPage.professionalGroupArrayBrother.get(i):"")
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
                                    .append(PopUpFamilyInfoSecondPage.maritalStatusArrayBrother.get(i)!=null?PopUpFamilyInfoSecondPage.maritalStatusArrayBrother.get(i):"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"spouse\":")
                                    .append("\"")
                                    .append(holder.nameBrotherSpouse.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_occupation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.occupationArrayBrotherSpouse.get(i)!=null?PopUpFamilyInfoSecondPage.occupationArrayBrotherSpouse.get(i):"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArrayBrotherSpouse.get(i)!=null?PopUpFamilyInfoSecondPage.professionalGroupArrayBrotherSpouse.get(i):"")
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

                        }
                    }

                }

                if (RegistrationFamilyInfoSecondPage.numberOfSister > 0) {
                    for (int i = 0; i < RegistrationFamilyInfoSecondPage.numberOfSister; i++) {
                        if (recyclerViewSister.findViewHolderForLayoutPosition(i) instanceof SisterViewAdapter.MyViewHolder) {
                            SisterViewAdapter.MyViewHolder holder = (SisterViewAdapter.MyViewHolder)
                                    recyclerViewSister.findViewHolderForLayoutPosition(i);

                            if (!isUserFillAllSiblingMandatoryField("বোনের",
                                    holder.nameSister.getText().toString(),
                                    PopUpFamilyInfoSecondPage.ageArraySister.get(i),
                                    PopUpFamilyInfoSecondPage.occupationArraySister.get(i),
                                    i)) {
                                sisterNumberTitleTextView.getParent()
                                        .requestChildFocus(sisterNumberTitleTextView, sisterNumberTitleTextView);
                                return;
                            }

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
                                    .append(PopUpFamilyInfoSecondPage.occupationArraySister.get(i)!=null?PopUpFamilyInfoSecondPage.occupationArraySister.get(i):"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArraySister.get(i)!=null?PopUpFamilyInfoSecondPage.professionalGroupArraySister.get(i):"")
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
                                    .append(PopUpFamilyInfoSecondPage.maritalStatusArraySister.get(i)!=null?PopUpFamilyInfoSecondPage.maritalStatusArraySister.get(i):"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"spouse\":")
                                    .append("\"")
                                    .append(holder.spouse.getVisibility()==View.VISIBLE?holder.nameSisterSpouse.getText().toString().trim():"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_occupation\":")
                                    .append("\"")
                                    .append((holder.spouse.getVisibility()==View.VISIBLE && PopUpFamilyInfoSecondPage.occupationArraySisterSpouse.get(i)!=null)?PopUpFamilyInfoSecondPage.occupationArraySisterSpouse.get(i):"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"s_professional_group\":")
                                    .append("\"")
                                    .append((holder.spouse.getVisibility()==View.VISIBLE && PopUpFamilyInfoSecondPage.professionalGroupArraySisterSpouse.get(i)!=null)?PopUpFamilyInfoSecondPage.professionalGroupArraySisterSpouse.get(i):"")
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
                }


                if (recyclerViewOther.getChildCount() > 0) {
                    for (int i = 0; i < recyclerViewOther.getChildCount(); i++) {
                        if (recyclerViewOther.findViewHolderForLayoutPosition(i) instanceof OtherViewAdapter.MyViewHolder) {
                            OtherViewAdapter.MyViewHolder holder = (OtherViewAdapter.MyViewHolder)
                                    recyclerViewOther.findViewHolderForLayoutPosition(i);

                            if(holder.otherRelationalStatus.getText().toString().length() == 0)
                            {
                                Toast.makeText(getBaseContext(),
                                        "R.string.choose_realtion_message",
                                        Toast.LENGTH_LONG).show();
                                holder.otherRelationalStatus.getParent().
                                        requestChildFocus(holder.otherRelationalStatus, holder.otherRelationalStatus);

                                return;
                            }

                            if(holder.nameOther.getText().toString().length() == 0)
                            {
                                Toast.makeText(getBaseContext(),
                                        "আপনার " + holder.otherRelationalStatus.getText().toString() + "র "
                                                + getString(R.string.write_name_message),
                                        Toast.LENGTH_LONG).show();
                                holder.otherRelationalStatus.getParent().
                                        requestChildFocus(holder.otherRelationalStatus, holder.otherRelationalStatus);

                                return;
                            }
                            if(holder.otherOccupation.getText().toString().length() == 0)
                            {
                                Toast.makeText(getBaseContext(),
                                        "আপনার " + holder.otherRelationalStatus.getText().toString() + "র "
                                                + getString(R.string.select_occupation_message),
                                        Toast.LENGTH_LONG).show();
                                holder.otherOccupation.getParent().
                                        requestChildFocus(holder.otherOccupation, holder.otherOccupation);

                                return;
                            }
//                            if (!isUserFillAllSiblingMandatoryField("বোনের",
//                                    holder.nameOther.getText().toString(),
//                                    PopUpFamilyInfoSecondPage.ageArraySister.get(i),
//                                    PopUpFamilyInfoSecondPage.occupationArraySister.get(i),
//                                    i)) {
//                                sisterNumberTitleTextView.getParent()
//                                        .requestChildFocus(sisterNumberTitleTextView, sisterNumberTitleTextView);
//                                return;
//                            }

                            String response = new StringBuilder().append("{")
                                    .append("\"name\":")
                                    .append("\"")
                                    .append(holder.nameOther.getText().toString())
                                    .append("\"")
                                    .append(",")
                                    .append("\"relation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.relationStatusArrayOther.get(i))
                                    .append("\"")
                                    .append(",")
//                                    .append("\"age\":")
//                                    .append("\"")
//                                    .append(PopUpFamilyInfoSecondPage.ageArrayOther.get(i)!=null?PopUpFamilyInfoSecondPage.ageArrayOther.get(i):"")
//                                    .append("\"")
//                                    .append(",")
                                    .append("\"occupation\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.occupationArrayOther.get(i)!=null?PopUpFamilyInfoSecondPage.occupationArrayOther.get(i):"")
                                    .append("\"")
                                    .append(",")
                                    .append("\"professional_group\":")
                                    .append("\"")
                                    .append(PopUpFamilyInfoSecondPage.professionalGroupArrayOther.get(i)!=null?PopUpFamilyInfoSecondPage.professionalGroupArrayOther.get(i):"")
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
//                Log.i("finalvalue: ", "totalJson-> " +   JSONResponse());

               //  JSONResponse();
                new RegistrationFamilyInfoSecondPage.SendFamilyInfo().execute(Utils.SEND_INFO);
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


        otherViewAdapter = new OtherViewAdapter(otherList, RegistrationFamilyInfoSecondPage.this,
                this);

        recyclerViewOther = (RecyclerView) findViewById(R.id.recycler_view_other);

        buttonOther = (LinearLayout) findViewById(R.id.button_other);

        RecyclerView.LayoutManager otherLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOther.setLayoutManager(otherLayoutManager);
        recyclerViewOther.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOther.setAdapter(otherViewAdapter);

        recyclerViewOther.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {

                /*OtherViewAdapter.MyViewHolder myViewHolder = (OtherViewAdapter.MyViewHolder) holder;

                InputMethodManager imm = (InputMethodManager) myViewHolder.nameOther.getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myViewHolder.nameOther.getWindowToken(), 0);*/
                Log.e("decendent", "methodcall");

            }
        });


        buttonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otherCount == 0) {
                    otherCount++;
                    prepareOtherData();
                    recyclerViewOther.setVisibility(View.VISIBLE);
                    Log.i("otherdata", "add: " + otherCount + "");

                } else {

                    if (recyclerViewOther.getChildCount() > 0) {
                        int childPosition = recyclerViewOther.getChildCount() - 1;

                        if (recyclerViewOther.findViewHolderForLayoutPosition(childPosition)
                                instanceof OtherViewAdapter.MyViewHolder) {

                            OtherViewAdapter.MyViewHolder holder = (OtherViewAdapter.MyViewHolder)
                                    recyclerViewOther.findViewHolderForLayoutPosition(childPosition);

                            if (holder.otherRelationalStatus.getText().toString().length() == 0) {
                                Toast.makeText(getBaseContext(), getString(R.string.choose_realtion_message),
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else if (holder.nameOther.getText().toString().isEmpty()) {
                                Toast.makeText(getBaseContext(),
                                        "আপনার " + holder.otherRelationalStatus.getText().toString() + " "
                                                + getString(R.string.write_name_message),
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } /*else if (PopUpFamilyInfoSecondPage.ageArrayOther.get(childPosition) == null) {
                                Toast.makeText(getBaseContext(),
                                        "আপনার " + holder.otherRelationalStatus.getText().toString() + " "
                                                + getString(R.string.select_age_message),
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } */else if (holder.otherOccupation.getText().toString().length() == 0) {
                                Toast.makeText(getBaseContext(),
                                        "আপনার " + holder.otherRelationalStatus.getText().toString() + " "
                                                + getString(R.string.select_occupation_message),
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                otherCount++;
                                otherViewAdapter.add(otherCount, otherViewAdapter.listSize());
                                Log.i("otherdata", "add: " + otherCount + "");
                            }

                           /*holder.nameOther.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                               @Override
                               public void onFocusChange(View view, boolean hasFocus) {

                                   if(hasFocus){
                                       holder.nameOther.setFocusableInTouchMode(true);
                                       holder.nameOther.requestFocus();
                                       InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                       imm.showSoftInput(holder.nameOther, InputMethodManager.SHOW_IMPLICIT);
                                   }else{
                                       holder.nameOther.getParent().clearChildFocus(holder.nameOther);
                                   }
                               }
                           });*/

                        }

                    }

                }

            }
        });

        try {
            JSONObject json = new JSONObject(constant);
            if(json.has("data")) {
                brotherNumber.setText(json.getJSONObject("data").getString("total_brothers"));
                numberOfBrother = json.getJSONObject("data").getInt("total_brothers");
                if(Integer.parseInt(json.getJSONObject("data").getString("total_brothers")) > 0){
                    brotherList.clear();
                    brotherViewAdapter.notifyDataSetChanged();

                    if (numberOfBrother > 0) {
                        brotherViewAdapter.updateWithBrotherData(json.getJSONObject("data").getJSONArray("brother_sister_information"));
                        brotherInfoDetailsLayout.setVisibility(View.VISIBLE);
                        brotherCount = 1;
                        prepareBrotherData();

//                        if (numberOfBrother > 1) {
                            buttonBrother.setVisibility(View.INVISIBLE);
//                        }
                    } else {

                        brotherInfoDetailsLayout.setVisibility(View.GONE);
                    }
                }
                sisterNumber.setText(json.getJSONObject("data").getString("total_sisters"));
                numberOfSister = json.getJSONObject("data").getInt("total_sisters");
                if(Integer.parseInt(json.getJSONObject("data").getString("total_sisters")) > 0){
                    sisterList.clear();
                    sisterViewAdapter.notifyDataSetChanged();

                    if (numberOfSister > 0) {
                        sisterInfoDetailsLayout.setVisibility(View.VISIBLE);
                        sisterCount = 1;
                        prepareSisterData();

//                        if (numberOfSister > 1) {
                            buttonSister.setVisibility(View.INVISIBLE);
//                        }
                    } else {
                        sisterInfoDetailsLayout.setVisibility(View.GONE);
                    }
                }
                for(int i = 0; i < json.getJSONObject("data").getJSONArray("brother_sister_information").length(); i++) {
                    JSONObject jsonObject = json.getJSONObject("data").getJSONArray("brother_sister_information").getJSONObject(i);
                    if(jsonObject.getInt("sibling_type")==1) {
                        buttonBrother.setVisibility(View.VISIBLE);
                        if(brotherCount == 1) {
                            brotherCount++;
                            continue;
                        }else if (brotherCount <= numberOfBrother) {
                            brotherViewAdapter.add(brotherCount -1, brotherCount -1 );
                            if(brotherCount < numberOfBrother)
                                buttonBrother.setVisibility(View.VISIBLE);
                            else
                                buttonBrother.setVisibility(View.INVISIBLE);
                            brotherCount++;
                            continue;
                        }

                    }else {
                        buttonSister.setVisibility(View.VISIBLE);
                        if(sisterCount == 1) {
                            sisterCount++;
                            continue;
                        }else if (sisterCount <= numberOfSister) {
                            sisterViewAdapter.add(sisterCount - 1, sisterCount - 1);
                            if(sisterCount < numberOfBrother)
                                buttonSister.setVisibility(View.VISIBLE);
                            else
                                buttonSister.setVisibility(View.INVISIBLE);
                            brotherCount++;
                            continue;
                        }
                    }
                }
                brotherCount--;
                sisterCount--;

//                int brotherItem = 0, sisterItem = 0;
//                for (int i = 0; i < json.getJSONObject("data").getJSONArray("brother_sister_information").length(); i++) {
//                    JSONObject jsonObject = json.getJSONObject("data").getJSONArray("brother_sister_information").getJSONObject(i);
//                    if(jsonObject.getInt("sibling_type") == 1)
//                    {
//                        if (recyclerViewBrother.findViewHolderForLayoutPosition(brotherItem) instanceof BrotherViewAdapter.MyViewHolder) {
//                            BrotherViewAdapter.MyViewHolder holder =
//                                    (BrotherViewAdapter.MyViewHolder)
//                                            recyclerViewBrother.findViewHolderForLayoutPosition(brotherItem);
//
//                            holder.nameBrother.setText(jsonObject.getString("name"));
//                            brotherItem++;
//                        }
//
//                    }else {
//                        if (recyclerViewSister.findViewHolderForLayoutPosition(
//                                sisterItem) instanceof SisterViewAdapter.MyViewHolder) {
//                            SisterViewAdapter.MyViewHolder holder = (SisterViewAdapter.MyViewHolder)
//                                    recyclerViewSister.findViewHolderForLayoutPosition(sisterItem);
//                            holder.nameSister.setText(jsonObject.getString("name"));
//
//                            sisterItem++;
//                        }
//                    }
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //   prepareOtherData();


    }

    private boolean isUserFillAllSiblingMandatoryField(String siblingType,
                                                       String siblingName,
                                                       String siblingAge,
                                                       String siblingOccupation,
                                                       int siblingNumber) {

        if (siblingName.isEmpty()) {
            Toast.makeText(getBaseContext(),
                    "আপনার " + Utils.englishToBanglaNumberConvertion(siblingNumber + 1)
                            + " " + siblingType +" "+ getString(R.string.write_name_message),
                    Toast.LENGTH_LONG).show();

            return false;
        }


        if (siblingAge == null) {
            Toast.makeText(getBaseContext(),
                    "আপনার " + Utils.englishToBanglaNumberConvertion(siblingNumber + 1)
                            + " " + siblingType + " "+ getString(R.string.select_age_message),
                    Toast.LENGTH_LONG).show();

            return false;
        }


        if (siblingOccupation == null) {
            Toast.makeText(getBaseContext(),
                    "আপনার " + Utils.englishToBanglaNumberConvertion(siblingNumber + 1)
                            + " " + siblingType+ " " + getString(R.string.select_occupation_message),
                    Toast.LENGTH_LONG).show();

            return false;
        }


        return true;

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
        //otherCount++;
        otherViewAdapter.notifyDataSetChanged();
    }


    public String JSONResponse() {
        String brotherSisterResponseInfo = "";

        brotherSisterResponseInfo = responseBrother + responseSister;

        if (!brotherSisterResponseInfo.isEmpty())
            brotherSisterResponseInfo = brotherSisterResponseInfo.
                    substring(0, brotherSisterResponseInfo.length() - 1);

        String otherResponseInfo = responseOther;
        if (!otherResponseInfo.isEmpty())
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
                .append(6)//Login.currentPageRegistration)
                .append("}")
                .toString();
        Log.i("finalvalue: ", "totalJson-> " + response);

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

    @Override
    public void removeOtherRelationItemCallBack(int position) {
        otherCount--;
        Log.i("otherdata", "remove: " + otherCount + "");
        /*OtherViewAdapter.MyViewHolder holder = (OtherViewAdapter.MyViewHolder)
                recyclerViewOther.findViewHolderForLayoutPosition(position);
        holder.nameOther.getParent().clearChildFocus(holder.nameOther);*/


    }

    class SendFamilyInfo extends AsyncTask<String, String, String> {
        // ProgressDialog progress = new ProgressDialog(RegistrationFamilyInfoSecondPage.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("val", s + "");
            if (progress.isShowing())
                progress.dismiss();
            if (s == null) {
                Utils.ShowAlert(RegistrationFamilyInfoSecondPage.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    clearStaticData();

                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    //Toast.makeText(RegistrationFamilyInfoSecondPage.this,"cyclecom "+s, Toast.LENGTH_LONG).show();
                    if (jsonObject.has("errors")) {
                        String error = jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationFamilyInfoSecondPage.this, error, Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(RegistrationFamilyInfoSecondPage.this,"allfine", Toast.LENGTH_LONG).show();
                        /*Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                        Intent intent = new Intent(RegistrationFamilyInfoSecondPage.this, Login.class);
                        startActivity(intent);
                        finish();*/

                        Intent intent = new Intent(RegistrationFamilyInfoSecondPage.this, RegistrationUserAddressInformation.class);
                        intent.putExtra("isSignUp", true);
                        startActivity(intent);
//                        finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new GetPreviousStepFetchConstant().execute();
    }


    public class GetPreviousStepFetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 5)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 5);
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (s == null) {
                Utils.ShowAlert(RegistrationFamilyInfoSecondPage.this, getString(R.string.no_internet_connection));
            } else {
              /*  if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                clearStaticData();
                if(isSignUp){
                    finish();
                }else {
                    Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
                    startActivity(new Intent(RegistrationFamilyInfoSecondPage.this,
                            RegistrationFamilyInfoFirstPage.class).
                            putExtra("constants", s));
                    finish();
                }
            }
        }
    }

    private void clearStaticData() {
        numberOfBrother = 0;
        numberOfSister = 0;
        selectedPopUp = 0;
    }
}
