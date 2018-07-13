
package com.nascenia.biyeta.model.newuserprofile;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Brother implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("sibling_type")
    @Expose
    private int relation_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("professional_group")
    @Expose
    private String professional_group;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("institute")
    @Expose
    private String institute;
    @SerializedName("spouse_name")
    @Expose
    private String spouseName;
    @SerializedName("spouse_occupation")
    @Expose
    private String spouseOccupation;
    @SerializedName("spouse_designation")
    @Expose
    private String spouseDesignation;
    @SerializedName("spouse_institue")
    @Expose
    private String spouseInstitue;
    private final static long serialVersionUID = 3179324841164780229L;

    protected Brother(Parcel in) {
        id = in.readInt();
        relation_id = in.readInt();
        name = in.readString();
        age = in.readInt();
        maritalStatus = in.readString();
        occupation = in.readString();
        professional_group = in.readString();
        designation = in.readString();
        institute = in.readString();
        spouseName = in.readString();
        spouseOccupation = in.readString();
        spouseDesignation = in.readString();
        spouseInstitue = in.readString();
    }

    public static final Creator<Brother> CREATOR = new Creator<Brother>() {
        @Override
        public Brother createFromParcel(Parcel in) {
            return new Brother(in);
        }

        @Override
        public Brother[] newArray(int size) {
            return new Brother[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brother withName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelationId() {
        return relation_id;
    }

    public void setRelationId(int id) {
        this.relation_id = id;
    }

    public Brother withAge(int age) {
        this.age = age;
        return this;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Brother withMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProfessionalGroup() {
        return professional_group;
    }

    public void setProfessionalGroup(String professional_group) {
        this.professional_group = professional_group;
    }

    public Brother withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Brother withDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Brother withInstitute(String institute) {
        this.institute = institute;
        return this;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public Brother withSpouseName(String spouseName) {
        this.spouseName = spouseName;
        return this;
    }

    public String getSpouseOccupation() {
        return spouseOccupation;
    }

    public void setSpouseOccupation(String spouseOccupation) {
        this.spouseOccupation = spouseOccupation;
    }

    public Brother withSpouseOccupation(String spouseOccupation) {
        this.spouseOccupation = spouseOccupation;
        return this;
    }

    public String getSpouseDesignation() {
        return spouseDesignation;
    }

    public void setSpouseDesignation(String spouseDesignation) {
        this.spouseDesignation = spouseDesignation;
    }

    public Brother withSpouseDesignation(String spouseDesignation) {
        this.spouseDesignation = spouseDesignation;
        return this;
    }

    public String getSpouseInstitue() {
        return spouseInstitue;
    }

    public void setSpouseInstitue(String spouseInstitue) {
        this.spouseInstitue = spouseInstitue;
    }

    public Brother withSpouseInstitue(String spouseInstitue) {
        this.spouseInstitue = spouseInstitue;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(relation_id);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(maritalStatus);
        parcel.writeString(occupation);
        parcel.writeString(professional_group);
        parcel.writeString(designation);
        parcel.writeString(institute);
        parcel.writeString(spouseName);
        parcel.writeString(spouseOccupation);
        parcel.writeString(spouseDesignation);
        parcel.writeString(spouseInstitue);
    }
}
