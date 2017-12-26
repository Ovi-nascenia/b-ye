package com.nascenia.biyeta.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nascenia.biyeta.R;

public class RegistrationUserAddressInformation extends AppCompatActivity {

    private LinearLayout villageHouseLayout;
    private AlertDialog.Builder villageHouseDistrictListDialog;
    private String[] districtList = {"ঢাকা", "ফরিদপুর", "গাজীপুর", "গোপালগঞ্জ", "কিশোরগঞ্জ", "মাদারীপুর", "মানিকগঞ্জ",
            "মুন্সীগঞ্জ", "নারায়ণগঞ্জ", "নরসিংদী", "রাজবাড়ী", "শরীয়তপুর", "টাঙ্গাইল", "চুয়াডাঙ্গা"};

    private TextView villageDistrictNameTextView;

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

        villageHouseDistrictListDialog.setItems(districtList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selectItemPosition) {
                // user checked an item
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

    private void initView() {
        villageHouseLayout = findViewById(R.id.village_house_layout);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper
                (RegistrationUserAddressInformation.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        villageHouseDistrictListDialog = new AlertDialog.
                Builder(contextThemeWrapper);
        villageDistrictNameTextView = findViewById(R.id.village_house_district_name_text_view);

    }

}
