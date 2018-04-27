
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Brother implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
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

}
