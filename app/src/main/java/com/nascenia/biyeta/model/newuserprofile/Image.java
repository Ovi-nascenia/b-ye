
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Serializable
{

    @SerializedName("other")
    @Expose
    private List<String> other = null;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    private final static long serialVersionUID = 4929199835408264296L;

    public List<String> getOther() {
        return other;
    }

    public void setOther(List<String> other) {
        this.other = other;
    }

    public Image withOther(List<String> other) {
        this.other = other;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Image withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

}
