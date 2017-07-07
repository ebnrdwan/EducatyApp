package com.asi.educatyapp.Data.Data.Models;

/**
 * Created by ASI on 2/14/2017.
 */

public class PostModel {
    static int i = 0;
    String name, time, content, id, profile, contentpic;

    public PostModel(String name) {
        this.name = name;
    }

    public PostModel(String id, String name, String content, String time, String profile, String contentpic) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.profile = profile;
        this.contentpic = contentpic;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }

    public String getContentpic() {
        return contentpic;
    }

    public static String useIdPost() {
        i++;
        String postId = "post" + i;

        return postId;


    }


}
