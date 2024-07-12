package com.myapp.userreg;

public class MainModel {
    String flavor, price, location, imgurl;

    MainModel() {
    }

    public MainModel(String flavor, String price, String location, String imgurl) {
        this.flavor = flavor;
        this.price = price;
        this.location = location;
        this.imgurl = imgurl;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
