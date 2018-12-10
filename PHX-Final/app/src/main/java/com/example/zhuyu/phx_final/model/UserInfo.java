package com.example.zhuyu.phx_final.model;

public class UserInfo {

    public static UserInfo sUserInfo;

    public UserInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserInfo get() {
        if (sUserInfo == null) {
            sUserInfo = new UserInfo("name", "email");
        }
        return sUserInfo;
    }

    public static void setUserInfo(UserInfo sUserInfo) {
        UserInfo.sUserInfo = sUserInfo;
    }

    private String name;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    private String email;
}
