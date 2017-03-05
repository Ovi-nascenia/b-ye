package com.nascenia.biyeta.model;

/**
 * Created by saiful on 3/5/17.
 */

public class MatchUserChoice {


    String titleName;
    String titleNameValue;

    public MatchUserChoice() {
    }


    public MatchUserChoice(String titleName, String titleNameValue) {
        this.titleName = titleName;
        this.titleNameValue = titleNameValue;
    }


    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleNameValue() {
        return titleNameValue;
    }

    public void setTitleNameValue(String titleNameValue) {
        this.titleNameValue = titleNameValue;
    }
}
