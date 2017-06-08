package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by ASI on 2/23/2017.
 */

public class groupsModel {

    String name,id,sid,path;


    public groupsModel(String id, String sid, String name, String path) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getPath() {
        return path;
    }
}
