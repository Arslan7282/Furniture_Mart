package com.example.finalyearproject.Models;

public class profilemodal {
    String user_image;
    String user_name,verified,search_name,user_mobile,user_email,user_nickname,user_profession,user_date,created_at,user_status,user_thumb_image;

    public profilemodal() {
    }

    public profilemodal(String user_image, String user_name, String verified, String search_name, String user_mobile, String user_email, String user_nickname, String user_profession, String user_date, String created_at, String user_status, String user_thumb_image) {
        this.user_image = user_image;
        this.user_name = user_name;
        this.verified = verified;
        this.search_name = search_name;
        this.user_mobile = user_mobile;
        this.user_email = user_email;
        this.user_nickname = user_nickname;
        this.user_profession = user_profession;
        this.user_date = user_date;
        this.created_at = created_at;
        this.user_status = user_status;
        this.user_thumb_image = user_thumb_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_profession() {
        return user_profession;
    }

    public void setUser_profession(String user_profession) {
        this.user_profession = user_profession;
    }

    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_thumb_image() {
        return user_thumb_image;
    }

    public void setUser_thumb_image(String user_thumb_image) {
        this.user_thumb_image = user_thumb_image;
    }
}
