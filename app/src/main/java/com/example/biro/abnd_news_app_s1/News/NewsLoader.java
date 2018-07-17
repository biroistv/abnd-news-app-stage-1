package com.example.biro.abnd_news_app_s1.News;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.biro.abnd_news_app_s1.Utils.HelperMethods;

import java.net.URL;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private URL url;

    public NewsLoader(Context context, URL url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        return HelperMethods.getNewsFromInternet(url);
    }
}
