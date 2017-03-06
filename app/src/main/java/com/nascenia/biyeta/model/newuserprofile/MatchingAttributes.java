
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MatchingAttributes implements Serializable {

    @SerializedName("home_town")
    @Expose
    @Nullable
    private String homeTown;
    @SerializedName("age")
    @Expose
    @Nullable
    private String age;
    @SerializedName("height")
    @Expose
    @Nullable
    private String height;
    @SerializedName("skin_color")
    @Expose
    @Nullable
    private String skinColor;
    @SerializedName("health")
    @Expose
    @Nullable
    private String health;
    @SerializedName("marital_status")
    @Expose
    @Nullable
    private String maritalStatus;
    @SerializedName("title_educational_qualification")
    @Expose
    @Nullable
    private String titleEducationalQualification;
    @SerializedName("title_own_house")
    @Expose
    @Nullable
    private String titleOwnHouse;
    @SerializedName("title_occupation")
    @Expose
    @Nullable
    private String titleOccupation;
    private final static long serialVersionUID = 3228585704972534132L;

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getTitleEducationalQualification() {
        return titleEducationalQualification;
    }

    public void setTitleEducationalQualification(String titleEducationalQualification) {
        this.titleEducationalQualification = titleEducationalQualification;
    }

    public String getTitleOwnHouse() {
        return titleOwnHouse;
    }

    public void setTitleOwnHouse(String titleOwnHouse) {
        this.titleOwnHouse = titleOwnHouse;
    }

    public String getTitleOccupation() {
        return titleOccupation;
    }

    public void setTitleOccupation(String titleOccupation) {
        this.titleOccupation = titleOccupation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
