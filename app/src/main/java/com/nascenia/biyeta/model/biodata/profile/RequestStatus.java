
package com.nascenia.biyeta.model.biodata.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestStatus {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_request_id")
    @Expose
    private Integer profileRequestId;

    public Integer getCommunicationRequestId() {
        return communicationRequestId;
    }

    public void setCommunicationRequestId(Integer communicationRequestId) {
        this.communicationRequestId = communicationRequestId;
    }

    @SerializedName("communication_request_id")
    @Expose

    private Integer communicationRequestId;
    @SerializedName("expired")
    @Expose
    private Boolean expired;
    @SerializedName("rejected")
    @Expose
    private Boolean rejected;
    @SerializedName("accepted")
    @Expose
    private Boolean accepted;
    @SerializedName("sender")
    @Expose
    private String sender;

    @SerializedName("receiver")
    @Expose
    private String receiver;

    @SerializedName("message")
    @Expose
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProfileRequestId() {
        return profileRequestId;
    }

    public void setProfileRequestId(Integer profileRequestId) {
        this.profileRequestId = profileRequestId;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String message) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String message) {
        this.receiver = receiver;
    }

}
