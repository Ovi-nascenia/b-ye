
package com.nascenia.biyeta.model.communication.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("height_ft")
    @Expose
    private Integer heightFt;
    @SerializedName("height_inc")
    @Expose
    private Integer heightInc;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("professional_group")
    @Expose
    private String professionalGroup;
    @SerializedName("skin_color")
    @Expose
    private String skinColor;
    @SerializedName("health")
    @Expose
    private String health;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("is_created_at")
    @Expose
    private String isCreatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(Integer heightFt) {
        this.heightFt = heightFt;
    }

    public Integer getHeightInc() {
        return heightInc;
    }

    public void setHeightInc(Integer heightInc) {
        this.heightInc = heightInc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProfessionalGroup() {
        return professionalGroup;
    }

    public void setProfessionalGroup(String professionalGroup) {
        this.professionalGroup = professionalGroup;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsCreatedAt() {
        return isCreatedAt;
    }

    public void setIsCreatedAt(String isCreatedAt) {
        this.isCreatedAt = isCreatedAt;
    }

}
