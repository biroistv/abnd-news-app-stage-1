package com.example.biro.abnd_news_app_s1;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.biro.abnd_news_app_s1.News.News;
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
    }

    private void updateUI(ArrayList<News> newsArrayList){
        //TODO: a lista tartalmának a frissítése
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
        updateUI(new ArrayList<News>(data));
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUI(new ArrayList<News>());
    }
}
