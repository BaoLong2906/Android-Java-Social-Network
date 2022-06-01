package com.example.blfood.Model;

import java.io.Serializable;

public class HorizontalItem implements Serializable {
    int idproduct;
    String productname;
    int price;
    String motasanpham;
    String itemImageUrl;

    public HorizontalItem(int idproduct, String productname, int price, String motasanpham, String itemImageUrl) {
        this.idproduct = idproduct;
        this.productname = productname;
        this.price = price;
        this.motasanpham = motasanpham;
        this.itemImageUrl = itemImageUrl;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }
}
