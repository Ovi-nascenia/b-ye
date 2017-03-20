
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
    @SerializedName("mobile_verified")
    @Expose
    private Boolean mobileVerified;

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

    public Boolean getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

}
