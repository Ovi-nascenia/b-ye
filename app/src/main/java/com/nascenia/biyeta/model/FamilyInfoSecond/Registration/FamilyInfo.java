package com.nascenia.biyeta.model.FamilyInfoSecond.Registration;

/**
 * Created by masum on 10/13/2017.
 */

public class FamilyInfo {

    int totalBrothers;
    int totalSisters;
    BrotherSisterInformation[] brotherSisterInformations;
    OtherMemberInfo[] otherMemberInfo;

    public int getTotalBrothers() {
        return totalBrothers;
    }

    public void setTotalBrothers(int totalBrothers) {
        this.totalBrothers = totalBrothers;
    }

    public int getTotalSisters() {
        return totalSisters;
    }

    public void setTotalSisters(int totalSisters) {
        this.totalSisters = totalSisters;
    }

    public BrotherSisterInformation[] getBrotherSisterInformations() {
        return brotherSisterInformations;
    }

    public void setBrotherSisterInformations(BrotherSisterInformation[] brotherSisterInformations) {
        this.brotherSisterInformations = brotherSisterInformations;
    }

    public OtherMemberInfo[] getOtherMemberInfo() {
        return otherMemberInfo;
    }

    public void setOtherMemberInfo(OtherMemberInfo[] otherMemberInfo) {
        this.otherMemberInfo = otherMemberInfo;
    }


}