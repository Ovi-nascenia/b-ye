
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PersonalInformation implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("height_ft")
    @Expose
    private int heightFt;
    @SerializedName("height_inc")
    @Expose
    private int heightInc;
    @SerializedName("about_yourself")
    @Expose
    private String aboutYourself;
    @SerializedName("skin_color")
    @Expose
    private String skinColor;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("gender")
    @Expose
    private String gender;


    private final static long serialVersionUID = -6389523718871776889L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PersonalInformation withId(long id) {
        this.id = id;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PersonalInformation withAge(int age) {
        this.age = age;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public PersonalInformation withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public int getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(int heightFt) {
        this.heightFt = heightFt;
    }

    public PersonalInformation withHeightFt(int heightFt) {
        this.heightFt = heightFt;
        return this;
    }

    public int getHeightInc() {
        return heightInc;
    }

    public void setHeightInc(int heightInc) {
        this.heightInc = heightInc;
    }

    public PersonalInformation withHeightInc(int heightInc) {
        this.heightInc = heightInc;
        return this;
    }

    public String getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public PersonalInformation withAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
        return this;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public PersonalInformation withSkinColor(String skinColor) {
        this.skinColor = skinColor;
        return this;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public PersonalInformation withWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public PersonalInformation withMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PersonalInformation withImage(String image) {
        this.image = image;
        return this;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PersonalInformation withGender(String gender) {
        this.gender = gender;
        return this;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
