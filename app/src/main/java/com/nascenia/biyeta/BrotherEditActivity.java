package com.nascenia.biyeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nascenia.biyeta.activity.BirthDatePickerPopUpActivity;
import com.nascenia.biyeta.activity.PopUpFamilyInfoSecondPage;
import com.nascenia.biyeta.activity.PopUpPersonalInfo;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.newuserprofile.Brother;
import com.nascenia.biyeta.utils.CalenderBanglaInfo;
import com.squareup.okhttp.OkHttpClient;

import java.util.Calendar;

public class BrotherEditActivity extends AppCompatActivity {

    LinearLayout reject;
    public EditText nameBrother;
    public EditText designationBrother;
    public EditText institutionBrother;
    public EditText nameBrotherSpouse;
    public EditText designationBrotherSpouse;
    public EditText institutionBrotherSpouse;
    public LinearLayout spouse;
    public LinearLayout rejectSpouse;

    public TextView brotherAge;
    public TextView brotherOccupation;
    public TextView brotherProfessionalGroup;
    public TextView brotherMaritalStatus;
    public TextView brotherOcupationSpouse;
    public TextView brotherProfessionalGroupSpouse;
    public TextView tv_bhai;
    LinearLayout professonalStatusBrother;
    LinearLayout occupationStatusBrother;
    LinearLayout maritalStatusBrother;
    LinearLayout ageBrother;
    LinearLayout occupationStatusBrotherSpouse;
    LinearLayout professonalStatusBrotherSpouse;

    Button btnSave;
    public int age = 0;

    public String occupation = "";

    ProgressDialog progress;
    private SharePref sharePref;
    public String constant;
    OkHttpClient client;

    private final int AGE_REQUEST_CODE = 2;
    private final int MARITAL_STATUS_REQUEST_CODE = 7;
    private final int PROFESSIONAL_GROUP_REQUEST_CODE = 11;
    private final int PROFESSION_REQUEST_CODE = 12;
    private final int EDUCATION_REQUEST_CODE = 16;
    private final int SPOUSE_PROFESSIONAL_GROUP_REQUEST_CODE = 18;
    private final int SPOUSE_PROFESSION_REQUEST_CODE = 19;
    private int brotherOcupationValue, brotherProfessionalGroupValue, brotherOcupationSpouseValue, brotherMaritalStatusValue, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brother_edit);

        sharePref = new SharePref(BrotherEditActivity.this);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        id = intent.getIntExtra("id", 0);
        client = new OkHttpClient();

        progress = new ProgressDialog(BrotherEditActivity.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        nameBrother = (EditText) findViewById(R.id.name_brother);
        designationBrother = (EditText) findViewById(R.id.designation_brother);
        institutionBrother = (EditText) findViewById(R.id.institution_brother);
        brotherOccupation = (TextView) findViewById(R.id.profession_text_view_brother);
        brotherProfessionalGroup = (TextView) findViewById(R.id.profession_group_text_view_brother);
        brotherMaritalStatus = (TextView) findViewById(R.id.marital_text_view_brother);
        tv_bhai = findViewById(R.id.tv_bhai);
        // brotherAge = (TextView) view.findViewById(R.id.age_text_view_brother);
        brotherAge = (TextView) findViewById(R.id.age_text_view_brother);
        brotherOcupationSpouse = (TextView) findViewById(R.id.profession_text_view_brother_spouse);
        brotherProfessionalGroupSpouse = (TextView) findViewById(R.id.profession_group_text_view_brother_spouse);

        nameBrotherSpouse = (EditText) findViewById(R.id.name_brother_spouse);
        designationBrotherSpouse = (EditText) findViewById(R.id.designation_brother_spouse);
        institutionBrotherSpouse = (EditText) findViewById(R.id.institution_brother_spouse);


        reject = (LinearLayout) findViewById(R.id.reject);
        spouse = (LinearLayout) findViewById(R.id.spouse);

//        reject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = getAdapterPosition();
//                brotherCount.remove(position);
//                notifyItemRemoved(position);
//                removeBrotherItemCallBack.removeBrotherItem();
//            }
//        });

        occupationStatusBrother = (LinearLayout) findViewById(R.id.professonal_status_brother);
        occupationStatusBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(BrotherEditActivity.this, PopUpPersonalInfo.class);
                setIntent.putExtra("constants", constant);
                setIntent.putExtra("data", "profession");
                startActivityForResult(setIntent, PROFESSION_REQUEST_CODE);
            }
        });

        professonalStatusBrother = (LinearLayout) findViewById(R.id.professonal_group_status_brother);
        professonalStatusBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(BrotherEditActivity.this, PopUpPersonalInfo.class);
                setIntent.putExtra("constants", constant);
                setIntent.putExtra("data", "professional_group");
                startActivityForResult(setIntent, PROFESSIONAL_GROUP_REQUEST_CODE);
            }
        });

        maritalStatusBrother = (LinearLayout) findViewById(R.id.marital_status_brother);
        maritalStatusBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(BrotherEditActivity.this, PopUpPersonalInfo.class);
                setIntent.putExtra("constants", constant);
                setIntent.putExtra("data", "marital_status");
                startActivityForResult(setIntent, MARITAL_STATUS_REQUEST_CODE);
            }
        });

        ageBrother = (LinearLayout) findViewById(R.id.age_brother);
        ageBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BrotherEditActivity.this, PopUpFamilyInfoSecondPage.class);
                intent.putExtra("constants", constant);
                intent.putExtra("data", "age");
                startActivityForResult(intent, AGE_REQUEST_CODE);
            }
        });

        occupationStatusBrotherSpouse = (LinearLayout) findViewById(R.id.professonal_status_brother_spouse);
        occupationStatusBrotherSpouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(BrotherEditActivity.this, PopUpPersonalInfo.class);
                setIntent.putExtra("constants", constant);
                setIntent.putExtra("data", "profession");
                startActivityForResult(setIntent, SPOUSE_PROFESSION_REQUEST_CODE);
            }
        });

        professonalStatusBrotherSpouse = (LinearLayout) findViewById(R.id.professonal_group_status_brother_spouse);
        professonalStatusBrotherSpouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(BrotherEditActivity.this, PopUpPersonalInfo.class);
                setIntent.putExtra("constants", constant);
                setIntent.putExtra("data", "professional_group");
                startActivityForResult(setIntent, SPOUSE_PROFESSIONAL_GROUP_REQUEST_CODE);
            }
        });

        rejectSpouse = (LinearLayout) findViewById(R.id.reject_spouse);
        rejectSpouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                brotherProfessionalGroupSpouse.setText("");
                brotherOcupationSpouse.setText("");
                nameBrotherSpouse.setText("");
                designationBrotherSpouse.setText("");
                institutionBrotherSpouse.setText("");
                spouse.setVisibility(View.GONE);
            }
        });

        btnSave = findViewById(R.id.next);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameBrother.getText().toString().equalsIgnoreCase("")){

                }
                if(brotherAge.getText().toString().equalsIgnoreCase("")){

                }
                if(brotherOccupation.getText().toString().equalsIgnoreCase("")){

                }
                if(nameBrotherSpouse.getText().toString().equalsIgnoreCase("")){

                }
                if(brotherOcupationSpouse.getText().toString().equalsIgnoreCase("")){

                }

                Intent intent = getIntent();
                intent.putExtra("id", id);
                intent.putExtra("name_brother", nameBrother.getText().toString());
                intent.putExtra("age_brother", brotherAge.getText().toString());
                intent.putExtra("profession_brother_data", brotherOccupation.getText().toString());
                intent.putExtra("profession_brother_value", brotherOcupationValue);
                intent.putExtra("professional_group_brother_data", brotherProfessionalGroup.getText().toString());
                intent.putExtra("professional_group_brother_value", brotherProfessionalGroupValue);
                intent.putExtra("designation_brother", designationBrother.getText().toString());
                intent.putExtra("institute_brother", institutionBrother.getText().toString());
                intent.putExtra("marital_status_brother_data", brotherMaritalStatus.getText().toString());
                intent.putExtra("marital_status_brother_value", brotherMaritalStatusValue);
                intent.putExtra("name_brother_spouse", nameBrotherSpouse.getText().toString());
                intent.putExtra("profession_brother_spouse_data", brotherOcupationSpouse.getText().toString());
                intent.putExtra("profession_brother_spouse_value", brotherOcupationSpouseValue);
                intent.putExtra("professional_group_brother_spouse_data", brotherProfessionalGroupSpouse.getText().toString());
                intent.putExtra("professional_group_brother_spouse_value", brotherProfessionalGroupValue);
                intent.putExtra("designation_brother_spouse", designationBrotherSpouse.getText().toString());
                intent.putExtra("institute_brother_spouse", institutionBrotherSpouse.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        String siblingType = "", name = "", designation = "", institute = "", spouse = "", sOccupation = "", sProfessionalGroup = "", sDesignation = "", sInstitute = "";

        siblingType = "1";
        name = nameBrother.getText().toString();

        //  Toast.makeText(view.getContext(),name,Toast.LENGTH_LONG).show();

        designation = designationBrother.getText().toString();
        institute = institutionBrother.getText().toString();
        spouse = "";
        sOccupation = "";
        sProfessionalGroup = "";
        sDesignation = "";
        sInstitute = "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {

                if (data != null && !data.getStringExtra("age_data").equalsIgnoreCase(
                        "reject")) {
//                    Log.d("age: ", data.getStringExtra("age_data"));
                    brotherAge.setText(data.getStringExtra("age_data"));
                }
            }else if (requestCode == MARITAL_STATUS_REQUEST_CODE) {
                if (data != null && !data.getStringExtra("marital_status_data").equalsIgnoreCase(
                        "reject")) {
                    brotherMaritalStatus.setText(data.getStringExtra("marital_status_data"));
                    brotherMaritalStatusValue = data.getIntExtra("marital_status_value", 0);
                    if (data.getIntExtra("marital_status_value", 0) == 4){
                        spouse.setVisibility(View.VISIBLE);
                    } else {
                        brotherProfessionalGroupSpouse.setText("");
                        brotherOcupationSpouse.setText("");
                        nameBrotherSpouse.setText("");
                        designationBrotherSpouse.setText("");
                        institutionBrotherSpouse.setText("");
                        spouse.setVisibility(View.GONE);
                    }
                }
            }else if (requestCode == PROFESSION_REQUEST_CODE) {
                if (data != null && !data.getStringExtra("profession_data").equalsIgnoreCase(
                        "reject")) {
                    brotherOccupation.setText(data.getStringExtra("profession_data"));
                    brotherOcupationValue = data.getIntExtra("profession", 0);
                }
            }else if (requestCode == PROFESSIONAL_GROUP_REQUEST_CODE) {
                if (data != null && !data.getStringExtra("professional_group_data").equalsIgnoreCase(
                        "reject")) {
                    brotherProfessionalGroup.setText(data.getStringExtra("professional_group_data"));
                    brotherProfessionalGroupValue = data.getIntExtra("professional_group_value", 0);
                }
            }else if (requestCode == SPOUSE_PROFESSION_REQUEST_CODE) {
                if (data != null && !data.getStringExtra("profession_data").equalsIgnoreCase(
                        "reject")) {
                    brotherOcupationSpouse.setText(data.getStringExtra("profession_data"));
                    brotherOcupationSpouseValue = data.getIntExtra("profession_value", 0);
                }
            }else if (requestCode == SPOUSE_PROFESSIONAL_GROUP_REQUEST_CODE) {
                if (data != null && !data.getStringExtra("professional_group_data").equalsIgnoreCase(
                        "reject")) {
                    brotherProfessionalGroupSpouse.setText(data.getStringExtra("professional_group_data"));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("resumed");
    }
}
