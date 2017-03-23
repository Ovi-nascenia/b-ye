package com.nascenia.biyeta.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 1/9/2017.
 */

public class Utils {

    public static final String MALE_GENDER = "male";
    public static final String FEMALE_GENDER = "female";
    public static final String Base_URL = "http://test.biyeta.com";


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

    public static String getBangleMonth(String month) {
        return null;
    }


    public static String formatString(String result) {

        if (result.startsWith(",") | result.startsWith(" : ")) {

            result = removeChar(result, 0);
        }

        if (result.charAt(result.length() - 1) == ',' |
                result.charAt(result.length() - 1) == ' ') {

            result = removeChar(result, result.length() - 1);
        }

        if (result.charAt(result.length() - 1) == ':') {

            result = removeChar(result, result.length() - 1);
        }

        result = result.replaceAll(",,", ",");
        result = result.replaceAll(", : ", " : ");


        return result;
    }

    public static String removeChar(String str, Integer n) {

        return str.substring(0, n) + str.substring(n + 1, str.length());
    }

    public static void scaleImage(Context context, float max_zoom, ImageView view) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int device_width = displayMetrics.widthPixels;
        int device_height = displayMetrics.heightPixels;
        Drawable drawing = view.getDrawable();
        if (drawing == null) {
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

        int img_width = bitmap.getWidth();
        int img_height = bitmap.getHeight();
        int img_height_max = device_height * 2 / 5;
        float xScale = 1, yScale = 1;
        if (img_height_max > img_height) {
            if (img_height_max > img_height) {
                if (img_height * max_zoom > img_height_max) {
                    img_height_max = img_height;
                } else {
                    img_height_max = (int) (img_height * max_zoom);
                    xScale = yScale = max_zoom;
                    if (img_width * yScale > device_width) {
                        xScale = yScale = (float) (device_width / img_width);
                        img_width = device_width;
                    }
                }
                img_height = img_height_max;
            }

        } else {
            xScale = yScale = (float) img_height_max / img_height;
            img_height = img_height_max;
            img_width = (int) (img_width * xScale);
            if (img_width * yScale > device_width) {
                yScale = xScale = (float) device_width / img_width;
                img_width = device_width;
                img_height = (int) (img_height * xScale);
            } else if (img_width > device_width) {
                img_width = device_width;
                xScale = yScale = (float) device_width / bitmap.getWidth();
                img_height = (int) (bitmap.getHeight() * yScale);
            }
        }

        Matrix matrix = new Matrix();
        matrix.postScale(xScale, yScale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        BitmapDrawable result = new BitmapDrawable(context.getResources(), scaledBitmap);

        view.setImageDrawable(result);

        view.setMaxWidth(img_width);
        view.setMaxHeight(img_height);
    }
}
