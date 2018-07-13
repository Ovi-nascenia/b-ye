package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopupEducationEdit extends AppCompatActivity {

    EditText subjectText, institutionText;
    LinearLayout educationalStatus;
    TextView educationTV;
    Button next;
    String constant, data, degreeValue = "", subjectValue = "", institutionValue, degreeData="";
    JSONObject educationObject;
    private SharePref sharePref;

    TextView educationalStatusLabel;
    ImageView back;

    private ArrayList<String> educationArray = new ArrayList<String>();
    private String[] educationName = new String[educationArray.size()];
    private ArrayList<String> educationConstant = new ArrayList<String>();
    private String[] educationConstantValue = new String[educationArray.size()];

    ProgressDialog progress;

    OkHttpClient client;
    private String loginUserReligion;
    private boolean isSignUp = false;
    private final static int EDUCATION_POPUP = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_edit_popup);

        progress = new ProgressDialog(PopupEducationEdit.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        client = new OkHttpClient();
        sharePref = new SharePref(PopupEducationEdit.this);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");

        subjectText = (EditText) findViewById(R.id.subject_text);
        institutionText = (EditText) findViewById(R.id.institution_text);
        educationalStatusLabel = (TextView) findViewById(R.id.educational_status_label);
        educationalStatus = (LinearLayout) findViewById(R.id.educationalStatus);
        educationTV = (TextView) findViewById(R.id.education_text_view);
        next = (Button) findViewById(R.id.next);

        try {
            JSONObject jsonObject = new JSONObject(constant);
            educationObject = jsonObject.getJSONObject("education_constant");

            for (int i = 0; i < educationObject.length(); i++) {
                educationConstant.add(educationObject.names().getString(i));
                educationArray.add((String) educationObject.get(educationObject.names().getString(i)));
            }
            educationName = educationArray.toArray(educationName);

            if(jsonObject.has("data")){
                JSONObject jsonData = jsonObject.getJSONObject("data").getJSONArray("educations_information").getJSONObject(0);
                institutionText.setText(jsonData.getString("institution"));
                subjectText.setText(jsonData.getString("subject"));
                degreeValue = jsonData.getInt("name") + "";
                degreeData = educationName[jsonData.getInt("name")];
                educationTV.setText(degreeData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                institutionValue = institutionText.getText().toString();
                subjectValue = subjectText.getText().toString();
                if (degreeValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার ডিগ্রীর নাম নির্বাচন করুন", Toast.LENGTH_LONG).show();
                    educationalStatusLabel.getParent().requestChildFocus(educationalStatusLabel, educationalStatusLabel);
                    return;
                }

                if (subjectValue.isEmpty()) {
                    Toast.makeText(getBaseContext(), "আপনার শিক্ষার বিষয় লিখুন", Toast.LENGTH_LONG).show();
                    educationalStatusLabel.getParent().requestChildFocus(educationalStatusLabel, educationalStatusLabel);
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("education_value", degreeValue);
                intent.putExtra("education_data", degreeData);
                intent.putExtra("subject", subjectText.getText().toString());
                intent.putExtra("institute", institutionText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        educationalStatus.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PopupEducationEdit.this, PopUpPersonalInfo.class);
                    intent.putExtra("data", "education");
                    intent.putExtra("constants", constant);
                    startActivityForResult(intent, EDUCATION_POPUP);
                }
            }
        );

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == EDUCATION_POPUP) {
                degreeData = data.getStringExtra("education_data");
                educationTV.setText(data.getStringExtra("education_data"));
                degreeValue = data.getIntExtra("education_value", 0) + "";
            }
        }
    }
}
