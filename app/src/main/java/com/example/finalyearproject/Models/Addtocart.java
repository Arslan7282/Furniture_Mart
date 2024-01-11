package com.example.finalyearproject.Models;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Addtocart {

    String cartimg,cartname,cartdesc,cartprice,orderId;

    private Object timestamp;


        public Addtocart() {
        long currentTimeMillis = System.currentTimeMillis();

        // Convert the time to a Firebase-compatible timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timestamp = sdf.format(new Date(currentTimeMillis));
    }

    public Addtocart(String cartimg, String cartname, String cartdesc, String cartprice, Object timestamp, String orderId) {
        this.cartimg = cartimg;
        this.cartname = cartname;
        this.cartdesc = cartdesc;
        this.cartprice = cartprice;
        this.timestamp = timestamp;
        this.orderId = orderId;
    }


    public String getCartimg() {
        return cartimg;
    }

    public void setCartimg(String cartimg) {
        this.cartimg = cartimg;
    }

    public String getCartname() {
        return cartname;
    }

    public void setCartname(String cartname) {
        this.cartname = cartname;
    }

    public String getCartdesc() {
        return cartdesc;
    }

    public void setCartdesc(String cartdesc) {
        this.cartdesc = cartdesc;
    }

    public String getCartprice() {
        return cartprice;
    }

    public void setCartprice(String cartprice) {
        this.cartprice = cartprice;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
