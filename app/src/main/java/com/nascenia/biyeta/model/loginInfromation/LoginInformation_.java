
package com.nascenia.biyeta.model.loginInfromation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInformation_ {

    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("current_user_signed_in")
    @Expose
    private Integer currentUserSignedIn;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("mobile_verified")
    @Expose
    private Boolean mobileVerified;


    @SerializedName("is_complete")
    @Expose
    private Boolean isProfileComplete;


    @SerializedName("current_mb_sign_up_step")
    @Expose
    private int currentMbSignUpStep;

    @SerializedName("religion")
    @Expose
    private int religion;

    public Boolean getProfileComplete() {
        return isProfileComplete;
    }

    public void setProfileComplete(Boolean profileComplete) {
        isProfileComplete = profileComplete;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Integer getCurrentUserSignedIn() {
        return currentUserSignedIn;
    }

    public void setCurrentUserSignedIn(Integer currentUserSignedIn) {
        this.currentUserSignedIn = currentUserSignedIn;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void setRealName(String displayName) {
        this.realName = realName;
    }

    public Boolean getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public int getStep() {
        return currentMbSignUpStep;
    }

    public void setStep(int step) {
        this.currentMbSignUpStep = step;
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }

}
