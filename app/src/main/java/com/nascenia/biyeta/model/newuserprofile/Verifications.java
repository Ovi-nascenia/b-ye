
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verifications implements Serializable
{

    @SerializedName("mobile")
    @Expose
    private boolean mobile;
    private final static long serialVersionUID = 3191843834865832048L;

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

}
