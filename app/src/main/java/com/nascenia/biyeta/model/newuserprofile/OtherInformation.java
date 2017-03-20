
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OtherInformation implements Serializable {

    @SerializedName("prayer")
    @Expose
    private String prayer;
    @SerializedName("fasting")
    @Expose
    private String fasting;
    @SerializedName("job_after_marriage")
    @Expose
    private String jobAfterMarriage;
    @SerializedName("hijab")
    @Expose
    private String hijab;


    @SerializedName("own_house")
    @Expose
    private String ownHouse;

    private final static long serialVersionUID = -270776123101135295L;

    public String getPrayer() {
        return prayer;
    }

    public void setPrayer(String prayer) {
        this.prayer = prayer;
    }

    public OtherInformation withPrayer(String prayer) {
        this.prayer = prayer;
        return this;
    }

    public String getFasting() {
        return fasting;
    }

    public void setFasting(String fasting) {
        this.fasting = fasting;
    }

    public OtherInformation withFasting(String fasting) {
        this.fasting = fasting;
        return this;
    }

    public String getJobAfterMarriage() {
        return jobAfterMarriage;
    }

    public void setJobAfterMarriage(String jobAfterMarriage) {
        this.jobAfterMarriage = jobAfterMarriage;
    }

    public OtherInformation withJobAfterMarriage(String jobAfterMarriage) {
        this.jobAfterMarriage = jobAfterMarriage;
        return this;
    }

    public String getHijab() {
        return hijab;
    }

    public void setHijab(String hijab) {
        this.hijab = hijab;
    }

    public OtherInformation withHijab(String hijab) {
        this.hijab = hijab;
        return this;
    }


    public String getOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(String ownHouse) {
        this.ownHouse = ownHouse;
    }

    public OtherInformation withOwnHouse(String ownHouse) {
        this.ownHouse = ownHouse;
        return this;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
