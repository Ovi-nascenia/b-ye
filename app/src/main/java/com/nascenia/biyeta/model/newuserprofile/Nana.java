
package com.nascenia.biyeta.model.newuserprofile;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Nana implements Parcelable
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
    private final static long serialVersionUID = 3441787034522447337L;

    protected Nana(Parcel in) {
        id = in.readInt();
        relation_id = in.readInt();
        name = in.readString();
        relation = in.readString();
        occupation = in.readString();
        designation = in.readString();
        institute = in.readString();
    }

    public static final Creator<Nana> CREATOR = new Creator<Nana>() {
        @Override
        public Nana createFromParcel(Parcel in) {
            return new Nana(in);
        }

        @Override
        public Nana[] newArray(int size) {
            return new Nana[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nana withName(String name) {
        this.name = name;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getRelationId() {
        return relation_id;
    }

    public void setRelationId(int id) {
        this.id = id;
    }

    public Nana withRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Nana withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Nana withDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Nana withInstitute(String institute) {
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
