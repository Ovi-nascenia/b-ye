
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PermanentAddress implements Serializable
{

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("district")
    @Expose
    private String district;
    private final static long serialVersionUID = -7689039370012588558L;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PermanentAddress withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PermanentAddress withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public PermanentAddress withDistrict(String district) {
        this.district = district;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
