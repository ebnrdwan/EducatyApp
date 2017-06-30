package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by Abdulrhman on 22/02/2017.
 */

public class StudentModel {

    private String password;
    private String name;
    private String school;
    private String email;
    private String image;
    private String idusername;

    //constructors
    public StudentModel(String email,String password, String name, String school,  String idusername ,String image) {
        this.password = password;
        this.name = name;
        this.school = school;
        this.email = email;
        this.image = image;
        this.idusername = idusername;
    }

    public StudentModel( String idusername,String name, String school, String email, String image) {
        this.name = name;
        this.school = school;
        this.email = email;
        this.image = image;
        this.idusername = idusername;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
