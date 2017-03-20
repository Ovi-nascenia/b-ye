
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import java.util.List;
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
    @SerializedName("family_members")
    @Expose
    private List<FamilyMember> familyMembers = null;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("other_information")
    @Expose
    private OtherInformation otherInformation;
    @SerializedName("matching_attributes")
    @Expose
    private MatchingAttributes matchingAttributes;
    @SerializedName("education_information")
    @Expose
    private List<EducationInformation> educationInformation = null;
    @SerializedName("request_status")
    @Expose
    private RequestStatus requestStatus;
    private final static long serialVersionUID = -6999151452998727246L;

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

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public Profile withFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Profile withAddress(Address address) {
        this.address = address;
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

    public List<EducationInformation> getEducationInformation() {
        return educationInformation;
    }

    public void setEducationInformation(List<EducationInformation> educationInformation) {
        this.educationInformation = educationInformation;
    }

    public Profile withEducationInformation(List<EducationInformation> educationInformation) {
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
