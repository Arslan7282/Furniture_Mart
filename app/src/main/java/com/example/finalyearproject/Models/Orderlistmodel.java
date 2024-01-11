package com.example.finalyearproject.Models;

public class Orderlistmodel {

    String oimg,oname,oprice,ordertime,ophone,oadress,oemail,delivery;

    public Orderlistmodel() {
    }

    public Orderlistmodel(String oimg, String oname, String oprice, String ordertime,String ophone, String oadress,String oemail,String delivery) {
        this.oimg = oimg;
        this.oname = oname;
        this.oprice = oprice;
        this.ordertime = ordertime;
        this.ophone = ophone;
        this.oadress = oadress;
        this.oemail = oemail;
        this.delivery = delivery;
    }

    public String getOimg() {
        return oimg;
    }

    public void setOimg(String oimg) {
        this.oimg = oimg;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;

    }

    public String getOphone() {
        return ophone;
    }

    public void setOphone(String ophone) {
        this.ophone = ophone;
    }

    public String getOadress() {
        return oadress;
    }

    public void setOadress(String oadress) {
        this.oadress = oadress;
    }

    public String getOemail() {
        return oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
