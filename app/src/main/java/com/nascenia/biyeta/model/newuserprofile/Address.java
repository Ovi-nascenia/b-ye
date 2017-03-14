
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Serializable
{

    @SerializedName("present_address")
    @Expose
    private PresentAddress presentAddress;
    @SerializedName("permanent_address")
    @Expose
    private PermanentAddress permanentAddress;
    private final static long serialVersionUID = -7981763224228299317L;

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

}
