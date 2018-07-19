package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.OtherViewAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.newuserprofile.Dada;
import com.nascenia.biyeta.model.newuserprofile.Fufa;
import com.nascenia.biyeta.model.newuserprofile.Kaka;
import com.nascenia.biyeta.model.newuserprofile.Khalu;
import com.nascenia.biyeta.model.newuserprofile.Mama;
import com.nascenia.biyeta.model.newuserprofile.Nana;
import com.nascenia.biyeta.model.newuserprofile.Other;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OthersEditActivity extends AppCompatActivity {

    private static final int PROFESSIONAL_GROUP_REQ_CODE = 1, RELATION_REQ_CODE = 2, OCCUPATION_REQ_CODE = 3;
    OkHttpClient client;
    String responseOther = "";
    int otherCount = 0;
    private List<Integer> otherList = new ArrayList<>();

    public static String constant;

    LinearLayout buttonOther;
    Button buttonNext;
    OtherViewAdapter otherViewAdapter;
    ImageView back;

    ProgressDialog progress;
    private SharePref sharePref;
    private boolean isSignUp = false;

    public EditText nameOther;
    public EditText designationOther;
    public EditText institutionOther;
    public EditText relationName;
    public LinearLayout rootLayout;
    public LinearLayout detailsInfoFieldsRootLayout;
    public TextView otherOccupation;
    public TextView otherProfessionalGroup;
    public  TextView otherMaritalStatus;
    public  TextView otherRelationalStatus;
    LinearLayout professonalStatusOther;
    LinearLayout occupationStatusOther;
    LinearLayout relationalStatusOther;
    private int relation_value, profession_value, professional_group_value, id;
    private Toolbar toolbar;
    private Dada dada;
    private String strDataUpdate;

    private ArrayList<String> professonalGroupArray = new ArrayList<String>();
    private String[] professonalGroupName = new String[professonalGroupArray.size()];
    private ArrayList<String> professonalGroupConstant = new ArrayList<String>();
    private String[] professonalGroupConstantValue = new String[professonalGroupArray.size()];

    private ArrayList<String> occupationArray = new ArrayList<String>();
    private String[] occupationName = new String[occupationArray.size()];
    private ArrayList<String> occupationConstant = new ArrayList<String>();
    private String[] occupationConstantValue = new String[occupationArray.size()];

    private JSONObject professionalGroupObject, occupationObject;
    private Other other;
    private Nana nana;
    private Kaka kaka;
    private Khalu khalu;
    private Mama mama;
    private Fufa fupa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_edit);

        sharePref = new SharePref(OthersEditActivity.this);
        toolbar = findViewById(R.id.toolbar);

        final Intent intent = getIntent();
        constant = intent.getStringExtra("constants");
//        id = getIntent().getIntExtra("id", 0);
        strDataUpdate = intent.getStringExtra("data");

        // Toast.makeText(RegistrationFamilyInfoSecondPage.this,constant,Toast.LENGTH_LONG).show();
        client = new OkHttpClient();

        progress = new ProgressDialog(OthersEditActivity.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        buttonNext = (Button) findViewById(R.id.next);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseOther = "";
                if(otherRelationalStatus.getText().toString().length() == 0)
                {
                    Toast.makeText(getBaseContext(),
                            R.string.choose_realtion_message,
                            Toast.LENGTH_LONG).show();
                    otherRelationalStatus.getParent().
                            requestChildFocus(otherRelationalStatus, otherRelationalStatus);

                    return;
                }

                if(nameOther.getText().toString().length() == 0)
                {
                    Toast.makeText(getBaseContext(),
                            "আপনার " + otherRelationalStatus.getText().toString() + "র "
                                    + getString(R.string.write_name_message),
                            Toast.LENGTH_LONG).show();
                    otherRelationalStatus.getParent().
                            requestChildFocus(otherRelationalStatus, otherRelationalStatus);

                    return;
                }
                if(otherOccupation.getText().toString().length() == 0)
                {
                    Toast.makeText(getBaseContext(),
                            "আপনার " + otherRelationalStatus.getText().toString() + "র "
                                    + getString(R.string.select_occupation_message),
                            Toast.LENGTH_LONG).show();
                    otherOccupation.getParent().
                            requestChildFocus(otherOccupation, otherOccupation);

                    return;
                }

                Intent intent = getIntent();
                intent.putExtra("id", id);
                if(relation_value == 9){
                    intent.putExtra("relation_data", relationName.getText().toString());
                }else {
                    intent.putExtra("relation_data", otherRelationalStatus.getText().toString());
                }
                intent.putExtra("relation_value", relation_value);
                intent.putExtra("name", nameOther.getText().toString());
                intent.putExtra("profession_data", otherOccupation.getText().toString());
                intent.putExtra("profession_value", profession_value);
                intent.putExtra("professional_group_data", otherProfessionalGroup.getText().toString());
                intent.putExtra("professional_group_value", professional_group_value);
                intent.putExtra("designation", designationOther.getText().toString());
                intent.putExtra("institute", institutionOther.getText().toString());
                setResult(RESULT_OK, intent);
                finish();

                /*String response = new StringBuilder().append("{")
                        .append("\"name\":")
                        .append("\"")
                        .append(nameOther.getText().toString())
                        .append("\"")
                        .append(",")
                        .append("\"relation\":")
                        .append("\"")
//                        .append(PopUpFamilyInfoSecondPage.relationStatusArrayOther.get(i))
                        .append("\"")
                        .append(",")
                        .append("\"occupation\":")
                        .append("\"")
//                        .append(PopUpFamilyInfoSecondPage.occupationArrayOther.get(i)!=null?PopUpFamilyInfoSecondPage.occupationArrayOther.get(i):"")
                        .append("\"")
                        .append(",")
                        .append("\"professional_group\":")
                        .append("\"")
//                        .append(PopUpFamilyInfoSecondPage.professionalGroupArrayOther.get(i)!=null?PopUpFamilyInfoSecondPage.professionalGroupArrayOther.get(i):"")
                        .append("\"")
                        .append(",")
                        .append("\"designation\":")
                        .append("\"")
                        .append(designationOther.getText().toString())
                        .append("\"")
                        .append(",")
                        .append("\"institute\":")
                        .append("\"")
                        .append(institutionOther.getText().toString())
                        .append("\"")
                        .append("}")
                        .append(",")
                        .toString();

                responseOther = responseOther + response;*/

            }
        });

        nameOther = (EditText) findViewById(R.id.name_other);
        designationOther = (EditText) findViewById(R.id.designation_other);
        institutionOther = (EditText) findViewById(R.id.institution_other);
        relationName = findViewById(R.id.relation_name);
        otherOccupation = (TextView) findViewById(R.id.profession_text_view_other);
        otherProfessionalGroup = (TextView) findViewById(R.id.profession_group_text_view_other);
        otherRelationalStatus = (TextView) findViewById(R.id.relation_text_view_other);
        detailsInfoFieldsRootLayout = (LinearLayout) findViewById(R.id.details_info_fields_root_layout);

        occupationStatusOther = (LinearLayout) findViewById(R.id.professonal_status_other);
        occupationStatusOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(OthersEditActivity.this, PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant", constant);
                setIntent.putExtra("data", "profession");
//                setIntent.putExtra("position", getAdapterPosition());
                startActivityForResult(setIntent, OCCUPATION_REQ_CODE);
            }
        });

        professonalStatusOther = (LinearLayout) findViewById(R.id.professonal_group_status_other);
        professonalStatusOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(OthersEditActivity.this, PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant", constant);
                setIntent.putExtra("data", "professional_group");
//                setIntent.putExtra("position", getAdapterPosition());
                startActivityForResult(setIntent, PROFESSIONAL_GROUP_REQ_CODE);
            }
        });

        relationalStatusOther = (LinearLayout) findViewById(R.id.relational_status_other);
        relationalStatusOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(OthersEditActivity.this, PopUpFamilyInfoSecondPage.class);
                setIntent.putExtra("constant", constant);
                setIntent.putExtra("data", "relation");
//                setIntent.putExtra("position", getAdapterPosition());
                startActivityForResult(setIntent, RELATION_REQ_CODE);

            }
        });

        otherRelationalStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (detailsInfoFieldsRootLayout.getVisibility() == View.GONE)
                    detailsInfoFieldsRootLayout.setVisibility(View.VISIBLE);
//                else
//                    detailsInfoFieldsRootLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (detailsInfoFieldsRootLayout.getVisibility() == View.GONE)
                    detailsInfoFieldsRootLayout.setVisibility(View.VISIBLE);
//                else
//                    detailsInfoFieldsRootLayout.setVisibility(View.VISIBLE);
            }
        });

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(constant);
//            if(strDataUpdate.equalsIgnoreCase("brother") || strDataUpdate.equalsIgnoreCase("sister")) {
                professionalGroupObject = jsonObject.getJSONObject("professional_group_constant");
                for (int i = 0; i < professionalGroupObject.length(); i++) {
                    professonalGroupConstant.add(professionalGroupObject.names().getString(i));
                    professonalGroupArray.add((String) professionalGroupObject.get(professionalGroupObject.names().getString(i)));
                }
//            }

//            if(strDataUpdate.equalsIgnoreCase("brother") || strDataUpdate.equalsIgnoreCase("sister")) {
                occupationObject = jsonObject.getJSONObject("occupation_constant");
                for (int i = 0; i < occupationObject.length(); i++) {
                    occupationConstant.add(occupationObject.names().getString(i));
                    occupationArray.add((String) occupationObject.get(occupationObject.names().getString(i)));
                }
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(strDataUpdate != null) {
            if(strDataUpdate.equalsIgnoreCase("dada")) {
                dada = getIntent().getParcelableExtra("dada_info");
                setViewWithDada(dada);
            }else if(strDataUpdate.equalsIgnoreCase("nana")) {
                nana = getIntent().getParcelableExtra("nana_info");
                setViewWithNana(nana);
            }if(strDataUpdate.equalsIgnoreCase("mama")) {
                mama = getIntent().getParcelableExtra("mama_info");
                setViewWithMama(mama);
            }if(strDataUpdate.equalsIgnoreCase("khalu")) {
                khalu = getIntent().getParcelableExtra("khalu_info");
                setViewWithKhalu(khalu);
            }if(strDataUpdate.equalsIgnoreCase("kaka")) {
                kaka = getIntent().getParcelableExtra("kaka_info");
                setViewWithKaka(kaka);
            }if(strDataUpdate.equalsIgnoreCase("fupa")) {
                fupa = getIntent().getParcelableExtra("fupa_info");
                setViewWithFupa(fupa);
            }else if(strDataUpdate.equalsIgnoreCase("other")){
                other = getIntent().getParcelableExtra("other_info");
                setViewWithOther(other);
            }
        }
    }

    private void setViewWithFupa(Fufa fupa) {
        id = fupa.getId();
        relationName.setText(fupa.getRelation().toString());
        otherRelationalStatus.setText(fupa.getRelation().toString());
        relation_value = fupa.getRelationId();
        nameOther.setText(fupa.getName().toString());
        String occupa = fupa.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            otherOccupation.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupation[0]));
        }
        if(occupation.length > 1) {
            otherProfessionalGroup.setText(occupation[1]);
            professional_group_value = Integer.parseInt(getProfessionValue(occupation[1]));
        }
        designationOther.setText(fupa.getDesignation().toString());
        institutionOther.setText(fupa.getInstitute().toString());
    }

    private void setViewWithKaka(Kaka kaka) {
        id = kaka.getId();
        relationName.setText(kaka.getRelation().toString());
        otherRelationalStatus.setText(kaka.getRelation().toString());
        relation_value = kaka.getRelationId();
        nameOther.setText(kaka.getName().toString());
        String[] occupa = null;
        boolean hasProGroup = false;
        if(kaka.getOccupation().contains("\\("))
            hasProGroup = true;
        occupa = kaka.getOccupation().split(" \\(");
//        occupa = occupa.replace(")", "");
//        String[] occupation = occupa.split(" ");
        if(occupa.length > 0) {
            otherOccupation.setText(occupa.length > 0 ? occupa[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupa[0]));
        }
        if(occupa.length > 1 && hasProGroup) {
            otherProfessionalGroup.setText(occupa[1].replace(")", ""));
            professional_group_value = Integer.parseInt(getProfessionValue(occupa[1]));
        }
        designationOther.setText(kaka.getDesignation().toString());
        institutionOther.setText(kaka.getInstitute().toString());
    }

    private void setViewWithMama(Mama mama) {
        id = mama.getId();
        relationName.setText(mama.getRelation().toString());
        otherRelationalStatus.setText(mama.getRelation().toString());
        relation_value = mama.getRelationId();
        nameOther.setText(mama.getName().toString());
        String occupa = mama.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            otherOccupation.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupation[0]));
        }
        if(occupation.length > 1) {
            otherProfessionalGroup.setText(occupation[1]);
            professional_group_value = Integer.parseInt(getProfessionValue(occupation[1]));
        }
        designationOther.setText(mama.getDesignation().toString());
        institutionOther.setText(mama.getInstitute().toString());
    }

    private void setViewWithKhalu(Khalu khalu) {
        id = khalu.getId();
        relationName.setText(khalu.getRelation().toString());
        otherRelationalStatus.setText(khalu.getRelation().toString());
        relation_value = khalu.getRelationId();
        nameOther.setText(khalu.getName().toString());
        String[] occupa = null;
        boolean hasProGroup = false;
        occupa = khalu.getOccupation().split(" \\(");
        if(occupa.length>1)
            hasProGroup = true;
        if(occupa.length > 0) {
            otherOccupation.setText(occupa.length > 0 ? occupa[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupa[0]));
        }
        if(occupa.length > 1 && hasProGroup) {
            otherProfessionalGroup.setText(occupa[1].replace(")", ""));
            professional_group_value = Integer.parseInt(getProfessionValue(occupa[1]));
        }
        designationOther.setText(khalu.getDesignation().toString());
        institutionOther.setText(khalu.getInstitute().toString());
    }

    private void setViewWithNana(Nana nana) {
        id = nana.getId();
        relationName.setText(nana.getRelation().toString());
        otherRelationalStatus.setText(nana.getRelation().toString());
        relation_value = nana.getRelationId();
        nameOther.setText(nana.getName().toString());
        String occupa = nana.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            otherOccupation.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupation[0]));
        }
        if(occupation.length > 1) {
            otherProfessionalGroup.setText(occupation[1]);
            professional_group_value = Integer.parseInt(getProfessionValue(occupation[1]));
        }
        designationOther.setText(nana.getDesignation().toString());
        institutionOther.setText(nana.getInstitute().toString());
    }

    private void setViewWithDada(Dada dada) {
        id = dada.getId();
        relationName.setText(dada.getRelation().toString());
        otherRelationalStatus.setText(dada.getRelation().toString());
        relation_value = dada.getRelationId();
        nameOther.setText(dada.getName().toString());
        String occupa = dada.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            otherOccupation.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupation[0]));
        }
        if(occupation.length > 1) {
            otherProfessionalGroup.setText(occupation[1]);
            professional_group_value = Integer.parseInt(getProfessionValue(occupation[1]));
        }
        designationOther.setText(dada.getDesignation().toString());
        institutionOther.setText(dada.getInstitute().toString());
    }

    private void setViewWithOther(Other other) {
        id = other.getId();
        relationName.setText(other.getRelation().toString());
        otherRelationalStatus.setText(getString(R.string.other));
//        intent.putExtra("relation_data", otherRelationalStatus.getText().toString());
        relation_value = other.getRelationId();
        if(relation_value == 9)
            relationName.setVisibility(View.VISIBLE);
        else
            relationName.setVisibility(View.GONE);
        nameOther.setText(other.getName().toString());
        String occupa = other.getOccupation().replaceAll(" \\(", " ");
        occupa = occupa.replace(")", "");
        String[] occupation = occupa.split(" ");
        if(occupation.length > 0) {
            otherOccupation.setText(occupation.length > 0 ? occupation[0] : "");
            profession_value = Integer.parseInt(getOccupationValue(occupation[0]));
        }
        if(occupation.length > 1) {
            otherProfessionalGroup.setText(occupation[1]);
            professional_group_value = Integer.parseInt(getProfessionValue(occupation[1]));
        }
        designationOther.setText(other.getDesignation().toString());
        institutionOther.setText(other.getInstitute().toString());
    }

    private String getProfessionValue(String profession) {
        int index = -1;
        for(int i = 0; i < professonalGroupArray.size(); i++){
            if(professonalGroupArray.get(i).equalsIgnoreCase(profession.replace(")", ""))) {
                index = i;
                break;
            }
        }
        return index > -1 ? professonalGroupConstant.get(index) : "";
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                relation_value = Integer.parseInt(data.getStringExtra("relation_value"));
                if (relation_value == 9)
                    relationName.setVisibility(View.VISIBLE);
                else
                    relationName.setVisibility(View.GONE);
                otherRelationalStatus.setText(data.getStringExtra("relation_data"));

            }else if(requestCode == 3){
                otherOccupation.setText(data.getStringExtra("profession_data"));
                profession_value = Integer.parseInt(data.getStringExtra("profession_value"));
            }else if(requestCode == 1){
                otherProfessionalGroup.setText(data.getStringExtra("professional_group_data"));
                professional_group_value = Integer.parseInt(data.getStringExtra("professional_group_value"));
            }
        }
    }
}
