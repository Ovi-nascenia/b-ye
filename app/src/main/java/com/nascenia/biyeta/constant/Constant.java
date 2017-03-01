package com.nascenia.biyeta.constant;

/**
 * Created by user on 1/9/2017.
 */

public class Constant {

    public static final int SPLASH_TIMEOUT = 3000;

    public static final String BASE_URL = "http://test.biyeta.com/api/v1/";

    private static String profileItemBanglaName = "";

    public static String profileItemBanglaName(String profileItem) {

        switch (profileItem) {
            case "personal_information":
                profileItemBanglaName = "বাক্তিগত তথ্য";
                break;
            case "id":
                profileItemBanglaName = "id";
                break;
            case "age":
                profileItemBanglaName = "age";
                break;
            case "display_name":
                profileItemBanglaName = "display_name";
                break;
            case "height_ft":
                profileItemBanglaName = "height_ft";
                break;
            case "height_inc":
                profileItemBanglaName = "height_inc";
                break;
            case "about_yourself":
                profileItemBanglaName = "about_yourself";
                break;
            case "image":
                profileItemBanglaName = "image";
                break;
            case "real_name":
                profileItemBanglaName = "real_name";
                break;
            case "skin_color":
                profileItemBanglaName = "skin_color";
                break;
            case "weight":
                profileItemBanglaName = "weight";
                break;
            case "blood_group":
                profileItemBanglaName = "blood_group";
                break;
            case "marital_status":
                profileItemBanglaName = "marital_status";
                break;
            case "disabilities":
                profileItemBanglaName = "disabilities";
                break;
            case "disabilities_description":
                profileItemBanglaName = "disabilities_description";
                break;
            case "smoking":
                profileItemBanglaName = "smoking";
                break;
            case "profession":
                profileItemBanglaName = "profession";
                break;
            case "occupation":
                profileItemBanglaName = "occupation";
                break;
            case "professional_group":
                profileItemBanglaName = "professional_group";
                break;
            case "designation":
                profileItemBanglaName = "designation";
                break;
            case "institute":
                profileItemBanglaName = "institute";
                break;
            case "is_smile_sent":
                profileItemBanglaName = "is_smile_sent";
                break;
            case "is_favorite":
                profileItemBanglaName = "is_favorite";
                break;
            case "verifications":
                profileItemBanglaName = "verifications";
                break;
            case "mobile":
                profileItemBanglaName = "mobile";
                break;
            case "profile_living_in":
                profileItemBanglaName = "profile_living_in";
                break;
            case "country":
                profileItemBanglaName = "country";
                break;
            case "location":
                profileItemBanglaName = "location";
                break;
            case "profile_religion":
                profileItemBanglaName = "profile_religion";
                break;
            case "religion":
                profileItemBanglaName = "religion";
                break;
            case "cast":
                profileItemBanglaName = "cast";
                break;
            case "family_members":
                profileItemBanglaName = "family_members";
                break;
            case "name":
                profileItemBanglaName = "name";
                break;
            case "relation":
                profileItemBanglaName = "relation";
                break;
            case "other_information":
                profileItemBanglaName = "other_information";
                break;
            case "prayer":
                profileItemBanglaName = "prayer";
                break;
            case "fasting":
                profileItemBanglaName = "fasting";
                break;
            case "job_after_marriage":
                profileItemBanglaName = "job_after_marriage";
                break;
            case "hijab":
                profileItemBanglaName = "hijab";
                break;
            case "education_information":
                profileItemBanglaName = "education_information";
                break;
            case "institution":
                profileItemBanglaName = "institution";
                break;
            case "passing_year":
                profileItemBanglaName = "passing_year";
                break;
            case "subject":
                profileItemBanglaName = "subject";
                break;
            case "case":
                profileItemBanglaName = "case";
                break;
            case "education":
                profileItemBanglaName = "education";
                break;
            default:
                profileItemBanglaName = profileItem;
                break;
        }

        return profileItemBanglaName;

    }


}
