package com.example.biro.abnd_news_app_s1;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.biro.abnd_news_app_s1.News.News;
import com.example.biro.abnd_news_app_s1.News.NewsAdapter;
import com.example.biro.abnd_news_app_s1.News.NewsLoader;
import com.example.biro.abnd_news_app_s1.Utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private String SEARCH_TERM = "search?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(0, null, this);

    }

    private void updateUI(ArrayList<News> newsArrayList){
        NewsAdapter newsAdapter = new NewsAdapter(this, newsArrayList);
        ListView lv = findViewById(R.id.main_activity_listView);
        lv.setAdapter(newsAdapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(
                this,
                HelperMethods.parseURL(HelperMethods.SITE + SEARCH_TERM + HelperMethods.API_KEY)
        );
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        ((ProgressBar)findViewById(R.id.main_activity_progbar)).setVisibility(View.GONE);
        updateUI(new ArrayList<News>(data));
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUI(new ArrayList<News>());
    }
}
