
package com.nascenia.biyeta.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Requests implements Serializable
{

    @SerializedName("profile_request_sender_ids")
    @Expose
    private List<Integer> profileRequestSenderIds = null;
    @SerializedName("profile_request_count")
    @Expose
    private int profileRequestCount;
    @SerializedName("communication_request_sender_ids")
    @Expose
    private List<Integer> communicationRequestSenderIds = null;
    @SerializedName("communication_request_count")
    @Expose
    private int communicationRequestCount;
    private final static long serialVersionUID = -3085104288692434484L;

    public List<Integer> getProfileRequestSenderIds() {
        return profileRequestSenderIds;
    }

    public void setProfileRequestSenderIds(List<Integer> profileRequestSenderIds) {
        this.profileRequestSenderIds = profileRequestSenderIds;
    }

    public int getProfileRequestCount() {
        return profileRequestCount;
    }

    public void setProfileRequestCount(int profileRequestCount) {
        this.profileRequestCount = profileRequestCount;
    }

    public List<Integer> getCommunicationRequestSenderIds() {
        return communicationRequestSenderIds;
    }

    public void setCommunicationRequestSenderIds(List<Integer> communicationRequestSenderIds) {
        this.communicationRequestSenderIds = communicationRequestSenderIds;
    }

    public int getCommunicationRequestCount() {
        return communicationRequestCount;
    }

    public void setCommunicationRequestCount(int communicationRequestCount) {
        this.communicationRequestCount = communicationRequestCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
