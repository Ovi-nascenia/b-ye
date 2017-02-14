package biyeta.nas.biyeta.Model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by saiful on 2/12/17.
 */

public class UserProfile implements Parent<UserProfileChild> {

    private List<UserProfileChild> mUserProfileChild;
    private String titleParentname;

    public UserProfile(String titleParentname,List<UserProfileChild> mUserProfileChild) {
        this.titleParentname = titleParentname;
        this.mUserProfileChild = mUserProfileChild;
    }

    @Override
    public List<UserProfileChild> getChildList() {
        return mUserProfileChild;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


    public String getTitleParentname() {
        return titleParentname;
    }

    public void setTitleParentname(String titleParentname) {
        this.titleParentname = titleParentname;
    }
}
