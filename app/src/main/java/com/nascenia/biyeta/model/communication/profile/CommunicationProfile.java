
package com.nascenia.biyeta.model.communication.profile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunicationProfile {

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("current_user_signed_in")
    @Expose
    private Integer currentUserSignedIn;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Integer getCurrentUserSignedIn() {
        return currentUserSignedIn;
    }

    public void setCurrentUserSignedIn(Integer currentUserSignedIn) {
        this.currentUserSignedIn = currentUserSignedIn;
    }

}
