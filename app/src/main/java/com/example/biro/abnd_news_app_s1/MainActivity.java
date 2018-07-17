package com.example.biro.abnd_news_app_s1;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.biro.abnd_news_app_s1.News.News;
import com.example.biro.abnd_news_app_s1.News.NewsLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void updateUI(){}

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUI();
    }
}
