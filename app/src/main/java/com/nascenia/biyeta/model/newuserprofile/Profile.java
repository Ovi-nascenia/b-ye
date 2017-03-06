
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Profile implements Serializable {

    @SerializedName("personal_information")
    @Expose
    @Nullable
    private PersonalInformation personalInformation;
    @SerializedName("profession")
    @Expose
    @Nullable
    private Profession profession;
    @SerializedName("is_smile_sent")
    @Expose
    @Nullable
    private boolean isSmileSent;
    @SerializedName("is_favorite")
    @Expose
    @Nullable
    private boolean isFavorite;
    @SerializedName("verifications")
    @Expose
    @Nullable
    private Verifications verifications;
    @SerializedName("profile_living_in")
    @Expose
    @Nullable
    private ProfileLivingIn profileLivingIn;
    @SerializedName("profile_religion")
    @Expose
    @Nullable
    private ProfileReligion profileReligion;
    @SerializedName("matching_attributes")
    @Expose
    @Nullable
    private MatchingAttributes matchingAttributes;
    @SerializedName("education_information")
    @Expose
    @Nullable
    private EducationInformation educationInformation;
    @SerializedName("request_status")
    @Expose
    @Nullable
    private RequestStatus requestStatus;
    private final static long serialVersionUID = -7681542115577339158L;

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public boolean isIsSmileSent() {
        return isSmileSent;
    }

    public void setIsSmileSent(boolean isSmileSent) {
        this.isSmileSent = isSmileSent;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Verifications getVerifications() {
        return verifications;
    }

    public void setVerifications(Verifications verifications) {
        this.verifications = verifications;
    }

    public ProfileLivingIn getProfileLivingIn() {
        return profileLivingIn;
    }

    public void setProfileLivingIn(ProfileLivingIn profileLivingIn) {
        this.profileLivingIn = profileLivingIn;
    }

    public ProfileReligion getProfileReligion() {
        return profileReligion;
    }

    public void setProfileReligion(ProfileReligion profileReligion) {
        this.profileReligion = profileReligion;
    }

    public MatchingAttributes getMatchingAttributes() {
        return matchingAttributes;
    }

    public void setMatchingAttributes(MatchingAttributes matchingAttributes) {
        this.matchingAttributes = matchingAttributes;
    }

    public EducationInformation getEducationInformation() {
        return educationInformation;
    }

    public void setEducationInformation(EducationInformation educationInformation) {
        this.educationInformation = educationInformation;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
