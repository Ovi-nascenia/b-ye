package com.nascenia.biyeta.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.FavoriteActivity;
import com.nascenia.biyeta.activity.HomeScreen;
import com.nascenia.biyeta.activity.Login;
import com.nascenia.biyeta.activity.NewUserProfileActivity;
import com.nascenia.biyeta.activity.RegistrationFirstActivity;
import com.nascenia.biyeta.activity.WebViewPayment;
import com.nascenia.biyeta.fragment.Search;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 1/9/2017.
 */

public class Utils{

    public static final String MALE_GENDER = "male";
    public static final String FEMALE_GENDER = "female";
    public static final String Base_URL = "http://test.biyeta.com";
//    public static final String Base_URL = "http://192.168.1.95:3000";
//    public static final String Base_URL = "https://www.biyeta.com";
    public static final String FACEBOOK_SUBURL = "/api/v1/facebook_authorization/authorize";
    public static final String FACEBOOK_LOGIN_URL = Base_URL + FACEBOOK_SUBURL;

    public static final String SEND_SMILE_URL = Utils.Base_URL+"/api/v1/smiles";
    public static final String FAVORITE_URL = Utils.Base_URL+"/api/v1/favorites";
    public static final String UNFAVORITE_URL = Utils.Base_URL+"/api/v1/favorites/unfavorate";
    public static final String PROFILE_REQUEST_URL = Utils.Base_URL+"/api/v1/profile_requests/";
    public static final String COMMUNICATION_REQUEST_URL = Utils.Base_URL+"/api/v1/communication_requests/";
    public static final String PROFILES_URL = Utils.Base_URL+"/api/v1/profiles/";
    public static final String APPUSER_OWN_PROFILE_VIEW_URL = Utils.Base_URL+"/api/v1/profiles/view";
    public static final String REQUEST_SENDER_IDS_URL = Utils.Base_URL+"/api/v1/requests/request_sender_ids";
    public static final String REGISTRATION_TOKEN_SENDER_URL = Utils.Base_URL+"/api/v1/fcm_service/login_notification";
    public static final String REGISTRATION_FIRST_PAGE_URL = Utils.Base_URL+"/api/v1/users";
    public static final String VERIFICATION_CODE_SEND_URL = Utils.Base_URL+"/api/v1/mobile_verifications";
    public static final String VERIFICATION_CODE_RESEND_URL = Utils.Base_URL+"/api/v1/mobile_verifications/resend_code";
    public static final String VERIFICATION_CODE_VERIFY_URL = Utils.Base_URL+"/api/v1/mobile_verifications/verify_mobile";
    public static final String STEP_CONSTANT_FETCH = Utils.Base_URL+"/api/v1/step_constants/fetch/";
    public static final String SEND_INFO = Utils.Base_URL+"/api/v1/registrations";
    public static final String FB_SIGNUP = Utils.Base_URL + "/api/v1/facebook_authorization/sign_up_with_fb";
    public static final String FB_SIGNUP_local = "https://www.biyeta.com"+"/api/v1/facebook_authorization/sign_up_with_fb";
    public static final String PASS_RESET = Utils.Base_URL+"/api/v1/passwords";
    public static final String PROFILE_UPDATE = Utils.Base_URL + "/api/v1/profile/update";
    public static final String PROFILE = "profile";
    public static final String ABOUT_YOURSELF = "about_yourself";
    public static final String DATE_OF_BIRTH = "dob";
    public static final String HEIGHT_FEET = "height_ft";
    public static final String HEIGHT_INCH = "height_inc";
    public static final String RELIGION = "religious";
    public static final String CAST = "cast";
    public static final String OTHER_RELIGION = "other_religious";
    public static final String OTHER_CAST = "other_cast";
    public static final String SKIN_COLOR = "skin_color";
    public static final String WEIGHT = "weight";
    public static final String MARITAL_STATUS = "marital_status";
    public static final String BLOOD_GROUP = "blood_group";
    public static final String SMOKE = "is_smoking";
    public static final String FAST = "fast";
    public static final String PRAYER = "prayer";
    public static final String OWN_HOUSE = "own_house";
    public static final String DISABILITY = "physical_disability";
    public static final String DISABILITY_DESC = "physical_disability_description";
    public static final String PROFESSIONAL_GROUP = "professional_group";
    public static final String OCCUPATION = "occupation";
    public static final String DESIGNATION = "designation";
    public static final String EDUCATION_ATTR = "educations_attributes";
    public static final String NAME = "name";
    public static final String SUBJECT = "subject";
    public static final String INSTITUTION = "institution";
    public static final String INSTITUTE = "institute";
    public static final String YEAR = "year";
    public static final String ID = "id";
    public static final String HOME_TOWN = "home_town";
    public static final String RESIDENCE = "residence";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_TYPE = "address_type";
    public static final String COUNTRY = "country";
    public static final String DISTRICT = "district";
    public static final String ADDRESSES_ATTRIBUTES = "addresses_attributes";
    public static String FAMILY_MEMBER_ATTRIBUTE = "family_members_attributes";
    public static final String RELATION = "relation";
//    public static final String FAMILY_MEMBER_ATTR = "family_members_attributes";
    public static String SIBLING_ID = "sibling_id";
    public static String SIBLING_ATTR = "siblings_attributes";
    public static String SIBLING_TYPE = "sibling_type";
    public static String SPOUSE = "spouse";
    public static String AGE = "age";



    public static final String MUSLIM_TAG="muslim";


    public static final int BIODATA_REQUEST_FRAGEMNT_CLASS = 000;
    public static final int COMMUNICATION_REQUEST_FRAGEMNT_CLASS = 111;

    public static final int SMILEY_BUTTON_PRESS_TAG = 222;
    public static final int FAVORITE_BUTTON_PRESS_TAG = 1;
    public static final int UNFAVORITE_BUTTON_PRESS_TAG = 0;
    public static final int UPGRADE_REQUEST_CODE = 1001;


    ///debug section

    public static final String LOGIN_DEBUG = "LoginDebug";
    public static final String DEBUG = "Debug";


    //userprofile request tag

    public static final String profileRequestAccept = "profile_request_accept";
    public static final String profileRequestCancel = "profile_request_cancel";
    public static final String sendCommunicationRequest = "send_communication_request";
    public static final String sendBiodataRequest = "send_biodata_request";
    public static final String commRequestAccept = "comm_request_accept";
    public static final String commRequestCancel = "comm_request_cancel";
    public static final String sendmessage = "message";
    public static final String call = "call";
    public static final String MESSAGE_CALL_BLOCK = "message_call_block";

    //for imei


    private static TelephonyManager telephonyManager;
    private static String deviceImei;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static String SPOUSE_OCCUPATION = "s_occupation";
    public static String SPOUSE_PROFESSIONAL_GROUP = "s_professional_group";
    public static String SPOUSE_DESIGNTION = "s_designation";
    public static String SPOUSE_INSTITUTE = "s_institute";


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


    public static void ShowInternetConnectionError(Context context) {
        if(context!=null)
        {
            new AlertDialog.Builder(context)
//                .setTitle("ইন্টারনেট")
                    .setMessage("ইন্টারনেট সংযোগ নেই")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static String convertEnglishDigittoBangla(int itemvalue) {

        String[] strItemValue = (itemvalue+"").split("");
        //int day = Integer.parseInt(itemvalue);
        /*int firstDigit = itemvalue / 10;
        int secondDigit = itemvalue % 10;
        int thirdDigit = 0;
        if(itemvalue > 99)
            thirdDigit = itemvalue / 100;

//        Log.i("bangla", firstDigit + " " + lastdigit);

       if (firstDigit == 0 && thirdDigit == 0) {
            return getBanglaDigit(secondDigit);
        } else if (thirdDigit == 0){
            return getBanglaDigit(firstDigit) + getBanglaDigit(secondDigit);
        }
        else
           return getBanglaDigit(firstDigit) + getBanglaDigit(firstDigit) + getBanglaDigit(secondDigit);
           */

        String number = "";
        for(int i = 0; i<strItemValue.length; i++)
        {
            if(strItemValue[i].length()>0)
                number+=getBanglaDigit(Integer.parseInt(strItemValue[i]));
        }

        return number.length()>0?number:"0";

    }


    public static String deviceIMEI(Context context)
    {
        telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager.getDeviceId()!=null)
        {
            deviceImei = telephonyManager.getDeviceId();
        }
        else{
            deviceImei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceImei;

    }


    public static String convertEnglishYearDigittoBangla(int itemvalue){


        String number = String.valueOf(itemvalue);
        String result = "";
        for (int i = 0; i < number.length(); i++){
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
//        Log.i("finalresult", "enter method " + result.length());
        if(result == null)
            return "";

        if (result.startsWith(",") | result.startsWith(" : ")) {

            result = removeChar(result, 0);
//            Log.i("finalresult", "enter first if  " + result + " " + result.length());
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

    public static void scaleImage(Context context, ImageView view) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int device_width = displayMetrics.widthPixels;
        int device_height = displayMetrics.heightPixels;
        Drawable drawing = view.getDrawable();
        int iv_width = view.getMeasuredWidth();
        int iv_height = view.getMeasuredWidth();
        float iv_ratio = 0, img_ratio = 0, scale = 0, yScale=0;
//        if(iv_width>iv_height)
//            iv_ratio = (float) (iv_width/iv_height);
//        else
//            iv_ratio = (float) (iv_height/iv_width);
//        if(iv_ratio < 1.2)
//            return;
        if (drawing == null) {
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

        int img_width = bitmap.getWidth();
        int img_height = bitmap.getHeight();
        if(img_height == 0 || img_width == 0)
            return;
        if(img_width>img_height) {
            img_ratio = (float) (img_width / img_height);
            scale = (float)img_width/iv_width;
        }else {
            img_ratio = (float) (img_height / img_width);
            scale = (float)img_height/iv_height;
        }
//        if(img_ratio < 1.2)
//            return;




//        int img_height_max = device_height * 2 / 5;
//        float xScale = 1, yScale = 1;
//        if (img_height_max > img_height) {
//            if (img_height_max > img_height) {
//                if (img_height * 1 > img_height_max) {
//                    img_height_max = img_height;
//                } else {
//                    img_height_max = (int) (img_height * 1);
//                    xScale = yScale = 1;
//                    if (img_width * yScale > device_width) {
//                        xScale = yScale = (float) (device_width / img_width);
//                        img_width = device_width;
//                    }
////                    else
////                    {
////                        img_width = (int)(img_width*max_zoom);
////                    }
//                }
//                img_height = img_height_max;
//            }
//
//        } else {
//            xScale = yScale = (float) img_height_max / img_height;
//            img_height = img_height_max;
//            img_width = (int) (img_width * xScale);
//            if (img_width * yScale > device_width) {
//                yScale = xScale = (float) device_width / img_width;
//                img_width = device_width;
//                img_height = (int) (img_height * xScale);
//            } else if (img_width > device_width) {
//                img_width = device_width;
//                xScale = yScale = (float) device_width / bitmap.getWidth();
//                img_height = (int) (bitmap.getHeight() * yScale);
//            }
//        }

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        BitmapDrawable result = new BitmapDrawable(context.getResources(), scaledBitmap);

        view.setImageDrawable(result);

        view.setMaxWidth(scaledBitmap.getWidth());
        view.setMaxHeight(scaledBitmap.getHeight());
    }

    public static String getTime(String utcTime){
        Calendar calendar = Calendar.getInstance(), systemCal = Calendar.getInstance();
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utcFormat.parse(utcTime);
        } catch (ParseException e){
            e.printStackTrace();
        }

        calendar.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        if(date!=null){
            calendar.setTime(date);
        }

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
        if (diffDays < 1){
            dateFormat = new SimpleDateFormat("hh:mm a");
            strTime = dateFormat.format(calendar.getTimeInMillis());
        } else if (diffWeeks < 1){
            dateFormat = new SimpleDateFormat("E, hh:mm a");
            strTime = dateFormat.format(calendar.getTimeInMillis());
        } else if (diffDays < 365){
            dateFormat = new SimpleDateFormat("dd MMM");
            strTime = dateFormat.format(calendar.getTimeInMillis());
        } else {
            dateFormat = new SimpleDateFormat("dd MMM");
            strTime = dateFormat.format(calendar.getTimeInMillis());
            dateFormat = new SimpleDateFormat("yy");
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

            case "professional_group":
                value = "প্রফেশনাল গ্রুপ";
                break;

            case "designation":
                value = "পদবি";
                break;

            case "institute":
                value = "প্রতিষ্ঠান";
                break;

            default:
                value = "";
                break;


        }

        return value;

    }


    public static void calculateHashKey(Context context, String yourPackageName) {
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


    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String englishToBanglaNumberConvertion(int number){

            String banglaNumber="";

            switch (number){
                case 1:
                    banglaNumber="প্রথম";
                    break;
                case 2:
                    banglaNumber="দ্বিতীয়";
                    break;
                case 3:
                    banglaNumber="তৃতীয়";
                    break;
                case 4:
                    banglaNumber="চতুর্থ";
                    break;
                case 5:
                    banglaNumber="পঞ্চম";
                    break;
                case 6:
                    banglaNumber="ষষ্ঠ";
                    break;

            }

        return banglaNumber;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkExternalStoragePermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions((Activity) context,
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                }
                            });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void showPricingPlanDialog(final Context context, String message){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
//                                alertBuilder.setTitle(R.string.account_recharge_title);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton(R.string.see_details,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(context,
                                WebViewPayment.class);
                        if (context instanceof NewUserProfileActivity) {
                            ((NewUserProfileActivity) context).startActivityForResult(
                                    myIntent, Utils.UPGRADE_REQUEST_CODE);
                        }else if (context instanceof FavoriteActivity) {
                            ((FavoriteActivity) context).startActivityForResult(
                                    myIntent, Utils.UPGRADE_REQUEST_CODE);
                        }
                    }
                });
        alertBuilder.setNegativeButton(R.string.not_now,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = alertBuilder.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
//                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(context.getResources().getColor(R.color.title_gray));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });

        alert.show();
    }

    public static void showOldUserDialog(final Context context, String message){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
//                                alertBuilder.setTitle(R.string.account_recharge_title);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton(R.string.see_plans,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(context,
                                WebViewPayment.class);
                        if (context instanceof HomeScreen) {
                            ((HomeScreen) context).startActivityForResult(
                                    myIntent, Utils.UPGRADE_REQUEST_CODE);
                        }
                    }
                });
        alertBuilder.setNegativeButton(R.string.not_now,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = alertBuilder.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
//                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(context.getResources().getColor(R.color.title_gray));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });

        alert.show();
    }

    public static void showAppUpdateDialog(final Context context, JSONObject jsonObject) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(false);
//                                alertBuilder.setTitle(R.string.account_recharge_title);
        try {
            alertBuilder.setMessage(jsonObject.getString("app_update_message"));

            alertBuilder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getApplicationContext().getPackageName(); // package name of the app
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        ((HomeScreen)context).onBackPressed();
                    }
                });
//            alertBuilder.setNegativeButton(R.string.not_now,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
            final AlertDialog alert = alertBuilder.create();
            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
//                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
    //                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(context.getResources().getColor(R.color.title_gray));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    //                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                }
            });

            alert.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void showRegisteredUserDialog(final Context context, String message, final String email, final String uid){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = ((Activity)context).getIntent();
                        myIntent.putExtra("email", email);
                        if(uid != null)
                            myIntent.putExtra("uid", uid);
                        ((RegistrationFirstActivity)context).setResult(Activity.RESULT_OK, myIntent);
                        ((RegistrationFirstActivity)context).finish();
                    }
                });
        alertBuilder.setNegativeButton(R.string.not_now,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = alertBuilder.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
//                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(context.getResources().getColor(R.color.title_gray));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });

        alert.show();
    }
}
