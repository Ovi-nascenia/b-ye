package com.nascenia.biyeta.model;

/**
 * Created by saiful on 3/5/17.
 */

public class GeneralInformation {

    private String generalInfo;
    private Integer itemImageDrwableId;


    public GeneralInformation() {
    }

    public GeneralInformation(String generalInfo, Integer itemImageDrwableId) {
        this.generalInfo = generalInfo;
        this.itemImageDrwableId = itemImageDrwableId;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }


    public Integer getItemImageDrwableId() {
        return itemImageDrwableId;
    }

    public void setItemImageDrwableId(Integer itemImageDrwableId) {
        this.itemImageDrwableId = itemImageDrwableId;
    }
}
