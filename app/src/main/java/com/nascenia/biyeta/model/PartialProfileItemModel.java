package com.nascenia.biyeta.model;

/**
 * Created by god father on 2/24/2017.
 */

public class PartialProfileItemModel {
    String key;
    String value;
    public PartialProfileItemModel(String key,String value)
    {
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
