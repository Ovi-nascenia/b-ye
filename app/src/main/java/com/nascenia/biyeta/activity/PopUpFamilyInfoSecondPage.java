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
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.BrotherViewAdapter;
import com.nascenia.biyeta.adapter.OtherViewAdapter;
import com.nascenia.biyeta.adapter.SisterViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class PopUpFamilyInfoSecondPage extends AppCompatActivity {

    String[] numberOfBrotherSister = {"০", "১", "২", "৩", "৪", "৫", "৫+"};

    int[] constantOfBrotherSister = {0,1,2,3,4,5,6,};

    String[] age;

    ArrayList<String> professonalGroup = new ArrayList<String>();
    String[] professonalGroupName = new String[professonalGroup.size()];
    ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    String[] professonalGroupConstantValue = new String[professonalGroup.size()];

    ArrayList<String> occupation = new ArrayList<String>();
    String[] occupationName = new String[occupation.size()];
    ArrayList<String> occupationConstant = new ArrayList<String>();
    String[] occupationConstantValue = new String[occupation.size()];

    ArrayList<String> relation = new ArrayList<String>();
    String[] relationName = new String[relation.size()];
    ArrayList<String> relationConstant = new ArrayList<String>();
    String[] relationConstantValue = new String[relation.size()];

    ArrayList<String> maritalStatus = new ArrayList<String>();
    String[] maritalStatusName = new String[maritalStatus.size()];
    ArrayList<String> maritalStatusConstant = new ArrayList<String>();
    String[] maritalStatusConstantValue = new String[maritalStatus.size()];



    Button accept,reject;
    NumberPicker picker;
    String[] data = new String[]{};
    String constant;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras.containsKey("constant")){
            String defaultValue = "";
            constant = extras.getString("constant", defaultValue);
        }

        age = new String[100];

        for(int i=0;i<100;i++)
        {
            age[i] = getAgeInBangla(Integer.toString(i));
        }


        try{
            JSONObject jsonObject = new JSONObject(constant);

            JSONObject professonalGroupObject = jsonObject.getJSONObject("professional_group_constant");
            JSONObject occupationObject = jsonObject.getJSONObject("occupation_constant");
            JSONObject relationObject = jsonObject.getJSONObject("relation_constant");
            JSONObject maritalStatusObject = jsonObject.getJSONObject("marital_status_constant");
            for(int i=0;i<professonalGroupObject.length();i++)
            {
                professonalGroupConstant.add(professonalGroupObject.names().getString(i));
                professonalGroup.add((String) professonalGroupObject.get(professonalGroupObject.names().getString(i)));
            }

            for(int i=0;i<occupationObject.length();i++)
            {
                occupationConstant.add(occupationObject.names().getString(i));
                occupation.add((String) occupationObject.get(occupationObject.names().getString(i)));
                Log.e("occupation",(String) occupationObject.get(occupationObject.names().getString(i)));
            }

            for(int i=0;i<relationObject.length();i++)
            {
                relationConstant.add(relationObject.names().getString(i));
                relation.add((String) relationObject.get(relationObject.names().getString(i)));
            }

            for(int i=0;i<maritalStatusObject.length();i++)
            {
                maritalStatusConstant.add(maritalStatusObject.names().getString(i));
                maritalStatus.add((String) maritalStatusObject.get(maritalStatusObject.names().getString(i)));
            }

            professonalGroupName = professonalGroup.toArray(professonalGroupName);
            professonalGroupConstantValue = professonalGroupConstant.toArray(professonalGroupConstantValue);

            occupationName = occupation.toArray(occupationName);
            occupationConstantValue = occupationConstant.toArray(occupationConstantValue);

            relationName = relation.toArray(relationName);
            relationConstantValue = relationConstant.toArray(relationConstantValue);

            maritalStatusName = maritalStatus.toArray(maritalStatusName);
            maritalStatusConstantValue = maritalStatusConstant.toArray(maritalStatusConstantValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_pop_up_family_info_first_page);
        picker = (NumberPicker)findViewById(R.id.picker);
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);


        if(BrotherViewAdapter.selectedPopUp == 1){
            data = occupationName;
        }else if(BrotherViewAdapter.selectedPopUp == 2){
            data = professonalGroupName;
        }else if(BrotherViewAdapter.selectedPopUp == 3){
            data = maritalStatusName;
        }else if(BrotherViewAdapter.selectedPopUp == 4){
            data = relationName;
        }else if(BrotherViewAdapter.selectedPopUp == 5){
            data = age;
        }

        else if(SisterViewAdapter.selectedPopUp== 1){
            data = occupationName;
        }else if(SisterViewAdapter.selectedPopUp == 2){
            data = professonalGroupName;
        }else if(SisterViewAdapter.selectedPopUp == 3){
            data = maritalStatusName;
        }else if(SisterViewAdapter.selectedPopUp == 4){
            data = relationName;
        }else if(SisterViewAdapter.selectedPopUp == 5){
            data = age;
        }

        else if(OtherViewAdapter.selectedPopUp== 1){
            data = occupationName;
        }else if(OtherViewAdapter.selectedPopUp == 2){
            data = professonalGroupName;
        }else if(OtherViewAdapter.selectedPopUp == 3){
            data = maritalStatusName;
        }else if(OtherViewAdapter.selectedPopUp == 4){
            data = relationName;
        }else if(OtherViewAdapter.selectedPopUp == 5){
            data = age;
        }

        else if(RegistrationFamilyInfoSecondPage.selectedPopUp==1){
            data = numberOfBrotherSister;
        }
        else if(RegistrationFamilyInfoSecondPage.selectedPopUp==2){
            data = numberOfBrotherSister;
        }

        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);
        picker.setOnValueChangedListener(new ListListener());
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
                BrotherViewAdapter.selectedPopUp = 0;
                SisterViewAdapter.selectedPopUp = 0;
                OtherViewAdapter.selectedPopUp = 0;
                RegistrationFamilyInfoSecondPage.selectedPopUp=0;
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrotherViewAdapter.selectedPopUp = 0;
                SisterViewAdapter.selectedPopUp = 0;
                OtherViewAdapter.selectedPopUp = 0;
                RegistrationFamilyInfoSecondPage.selectedPopUp=0;
                finish();
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        BrotherViewAdapter.selectedPopUp = 0;
        SisterViewAdapter.selectedPopUp = 0;
        OtherViewAdapter.selectedPopUp = 0;
        RegistrationFamilyInfoSecondPage.selectedPopUp=0;
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

            if(BrotherViewAdapter.selectedPopUp == 1){
                BrotherViewAdapter.brotherOccupation.setText(occupationName[newVal]);
                BrotherViewAdapter.occupation = occupationConstantValue[newVal];
            }else if(BrotherViewAdapter.selectedPopUp == 2){
                BrotherViewAdapter.brotherProfessionalGroup.setText(professonalGroupName[newVal]);
                BrotherViewAdapter.professonalGroup = professonalGroupConstantValue[newVal];
            }else if(BrotherViewAdapter.selectedPopUp == 3){
                BrotherViewAdapter.brotherMaritalStatus.setText(maritalStatusName[newVal]);
                BrotherViewAdapter.maritalStatus = maritalStatusConstantValue[newVal];
            }else if(BrotherViewAdapter.selectedPopUp == 5){
                BrotherViewAdapter.brotherAge.setText(age[newVal]);
                BrotherViewAdapter.age = newVal;
            }

            else if(SisterViewAdapter.selectedPopUp == 1){
                SisterViewAdapter.sisterOccupation.setText(occupationName[newVal]);
                SisterViewAdapter.occupation = occupationConstantValue[newVal];
            }else if(SisterViewAdapter.selectedPopUp == 2){
                SisterViewAdapter.sisterProfessionalGroup.setText(professonalGroupName[newVal]);
                SisterViewAdapter.professonalGroup = professonalGroupConstantValue[newVal];
            }else if(SisterViewAdapter.selectedPopUp == 3){
                SisterViewAdapter.sisterMaritalStatus.setText(maritalStatusName[newVal]);
                SisterViewAdapter.maritalStatus = maritalStatusConstantValue[newVal];
            }else if(SisterViewAdapter.selectedPopUp == 5){
                SisterViewAdapter.sisterAge.setText(age[newVal]);
                SisterViewAdapter.age = newVal;
            }

            else if(OtherViewAdapter.selectedPopUp == 1){
                OtherViewAdapter.otherOccupation.setText(occupationName[newVal]);
                OtherViewAdapter.occupation = occupationConstantValue[newVal];
            }else if(OtherViewAdapter.selectedPopUp == 2){
                OtherViewAdapter.otherProfessionalGroup.setText(professonalGroupName[newVal]);
                OtherViewAdapter.professonalGroup = professonalGroupConstantValue[newVal];
            }else if(OtherViewAdapter.selectedPopUp == 3){
                OtherViewAdapter.otherMaritalStatus.setText(maritalStatusName[newVal]);
                OtherViewAdapter.maritalStatus = maritalStatusConstantValue[newVal];
            }else if(OtherViewAdapter.selectedPopUp == 4){
                OtherViewAdapter.otherRelationalStatus.setText(relationName[newVal]);
                OtherViewAdapter.relation = relationConstantValue[newVal];
            }else if(OtherViewAdapter.selectedPopUp == 5){
                OtherViewAdapter.otherAge.setText(age[newVal]);
                OtherViewAdapter.age = newVal;
            }

            else if(RegistrationFamilyInfoSecondPage.selectedPopUp == 1){
                RegistrationFamilyInfoSecondPage.brotherNumber.setText(data[newVal]);
                RegistrationFamilyInfoSecondPage.numberOfBrother = constantOfBrotherSister[newVal];
            }

            else if(RegistrationFamilyInfoSecondPage.selectedPopUp == 2){
                RegistrationFamilyInfoSecondPage.sisterNumber.setText(data[newVal]);
                RegistrationFamilyInfoSecondPage.numberOfSister = constantOfBrotherSister[newVal];
            }
        }
    }

    public String getAgeInBangla(String string)
    {
        Character bangla_number[]={'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
        Character eng_number[]={'0','1','2','3','4','5','6','7','8','9'};
        String values = "";
        char[] character = string.toCharArray();
        for (int i=0; i<character.length ; i++) {
            Character c = ' ';
            for (int j = 0; j < eng_number.length; j++) {
                if(character[i]==eng_number[j])
                {
                    c=bangla_number[j];
                    break;
                }else {
                    c=character[i];
                }
            }
            values=values+c;
        }
        return values;
    }
}