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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
    Button accept, reject;
    private int religionValue = 0;
    private static int castValue = 0;
    private static String otherReligion = "",otherCast = "";
    String constant;
    JSONObject religionObject, muslimCastObject, hinduCastObject, christianCastObject;
    private SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Bundle extras = getIntent().getExtras();
        Log.i("classnames", getClass().getSimpleName());


        constant = extras.getString("constants");
        Log.i("constantjson", constant);

        sharePref = new SharePref(PopUpCastReligion.this);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(constant);
            Log.i("religionvalue: ","total: "+jsonObject.toString());

            religionObject = jsonObject.getJSONObject("religion_constant");
            muslimCastObject = jsonObject.getJSONObject("muslim_cast");
            hinduCastObject = jsonObject.getJSONObject("hindu_cast");
            christianCastObject = jsonObject.getJSONObject("christian_cast");

            Log.i("religionvalue: ",religionObject.toString());
            Log.i("religionvalue: ",muslimCastObject.toString());
            Log.i("religionvalue: ",hinduCastObject.toString());
            Log.i("religionvalue: ",christianCastObject.toString());



            for (int i = 0; i < religionObject.length(); i++) {
                dataConstant.add(religionObject.names().getString(i));
                data.add((String) religionObject.get(religionObject.names().getString(i)));
            }

            for (int i = 0; i < muslimCastObject.length() - 1; i++) { //quick fix to remove others muslim cast

                muslimCastConstant.add(muslimCastObject.names().getString(i));
                muslimCast.add((String) muslimCastObject.get(muslimCastObject.names().getString(i)));

            }

            String shiaPos = muslimCastConstant.get(0);
            String shiaValue = muslimCast.get(0);

            muslimCastConstant.remove(0);
            muslimCast.remove(0);

            muslimCastConstant.add(1, shiaPos);
            muslimCast.add(1, shiaValue);

            Log.i("totalmulimdata: ", "cons" + Arrays.toString(muslimCastConstant.toArray()));
            Log.i("totalmulimdata: ", Arrays.toString(muslimCast.toArray()));

            for (int i = 0; i < hinduCastObject.length(); i++) {
                hinduCastConstant.add(hinduCastObject.names().getString(i));
                hinduCast.add((String) hinduCastObject.get(hinduCastObject.names().getString(i)));
            }

            for (int i = 0; i < christianCastObject.length(); i++) {
                christianCastConstant.add(christianCastObject.names().getString(i));
                christianCast.add((String) christianCastObject.get(christianCastObject.names().getString(i)));
            }

            religionName = data.toArray(religionName);

            muslimCastName = muslimCast.toArray(muslimCastName);

            hinduCastName = hinduCast.toArray(hinduCastName);

            christianCastName = christianCast.toArray(christianCastName);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        cast = (EditText) findViewById(R.id.cast);
        religion = (EditText) findViewById(R.id.religion);
        religionPicker = (NumberPicker) findViewById(R.id.religion_picker);
        religionPicker.setMinValue(0);
        try{
            religionValue = Integer.parseInt(sharePref.get_data("religion"));
        }catch (NumberFormatException ne){
            religionValue = 0;
        }
        try {
            castValue = Integer.parseInt(sharePref.get_data("cast"));
        }catch (NumberFormatException ne){
            castValue = 0;
        }

        otherReligion = sharePref.get_data("other_religion");
        otherCast = sharePref.get_data("other_cast");
        if(otherCast.length() > 0 && religionValue == 1)
            castValue = 3;

        if(religionValue == 0){
            religionValue = 1;
            castValue = 2;
//            updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
        }
        religionPicker.setValue(religionValue);
        religionPicker.setMaxValue(religionName.length - 1);
        religionPicker.setDisplayedValues(religionName);
        religionPicker.setOnValueChangedListener(new RligionListListener());
        setDividerColor(religionPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(religionPicker, Color.parseColor("#626262"));

        castPicker = (NumberPicker) findViewById(R.id.cast_picker);
        castPicker.setMinValue(0);
//        castPicker.setMaxValue(muslimCastName.length - 1);
        int icast = -1;
        String other_cast = null;
//        if(jsonObject.has("data")) {
//            try {
//                if (sharePref.get_data("other_cast").length() > 0) {
////                    other_cast = sharePref.get_data("other_cast");
//                    icast = 3;
//                } else {
//                    icast = Integer.parseInt(sharePref.get_data("cast"));
//                }
////                if (jsonObject.getJSONObject("data").getString("other_cast").length() > 0) {
////                    other_cast = jsonObject.getJSONObject("data").getString("other_cast");
////                    icast = 3;
////                } else {
////                    icast = Integer.parseInt(sharePref.get_data("cast"));
////                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        if(religionValue == 1){
            castPicker.setDisplayedValues(muslimCastName);
            castPicker.setMaxValue(muslimCastName.length - 1);
            if(castValue == 2)
                castPicker.setValue(0);
            else if(castValue == 3) {
                castPicker.setValue(2);
                cast.setVisibility(View.VISIBLE);
                cast.setHint("আপনার বর্ণ");
                cast.setText(otherCast.equalsIgnoreCase("key")?"":otherCast);
                cast.setSelection(cast.getText().length());
            }else
                castPicker.setValue(1);
        }else if(religionValue == 2){
            religionPicker.setValue(1);
            castPicker.setDisplayedValues(hinduCastName);
            castPicker.setMaxValue(hinduCastName.length - 1);
            castPicker.setValue(castValue-2);
        }else if(religionValue == 3){
            religionPicker.setValue(2);
            castPicker.setDisplayedValues(christianCastName);
            castPicker.setMaxValue(christianCastName.length - 1);
            castPicker.setValue(castValue-6);
        }else if(religionValue == 4){
            religionPicker.setValue(3);
            castPicker.setVisibility(View.INVISIBLE);
        }else{
            religionPicker.setValue(4);
            castPicker.setVisibility(View.INVISIBLE);
            religion.setVisibility(View.VISIBLE);
            religion.setText(otherReligion.equalsIgnoreCase("key")?"":otherReligion);
            religion.setSelection(religion.getText().length());
        }


//        castPicker.setDisplayedValues(muslimCastName);
        castPicker.setOnValueChangedListener(new CastListListener());
        setDividerColor(castPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(castPicker, Color.parseColor("#626262"));
        accept = (Button) findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.cancel);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RegistrationOwnInfo.castReligionOwn == 1) {

                    if (dataConstant.get(religionValue -1).equals("1") && muslimCastConstant.get(castValue - 1).equals("3")) {
                        if (cast.getText().toString().isEmpty())
                            Toast.makeText(getBaseContext(), getString(R.string.write_muslim_cast_name), Toast.LENGTH_LONG).show();
                        else {

                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue -1] + ", " +
                                    cast.getText().toString());
//                            religionValue = Integer.parseInt(dataConstant.get(religionValue));
                            otherCast = cast.getText().toString();
//                            castValue=0;
                            otherReligion="";
                            updateSharedPrf(religionValue, castValue, otherCast, otherReligion);

                            onFinishPopUpCastReligionTask();
                        }
//                        PopUpCastReligion.castValue=0;
                    } else if (!dataConstant.get(religionValue -1).equals("4") && !dataConstant.get(religionValue-1).equals("5")) {

                        if (dataConstant.get(religionValue-1).equals("1")) {
                            if(castValue == 3){//sharePref.get_data("other_cast").length()>0) {
                                RegistrationOwnInfo.castReligionText.setText(
                                        religionName[religionValue-1]
                                                + ", " + cast.getText().toString());
                                otherCast = cast.getText().toString();
//                                sharePref.set_data("other_cast", cast.getText().toString());
//                                sharePref.set_data("cast", "");
//                                castValue = 0;
                                otherReligion = "";
                                updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
                            }else if(castValue == 2){
//                                RegistrationOwnInfo.castReligionText.setText(
//                                        religionName[religionValue-1]
//                                                + ", " + muslimCastName[castValue]);
//                                RegistrationOwnInfo.castValue = muslimCastConstant.get(castValue);
//                                castValue = Integer.parseInt(muslimCastConstant.get(castValue));
//                                sharePref.set_data("cast", muslimCastConstant.get(castValue));
//                                sharePref.set_data("other_cast", "");
                                otherCast = "";
                                otherReligion = "";
                                updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
                            }else{
                                otherCast = "";
                                otherReligion = "";
                                updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
                            }

//                            Log.i("religiondata",  RegistrationOwnInfo.castReligionText.getText().toString()+" "+castValue);
//                            PopUpCastReligion.castValue=0;
                        } else if (dataConstant.get(religionValue-1).equals("2")) {
//                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]
//                                    + ", " + hinduCastName[castValue]);
//                            RegistrationOwnInfo.castValue = hinduCastConstant.get(castValue);
                            castValue = Integer.parseInt(hinduCastConstant.get(castValue-2));
                            otherCast = "";
                            otherReligion = "";
//                            PopUpCastReligion.castValue=0;
                            updateSharedPrf(religionValue, castValue, otherCast, otherReligion);

                        } else if (dataConstant.get(religionValue-1).equals("3")) {
//                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]
//                                    + ", " + christianCastName[castValue]);
//                            RegistrationOwnInfo.castValue = christianCastConstant.get(castValue);
                            castValue = Integer.parseInt(christianCastConstant.get(castValue-6));
//                            PopUpCastReligion.castValue=0;
                            otherCast = "";
                            otherReligion = "";
//                            PopUpCastReligion.castValue=0;
                            updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
                        }

//                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
//                        RegistrationOwnInfo.otherCast="";
//                        RegistrationOwnInfo.otherReligion="";

                        onFinishPopUpCastReligionTask();

                    } else if (dataConstant.get(religionValue-1).equals("4")) {
//                        RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]);
//                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                        castValue = 0;
                        otherCast="";
                        otherReligion="";
                        updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
//                        PopUpCastReligion.castValue=0;

                        onFinishPopUpCastReligionTask();
                    } else if (dataConstant.get(religionValue-1).equals("5")) {
                        if(religion.getText().toString().isEmpty())
                            Toast.makeText(getBaseContext(),getString(R.string.write_other_religion_name_message)
                                    ,Toast.LENGTH_SHORT).show();
                        else{
//                            RegistrationOwnInfo.castReligionText.setText(religion.getText().toString());
                            otherReligion = religion.getText().toString();
//                            RegistrationOwnInfo.religionValue = "5";
                            castValue=0;
                            otherCast="";
                            updateSharedPrf(religionValue, castValue, otherCast, otherReligion);
                            onFinishPopUpCastReligionTask();
                        }

                        PopUpCastReligion.castValue=0;
                    }
                }

                /*RegistrationOwnInfo.castReligionOwn = 0;
                RegistrationChoiceSelectionThirdPage.castReligionChoice = 0;
                finish();*/
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  RegistrationOwnInfo.castReligionOwn = 0;
                RegistrationChoiceSelectionThirdPage.castReligionChoice = 0;
                finish();*/
              onFinishPopUpCastReligionTask();
            }
        });
    }

    private void updateSharedPrf(int religionValue, int castValue, String otherCast,
            String otherReligion) {
        sharePref.set_data("religion", religionValue + "");
        sharePref.set_data("cast", castValue == 0 ? "" : castValue + "");
        sharePref.set_data("other_religion", otherReligion);
        sharePref.set_data("other_cast", otherCast);
    }

    private void onFinishPopUpCastReligionTask() {

        RegistrationOwnInfo.castReligionOwn = 0;
        RegistrationChoiceSelectionThirdPage.castReligionChoice = 0;
        finish();
    }


    private class RligionListListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            //get new value and convert it to String
            //if you want to use variable value elsewhere, declare it as a field
            //of your main function
            String value = "" + newVal;
            religionValue = newVal + 1;
            if (newVal == 0) {
                cast.setVisibility(View.GONE);
                if (religion != null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker) findViewById(R.id.cast_picker);
                castPicker.setValue(0);
                castPicker.setMinValue(0);
                castPicker.setMaxValue(muslimCastName.length - 1);
                castPicker.setDisplayedValues(muslimCastName);
                castValue = 1;
                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            } else if (newVal == 1) {
                cast.setVisibility(View.GONE);
                if (religion != null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker) findViewById(R.id.cast_picker);
                castPicker.setValue(0);
                castValue = 2;
                castPicker.setDisplayedValues(hinduCastName);
                castPicker.setMinValue(0);
                castPicker.setMaxValue(hinduCastName.length - 1);
                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            } else if (newVal == 2) {
                cast.setVisibility(View.GONE);
                if (religion != null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker) findViewById(R.id.cast_picker);
                castPicker.setValue(0);
                castValue = 6;
                castPicker.setMinValue(0);
                castPicker.setMaxValue(christianCastName.length - 1);
                castPicker.setDisplayedValues(christianCastName);
                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            } else if (newVal == 3) {
                cast.setVisibility(View.GONE);
                if (religion != null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.INVISIBLE);
            } else if (newVal == 4) {
                cast.setVisibility(View.GONE);
                castPicker.setVisibility(View.INVISIBLE);
                religion.setVisibility(View.VISIBLE);
                religion.setHint("আপনার ধর্ম");
            }

        }
    }

    private class CastListListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            //get new value and convert it to String
            //if you want to use variable value elsewhere, declare it as a field
            //of your main function
            String value = "" + newVal;

            Log.i("religiondata", "listener " + castValue);
            if (religionValue == 1) {
                if (newVal == 2) {
                    castValue = 3;
                    cast.setVisibility(View.VISIBLE);
                    cast.setHint("আপনার বর্ণ");
                }else if(newVal == 0){
                    castValue = 2;
                    cast.setVisibility(View.GONE);
                }else{
                    castValue = 1;
                    cast.setVisibility(View.GONE);
                }
            }
            if(religionValue == 2)
            {
                cast.setVisibility(View.GONE);
                castValue = newVal + 2;
            }
            if(religionValue == 3)
            {
                cast.setVisibility(View.GONE);
                castValue = newVal + 6;
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


}
