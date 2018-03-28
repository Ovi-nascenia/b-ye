package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.CalenderBanglaInfo;
import com.nascenia.biyeta.utils.Utils;

public class FacebookRegistrationDataInput extends AppCompatActivity {

    EditText email_edit_text, password_edit_text, name_edit_text, display_name_edit_text, dateOfBirthEditext;
    String email, realName, displayName ,birthday ,createdBy ,uid ,provider;
    Button buttonRegistration;
    TextView termsTextView1;
    private CheckBox terms_check;
    private Button male, female;
    private static int genderValue = -1;
    private String gender;
    private Toolbar toolbar;

    private final int DATE_OF_BIRTH_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_registration_data_input);
        setData();
//        setUpToolBar("ফেইসবুক রেজিস্ট্রেশন", this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void backBtnAction(View v) {
        finish();
    }

    private void underLineText(TextView tv, String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv.setText(content);
    }

    private void setData() {

        email_edit_text = (EditText) findViewById(R.id.email);
        password_edit_text = (EditText) findViewById(R.id.password);
        name_edit_text = (EditText) findViewById(R.id.name);
        display_name_edit_text = (EditText) findViewById(R.id.display_name);
        dateOfBirthEditext = findViewById(R.id.date_Of_birth_editext);
        dateOfBirthEditext.setKeyListener(null);

        dateOfBirthEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(FacebookRegistrationDataInput.this, BirthDatePickerPopUpActivity.class),
                        DATE_OF_BIRTH_REQUEST_CODE);
            }
        });

//        dateOfBirthEditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    startActivityForResult(
//                            new Intent(FacebookRegistrationDataInput.this, BirthDatePickerPopUpActivity.class),
//                            DATE_OF_BIRTH_REQUEST_CODE);
//                }
//            }
//        });


        buttonRegistration = (Button) findViewById(R.id.register_button);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        email = getIntent().getStringExtra("email");
        if(email!=null && email.length()>0) {
            email_edit_text.setText(email);
            email_edit_text.setSelection(email.length());
//            email_edit_text.setEnabled(false);
//            email_edit_text.setVisibility(View.GONE);
        }/*else {
            email_edit_text.setVisibility(View.VISIBLE);
        }*/
        /*realName = getIntent().getStringExtra("first_name");
        if(realName!=null && realName.length()>0) {
//            name_edit_text.setText(realName);
//            name_edit_text.setEnabled(false);
            name_edit_text.setVisibility(View.GONE);
        }else {
            name_edit_text.setVisibility(View.VISIBLE);
        }*/
        /*displayName = getIntent().getStringExtra("display_name");
        if(displayName!=null && displayName.length()>0) {
//            display_name_edit_text.setText(displayName);
//            display_name_edit_text.setEnabled(false);
            display_name_edit_text.setVisibility(View.GONE);
        }else {
            display_name_edit_text.setVisibility(View.VISIBLE);
        }*/
        /*birthday = getIntent().getStringExtra("birthday");
        if(birthday!=null && birthday.length()>0) {
//            dateOfBirthEditext.setText(birthday);
//            dateOfBirthEditext.setEnabled(false);
            dateOfBirthEditext.setVisibility(View.GONE);
        }else {
            dateOfBirthEditext.setVisibility(View.VISIBLE);
        }*/
        createdBy = getIntent().getStringExtra("created_by");
        uid = getIntent().getStringExtra("uid");
        provider = getIntent().getStringExtra("provider");

        termsTextView1 = findViewById(R.id.terms_text1);
        underLineText(termsTextView1, "শর্তাবলী ");
        terms_check = findViewById(R.id.terms_check);
        termsTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacebookRegistrationDataInput.this, TermOfUse.class));
            }
        });
        male = (Button) findViewById(R.id.man);
        female = (Button) findViewById(R.id.woman);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundColor(getResources().getColor(R.color.back_varify_text_view_1));
                male.setTextColor(getResources().getColor(R.color.white));
                female.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
                female.setTextColor(getResources().getColor(R.color.back_varify_text_view_1));
                genderValue = 0;
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // male.setHeight(35);
//                female.setHeight(35);
                female.setBackgroundColor(getResources().getColor(R.color.back_varify_text_view_1));
                female.setTextColor(getResources().getColor(R.color.white));
                male.setBackgroundColor(getResources().getColor(R.color.tab_background_unselected));
                male.setTextColor(getResources().getColor(R.color.back_varify_text_view_1));
                genderValue = 1;
            }
        });
    }

    private void getData() {

        if (genderValue == 0) {
            gender = "female";
        } else if (genderValue == 1) {
            gender = "male";
        }

        if (gender == null) {
            //Utils.ShowAlert(RegistrationFirstActivity.this, "পাত্র/পাত্রী নির্বাচন করুন");
            Toast.makeText(getBaseContext(), "পাত্র/পাত্রী নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        }

        if (/*email_edit_text.getVisibility()==View.VISIBLE &&*/ email_edit_text.getText().length() == 0) {
            email_edit_text.requestFocus();
            email_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার ইমেইল অ্যাড্রেসটি পূরণ করুন", Toast.LENGTH_LONG).show();
            return;
        }else if (/*email_edit_text.getVisibility()==View.VISIBLE &&*/ !Utils.isValidEmailAddress(email_edit_text.getText().toString())) {
            email_edit_text.requestFocus();
            email_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার ইমেইলটি সঠিক নয়", Toast.LENGTH_LONG).show();
            return;
        }//else if (email_edit_text.getVisibility()==View.VISIBLE){
        email = email_edit_text.getText().toString();
        //}
        if (/*name_edit_text.getVisibility()==View.VISIBLE &&*/ name_edit_text.getText().length() == 0) {
            name_edit_text.requestFocus();
            name_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার নাম পূরণ করুন", Toast.LENGTH_LONG).show();
            return;
        }
        //}else if (name_edit_text.getVisibility()==View.VISIBLE)
        realName = name_edit_text.getText().toString();
        //}
        if (/*display_name_edit_text.getVisibility()==View.VISIBLE &&*/ display_name_edit_text.getText().length() == 0) {
            display_name_edit_text.requestFocus();
            display_name_edit_text.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার ডিসপ্লে নাম পূরণ করুন", Toast.LENGTH_LONG).show();
            return;
        }//else if (display_name_edit_text.getVisibility()==View.VISIBLE){
        displayName = display_name_edit_text.getText().toString();
        //}
        if (/*dateOfBirthEditext.getVisibility()==View.VISIBLE &&*/ dateOfBirthEditext.getText().length() == 0) {
            dateOfBirthEditext.requestFocus();
            dateOfBirthEditext.setSelection(0);
            Toast.makeText(getBaseContext(), "আপনার জন্ম তারিখ নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        }//else if (dateOfBirthEditext.getVisibility()==View.VISIBLE){
//            birthday = dateOfBirthEditext.getText().toString();
        //}

        if(!terms_check.isChecked()) {
            terms_check.requestFocus();
            Toast.makeText(getBaseContext(), "শর্তাবলী গ্রহণ করুন",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Intent returnIntent = getIntent();
        returnIntent.putExtra("email", email);
        returnIntent.putExtra("first_name", realName);
        returnIntent.putExtra("display_name", displayName);
        returnIntent.putExtra("birthday", birthday);
        returnIntent.putExtra("gender", gender);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (!data.getStringExtra("DATE_OF_BIRTH").equalsIgnoreCase("reject")) {
                dateOfBirthEditext.setText(data.getStringExtra("DATE_OF_BIRTH"));
                String dateOfBirthBangla[] = data.getStringExtra("DATE_OF_BIRTH").split("/");

                String convertedEnglishDateFromBanglaDate = Integer.parseInt(
                        CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfBirthBangla[0])) + "";

                String convertedEnglishMonthFromBanglaMonth = Integer.parseInt(
                        CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfBirthBangla[1])) + "";

                String convertedEglishYearFromBanglaYear = Integer.parseInt(
                        CalenderBanglaInfo.getDigitEnglishFromBangla(dateOfBirthBangla[2])) + "";

                birthday = convertedEglishYearFromBanglaYear + "/" +
                        convertedEnglishMonthFromBanglaMonth + "/" +  convertedEnglishDateFromBanglaDate;
            }
        }
    }
}
