package com.example.biro.abnd_news_app_s1;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.biro.abnd_news_app_s1.News.News;
import com.example.biro.abnd_news_app_s1.News.NewsAdapter;
import com.example.biro.abnd_news_app_s1.News.NewsLoader;
import com.example.biro.abnd_news_app_s1.Utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private String SEARCH_TERM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.main_activity_progbar)).setVisibility(View.GONE);

        Button searchButton = findViewById(R.id.main_activity_searchBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                (findViewById(R.id.main_activity_progbar)).setVisibility(View.VISIBLE);

                SEARCH_TERM = "search?q=" + ((EditText)findViewById(R.id.main_activity_editText)).getText();

                setupLoader();
            }
        });
    }

    private void setupLoader(){
        getLoaderManager().initLoader(0, null, this);
    }

    private void updateUI(ArrayList<News> newsArrayList){
        NewsAdapter newsAdapter = new NewsAdapter(this, newsArrayList);
        ListView lv = findViewById(R.id.main_activity_listView);
        lv.setEmptyView(findViewById(R.id.main_activity_empty_result));
        ((TextView)findViewById(R.id.main_activity_empty_result)).setText("There is no news.");
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
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUI(new ArrayList<News>());
    }
}
