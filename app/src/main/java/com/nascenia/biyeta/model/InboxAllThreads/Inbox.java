
package com.nascenia.biyeta.model.InboxAllThreads;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inbox {

    @SerializedName("sender_name")
    @Expose
    private String senderName;
    @SerializedName("sender_image")
    @Expose
    @Nullable
    private String senderImage;
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("unread")
    @Expose
    private Integer unread;

    @SerializedName("user_status")
    @Expose
    private Integer userStatus;

    public String getSenderName(){
        return senderName;
    }

    public void setSenderName(String senderName){
        this.senderName = senderName;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }



}
