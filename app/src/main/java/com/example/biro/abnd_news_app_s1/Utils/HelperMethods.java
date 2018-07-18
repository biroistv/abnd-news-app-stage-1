package com.example.biro.abnd_news_app_s1.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.biro.abnd_news_app_s1.News.News;

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

    public static final String SITE = "https://content.guardianapis.com/";
    public static final String API_KEY = "&api-key=914853e8-38e5-4804-a7f7-b5dfa869fca5";

    public static URL parseURL(String source){
        URL url = null;

        try {
            url = new URL(source);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static ArrayList<News> getNewsFromInternet(URL url){

        String JsonSTR = getJsonFromNet(url);
        ArrayList<News> newsArrayList = getNewsFromJson(JsonSTR);

        return newsArrayList;
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

            inputStream = httpURLConnection.getInputStream();
            jsonStr = getJsonStringFromStream(inputStream);

        } catch (IOException e) {
            Log.d(TAG, "getJsonFromNet: " + e.getMessage());
        }finally {
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
        StringBuffer output = new StringBuffer();
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

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

}
