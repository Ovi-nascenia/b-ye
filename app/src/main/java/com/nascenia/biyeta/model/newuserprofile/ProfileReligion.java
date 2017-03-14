
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileReligion implements Serializable
{

    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("cast")
    @Expose
    private String cast;
    private final static long serialVersionUID = 9113879827946037412L;

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

}
