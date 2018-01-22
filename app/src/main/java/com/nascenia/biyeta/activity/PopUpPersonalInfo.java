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
import android.widget.TextView;

import com.nascenia.biyeta.R;

import java.lang.reflect.Field;

public class PopUpPersonalInfo extends AppCompatActivity {
    Button accept,reject;
    NumberPicker picker;
    String[] data = new String[]{};
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_personal_info);
        picker = (NumberPicker)findViewById(R.id.picker);
        accept = (Button)findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.cancel);
        mTextView = (TextView) findViewById(R.id.title);


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
        if(RegistrationPersonalInformation.selectedPopUp==12)
        {
            picker.setValue(30);                             // default 60 kg
        }
        if(RegistrationPersonalInformation.selectedPopUp==11)
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

                if(RegistrationPersonalInformation.selectedPopUp == 11){
                    RegistrationPersonalInformation.height = picker.getValue() + 1;
                }else if(RegistrationPersonalInformation.selectedPopUp == 12){
                    RegistrationPersonalInformation.weight = picker.getValue() + 1;
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
