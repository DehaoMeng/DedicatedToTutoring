package com.example.myapplication.pojo;

/**
 * @author ITApeDeHao
 * @date 2022/10/2 16 17
 * discription
 */
public class Award {
    private String phonenum;
    private String account;
    private String data;
    public Award(){
    }

    public Award(String phonenum, String account, String data) {
        this.phonenum = phonenum;
        this.account = account;
        this.data = data;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
