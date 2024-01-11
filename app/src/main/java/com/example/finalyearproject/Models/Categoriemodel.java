package com.example.finalyearproject.Models;



public class Categoriemodel {

    String catname,id;

    public Categoriemodel(String catname, String id) {
        this.catname = catname;
        this.id = id;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
