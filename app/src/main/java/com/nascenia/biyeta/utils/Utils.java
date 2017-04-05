package com.nascenia.biyeta.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by user on 1/9/2017.
 */

public class Utils {

    public static final String MALE_GENDER = "male";
    public static final String FEMALE_GENDER = "female";
    public static final String Base_URL = "http://test.biyeta.com";
    public static final String FACEBOOK_SUBURL = "/api/v1/facebook_authorization/authorize";
    public static final String FACEBOOK_LOGIN_URL = Base_URL + FACEBOOK_SUBURL;

    public static final String SEND_SMILE_URL = "http://test.biyeta.com/api/v1/smiles";
    public static final String FAVORITE_URL = "http://test.biyeta.com/api/v1/favorites";
    public static final String PROFILE_REQUEST_URL = "http://test.biyeta.com/api/v1/profile_requests/";
    public static final String COMMUNICATION_REQUEST_URL = "http://test.biyeta.com/api/v1/communication_requests";
    public static final String PROFILES_URL = "http://test.biyeta.com/api/v1/profiles/";
    public static final String APPUSER_OWN_PROFILE_VIEW_URL = "http://test.biyeta.com/api/v1/profiles/view";

    public static final int BIODATA_REQUEST_FRAGEMNT_CLASS = 000;
    public static final int COMMUNICATION_REQUEST_FRAGEMNT_CLASS = 111;


    ///debug section

    public static final String LOGIN_DEBUG = "LoginDebug";


    //userprofile request tag

    public static final String profileRequestAccept = "profile_request_accept";
    public static final String profileRequestCancel = "profile_request_cancel";
    public static final String sendCommunicationRequest = "send_communication_request";
    public static final String sendBiodataRequest = "send_biodata_request";
    public static final String commRequestAccept = "comm_request_accept";
    public static final String commRequestCancel = "comm_request_cancel";
    public static final String sendmessage = "message";
    public static final String call = "call";


    public static void ShowAlert(Context context, String bodyMessage) {

        new AlertDialog.Builder(context)
//                .setTitle("Error")
                .setMessage(bodyMessage)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    public static void ShowInternetConnectionError(Context context)
    {
        new AlertDialog.Builder(context)
                .setTitle("ইন্টারনেট")
                .setMessage("ইন্টারনেট সংযোগ নেই")
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
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
        Log.i("finalresult", "enter method " + result.length());


        if (result.startsWith(",") | result.startsWith(" : ")) {

            result = removeChar(result, 0);
            Log.i("finalresult", "enter first if  " + result + " " + result.length());
        }


       /* if (result.charAt(result.length() - 1) == ',' |
                result.charAt(result.length() - 1) == ' ') {

            result = removeChar(result, result.length() - 1);
            Log.i("finalresult", "enter 2nd if  " + result + " " + result.length());
        }

        if (result.charAt(result.length() - 1) == ':' |
                result.charAt(result.length() - 1) == ' ') {

            result = removeChar(result, result.length() - 1);
            Log.i("finalresult", "enter 3rd if  " + result + " " + result.length());
        }*/


        // Log.i("finalresult", "after all if  " + result + " " + result.length());


        if (result.lastIndexOf(',') > 0) {

            result = result.substring(0, result.lastIndexOf(','));
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
//                    else
//                    {
//                        img_width = (int)(img_width*max_zoom);
//                    }
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

        view.setMaxWidth(scaledBitmap.getWidth());
        view.setMaxHeight(scaledBitmap.getHeight());
    }

    public static String getTime(String utcTime) {
        Calendar calendar = Calendar.getInstance(), systemCal = Calendar.getInstance();
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utcFormat.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        calendar.setTime(date);
        long diffMillis = systemCal.getTimeInMillis() - calendar.getTimeInMillis();
        long diffSeconds = diffMillis / 1000;
        long diffMins = diffSeconds / 60;
        long diffHours = diffMins / 60;
        long diffDays = diffHours / 24;
        long diffWeeks = diffDays / 7;
        long diffMons = diffDays / 30;
        long diffYears = diffDays / 365;

        String strTime = "";
        DateFormat dateFormat;
        if (diffDays < 1) {
            dateFormat = new SimpleDateFormat("hh:mm a");
            strTime = dateFormat.format(calendar.getTimeInMillis());
        } else if (diffWeeks < 1) {
            dateFormat = new SimpleDateFormat("E, hh:mm a");
            strTime = dateFormat.format(calendar.getTimeInMillis());
        } else if (diffDays < 365) {
            dateFormat = new SimpleDateFormat("dd MMM hh:mm a");
            strTime = dateFormat.format(calendar.getTimeInMillis());
        } else {
            dateFormat = new SimpleDateFormat("dd MMM");
            strTime = dateFormat.format(calendar.getTimeInMillis());
            dateFormat = new SimpleDateFormat("yy hh:mm a");
            strTime += "'" + dateFormat.format(calendar.getTimeInMillis());
        }
        return strTime;
    }


    public static String setBanglaProfileTitle(String value) {


        switch (value) {

            case "home town":
                value = "দেশের বাড়ি";
                break;

            case "age":
                value = "বয়স";
                break;

            case "height":
                value = "উচ্চতা";
                break;

            case "skin color":
                value = "গায়ের রং";
                break;

            case "health":
                value = "স্বাস্থ্য";
                break;

            case "marital status":
                value = "বৈবাহিক অবস্থা";
                break;

            case "educational qualification":
                value = "শিক্ষাগত যোগ্যতা";
                break;

            case "own house":
                value = "নিজের বাড়ি";
                break;

            case "occupation":
                value = "পেশা";
                break;


            default:
                value = "";
                break;


        }

        return value;

    }


    private void calculateHashKey(Context context, String yourPackageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    yourPackageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
