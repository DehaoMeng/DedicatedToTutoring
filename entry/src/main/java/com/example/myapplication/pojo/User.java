package com.example.myapplication.pojo;

import ohos.media.image.PixelMap;

import java.util.Objects;

public class User {
    private String ID;
    private PixelMap img;
    private String name;
    private String password;
    private String token;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String ID, String name, String password) {
        this.ID = ID;
        this.name = name;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String  ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PixelMap getImg() {
        return img;
    }

    public void setImg(PixelMap img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID && Objects.equals(img, user.img) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(token, user.token);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", img=" + img +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
