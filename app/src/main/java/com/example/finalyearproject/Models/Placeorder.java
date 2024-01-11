package com.example.finalyearproject.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Placeorder {

    private String img;
    private String itemName;
    private String itemPrice;
    private String deliveryFee;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Object timestamp;
    private String id;


    public Placeorder() {
        long currentTimeMillis = System.currentTimeMillis();

        // Convert the time to a Firebase-compatible timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timestamp = sdf.format(new Date(currentTimeMillis));
    }

    public Placeorder(String id, String img,String itemName, String itemPrice, String deliveryFee, String fullName, String email, String phone, String address,Object timestamp) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.deliveryFee = deliveryFee;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.img = img;
        this.timestamp = timestamp;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }


}
