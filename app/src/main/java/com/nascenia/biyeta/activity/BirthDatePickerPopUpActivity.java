package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.CalenderBanglaInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by saiful on 12/22/17.
 */

public class BirthDatePickerPopUpActivity extends AppCompatActivity {

    private ArrayList<String> yearArrayList = new ArrayList<>();
    private String[] yearArray = new String[yearArrayList.size()];

    private ArrayList<String> dateArrayList = new ArrayList<>();
    private String[] dateArray = new String[dateArrayList.size()];

    private ArrayList<String> monthArrayList = new ArrayList<>();
    private String[] monthArray = new String[monthArrayList.size()];

    private NumberPicker yearPicker, monthPicker, datePicker;
    private Button acceptBtn, rejectBtn;
    private int yearValue = 0, monthValue = 0, dateValue = 0;

    private int startingYear = 1967;

    private static Calendar calendar;//=new GregorianCalendar(1967, Calendar.JANUARY, 1);;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_datepicker_dialog);

        for (int i = startingYear; i <= 3000; i++) {
            String number = String.valueOf(i);
            String banglaYear = "";
            for (int j = 0; j < 4; j++) {
                banglaYear = banglaYear + CalenderBanglaInfo.getBanglaDigit(
                        Character.digit(number.charAt(j), 10));
            }

            yearArrayList.add(banglaYear);
        }

        acceptBtn = findViewById(R.id.accept_btn);
        rejectBtn = findViewById(R.id.cancel_btn);


        yearPicker = findViewById(R.id.year_picker);

        yearArray = yearArrayList.toArray(yearArray);

        yearPicker.setMinValue(0);
        yearPicker.setMaxValue(yearArray.length - 1);
        yearPicker.setDisplayedValues(yearArray);
        yearPicker.setOnValueChangedListener(new YearListListener());
        setDividerColor(yearPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(yearPicker, Color.parseColor("#626262"));


        monthPicker = findViewById(R.id.month_picker);

        loadMonthData();
        monthArray = monthArrayList.toArray(monthArray);

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(monthArray.length - 1);
        monthPicker.setDisplayedValues(monthArray);
        monthPicker.setOnValueChangedListener(new MonthListListener());
        setDividerColor(monthPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(monthPicker, Color.parseColor("#626262"));


        datePicker = findViewById(R.id.date_picker);

        //set default date
        calendar = new GregorianCalendar(startingYear, Calendar.JANUARY, 1);
        int initialMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("totalday", initialMonthDay + "");

        prepareDateList(initialMonthDay);

        // datePicker.setMinValue(0);
        // datePicker.setMaxValue(dateArray.length - 1);
        // datePicker.setDisplayedValues(dateArray);
        datePicker.setOnValueChangedListener(new DateListListener());
        setDividerColor(datePicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(datePicker, Color.parseColor("#626262"));


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String convertedBanglaMonthValue = String.valueOf(monthValue + 1);

                if (convertedBanglaMonthValue.length() == 2) {

                    convertedBanglaMonthValue = CalenderBanglaInfo.getBanglaDigit(
                            Integer.parseInt(convertedBanglaMonthValue) / 10)
                            +
                            CalenderBanglaInfo.getBanglaDigit(
                                    Integer.parseInt(convertedBanglaMonthValue) % 10);

                } else {

                    convertedBanglaMonthValue = "০" + CalenderBanglaInfo.getBanglaDigit(
                            Integer.parseInt(convertedBanglaMonthValue));
                }

                Intent intent = new Intent();
                intent.putExtra("DATE_OF_BIRTH", dateArray[dateValue] + "/" +
                        convertedBanglaMonthValue + "/" +
                        yearArray[yearValue]);
                setResult(2, intent);
                finish();//finishing activity
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("DATE_OF_BIRTH", "reject");
                setResult(2, intent);
                finish();//finishing activity
            }
        });

    }

    private void prepareDateList(int totalDayOfMonth) {

        dateArrayList.clear();
        for (int i = 1; i < totalDayOfMonth + 1; i++) {

            if (String.valueOf(i).length() == 2) {
                dateArrayList.add(CalenderBanglaInfo.getBanglaDigit(i / 10) +
                        CalenderBanglaInfo.getBanglaDigit(i % 10));
            } else {
                dateArrayList.add("০" + CalenderBanglaInfo.getBanglaDigit(i));
            }
        }

        // Prevent ArrayOutOfBoundExceptions by setting
        // values array to null so its not checked
        datePicker.setDisplayedValues(null);

        datePicker.setMinValue(0);
        dateArray = new String[dateArrayList.size()];
        Log.i("totalday", "before: " + dateArrayList.size() + " " + dateArray.length);
        dateArray = dateArrayList.toArray(dateArray);
        /*int min = dateArray.length-1;
        Log.i("totalday", " "+min);*/
        datePicker.setMaxValue(dateArray.length - 1);
        datePicker.setDisplayedValues(dateArray);

    }

    private void loadMonthData() {

        monthArrayList.add("জানুয়ারী");
        monthArrayList.add("ফেব্রুয়ারি");
        monthArrayList.add("মার্চ");
        monthArrayList.add("এপ্রিল");
        monthArrayList.add("মে");
        monthArrayList.add("জুন");
        monthArrayList.add("জুলাই");
        monthArrayList.add("অগাস্ট");
        monthArrayList.add("সেপ্টেম্বর");
        monthArrayList.add("অক্টোবর");
        monthArrayList.add("নভেম্বর");
        monthArrayList.add("ডিসেম্বর");

    }

    private class YearListListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
            yearValue = newValue;

            calendar.set(Integer.parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(yearArray[yearValue])),
                    monthValue, 1);

            Log.i("totalday", Integer.
                    parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(yearArray[yearValue])) + " "
                    + monthValue + " " +
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));


            prepareDateList(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
    }

    private class MonthListListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
            monthValue = newValue;
            calendar.set(Integer.parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(yearArray[yearValue])),
                    monthValue, 1);
            Log.i("totalday", Integer.
                    parseInt(CalenderBanglaInfo.getDigitEnglishFromBangla(yearArray[yearValue])) + " "
                    + monthValue + " " +
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH));


            prepareDateList(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
    }

    private class DateListListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
            dateValue = newValue;
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
