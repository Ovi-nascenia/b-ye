
package com.nascenia.biyeta.model.newuserprofile;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Khalu implements Parcelable
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
    private final static long serialVersionUID = 1064018798817503737L;

    protected Khalu(Parcel in) {
        id = in.readInt();
        relation_id = in.readInt();
        name = in.readString();
        relation = in.readString();
        occupation = in.readString();
        designation = in.readString();
        institute = in.readString();
    }

    public static final Creator<Khalu> CREATOR = new Creator<Khalu>() {
        @Override
        public Khalu createFromParcel(Parcel in) {
            return new Khalu(in);
        }

        @Override
        public Khalu[] newArray(int size) {
            return new Khalu[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Khalu withName(String name) {
        this.name = name;
        return this;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Khalu withRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public int getRelationId() {
        return relation_id;
    }

    public void setRelationId(int id) {
        this.id = id;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Khalu withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Khalu withDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Khalu withInstitute(String institute) {
        this.institute = institute;
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
        parcel.writeString(relation);
        parcel.writeString(occupation);
        parcel.writeString(designation);
        parcel.writeString(institute);
    }
}
