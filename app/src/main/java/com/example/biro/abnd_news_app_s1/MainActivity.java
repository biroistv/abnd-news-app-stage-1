package com.example.biro.abnd_news_app_s1;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.biro.abnd_news_app_s1.News.News;
import com.example.biro.abnd_news_app_s1.News.NewsAdapter;
import com.example.biro.abnd_news_app_s1.News.NewsLoader;
import com.example.biro.abnd_news_app_s1.Utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private String SEARCH_TERM = "search?q=";
    private NewsAdapter newsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (HelperMethods.isInternetAvailable(MainActivity.this))
            getLoaderManager().initLoader(0, null, this);
        else {
            ((TextView) findViewById(R.id.main_activity_empty_result)).setText(R.string.no_internet);
        }

        (findViewById(R.id.main_activity_progbar)).setVisibility(View.GONE);
        Button searchButton = findViewById(R.id.main_activity_searchBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (HelperMethods.isInternetAvailable(MainActivity.this)) {
                    (findViewById(R.id.main_activity_progbar)).setVisibility(View.VISIBLE);
                    SEARCH_TERM = "search?q=" + ((EditText) findViewById(R.id.main_activity_editText)).getText();

                    if (getLoaderManager() != null)
                        getLoaderManager().restartLoader(0, null, MainActivity.this);
                    else
                        getLoaderManager().initLoader(0, null, MainActivity.this);
                }else {
                    if (newsAdapter != null)
                        newsAdapter.clear();
                    ((TextView) findViewById(R.id.main_activity_empty_result)).setText(R.string.no_internet);
                }
            }
        });
    }

    private void updateUI(ArrayList<News> newsArrayList) {
        newsAdapter = new NewsAdapter(this, newsArrayList);
        ListView lv = findViewById(R.id.main_activity_listView);
        lv.setEmptyView(findViewById(R.id.main_activity_empty_result));
        ((TextView) findViewById(R.id.main_activity_empty_result)).setText(R.string.no_news);
        lv.setAdapter(newsAdapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.d("help", "onCreateLoader: " + SEARCH_TERM);
        return new NewsLoader(
                this,
                HelperMethods.parseURL(HelperMethods.SITE + SEARCH_TERM + HelperMethods.API_KEY)
        );
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        findViewById(R.id.main_activity_progbar).setVisibility(View.GONE);
        updateUI(new ArrayList<>(data));
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUI(new ArrayList<News>());
    }
}
