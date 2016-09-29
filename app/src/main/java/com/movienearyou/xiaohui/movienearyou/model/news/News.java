package com.movienearyou.xiaohui.movienearyou.model.news;

/**
 * Created by qixiaohui on 9/26/16.
 */
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = new ArrayList<>();

    /**
     *
     * @return
     * The list
     */
    public java.util.List<List> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(java.util.List<List> list) {
        this.list = list;
    }

}
