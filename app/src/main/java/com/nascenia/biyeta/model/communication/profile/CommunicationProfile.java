
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


    @SerializedName("total_page")
    @Expose
    private Integer totalPage;


    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer total_page) {
        this.totalPage = total_page;
    }



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
