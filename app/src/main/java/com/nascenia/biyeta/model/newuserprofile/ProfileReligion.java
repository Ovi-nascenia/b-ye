
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProfileReligion implements Serializable {

    @SerializedName("religion")
    @Expose
    @Nullable
    private String religion;
    @SerializedName("cast")
    @Expose
    @Nullable
    private String cast;
    private final static long serialVersionUID = -8723543731250647893L;

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
