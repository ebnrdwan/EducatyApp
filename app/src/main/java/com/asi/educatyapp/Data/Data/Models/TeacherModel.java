package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by ASI on 2/14/2017.
 */

public class TeacherModel {


    private String name;
    private String password;
    private String feild;
    private String title;
    private String image ;
    private String idusername;
    private String email;

    public TeacherModel() {
        //required for firebase database
    }

    public TeacherModel(String email, String password, String name, String title, String feild, String idusername , String image) {
        this.password = password;
        this.name = name;
        this.feild = feild;
        this.email = email;
        this.image = image;
        this.title=title;
        this.idusername = idusername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFeild() {
        return feild;
    }

    public void setFeild(String feild) {
        this.feild = feild;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdusername() {
        return idusername;
    }

    public void setIdusername(String idusername) {
        this.idusername = idusername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
