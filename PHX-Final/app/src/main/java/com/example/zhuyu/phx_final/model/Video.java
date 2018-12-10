package com.example.zhuyu.phx_final.model;

import android.graphics.drawable.Drawable;

public class Video {
    private String mId;
    private String mTitle;
    private String mTime;
    private String mDescrip;
    private String mImgUrl;
    private String mMp4Url;
    private Boolean mIsDownloaded;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getDescrip() {
        return mDescrip;
    }

    public void setDescrip(String descrip) {
        mDescrip = descrip;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    public String getMp4Url() {
        return mMp4Url;
    }

    public void setMp4Url(String mp4Url) {
        mMp4Url = mp4Url;
    }

    public Boolean getDownloaded() {
        return mIsDownloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        mIsDownloaded = downloaded;
    }

    public Drawable getImgShow() {
        return mImgShow;
    }

    public void setImgShow(Drawable imgShow) {
        mImgShow = imgShow;
    }

    public Drawable mImgShow;

}


