package com.example.blfood.Model;

import java.io.Serializable;

public class NewFeedItem implements Serializable {
    // thuộc tính của status trong newfeed
    int idstatus;
    int iduser;
    String avatarURL;
    String username;
    // lỗi dùng username thay cho name
    String name;
    String contentNewFeed;
    int likecount;
    String imageurl;

    // thuộc tính của comment trong status
    int idcomment;

    // thuộc tính của danh sách bạn bè, lời mời kết bạn
    int idfriend;
    String requester;
    String admirer;
    int isfriend;

    // hàm contruction dành cho status
    public NewFeedItem(int idstatus, int iduser, String avatarURL, String username, String contentNewFeed, int likecount, String imageurl) {
        this.idstatus = idstatus;
        this.iduser = iduser;
        this.avatarURL = avatarURL;
        this.username = username;
        this.contentNewFeed = contentNewFeed;
        this.likecount = likecount;
        this.imageurl = imageurl;
    }

    // hàm contruction dành cho comment activity
    public NewFeedItem(int idcomment, int idstatus, int iduser, String avatarURL, String username, String contentNewFeed) {
        this.idcomment = idcomment;
        this.idstatus = idstatus;
        this.iduser = iduser;
        this.avatarURL = avatarURL;
        this.username = username;
        this.contentNewFeed = contentNewFeed;
    }

    public NewFeedItem(int idstatus, int iduser, String avatarURL, String username, String contentNewFeed) {
        this.idstatus = idstatus;
        this.iduser = iduser;
        this.avatarURL = avatarURL;
        this.username = username;
        this.contentNewFeed = contentNewFeed;
    }

    // hàm contruction dành cho list các lời mời kết bạn
    public NewFeedItem(int iduser, String username, String name, String avatarURL, int idfriend, String requester, String admirer, int isfriend) {
        this.iduser = iduser;
        this.username = username;
        this.name = name;
        this.avatarURL = avatarURL;
        this.idfriend = idfriend;
        this.requester = requester;
        this.admirer = admirer;
        this.isfriend = isfriend;
    }

    // hàm contruction dành cho các item user trong tìm kiếm bạn bè
    public NewFeedItem(int iduser, String avatarURL, String username, String name, int isfriend) {
        this.iduser = iduser;
        this.avatarURL = avatarURL;
        this.username = username;
        this.name = name;
        this.isfriend = isfriend;
    }

    // hàm contruction dành cho trang profile của chính user client đang sử dụng
    public NewFeedItem(String avatarURL, String username, String name) {
        this.avatarURL = avatarURL;
        this.username = username;
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdfriend() {
        return idfriend;
    }

    public void setIdfriend(int idfriend) {
        this.idfriend = idfriend;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getAdmirer() {
        return admirer;
    }

    public void setAdmirer(String admirer) {
        this.admirer = admirer;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIdcomment() {
        return idcomment;
    }

    public void setIdcomment(int idcomment) {
        this.idcomment = idcomment;
    }

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContentNewFeed() {
        return contentNewFeed;
    }

    public void setContentNewFeed(String contentNewFeed) {
        this.contentNewFeed = contentNewFeed;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }
}
