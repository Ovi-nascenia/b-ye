package com.nascenia.biyeta.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 1/6/2017.
 */

public class Profile {

    @SerializedName("id")
    public int id;

    @SerializedName("age")
    public int age;

    @SerializedName("height_ft")
    public int heightFt;

    @SerializedName("height_inc")
    public int heightInc;

    @SerializedName("display_name")
    public String displayName;

    @SerializedName("occupation")
    public String occupation;

    @SerializedName("professional_group")
    public String professionalGroup;

    @SerializedName("skin_color")
    public String skinColor;



    @SerializedName("health")
    public String health;

    @SerializedName("location")
    public String location;

    @SerializedName("image")
    public String image;


}
