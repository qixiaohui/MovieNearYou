package com.movienearyou.xiaohui.movienearyou.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qixiaohui on 8/8/16.
 */
public class User {
    @SerializedName("displayName")
    private String displayName;

    @SerializedName("uid")
    private String uid;

    @SerializedName("email")
    private String email;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
