package com.bignerdranch.android.databaseupdation;

public class Podcast {

    private String mId;
    private String mTitle;
    private String mTime;
    private String mDescrip;
    private String mImgUrl;
    private String mMp3Url;
    private Boolean mIsDownloaded

            ;

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

    public String getMp3Url() {
        return mMp3Url;
    }

    public void setMp3Url(String mp3Url) {
        mMp3Url = mp3Url;
    }

    public Boolean getDownloaded() {
        return mIsDownloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        mIsDownloaded = downloaded;
    }
}