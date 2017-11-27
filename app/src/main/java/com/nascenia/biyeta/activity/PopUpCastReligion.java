package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.nascenia.biyeta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public class PopUpCastReligion extends AppCompatActivity {

    ArrayList<String> data = new ArrayList<String>();
    String[] religionName = new String[data.size()];
    ArrayList<String> dataConstant = new ArrayList<String>();

    ArrayList<String> muslimCast = new ArrayList<String>();
    String[] muslimCastName = new String[muslimCast.size()];
    ArrayList<String> muslimCastConstant = new ArrayList<String>();

    ArrayList<String> hinduCast = new ArrayList<String>();
    String[] hinduCastName = new String[hinduCast.size()];
    ArrayList<String> hinduCastConstant = new ArrayList<String>();

    ArrayList<String> christianCast = new ArrayList<String>();
    String[] christianCastName = new String[christianCast.size()];
    ArrayList<String> christianCastConstant = new ArrayList<String>();

    NumberPicker religionPicker;
    NumberPicker castPicker;
    EditText cast;
    EditText religion;
    Button accept,reject;
    int religionValue = 0;
    static int castValue = 0;
    String constant;
    JSONObject religionObject, muslimCastObject, hinduCastObject, christianCastObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        constant = extras.getString("constants");

        try {
            JSONObject jsonObject = new JSONObject(constant);

            religionObject = jsonObject.getJSONObject("religion_constant");
            muslimCastObject = jsonObject.getJSONObject("muslim_cast");
            hinduCastObject = jsonObject.getJSONObject("hindu_cast");
            christianCastObject = jsonObject.getJSONObject("christian_cast");
            for(int i=0;i<religionObject.length();i++)
            {
                dataConstant.add(religionObject.names().getString(i));
                data.add((String) religionObject.get(religionObject.names().getString(i)));
            }

            for(int i=0;i<muslimCastObject.length();i++)
            {
                muslimCastConstant.add(muslimCastObject.names().getString(i));
                muslimCast.add((String) muslimCastObject.get(muslimCastObject.names().getString(i)));
            }

            for(int i=0;i<hinduCastObject.length();i++)
            {
                hinduCastConstant.add(hinduCastObject.names().getString(i));
                hinduCast.add((String) hinduCastObject.get(hinduCastObject.names().getString(i)));
            }

            for(int i=0;i<christianCastObject.length();i++)
            {
                christianCastConstant.add(christianCastObject.names().getString(i));
                christianCast.add((String) christianCastObject.get(christianCastObject.names().getString(i)));
            }

            religionName = data.toArray(religionName);

            muslimCastName = muslimCast.toArray(muslimCastName);

            hinduCastName = hinduCast.toArray(hinduCastName);

            christianCastName = christianCast.toArray(christianCastName);

        }catch (JSONException e){
            e.printStackTrace();
        }





        setContentView(R.layout.activity_pop);

        cast = (EditText)findViewById(R.id.cast);
        religion = (EditText)findViewById(R.id.religion);
        religionPicker = (NumberPicker)findViewById(R.id.religion_picker);
        religionPicker.setMinValue(0);
        religionPicker.setMaxValue(religionName.length-1);
        religionPicker.setDisplayedValues(religionName);
        religionPicker.setOnValueChangedListener(new RligionListListener());
        setDividerColor(religionPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(religionPicker, Color.parseColor("#626262"));

        castPicker = (NumberPicker)findViewById(R.id.cast_picker);
        castPicker.setMinValue(0);
        castPicker.setMaxValue(muslimCastName.length-1);
        castPicker.setValue(0);
        castPicker.setDisplayedValues(muslimCastName);
        castPicker.setOnValueChangedListener(new CastListListener());
        setDividerColor(castPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(castPicker, Color.parseColor("#626262"));
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(RegistrationOwnInfo.castReligionOwn ==1){
                    if(religionValue == 0 && castValue == 2)
                    {
                        RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]+", " + cast.getText());
                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                        RegistrationOwnInfo.otherCast = cast.getText().toString();
                    }
                    else if(religionValue != 2 && religionValue != 4){

                        if(religionValue ==0){
                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]+", "+muslimCastName[castValue]);
                            RegistrationOwnInfo.castValue = muslimCastConstant.get(castValue);
                        }
                        else if(religionValue == 1){
                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]+", "+hinduCastName[castValue]);
                            RegistrationOwnInfo.castValue = hinduCastConstant.get(castValue);
                        }
                        else if(religionValue == 3){
                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]+", "+christianCastName[castValue]);
                            RegistrationOwnInfo.castValue = christianCastConstant.get(castValue);
                        }

                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);

                    }
                    else if(religionValue == 2){
                        RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]);
                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                    }
                    else if(religionValue == 4){
                        RegistrationOwnInfo.castReligionText.setText(religion.getText());
                        RegistrationOwnInfo.otherReligion = religion.getText().toString();
                    }
                }

                if(RegistrationChoiceSelectionThirdPage.castReligionChoice == 1){
                    if(religionValue == 0 && castValue == 2)
                    {
                        RegistrationChoiceSelectionThirdPage.castReligionText.setText(religionName[religionValue]+", " + cast.getText());
                        RegistrationChoiceSelectionThirdPage.religionValue = dataConstant.get(religionValue);
                        RegistrationChoiceSelectionThirdPage.otherCast = cast.getText().toString();
                    }
                    else if(religionValue != 2 && religionValue != 4){

                        if(religionValue ==0){
                            RegistrationChoiceSelectionThirdPage.castReligionText.setText(religionName[religionValue]+", "+muslimCastName[castValue]);
                            RegistrationChoiceSelectionThirdPage.castValue = muslimCastConstant.get(castValue);
                        }
                        else if(religionValue == 1){
                            RegistrationChoiceSelectionThirdPage.castReligionText.setText(religionName[religionValue]+", "+hinduCastName[castValue]);
                            RegistrationChoiceSelectionThirdPage.castValue = hinduCastConstant.get(castValue);
                        }
                        else if(religionValue == 3){
                            RegistrationChoiceSelectionThirdPage.castReligionText.setText(religionName[religionValue]+", "+christianCastName[castValue]);
                            RegistrationChoiceSelectionThirdPage.castValue = christianCastConstant.get(castValue);
                        }

                        RegistrationChoiceSelectionThirdPage.religionValue = dataConstant.get(religionValue);

                    }
                    else if(religionValue == 2){
                        RegistrationChoiceSelectionThirdPage.castReligionText.setText(religionName[religionValue]);
                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                    }
                    else if(religionValue == 4){
                        RegistrationChoiceSelectionThirdPage.castReligionText.setText(religion.getText());
                        RegistrationChoiceSelectionThirdPage.otherReligion = religion.getText().toString();
                    }


                    if(religionValue == 0){
                        RegistrationChoiceSelectionThirdPage.onlyForMuslimLayout.setVisibility(View.VISIBLE);
                    }
                }



                RegistrationOwnInfo.castReligionOwn = 0 ;
                RegistrationChoiceSelectionThirdPage.castReligionChoice = 0;
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RegistrationOwnInfo.castReligionOwn = 0 ;
                RegistrationChoiceSelectionThirdPage.castReligionChoice = 0;
                finish();
            }
        });
    }



    private class RligionListListener implements NumberPicker.OnValueChangeListener{
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            //get new value and convert it to String
            //if you want to use variable value elsewhere, declare it as a field
            //of your main function
            String value = "" + newVal;
            religionValue = newVal;
            if(newVal==0){
                cast.setVisibility(View.GONE);
                if(religion!=null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker)findViewById(R.id.cast_picker);
                castPicker.setValue(0);
                castPicker.setMinValue(0);
                castPicker.setMaxValue(muslimCastName.length-1);
                castPicker.setDisplayedValues(muslimCastName);

                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            }
            else if(newVal==1)
            {
                cast.setVisibility(View.GONE);
                if(religion!=null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker)findViewById(R.id.cast_picker);
                castPicker.setValue(0);
                castPicker.setDisplayedValues(hinduCastName);
                castPicker.setMinValue(0);
                castPicker.setMaxValue(hinduCastName.length-1);
                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            }

            else if(newVal==2)
            {
                cast.setVisibility(View.GONE);
                if(religion!=null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker)findViewById(R.id.cast_picker);
                castPicker.setValue(0);
                castPicker.setMinValue(0);
                castPicker.setMaxValue(christianCastName.length-1);
                castPicker.setDisplayedValues(christianCastName);

                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            }

            else if(newVal==3)
            {
                cast.setVisibility(View.GONE);
                if(religion!=null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.INVISIBLE);
            }

            else if(newVal==4)
            {
                cast.setVisibility(View.GONE);
                castPicker.setVisibility(View.INVISIBLE);
                religion.setVisibility(View.VISIBLE);
                religion.setHint("আপনার ধর্ম");
            }

        }
    }

    private class CastListListener implements NumberPicker.OnValueChangeListener{
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            //get new value and convert it to String
            //if you want to use variable value elsewhere, declare it as a field
            //of your main function
            String value = "" + newVal;
            castValue = newVal;
            if(religionValue==0 && newVal==2)
            {
                cast.setVisibility(View.VISIBLE);
                cast.setHint("আপনার বর্ণ");
            }
            else{
                cast.setVisibility(View.GONE);
            }
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




}
