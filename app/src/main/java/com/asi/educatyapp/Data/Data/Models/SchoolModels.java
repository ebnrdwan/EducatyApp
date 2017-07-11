package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by ASI on 2/26/2017.
 */

public class SchoolModels {
    String id ;
    String name;

    public SchoolModels() {
        //required for firebase database
    }

    public SchoolModels(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
