
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Father implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("relation_id")
    @Expose
    private int relation_id;
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
    private final static long serialVersionUID = 2724458538623361428L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Father withName(String name) {
        this.name = name;
        return this;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public Father withRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Father withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Father withDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Father withInstitute(String institute) {
        this.institute = institute;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
