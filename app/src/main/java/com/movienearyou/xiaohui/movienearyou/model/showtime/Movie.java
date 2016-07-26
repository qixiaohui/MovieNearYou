package com.movienearyou.xiaohui.movienearyou.model.showtime;

/**
 * Created by TQi on 7/25/16.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("theaters")
    @Expose
    private List<Theater> theaters = new ArrayList<Theater>();

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
     * The theaters
     */
    public List<Theater> getTheaters() {
        return theaters;
    }

    /**
     *
     * @param theaters
     * The theaters
     */
    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
    }

}
