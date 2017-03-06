
package com.nascenia.biyeta.model.newuserprofile;

import android.support.annotation.Nullable;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EducationInformation implements Serializable
{

    @SerializedName("highest_degree")
    @Expose
    @Nullable
    private String highestDegree;
    @SerializedName("institution")
    @Expose
    @Nullable
    private Object institution;
    private final static long serialVersionUID = -3939793493706824519L;

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    public Object getInstitution() {
        return institution;
    }

    public void setInstitution(Object institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
