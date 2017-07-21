
package com.nascenia.biyeta.model.loginInfromation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInformation {

    @SerializedName("login_information")
    @Expose
    private LoginInformation_ loginInformation;

    public LoginInformation_ getLoginInformation() {
        return loginInformation;
    }

    public void setLoginInformation(LoginInformation_ loginInformation) {
        this.loginInformation = loginInformation;
    }

}
