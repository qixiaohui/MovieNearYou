package com.movienearyou.xiaohui.movienearyou.model.news;

/**
 * Created by qixiaohui on 9/26/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    private String author;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The img
     */
    public String getImg() {
        return img;
    }

    /**
     *
     * @param img
     * The img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     *
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
