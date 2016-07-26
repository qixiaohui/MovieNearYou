package com.movienearyou.xiaohui.movienearyou.model.showtime;

/**
 * Created by TQi on 7/25/16.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Showtime {

    @SerializedName("movies")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();

    /**
     *
     * @return
     * The movies
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     *
     * @param movies
     * The movies
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
