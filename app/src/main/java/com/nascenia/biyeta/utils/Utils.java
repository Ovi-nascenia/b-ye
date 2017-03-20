package com.nascenia.biyeta.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by user on 1/9/2017.
 */

public class Utils {

    public static final String MALE_GENDER = "male";
    public static final String FEMALE_GENDER = "female";


    public static void ShowAlert(Context context, String bodyMessage) {

        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(bodyMessage)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static String convertEnglishDigittoBangla(int itemvalue) {


        //int day = Integer.parseInt(itemvalue);
        int firstDigit = itemvalue / 10;
        int lastdigit = itemvalue % 10;

        Log.i("bangla", firstDigit + " " + lastdigit);

        if (firstDigit == 0) {
            return getBanglaDigit(lastdigit);
        } else {
            return getBanglaDigit(firstDigit) + getBanglaDigit(lastdigit);

        }

    }


    public static String convertEnglishYearDigittoBangla(int itemvalue) {


        String number = String.valueOf(itemvalue);
        String result = "";
        for (int i = 0; i < number.length(); i++) {
            result = result + getBanglaDigit(Character.digit(number.charAt(i), 10));

        }

        return result;
    }


    public static String getBanglaDigit(int digit) {
        //  String convertedDateValue;

        switch (digit) {
            case 0:
                return "০";
            case 1:
                return "১";
            case 2:
                return "২";
            case 3:
                return "৩";
            case 4:
                return "৪";
            case 5:
                return "৫";
            case 6:
                return "৬";
            case 7:
                return "৭";
            case 8:
                return "৮";
            case 9:
                return "৯";
            default:
                return "";

        }

    }

    public static String getBangleMonth(String month)
    {
        return null;
    }




}
