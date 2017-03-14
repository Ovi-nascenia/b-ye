
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileLivingIn implements Serializable
{

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("location")
    @Expose
    private String location;
    private final static long serialVersionUID = 1348916429891203464L;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ProfileLivingIn withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProfileLivingIn withLocation(String location) {
        this.location = location;
        return this;
    }

}
