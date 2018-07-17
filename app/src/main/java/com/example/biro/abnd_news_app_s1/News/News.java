package com.example.biro.abnd_news_app_s1.News;

import java.net.MalformedURLException;
import java.net.URL;

public class News {

    private String sectionName;
    private String publicationDate;
    private String title;
    private URL url;

    public News(){}

    public News(String sectionName, String publicationDate, String title, URL url) {
        this.sectionName = sectionName;
        this.publicationDate = publicationDate;
        this.title = title;
        this.url = url;
    }

    public String getSectionName() {

        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setUrlFromString(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
