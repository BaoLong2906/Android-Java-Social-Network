package com.example.blfood.Model;

import java.io.Serializable;

public class HistoryOrderItem implements Serializable {
    int idhoadon;
    String ngaymua;
    // không cần username vì class user đã lưu username
    int tonghoadon;

    public HistoryOrderItem(int idhoadon, String ngaymua, int tonghoadon) {
        this.idhoadon = idhoadon;
        this.ngaymua = ngaymua;
        this.tonghoadon = tonghoadon;
    }

    public int getIdhoadon() {
        return idhoadon;
    }

    public void setIdhoadon(int idhoadon) {
        this.idhoadon = idhoadon;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public int getTonghoadon() {
        return tonghoadon;
    }

    public void setTonghoadon(int tonghoadon) {
        this.tonghoadon = tonghoadon;
    }
}