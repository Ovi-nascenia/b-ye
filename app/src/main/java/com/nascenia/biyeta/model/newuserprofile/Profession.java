
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Profession implements Serializable {

    @SerializedName("occupation")
    @Expose
    @Nullable
    private String occupation;
    @SerializedName("professional_group")
    @Expose
    @Nullable
    private String professionalGroup;
    @SerializedName("designation")
    @Expose
    @Nullable
    private Object designation;
    @SerializedName("institute")
    @Expose
    @Nullable
    private Object institute;
    private final static long serialVersionUID = -5382033363545158755L;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProfessionalGroup() {
        return professionalGroup;
    }

    public void setProfessionalGroup(String professionalGroup) {
        this.professionalGroup = professionalGroup;
    }

    public Object getDesignation() {
        return designation;
    }

    public void setDesignation(Object designation) {
        this.designation = designation;
    }

    public Object getInstitute() {
        return institute;
    }

    public void setInstitute(Object institute) {
        this.institute = institute;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
