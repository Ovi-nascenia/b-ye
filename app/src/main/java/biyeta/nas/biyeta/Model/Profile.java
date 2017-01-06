package biyeta.nas.biyeta.Model;

/**
 * Created by user on 1/6/2017.
 */

public class Profile {

    private String user_name;
//    private String profession;
//    private String age;
//    private String skin_color;
//    private String marital_status;
//    private String weight_status;
//    private String religion;
//    private String city;


    public  Profile(String user_name)
    {
        this.user_name=user_name;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
