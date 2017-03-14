
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile implements Serializable
{

    @SerializedName("profile")
    @Expose
    private Profile profile;
    private final static long serialVersionUID = -2478245804928241915L;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public UserProfile withProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

}
