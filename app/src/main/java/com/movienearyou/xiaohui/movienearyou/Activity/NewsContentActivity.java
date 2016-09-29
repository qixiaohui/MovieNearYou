package com.movienearyou.xiaohui.movienearyou.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.news.List;
import com.movienearyou.xiaohui.movienearyou.model.newsContent.NewsContent;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by qixiaohui on 9/27/16.
 */
public class NewsContentActivity extends AppCompatActivity {
    public static final String TAG = "NewsContentActivity";
    public static final String LIST = "LIST";

    private List list;

    private ImageView contentImg;
    private TextView content;
    private Toolbar toolbar;
    private TextView title;
    private TextView author;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        initView();
    }

    private void initView() {
        contentImg = (ImageView) findViewById(R.id.newsContentImg);
        content = (TextView) findViewById(R.id.newsContent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white
        ));
        toolbar.setTitle(R.string.latest_news);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        list = new Gson().fromJson(getIntent().getStringExtra(LIST), List.class);
        if(list.getImg() != null) {
            Picasso.with(NewsContentActivity.this).load(list.getImg()).into(contentImg);
        }

        title.setText(list.getTitle());
        author.setText(list.getAuthor());

        if(list.getContent() == null) return;
        getNewsContent();
    }

    private void getNewsContent() {

        String [] titleList = list.getContent().substring(0, list.getContent().length()-1).split("/");
        String title = titleList[titleList.length-1];
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getNewsContent(title, new Callback<NewsContent>() {
            @Override
            public void success(NewsContent newsContent, Response response) {
                if(newsContent.getArticle().size() > 0) {
                    String contentString = "";
                    for(String article : newsContent.getArticle()) {
                        if(!article.contains("function()") && !article.contains("Function()") && article.length() != 0) {
                            contentString = contentString.concat("\n"+"\n"+"\t"+article);
                        }
                    }

                    content.setText(contentString);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public static void launchActivity(Activity fromActivity, List list, ActivityOptionsCompat options) {
        Intent intent = new Intent(fromActivity, NewsContentActivity.class);
        intent.putExtra(LIST, new Gson().toJson(list));
        fromActivity.startActivity(intent, options.toBundle());
        fromActivity.overridePendingTransition(R.anim.activity_start_leave, R.anim.activity_start_enter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            supportFinishAfterTransition();
            NewsContentActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
        }
        return super.onOptionsItemSelected(item);
    }
}
