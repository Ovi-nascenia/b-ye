
package com.nascenia.biyeta.model.newuserprofile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EducationInformation implements Serializable
{

    @SerializedName("highest_degree")
    @Expose
    private String highestDegree;
    @SerializedName("institution")
    @Expose
    private String institution;
    private final static long serialVersionUID = -3624863976886663111L;

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    public EducationInformation withHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
        return this;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public EducationInformation withInstitution(String institution) {
        this.institution = institution;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
