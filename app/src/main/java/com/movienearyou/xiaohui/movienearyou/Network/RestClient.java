package com.movienearyou.xiaohui.movienearyou.Network;

/**
 * Created by TQi on 7/23/16.
 */
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by qixiaohui on 4/4/16.
 */
public class RestClient {
    static final String BASE_URL = "https://sheltered-peak-44325.herokuapp.com";

    public static MoviesGateway getMoviesGateway(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(SelfSigningClientBuilder.createClient()))
                .build();

        return restAdapter.create(MoviesGateway.class);
    }
}
