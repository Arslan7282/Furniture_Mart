package com.example.finalyearproject.Models;

public class Aboutmodel {

    String appinfo,developer;


    public Aboutmodel() {
    }

    public Aboutmodel(String appinfo, String developer) {
        this.appinfo = appinfo;
        this.developer = developer;
    }

    public String getAppinfo() {
        return appinfo;
    }

    public void setAppinfo(String appinfo) {
        this.appinfo = appinfo;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
