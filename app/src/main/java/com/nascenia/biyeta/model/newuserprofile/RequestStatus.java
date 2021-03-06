
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RequestStatus implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_request_id")
    @Expose
    private int profileRequestId;
    @SerializedName("communication_request_id")
    @Expose
    private Integer communicationRequestId;
    @SerializedName("sender")
    @Expose
    private Integer sender;
    @SerializedName("receiver")
    @Expose
    private Integer receiver;
    @SerializedName("accepted")
    @Expose
    private boolean accepted;
    @SerializedName("rejected")
    @Expose
    private boolean rejected;
    @SerializedName("expired")
    @Expose
    private boolean expired;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sent")
    @Expose
    private boolean sent;

    private final static long serialVersionUID = 4465158792950174515L;

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestStatus withName(String name) {
        this.name = name;
        return this;
    }

    public int getProfileRequestId() {
        return profileRequestId;
    }

    public void setProfileRequestId(int profileRequestId) {
        this.profileRequestId = profileRequestId;
    }

    public RequestStatus withProfileRequestId(int profileRequestId) {
        this.profileRequestId = profileRequestId;
        return this;
    }

    public Integer getCommunicationRequestId() {
        return communicationRequestId;
    }

    public void setCommunicationRequestId(Integer communicationRequestId) {
        this.communicationRequestId = communicationRequestId;
    }

    public RequestStatus withCommunicationRequestId(Integer communicationRequestId) {
        this.communicationRequestId = communicationRequestId;
        return this;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public RequestStatus withSender(Integer sender) {
        this.sender = sender;
        return this;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public RequestStatus withReceiver(Integer receiver) {
        this.receiver = receiver;
        return this;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public RequestStatus withAccepted(boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public RequestStatus withRejected(boolean rejected) {
        this.rejected = rejected;
        return this;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public RequestStatus withExpired(boolean expired) {
        this.expired = expired;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RequestStatus withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
