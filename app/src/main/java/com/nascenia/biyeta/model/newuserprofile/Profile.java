
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Profile implements Serializable
{

    @SerializedName("personal_information")
    @Expose
    private PersonalInformation personalInformation;
    @SerializedName("profession")
    @Expose
    private Profession profession;
    @SerializedName("is_smile_sent")
    @Expose
    private boolean isSmileSent;
    @SerializedName("is_favorite")
    @Expose
    private boolean isFavorite;
    @SerializedName("verifications")
    @Expose
    private Verifications verifications;
    @SerializedName("profile_living_in")
    @Expose
    private ProfileLivingIn profileLivingIn;
    @SerializedName("profile_religion")
    @Expose
    private ProfileReligion profileReligion;
    @SerializedName("other_information")
    @Expose
    private OtherInformation otherInformation;
    @SerializedName("matching_attributes")
    @Expose
    private MatchingAttributes matchingAttributes;
    @SerializedName("education_information")
    @Expose
    private EducationInformation educationInformation;
    @SerializedName("request_status")
    @Expose
    private RequestStatus requestStatus;
    private final static long serialVersionUID = -1021522188555560575L;

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public Profile withPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
        return this;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Profile withProfession(Profession profession) {
        this.profession = profession;
        return this;
    }

    public boolean isIsSmileSent() {
        return isSmileSent;
    }

    public void setIsSmileSent(boolean isSmileSent) {
        this.isSmileSent = isSmileSent;
    }

    public Profile withIsSmileSent(boolean isSmileSent) {
        this.isSmileSent = isSmileSent;
        return this;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Profile withIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        return this;
    }

    public Verifications getVerifications() {
        return verifications;
    }

    public void setVerifications(Verifications verifications) {
        this.verifications = verifications;
    }

    public Profile withVerifications(Verifications verifications) {
        this.verifications = verifications;
        return this;
    }

    public ProfileLivingIn getProfileLivingIn() {
        return profileLivingIn;
    }

    public void setProfileLivingIn(ProfileLivingIn profileLivingIn) {
        this.profileLivingIn = profileLivingIn;
    }

    public Profile withProfileLivingIn(ProfileLivingIn profileLivingIn) {
        this.profileLivingIn = profileLivingIn;
        return this;
    }

    public ProfileReligion getProfileReligion() {
        return profileReligion;
    }

    public void setProfileReligion(ProfileReligion profileReligion) {
        this.profileReligion = profileReligion;
    }

    public Profile withProfileReligion(ProfileReligion profileReligion) {
        this.profileReligion = profileReligion;
        return this;
    }

    public OtherInformation getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(OtherInformation otherInformation) {
        this.otherInformation = otherInformation;
    }

    public Profile withOtherInformation(OtherInformation otherInformation) {
        this.otherInformation = otherInformation;
        return this;
    }

    public MatchingAttributes getMatchingAttributes() {
        return matchingAttributes;
    }

    public void setMatchingAttributes(MatchingAttributes matchingAttributes) {
        this.matchingAttributes = matchingAttributes;
    }

    public Profile withMatchingAttributes(MatchingAttributes matchingAttributes) {
        this.matchingAttributes = matchingAttributes;
        return this;
    }

    public EducationInformation getEducationInformation() {
        return educationInformation;
    }

    public void setEducationInformation(EducationInformation educationInformation) {
        this.educationInformation = educationInformation;
    }

    public Profile withEducationInformation(EducationInformation educationInformation) {
        this.educationInformation = educationInformation;
        return this;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Profile withRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
