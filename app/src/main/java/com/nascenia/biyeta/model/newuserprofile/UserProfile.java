
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserProfile implements Serializable
{

    @SerializedName("profile")
    @Expose
    @Nullable
    private Profile profile;
    private final static long serialVersionUID = -6333469655727818643L;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
