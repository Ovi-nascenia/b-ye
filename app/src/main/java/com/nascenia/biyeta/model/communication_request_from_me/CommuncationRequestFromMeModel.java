
package com.nascenia.biyeta.model.communication_request_from_me;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommuncationRequestFromMeModel {

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("current_user_signed_in")
    @Expose
    private Integer currentUserSignedIn;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentUserSignedIn() {
        return currentUserSignedIn;
    }

    public void setCurrentUserSignedIn(Integer currentUserSignedIn) {
        this.currentUserSignedIn = currentUserSignedIn;
    }

}
