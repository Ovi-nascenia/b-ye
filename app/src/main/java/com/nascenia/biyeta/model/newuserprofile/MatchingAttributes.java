
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MatchingAttributes implements Serializable
{

    @SerializedName("home_town")
    @Expose
    private String homeTown;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("skin_color")
    @Expose
    private String skinColor;
    @SerializedName("health")
    @Expose
    private String health;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("title_educational_qualification")
    @Expose
    private String titleEducationalQualification;
    @SerializedName("title_own_house")
    @Expose
    private String titleOwnHouse;
    @SerializedName("title_occupation")
    @Expose
    private String titleOccupation;
    private final static long serialVersionUID = 7533326798525734417L;

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public MatchingAttributes withHomeTown(String homeTown) {
        this.homeTown = homeTown;
        return this;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public MatchingAttributes withAge(String age) {
        this.age = age;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public MatchingAttributes withHeight(String height) {
        this.height = height;
        return this;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public MatchingAttributes withSkinColor(String skinColor) {
        this.skinColor = skinColor;
        return this;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public MatchingAttributes withHealth(String health) {
        this.health = health;
        return this;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public MatchingAttributes withMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public String getTitleEducationalQualification() {
        return titleEducationalQualification;
    }

    public void setTitleEducationalQualification(String titleEducationalQualification) {
        this.titleEducationalQualification = titleEducationalQualification;
    }

    public MatchingAttributes withTitleEducationalQualification(String titleEducationalQualification) {
        this.titleEducationalQualification = titleEducationalQualification;
        return this;
    }

    public String getTitleOwnHouse() {
        return titleOwnHouse;
    }

    public void setTitleOwnHouse(String titleOwnHouse) {
        this.titleOwnHouse = titleOwnHouse;
    }

    public MatchingAttributes withTitleOwnHouse(String titleOwnHouse) {
        this.titleOwnHouse = titleOwnHouse;
        return this;
    }

    public String getTitleOccupation() {
        return titleOccupation;
    }

    public void setTitleOccupation(String titleOccupation) {
        this.titleOccupation = titleOccupation;
    }

    public MatchingAttributes withTitleOccupation(String titleOccupation) {
        this.titleOccupation = titleOccupation;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
