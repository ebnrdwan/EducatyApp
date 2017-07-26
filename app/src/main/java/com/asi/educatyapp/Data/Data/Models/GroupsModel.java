package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by ASI on 2/23/2017.
 */

public class GroupsModel {

    String name, id, tid, path;


    public GroupsModel() {
        // required for firebase databse
    }


    public GroupsModel(String id, String tid, String name, String path) {
        this.id = id;
        this.tid = tid;
        this.name = name;
        this.path = path;
    }

    public GroupsModel(String tid, String name, String path) {
        this.id = id;
        this.tid = tid;
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String gettid() {
        return tid;
    }

    public String getPath() {
        return path;
    }
}
