package com.nascenia.biyeta.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
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
    EditText disableEditText;
    private JSONObject skinColorObject, marriageObject, bloodObject, smokeObject, disableObject,  professionalGroupObject,
            occupationObject, rojaObject, religionObject, houseObject, educationObject;

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

    private ArrayList<String> disableArray = new ArrayList<String>();
    private String[] disableName = new String[disableArray.size()];
    private ArrayList<String> disableConstant = new ArrayList<String>();
    private String[] disableConstantValue = new String[disableArray.size()];

    private ArrayList<String> professonalGroupArray = new ArrayList<String>();
    private String[] professonalGroupName = new String[professonalGroupArray.size()];
    private ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    private String[] professonalGroupConstantValue = new String[professonalGroupArray.size()];

    private ArrayList<String> occupationArray = new ArrayList<String>();
    private String[] occupationName = new String[occupationArray.size()];
    private ArrayList<String> occupationConstant = new ArrayList<String>();
    private String[] occupationConstantValue = new String[occupationArray.size()];

    private ArrayList<String> rojaArray = new ArrayList<String>();
    private String[] rojaName = new String[rojaArray.size()];
    private ArrayList<String> rojaConstant = new ArrayList<String>();
    private String[] rojaConstantValue = new String[rojaArray.size()];

    private ArrayList<String> religionArray = new ArrayList<String>();
    private String[] religionName = new String[religionArray.size()];
    private ArrayList<String> religionConstant = new ArrayList<String>();
    private String[] religionConstantValue = new String[religionArray.size()];

    private ArrayList<String> houseArray = new ArrayList<String>();
    private String[] houseName = new String[houseArray.size()];
    private ArrayList<String> houseConstant = new ArrayList<String>();
    private String[] houseConstantValue = new String[houseArray.size()];

    private ArrayList<String> educationArray = new ArrayList<String>();
    private String[] educationName = new String[educationArray.size()];
    private ArrayList<String> educationConstant = new ArrayList<String>();
    private String[] educationConstantValue = new String[educationArray.size()];

    int width = 0;
    int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_personal_info);
        picker = (NumberPicker)findViewById(R.id.picker);
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);
        mTextView = (TextView) findViewById(R.id.title);
        disableEditText = (EditText) findViewById(R.id.disable_desc);


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

        width = dm.widthPixels;
        height = dm.heightPixels;

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
            }else if(strDataForUpdate.equalsIgnoreCase("disable")) {
                Intent intent = new Intent();
                Log.d("disable value", picker.getValue() + "");
                intent.putExtra("disable_value", picker.getValue() + 1);
                intent.putExtra("disable_data", disableName[picker.getValue()]);
                intent.putExtra("disable_desc_data", picker.getValue()>0?disableEditText.getText().toString():"");
                setResult(RESULT_OK, intent);
            }else if(strDataForUpdate.equalsIgnoreCase("professional_group") || strDataForUpdate.equalsIgnoreCase("father_professional_group")) {
                Intent intent = new Intent();
                intent.putExtra("professional_group_value", picker.getValue() + 1);
                intent.putExtra("professional_group_data", professonalGroupName[picker.getValue()]);
                setResult(RESULT_OK, intent);
            }else if(strDataForUpdate.equalsIgnoreCase("profession") || strDataForUpdate.equalsIgnoreCase("father_profession")) {
                Intent intent = new Intent();
                intent.putExtra("profession_value", picker.getValue() + 1);
                intent.putExtra("profession_data", occupationName[picker.getValue()]);
                setResult(RESULT_OK, intent);
            }else if(strDataForUpdate.equalsIgnoreCase("fasting")) {
                Intent intent = new Intent();
                intent.putExtra("fasting_value", picker.getValue() + 1);
                intent.putExtra("fasting_data", rojaName[picker.getValue()]);
                setResult(RESULT_OK, intent);
            }else if(strDataForUpdate.equalsIgnoreCase("prayer")) {
                Intent intent = new Intent();
                intent.putExtra("prayer_value", picker.getValue() + 1);
                intent.putExtra("prayer_data", religionName[picker.getValue()]);
                setResult(RESULT_OK, intent);
            }else if(strDataForUpdate.equalsIgnoreCase("own_house")) {
                Intent intent = new Intent();
                intent.putExtra("own_house_value", picker.getValue() + 1);
                intent.putExtra("own_house_data", houseName[picker.getValue()]);
                setResult(RESULT_OK, intent);
            }else if(strDataForUpdate.equalsIgnoreCase("education")) {
                Intent intent = new Intent();
                intent.putExtra("education_value", picker.getValue() + 1);
                intent.putExtra("education_data", educationName[picker.getValue()]);
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

//    private void showDisableDescUI(String message) {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.disable_desc, null);
//        dialogBuilder.setView(dialogView);
//
//        EditText editText = (EditText) dialogView.findViewById(R.id.disable_desc);
//        editText.setText(message);
//        dialogBuilder.setCancelable(true);
//        dialogBuilder.setPositiveButton(R.string.see_details,
//            new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//        dialogBuilder.setNegativeButton(R.string.not_now,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        final AlertDialog alert = dialogBuilder.create();
//        alert.setOnShowListener( new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
//                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
//            }
//        });
//
//        alert.show();
//    }

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
            }else if(strDataForUpdate.equalsIgnoreCase("disable")) {
                disableObject = jsonObject.getJSONObject("physical_disability_options");
                for (int i = 0; i < disableObject.length(); i++) {
                    disableConstant.add(disableObject.names().getString(i));
                    disableArray.add((String) disableObject.get(disableObject.names().getString(i)));
                }

                disableName = disableArray.toArray(disableName);
                data = disableName;
                mTextView.setText(getResources().getString(R.string.disabilities_text));
            }else if(strDataForUpdate.equalsIgnoreCase("professional_group") || strDataForUpdate.equalsIgnoreCase("father_professional_group")) {
                professionalGroupObject = jsonObject.getJSONObject("professional_group_constant");
                for (int i = 0; i < professionalGroupObject.length(); i++) {
                    professonalGroupConstant.add(professionalGroupObject.names().getString(i));
                    professonalGroupArray.add((String) professionalGroupObject.get(professionalGroupObject.names().getString(i)));
                }

                professonalGroupName = professonalGroupArray.toArray(professonalGroupName);
                data = professonalGroupName;
                mTextView.setText(Utils.setBanglaProfileTitle(getResources().getString(R.string.professional_group_text)));
            }else if(strDataForUpdate.equalsIgnoreCase("profession") || strDataForUpdate.equalsIgnoreCase("father_profession")) {
                occupationObject = jsonObject.getJSONObject("occupation_constant");
                for (int i = 0; i < occupationObject.length(); i++) {
                    occupationConstant.add(occupationObject.names().getString(i));
                    occupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
                }

                occupationName = occupationArray.toArray(occupationName);
                data = occupationName;
                mTextView.setText(Utils.setBanglaProfileTitle(getResources().getString(R.string.profession_text)));
            }else if(strDataForUpdate.equalsIgnoreCase("fasting")) {
                rojaObject = jsonObject.getJSONObject("fasting");
                for (int i = 0; i < rojaObject.length(); i++) {
                    rojaConstant.add(rojaObject.names().getString(i));
                    rojaArray.add((String) rojaObject.get(rojaObject.names().getString(i)));
                }

                rojaName = rojaArray.toArray(rojaName);
                data = rojaName;
                mTextView.setText(getResources().getString(R.string.fast_text));
            }else if(strDataForUpdate.equalsIgnoreCase("prayer")) {
                religionObject = jsonObject.getJSONObject("prayer_options");
                for (int i = 0; i < religionObject.length(); i++) {
                    religionConstant.add(religionObject.names().getString(i));
                    religionArray.add((String) religionObject.get(religionObject.names().getString(i)));
                }

                religionName = religionArray.toArray(religionName);
                data = religionName;
                mTextView.setText(getResources().getString(R.string.religion_text));
            }else if(strDataForUpdate.equalsIgnoreCase("own_house")) {
                houseObject = jsonObject.getJSONObject("house_options");
                for (int i = 0; i < houseObject.length(); i++) {
                    houseConstant.add(houseObject.names().getString(i));
                    houseArray.add((String) houseObject.get(houseObject.names().getString(i)));
                }

                houseName = houseArray.toArray(houseName);
                data = houseName;
                mTextView.setText(Utils.setBanglaProfileTitle(getResources().getString(R.string.own_house_text)));
            }else if(strDataForUpdate.equalsIgnoreCase("education")) {
                educationObject = jsonObject.getJSONObject("education_constant");
                for (int i = 0; i < educationObject.length(); i++) {
                    educationConstant.add(educationObject.names().getString(i));
                    educationArray.add((String) educationObject.get(educationObject.names().getString(i)));
                }
                educationName = educationArray.toArray(educationName);
                data = educationName;
                mTextView.setText(Utils.setBanglaProfileTitle(getResources().getString(R.string.education_male)));
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
            }else if(RegistrationPersonalInformation.selectedPopUp == 6 || strDataForUpdate.equalsIgnoreCase("disable")){
                RegistrationPersonalInformation.disable = newVal + 1;
                if(newVal == 0 && strDataForUpdate.equalsIgnoreCase("disable"))
                {
                    findViewById(R.id.ln_disable_desc).setVisibility(View.GONE);
                    getWindow().setLayout((int)(width*.8),(int)(height*.6));
                }else if(strDataForUpdate.equalsIgnoreCase("disable")){
                    findViewById(R.id.ln_disable_desc).setVisibility(View.VISIBLE);
                    getWindow().setLayout((int)(width*.8),(int)(height*.75));
                }

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
