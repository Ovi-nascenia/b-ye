package biyeta.nas.biyeta.Model;

/**
 * Created by user on 1/6/2017.
 */

public class Profile {

    private String id;
    private String age;
    String height_ft, height_inc,display_name,occupation,professional_group,skin_color,marital_status,health,religion,cast,location,image;
//    private String profession;
//    private String age;
//    private String skin_color;
//    private String marital_status;
//    private String weight_status;
//    private String religion;
//    private String city;


    public  Profile(String id,String age,String height_ft,String height_inc,String display_name,String occupation,String professional_group,String skin_color,String marital_status,String health,String religion,String cast,String location,String image )
    {
        this.id=id;
        this.age=age;
        this.height_ft=height_ft;
        this.height_inc=height_inc;
        this.display_name=display_name;
        this.occupation=occupation;
        this.professional_group=professional_group;
        this.skin_color=skin_color;
        this.marital_status=marital_status;
        this.health=health;
        this.religion=religion;
        this.cast=cast;
        this.location=location;
        this.image=image;


    }

    public String getHeight_ft() {
        return height_ft;
    }

    public void setHeight_ft(String height_ft) {
        this.height_ft = height_ft;
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

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getHeight_inc() {
        return height_inc;
    }

    public void setHeight_inc(String height_inc) {
        this.height_inc = height_inc;
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

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
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
