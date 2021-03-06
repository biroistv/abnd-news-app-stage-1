package com.example.biro.abnd_news_app_s1;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.biro.abnd_news_app_s1.news.News;
import com.example.biro.abnd_news_app_s1.news.NewsAdapter;
import com.example.biro.abnd_news_app_s1.news.NewsLoader;
import com.example.biro.abnd_news_app_s1.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsAdapter newsAdapter = null;

    @BindView(R.id.main_activity_empty_result) TextView mainEmptyResult;
    @BindView(R.id.main_activity_progbar) ProgressBar mainProgressBar;
    @BindView(R.id.main_activity_searchBtn) Button mainSearchButton;
    @BindView(R.id.main_activity_listView) ListView mainListView;
    @BindView(R.id.main_activity_editText) EditText searchText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // If we have internet the the loader will start, otherwise "No internet" text appear
        if (HelperMethods.isInternetAvailable(MainActivity.this))
            getLoaderManager().initLoader(0, null, this);
        else {
            mainEmptyResult.setText(R.string.no_internet);
        }

        // Set the progressbar visibility to Gone
        mainProgressBar.setVisibility(View.GONE);

        // Search button implementation
        mainSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If we have internet the do the search, if we don't have, then "No internet" text appear
                if (HelperMethods.isInternetAvailable(MainActivity.this)) {
                    mainProgressBar.setVisibility(View.VISIBLE);

                    // If the loader manager is null then start a loader manager, otherwise restart it
                    if (getLoaderManager() != null)
                        getLoaderManager().restartLoader(0, null, MainActivity.this);
                    else
                        getLoaderManager().initLoader(0, null, MainActivity.this);
                }else {
                    // If we don't have any data in the adapter then just simply set the textView value to "No internet"
                    // If we have any data in the adapter, just clear it
                    if (newsAdapter != null)
                        newsAdapter.clear();
                    mainEmptyResult.setText(R.string.no_internet);
                }
            }
        });
    }

    // This method update the content of the views on the layout
    private void updateUI(ArrayList<News> newsArrayList) {
        newsAdapter = new NewsAdapter(this, newsArrayList);
        mainListView.setEmptyView(mainEmptyResult);
        mainEmptyResult.setText(R.string.no_news);
        mainListView.setAdapter(newsAdapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        return new NewsLoader(
                this,
                HelperMethods.createURL(this, searchText.getText().toString())
        );
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        mainProgressBar.setVisibility(View.GONE);
        updateUI(new ArrayList<>(data));
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUI(new ArrayList<News>());
    }
}
