
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("rejected")
    @Expose
    private Object rejected;
    @SerializedName("message")
    @Expose
    private Object message;
    private final static long serialVersionUID = -1309112087272299807L;

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

    public Object getRejected() {
        return rejected;
    }

    public void setRejected(Object rejected) {
        this.rejected = rejected;
    }

    public RequestStatus withRejected(Object rejected) {
        this.rejected = rejected;
        return this;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public RequestStatus withMessage(Object message) {
        this.message = message;
        return this;
    }

}
