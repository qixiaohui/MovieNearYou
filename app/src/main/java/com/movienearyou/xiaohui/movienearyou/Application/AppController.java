package com.movienearyou.xiaohui.movienearyou.Application;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qixiaohui on 8/9/16.
 */
public class AppController extends Application {
    private static AppController appInstance;
    private ArrayList<Result> movies = new ArrayList<>();
    private HashMap<Integer, String> uidMap = new HashMap<>();
    private DatabaseReference ref;
    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = new AppController();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        appInstance.ref = database.getReference("/users/"+uid+"/mycollection");
        appInstance.ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appInstance.uidMap.clear();
                appInstance.movies.clear();
                for(DataSnapshot children : dataSnapshot.getChildren()){
                    Result movie = new Result();
                    movie.setBackdropPath(children.child("backdrop_path").getValue(String.class));
                    movie.setPosterPath(children.child("poster_path").getValue(String.class));
                    movie.setTitle(children.child("title").getValue(String.class));
                    movie.setVoteAverage(children.child("vote_average").getValue(Double.class));
                    movie.setPopularity(children.child("popularity").getValue(Double.class));
                    movie.setVoteCount(children.child("vote_count").getValue(Integer.class));
                    movie.setOverview(children.child("overview").getValue(String.class));
                    movie.setId(children.child("id").getValue(Integer.class));
                    movie.setReleaseDate(children.child("release_date").getValue(String.class));
                    appInstance.movies.add(movie);
                    appInstance.uidMap.put(movie.getId(), children.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Result> getMovies(){
        return this.movies;
    }

    public void removeMovie(Integer id){
        if(this.uidMap.get(id) != null) {
            this.ref.child(this.uidMap.get(id)).removeValue();
        }
    }

    public void addMovie(Result movie){
        this.movies.add(movie);
        String key = this.ref.push().getKey();
        this.ref.child(key).child("poster_path").setValue(movie.getPosterPath());
        this.ref.child(key).child("adult").setValue(movie.getAdult());
        this.ref.child(key).child("overview").setValue(movie.getOverview());
        this.ref.child(key).child("release_date").setValue(movie.getReleaseDate());
        this.ref.child(key).child("genre_ids").setValue(movie.getGenreIds());
        this.ref.child(key).child("original_title").setValue(movie.getOriginalTitle());
        this.ref.child(key).child("original_language").setValue(movie.getOriginalLanguage());
        this.ref.child(key).child("title").setValue(movie.getTitle());
        this.ref.child(key).child("backdrop_path").setValue(movie.getBackdropPath());
        this.ref.child(key).child("popularity").setValue(movie.getPopularity());
        this.ref.child(key).child("vote_count").setValue(movie.getVoteCount());
        this.ref.child(key).child("video").setValue(movie.getVideo());
        this.ref.child(key).child("vote_average").setValue(movie.getVoteAverage());
        this.ref.child(key).child("id").setValue(movie.getId());
    }


    public static synchronized AppController getInstance() {
        return appInstance;
    }

}
