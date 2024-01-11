package com.example.finalyearproject.Models;

public class Todayoffermodel {

    String desc,id, img,name,dprice;

    public Todayoffermodel() {
    }

    public Todayoffermodel(String desc, String id, String img, String name, String dprice) {
        this.desc = desc;
        this.id = id;
        this.img = img;
        this.name = name;
        this.dprice = dprice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDprice() {
        return dprice;
    }

    public void setDprice(String dprice) {
        this.dprice = dprice;
    }
}
