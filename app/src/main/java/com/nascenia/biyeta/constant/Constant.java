package com.nascenia.biyeta.constant;

import com.nascenia.biyeta.utils.Utils;

/**
 * Created by user on 1/9/2017.
 */

public class Constant {

    public static final int SPLASH_TIMEOUT = 3000;

    public static final String BASE_URL = Utils.Base_URL+"/api/v1/";

    private static String profileItemBanglaName = "";

    public static String profileItemBanglaName(String profileItem) {

        switch (profileItem) {
            case "personal_information":
            case "id":
            case "age":
            case "display_name":
            case "height_ft":
            case "height_inc":
            case "about_yourself":
            case "image":
            case "real_name":
            case "skin_color":
            case "weight":
            case "blood_group":
            case "marital_status":
            case "disabilities":
            case "disabilities_description":
            case "smoking":
            case "profession":
            case "occupation":
            case "professional_group":
            case "designation":
            case "institute":
            case "is_smile_sent":
            case "is_favorite":
            case "verifications":
            case "mobile":
            case "profile_living_in":
            case "country":
            case "location":
            case "profile_religion":
            case "religion":
            case "cast":
            case "family_members":
            case "name":
            case "relation":
            case "other_information":
            case "prayer":
            case "fasting":
            case "job_after_marriage":
            case "hijab":
            case "education_information":
            case "institution":
            case "passing_year":
            case "subject":
            case "case":


        }

        return profileItemBanglaName;

    }


}
