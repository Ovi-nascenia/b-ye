
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProfileReligion implements Serializable
{

    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("cast")
    @Expose
    private String cast;
    private final static long serialVersionUID = -3305052300264536750L;

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public ProfileReligion withReligion(String religion) {
        this.religion = religion;
        return this;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public ProfileReligion withCast(String cast) {
        this.cast = cast;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
