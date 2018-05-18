
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Address implements Serializable
{

    @SerializedName("same_address")
    @Expose
    private Boolean sameAddress;
    @SerializedName("present_address")
    @Expose
    private PresentAddress presentAddress;
    @SerializedName("permanent_address")
    @Expose
    private PermanentAddress permanentAddress;
    private final static long serialVersionUID = -7774737620627329511L;

    public Boolean getSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(Boolean sameAddress) {
        this.sameAddress = sameAddress;
    }

    public PresentAddress getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(PresentAddress presentAddress) {
        this.presentAddress = presentAddress;
    }

    public Address withPresentAddress(PresentAddress presentAddress) {
        this.presentAddress = presentAddress;
        return this;
    }

    public PermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Address withPermanentAddress(PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
