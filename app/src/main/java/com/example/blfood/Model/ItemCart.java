package com.example.blfood.Model;

import java.io.Serializable;

public class ItemCart implements Serializable {
    public int idproduct;
    public String productname;
    public int gia;
    public int tonggia;
    public int soluongmua;
    public String ofUrl;

    public ItemCart(int idproduct, String productname, int gia, int tonggia, int soluongmua, String ofUrl) {
        this.idproduct = idproduct;
        this.productname = productname;
        this.gia = gia;
        this.tonggia = tonggia;
        this.soluongmua = soluongmua;
        this.ofUrl = ofUrl;
    }

    public ItemCart(String productname, int gia, int soluongmua, int tonggia) {
        this.productname = productname;
        this.gia = gia;
        this.soluongmua = soluongmua;
        this.tonggia = tonggia;
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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTonggia() {
        return tonggia;
    }

    public void setTonggia(int tonggia) {
        this.tonggia = tonggia;
    }

    public int getSoluongmua() {
        return soluongmua;
    }

    public void setSoluongmua(int soluongmua) {
        this.soluongmua = soluongmua;
    }

    public String getOfUrl() {
        return ofUrl;
    }

    public void setOfUrl(String ofUrl) {
        this.ofUrl = ofUrl;
    }
}
