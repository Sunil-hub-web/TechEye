package com.example.techeye.subscriber.viewalldetails;

public class UserId_ModelClass {

    String id;

    public UserId_ModelClass() {
    }

    public UserId_ModelClass(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                '}';
    }
}
