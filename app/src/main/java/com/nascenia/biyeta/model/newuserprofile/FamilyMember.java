
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyMember implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("institute")
    @Expose
    private String institute;
    private final static long serialVersionUID = -3013553689476022244L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FamilyMember withName(String name) {
        this.name = name;
        return this;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public FamilyMember withRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public FamilyMember withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public FamilyMember withDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public FamilyMember withInstitute(String institute) {
        this.institute = institute;
        return this;
    }

}
