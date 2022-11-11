package com.example.myapplication.pojo;

public class IndexImg {
    private int indexImg;
    private int indexResourceId;
    private String indexImgPath;
    private String indexImgType;
    private String indexImgUrl;

    public IndexImg() {
    }

    public IndexImg(int indexResourceId) {
        this.indexResourceId = indexResourceId;
    }


    public int getIndexImg() {
        return indexImg;
    }

    public void setIndexImg(int indexImg) {
        this.indexImg = indexImg;
    }

    public int getIndexResourceId() {
        return indexResourceId;
    }

    public void setIndexResourceId(int indexResourceId) {
        this.indexResourceId = indexResourceId;
    }

    public String getIndexImgPath() {
        return indexImgPath;
    }

    public void setIndexImgPath(String indexImgPath) {
        this.indexImgPath = indexImgPath;
    }

    public String getIndexImgType() {
        return indexImgType;
    }

    public void setIndexImgType(String indexImgType) {
        this.indexImgType = indexImgType;
    }

    public String getIndexImgUrl() {
        return indexImgUrl;
    }

    public void setIndexImgUrl(String indexImgUrl) {
        this.indexImgUrl = indexImgUrl;
    }
}
