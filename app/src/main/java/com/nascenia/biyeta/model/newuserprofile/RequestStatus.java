
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RequestStatus implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sent")
    @Expose
    private boolean sent;
    @SerializedName("accepted")
    @Expose
    private boolean accepted;
    private final static long serialVersionUID = 6237132305173899215L;

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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public RequestStatus withSent(boolean sent) {
        this.sent = sent;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
