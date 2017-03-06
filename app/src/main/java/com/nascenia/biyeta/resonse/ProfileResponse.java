package com.nascenia.biyeta.resonse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.nascenia.biyeta.model.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by god father on 3/2/2017.
 */

public class ProfileResponse {

    public List<Profile> profiles;
    @SerializedName("total_page")
    public int totalPage;

}
