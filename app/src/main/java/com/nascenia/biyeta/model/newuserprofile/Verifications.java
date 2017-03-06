
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Verifications implements Serializable {

    @SerializedName("mobile")
    @Expose
    @Nullable
    private boolean mobile;
    private final static long serialVersionUID = 2219073339329234033L;

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
