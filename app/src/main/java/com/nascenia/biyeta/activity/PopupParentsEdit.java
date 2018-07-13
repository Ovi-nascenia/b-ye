package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.newuserprofile.Father;
import com.nascenia.biyeta.model.newuserprofile.Mother;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopupParentsEdit extends AppCompatActivity {

    private ArrayList<String> professonalGroupArray = new ArrayList<String>();
    private String[] professonalGroupName = new String[professonalGroupArray.size()];
    private ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    private String[] professonalGroupConstantValue = new String[professonalGroupArray.size()];

    private ArrayList<String> occupationArray = new ArrayList<String>();
    private String[] occupationName = new String[occupationArray.size()];
    private ArrayList<String> occupationConstant = new ArrayList<String>();
    private String[] occupationConstantValue = new String[occupationArray.size()];
    LinearLayout professionFatherStatus, professionalGroupFatherStatus;

    TextView professionFatherTV, professionalGroupFatherTV;

    EditText nameFather, designationFather, institutionFather;

    Button next;

    ImageView back;

    String constant, data, occupationOfFather  = "", professionOfFather = "";

    JSONObject professionalGroupObject, occupationObject;

    TextView labelFather;

    private ArrayList<String> fatherOccupationArray = new ArrayList<String>();
    private String[] fatherOccupationName = new String[occupationArray.size()];
    private ArrayList<String> fatherOccupationConstant = new ArrayList<String>();

    ProgressDialog progress;
    OkHttpClient client;
    private SharePref sharePref;
    private final static int PROFESSION_REQUEST = 1001;
    private final static int PROFESSIONAL_GROUP_REQUEST = 1002;
    private String profession_value, profession_group_value;
    private View toolbar;
    private Father father = null;
    private Mother mother = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_parents_edit);

        sharePref = new SharePref(PopupParentsEdit.this);
        toolbar = findViewById(R.id.toolbar);

        progress = new ProgressDialog(PopupParentsEdit.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        client = new OkHttpClient();

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
        data = intent.getStringExtra("data");
        if(data.equalsIgnoreCase("father")){
            father = (Father) intent.getSerializableExtra("father");
        }else{
            mother = (Mother) intent.getSerializableExtra("mother");
        }

        try {
            JSONObject jsonObject = new JSONObject(constant);

            occupationObject = jsonObject.getJSONObject("occupation_constant");
            professionalGroupObject = jsonObject.getJSONObject("professional_group_constant");

            Log.i("occupation", occupationObject.toString());

            for (int i = 0; i < occupationObject.length(); i++) {
                occupationConstant.add(occupationObject.names().getString(i));
                occupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));

            }

            occupationName = occupationArray.toArray(occupationName);

            for (int i = 0; i < occupationObject.length() - 1; i++) {
                fatherOccupationConstant.add(occupationObject.names().getString(i));
                fatherOccupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
            }

            fatherOccupationName = fatherOccupationArray.toArray(fatherOccupationName);

            for (int i = 0; i < professionalGroupObject.length(); i++) {
                professonalGroupConstant.add(professionalGroupObject.names().getString(i));
                professonalGroupArray.add((String) professionalGroupObject.get(professionalGroupObject.names().getString(i)));
            }

            professonalGroupName = professonalGroupArray.toArray(professonalGroupName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data != null) {
                    finish();
                }
            }
        });

        nameFather = (EditText) findViewById(R.id.name_father);
        designationFather = (EditText) findViewById(R.id.designation_father);
        institutionFather = (EditText) findViewById(R.id.institution_father);
        labelFather = (TextView) findViewById(R.id.label_father);
        professionFatherStatus = (LinearLayout) findViewById(R.id.professonalalStatusFather);
        professionalGroupFatherStatus = (LinearLayout) findViewById(R.id.professonalGroupStatusFather);
        professionFatherTV = (TextView) findViewById(R.id.profession_text_view_father);
        professionalGroupFatherTV = (TextView) findViewById(R.id.profession_group_text_view_father);
        if(data.equalsIgnoreCase("mother")){
            labelFather.setText(getResources().getString(R.string.mother_text));
            nameFather.setHint(R.string.mother_name);
            TextView tvType = findViewById(R.id.type);
            tvType.setText(getResources().getString(R.string.mother_text));
        }

        professionFatherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.equalsIgnoreCase("father"))
                    startActivityForResult(new Intent(PopupParentsEdit.this, PopUpPersonalInfo.class).putExtra("constants", constant).putExtra("data", "father_profession"), PROFESSION_REQUEST);
                else
                    startActivityForResult(new Intent(PopupParentsEdit.this, PopUpPersonalInfo.class).putExtra("constants", constant).putExtra("data", "mother_profession"), PROFESSION_REQUEST);
            }
        });

        professionalGroupFatherStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PopupParentsEdit.this, PopUpPersonalInfo.class).putExtra("constants", constant).putExtra("data", "father_professional_group"), PROFESSIONAL_GROUP_REQUEST);
            }
        });

        if(data.equalsIgnoreCase("father")){
            setFatherData(father);
        }else{
            setMotherData(mother);
        }
    }

    private void setMotherData(Mother mother) {
        nameFather.setText(mother.getName().toString());
        String occupa = mother.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            professionFatherTV.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = getOccupationValue(occupation[0]);
        }

        if(occupation.length > 1) {
            professionalGroupFatherTV.setText(occupation[1]);
            profession_group_value = getProfessionValue(occupation[1]);
        }

        designationFather.setText(mother.getDesignation().toString());
        institutionFather.setText(mother.getInstitute().toString());
    }

    private void setFatherData(Father father) {
        nameFather.setText(father.getName().toString());
        String occupa = father.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            professionFatherTV.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = getOccupationValue(occupation[0]);
        }

        if(occupation.length > 1) {
            professionalGroupFatherTV.setText(occupation[1]);
            profession_group_value = getProfessionValue(occupation[1]);
        }

        designationFather.setText(father.getDesignation().toString());
        institutionFather.setText(father.getInstitute().toString());
    }

    private String getOccupationValue(String occupation){
        int index = -1;
        for(int i = 0; i < occupationArray.size(); i++){
            if(occupationArray.get(i).equalsIgnoreCase(occupation)) {
                index = i;
                break;
            }
        }
        return index > -1 ? occupationConstant.get(index) : "";
    }

    private String getProfessionValue(String profession) {
        int index = -1;
        for(int i = 0; i < professonalGroupArray.size(); i++){
            if(professonalGroupArray.get(i).equalsIgnoreCase(profession)) {
                index = i;
                break;
            }
        }
        return index > -1 ? professonalGroupConstant.get(index) : "";
    }


    private void sendData() {
        if (nameFather.getText().toString().isEmpty()) {
            if(data.equalsIgnoreCase("father")) {
                Toast.makeText(getBaseContext(), getString(R.string.write_your_father_name),
                        Toast.LENGTH_LONG).show();
                labelFather.getParent().
                        requestChildFocus(labelFather, labelFather);
                return;
            }
        }

        occupationOfFather = professionFatherTV.getText().toString();

        if (occupationOfFather.isEmpty()) {
            if(data.equalsIgnoreCase("father")) {
                Toast.makeText(getBaseContext(), getString(R.string.choose_your_father_occupation),
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(), getString(R.string.choose_your_mother_occupation), Toast.LENGTH_LONG).show();
            }
            professionFatherStatus.getParent().
                    requestChildFocus(professionFatherStatus, professionFatherStatus);
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("profession_data", professionFatherTV.getText().toString());
        intent.putExtra("profession_value", profession_value);
        intent.putExtra("professional_group_data", professionalGroupFatherTV.getText().toString());
        intent.putExtra("professional_group_value", profession_group_value);
        intent.putExtra("name", nameFather.getText().toString());
        intent.putExtra("designation", designationFather.getText().toString());
        intent.putExtra("institute", institutionFather.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == PROFESSION_REQUEST){
                professionFatherTV.setText(data.getStringExtra("profession_data"));
                profession_value = data.getStringExtra("profession_value");
            }else if(requestCode == PROFESSIONAL_GROUP_REQUEST){
                professionalGroupFatherTV.setText(data.getStringExtra("professional_group_data"));
                profession_group_value = data.getStringExtra("professional_group_value");
            }
        }
    }
}
