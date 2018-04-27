package com.nascenia.biyeta.model;

/**
 * Created by saiful on 2/8/17.
 */

public class UserProfileChild {

    private String title;
    private String titleResult;
    private boolean isEditable = false;
    private  int id;

    public UserProfileChild(String title, String titleResult) {
        this.title = title;
        this.titleResult = titleResult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleResult() {
        return titleResult;
    }

    public void setTitleResult(String titleResult) {
        this.titleResult = titleResult;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
