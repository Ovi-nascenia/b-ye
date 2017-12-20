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
    int religionValue = 0;
    static int castValue = 0;
    String constant;
    JSONObject religionObject, muslimCastObject, hinduCastObject, christianCastObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Bundle extras = getIntent().getExtras();
        Log.i("classnames", getClass().getSimpleName());


        constant = extras.getString("constants");
        Log.i("constantjson", constant);

        try {
            JSONObject jsonObject = new JSONObject(constant);
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

            for (int i = 0; i < muslimCastObject.length(); i++) {

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
        religionPicker.setMaxValue(religionName.length - 1);
        religionPicker.setDisplayedValues(religionName);
        religionPicker.setOnValueChangedListener(new RligionListListener());
        setDividerColor(religionPicker, Color.parseColor("#626262"));
        setNumberPickerTextColor(religionPicker, Color.parseColor("#626262"));

        castPicker = (NumberPicker) findViewById(R.id.cast_picker);
        castPicker.setMinValue(0);
        castPicker.setMaxValue(muslimCastName.length - 1);
        castPicker.setValue(0);
        castPicker.setDisplayedValues(muslimCastName);
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

                    if (dataConstant.get(religionValue).equals("1") && muslimCastConstant.get(castValue).equals("3")) {
                        if (cast.getText().toString().isEmpty())
                            Toast.makeText(getBaseContext(), getString(R.string.write_muslim_cast_name), Toast.LENGTH_LONG).show();
                        else {

                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue] + ", " +
                                    cast.getText().toString());
                            RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                            RegistrationOwnInfo.otherCast = cast.getText().toString();
                            RegistrationOwnInfo.castValue="";
                            RegistrationOwnInfo.otherReligion="";

                            onFinishPopUpCastReligionTask();
                        }
                        PopUpCastReligion.castValue=0;
                    } else if (!dataConstant.get(religionValue).equals("4") && !dataConstant.get(religionValue).equals("5")) {

                        if (dataConstant.get(religionValue).equals("1")) {
                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]
                                    + ", " + muslimCastName[castValue]);
                            RegistrationOwnInfo.castValue = muslimCastConstant.get(castValue);

                            Log.i("religiondata",  RegistrationOwnInfo.castReligionText.getText().toString()+" "+castValue);
                            PopUpCastReligion.castValue=0;
                        } else if (dataConstant.get(religionValue).equals("2")) {
                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]
                                    + ", " + hinduCastName[castValue]);
                            RegistrationOwnInfo.castValue = hinduCastConstant.get(castValue);
                            PopUpCastReligion.castValue=0;

                        } else if (dataConstant.get(religionValue).equals("3")) {
                            RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]
                                    + ", " + christianCastName[castValue]);
                            RegistrationOwnInfo.castValue = christianCastConstant.get(castValue);
                            PopUpCastReligion.castValue=0;
                        }

                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                        RegistrationOwnInfo.otherCast="";
                        RegistrationOwnInfo.otherReligion="";

                        onFinishPopUpCastReligionTask();

                    } else if (dataConstant.get(religionValue).equals("4")) {
                        RegistrationOwnInfo.castReligionText.setText(religionName[religionValue]);
                        RegistrationOwnInfo.religionValue = dataConstant.get(religionValue);
                        RegistrationOwnInfo.castValue = "";
                        RegistrationOwnInfo.otherCast="";
                        RegistrationOwnInfo.otherReligion="";

                        PopUpCastReligion.castValue=0;

                        onFinishPopUpCastReligionTask();
                    } else if (dataConstant.get(religionValue).equals("5")) {
                        if(religion.getText().toString().isEmpty())
                            Toast.makeText(getBaseContext(),getString(R.string.write_other_religion_name_message)
                                    ,Toast.LENGTH_SHORT).show();
                        else{
                            RegistrationOwnInfo.castReligionText.setText(religion.getText().toString());
                            RegistrationOwnInfo.otherReligion = religion.getText().toString();
                            RegistrationOwnInfo.religionValue = "";
                            RegistrationOwnInfo.castValue="";
                            RegistrationOwnInfo.otherCast="";

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
            religionValue = newVal;
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

                castPicker.setOnValueChangedListener(new CastListListener());
                religionPicker.setOnValueChangedListener(new RligionListListener());
            } else if (newVal == 1) {
                cast.setVisibility(View.GONE);
                if (religion != null)
                    religion.setVisibility(View.GONE);
                castPicker.setVisibility(View.VISIBLE);
                castPicker = (NumberPicker) findViewById(R.id.cast_picker);
                castPicker.setValue(0);
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
            castValue = newVal;
            Log.i("religiondata",  "listener "+castValue);
            if (religionValue == 0 && newVal == 2) {
                cast.setVisibility(View.VISIBLE);
                cast.setHint("আপনার বর্ণ");
            } else {
                cast.setVisibility(View.GONE);
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
