package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by Abdulrhman on 27/02/2017.
 */

public class CommentModel {

    String name;
    String image;
    String comment;
    String time;
    String id,pid;

    public CommentModel(String id,String pid,String name, String image, String comment, String time) {
        this.name = name;
        this.image = image;
        this.comment = comment;
        this.time = time;
        this.id=id;
        this.pid=pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getPid() {
        return pid;
    }
}
