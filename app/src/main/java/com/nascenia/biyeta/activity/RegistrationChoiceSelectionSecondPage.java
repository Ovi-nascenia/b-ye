package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RegistrationChoiceSelectionSecondPage extends AppCompatActivity {
    public String constant;
    public int currentStep;
    LinearLayout l1;
    CheckBox occupationCheckbox, professonalGroupCheckBox;
    CheckBox dhakaDivision, mymensingDivision, chittagongDivision, khulnaDivision, rajshahiDivision, barisalDivision, sylhetDivision, rangpurDivision;
    CheckBox dhakaDivisionDistricts, mymensingDivisionDistricts, chittagongDivisionDistricts, khulnaDivisionDistricts, rajshahiDivisionDistricts, barisalDivisionDistricts, sylhetDivisionDistricts, rangpurDivisionDistricts;
    TextView dhakaLabel, mymensinghLabel, rajshahiLabel, khulnaLabel, barisalLabel, sylhetLabel, chittagongLabel, rangpurLabel;

    ArrayList<String> occupationSelectedArray;
    ArrayList<String> professonalGroupSelectedArray;
    ArrayList<String> districtSelectedArray;

    Button next;
    ImageView back;


    int dhakaDivisionCheckboxColor = 0;

    private TextView occupationTextView, professionalGroupTextView, homeTownTextView;

    final ArrayList<String> dhakaDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> dhakaDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> mymensinghDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> mymensinghDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> chittagongDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> chittagongDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> khulnaDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> khulnaDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> rajshahiDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> rajshahiDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> barisalDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> barisalDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> sylhetDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> sylhetDivisionDistrictConstant = new ArrayList<String>();

    ArrayList<String> rangpurDivisionDistrict = new ArrayList<String>();
    final ArrayList<String> rangpurDivisionDistrictConstant = new ArrayList<String>();

    ProgressDialog progress ;
    OkHttpClient client;

    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice_selection_second_page);

        sharePref = new SharePref(RegistrationChoiceSelectionSecondPage.this);

        progress = new ProgressDialog(RegistrationChoiceSelectionSecondPage.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        client = new OkHttpClient();

        occupationSelectedArray = new ArrayList<String>();
        professonalGroupSelectedArray = new ArrayList<String>();
        districtSelectedArray = new ArrayList<String>();

        final ArrayList<String> occupation = new ArrayList<String>();
        final ArrayList<String> occupationConstant = new ArrayList<String>();

        ArrayList<String> professonalGroup = new ArrayList<String>();
        final ArrayList<String> professonalGroupConstant = new ArrayList<String>();


        try {
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject professonalGroupObject = jsonObject.getJSONObject("professional_group_constant");
            JSONObject occupationObject = jsonObject.getJSONObject("occupation_constant");
            JSONObject dhakaDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("dhaka");
            JSONObject mymensinghDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("mymensingh");
            JSONObject chittagongDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("chittagong");
            JSONObject khulnaDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("khulna");
            JSONObject rajshahiDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("rajshahi");
            JSONObject barisalDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("barisal");
            JSONObject sylhetDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("sylhet");
            JSONObject rangpurDivisionObject = jsonObject.getJSONObject("location_constant").getJSONObject("rangpur");


            for (int i = 0; i < professonalGroupObject.length(); i++) {
                professonalGroupConstant.add(professonalGroupObject.names().getString(i));
                professonalGroup.add((String) professonalGroupObject.get(professonalGroupObject.names().getString(i)));
            }

            for (int i = 0; i < occupationObject.length(); i++) {
                occupationConstant.add(occupationObject.names().getString(i));
                occupation.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            for (int i = 0; i < mymensinghDivisionObject.length(); i++) {
                mymensinghDivisionDistrictConstant.add(mymensinghDivisionObject.names().getString(i));
                mymensinghDivisionDistrict.add((String) mymensinghDivisionObject.get(mymensinghDivisionObject.names().getString(i)));
            }

            for (int i = 0; i < dhakaDivisionObject.length(); i++) {
                dhakaDivisionDistrictConstant.add(dhakaDivisionObject.names().getString(i));
                dhakaDivisionDistrict.add((String) dhakaDivisionObject.get(dhakaDivisionObject.names().getString(i)));
            }

            for (int i = 0; i < chittagongDivisionObject.length(); i++) {
                chittagongDivisionDistrictConstant.add(chittagongDivisionObject.names().getString(i));
                chittagongDivisionDistrict.add((String) chittagongDivisionObject.get(chittagongDivisionObject.names().getString(i)));
            }

            for (int i = 0; i < khulnaDivisionObject.length(); i++) {
                khulnaDivisionDistrictConstant.add(khulnaDivisionObject.names().getString(i));
                khulnaDivisionDistrict.add((String) khulnaDivisionObject.get(khulnaDivisionObject.names().getString(i)));
            }

            for (int i = 0; i < rajshahiDivisionObject.length(); i++) {
                rajshahiDivisionDistrictConstant.add(rajshahiDivisionObject.names().getString(i));
                rajshahiDivisionDistrict.add((String) rajshahiDivisionObject.get(rajshahiDivisionObject.names().getString(i)));
            }

            for (int i = 0; i < barisalDivisionObject.length(); i++) {
                barisalDivisionDistrictConstant.add(barisalDivisionObject.names().getString(i));
                barisalDivisionDistrict.add((String) barisalDivisionObject.get(barisalDivisionObject.names().getString(i)));
            }
            Log.i("chhosedata ", sylhetDivisionObject.toString());
            for (int i = 0; i < sylhetDivisionObject.length(); i++) {
                sylhetDivisionDistrictConstant.add(sylhetDivisionObject.names().getString(i));
                sylhetDivisionDistrict.add((String) sylhetDivisionObject.get(sylhetDivisionObject.names().getString(i)));
            }

            for (int i = 0; i < rangpurDivisionObject.length(); i++) {
                rangpurDivisionDistrictConstant.add(rangpurDivisionObject.names().getString(i));
                rangpurDivisionDistrict.add((String) rangpurDivisionObject.get(rangpurDivisionObject.names().getString(i)));
            }

            currentStep = 5;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        occupationTextView = findViewById(R.id.occupation_Text_view);
        professionalGroupTextView = findViewById(R.id.professional_group_Text_view);
        homeTownTextView = findViewById(R.id.home_town_Text_view);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new Intent(RegistrationChoiceSelectionSecondPage.this, Login.class);
                finish();*/
                new GetPreviousStepFetchConstant().execute();
            }
        });

        LinearLayout linear1 = (LinearLayout) findViewById(R.id.l1);

        for (int i = 0; i < occupationConstant.size(); i++) {
            occupationCheckbox = new CheckBox(this);
            occupationCheckbox.setText(occupation.get(i));
            occupationCheckbox.setId(Integer.parseInt(occupationConstant.get(i)));
            occupationCheckbox.setChecked(true);
            occupationSelectedArray.add(occupationConstant.get(i));
            final int index = i;
            occupationCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(occupationConstant.get(index)));
                    if (checkBox.isChecked()) {
                        occupationSelectedArray.add(occupationConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        occupationSelectedArray.remove(occupationConstant.get(index));
                    }
                    //  Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ occupationConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            linear1.addView(occupationCheckbox);
        }

        LinearLayout linear2 = (LinearLayout) findViewById(R.id.l2);

        for (int i = 0; i < professonalGroupConstant.size(); i++) {
            professonalGroupCheckBox = new CheckBox(this);
            professonalGroupCheckBox.setText(professonalGroup.get(i));
            professonalGroupCheckBox.setId(Integer.parseInt(professonalGroupConstant.get(i)));
            professonalGroupCheckBox.setChecked(true);
            professonalGroupSelectedArray.add(professonalGroupConstant.get(i));
            final int index = i;
            professonalGroupCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(professonalGroupConstant.get(index)));
                    if (checkBox.isChecked()) {
                        professonalGroupSelectedArray.add(professonalGroupConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        professonalGroupSelectedArray.remove(professonalGroupConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ professonalGroupConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            linear2.addView(professonalGroupCheckBox);
        }


        final LinearLayout dhaka = (LinearLayout) findViewById(R.id.dhaka_districts);

        dhakaDivision = (CheckBox) findViewById(R.id.dhaka);
        dhakaLabel = findViewById(R.id.dhaka_label);

//        if (dhaka.getVisibility() == View.GONE) {
//            dhaka.setVisibility(View.VISIBLE);
//            Log.i("arraysize: ", dhakaDivisionDistrictConstant.size() + "");
        prepareDhakaDivisionLoationCheckbox(dhaka);
//        }

        dhakaDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < dhakaDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(dhakaDivisionDistrictConstant.get(i)));
                if(dhakaDivision.isChecked()) {
                    if(dhaka.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(dhakaDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (dhakaDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(dhaka.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(dhakaDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        dhakaDivision.setChecked(true);
        dhakaLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (dhaka.getVisibility() == View.GONE) {
                dhaka.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",dhakaDivisionDistrictConstant.size()+"");
                prepareDhakaDivisionLoationCheckbox(dhaka);
            } else {
                dhaka.setVisibility(View.GONE);
                dhaka.removeAllViews();
            }
            }
        });

        /*dhakaDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < dhakaDivisionDistrictConstant.size(); i++) {
                    CheckBox checkBox = findViewById(Integer.parseInt(dhakaDivisionDistrictConstant.get(i)));
                    if(dhakaDivision.isChecked()) {
                        if(dhaka.getVisibility() == View.VISIBLE)
                            checkBox.setChecked(true);
                        districtSelectedArray.add(dhakaDivisionDistrictConstant.get(i));
                    }
                    else
                    {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (dhakaDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                                if(dhaka.getVisibility() == View.VISIBLE)
                                    checkBox.setChecked(dhakaDivision.isChecked());
                            }
                        }
                    }
                }
            }
        });
        */
//        dhakaDivision.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dhaka.getVisibility() == View.GONE) {
//                    dhaka.setVisibility(View.VISIBLE);
//                    Log.i("arraysize: ",dhakaDivisionDistrictConstant.size()+"");
//                    prepareDhakaDivisionLoationCheckbox(dhaka);
//                } else {
//                    dhaka.setVisibility(View.GONE);
//                    dhaka.removeAllViews();
//
//                    for (int i = 0; i < dhakaDivisionDistrictConstant.size(); i++) {
//                        for (int j = 0; j < districtSelectedArray.size(); j++) {
//                            if (dhakaDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
//                                districtSelectedArray.remove(j);
//                            }
//                        }
//                    }
//                }
//            }
//        });


        final LinearLayout mymensing = (LinearLayout) findViewById(R.id.mymensing_districts);

        mymensingDivision = (CheckBox) findViewById(R.id.mymensing);
        mymensinghLabel = findViewById(R.id.mymensing_label);
//        mymensingDivision.setChecked(true);
//        if (mymensing.getVisibility() == View.GONE) {
//            mymensing.setVisibility(View.VISIBLE);
        prepareMymensingDivisionLocationCheckbox(mymensing);
//        }

        mymensingDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < mymensinghDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(mymensinghDivisionDistrictConstant.get(i)));
                if(mymensingDivision.isChecked()) {
                    if(mymensing.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(mymensinghDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(mymensinghDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (mymensinghDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(mymensing.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(mymensingDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        mymensingDivision.setChecked(true);
        mymensinghLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (mymensing.getVisibility() == View.GONE) {
                mymensing.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",mymensinghDivisionDistrictConstant.size()+"");
                prepareMymensingDivisionLocationCheckbox(mymensing);
            } else {
                mymensing.setVisibility(View.GONE);
                mymensing.removeAllViews();
            }
            }
        });

        /*
        mymensingDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mymensing.getVisibility() == View.GONE) {
                    mymensing.setVisibility(View.VISIBLE);
                    prepareMymensingDivisionLocationCheckbox(mymensing);
                } else {
                    mymensing.setVisibility(View.GONE);
                    mymensing.removeAllViews();

                    for (int i = 0; i < mymensinghDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (mymensinghDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });
*/

        final LinearLayout chittagong = (LinearLayout) findViewById(R.id.chittagong_districts);

        chittagongDivision = (CheckBox) findViewById(R.id.chittagong);
        chittagongLabel = findViewById(R.id.chittagong_label);
//        chittagongDivision.setChecked(true);
//        if (chittagong.getVisibility() == View.GONE) {
//            chittagong.setVisibility(View.VISIBLE);
        prepareChittagongDivisionLocationCheckbox(chittagong);
//        }
        chittagongDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < chittagongDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(chittagongDivisionDistrictConstant.get(i)));
                if(chittagongDivision.isChecked()) {
                    if(chittagong.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(chittagongDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(chittagongDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (chittagongDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(chittagong.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(chittagongDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        chittagongDivision.setChecked(true);
        chittagongLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (chittagong.getVisibility() == View.GONE) {
                chittagong.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",chittagongDivisionDistrictConstant.size()+"");
                prepareChittagongDivisionLocationCheckbox(chittagong);
            } else {
                chittagong.setVisibility(View.GONE);
                chittagong.removeAllViews();
            }
            }
        });
        /*chittagongDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chittagong.getVisibility() == View.GONE) {
                    chittagong.setVisibility(View.VISIBLE);
                    prepareChittagongDivisionLocationCheckbox(chittagong);
                } else {
                    chittagong.setVisibility(View.GONE);
                    chittagong.removeAllViews();

                    for (int i = 0; i < chittagongDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (chittagongDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });*/

        final LinearLayout khulna = (LinearLayout) findViewById(R.id.khulna_districts);

        khulnaDivision = (CheckBox) findViewById(R.id.khulna);
        khulnaLabel = findViewById(R.id.khulna_label);
//        khulnaDivision.setChecked(true);
//        if (khulna.getVisibility() == View.GONE) {
//            khulna.setVisibility(View.VISIBLE);
        prepareKhulnaDivisionLocationCheckbox(khulna);
//        }

        khulnaDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < khulnaDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(khulnaDivisionDistrictConstant.get(i)));
                if(khulnaDivision.isChecked()) {
                    if(khulna.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(khulnaDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(khulnaDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (khulnaDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(khulna.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(khulnaDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        khulnaDivision.setChecked(true);
        khulnaLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (khulna.getVisibility() == View.GONE) {
                khulna.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",khulnaDivisionDistrictConstant.size()+"");
                prepareKhulnaDivisionLocationCheckbox(khulna);
            } else {
                khulna.setVisibility(View.GONE);
                khulna.removeAllViews();
            }
            }
        });
        /*khulnaDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (khulna.getVisibility() == View.GONE) {
                    khulna.setVisibility(View.VISIBLE);
                    prepareKhulnaDivisionLocationCheckbox(khulna);
                } else {
                    khulna.setVisibility(View.GONE);
                    khulna.removeAllViews();

                    for (int i = 0; i < khulnaDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (khulnaDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });*/


        final LinearLayout rajshahi = (LinearLayout) findViewById(R.id.rajshahi_districts);

        rajshahiDivision = (CheckBox) findViewById(R.id.rajshahi);
        rajshahiLabel = findViewById(R.id.rajshahi_label);
//        rajshahiDivision.setChecked(true);
//        if (rajshahi.getVisibility() == View.GONE) {
//            rajshahi.setVisibility(View.VISIBLE);
        prepareRajshahiDivisionLocationCheckbox(rajshahi);
//        }
        rajshahiDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < rajshahiDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(rajshahiDivisionDistrictConstant.get(i)));
                if(rajshahiDivision.isChecked()) {
                    if(rajshahi.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(rajshahiDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(rajshahiDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (rajshahiDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(rajshahi.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(rajshahiDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        rajshahiDivision.setChecked(true);
        rajshahiLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (rajshahi.getVisibility() == View.GONE) {
                rajshahi.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",rajshahiDivisionDistrictConstant.size()+"");
                prepareRajshahiDivisionLocationCheckbox(rajshahi);
            } else {
                rajshahi.setVisibility(View.GONE);
                rajshahi.removeAllViews();
            }
            }
        });
        /*rajshahiDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rajshahi.getVisibility() == View.GONE) {
                    rajshahi.setVisibility(View.VISIBLE);
                    prepareRajshahiDivisionLocationCheckbox(rajshahi);
                } else {
                    rajshahi.setVisibility(View.GONE);
                    rajshahi.removeAllViews();

                    for (int i = 0; i < rajshahiDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (rajshahiDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });*/


        final LinearLayout barisal = (LinearLayout) findViewById(R.id.barisal_districts);

        barisalDivision = (CheckBox) findViewById(R.id.barisal);
        barisalLabel = findViewById(R.id.barisal_label);
//        barisalDivision.setChecked(true);
//        if (barisal.getVisibility() == View.GONE) {
//            barisal.setVisibility(View.VISIBLE);
        prepareBarisalDivisionLocationCheckbox(barisal);
//        }
        barisalDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < barisalDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = barisal.findViewById(Integer.parseInt(barisalDivisionDistrictConstant.get(i)));
                if(barisalDivision.isChecked()) {
                    if(barisal.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(barisalDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(barisalDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (barisalDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(barisal.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(barisalDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        barisalDivision.setChecked(true);
        barisalLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barisal.getVisibility() == View.GONE) {
                    barisal.setVisibility(View.VISIBLE);
                    Log.i("arraysize: ",barisalDivisionDistrictConstant.size()+"");
                    prepareBarisalDivisionLocationCheckbox(barisal);
                } else {
                    barisal.setVisibility(View.GONE);
                    barisal.removeAllViews();
                }
            }
        });
        /*barisalDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barisal.getVisibility() == View.GONE) {
                    barisal.setVisibility(View.VISIBLE);
                    prepareBarisalDivisionLocationCheckbox(barisal);
                } else {
                    barisal.setVisibility(View.GONE);
                    barisal.removeAllViews();

                    for (int i = 0; i < barisalDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (barisalDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });*/


        final LinearLayout sylhet = (LinearLayout) findViewById(R.id.sylhet_districts);

        sylhetDivision = (CheckBox) findViewById(R.id.sylhet);
        sylhetLabel = findViewById(R.id.sylhet_label);
//        sylhetDivision.setChecked(true);
//        if (sylhet.getVisibility() == View.GONE) {
//            sylhet.setVisibility(View.VISIBLE);
        prepareSylhetDivisionLocationCheckbox(sylhet);
//        }
        sylhetDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < sylhetDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(sylhetDivisionDistrictConstant.get(i)));
                if(sylhetDivision.isChecked()) {
                    if(sylhet.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(sylhetDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(sylhetDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (sylhetDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(sylhet.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(sylhetDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        sylhetDivision.setChecked(true);
        sylhetLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (sylhet.getVisibility() == View.GONE) {
                sylhet.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",sylhetDivisionDistrictConstant.size()+"");
                prepareSylhetDivisionLocationCheckbox(sylhet);
            } else {
                sylhet.setVisibility(View.GONE);
                sylhet.removeAllViews();
            }
            }
        });
        /*sylhetDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sylhet.getVisibility() == View.GONE) {
                    sylhet.setVisibility(View.VISIBLE);
                    prepareSylhetDivisionLocationCheckbox(sylhet);
                } else {
                    sylhet.setVisibility(View.GONE);
                    sylhet.removeAllViews();

                    for (int i = 0; i < sylhetDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (sylhetDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });*/


        final LinearLayout rangpur = (LinearLayout) findViewById(R.id.rangpur_districts);

        rangpurDivision = (CheckBox) findViewById(R.id.rangpur);
        rangpurLabel = findViewById(R.id.rangpur_label);
//        rangpurDivision.setChecked(true);
//        if (rangpur.getVisibility() == View.GONE) {
//            rangpur.setVisibility(View.VISIBLE);
        prepareRangpurDivisionLocationCheckbox(rangpur);
//        }

        rangpurDivision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < rangpurDivisionDistrictConstant.size(); i++) {
                CheckBox checkBox = findViewById(Integer.parseInt(rangpurDivisionDistrictConstant.get(i)));
                if(rangpurDivision.isChecked()) {
                    if(rangpur.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                        checkBox.setChecked(true);
                    if(districtSelectedArray.indexOf(rangpurDivisionDistrictConstant.get(i)) <0)
                        districtSelectedArray.add(rangpurDivisionDistrictConstant.get(i));
                }
                else
                {
                    for (int j = 0; j < districtSelectedArray.size(); j++) {
                        if (rangpurDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                            districtSelectedArray.remove(j);
                            if(rangpur.getVisibility() == View.VISIBLE)// && districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) < 0)
                                checkBox.setChecked(rangpurDivision.isChecked());
                        }
                    }
                }
            }
            }
        });
        rangpurDivision.setChecked(true);
        rangpurLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (rangpur.getVisibility() == View.GONE) {
                rangpur.setVisibility(View.VISIBLE);
                Log.i("arraysize: ",rangpurDivisionDistrictConstant.size()+"");
                prepareRangpurDivisionLocationCheckbox(rangpur);
            } else {
                rangpur.setVisibility(View.GONE);
                rangpur.removeAllViews();
            }
            }
        });
        /*rangpurDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rangpur.getVisibility() == View.GONE) {
                    rangpur.setVisibility(View.VISIBLE);
                    prepareRangpurDivisionLocationCheckbox(rangpur);
                } else {
                    rangpur.setVisibility(View.GONE);
                    rangpur.removeAllViews();

                    for (int i = 0; i < rangpurDivisionDistrictConstant.size(); i++) {
                        for (int j = 0; j < districtSelectedArray.size(); j++) {
                            if (rangpurDivisionDistrictConstant.get(i).equalsIgnoreCase(districtSelectedArray.get(j))) {
                                districtSelectedArray.remove(j);
                            }
                        }
                    }
                }
            }
        });*/


        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("chhosedata: ", occupationListString());
                Log.i("chhosedata: ", professonalGroupListString());
                Log.i("chhosedata: ", locationString());

                if (occupationListString().equals("[]") || occupationListString().isEmpty()) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.select_your_choice_occupation_message),
                            Toast.LENGTH_LONG).show();
                    occupationTextView.getParent().
                            requestChildFocus(occupationTextView, occupationTextView);
                    return;
                }

                if (professonalGroupListString().equals("[]") || professonalGroupListString().isEmpty()) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.select_your_choice_professional_group_message),
                            Toast.LENGTH_LONG).show();
                    professionalGroupTextView.getParent().
                            requestChildFocus(professionalGroupTextView, professionalGroupTextView);
                    return;
                }

                if (locationString().equals("[]") || locationString().isEmpty()) {
                    Toast.makeText(getBaseContext(),
                            getString(R.string.select_your_choice_home_location_message),
                            Toast.LENGTH_LONG).show();
                    homeTownTextView.getParent().
                            requestChildFocus(homeTownTextView, homeTownTextView);
                    return;
                }

                String total = new StringBuilder().append("{")
                        .append("\"occupation\":")
                        .append("")
                        .append(occupationListString())
                        .append("")
                        .append(",")
                        .append("\"professional_group\":")
                        .append("")
                        .append(professonalGroupListString())
                        .append("")
                        .append(",")
                        .append("\"location\":")
                        .append(locationString())
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(9)
                        .append("}")
                        .toString();
                Log.i("result", total);
              new RegistrationChoiceSelectionSecondPage.SendChoiceInfo().execute(total);

            }
        });
    }

    private void prepareRangpurDivisionLocationCheckbox(LinearLayout rangpur) {

        for (int i = 0; i < rangpurDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(rangpurDivisionDistrictConstant.get(i))) == null)
                rangpurDivisionDistricts = new CheckBox(this);
            else
                rangpurDivisionDistricts = findViewById(Integer.parseInt(rangpurDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(rangpurDivisionDistrictConstant.get(i)) >= 0)
                rangpurDivisionDistricts.setChecked(true);
            rangpurDivisionDistricts.setText(rangpurDivisionDistrict.get(i));
            rangpurDivisionDistricts.setId(Integer.parseInt(rangpurDivisionDistrictConstant.get(i)));
            final int index = i;
            rangpurDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(rangpurDivisionDistrictConstant.get(index)));

                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(rajshahiDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(rajshahiDivisionDistrictConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ rangpurDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                rangpur.addView(rangpurDivisionDistricts);
            }
            catch (Exception ex)
            {

            }

        }
    }

    private void prepareSylhetDivisionLocationCheckbox(LinearLayout sylhet) {

        for (int i = 0; i < sylhetDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(sylhetDivisionDistrictConstant.get(i))) == null)
                sylhetDivisionDistricts = new CheckBox(this);
            else
                sylhetDivisionDistricts = findViewById(Integer.parseInt(sylhetDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(sylhetDivisionDistrictConstant.get(i)) >= 0)
                sylhetDivisionDistricts.setChecked(true);
            sylhetDivisionDistricts.setText(sylhetDivisionDistrict.get(i));
            sylhetDivisionDistricts.setId(Integer.parseInt(sylhetDivisionDistrictConstant.get(i)));
            final int index = i;
            sylhetDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(sylhetDivisionDistrictConstant.get(index)));

                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(sylhetDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(sylhetDivisionDistrictConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ sylhetDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                sylhet.addView(sylhetDivisionDistricts);
            }
            catch (Exception ex)
            {

            }

        }

    }

    private void prepareBarisalDivisionLocationCheckbox(LinearLayout barisal) {

        for (int i = 0; i < barisalDivisionDistrictConstant.size(); i++) {
            if(barisal.findViewById(Integer.parseInt(barisalDivisionDistrictConstant.get(i))) == null)
                barisalDivisionDistricts = new CheckBox(this);
            else
                barisalDivisionDistricts = barisal.findViewById(Integer.parseInt(barisalDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(barisalDivisionDistrictConstant.get(i)) >= 0)
                barisalDivisionDistricts.setChecked(true);
            barisalDivisionDistricts.setText(barisalDivisionDistrict.get(i));
            barisalDivisionDistricts.setId(Integer.parseInt(barisalDivisionDistrictConstant.get(i)));
            final int index = i;
            barisalDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(barisalDivisionDistrictConstant.get(index)));

                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(barisalDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(barisalDivisionDistrictConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ barisalDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                barisal.addView(barisalDivisionDistricts);
            }
            catch (Exception ex)
            {

            }

        }
    }

    private void prepareRajshahiDivisionLocationCheckbox(LinearLayout rajshahi) {

        for (int i = 0; i < rajshahiDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(rajshahiDivisionDistrictConstant.get(i))) == null)
                rajshahiDivisionDistricts = new CheckBox(this);
            else
                rajshahiDivisionDistricts = findViewById(Integer.parseInt(rajshahiDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(rajshahiDivisionDistrictConstant.get(i)) >= 0)
                rajshahiDivisionDistricts.setChecked(true);
//            districtSelectedArray.add(rajshahiDivisionDistrictConstant.get(i));
            rajshahiDivisionDistricts.setText(rajshahiDivisionDistrict.get(i));
            rajshahiDivisionDistricts.setId(Integer.parseInt(rajshahiDivisionDistrictConstant.get(i)));
            final int index = i;
            rajshahiDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(rajshahiDivisionDistrictConstant.get(index)));

                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(rajshahiDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(rajshahiDivisionDistrictConstant.get(index));
                    }
                    // Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ rajshahiDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                rajshahi.addView(rajshahiDivisionDistricts);
            }
            catch (Exception ex)
            {

            }

        }

    }

    private void prepareKhulnaDivisionLocationCheckbox(LinearLayout khulna) {

        for (int i = 0; i < khulnaDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(khulnaDivisionDistrictConstant.get(i))) == null)
                khulnaDivisionDistricts = new CheckBox(this);
            else
                khulnaDivisionDistricts = findViewById(Integer.parseInt(khulnaDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(khulnaDivisionDistrictConstant.get(i)) >= 0)
                khulnaDivisionDistricts.setChecked(true);
            khulnaDivisionDistricts.setText(khulnaDivisionDistrict.get(i));
            khulnaDivisionDistricts.setId(Integer.parseInt(khulnaDivisionDistrictConstant.get(i)));
            final int index = i;
            khulnaDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(khulnaDivisionDistrictConstant.get(index)));

                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(khulnaDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(khulnaDivisionDistrictConstant.get(index));
                    }
                    // Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ khulnaDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                khulna.addView(khulnaDivisionDistricts);
            }
            catch (Exception ex)
            {

            }
        }
    }

    private void prepareChittagongDivisionLocationCheckbox(LinearLayout chittagong) {

        for (int i = 0; i < chittagongDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(chittagongDivisionDistrictConstant.get(i))) == null)
                chittagongDivisionDistricts = new CheckBox(this);
            else
                chittagongDivisionDistricts = findViewById(Integer.parseInt(chittagongDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(chittagongDivisionDistrictConstant.get(i)) >= 0)
                chittagongDivisionDistricts.setChecked(true);
            chittagongDivisionDistricts.setText(chittagongDivisionDistrict.get(i));
            chittagongDivisionDistricts.setId(Integer.parseInt(chittagongDivisionDistrictConstant.get(i)));
            final int index = i;
            chittagongDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(chittagongDivisionDistrictConstant.get(index)));

                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(chittagongDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(chittagongDivisionDistrictConstant.get(index));
                    }
                    //  Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ chittagongDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                chittagong.addView(chittagongDivisionDistricts);
            }
            catch (Exception ex)
            {

            }

        }
    }

    private void prepareMymensingDivisionLocationCheckbox(LinearLayout mymensing) {

        for (int i = 0; i < mymensinghDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(mymensinghDivisionDistrictConstant.get(i))) == null)
                mymensingDivisionDistricts = new CheckBox(this);
            else
                mymensingDivisionDistricts = findViewById(Integer.parseInt(mymensinghDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(mymensinghDivisionDistrictConstant.get(i)) >= 0)
                mymensingDivisionDistricts.setChecked(true);
//            districtSelectedArray.add(mymensinghDivisionDistrictConstant.get(i));
            mymensingDivisionDistricts.setText(mymensinghDivisionDistrict.get(i));
            mymensingDivisionDistricts.setId(Integer.parseInt(mymensinghDivisionDistrictConstant.get(i)));
            final int index = i;
            mymensingDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(mymensinghDivisionDistrictConstant.get(index)));
                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(mymensinghDivisionDistrictConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(mymensinghDivisionDistrictConstant.get(index));
                    }
                    // Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ mymensinghDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }
            });
            try {
                mymensing.addView(mymensingDivisionDistricts);
            }
            catch (Exception ex)
            {

            }

        }

    }

    private void prepareDhakaDivisionLoationCheckbox(LinearLayout dhaka) {

        for (int i = 0; i < dhakaDivisionDistrictConstant.size(); i++) {
            if(findViewById(Integer.parseInt(dhakaDivisionDistrictConstant.get(i))) == null)
                dhakaDivisionDistricts = new CheckBox(this);
            else
                dhakaDivisionDistricts = findViewById(Integer.parseInt(dhakaDivisionDistrictConstant.get(i)));
            if(districtSelectedArray.indexOf(dhakaDivisionDistrictConstant.get(i)) >= 0)
                dhakaDivisionDistricts.setChecked(true);
//            districtSelectedArray.add(dhakaDivisionDistrictConstant.get(i));
            dhakaDivisionDistricts.setText(dhakaDivisionDistrict.get(i));
            dhakaDivisionDistricts.setId(Integer.parseInt(dhakaDivisionDistrictConstant.get(i)));
            final int index = i;

            dhakaDivisionDistricts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(dhakaDivisionDistrictConstant.get(index)));
                    Log.i("Checkboxdata: ", " " + dhakaDivisionDistrictConstant.get(index));
                    if (checkBox.isChecked()) {
                        districtSelectedArray.add(dhakaDivisionDistrictConstant.get(index));
                        dhakaDivisionCheckboxColor++;
                    } else if (!checkBox.isChecked()) {
                        districtSelectedArray.remove(dhakaDivisionDistrictConstant.get(index));
                        dhakaDivisionCheckboxColor--;
                    }


                    if (dhakaDivisionCheckboxColor != dhakaDivisionDistrict.size()) {
//                        dhakaDivision.setBackgroundColor(Color.rgb(64, 131, 207));
                    } else if (dhakaDivisionCheckboxColor == dhakaDivisionDistrict.size()) {
                        dhakaDivision.setBackgroundColor(Color.WHITE);
                    }
                    // Toast.makeText(RegistrationChoiceSelectionSecondPage.this,"id  : "+ dhakaDivisionDistrictConstant.get(index) ,Toast.LENGTH_LONG ).show();
                }


            });
            try {
                dhaka.addView(dhakaDivisionDistricts);
            }
            catch (Exception ex)
            {

            }
        }
    }

    String occupationListString() {
        StringBuilder occupationdata = new StringBuilder();
        for (int i = 0; i < occupationSelectedArray.size(); i++) {
            if (i != occupationSelectedArray.size() - 1) {
                occupationdata.append("\"")
                        .append(occupationSelectedArray.get(i))
                        .append("\"")
                        .append(",");
            } else {
                occupationdata.append("\"")
                        .append(occupationSelectedArray.get(i))
                        .append("\"");
            }

        }

        String occupation = new StringBuilder().append("[")
                .append(occupationdata.toString())
                .append("]")
                .toString();
        return occupation;
    }


    String professonalGroupListString() {
        StringBuilder professonalGroupData = new StringBuilder();
        for (int i = 0; i < professonalGroupSelectedArray.size(); i++) {
            if (i != professonalGroupSelectedArray.size() - 1) {
                professonalGroupData.append("\"")
                        .append(professonalGroupSelectedArray.get(i))
                        .append("\"")
                        .append(",");
            } else {
                professonalGroupData.append("\"")
                        .append(professonalGroupSelectedArray.get(i))
                        .append("\"");
            }

        }

        String professonalGroup = new StringBuilder().append("[")
                .append(professonalGroupData.toString())
                .append("]")
                .toString();
        return professonalGroup;
    }


    String locationString() {
        StringBuilder locationData = new StringBuilder();
        for (int i = 0; i < districtSelectedArray.size(); i++) {
            if (i != districtSelectedArray.size() - 1) {
                locationData.append("\"")
                        .append(districtSelectedArray.get(i))
                        .append("\"")
                        .append(",");
            } else {
                locationData.append("\"")
                        .append(districtSelectedArray.get(i))
                        .append("\"");
            }

        }

        String professonalGroup = new StringBuilder().append("[")
                .append(locationData.toString())
                .append("]")
                .toString();
        return professonalGroup;
    }


    class SendChoiceInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                if(progress.isShowing())
                    progress.dismiss();

                Utils.ShowAlert(RegistrationChoiceSelectionSecondPage.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    //progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
                        if(progress.isShowing())
                            progress.dismiss();

                       String error= jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationChoiceSelectionSecondPage.this,
                                error, Toast.LENGTH_LONG).show();
                    } else {
                        new RegistrationChoiceSelectionSecondPage.FetchConstant().execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(RegistrationChoiceSelectionSecondPage.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, strings[0]);
            Request request = new Request.Builder()
                    .url(Utils.SEND_INFO)
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


    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                if(progress.isShowing())
                    progress.dismiss();
                Utils.ShowAlert(RegistrationChoiceSelectionSecondPage.this, getString(R.string.no_internet_connection));
            } else {
               /* if(progress.isShowing())
                    progress.dismiss();*/
                Log.i("constantval",this.getClass().getSimpleName()+"_nextfetchval: "+s);
                Intent signupIntent;
                signupIntent = new Intent(RegistrationChoiceSelectionSecondPage.this,
                        RegistrationChoiceSelectionThirdPage.class);
                signupIntent.putExtra("constants", s);
                startActivity(signupIntent);
                finish();
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
           // Login.currentMobileSignupStep += 1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep)
                    .url(Utils.STEP_CONSTANT_FETCH + 10)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
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
        protected void onPreExecute() {
            super.onPreExecute();
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
                    .url(Utils.STEP_CONSTANT_FETCH + 8)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 8);
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
            if (s == null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }

                Utils.ShowAlert(RegistrationChoiceSelectionSecondPage.this, getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                Log.i("constantval",this.getClass().getSimpleName()+"_backfetchval: "+s);
                startActivity(new Intent(RegistrationChoiceSelectionSecondPage.this,
                        RegistrationChoiceSelectionFirstPage.class).
                        putExtra("constants", s));
                finish();
            }
        }
    }
}
