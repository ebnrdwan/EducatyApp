package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by Abdulrhman on 22/02/2017.
 */

public class StudentModel {

    private String name;
    private String school;
    private String title;
    private int image;
    private String id;

    public StudentModel(String name, String id, int image) {
        this.name = name;
        this.image = image;
        this.id = id;

    }

    public StudentModel(String id,String name, String school) {
        this.name = name;
        this.school = school;
        this.id=id;
    }

    public StudentModel(String name, int image) {
        this.name = name;
        this.image = image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public StudentModel(String name, String school, String title, int image) {
        this.name = name;
        this.school = school;
        this.title = title;
        this.image = image;
    }
}
