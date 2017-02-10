package biyeta.nas.biyeta.model;

/**
 * Created by saiful on 2/8/17.
 */

public class UserProfileInfo {

    private String title;
    private String titleResult;

    public UserProfileInfo(String title, String titleResult) {
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
}
