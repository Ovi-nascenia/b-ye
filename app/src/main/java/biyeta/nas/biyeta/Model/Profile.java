package biyeta.nas.biyeta.Model;

/**
 * Created by user on 1/6/2017.
 */

public class Profile {

    private String id;
    private String age;
    String height_ft, height_inc,display_name,occupation,professional_group,skin_color,location,image,health;
//    private String profession;
//    private String age;
//    private String skin_color;
//    private String marital_status;
//    private String weight_status;
//    private String religion;
//    private String city;


    public  Profile(String id,String age,String height_ft,String height_inc,String display_name,String occupation,String professional_group,String skin_color,String location,String health,String image )
    {
        this.id=id;
        this.age=age;
        this.height_ft=height_ft;
        this.height_inc=height_inc;
        this.display_name=display_name;
        this.occupation=occupation;
        this.professional_group=professional_group;
        this.skin_color=skin_color;
        this.image=image;
        this.location=location;
        this.health=health;



    }

    public String getHealth() {
        return health;
    }

    public String getHeight_ft() {
        return height_ft;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }


    public String getHeight_inc() {
        return height_inc;
    }


    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProfessional_group() {
        return professional_group;
    }

    public void setProfessional_group(String professional_group) {
        this.professional_group = professional_group;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }




    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
