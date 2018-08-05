package com.example.biro.abnd_news_app_s1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

import com.example.biro.abnd_news_app_s1.R;
import com.example.biro.abnd_news_app_s1.exception.RespondCodeException;
import com.example.biro.abnd_news_app_s1.news.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class HelperMethods {

    public static final String SITE = "https://content.guardianapis.com/search?";
    public static final String API_KEY = "914853e8-38e5-4804-a7f7-b5dfa869fca5";

    // This method build up the URL using uriBuilder and the parseURL method
    public static URL createURL(Context context, String searchTerm)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String numberOfNews = sharedPreferences.getString(
                context.getString(R.string.settings_news_number_key),
                context.getString(R.string.settings_news_number_default)
        );

        Uri baseUri = Uri.parse(HelperMethods.SITE);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("page-size", numberOfNews);
        uriBuilder.appendQueryParameter("q", searchTerm);
        uriBuilder.appendQueryParameter("api-key", HelperMethods.API_KEY);

        return parseURL(uriBuilder.toString());
    }

    /**
     *  This method create an URL from string
     * */
    public static URL parseURL(String source){
        URL url = null;

        try {
            url = new URL(source);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     *  This method give back all the news what i get from the Json
     * */
    public static ArrayList<News> getNewsFromInternet(URL url){
        String JsonSTR = getJsonFromNet(url);
        return getNewsFromJson(JsonSTR);
    }

    private static String getJsonFromNet(URL url) {

        String jsonStr = "";

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    jsonStr = getJsonStringFromStream(inputStream);
                }else
                    throw new RespondCodeException("The respond code on connect is " + httpURLConnection.getResponseCode());

        } catch (IOException e) {
            Log.d(TAG, "getJsonFromNet: " + e.getMessage());
        } catch (RespondCodeException e) {
            Log.d(TAG, "getJsonFromNet: " + e.getMessage());
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "getJsonFromNet: " + e.getMessage());
                }
            }
        }

        return jsonStr;
    }

    private static String getJsonStringFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null)
            output.append(line);

        return output.toString();
    }

    private static ArrayList<News> getNewsFromJson(String JsonStr) {

        ArrayList<News> newsArrayList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(JsonStr);

            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); ++i)
            {
                JSONObject result = results.getJSONObject(i);

                newsArrayList.add(new News(
                        result.getString("sectionName"),
                        result.getString("webPublicationDate"),
                        result.getString("webTitle"),
                        parseURL(result.getString("webUrl"))
                ));

            }

        } catch (JSONException e) {
            Log.d(TAG, "getNewsFromJson: " + e.getMessage());
        }

        return newsArrayList;
    }

    /**
     *  This method check the internet connection status.
     * */
    public static boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}