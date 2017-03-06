
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RequestStatus implements Serializable {

    @SerializedName("name")
    @Expose
    @Nullable
    private String name;
    @SerializedName("sent")
    @Expose
    @Nullable
    private boolean sent;
    @SerializedName("accepted")
    @Expose
    @Nullable
    private boolean accepted;
    private final static long serialVersionUID = -3907972791505742892L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
