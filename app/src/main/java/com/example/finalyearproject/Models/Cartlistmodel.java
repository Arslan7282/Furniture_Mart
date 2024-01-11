package com.example.finalyearproject.Models;

public class Cartlistmodel {

    String cartimg, cartname, cartdisc, cartprice, carttime,orderId;

    public Cartlistmodel() {
    }

    public Cartlistmodel(String cartimg, String cartname, String cartdisc, String cartprice, String carttime, String orderId) {
        this.cartimg = cartimg;
        this.cartname = cartname;
        this.cartdisc = cartdisc;
        this.cartprice = cartprice;
        this.carttime = carttime;
        this.orderId=orderId;
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
        return cartdisc;
    }

    public void setCartdesc(String cartdesc) {
        this.cartdisc = cartdesc;
    }

    public String getCartprice() {
        return cartprice;
    }

    public void setCartprice(String cartprice) {
        this.cartprice = cartprice;
    }

    public String getCarttime() {
        return carttime;
    }

    public void setCarttime(String carttime) {
        this.carttime = carttime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
