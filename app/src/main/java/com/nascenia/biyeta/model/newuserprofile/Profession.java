
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Profession implements Serializable
{

    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("professional_group")
    @Expose
    private String professionalGroup;
    @SerializedName("designation")
    @Expose
    private Object designation;
    @SerializedName("institute")
    @Expose
    private Object institute;
    private final static long serialVersionUID = -8465011399775391739L;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Profession withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getProfessionalGroup() {
        return professionalGroup;
    }

    public void setProfessionalGroup(String professionalGroup) {
        this.professionalGroup = professionalGroup;
    }

    public Profession withProfessionalGroup(String professionalGroup) {
        this.professionalGroup = professionalGroup;
        return this;
    }

    public Object getDesignation() {
        return designation;
    }

    public void setDesignation(Object designation) {
        this.designation = designation;
    }

    public Profession withDesignation(Object designation) {
        this.designation = designation;
        return this;
    }

    public Object getInstitute() {
        return institute;
    }

    public void setInstitute(Object institute) {
        this.institute = institute;
    }

    public Profession withInstitute(Object institute) {
        this.institute = institute;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
