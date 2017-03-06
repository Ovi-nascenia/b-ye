
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PersonalInformation implements Serializable {

    @SerializedName("id")
    @Expose
    @Nullable
    private long id;
    @SerializedName("age")
    @Expose
    @Nullable
    private long age;
    @SerializedName("display_name")
    @Expose
    @Nullable
    private String displayName;
    @SerializedName("height_ft")
    @Expose
    @Nullable
    private long heightFt;
    @SerializedName("height_inc")
    @Expose
    @Nullable
    private long heightInc;
    @SerializedName("about_yourself")
    @Expose
    @Nullable
    private String aboutYourself;
    @SerializedName("image")
    @Expose
    @Nullable
    private Object image;
    private final static long serialVersionUID = -8066021308856533522L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(long heightFt) {
        this.heightFt = heightFt;
    }

    public long getHeightInc() {
        return heightInc;
    }

    public void setHeightInc(long heightInc) {
        this.heightInc = heightInc;
    }

    public String getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
