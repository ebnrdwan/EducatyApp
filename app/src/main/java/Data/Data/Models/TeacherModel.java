package Models;

/**
 * Created by ASI on 2/14/2017.
 */

public class TeacherModel {

    private String id ;
    private String name;
    private String Title;
    private String school;
    private String field;
    private int image ;
    private String num;

    private String imagepath;
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public TeacherModel(String name, String title, int image) {
        this.name = name;
        Title = title;
        this.image = image;
    }



    public TeacherModel(String id, String name, String title, String school, String field,String num ,String imagepath) {
        this.id = id;
        this.name = name;
        Title = title;
        this.school = school;
        this.field = field;
        this.imagepath = imagepath;
        this.num=num;
    }

    public String getNum() {
        return num;
    }

    public String getId() {
        return id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public TeacherModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
