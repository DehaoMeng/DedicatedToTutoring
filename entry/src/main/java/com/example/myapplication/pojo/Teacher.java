package com.example.myapplication.pojo;

import ohos.media.image.PixelMap;

public class Teacher {
    private PixelMap img;
    private String name;
    private String subject;
    private String locate;
    private String grade;

    public Teacher(PixelMap img, String name, String subject, String locate, String grade) {
        this.img = img;
        this.name = name;
        this.subject = subject;
        this.locate = locate;
        this.grade =grade;
    }

    public PixelMap getImg() {
        return img;
    }

    public void setImg(PixelMap img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }
}
