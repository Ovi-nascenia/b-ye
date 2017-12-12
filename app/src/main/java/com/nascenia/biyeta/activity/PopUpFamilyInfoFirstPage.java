package com.nascenia.biyeta.activity;

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

import com.nascenia.biyeta.R;

import java.lang.reflect.Field;

public class PopUpFamilyInfoFirstPage extends AppCompatActivity {
    Button accept,reject;
    NumberPicker picker;
    String[] data = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_family_info_first_page);
        picker = (NumberPicker)findViewById(R.id.picker);
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);


        if(RegistrationFamilyInfoFirstPage.selectedPopUp == 1 ){
            RegistrationFamilyInfoFirstPage.professionFather = 1;
            data = RegistrationFamilyInfoFirstPage.fatherOccupationName;
        }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 2){
            RegistrationFamilyInfoFirstPage.professionalGroupFather = 1;
            data = RegistrationFamilyInfoFirstPage.professonalGroupName;
        }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 3){
            RegistrationFamilyInfoFirstPage.professionMother = 1;
            data = RegistrationFamilyInfoFirstPage.occupationName;
        }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 4){
            RegistrationFamilyInfoFirstPage.professionalGroupMother = 1;
            data = RegistrationFamilyInfoFirstPage.professonalGroupName;
        }

        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);
        picker.setOnValueChangedListener(new PopUpFamilyInfoFirstPage.ListListener());
        setDividerColor(picker, Color.parseColor("#626262"));
        setNumberPickerTextColor(picker, Color.parseColor("#626262"));



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(RegistrationFamilyInfoFirstPage.selectedPopUp == 1 ){
                    RegistrationFamilyInfoFirstPage.professionFather = -1;
                }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 2){
                    RegistrationFamilyInfoFirstPage.professionalGroupFather = -1;
                }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 3){
                    RegistrationFamilyInfoFirstPage.professionMother = -1;
                }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 4){
                    RegistrationFamilyInfoFirstPage.professionalGroupMother = -1;
                }
                finish();
            }
        });
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
            if(RegistrationFamilyInfoFirstPage.selectedPopUp == 1 ){
                RegistrationFamilyInfoFirstPage.professionFather = newVal + 1;
            }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 2){
                RegistrationFamilyInfoFirstPage.professionalGroupFather = newVal + 1;
            }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 3){
                RegistrationFamilyInfoFirstPage.professionMother = newVal + 1;
            }else if(RegistrationFamilyInfoFirstPage.selectedPopUp == 4){
                RegistrationFamilyInfoFirstPage.professionalGroupMother = newVal + 1;
            }

        }
    }


}
