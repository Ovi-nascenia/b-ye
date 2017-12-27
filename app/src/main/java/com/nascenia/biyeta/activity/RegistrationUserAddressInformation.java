package com.nascenia.biyeta.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrationUserAddressInformation extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout villageHouseLayout, presentCountryLayout, permanentCountryLayout,
            abroadTypeLayout;
    private AlertDialog.Builder villageHouseDistrictListDialog,presentCountryListDialog,
    permanentCountryListDialog,countryListDialog;

    private TextView villageDistrictNameTextView, presentCountryTextView, permanentCountryTextView,
            abroadTypeTextView,abroadTypeStatusTitleTextView;

    private LinkedHashMap<Integer, String> disLinkedHashMap = new LinkedHashMap<Integer, String>();
    private String[] districtList;
    private int districtCounter = 0,countryCounter=0;
    private Integer districtKey = null,selectedAbroadTypeNumber=null,
            presentCountryKey=null,
            permanentCountryKey=null;

    private EditText presentAddressEditext, permanentAddressEditext;
    private CheckBox permanentAddressCheckbox, addressBangldeshCheckbox, addressAbroadCheckbox;

    private String currentLivingLocationStatus = "";

    private Button nextBtn;

    private LinkedHashMap<Integer, String> countryLinkedHashMap = new LinkedHashMap<Integer, String>();
    private String[] countryNameArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_address_information);

        initView();
        prePareVillageHouseDistrictListDialog();
        prepareCountryListDialog();


       /* villageHouseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                villageHouseDistrictListDialog.create();
                villageHouseDistrictListDialog.show();
            }
        });*/

    }

    private void prepareCountryListDialog() {
        countryNameArray =new String[Locale.getISOCountries().length];

       // String[] locales = Locale.getISOCountries();

        for (String countryCode : Locale.getISOCountries()) {

            Locale obj = new Locale("", countryCode);
            countryNameArray[countryCounter]=obj.getDisplayCountry();

            countryCounter++;
            Log.i("countrylist","Country Code = " + obj.getCountry()
                    + ", Country Name = " + obj.getDisplayCountry());

        }

        countryListDialog.setItems(countryNameArray,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectItemPosition) {
                        // user checked an item
                        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(Locale.getISOCountries()));
                        Locale obj = new Locale("", arrayList.get(selectItemPosition));
                        Toast.makeText(getBaseContext(),obj.getCountry()+" "+obj.getDisplayCountry(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void prePareVillageHouseDistrictListDialog() {

        loadDistrictListOnHashMap();

        districtList = new String[disLinkedHashMap.size()];

        for (Map.Entry m : disLinkedHashMap.entrySet()) {
            Log.i("hashmapval", districtCounter + " " + m.getKey() + " " + m.getValue());
            districtList[districtCounter] = (String) m.getValue();
            districtCounter++;
        }

        villageHouseDistrictListDialog.setItems(districtList,
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

                        villageDistrictNameTextView.setText(districtList[selectItemPosition]);
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
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper
                (RegistrationUserAddressInformation.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        villageHouseDistrictListDialog = new AlertDialog.
                Builder(contextThemeWrapper);
        villageDistrictNameTextView = findViewById(R.id.village_house_district_name_text_view);

       /* presentCountryListDialog = new AlertDialog.
                Builder(contextThemeWrapper);
        permanentCountryListDialog = new AlertDialog.
                Builder(contextThemeWrapper);*/
        countryListDialog= new AlertDialog.
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

        nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.village_house_layout:
                villageHouseDistrictListDialog.create();
                villageHouseDistrictListDialog.show();
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
                Log.i("resultdata",currentLivingLocationStatus);
                sendDataToServer();
                break;
            case R.id.present_country_layout:
                countryListDialog.create();
                countryListDialog.show();
                break;
            case R.id.permanent_country_layout:
                countryListDialog.create();
                countryListDialog.show();
                break;
        }

    }

    private void sendDataToServer() {

        if(villageDistrictNameTextView.getText().toString().equalsIgnoreCase("")){

        }
    }

    private void addressBangldeshCheckboxAction() {

        if(addressBangldeshCheckbox.isChecked()){
            currentLivingLocationStatus = "ban";
            addressAbroadCheckbox.setChecked(false);
            abroadTypeStatusTitleTextView.setVisibility(View.GONE);
            abroadTypeLayout.setVisibility(View.GONE);
            abroadTypeTextView.setText(null);
            selectedAbroadTypeNumber=null;
        }else{
            currentLivingLocationStatus = "";
        }
    }

    private void addressAbroadCheckboxAction() {

        if(addressAbroadCheckbox.isChecked()){
            currentLivingLocationStatus = "ab";
            addressBangldeshCheckbox.setChecked(false);
            abroadTypeStatusTitleTextView.setVisibility(View.VISIBLE);
            abroadTypeLayout.setVisibility(View.VISIBLE);
            abroadTypeTextView.setText("বিদেশে অবস্থানের ধরন");
        }else{
            currentLivingLocationStatus = "";
            abroadTypeStatusTitleTextView.setVisibility(View.GONE);
            abroadTypeLayout.setVisibility(View.GONE);
            abroadTypeTextView.setText(null);
            selectedAbroadTypeNumber=null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {

            if (!data.getStringExtra("ABROAD_STATUS_TYPE").equalsIgnoreCase("reject")) {
                abroadTypeTextView.setText(data.getStringExtra("ABROAD_STATUS_TYPE"));
                selectedAbroadTypeNumber=data.getIntExtra("ABROAD_STATUS_TYPE_SELECTOR_NUMBER",
                        0);
                Log.i("resultdata",selectedAbroadTypeNumber+"");
            }
        }
    }

}
