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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HandshakeCompletedListener;

import me.bendik.simplerangeview.SimpleRangeView;

public class RegistrationChoiceSelectionThirdPage extends AppCompatActivity {

    Button next;

    ImageView back, down_arrow_religion;

    ArrayList<String> muslimCastSelectedArray = new ArrayList<String>();
    ArrayList<String> hinduCastSelectedArray = new ArrayList<String>();
    ArrayList<String> christianCastSelectedArray = new ArrayList<String>();
    ArrayList<String> marriageSelectedArray = new ArrayList<String>();

    Map<Integer, String> prayerMale = new HashMap<Integer, String>();

    Map<Integer, String> prayerFemale = new HashMap<Integer, String>();

    Map<Integer, String> fasting = new HashMap<Integer, String>();

    Map<Integer, String> hijab = new HashMap<Integer, String>();

    public static int castReligionChoice = 0;

    int namajStart, rojaStart, hijabStart, namajEnd, rojaEnd, hijabEnd;

    public static String religionValue="", castValue="", otherCast = "", otherReligion = "";
    public static String jobAfterMarriage = "", maritalStatus = "", religionStatus = "", houseStatus = "";

    public static ArrayList<String> jobArray = new ArrayList<String>();
    public static ArrayList<String> jobConstant = new ArrayList<String>();

    public static ArrayList<String> marriageArray = new ArrayList<String>();
    public static ArrayList<String> marriageArrayConstant = new ArrayList<String>();

    public static ArrayList<String> religionArray = new ArrayList<String>();
    public static ArrayList<String> religionArrayConstant = new ArrayList<String>();

    public static ArrayList<String> muslimCast = new ArrayList<String>();
    public static ArrayList<String> muslimCastConstant = new ArrayList<String>();

    public static ArrayList<String> hinduCast = new ArrayList<String>();
    public static ArrayList<String> hinduCastConstant = new ArrayList<String>();

    public static ArrayList<String> christianCast = new ArrayList<String>();
    public static ArrayList<String> christianCastConstant = new ArrayList<String>();

    public static ArrayList<String> houseArray = new ArrayList<String>();
    public static ArrayList<String> houseConstant = new ArrayList<String>();

    LinearLayout jobLayout, maritalStatusLayout, religionLayout, mCastLayout, hCastLayout, cCastLayout,
            namajLayout, rojaLayout, hijabLayout;
    public LinearLayout onlyForMuslimLayout, muslimCastLayout, hinduCastLayout, christianCastLayout;

    public CheckBox muslimCheckBox, hinduCheckBox, christianCheckBox, marriageCheckBox;

    public TextView jobLabel, maritalStatusLabel, religionLabel, castLabel,
            namajLabel, rojaLabel, hijabLabel, houseStatusLabel;
    public TextView religionText, /*maritalText,*/ jobText, houseStatusTextView;

    SimpleRangeView rangeView_namaj, rangeView_roja, rangeView_hijab;
    public static int job = -1;
    public static int marriage = -1;
    public static int religion = -1;
    public static int house = -1;

    public static int selectedPopUp = 0;
    int currentStep;
    String constant;
    ProgressDialog progress;

    OkHttpClient client;

    private LinearLayout houseLinearLayout;
    SharePref sharePref;
    String religionName = "";
    private boolean isSignUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice_selection_third_page);

        sharePref = new SharePref(RegistrationChoiceSelectionThirdPage.this);
//        sharePref.set_data("religion", "মুসলিম");
//        "1":"মুসলিম","2":"হিন্দু","3":"খ্রীষ্টান","4":"বৌদ্ধ","5":"অন্যান্য "        for testing
        religionName = sharePref.get_data("religion").trim();
        progress = new ProgressDialog(RegistrationChoiceSelectionThirdPage.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(false);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        isSignUp = getIntent().getBooleanExtra("isSignUp", false);
        client = new OkHttpClient();

//        currentStep = 6;
        Log.i("gender", sharePref.get_data("gender"));

        try {
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject jobAfterMarriageObject = jsonObject.getJSONObject("job_after_marriage");
            JSONObject meritalStatusObject = jsonObject.getJSONObject("marital_status_constant");
            JSONObject religionObject = jsonObject.getJSONObject("religion_constant");
            JSONObject muslimCastObject = jsonObject.getJSONObject("muslim_cast");
            JSONObject hinduCastObject = jsonObject.getJSONObject("hindu_cast");
            JSONObject christianCastObject = jsonObject.getJSONObject("christian_cast");

            JSONObject prayerMaleObject = jsonObject.getJSONObject("prayer_options_male");
            JSONObject prayerFemaleObject = jsonObject.getJSONObject("prayer_options_female");
            JSONObject fastingObject = jsonObject.getJSONObject("fasting");
            JSONObject hijabObject = jsonObject.getJSONObject("hijab_options");
            JSONObject houseObject = jsonObject.getJSONObject("house_options");


            for (int i = 0; i < jobAfterMarriageObject.length(); i++) {
                jobConstant.add(jobAfterMarriageObject.names().getString(i));
                jobArray.add((String) jobAfterMarriageObject.get(jobAfterMarriageObject.names().getString(i)));
            }
            for (int i = 0; i < meritalStatusObject.length(); i++) {
                marriageArrayConstant.add(meritalStatusObject.names().getString(i));
                marriageArray.add((String) meritalStatusObject.get(meritalStatusObject.names().getString(i)));
            }
            for (int i = 0; i < religionObject.length(); i++) {
                religionArrayConstant.add(religionObject.names().getString(i));
                religionArray.add((String) religionObject.get(religionObject.names().getString(i)));
            }
            for (int i = 0; i < muslimCastObject.length(); i++) {
                muslimCastConstant.add(muslimCastObject.names().getString(i));
                muslimCast.add((String) muslimCastObject.get(muslimCastObject.names().getString(i)));
            }
            for (int i = 0; i < hinduCastObject.length(); i++) {
                hinduCastConstant.add(hinduCastObject.names().getString(i));
                hinduCast.add((String) hinduCastObject.get(hinduCastObject.names().getString(i)));
            }
            for (int i = 0; i < christianCastObject.length(); i++) {
                christianCastConstant.add(christianCastObject.names().getString(i));
                christianCast.add((String) christianCastObject.get(christianCastObject.names().getString(i)));
            }

            Log.i("val","cons: "+Arrays.toString(hinduCastConstant.toArray()));
            Log.i("val","name: "+Arrays.toString(hinduCast.toArray()));

            for (int i = 0; i < prayerMaleObject.length(); i++) {
                prayerMale.put(Integer.parseInt(prayerMaleObject.names().getString(i)), (String) prayerMaleObject.get(prayerMaleObject.names().getString(i)));
            }

            for (int i = 0; i < prayerFemaleObject.length(); i++) {
                prayerFemale.put(Integer.parseInt(prayerFemaleObject.names().getString(i)), (String) prayerFemaleObject.get(prayerFemaleObject.names().getString(i)));
            }

            for (int i = 0; i < fastingObject.length(); i++) {
                fasting.put(Integer.parseInt(fastingObject.names().getString(i)), (String) fastingObject.get(fastingObject.names().getString(i)));
            }

            for (int i = 0; i < hijabObject.length(); i++) {
                hijab.put(Integer.parseInt(hijabObject.names().getString(i)), (String) hijabObject.get(hijabObject.names().getString(i)));
            }

            for (int i = 0; i < houseObject.length(); i++) {
                houseConstant.add(houseObject.names().getString(i));
                houseArray.add((String) houseObject.get(houseObject.names().getString(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        rangeView_namaj = (SimpleRangeView) findViewById(R.id.namaj);
        rangeView_roja = (SimpleRangeView) findViewById(R.id.roja);
        rangeView_hijab = (SimpleRangeView) findViewById(R.id.hajab);

        jobText = (TextView) findViewById(R.id.job_text_view);
//        maritalText = (TextView) findViewById(R.id.marital_text_view);

        onlyForMuslimLayout = (LinearLayout) findViewById(R.id.only_for_muslim);
        onlyForMuslimLayout.setVisibility(View.GONE);

        religionText = (TextView) findViewById(R.id.religion_cast_text_view);

        jobLayout = (LinearLayout) findViewById(R.id.job);

//        maritalStatusLayout = (LinearLayout) findViewById(R.id.marital);

        religionLayout = (LinearLayout) findViewById(R.id.cast_religion);

        namajLayout = (LinearLayout) findViewById(R.id.namaj_layout);

        rojaLayout = (LinearLayout) findViewById(R.id.roja_layout);

        hijabLayout = (LinearLayout) findViewById(R.id.hijab_layout);


        jobLabel = (TextView) findViewById(R.id.job_label);
        maritalStatusLabel = (TextView) findViewById(R.id.marital_status_label);
        religionLabel = (TextView) findViewById(R.id.religion_cast_label);
        namajLabel = (TextView) findViewById(R.id.namaj_label);
        rojaLabel = (TextView) findViewById(R.id.roja_label);
        hijabLabel = (TextView) findViewById(R.id.hijab_label);
        castLabel = (TextView) findViewById(R.id.cast_label);
        down_arrow_religion = findViewById(R.id.down_arrow_religion);
        castLabel.setVisibility(View.GONE);

        maritalStatusLayout = (LinearLayout) findViewById(R.id.marital_status);
        maritalStatusLayout.removeAllViews();
        for (int i = 0; i < marriageArrayConstant.size(); i++) {
            marriageCheckBox = new CheckBox(this);
            marriageCheckBox.setText(marriageArray.get(i) + "   ");
            marriageCheckBox.setId(Integer.parseInt(1000 + marriageArrayConstant.get(i)));
            marriageCheckBox.setPadding(0, 0, 5, 0);
            final int index = i;
            marriageCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(1000+marriageArrayConstant.get(index)));
                    if (checkBox.isChecked()) {
                        marriageSelectedArray.add(marriageArrayConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        marriageSelectedArray.remove(marriageArrayConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionThirdPage.this, "id  : " + muslimCastConstant.get(index), Toast.LENGTH_LONG).show();
                }
            });
            maritalStatusLayout.addView(marriageCheckBox);
        }


        muslimCastLayout = (LinearLayout) findViewById(R.id.muslim_cast);


        houseStatusLabel = findViewById(R.id.house_status_label);
        houseLinearLayout = findViewById(R.id.house_linearLayout);
        houseStatusTextView = findViewById(R.id.house_text_view);
        muslimCastLayout.removeAllViews();
        for (int i = 0; i < muslimCastConstant.size(); i++) {
            muslimCheckBox = new CheckBox(this);
            muslimCheckBox.setText(muslimCast.get(i) + "   ");
            muslimCheckBox.setId(Integer.parseInt(muslimCastConstant.get(i)));
            muslimCheckBox.setPadding(0, 0, 5, 0);
            final int index = i;
            muslimCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(muslimCastConstant.get(index)));
                    if (checkBox.isChecked()) {
                        muslimCastSelectedArray.add(muslimCastConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        muslimCastSelectedArray.remove(muslimCastConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionThirdPage.this, "id  : " + muslimCastConstant.get(index), Toast.LENGTH_LONG).show();
                }
            });
            muslimCastLayout.addView(muslimCheckBox);
        }

        hinduCastLayout = (LinearLayout) findViewById(R.id.hindu_cast);
        hinduCastLayout.removeAllViews();
        for (int i = 0; i < hinduCastConstant.size(); i++) {
            Log.i("checkbox:","data: "+hinduCast.get(i)+" "+hinduCastConstant.get(i));
            hinduCheckBox = new CheckBox(this);
            hinduCheckBox.setText(hinduCast.get(i) + "   ");
            hinduCheckBox.setId(Integer.parseInt("100"+hinduCastConstant.get(i)));// here 100 added for making unique hinducast checkbox
            final int index = i;
            hinduCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt("100"+hinduCastConstant.get(index)));// here 100 added for making unique hinducast checkbox
                    if (checkBox.isChecked()) {
                        hinduCastSelectedArray.add(hinduCastConstant.get(index));
                        Log.i("checkbox:","onclick: "+index+" "+hinduCastConstant.get(index)
                                +" " +Arrays.toString(hinduCastSelectedArray.toArray()));
                    } else if (!checkBox.isChecked()) {
                        hinduCastSelectedArray.remove(hinduCastConstant.get(index));
                        Log.i("checkbox:","onclick: "+index+" "+hinduCastConstant.get(index)
                                +" " +Arrays.toString(hinduCastSelectedArray.toArray()));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionThirdPage.this, "id  : " + hinduCastConstant.get(index), Toast.LENGTH_LONG).show();
                }
            });
            hinduCastLayout.addView(hinduCheckBox);
        }


        christianCastLayout = (LinearLayout) findViewById(R.id.christian_cast);
        christianCastLayout.removeAllViews();
        for (int i = 0; i < christianCastConstant.size(); i++) {
            christianCheckBox = new CheckBox(this);
            christianCheckBox.setText(christianCast.get(i) + "   ");
            christianCheckBox.setId(Integer.parseInt(christianCastConstant.get(i)));
            final int index = i;
            christianCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) findViewById(Integer.parseInt(christianCastConstant.get(index)));
                    if (checkBox.isChecked()) {
                        christianCastSelectedArray.add(christianCastConstant.get(index));
                    } else if (!checkBox.isChecked()) {
                        christianCastSelectedArray.remove(christianCastConstant.get(index));
                    }
                    //Toast.makeText(RegistrationChoiceSelectionThirdPage.this, "id  : " + christianCastConstant.get(index), Toast.LENGTH_LONG).show();
                }
            });
            christianCastLayout.addView(christianCheckBox);
        }


        christianCastLayout.setVisibility(View.GONE);
        muslimCastLayout.setVisibility(View.GONE);
        hinduCastLayout.setVisibility(View.GONE);


        if (sharePref.get_data("gender").equalsIgnoreCase("female")) {
            namajEnd = prayerMale.size() - 1;
        } else {
            namajEnd = prayerFemale.size() - 1;
        }

        rojaEnd = fasting.size() - 1;
        hijabEnd = hijab.size() - 1;

        jobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 1;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class));
            }
        });
//        maritalStatusLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedPopUp = 2;
//                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class));
//            }
//        });

        religion = Integer.parseInt(religionName);
        if(religion > 0) {
            setReligionView();
            down_arrow_religion.setVisibility(View.GONE);
        }else{
            down_arrow_religion.setVisibility(View.VISIBLE);
            religionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPopUp = 3;
                    //castReligionChoice = 1;
                    Intent intent = new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class);
                    intent.putExtra("constants", constant);
                    startActivity(intent);
                }
            });
        }

        houseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 4;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class));
            }
        });

        rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setLabelColor(Color.TRANSPARENT);

        rangeView_namaj.setFixedLabelColor(Color.TRANSPARENT);

        rangeView_namaj.setStart(0);
        if (sharePref.get_data("gender").equalsIgnoreCase("female")) {
            rangeView_namaj.setCount(5);
            rangeView_namaj.setEnd(4);
        } else {
            rangeView_namaj.setCount(4);
            rangeView_namaj.setEnd(3);
        }


        rangeView_namaj.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setLabelColor(Color.TRANSPARENT);
                namajStart = rangeView_namaj.getStart();
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setLabelColor(Color.TRANSPARENT);
                namajEnd = rangeView_namaj.getEnd();

            }
        });

        rangeView_namaj.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {

                if (sharePref.get_data("gender").equalsIgnoreCase("female"))
                    return prayerMale.get(i);
                else {
                    return prayerFemale.get(i);
                }
            }
        });


        rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_roja.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_roja.setLabelColor(Color.TRANSPARENT);

        rangeView_roja.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_roja.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_roja.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_roja.setLabelColor(Color.TRANSPARENT);
                rojaStart = rangeView_roja.getStart();
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_roja.setLabelColor(Color.TRANSPARENT);
                rojaEnd = rangeView_roja.getEnd();

            }
        });

        rangeView_roja.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return fasting.get(i);
            }
        });
        rangeView_roja.setStart(0);
        rangeView_roja.setEnd(2);

        rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setLabelColor(Color.TRANSPARENT);

        rangeView_hijab.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setLabelColor(Color.TRANSPARENT);
                hijabStart = rangeView_hijab.getStart();
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setLabelColor(Color.TRANSPARENT);
                hijabEnd = rangeView_hijab.getEnd();
            }
        });

        rangeView_hijab.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return hijab.get(i);
            }
        });
        rangeView_hijab.setStart(0);
        rangeView_hijab.setEnd(2);


        if (sharePref.get_data("gender").equalsIgnoreCase("female")) {
            jobLabel.setVisibility(View.GONE);
            jobLayout.setVisibility(View.GONE);

            maritalStatusLabel.setText("পাত্রের বৈবাহিক অবস্থা*");

            religionLabel.setText("পাত্রের ধর্ম");

            hijabLabel.setVisibility(View.GONE);

            hijabLayout.setVisibility(View.GONE);
        } else if (sharePref.get_data("gender").equalsIgnoreCase("male")) {

            maritalStatusLabel.setText("পাত্রীর বৈবাহিক অবস্থা*");

            religionLabel.setText("পাত্রীর ধর্ম");
        }


        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.i("checkbox", Arrays.toString(hinduCastSelectedArray.toArray()));
                //Log.i("checkbox", Arrays.toString(muslimCastSelectedArray.toArray()));

                if (sharePref.get_data("gender").equalsIgnoreCase("male") &&
                        jobAfterMarriage.isEmpty()) {

                    Toast.makeText(getBaseContext(),
                            getResources().getString(R.string.bride_job_status_selction_message),
                            Toast.LENGTH_LONG).show();
                    jobLabel.getParent().
                            requestChildFocus(jobLabel, jobLabel);
                    return;
                }

//                if (maritalStatus.isEmpty()) {
//                    Toast.makeText(getBaseContext(),
//                            getResources().getString(R.string.choose_maritial_status_selction_message),
//                            Toast.LENGTH_LONG).show();
//                    maritalStatusLabel.getParent().
//                            requestChildFocus(maritalStatusLabel, maritalStatusLabel);
//                    return;
//                }
                if (marriageSelectedArray.size() == 0) {
                    Toast.makeText(getBaseContext(),
                            getResources().getString(R.string.choose_maritial_status_selction_message),
                            Toast.LENGTH_LONG).show();
                    maritalStatusLabel.getParent().
                            requestChildFocus(maritalStatusLabel, maritalStatusLabel);
                    return;
                }

                if (religionStatus.isEmpty()) {
                    Toast.makeText(getBaseContext(), getResources().
                                    getString(R.string.choose_religion_status_selction_message),
                            Toast.LENGTH_LONG).show();
                    religionLabel.getParent().
                            requestChildFocus(religionLabel, religionLabel);
                    return;
                }

                String maritalStatus = "";
                for (int i = 0; i < marriageSelectedArray.size(); i++) {
                    maritalStatus += "\"" + marriageSelectedArray.get(i) +"\"";
                    if (i != marriageSelectedArray.size() - 1) {
                        maritalStatus += ",";
                    }
                }

                String castStatus = "";
                if (religionStatus.equals("1")) {
                    for (int i = 0; i < muslimCastSelectedArray.size(); i++) {
                        castStatus += "\"" + muslimCastSelectedArray.get(i) + "\"";
                        if (i != muslimCastSelectedArray.size() - 1) {
                            castStatus += ",";
                        }
                    }

                    if (castStatus.isEmpty()) {
                        Toast.makeText(getBaseContext(), getString(R.string.choose_muslim_cast_name__selction_message),
                                Toast.LENGTH_SHORT).show();
                        castLabel.getParent().
                                requestChildFocus(castLabel, castLabel);
                        return;
                    }
                } else if (religionStatus.equals("2")) {
                    for (int i = 0; i < hinduCastSelectedArray.size(); i++) {
                        castStatus += "\"" + hinduCastSelectedArray.get(i) + "\"";
                        if (i != hinduCastSelectedArray.size() - 1) {
                            castStatus += ",";
                        }
                    }

                    if (castStatus.isEmpty()) {
                        Toast.makeText(getBaseContext(), getString(R.string.choose_cast_name__selction_message),
                                Toast.LENGTH_SHORT).show();
                        castLabel.getParent().
                                requestChildFocus(castLabel, castLabel);
                        return;
                    }
                } else if (religionStatus.equals("3")) {
                    for (int i = 0; i < christianCastSelectedArray.size(); i++) {
                        castStatus += "\"" + christianCastSelectedArray.get(i) + "\"";
                        if (i != christianCastSelectedArray.size() - 1) {
                            castStatus += ",";
                        }
                    }

                    if (castStatus.isEmpty()) {
                        Toast.makeText(getBaseContext(), getString(R.string.choose_cast_name__selction_message),
                                Toast.LENGTH_SHORT).show();
                        castLabel.getParent().
                                requestChildFocus(castLabel, castLabel);
                        return;
                    }
                }


                if (sharePref.get_data("gender").equalsIgnoreCase("female") && houseStatus.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.select_house_option_message), Toast.LENGTH_LONG).show();
                    houseStatusLabel.getParent().
                            requestChildFocus(houseStatusLabel, houseStatusLabel);
                    return;
                }

                String data = new StringBuilder().append("{")
                        .append("\"job_permission\":")
                        .append("\"")
                        .append(jobAfterMarriage)
                        .append("\"")
                        .append(",")
                        .append("\"marital_status\":")
                        .append("[")
                        .append(maritalStatus)
                        .append("]")
                        .append(",")
                        .append("\"religion\":")
                        .append("\"")
                        .append(religionStatus)
                        .append("\"")
                        .append(",")
                        .append("\"cast\":")
                        .append("[")
                        .append(castStatus)
                        .append("]")
                        .append(",")
                        .append("\"other_cast\":")
                        .append("\"")
                        .append(otherCast)
                        .append("\"")
                        .append(",")
                        .append("\"other_religion\":")
                        .append("\"")
                        .append(otherReligion)
                        .append("\"")
                        .append(",")
                        .append("\"prayer\":")
                        .append("\"")
                        .append(namajStart)
                        .append(";")
                        .append(namajEnd)
                        .append("\"")
                        .append(",")
                        .append("\"fast\":")
                        .append("\"")
                        .append(rojaStart)
                        .append(";")
                        .append(rojaEnd)
                        .append("\"")
                        .append(",")
                        .append("\"hijab\":")
                        .append("\"")
                        .append(hijabStart)
                        .append(";")
                        .append(hijabEnd)
                        .append("\"")
                        .append(",")
                        .append("\"house_in_dhaka\":")
                        .append("\"")
                        .append(houseStatus)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(10)
                        .append("}")
                        .toString();
                Log.e("response","finaldata: "+ data);

                new RegistrationChoiceSelectionThirdPage.SendChoiceThird().execute(data, Utils.SEND_INFO);

            }
        });

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new Intent(RegistrationChoiceSelectionThirdPage.this, Login.class);
                finish();*/
                new GetPreviousStepFetchConstant().execute();
            }
        });
    }


    class SendChoiceThird extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progress.isShowing())
                progress.dismiss();
            if (s == null) {
                Utils.ShowAlert(RegistrationChoiceSelectionThirdPage.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
//                        if (progress.isShowing())
//                            progress.dismiss();

                        String error = jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(RegistrationChoiceSelectionThirdPage.this, error,
                                Toast.LENGTH_LONG).show();
                    } else {
//                        new RegistrationChoiceSelectionThirdPage.FetchConstant().execute();
                        clearStaticData();
                        Intent intent = new Intent(getApplicationContext(),
                                HomeScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("constants", s);
                        intent.putExtra("isSignUp", true);
                        startActivity(intent);
//                        startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this,
//                                HomeScreen.class)
//                                .putExtra("constants", s)
//                                .putExtra("isSignUp", true));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, strings[0]);
            Request request = new Request.Builder()
                    .url(strings[1])
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


//    public class FetchConstant extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            if (s == null) {
//                if (progress.isShowing())
//                    progress.dismiss();
//                Utils.ShowAlert(RegistrationChoiceSelectionThirdPage.this, getString(R.string.no_internet_connection));
//            } else {
//               /* if (progress.isShowing())
//                    progress.dismiss();*/
//                clearStaticData();
//                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
//                Intent signupIntent;
//                signupIntent = new Intent(RegistrationChoiceSelectionThirdPage.this,
//                        RegistrationPersonalInformation.class);
//                signupIntent.putExtra("constants", s);
//                startActivity(signupIntent);
//                finish();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... parameters) {
//            final String token = sharePref.get_data("token");
//            // Login.currentMobileSignupStep += 1;
//            Request request = new Request.Builder()
//                    // .url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep)
//                    .url(Utils.STEP_CONSTANT_FETCH + 7)
//                    .addHeader("Authorization", "Token token=" + token)
//                    .build();
//            try {
//                OkHttpClient client = new OkHttpClient();
//                Response response = client.newCall(request).execute();
//                String responseString = response.body().string();
//                Log.e(Utils.LOGIN_DEBUG, responseString);
//                response.body().close();
//                return responseString;
//            } catch (Exception e) {
//                e.printStackTrace();
////                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();

        if (sharePref.get_data("gender").equalsIgnoreCase("male")) {
            houseStatusLabel.setVisibility(View.GONE);
            houseLinearLayout.setVisibility(View.GONE);
        }

        if (selectedPopUp == 1 && job != -1) {
            jobAfterMarriage = jobConstant.get(job - 1);
            jobText.setText(jobArray.get(job - 1));
        }
//        if (selectedPopUp == 2 && marriage != -1) {
//            maritalStatus = marriageArrayConstant.get(marriage - 1);
//            maritalText.setText(marriageArray.get(marriage - 1));
//        }
        if (selectedPopUp == 3 && religion != -1) {
            religionStatus = religionArrayConstant.get(religion - 1);
            religionText.setText(religionArray.get(religion - 1));
            if (religionStatus.equals("1")) {
                castLabel.setText("সম্প্রদায়*");
                castLabel.setVisibility(View.VISIBLE);
                muslimCastLayout.setVisibility(View.VISIBLE);
                hinduCastLayout.setVisibility(View.GONE);
                christianCastLayout.setVisibility(View.GONE);
                onlyForMuslimLayout.setVisibility(View.VISIBLE);
            } else if (religionStatus.equals("2")) {
                castLabel.setText("বর্ণ*");
                castLabel.setVisibility(View.VISIBLE);
                hinduCastLayout.setVisibility(View.VISIBLE);
                muslimCastLayout.setVisibility(View.GONE);
                christianCastLayout.setVisibility(View.GONE);
                onlyForMuslimLayout.setVisibility(View.GONE);
            } else if (religionStatus.equals("3")) {
                castLabel.setText("বর্ণ*");
                castLabel.setVisibility(View.VISIBLE);
                christianCastLayout.setVisibility(View.VISIBLE);
                muslimCastLayout.setVisibility(View.GONE);
                hinduCastLayout.setVisibility(View.GONE);
                onlyForMuslimLayout.setVisibility(View.GONE);
            } else {
                castLabel.setVisibility(View.GONE);
                christianCastLayout.setVisibility(View.GONE);
                muslimCastLayout.setVisibility(View.GONE);
                hinduCastLayout.setVisibility(View.GONE);

                onlyForMuslimLayout.setVisibility(View.GONE);
            }
        }

        if (selectedPopUp == 4 && house != -1) {
            houseStatus = houseConstant.get(house - 1);
            houseStatusTextView.setText(houseArray.get(house - 1));
        }

    }

    private void setReligionView()
    {
        if (religion != -1) {
            religionStatus = religionArrayConstant.get(religion - 1);
            religionText.setText(religionArray.get(religion - 1));
            if (religionStatus.equals("1")) {
                castLabel.setText("সম্প্রদায়*");
                castLabel.setVisibility(View.VISIBLE);
                muslimCastLayout.setVisibility(View.VISIBLE);
                hinduCastLayout.setVisibility(View.GONE);
                christianCastLayout.setVisibility(View.GONE);
                onlyForMuslimLayout.setVisibility(View.VISIBLE);
            } else if (religionStatus.equals("2")) {
                castLabel.setText("বর্ণ*");
                castLabel.setVisibility(View.VISIBLE);
                hinduCastLayout.setVisibility(View.VISIBLE);
                muslimCastLayout.setVisibility(View.GONE);
                christianCastLayout.setVisibility(View.GONE);
                onlyForMuslimLayout.setVisibility(View.GONE);
            } else if (religionStatus.equals("3")) {
                castLabel.setText("বর্ণ*");
                castLabel.setVisibility(View.VISIBLE);
                christianCastLayout.setVisibility(View.VISIBLE);
                muslimCastLayout.setVisibility(View.GONE);
                hinduCastLayout.setVisibility(View.GONE);
                onlyForMuslimLayout.setVisibility(View.GONE);
            } else {
                castLabel.setVisibility(View.GONE);
                christianCastLayout.setVisibility(View.GONE);
                muslimCastLayout.setVisibility(View.GONE);
                hinduCastLayout.setVisibility(View.GONE);

                onlyForMuslimLayout.setVisibility(View.GONE);
            }
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
                    .url(Utils.STEP_CONSTANT_FETCH + 9)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 9);
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
            if (progress.isShowing()) {
                progress.dismiss();
            }
            Log.i("urldata", s + "");
            if (s == null) {
                Utils.ShowAlert(RegistrationChoiceSelectionThirdPage.this, getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                clearStaticData();
                if(isSignUp){
                    finish();
                }else {
                    Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
                    startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this,
                            RegistrationChoiceSelectionSecondPage.class).
                            putExtra("constants", s));
                    finish();
                }
            }
        }
    }

    private void clearStaticData() {
        jobArray.clear();
        jobConstant.clear();
        marriageArray.clear();
        marriageArrayConstant.clear();
        religionArray.clear();
        religionArrayConstant.clear();
        muslimCast.clear();
        muslimCastConstant.clear();
        hinduCast.clear();
        hinduCastConstant.clear();
        christianCast.clear();
        christianCastConstant.clear();
        houseArray.clear();
        houseConstant.clear();


        castReligionChoice = 0;
        religionValue = "";
        castValue = "";
        otherCast = "";
        otherReligion = "";
        jobAfterMarriage = "";
        maritalStatus = "";
        religionStatus = "";
        houseStatus = "";

        job = -1;
        marriage = -1;
        religion = -1;
        house = -1;
        selectedPopUp = 0;
    }
}
