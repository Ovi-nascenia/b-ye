package com.nascenia.biyeta.utils;

/**
 * Created by saiful on 1/27/17.
 */

public class CalenderBanglaInfo {

    public static String getBanglaDay(final String day) {

        if (day.equals("sunday")) {
            return "রবিবার";
        } else if (day.equals("monday")) {
            return "সোমবার";
        } else if (day.equals("tuesday")) {
            return "মঙ্গলবার";
        } else if (day.equals("wednesday")) {
            return "বুধবার";
        } else if (day.equals("thursday")) {
            return "বৃহস্পতিবার";
        } else if (day.equals("friday")) {
            return "শুক্রবার";
        } else if (day.equals("saturday")) {
            return "শনিবার";
        }

        return "";
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

    public static String getEnglissDigit(char digit) {
        //  String convertedDateValue;

        switch (digit) {
            case '০':
                return "0";
            case '১':
                return "1";
            case '২':
                return "2";
            case '৩':
                return "3";
            case '৪':
                return "4";
            case '৫':
                return "5";
            case '৬':
                return "6";
            case '৭':
                return "7";
            case '৮':
                return "8";
            case '৯':
                return "9";
            default:
                return "";

        }

    }

    public static String getBanglaMonth(final String month) {

        if (month.equals("january")) {
            return "জানুয়ারী";
        } else if (month.equals("february")) {
            return "ফেব্রুয়ারি";
        } else if (month.equals("march")) {
            return "মার্চ";
        } else if (month.equals("april")) {
            return "এপ্রিল";
        } else if (month.equals("may")) {
            return "মে";
        } else if (month.equals("june")) {
            return "জুন";
        } else if (month.equals("july")) {
            return "জুলাই";
        } else if (month.equals("august")) {
            return "অগাস্ট";
        } else if (month.equals("september")) {
            return "সেপ্টেম্বর";
        } else if (month.equals("october")) {
            return "অক্টোবর";
        } else if (month.equals("november")) {
            return "নভেম্বর";
        } else if (month.equals("december")) {
            return "ডিসেম্বর";
        }


        return month;
    }

    public  static  String  getDigitEnglishFromBangla(String number){
        if(number==null)
            return new String("");
        StringBuilder builder = new StringBuilder();
        try{
            for(int i =0;i<number.length();i++){

                builder.append(number.charAt(i));
            }
        }catch(Exception e){
            //logger.debug("getDigitBanglaFromEnglish: ",e);
            return new String("");
        }
        return builder.toString();
    }

}
