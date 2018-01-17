package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.nascenia.biyeta.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by saiful on 12/27/17.
 */

public class AbroadOptionPickerPopUpActivity extends AppCompatActivity {

    private String abroadStatusTypeArray[] = {"শিক্ষার্থী", "ওয়ার্ক পারমিট", "স্থায়ী বাসিন্দা", "নাগরিক", "প্রক্রিয়াধীন"};
    private NumberPicker abroadStatusPicker;
    private Button acceptBtn, rejectBtn;
    private int statusNumber = 0;
    TextView mTextViewTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_abroad_option_dialog);


        abroadStatusPicker = findViewById(R.id.picker);
        acceptBtn = findViewById(R.id.accept);
        rejectBtn = findViewById(R.id.cancel);
        mTextViewTitle = findViewById(R.id.title);

        abroadStatusPicker.setMinValue(0);
        abroadStatusPicker.setMaxValue(abroadStatusTypeArray.length - 1);
        abroadStatusPicker.setDisplayedValues(abroadStatusTypeArray);
        abroadStatusPicker.setOnValueChangedListener(new AbroadStatusTypeListListener());
        setDividerColor(abroadStatusPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(abroadStatusPicker, Color.parseColor("#626262"));

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ABROAD_STATUS_TYPE",abroadStatusTypeArray[statusNumber]);
                intent.putExtra("ABROAD_STATUS_TYPE_SELECTOR_NUMBER",statusNumber+1);

                setResult(2, intent);
                finish();
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ABROAD_STATUS_TYPE","reject");
                setResult(2, intent);
                finish();
            }
        });


    }

    private class AbroadStatusTypeListListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
            statusNumber = newValue;
        }
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
}
