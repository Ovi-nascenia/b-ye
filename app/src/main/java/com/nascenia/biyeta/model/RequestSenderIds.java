
package com.nascenia.biyeta.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RequestSenderIds implements Serializable
{

    @SerializedName("requests")
    @Expose
    private Requests requests;
    private final static long serialVersionUID = 1232480464431582700L;

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
