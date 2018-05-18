package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.BrotherViewAdapter;
import com.nascenia.biyeta.adapter.OtherViewAdapter;
import com.nascenia.biyeta.adapter.SisterViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PopUpFamilyInfoSecondPage extends AppCompatActivity {

    public static Map<Integer, String> occupationArrayBrother = new HashMap<Integer, String>();
    public static Map<Integer, String> professionalGroupArrayBrother = new HashMap<Integer, String>();
    public static Map<Integer, String> maritalStatusArrayBrother = new HashMap<Integer, String>();
    public static Map<Integer, String> ageArrayBrother = new HashMap<Integer, String>();
    public static Map<Integer, String> occupationArrayBrotherSpouse = new HashMap<Integer, String>();
    public static Map<Integer, String> professionalGroupArrayBrotherSpouse = new HashMap<Integer, String>();


    public static Map<Integer, String> occupationArraySister = new HashMap<Integer, String>();
    public static Map<Integer, String> professionalGroupArraySister = new HashMap<Integer, String>();
    public static Map<Integer, String> maritalStatusArraySister = new HashMap<Integer, String>();
    public static Map<Integer, String> ageArraySister = new HashMap<Integer, String>();
    public static Map<Integer, String> occupationArraySisterSpouse = new HashMap<Integer, String>();
    public static Map<Integer, String> professionalGroupArraySisterSpouse = new HashMap<Integer, String>();

    public static Map<Integer, String> occupationArrayOther = new HashMap<Integer, String>();
    public static Map<Integer, String> professionalGroupArrayOther = new HashMap<Integer, String>();
    public static Map<Integer, String> maritalStatusArrayOther = new HashMap<Integer, String>();
    public static Map<Integer, String> relationStatusArrayOther = new HashMap<Integer, String>();
    public static Map<Integer, String> ageArrayOther = new HashMap<Integer, String>();

    String[] numberOfBrotherSister = {"০", "১", "২", "৩", "৪", "৫", "৫+"};

    int[] constantOfBrotherSister = {0, 1, 2, 3, 4, 5, 6};

    public static String value;
    int newValue;

    String[] age;

    ArrayList<String> professonalGroup = new ArrayList<String>();
    String[] professonalGroupName = new String[professonalGroup.size()];
    ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    String[] professonalGroupConstantValue = new String[professonalGroup.size()];

    ArrayList<String> occupation = new ArrayList<String>();
    String[] occupationName = new String[occupation.size()];
    ArrayList<String> occupationConstant = new ArrayList<String>();
    String[] occupationConstantValue = new String[occupation.size()];

    ArrayList<String> brotherOccupation = new ArrayList<String>();
    String[] brotherOccupationName = new String[brotherOccupation.size()];
    ArrayList<String> brotherOccupationConstant = new ArrayList<String>();
    String[] brotherOccupationConstantValue = new String[brotherOccupation.size()];

    ArrayList<String> relation = new ArrayList<String>();
    String[] relationName = new String[relation.size()];
    ArrayList<String> relationConstant = new ArrayList<String>();
    String[] relationConstantValue = new String[relation.size()];

    ArrayList<String> maritalStatusMale = new ArrayList<String>();
    String[] maritalStatusNameMale = new String[maritalStatusMale.size()];
    ArrayList<String> maritalStatusConstantMale = new ArrayList<String>();
    String[] maritalStatusConstantValueMale = new String[maritalStatusMale.size()];

    ArrayList<String> maritalStatusFemale = new ArrayList<String>();
    String[] maritalStatusNameFemale = new String[maritalStatusFemale.size()];
    ArrayList<String> maritalStatusConstantFemale = new ArrayList<String>();
    String[] maritalStatusConstantValueFemale = new String[maritalStatusFemale.size()];


    Button accept, reject;
    NumberPicker picker;
    String[] data = new String[]{};
    String constant;
    int position;

    private int brotherPickerSelectedValue = 0;
    private int sisterPickerSelectedValue = 0;

    TextView mTextViewTitle;

    String strUpdateData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_family_info_first_page);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("constant")) {
            String defaultValue = "";
            constant = extras.getString("constant", defaultValue);
        }
        if (extras.containsKey("position")) {
            int defaultValue = 0;
            position = extras.getInt("position", defaultValue);
        }

        strUpdateData = getIntent().getStringExtra("data");

        age = new String[100];

        for (int i = 0; i < 100; i++) {
            age[i] = getAgeInBangla(Integer.toString(i));
        }


        try {
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject professonalGroupObject = jsonObject.getJSONObject("professional_group_constant");
            JSONObject occupationObject = jsonObject.getJSONObject("occupation_constant");
            JSONObject relationObject = jsonObject.getJSONObject("relation_constant");
            JSONObject maritalStatusObjectMale = jsonObject.getJSONObject("marital_status_constant_male");
            JSONObject maritalStatusObjectFemale = jsonObject.getJSONObject("marital_status_constant_female");

            Log.i("occupation",occupationObject.toString());

            for (int i = 0; i < professonalGroupObject.length(); i++) {
                professonalGroupConstant.add(professonalGroupObject.names().getString(i));
                professonalGroup.add((String) professonalGroupObject.get(professonalGroupObject.names().getString(i)));
            }

            for (int i = 0; i < occupationObject.length(); i++) {
                String occupationName=(String) occupationObject.get(occupationObject.names().getString(i));
                if(!occupationName.equalsIgnoreCase("গৃহিণী")){
                    brotherOccupationConstant.add(occupationObject.names().getString(i));
                    brotherOccupation.add((String) occupationObject.get(occupationObject.names().getString(i)));
                }
            }

            for (int i = 0; i < occupationObject.length(); i++) {
                occupationConstant.add(occupationObject.names().getString(i));
                occupation.add((String) occupationObject.get(occupationObject.names().getString(i)));
                Log.e("occupation", (String) occupationObject.get(occupationObject.names().getString(i)));
            }

            for (int i = 0; i < relationObject.length(); i++) {
                relationConstant.add(relationObject.names().getString(i));
                relation.add((String) relationObject.get(relationObject.names().getString(i)));
            }

            for (int i = 0; i < maritalStatusObjectMale.length(); i++) {
                maritalStatusConstantMale.add(maritalStatusObjectMale.names().getString(i));
                maritalStatusMale.add((String) maritalStatusObjectMale.get(maritalStatusObjectMale.names().getString(i)));
            }

            for (int i = 0; i < maritalStatusObjectFemale.length(); i++) {
                maritalStatusConstantFemale.add(maritalStatusObjectFemale.names().getString(i));
                maritalStatusFemale.add((String) maritalStatusObjectFemale.get(maritalStatusObjectFemale.names().getString(i)));
            }

            professonalGroupName = professonalGroup.toArray(professonalGroupName);
            professonalGroupConstantValue = professonalGroupConstant.toArray(professonalGroupConstantValue);

            occupationName = occupation.toArray(occupationName);
            occupationConstantValue = occupationConstant.toArray(occupationConstantValue);

            brotherOccupationName = brotherOccupation.toArray(brotherOccupationName);
            brotherOccupationConstantValue = brotherOccupationConstant.toArray(brotherOccupationConstantValue);

            relationName = relation.toArray(relationName);
            relationConstantValue = relationConstant.toArray(relationConstantValue);

            maritalStatusNameMale = maritalStatusMale.toArray(maritalStatusNameMale);
            maritalStatusConstantValueMale = maritalStatusConstantMale.toArray(maritalStatusConstantValueMale);

            maritalStatusNameFemale = maritalStatusFemale.toArray(maritalStatusNameFemale);
            maritalStatusConstantValueFemale = maritalStatusConstantFemale.toArray(maritalStatusConstantValueFemale);

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }


        picker = (NumberPicker) findViewById(R.id.picker);
        accept = (Button) findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.cancel);
        mTextViewTitle = (TextView) findViewById(R.id.title);


        if (BrotherViewAdapter.selectedPopUp == 1) {
            // brother himself
            data = brotherOccupationName;
            mTextViewTitle.setText("পেশা");
        } else if (BrotherViewAdapter.selectedPopUp == 2) {
            data = professonalGroupName;
            mTextViewTitle.setText("প্রফেশনাল গ্রুপ");
        } else if (BrotherViewAdapter.selectedPopUp == 3) {
            data = maritalStatusNameMale;
            mTextViewTitle.setText("বৈবাহিক অবস্থা");
        } else if (BrotherViewAdapter.selectedPopUp == 4) {
            data = relationName;
            mTextViewTitle.setText("");
        } else if (BrotherViewAdapter.selectedPopUp == 5 || (strUpdateData!=null && strUpdateData.equalsIgnoreCase("age"))) {
            data = age;
            mTextViewTitle.setText("বয়স");
        } else if (BrotherViewAdapter.selectedPopUp == 6) {
            //brother spouse
            data = occupationName;
            mTextViewTitle.setText("পেশা");
        } else if (BrotherViewAdapter.selectedPopUp == 7) {
            data = professonalGroupName;
            mTextViewTitle.setText("প্রফেশনাল গ্রুপ");
        } else if (SisterViewAdapter.selectedPopUp == 1) {
            data = occupationName;
            mTextViewTitle.setText("পেশা");
        } else if (SisterViewAdapter.selectedPopUp == 2) {
            data = professonalGroupName;
            mTextViewTitle.setText("প্রফেশনাল গ্রুপ");
        } else if (SisterViewAdapter.selectedPopUp == 3) {
            data = maritalStatusNameFemale;
            mTextViewTitle.setText("বৈবাহিক অবস্থা");
        } else if (SisterViewAdapter.selectedPopUp == 4) {
            data = relationName;
            mTextViewTitle.setText("");
        } else if (SisterViewAdapter.selectedPopUp == 5) {
            data = age;
            mTextViewTitle.setText("বয়স");
        } else if (SisterViewAdapter.selectedPopUp == 6) {
            //brother spouse
            data = brotherOccupationName;
            mTextViewTitle.setText("পেশা");
        } else if (SisterViewAdapter.selectedPopUp == 7) {
            data = professonalGroupName;
            mTextViewTitle.setText("প্রফেশনাল গ্রুপ");
        }else if (OtherViewAdapter.selectedPopUp == 1 || (strUpdateData!=null && strUpdateData.equalsIgnoreCase("profession"))) {
            data = brotherOccupationName;
            mTextViewTitle.setText("পেশা");
        } else if (OtherViewAdapter.selectedPopUp == 2 || (strUpdateData!=null && strUpdateData.equalsIgnoreCase("professional_group"))) {
            data = professonalGroupName;
            mTextViewTitle.setText("প্রফেশনাল গ্রুপ");
        } else if (OtherViewAdapter.selectedPopUp == 3) {
            data = maritalStatusNameMale;
            mTextViewTitle.setText("বৈবাহিক অবস্থা");
        } else if (OtherViewAdapter.selectedPopUp == 4 || (strUpdateData!=null && strUpdateData.equalsIgnoreCase("relation"))) {
            data = relationName;
            mTextViewTitle.setText("সম্পর্ক");
        } else if (OtherViewAdapter.selectedPopUp == 5) {
            data = age;
            mTextViewTitle.setText("বয়স");
        } else if (RegistrationFamilyInfoSecondPage.selectedPopUp == 1) {
            data = numberOfBrotherSister;
            mTextViewTitle.setText("ভাইয়ের সংখ্যা");
        } else if (RegistrationFamilyInfoSecondPage.selectedPopUp == 2) {
            data = numberOfBrotherSister;
            mTextViewTitle.setText("বোনের সংখ্যা");
        }

        picker.setMinValue(0);
        picker.setMaxValue(data.length - 1);
        picker.setDisplayedValues(data);
        picker.setOnValueChangedListener(new ListListener());
        setDividerColor(picker, Color.parseColor("#626262"));
        setNumberPickerTextColor(picker, Color.parseColor("#626262"));


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //     Toast.makeText(getBaseContext(),RegistrationFamilyInfoSecondPage.selectedPopUp +"",Toast.LENGTH_LONG).show();
                if (RegistrationFamilyInfoSecondPage.selectedPopUp == 1){
                    //Toast.makeText(getBaseContext(),"brother",Toast.LENGTH_LONG).show();
                    RegistrationFamilyInfoSecondPage.numberOfBrother = constantOfBrotherSister[newValue];
                    RegistrationFamilyInfoSecondPage.brotherNumber.setText(data[newValue]);
                } else if (RegistrationFamilyInfoSecondPage.selectedPopUp == 2){
                    //  Toast.makeText(getBaseContext(),"sister",Toast.LENGTH_LONG).show();
                    RegistrationFamilyInfoSecondPage.numberOfSister = constantOfBrotherSister[newValue];
                    RegistrationFamilyInfoSecondPage.sisterNumber.setText(data[newValue]);
                }


                if (BrotherViewAdapter.selectedPopUp == 1){

                    //   BrotherViewAdapter.brotherOccupation.setText(occupationName[newVal]);

                    BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);
                    holder.brotherOccupation.setText(brotherOccupationName[newValue]);

                    value = brotherOccupationConstantValue[newValue];
                    occupationArrayBrother.put(position, value);
                } else if (BrotherViewAdapter.selectedPopUp == 2){

                    //BrotherViewAdapter.brotherProfessionalGroup.setText(professonalGroupName[newVal]);

                    BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);
                    holder.brotherProfessionalGroup.setText(professonalGroupName[newValue]);

                    value = professonalGroupConstantValue[newValue];
                    professionalGroupArrayBrother.put(position, value);
                } else if (BrotherViewAdapter.selectedPopUp == 3){
                    // BrotherViewAdapter.brotherMaritalStatus.setText(maritalStatusName[newVal]);

                    BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);
                    holder.brotherMaritalStatus.setText(maritalStatusNameMale[newValue]);


                    value = maritalStatusConstantValueMale[newValue];
                    maritalStatusArrayBrother.put(position, value);

//                    if (value.equals("4")){
//                   /* BrotherViewAdapter.MyViewHolder holder =
//                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
//                                    .findViewHolderForLayoutPosition(position);*/
//                        holder.spouse.setVisibility(View.VISIBLE);
//                    } else {
//                   /* BrotherViewAdapter.MyViewHolder holder = (BrotherViewAdapter.MyViewHolder)
//                            RegistrationFamilyInfoSecondPage.recyclerViewBrother.findViewHolderForLayoutPosition(position);*/
//                        holder.spouse.setVisibility(View.GONE);
//                    }
                } else if (BrotherViewAdapter.selectedPopUp == 5){
                    //BrotherViewAdapter.brotherAge.setText(age[newVal]);
                    BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);

                    holder.brotherAge.setText(age[newValue]);
                    value = "" + newValue;
                    ageArrayBrother.put(position, value);
                }else if (strUpdateData.equalsIgnoreCase("age")){
                    Intent intent = new Intent();
                    intent.putExtra("age_data", newValue + "");
                    setResult(RESULT_OK, intent);
                }else if (strUpdateData!= null && strUpdateData.equalsIgnoreCase("relation")){
                    Intent intent = new Intent();
                    intent.putExtra("relation_data", relationName[newValue]);
                    intent.putExtra("relation_value", relationConstant.get(newValue));
                    setResult(RESULT_OK, intent);
                } else if (BrotherViewAdapter.selectedPopUp == 6) {
                    //BrotherViewAdapter.brotherOcupationSpouse.setText(occupationName[newVal]);

                    BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);
                    holder.brotherOcupationSpouse.setText(occupationName[newValue]);

                    value = occupationConstantValue[newValue];
                    occupationArrayBrotherSpouse.put(position, value);
                } else if (BrotherViewAdapter.selectedPopUp == 7) {
                    //BrotherViewAdapter.brotherProfessionalGroupSpouse.setText(professonalGroupName[newVal]);

                    BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);
                    holder.brotherProfessionalGroupSpouse.setText(professonalGroupName[newValue]);
                    value = professonalGroupConstantValue[newValue];
                    professionalGroupArrayBrotherSpouse.put(position, value);
                } else if (SisterViewAdapter.selectedPopUp == 1) {
                    //  SisterViewAdapter.sisterOccupation.setText(occupationName[newVal]);

                    SisterViewAdapter.MyViewHolder holder =
                            (SisterViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewSister
                                    .findViewHolderForLayoutPosition(position);

                    holder.sisterOccupation.setText(occupationName[newValue]);

                    value = occupationConstantValue[newValue];
                    occupationArraySister.put(position, value);
                } else if (SisterViewAdapter.selectedPopUp == 2) {
                    //SisterViewAdapter.sisterProfessionalGroup.setText(professonalGroupName[newVal]);

                    SisterViewAdapter.MyViewHolder holder =
                            (SisterViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewSister
                                    .findViewHolderForLayoutPosition(position);

                    holder.sisterProfessionalGroup.setText(professonalGroupName[newValue]);

                    value = professonalGroupConstantValue[newValue];
                    professionalGroupArraySister.put(position, value);
                } else if (SisterViewAdapter.selectedPopUp == 3) {
                    // SisterViewAdapter.sisterMaritalStatus.setText(maritalStatusName[newVal]);

                    SisterViewAdapter.MyViewHolder holder =
                            (SisterViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewSister
                                    .findViewHolderForLayoutPosition(position);

                    holder.sisterMaritalStatus.setText(maritalStatusNameFemale[newValue]);

                    value = maritalStatusConstantValueFemale[newValue];
                    maritalStatusArraySister.put(position, value);

                    if (value.equals("4")){
                   /* BrotherViewAdapter.MyViewHolder holder =
                            (BrotherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewBrother
                                    .findViewHolderForLayoutPosition(position);*/
                        holder.spouse.setVisibility(View.VISIBLE);
                    } else {
                   /* BrotherViewAdapter.MyViewHolder holder = (BrotherViewAdapter.MyViewHolder)
                            RegistrationFamilyInfoSecondPage.recyclerViewBrother.findViewHolderForLayoutPosition(position);*/
                        holder.spouse.setVisibility(View.GONE);
                    }
                } else if (SisterViewAdapter.selectedPopUp == 5) {
                    // SisterViewAdapter.sisterAge.setText(age[newVal]);

                    SisterViewAdapter.MyViewHolder holder =
                            (SisterViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewSister
                                    .findViewHolderForLayoutPosition(position);
                    holder.sisterAge.setText(age[newValue]);

                    value = "" + newValue;
                    ageArraySister.put(position, value);
                } else if (SisterViewAdapter.selectedPopUp == 6) {
                    //BrotherViewAdapter.brotherOcupationSpouse.setText(occupationName[newVal]);

                    SisterViewAdapter.MyViewHolder holder =
                            (SisterViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewSister
                                    .findViewHolderForLayoutPosition(position);
                    holder.sisterOcupationSpouse.setText(occupationName[newValue]);

                    value = occupationConstantValue[newValue];
                    occupationArraySisterSpouse.put(position, value);
                } else if (SisterViewAdapter.selectedPopUp == 7) {
                    //BrotherViewAdapter.brotherProfessionalGroupSpouse.setText(professonalGroupName[newVal]);

                    SisterViewAdapter.MyViewHolder holder =
                            (SisterViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewSister
                                    .findViewHolderForLayoutPosition(position);
                    holder.sisterProfessionalGroupSpouse.setText(professonalGroupName[newValue]);
                    value = professonalGroupConstantValue[newValue];
                    professionalGroupArraySisterSpouse.put(position, value);
                } else if (OtherViewAdapter.selectedPopUp == 1) {
                    //OtherViewAdapter.otherOccupation.setText(occupationName[newVal]);
                    OtherViewAdapter.MyViewHolder holder =
                            (OtherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewOther
                                    .findViewHolderForLayoutPosition(position);
                    holder.otherOccupation.setText(occupationName[newValue]);

                    value = occupationConstantValue[newValue];
                    occupationArrayOther.put(position, value);
                }else if (strUpdateData!= null && strUpdateData.equalsIgnoreCase("profession")) {
                    Intent intent = new Intent();
                    intent.putExtra("profession_data", occupationName[newValue]);
                    intent.putExtra("profession_value", occupationConstantValue[newValue]);
                    setResult(RESULT_OK, intent);
                }else if (strUpdateData!= null && strUpdateData.equalsIgnoreCase("professional_group")) {
                    Intent intent = new Intent();
                    intent.putExtra("professional_group_data", professonalGroupName[newValue]);
                    intent.putExtra("professional_group_value", professonalGroupConstantValue[newValue]);
                    setResult(RESULT_OK, intent);
                } else if (OtherViewAdapter.selectedPopUp == 2) {
                    //OtherViewAdapter.otherProfessionalGroup.setText(professonalGroupName[newVal]);

                    OtherViewAdapter.MyViewHolder holder =
                            (OtherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewOther
                                    .findViewHolderForLayoutPosition(position);
                    holder.otherProfessionalGroup.setText(professonalGroupName[newValue]);

                    value = professonalGroupConstantValue[newValue];
                    professionalGroupArrayOther.put(position, value);
                } else if (OtherViewAdapter.selectedPopUp == 3) {
                    //OtherViewAdapter.otherMaritalStatus.setText(maritalStatusName[newVal]);
                    OtherViewAdapter.MyViewHolder holder =
                            (OtherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewOther
                                    .findViewHolderForLayoutPosition(position);
                    holder.otherMaritalStatus.setText(maritalStatusNameMale[newValue]);

                    value = maritalStatusConstantValueMale[newValue];
                    maritalStatusArrayOther.put(position, value);
                } else if (OtherViewAdapter.selectedPopUp == 4) {
                    //   OtherViewAdapter.otherRelationalStatus.setText(relationName[newVal]);

                    OtherViewAdapter.MyViewHolder holder =
                            (OtherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewOther
                                    .findViewHolderForLayoutPosition(position);
                    holder.otherRelationalStatus.setText(relationName[newValue]);

                    value = relationConstantValue[newValue];
                    relationStatusArrayOther.put(position, value);
                }
                /*else if (OtherViewAdapter.selectedPopUp == 5) {
                    //OtherViewAdapter.otherAge.setText(age[newVal]);

                    OtherViewAdapter.MyViewHolder holder =
                            (OtherViewAdapter.MyViewHolder) RegistrationFamilyInfoSecondPage.recyclerViewOther
                                    .findViewHolderForLayoutPosition(position);
                    holder.otherAge.setText(age[newValue]);

                    value = "" + newValue;
                    ageArrayOther.put(position, value);
                }*//* else if (RegistrationFamilyInfoSecondPage.selectedPopUp == 1) {
                *//*RegistrationFamilyInfoSecondPage.brotherNumber.setText(data[newVal]);
                RegistrationFamilyInfoSecondPage.numberOfBrother = constantOfBrotherSister[newVal];
                *//*
                    brotherPickerSelectedValue = newValue;
                } else if (RegistrationFamilyInfoSecondPage.selectedPopUp == 2) {
                    sisterPickerSelectedValue = newValue;
               *//* RegistrationFamilyInfoSecondPage.sisterNumber.setText(data[newVal]);
                RegistrationFamilyInfoSecondPage.numberOfSister = constantOfBrotherSister[newVal];*//*
                }*/

                BrotherViewAdapter.selectedPopUp = 0;
                SisterViewAdapter.selectedPopUp = 0;
                OtherViewAdapter.selectedPopUp = 0;
                RegistrationFamilyInfoSecondPage.selectedPopUp = 0;
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrotherViewAdapter.selectedPopUp = 0;
                SisterViewAdapter.selectedPopUp = 0;
                OtherViewAdapter.selectedPopUp = 0;
                RegistrationFamilyInfoSecondPage.selectedPopUp = 0;
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        BrotherViewAdapter.selectedPopUp = 0;
        SisterViewAdapter.selectedPopUp = 0;
        OtherViewAdapter.selectedPopUp = 0;
        RegistrationFamilyInfoSecondPage.selectedPopUp = 0;
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    //Log.w("setNumberPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    // Log.w("setNumberPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    // Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }

    private void setDividerColor(NumberPicker picker, int color) {

        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    private class ListListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            Log.i("brotherval: ", "listener");
            newValue = newVal;
        }
    }

    public String getAgeInBangla(String string) {
        Character bangla_number[] = {'০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'};
        Character eng_number[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String values = "";
        char[] character = string.toCharArray();
        for (int i = 0; i < character.length; i++) {
            Character c = ' ';
            for (int j = 0; j < eng_number.length; j++) {
                if (character[i] == eng_number[j]) {
                    c = bangla_number[j];
                    break;
                } else {
                    c = character[i];
                }
            }
            values = values + c;
        }
        return values;
    }
}
