
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class EducationInformation implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("institution")
    @Expose
    private String institution;
    @SerializedName("passing_year")
    @Expose
    private Integer passingYear;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("highest_degree")
    @Expose
    private String highestDegree;

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    private final static long serialVersionUID = 4585354323670632578L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EducationInformation withName(String name) {
        this.name = name;
        return this;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public EducationInformation withInstitution(String institution) {
        this.institution = institution;
        return this;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public EducationInformation withPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EducationInformation withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
