package com.nascenia.biyeta.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nascenia.biyeta.R;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegistrationUserAddressInformation extends AppCompatActivity {

    private LinearLayout villageHouseLayout;
    private AlertDialog.Builder villageHouseDistrictListDialog;

    private TextView villageDistrictNameTextView;

    LinkedHashMap<Integer, String> disLinkedHashMap = new LinkedHashMap<Integer, String>();
    private String[] districtList;
    private int districtCounter = 0;
    private Integer districtKey = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user_address_information);

        initView();
        prePareVillageHouseDistrictListDialog();

        villageHouseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                villageHouseDistrictListDialog.create();
                villageHouseDistrictListDialog.show();

            }
        });

    }

    private void prePareVillageHouseDistrictListDialog() {

        loadDistrictListOnHashMap();

        districtList = new String[disLinkedHashMap.size()];

        for (Map.Entry m : disLinkedHashMap.entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue());
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
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper
                (RegistrationUserAddressInformation.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        villageHouseDistrictListDialog = new AlertDialog.
                Builder(contextThemeWrapper);
        villageDistrictNameTextView = findViewById(R.id.village_house_district_name_text_view);

    }

}
