package com.movienearyou.xiaohui.movienearyou.Network;

/**
 * Created by TQi on 7/23/16.
 */

import com.movienearyou.xiaohui.movienearyou.model.cast.Cast;
import com.movienearyou.xiaohui.movienearyou.model.channels.Channel;
import com.movienearyou.xiaohui.movienearyou.model.credit.Credit;
import com.movienearyou.xiaohui.movienearyou.model.movies.Movies;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;
import com.movienearyou.xiaohui.movienearyou.model.videos.Video;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by TQi on 2/18/16.
 */
public interface MoviesGateway {

    @GET("/movies/category/{category}/{page}")
    void getMovies(@Path("category") String category, @Path("page") int page, retrofit.Callback<Movies> callback);

    @GET("/movie/video/{movie}")
    void getVideos(@Path("movie") String movie, retrofit.Callback<ArrayList<Video>> callback);

    @GET("/schedule/theaters/{name}/{zip}/{day}")
    void getShowtime(@Path("name") String name, @Path("zip") String zip, @Path("day") String day, retrofit.Callback<Showtime> callback);

    @GET("/movie/credit/{id}")
    void getCredit(@Path("id") String id, retrofit.Callback<Credit> callback);

    @GET("/movie/similar/{id}")
    void getSimilar(@Path("id") String id, retrofit.Callback<Movies> callback);

    @GET("/schedule/avilableon/webpurchase/{id}")
    void getAvilableOn(@Path("id") String id, retrofit.Callback<ArrayList<Channel>> callback);

    @GET("/movie/person/{id}")
    void getPerson(@Path("id") String id, retrofit.Callback<Cast> callback);

    @GET("/movies/search/{name}/{page}")
    void getMovieSearchResult(@Path("name") String name, @Path("page") int page, retrofit.Callback<Movies> callback);

}
