package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.newuserprofile.Address;
import com.nascenia.biyeta.model.newuserprofile.PersonalInformation;
import com.nascenia.biyeta.model.newuserprofile.ProfileLivingIn;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrationUserAddressInformation extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout villageHouseLayout, presentCountryLayout, permanentCountryLayout,
            abroadTypeLayout, presentDistrictLayout, permanentDistrictLayout, permanentAddressLayout;
    private AlertDialog.Builder districtListDialog, presentCountryListDialog,
            permanentCountryListDialog, countryListDialog;

    private TextView villageDistrictNameTextView, presentCountryTextView, permanentCountryTextView,
            abroadTypeTextView, abroadTypeStatusTitleTextView, presentDistrictTextView, permanentDistrictTextView;

    private LinkedHashMap<Integer, String> disLinkedHashMap = new LinkedHashMap<Integer, String>();
    private String[] districtList;
    private int districtCounter = 0, countryCounter = 0;
    private Integer districtKey = null,
            presentCountryKey = null,
            permanentCountryKey = null;

    private EditText presentAddressEditext, permanentAddressEditext;
    private CheckBox permanentAddressCheckbox, addressBangldeshCheckbox, addressAbroadCheckbox;

    private String currentLivingLocationStatus = "", selectedAbroadTypeNumber = "";

    private Button nextBtn;

    private LinkedHashMap<Integer, String> countryLinkedHashMap = new LinkedHashMap<Integer, String>();
    private String[] countryNameArray;
    private int bdIndex;

    private int countryIndicator, distIndicator;
    private String presentCountryCode = "", permanentCountryCode = "";
    ImageView back;

    private ProgressDialog progress;

    SharePref sharePref;
    String data = "", strDataForUpdate = "";
    private String abroadStatusTypeArray[] = {"শিক্ষার্থী", "ওয়ার্ক পারমিট", "স্থায়ী বাসিন্দা", "নাগরিক", "প্রক্রিয়াধীন"};
    private boolean isSignUp = false;
    Toolbar toolbar;

    private ProfileLivingIn mProfileLivingIn;
    private Address mAddress;
    private PersonalInformation mPersonalInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_address_information);
        toolbar = findViewById(R.id.toolbar);
        initView();
        prePareDistrictListDialog();
        prepareCountryListDialog();

        progress = new ProgressDialog(RegistrationUserAddressInformation.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        sharePref = new SharePref(RegistrationUserAddressInformation.this);

       /* villageHouseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                villageHouseDistrictListDialog.create();
                villageHouseDistrictListDialog.show();
            }
        });*/

        data = getIntent().getExtras().getString("constants");
        strDataForUpdate = getIntent().getExtras().getString("data");
        mProfileLivingIn =
                (ProfileLivingIn) getIntent().getExtras().getSerializable("profile_living_in");
        mAddress = (Address) getIntent().getExtras().getSerializable("address");
        mPersonalInformation = (PersonalInformation) getIntent().getExtras().getSerializable("personal_info");
        if(strDataForUpdate != null){
//            toolbar.setVisibility(View.GONE);
//            TextView tvTitle = findViewById(R.id.address_title_for_update);
//            tvTitle.setVisibility(View.VISIBLE);
            nextBtn.setText("সংরক্ষণ করুন ");
            setViewWithData();
        }
        isSignUp = getIntent().getBooleanExtra("isSignUp", false);
//        if (data != null && data.length() > 0) {
//            try {
//                JSONObject json = new JSONObject(data);
//                if(json.has("data"))
//                {
//                    setViewWithData(json);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

    }

    private void setViewWithData() {
//        intent.putExtra("home_town_data", getDistrictCode(villageDistrictNameTextView.getText().toString()));
        villageDistrictNameTextView.setText(mProfileLivingIn.getLocation());
//        intent.putExtra("home_town_value", villageDistrictNameTextView.getText().toString());
//        intent.putExtra("residence", currentLivingLocationStatus);
        if(mProfileLivingIn.getCountry() != null && mProfileLivingIn.getCountry().equalsIgnoreCase(getString(R.string.bangladesh))){
            addressBangldeshCheckbox.setChecked(true);
            addressAbroadCheckbox.setChecked(false);
            addressBangldeshCheckboxAction();
        }else{
            addressBangldeshCheckbox.setChecked(false);
            addressAbroadCheckbox.setChecked(true);
            addressAbroadCheckboxAction();
        }
//        intent.putExtra("living_abroad_data", abroadTypeTextView.getText().toString());
        abroadTypeTextView.setText(mPersonalInformation.getLivingAbroadStatus());
//        intent.putExtra("living_abroad_value", selectedAbroadTypeNumber);
        selectedAbroadTypeNumber = (Arrays.asList(abroadStatusTypeArray).indexOf(mPersonalInformation.getLivingAbroadStatus()) + 1)+ "";
//        intent.putExtra("same_address", permanentAddressCheckbox.isChecked() ? 1 : 0);
        permanentAddressCheckbox.setChecked(mAddress.getSameAddress());
        if(mAddress.getSameAddress()){
            permanentAddressLayout.setVisibility(View.GONE);
        }else{
            permanentAddressLayout.setVisibility(View.VISIBLE);
        }
//        intent.putExtra("present_address", presentAddressEditext.getText().toString().trim());
        presentAddressEditext.setText(mAddress.getPresentAddress().getAddress());
//        intent.putExtra("present_country_value", presentCountryCode);
        presentCountryCode = getCountryCode(mAddress.getPresentAddress().getCountry());
        if(presentCountryCode.equalsIgnoreCase("bd")){
            presentDistrictLayout.setVisibility(View.VISIBLE);
        }else{
            presentDistrictLayout.setVisibility(View.GONE);
        }
//        intent.putExtra("present_country_data", presentCountryTextView.getText().toString());
        presentCountryTextView.setText(mAddress.getPresentAddress().getCountry());
//        intent.putExtra("present_district_data", presentDistrictTextView.getText().toString());
        presentDistrictTextView.setText(mAddress.getPresentAddress().getDistrict());
//        intent.putExtra("present_district_value", getDistrictCode(presentDistrictTextView.getText().toString()));
//        intent.putExtra("permanent_address", permanentAddressEditext.getText().toString().trim());
        permanentAddressEditext.setText(mAddress.getPermanentAddress().getAddress());
//        intent.putExtra("permanent_country_value", permanentCountryCode);
        permanentCountryCode = getCountryCode(mAddress.getPermanentAddress().getCountry());
        if(permanentCountryCode.equalsIgnoreCase("bd")){
            permanentDistrictLayout.setVisibility(View.VISIBLE);
        }else{
            permanentDistrictLayout.setVisibility(View.GONE);
        }
//        intent.putExtra("permanent_country_data", permanentCountryTextView.getText().toString());
        permanentCountryTextView.setText(mAddress.getPermanentAddress().getCountry());
//        intent.putExtra("permanent_district_data", permanentDistrictTextView.getText().toString());
        permanentDistrictTextView.setText(mAddress.getPermanentAddress().getDistrict());
//        intent.putExtra("permanent_district_value", getDistrictCode(permanentDistrictTextView.getText().toString()));
    }

    private void setViewWithData(JSONObject json) {

        try {
            JSONObject jsonData = json.getJSONObject("data");
            villageDistrictNameTextView.setText(disLinkedHashMap.get(jsonData.get("home_town")));
            if(jsonData.getString("residence").equalsIgnoreCase("BD")){
                addressBangldeshCheckbox.setChecked(true);
                addressAbroadCheckbox.setChecked(false);
                addressBangldeshCheckboxAction();
            }else{
                addressAbroadCheckbox.setChecked(true);
                addressBangldeshCheckbox.setChecked(false);
                addressAbroadCheckboxAction();
                selectedAbroadTypeNumber = jsonData.getString("living_abroad");
                abroadTypeTextView.setText(abroadStatusTypeArray[Integer.parseInt(selectedAbroadTypeNumber)-1]);
            }
            JSONArray jsonArray = jsonData.getJSONArray("address");
            if (jsonData.getBoolean("same_address")) {
                permanentAddressCheckbox.setChecked(true);
                permanentAddressLayout.setVisibility(View.GONE);
            } else {
                permanentAddressCheckbox.setChecked(false);
                permanentAddressLayout.setVisibility(View.VISIBLE);
            }
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(Locale.getISOCountries()));
            for(int i = 0; i <jsonArray.length(); i++) {
                if(jsonArray.getJSONObject(i).getInt("address_type")==1) {
                    presentAddressEditext.setText(jsonArray.getJSONObject(i).getString("address"));
                    presentCountryCode = jsonArray.getJSONObject(i).getString("country");
                    presentCountryTextView.setText(countryNameArray[arrayList.indexOf(jsonArray.getJSONObject(i).getString("country"))]);
                    if(jsonArray.getJSONObject(i).getString("district").length()>0){
                        presentDistrictTextView.setText(disLinkedHashMap.get(Integer.parseInt(jsonArray.getJSONObject(i).getString("district"))));
                    }
                }else{
                    if (jsonData.getBoolean("same_address")) {
                        permanentAddressCheckbox.setChecked(true);
                        permanentAddressLayout.setVisibility(View.GONE);
                    } else {
                        permanentAddressCheckbox.setChecked(false);
                        permanentAddressLayout.setVisibility(View.VISIBLE);
                        permanentAddressEditext.setText(jsonArray.getJSONObject(i).getString("address"));
                        permanentCountryCode = jsonArray.getJSONObject(i).getString("country");
                        permanentCountryTextView.setText(countryNameArray[arrayList.indexOf(jsonArray.getJSONObject(i).getString("country"))]);
                        if(jsonArray.getJSONObject(i).getString("district").length()>0){
                            System.out.println(disLinkedHashMap.get(jsonArray.getJSONObject(i).getString("district")));
                            permanentDistrictTextView.setText(disLinkedHashMap.get(Integer.parseInt(jsonArray.getJSONObject(i).getString("district"))));
                        }
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void prepareCountryListDialog() {
        countryNameArray = new String[Locale.getISOCountries().length];

        // String[] locales = Locale.getISOCountries();

        for (String countryCode : Locale.getISOCountries()) {

            Locale obj = new Locale("", countryCode);
            countryNameArray[countryCounter] = obj.getDisplayCountry();
            if(countryCode.equalsIgnoreCase("bd"))
                bdIndex = countryCounter;
            countryCounter++;
            Log.i("countrylist", "Country Code = " + obj.getCountry()
                    + ", Country Name = " + obj.getDisplayCountry());

        }

        countryListDialog.setItems(countryNameArray,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectItemPosition) {
                        // user checked an item
                        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(Locale.getISOCountries()));
                        Locale obj = new Locale("", arrayList.get(selectItemPosition));
                        // Toast.makeText(getBaseContext(), obj.getCountry() + " " + obj.getDisplayCountry(), Toast.LENGTH_LONG).show();

                        if (countryIndicator == 0) {
                            presentCountryTextView.setText(obj.getDisplayCountry());
                            presentCountryCode = obj.getCountry();
                            if( currentLivingLocationStatus.equalsIgnoreCase("AB"))
                                presentDistrictLayout.setVisibility(View.GONE);
                            else
                                presentDistrictLayout.setVisibility(View.VISIBLE);
                        } else {
                            permanentCountryTextView.setText(obj.getDisplayCountry());
                            permanentCountryCode = obj.getCountry();
                            if( !permanentCountryCode.equalsIgnoreCase("BD"))
                                permanentDistrictLayout.setVisibility(View.GONE);
                            else
                                permanentDistrictLayout.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    private void prePareDistrictListDialog() {

        loadDistrictListOnHashMap();

        districtList = new String[disLinkedHashMap.size()];

        for (Map.Entry m : disLinkedHashMap.entrySet()) {
            Log.i("hashmapval", districtCounter + " " + m.getKey() + " " + m.getValue());
            districtList[districtCounter] = (String) m.getValue();
            districtCounter++;
        }

        districtListDialog.setItems(districtList,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectItemPosition) {
                        // user checked an item

                        for (Map.Entry entry : disLinkedHashMap.entrySet()) {
                            if (districtList[selectItemPosition].equals(entry.getValue())) {
                                districtKey = (Integer) entry.getKey();
                                break; //breaking because its one to one map
                            }
                        }

                        if(distIndicator == 0)
                            villageDistrictNameTextView.setText(districtList[selectItemPosition]);
                        else if(distIndicator == 1)
                            presentDistrictTextView.setText(districtList[selectItemPosition]);
                        else
                            permanentDistrictTextView.setText(districtList[selectItemPosition]);
                    }
                });


       /* villageHouseDistrictListDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        villageHouseDistrictListDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
*/
    }

    private void loadDistrictListOnHashMap() {

        disLinkedHashMap.put(18, "ঢাকা");
        disLinkedHashMap.put(19, "ফরিদপুর");
        disLinkedHashMap.put(20, "গাজীপুর");
        disLinkedHashMap.put(21, "গোপালগঞ্জ");
        disLinkedHashMap.put(23, "কিশোরগঞ্জ");
        disLinkedHashMap.put(24, "মাদারীপুর");
        disLinkedHashMap.put(25, "মানিকগঞ্জ");
        disLinkedHashMap.put(26, "মুন্সীগঞ্জ");
        disLinkedHashMap.put(28, "নারায়ণগঞ্জ");
        disLinkedHashMap.put(29, "নরসিংদী");
        disLinkedHashMap.put(31, "রাজবাড়ী");
        disLinkedHashMap.put(32, "শরীয়তপুর");
        disLinkedHashMap.put(34, "টাঙ্গাইল");

        disLinkedHashMap.put(27, "ময়মনসিংহ");
        disLinkedHashMap.put(22, "জামালপুর");
        disLinkedHashMap.put(30, "নেত্রকোনা");
        disLinkedHashMap.put(33, "শেরপুর");

        disLinkedHashMap.put(7, "বান্দরবান");
        disLinkedHashMap.put(8, "ব্রাহ্মণবাড়ীয়া");
        disLinkedHashMap.put(9, "চাঁদপুর");
        disLinkedHashMap.put(10, "চট্টগ্রাম");
        disLinkedHashMap.put(11, "কুমিল্লা");
        disLinkedHashMap.put(12, "কক্সবাজার");
        disLinkedHashMap.put(13, "ফেনী");
        disLinkedHashMap.put(14, "খাগড়াছড়ি");
        disLinkedHashMap.put(15, "লক্ষীপুর");
        disLinkedHashMap.put(16, "নোয়াখালী");
        disLinkedHashMap.put(17, "রাঙ্গামাটি");

        disLinkedHashMap.put(35, "বাগেরহাট");
        disLinkedHashMap.put(36, "চুয়াডাঙ্গা");
        disLinkedHashMap.put(37, "যশোর");
        disLinkedHashMap.put(38, "ঝিনাইদহ");
        disLinkedHashMap.put(39, "খুলনা");
        disLinkedHashMap.put(40, "কুষ্টিয়া");
        disLinkedHashMap.put(41, "মাগুরা");
        disLinkedHashMap.put(42, "মেহেরপুর");
        disLinkedHashMap.put(43, "নড়াইল");
        disLinkedHashMap.put(44, "সাতক্ষীরা");

        disLinkedHashMap.put(45, "বগুড়া");
        disLinkedHashMap.put(46, "চাঁপাইনবাবগঞ্জ");
        disLinkedHashMap.put(49, "জয়পুরহাট");
        disLinkedHashMap.put(52, "পাবনা");
        disLinkedHashMap.put(54, "নওগাঁ");
        disLinkedHashMap.put(55, "নাটোর");
        disLinkedHashMap.put(57, "রাজশাহী");
        disLinkedHashMap.put(59, "সিরাজগঞ্জ");

        disLinkedHashMap.put(1, "বরগুনা ");
        disLinkedHashMap.put(2, "বরিশাল ");
        disLinkedHashMap.put(3, "ভোলা");
        disLinkedHashMap.put(4, "ঝালকাঠি");
        disLinkedHashMap.put(5, "পটুয়াখালী");
        disLinkedHashMap.put(6, "পিরোজপুর");

        disLinkedHashMap.put(61, "হবিগঞ্জ");
        disLinkedHashMap.put(62, "মৌলভীবাজার");
        disLinkedHashMap.put(63, "সুনামগঞ্জ");
        disLinkedHashMap.put(64, "সিলেট");
    }

    private void initView() {

        villageHouseLayout = findViewById(R.id.village_house_layout);
        villageHouseLayout.setOnClickListener(this);
        presentDistrictLayout = findViewById(R.id.ln_district_present);
        presentDistrictLayout.setOnClickListener(this);
        permanentDistrictLayout = findViewById(R.id.ln_district_permanent);
        permanentDistrictLayout.setOnClickListener(this);
        villageHouseLayout = findViewById(R.id.village_house_layout);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper
                (RegistrationUserAddressInformation.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        districtListDialog = new AlertDialog.
                Builder(contextThemeWrapper);
        villageDistrictNameTextView = findViewById(R.id.village_house_district_name_text_view);
        presentDistrictTextView = findViewById(R.id.present_address_district);
        permanentDistrictTextView = findViewById(R.id.permanent_address_district);
        permanentAddressLayout = findViewById(R.id.ln_permanent_address);

       /* presentCountryListDialog = new AlertDialog.
                Builder(contextThemeWrapper);
        permanentCountryListDialog = new AlertDialog.
                Builder(contextThemeWrapper);*/
        countryListDialog = new AlertDialog.
                Builder(contextThemeWrapper);

        presentAddressEditext = findViewById(R.id.present_address_editext);
        permanentAddressEditext = findViewById(R.id.permanent_address_editext);

        presentCountryLayout = findViewById(R.id.present_country_layout);
        presentCountryLayout.setOnClickListener(this);
        presentCountryTextView = findViewById(R.id.present_country_text_view);

        permanentCountryLayout = findViewById(R.id.permanent_country_layout);
        permanentCountryLayout.setOnClickListener(this);
        permanentCountryTextView = findViewById(R.id.permanent_country_text_view);

        permanentAddressCheckbox = findViewById(R.id.permanent_address_checkbox);
        permanentAddressCheckbox.setOnClickListener(this);
        addressBangldeshCheckbox = findViewById(R.id.address_bangldesh_checkbox);
        addressBangldeshCheckbox.setOnClickListener(this);
        addressAbroadCheckbox = findViewById(R.id.address_abroad_checkbox);
        addressAbroadCheckbox.setOnClickListener(this);

        abroadTypeLayout = findViewById(R.id.abroad_type_layout);
        abroadTypeLayout.setVisibility(View.GONE);
        abroadTypeLayout.setOnClickListener(this);
        abroadTypeTextView = findViewById(R.id.abroad_type_text_view);
        abroadTypeStatusTitleTextView = findViewById(R.id.abroad_type_status_title);
        abroadTypeStatusTitleTextView.setVisibility(View.GONE);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new Intent(RegistrationPersonalInformation.this, Login.class);
                finish();*/
                if(strDataForUpdate != null) {
                    finish();
                }else {
                    new GetPreviousStepFetchConstant().execute();
                }
            }
        });

        nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.village_house_layout:
                districtListDialog.create();
                districtListDialog.show();
                distIndicator = 0;
                break;
            case R.id.ln_district_present:
                districtListDialog.create();
                districtListDialog.show();
                distIndicator = 1;
                break;
            case R.id.ln_district_permanent:
                districtListDialog.create();
                districtListDialog.show();
                distIndicator = 2;
                break;
            case R.id.address_bangldesh_checkbox:

                addressBangldeshCheckboxAction();

                break;
            case R.id.address_abroad_checkbox:

                addressAbroadCheckboxAction();
                break;

            case R.id.abroad_type_layout:
                startActivityForResult(new Intent(
                                RegistrationUserAddressInformation.this, AbroadOptionPickerPopUpActivity.class),
                        2);
                break;
            case R.id.next:
                Log.i("resultdata", currentLivingLocationStatus);
                sendDataToServer();
                break;
            case R.id.present_country_layout:
                countryIndicator = 0;
                countryListDialog.create();
                countryListDialog.show();
                break;
            case R.id.permanent_country_layout:
                countryIndicator = 1;
                countryListDialog.create();
                countryListDialog.show();
                break;
            case R.id.permanent_address_checkbox:
                permanentAddressLayout.setVisibility(permanentAddressLayout.getVisibility() == View.VISIBLE?View.GONE:View.VISIBLE);
        }

    }

    private void sendDataToServer() {

        if (villageDistrictNameTextView.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(), "আপনার দেশের বাড়ি কোথায় নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        }

        if (currentLivingLocationStatus.isEmpty()) {
            Toast.makeText(getBaseContext(), "আপনার বর্তমান অবস্থান কোথায় নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (currentLivingLocationStatus.equalsIgnoreCase("ab") &&
                    abroadTypeTextView.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getBaseContext(), "আপনার বিদেশে অবস্থানের ধরণ নির্বাচন করুন", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (presentAddressEditext.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "আপনার বর্তমান ঠিকানা লিখুন", Toast.LENGTH_LONG).show();
            return;
        }

        if (presentCountryCode.isEmpty()) {
            Toast.makeText(getBaseContext(), "আপনার  বর্তমানে অবস্থানরত দেশ নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        }
        else if(!currentLivingLocationStatus.equalsIgnoreCase("BD") && presentCountryCode.equalsIgnoreCase("BD"))
        {
            Toast.makeText(getBaseContext(), "আপনি আপনার বর্তমান অবস্থান-এ বিদেশ নির্বাচন করেছেন। বর্তমানে আপনি যে দেশে আছেন তা নির্বাচন করুন।", Toast.LENGTH_LONG).show();
            return;
        }
        else if(currentLivingLocationStatus.equalsIgnoreCase("BD") && !presentCountryCode.equalsIgnoreCase("BD"))
        {
            Toast.makeText(getBaseContext(), "আপনি আপনার বর্তমান অবস্থান-এ বাংলাদেশ নির্বাচন করেছেন। বর্তমানে আপনি বাংলাদেশ নির্বাচন করুন।", Toast.LENGTH_LONG).show();
            return;
        }



        if((presentDistrictLayout.getVisibility() == View.VISIBLE) && presentDistrictTextView.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getBaseContext(), "আপনার বর্তমান জেলা নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return;
        }

        if (!permanentAddressCheckbox.isChecked()) {

            if (permanentAddressEditext.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "আপনার স্থায়ী ঠিকানা লিখুন", Toast.LENGTH_LONG).show();
                return;
            }
            if (permanentCountryCode.isEmpty()) {
                Toast.makeText(getBaseContext(), "আপনার  স্থায়ী  দেশ  নির্বাচন করুন", Toast.LENGTH_LONG).show();
                return;
            }

            if((permanentDistrictLayout.getVisibility() == View.VISIBLE) && permanentDistrictTextView.getText().toString().equalsIgnoreCase(""))
            {
                Toast.makeText(getBaseContext(), "আপনার স্থায়ী জেলা নির্বাচন করুন", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if(strDataForUpdate != null){
            Intent intent = getIntent();
            intent.putExtra("home_town_data", getDistrictCode(villageDistrictNameTextView.getText().toString()));
            intent.putExtra("home_town_value", villageDistrictNameTextView.getText().toString());
            intent.putExtra("residence", presentCountryCode);
            intent.putExtra("living_abroad_data", abroadTypeTextView.getText().toString());
            intent.putExtra("living_abroad_value", selectedAbroadTypeNumber);
            intent.putExtra("same_address", permanentAddressCheckbox.isChecked() ? 1 : 0);
            intent.putExtra("present_address", presentAddressEditext.getText().toString().trim());
            intent.putExtra("present_country_value", presentCountryCode);
            intent.putExtra("present_country_data", presentCountryTextView.getText().toString());
            intent.putExtra("present_district_data", presentDistrictTextView.getText().toString());
            intent.putExtra("present_district_value", getDistrictCode(presentDistrictTextView.getText().toString()));
            intent.putExtra("permanent_address", permanentAddressEditext.getText().toString().trim());
            intent.putExtra("permanent_country_value", permanentCountryCode);
            intent.putExtra("permanent_country_data", permanentCountryTextView.getText().toString());
            intent.putExtra("permanent_district_data", permanentDistrictTextView.getText().toString());
            intent.putExtra("permanent_district_value", getDistrictCode(permanentDistrictTextView.getText().toString()));
            setResult(RESULT_OK, intent);
            finish();
        }else {

            String response = new StringBuilder().append("{")
                    .append("\"current_mobile_sign_up_step\":")
                    //.append(Login.currentMobileSignupStep)
                    .append(7)
                    .append(",")
                    .append("\"home_town\":")
                    .append(getDistrictCode(
                            villageDistrictNameTextView.getText().toString()))//disLinkedHashMap.villageDistrictNameTextView.getText().toString())

                    .append(",")
                    .append("\"residence\":")
                    .append("\"")
                    .append(currentLivingLocationStatus)
                    .append("\"")
                    .append(",")
                    .append("\"living_abroad\":")
                    .append("\"")
                    .append(selectedAbroadTypeNumber)
                    .append("\"")
                    .append(",")
                    .append("\"same_address\":")
                    .append(permanentAddressCheckbox.isChecked() ? 1 : 0)
                    .append(",")
                    .append("\"address\":")
                    .append("[{")
                    .append("\"address\":")
                    .append("\"")
                    .append(presentAddressEditext.getText().toString().trim())
                    .append("\"")
                    .append(",")
                    .append("\"address_type\":")
                    .append(1)
                    .append(",")
                    .append("\"country\":")
                    .append("\"")
                    .append(presentCountryCode)
                    .append("\"")
                    .append(",")
                    .append("\"district\":")
                    .append("\"")
                    .append(presentCountryCode.equalsIgnoreCase("BD") ? getDistrictCode(
                            presentDistrictTextView.getText().toString()) : "")
                    .append("\"")
                    .append("},{")
                    .append("\"address\":")
                    .append("\"")
                    .append(permanentAddressCheckbox.isChecked()
                            ? presentAddressEditext.getText().toString().trim()
                            : permanentAddressEditext.getText().toString().trim())
                    .append("\"")
                    .append(",")
                    .append("\"address_type\":")
                    .append(2)
                    .append(",")
                    .append("\"country\":")
                    .append("\"")
                    .append(permanentAddressCheckbox.isChecked() ? presentCountryCode
                            : permanentCountryCode)
                    .append("\"")
                    .append(",")
                    .append("\"district\":")
                    .append("\"")
                    .append(permanentAddressCheckbox.isChecked() ? getDistrictCode(
                            presentDistrictTextView.getText().toString())
                            : getDistrictCode(permanentDistrictTextView.getText().toString()))
                    .append("\"")
                    .append("}]")
                    .append("}")
                    .toString();
            Log.i("data", response);

            new SendAddressInfo().execute(response, Utils.SEND_INFO);
        }

    }

    class SendAddressInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(progress.isShowing())
                progress.dismiss();
            if(s == null){
                Utils.ShowAlert(RegistrationUserAddressInformation.this, getString(R.string.no_internet_connection));
            }
            else{
                try {
                    // progress.cancel();
                    JSONObject jsonObject=new JSONObject(s);
                    Log.e("Response",s);

                    if(jsonObject.has("errors"))
                    {
                        Toast.makeText(RegistrationUserAddressInformation.this,
                                jsonObject.getJSONObject("errors").getString("detail"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        new RegistrationUserAddressInformation.FetchConstant().execute();
//                        Intent homeIntent = new Intent(RegistrationUserAddressInformation.this, HomeScreen.class);
//                        startActivity(homeIntent);
//                        finish();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... strings){
            SharePref sharePref = new SharePref(RegistrationUserAddressInformation.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]+" "+strings[1]+" "+token);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,strings[0]);
            Request request = new Request.Builder()
                    .url(strings[1])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(body)
                    .build();
            Response response = null;
            String responseString = null;
            try {
                response = client.newCall(request).execute();
                responseString = response.body().string();
                progress.dismiss();
            }catch(IOException e){
                progress.dismiss();
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }

    private String getDistrictCode(String strDistrict)
    {
        if (disLinkedHashMap.containsValue(strDistrict)) {
            for (final Object entry : disLinkedHashMap.keySet()) {
                if (disLinkedHashMap.get(entry).contains(strDistrict)) {
                    return entry.toString();
                }
            }
        }
        return "";
    }

    private String getCountryCode(String strCountry)
    {
        if(strCountry.equalsIgnoreCase(getString(R.string.bangladesh)))
            strCountry = "Bangladesh";
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(Locale.getISOCountries()));
        int index = Arrays.asList(countryNameArray).indexOf(strCountry);
        return arrayList.get(index);
    }

    private void addressBangldeshCheckboxAction() {

        if (addressBangldeshCheckbox.isChecked()) {
            currentLivingLocationStatus = "BD";
            addressAbroadCheckbox.setChecked(false);
            abroadTypeStatusTitleTextView.setVisibility(View.GONE);
            abroadTypeLayout.setVisibility(View.GONE);
            abroadTypeTextView.setText(null);
            selectedAbroadTypeNumber = "";
            presentDistrictLayout.setVisibility(View.VISIBLE);
            presentCountryTextView.setText("বাংলাদেশ");
            presentCountryCode = "BD";
            permanentDistrictLayout.setVisibility(View.VISIBLE);
        } else {
            currentLivingLocationStatus = "";
            presentCountryTextView.setHint("দেশ");
        }
    }

    private void addressAbroadCheckboxAction() {

        if (addressAbroadCheckbox.isChecked()) {
            currentLivingLocationStatus = "AB";
            addressBangldeshCheckbox.setChecked(false);
            abroadTypeStatusTitleTextView.setVisibility(View.VISIBLE);
            abroadTypeLayout.setVisibility(View.VISIBLE);
            abroadTypeTextView.setHint("বিদেশে অবস্থানের ধরণ");
            presentDistrictLayout.setVisibility(View.GONE);
            presentCountryTextView.setText(null);
            presentCountryTextView.setHint("দেশ");
        } else {
            currentLivingLocationStatus = "";
            abroadTypeStatusTitleTextView.setVisibility(View.GONE);
            abroadTypeLayout.setVisibility(View.GONE);
            abroadTypeTextView.setText(null);
            selectedAbroadTypeNumber = "";
            presentDistrictLayout.setVisibility(View.VISIBLE);
            permanentDistrictLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {

            if (data!=null && !data.getStringExtra("ABROAD_STATUS_TYPE").equalsIgnoreCase("reject")) {
                abroadTypeTextView.setText(data.getStringExtra("ABROAD_STATUS_TYPE"));
                selectedAbroadTypeNumber = data.getIntExtra("ABROAD_STATUS_TYPE_SELECTOR_NUMBER",
                        0) + "";
                Log.i("resultdata", selectedAbroadTypeNumber + "");
            }
        }
    }

    public class GetPreviousStepFetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharePref sharePref = new SharePref(RegistrationUserAddressInformation.this);
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 6)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 6);
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("urldata", s + "");
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (s == null) {
                Utils.ShowAlert(RegistrationUserAddressInformation.this, getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing()) {
                    progress.dismiss();
                }*/
               if(isSignUp) {
                   finish();
               }else {
                   Log.i("constantval", this.getClass().getSimpleName() + "_backfetchval: " + s);
                   startActivity(new Intent(RegistrationUserAddressInformation.this,
                           RegistrationFamilyInfoSecondPage.class).
                           putExtra("constants", s));
                   finish();
               }
            }
        }
    }

    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progress.isShowing())
                progress.dismiss();
            if (s == null) {
                Utils.ShowAlert(RegistrationUserAddressInformation.this, getString(R.string.no_internet_connection));
            } else {
               /* if (progress.isShowing())
                    progress.dismiss();*/
//                clearStaticData();
                Log.i("constantval", this.getClass().getSimpleName() + "_nextfetchval: " + s);
                Intent signupIntent;
                signupIntent = new Intent(RegistrationUserAddressInformation.this,
                        ThanksActivity.class);
                signupIntent.putExtra("constants", s);
                signupIntent.putExtra("isSignUp", true);
                startActivity(signupIntent);
//                finish();
            }


        }

        @Override
        protected String doInBackground(String... parameters) {
            //  Login.currentMobileSignupStep+=1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep )
                    .url(Utils.STEP_CONSTANT_FETCH + 8)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

//    private void clearStaticData() {
//
//    }

}
