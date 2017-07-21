
package com.nascenia.biyeta.favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteProfile {

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("current_user_signed_in")
    @Expose
    private Integer currentUserSignedIn;
    @SerializedName("notification_count")
    @Expose
    private Object notificationCount;

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

    public Object getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(Object notificationCount) {
        this.notificationCount = notificationCount;
    }

}
