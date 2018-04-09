package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PopUpPersonalInfo extends AppCompatActivity {
    Button accept,reject;
    NumberPicker picker;
    String[] data = new String[]{};
    TextView mTextView;
    String strDataForUpdate = "";
    private ArrayList<String> heightArray = new ArrayList<String>();
    private String[] heightName = new String[heightArray.size()];
    private String info_data = "";

    private JSONObject skinColorObject, marriageObject, bloodObject, smokeObject;

    //skin
    private ArrayList<String> skinColorArray = new ArrayList<String>();
    private String[] skinColorName = new String[skinColorArray.size()];
    private ArrayList<String> skinColorConstant = new ArrayList<String>();
    private String[] skinColorConstantValue = new String[skinColorArray.size()];

    private ArrayList<String> weightArray = new ArrayList<String>();
    private String[] weightName = new String[weightArray.size()];
    private ArrayList<String> weightConstant = new ArrayList<String>();
    private String[] weightConstantValue = new String[weightArray.size()];

    private ArrayList<String> marriageArray = new ArrayList<String>();
    private String[] maritalStatusName = new String[marriageArray.size()];
    private ArrayList<String> maritalStatusConstant = new ArrayList<String>();
    private String[] maritalStatusConstantValue = new String[marriageArray.size()];

    private ArrayList<String> bloodGroupArray = new ArrayList<String>();
    private String[] bloodGroupName = new String[bloodGroupArray.size()];
    private ArrayList<String> bloodGroupConstant = new ArrayList<String>();
    private String[] bloodGroupConstantValue = new String[bloodGroupArray.size()];

    private ArrayList<String> smokeArray = new ArrayList<String>();
    private String[] smokeName = new String[smokeArray.size()];
    private ArrayList<String> smokeConstant = new ArrayList<String>();
    private String[] smokeConstantValue = new String[smokeArray.size()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_personal_info);
        picker = (NumberPicker)findViewById(R.id.picker);
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);
        mTextView = (TextView) findViewById(R.id.title);


        info_data = getIntent().getStringExtra("constants");
        strDataForUpdate = getIntent().getStringExtra("data");
        if(info_data != null){
            setData();
        }
        if(strDataForUpdate != null){
            if(strDataForUpdate.equalsIgnoreCase("height")){
                for (int i = 4; i < 8; i++) {           //height from 4ft to 7ft 11inch
                    for(int j = 0; j < 12; j++)
                        heightArray.add(Utils.convertEnglishDigittoBangla(i) + " ফিট " + Utils.convertEnglishDigittoBangla(j) +" ইঞ্চি");
                }

                data = heightArray.toArray(heightName);
                mTextView.setText("উচ্চতা");
            }else if(strDataForUpdate.equalsIgnoreCase("body")){
                for (int i = 30; i <= 200; i++) {         //weight from 30kg to 200kg
                    weightConstant.add(i+"");
                    weightArray.add(Utils.convertEnglishDigittoBangla(i)+" কেজি ");
                }

                data = weightArray.toArray(weightName);
                mTextView.setText(getResources().getString(R.string.body));
            }
        }


        if(RegistrationPersonalInformation.selectedPopUp == 1 ){
            RegistrationPersonalInformation.marriage = 1;
            data = RegistrationPersonalInformation.maritalStatusName;
            mTextView.setText("বৈবাহিক অবস্থা");
        }else if(RegistrationPersonalInformation.selectedPopUp == 2){
            RegistrationPersonalInformation.education = 1;
            data = RegistrationPersonalInformation.educationName;
            mTextView.setText("ডিগ্রীর নাম");
        }else if(RegistrationPersonalInformation.selectedPopUp == 3){
            RegistrationPersonalInformation.profession = 1;
            data = RegistrationPersonalInformation.occupationName;
            mTextView.setText("পেশা");
        }else if(RegistrationPersonalInformation.selectedPopUp == 4){
            RegistrationPersonalInformation.religion = 1;
            data = RegistrationPersonalInformation.religionName;
            mTextView.setText("নামায");
        }else if(RegistrationPersonalInformation.selectedPopUp == 5){
            RegistrationPersonalInformation.roja =1;
            data = RegistrationPersonalInformation.rojaName;
            mTextView.setText("রোজা");
        }else if(RegistrationPersonalInformation.selectedPopUp == 6){
            RegistrationPersonalInformation.disable =1;
            data = RegistrationPersonalInformation.disableName;
            mTextView.setText("প্রতিবন্ধকতা");
        }else if(RegistrationPersonalInformation.selectedPopUp == 7){
            RegistrationPersonalInformation.smoke = 1;
            data = RegistrationPersonalInformation.smokeName;
            mTextView.setText("ধূমপান");
        }else if(RegistrationPersonalInformation.selectedPopUp == 8){
            RegistrationPersonalInformation.professonalGroup = 1;
            data = RegistrationPersonalInformation.professonalGroupName;
            mTextView.setText("প্রফেশনাল গ্রুপ");
        }else if(RegistrationPersonalInformation.selectedPopUp == 9){
            RegistrationPersonalInformation.house = 1;
            data = RegistrationPersonalInformation.houseName;
            mTextView.setText("আবাসস্থল");
        }else if(RegistrationPersonalInformation.selectedPopUp == 10){
            RegistrationPersonalInformation.hijab = 1;
            data = RegistrationPersonalInformation.hijabName;
            mTextView.setText("হিজাব");
        }
        ////////////////////////////////////////////////////////////
        else if(RegistrationPersonalInformation.selectedPopUp == 11){
            RegistrationPersonalInformation.height = 1;
            data = RegistrationPersonalInformation.heightName;
            mTextView.setText("উচ্চতা");
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 12){
            RegistrationPersonalInformation.weight = 1;
            data = RegistrationPersonalInformation.weightName;
            mTextView.setText("ওজন");
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 13){
            RegistrationPersonalInformation.blood_group = 1;
            data = RegistrationPersonalInformation.bloodGroupName;
            mTextView.setText(" ব্লাড গ্রুপ");
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 14){
            RegistrationPersonalInformation.skin_color = 1;
            data = RegistrationPersonalInformation.skinColorName;
            mTextView.setText("গায়ের রং");
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 15){
            RegistrationPersonalInformation.child_number = 1;
            data = RegistrationPersonalInformation.childName;
            mTextView.setText("সন্তান সংখ্যা");
        }
        else if(RegistrationPersonalInformation.selectedPopUp == 16){
            RegistrationPersonalInformation.stay_status = 1;
            data = RegistrationPersonalInformation.stayStausArrayName;
            mTextView.setText("সন্তান সাথে থাকে?");
        }
        /////////////////////////////////////////////////////////////

        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);
        if(RegistrationPersonalInformation.selectedPopUp==12 || strDataForUpdate.equalsIgnoreCase("body"))
        {
            picker.setValue(30);                             // default 60 kg
        }
        if(RegistrationPersonalInformation.selectedPopUp==11 || strDataForUpdate.equalsIgnoreCase("height"))
        {
            picker.setValue(17);                             // default 5 feet 5 inch
        }
        picker.setOnValueChangedListener(new PopUpPersonalInfo.ListListener());
        setDividerColor(picker, Color.parseColor("#626262"));
        setNumberPickerTextColor(picker, Color.parseColor("#626262"));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RegistrationPersonalInformation.selectedPopUp == 11 || strDataForUpdate.equalsIgnoreCase("height")){
                    if(strDataForUpdate.equalsIgnoreCase("height"))
                    {
                        Intent intent = new Intent();
                        intent.putExtra("height", picker.getValue() + 1);
                        setResult(RESULT_OK, intent);
                    }
                    RegistrationPersonalInformation.height = picker.getValue() + 1;
                }else if(RegistrationPersonalInformation.selectedPopUp == 12){
                    RegistrationPersonalInformation.weight = picker.getValue() + 1;
                }else if(strDataForUpdate.equalsIgnoreCase("skin")) {
                    Intent intent = new Intent();
                    intent.putExtra("skin_color_value", picker.getValue() + 1);
                    intent.putExtra("skin_color_data", skinColorName[picker.getValue()]);
                    setResult(RESULT_OK, intent);
                }else if(strDataForUpdate.equalsIgnoreCase("body")){
                    Intent intent = new Intent();
                    intent.putExtra("body_value", picker.getValue() + 1);
                    setResult(RESULT_OK, intent);
                }else if(strDataForUpdate.equalsIgnoreCase("marital_status")) {
                    Intent intent = new Intent();
                    intent.putExtra("marital_status_value", picker.getValue() + 1);
                    intent.putExtra("marital_status_data", maritalStatusName[picker.getValue()]);
                    setResult(RESULT_OK, intent);
                }else if(strDataForUpdate.equalsIgnoreCase("blood_group")) {
                    Intent intent = new Intent();
                    intent.putExtra("blood_group_value", picker.getValue() + 1);
                    intent.putExtra("blood_group_data", bloodGroupName[picker.getValue()]);
                    setResult(RESULT_OK, intent);
                }else if(strDataForUpdate.equalsIgnoreCase("smoke")) {
                    Intent intent = new Intent();
                    intent.putExtra("smoke_value", picker.getValue() + 1);
                    intent.putExtra("smoke_data", smokeName[picker.getValue()]);
                    setResult(RESULT_OK, intent);
                }

                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValue();
            }
        });
    }

    private void setData() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(info_data);

            if(strDataForUpdate.equalsIgnoreCase("skin")) {
                skinColorObject = jsonObject.getJSONObject("skin_color_options");
                for (int i = 0; i < skinColorObject.length(); i++) {
                    skinColorConstant.add(skinColorObject.names().getString(i));
                    skinColorArray.add(
                            (String) skinColorObject.get(skinColorObject.names().getString(i)));
                }
                skinColorName = skinColorArray.toArray(skinColorName);
                data = skinColorName;
                mTextView.setText("গায়ের রং");
            }else if(strDataForUpdate.equalsIgnoreCase("smoke")) {
                smokeObject = jsonObject.getJSONObject("smoking_options");
                for (int i = 0; i < smokeObject.length(); i++) {
                    smokeConstant.add(smokeObject.names().getString(i));
                    smokeArray.add((String) smokeObject.get(smokeObject.names().getString(i)));
                }

                smokeName = smokeArray.toArray(smokeName);
                data = smokeName;
                mTextView.setText(getResources().getString(R.string.smoking_text));
            }else if(strDataForUpdate.equalsIgnoreCase("blood_group")) {
                bloodObject = jsonObject.getJSONObject("blood_group_options");
                for (int i = 0; i < bloodObject.length(); i++) {
                    bloodGroupConstant.add(bloodObject.names().getString(i));
                    bloodGroupArray.add((String) bloodObject.get(bloodObject.names().getString(i)));
                }

                bloodGroupName = bloodGroupArray.toArray(bloodGroupName);
                data = bloodGroupName;
                mTextView.setText(getResources().getString(R.string.blood_group_text));
            }else if(strDataForUpdate.equalsIgnoreCase("marital_status")) {
                SharePref sharePref = new SharePref(PopUpPersonalInfo.this);
                if (sharePref.get_data("gender").equalsIgnoreCase("female")) {
                    marriageObject = jsonObject.getJSONObject("marital_status_constant_for_female");
                } else if (sharePref.get_data("gender").equalsIgnoreCase("male")) {
                    marriageObject = jsonObject.getJSONObject("marital_status_constant_for_male");
                }
                for (int i = 0; i < marriageObject.length(); i++) {
                    maritalStatusConstant.add(marriageObject.names().getString(i));
                    marriageArray.add((String) marriageObject.get(marriageObject.names().getString(i)));
                }
                maritalStatusName = marriageArray.toArray(maritalStatusName);
                data = maritalStatusName;
                mTextView.setText(getResources().getString(R.string.marital_status));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultValue() {
        if(RegistrationPersonalInformation.selectedPopUp == 1 ){
            RegistrationPersonalInformation.marriage = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 2){
            RegistrationPersonalInformation.education = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 3){
            RegistrationPersonalInformation.profession = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 4){
            RegistrationPersonalInformation.religion = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 5){
            RegistrationPersonalInformation.roja = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 6){
            RegistrationPersonalInformation.disable = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 7){
            RegistrationPersonalInformation.smoke = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 8){
            RegistrationPersonalInformation.professonalGroup = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 9){
            RegistrationPersonalInformation.house = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 10){
            RegistrationPersonalInformation.hijab = -1;
        }
        /////////////////////////////////////////////////////////////
        else if(RegistrationPersonalInformation.selectedPopUp == 11){
            RegistrationPersonalInformation.height = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 12){
            RegistrationPersonalInformation.weight = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 13){
            RegistrationPersonalInformation.blood_group = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 14){
            RegistrationPersonalInformation.skin_color = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 15){
            RegistrationPersonalInformation.child_number = -1;
        }else if(RegistrationPersonalInformation.selectedPopUp == 16){
            RegistrationPersonalInformation.stay_status = -1;
        }
        finish();
    }


    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    //Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalAccessException e){
                    // Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
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
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    private class ListListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if(RegistrationPersonalInformation.selectedPopUp == 1 ){
                RegistrationPersonalInformation.marriage = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 2){
                RegistrationPersonalInformation.education = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 3){
                RegistrationPersonalInformation.profession = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 4){
                RegistrationPersonalInformation.religion = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 5){
                RegistrationPersonalInformation.roja = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 6){
                RegistrationPersonalInformation.disable = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 7){
                RegistrationPersonalInformation.smoke = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 8){
                RegistrationPersonalInformation.professonalGroup = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 9){
                RegistrationPersonalInformation.house = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 10){
                RegistrationPersonalInformation.hijab = newVal + 1;
            }
            /////////////////////////////////////////////
            else if(RegistrationPersonalInformation.selectedPopUp == 11){
                RegistrationPersonalInformation.height = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 12){
                RegistrationPersonalInformation.weight = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 13){
                RegistrationPersonalInformation.blood_group = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 14){
                RegistrationPersonalInformation.skin_color = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 15){
                RegistrationPersonalInformation.child_number = newVal + 1;
            }else if(RegistrationPersonalInformation.selectedPopUp == 16){
                RegistrationPersonalInformation.stay_status = newVal + 1;
            }
            ////////////////////////////////////////////

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setDefaultValue();
    }

}
