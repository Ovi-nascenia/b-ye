
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Verifications implements Serializable
{

    @SerializedName("mobile")
    @Expose
    private boolean mobile;

    @SerializedName("facebook")
    @Expose
    private boolean facebook;
    @SerializedName("email")
    @Expose
    private boolean email;


    private final static long serialVersionUID = 4042972474749225979L;

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public Verifications withMobile(boolean mobile) {
        this.mobile = mobile;
        return this;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
