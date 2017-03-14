package com.nascenia.biyeta.model.conversation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class TempMessage {


    private Integer id;

    private Integer userId;

    private Integer receiver;

    private String text;

    private String createdAt;

    private Boolean isSeen;


    public TempMessage(String message,String createdAt)
    {
        this.text=message;
        this.createdAt=createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Boolean isSeen) {
        this.isSeen = isSeen;
    }
}
