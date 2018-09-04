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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PopUpChoiceSelectionThirdPage extends AppCompatActivity {

    Button accept,reject;
    TextView tv_title;
    NumberPicker picker;
    String[] data = new String[]{};
    private String info_data, strDataForUpdate;
    private static ArrayList<String> jobArray = new ArrayList<String>();
    private static ArrayList<String> jobConstant = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_choice_selection_third_page);
        picker = (NumberPicker)findViewById(R.id.picker);
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);
        tv_title = (TextView) findViewById(R.id.title);

        info_data = getIntent().getStringExtra("constants");
        strDataForUpdate = getIntent().getStringExtra("data");
        if((strDataForUpdate != null && info_data != null))   // from profile edit
        {
            setData();
        }else {

            if (RegistrationChoiceSelectionThirdPage.selectedPopUp == 1) {
                RegistrationChoiceSelectionThirdPage.job = 1;
                data = RegistrationChoiceSelectionThirdPage.jobArray.toArray(data);
                tv_title.setText("বিয়ের পরে কি চাকরি করবেন?");
            } else if (RegistrationChoiceSelectionThirdPage.selectedPopUp == 2) {
                RegistrationChoiceSelectionThirdPage.marriage = 1;
                data = RegistrationChoiceSelectionThirdPage.marriageArray.toArray(data);
                tv_title.setText("বৈবাহিক অবস্থা");
            } else if (RegistrationChoiceSelectionThirdPage.selectedPopUp == 3) {
                RegistrationChoiceSelectionThirdPage.religion = 1;
                data = RegistrationChoiceSelectionThirdPage.religionArray.toArray(data);
                tv_title.setText("ধর্ম");
            } else if (RegistrationChoiceSelectionThirdPage.selectedPopUp == 4) {
                RegistrationChoiceSelectionThirdPage.house = 1;
                data = RegistrationChoiceSelectionThirdPage.houseArray.toArray(data);
                tv_title.setText("বাড়ি");
            }
        }

        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);
        picker.setOnValueChangedListener(new PopUpChoiceSelectionThirdPage.ListListener());
        setDividerColor(picker, Color.parseColor("#626262"));
        setNumberPickerTextColor(picker, Color.parseColor("#626262"));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(strDataForUpdate!=null) {
                    Intent intent = new Intent();
                    intent.putExtra("job_after_marriage_value", jobConstant.get(picker.getValue() % 3));
                    intent.putExtra("job_after_marriage_data", jobArray.get(picker.getValue() % 3));
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 1 ){
                    RegistrationChoiceSelectionThirdPage.job = -1;
                }else if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 2){
                    RegistrationChoiceSelectionThirdPage.marriage = -1;
                }else if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 3){
                    RegistrationChoiceSelectionThirdPage.religion = -1;
                }else if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 4){
                    RegistrationChoiceSelectionThirdPage.house = -1;
                }

                finish();
            }
        });
    }

    private void setData() {
        try {
            JSONObject jsonObject = new JSONObject(info_data);
            JSONObject jobAfterMarriageObject = null;
            jobAfterMarriageObject = jsonObject.getJSONObject("job_after_marriage");

            for (int i = 0; i < jobAfterMarriageObject.length(); i++) {
                jobConstant.add(jobAfterMarriageObject.names().getString(i));
//                jobArray.add((String) jobAfterMarriageObject.get(jobAfterMarriageObject.names().getString(i)));
            }
            jobArray = new ArrayList<>();
            jobArray.add("করতে পারি");
            jobArray.add("অবশ্যই করবো");
            jobArray.add("করবো না");
            data = jobArray.toArray(data);
            tv_title.setText("বিয়ের পরে কি চাকরি করতে পারবে?");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


    private class ListListener implements NumberPicker.OnValueChangeListener{
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 1){
                RegistrationChoiceSelectionThirdPage.job = newVal + 1;
            }else if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 2){
                RegistrationChoiceSelectionThirdPage.marriage = newVal + 1;
            }else if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 3){
                RegistrationChoiceSelectionThirdPage.religion = newVal + 1;
            }else if(RegistrationChoiceSelectionThirdPage.selectedPopUp == 4){
                RegistrationChoiceSelectionThirdPage.house = newVal + 1;
            }
        }
    }
}
