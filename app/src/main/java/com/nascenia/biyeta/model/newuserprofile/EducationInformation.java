
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationInformation implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("institution")
    @Expose
    private String institution;
    @SerializedName("passing_year")
    @Expose
    private String passingYear;
    @SerializedName("subject")
    @Expose
    private String subject;
    private final static long serialVersionUID = -8609689958968348325L;

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

    public String getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear = passingYear;
    }

    public EducationInformation withPassingYear(String passingYear) {
        this.passingYear = passingYear;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EducationInformation withSubject(String subject) {
        this.subject = subject;
        return this;
    }

}
