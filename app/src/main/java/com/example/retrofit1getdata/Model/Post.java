package com.example.retrofit1getdata.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;

public class Post {
    private int userId;

    private Integer id;

    private String title;

    @SerializedName("body")
    private String body;

    public Post(int id,int userId, String title, String body) {
        this.id=id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Post(int userId, Integer id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
