package com.xd.batch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.xd.batch.annotation.UniqueKey;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    @JsonProperty("w")
    @SerializedName("nu")
    @UniqueKey(group = "1",sort = 1)
    private String userName;
    @SerializedName("pw")
    @JsonProperty("p")
    @UniqueKey(group = "1",sort = 2)
    private String password;
    @SerializedName("age")
    @JsonProperty("age")

    private int age;

    private List<User> list = new ArrayList<>();

    public List<User> getList() {
        return list;
    }

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
