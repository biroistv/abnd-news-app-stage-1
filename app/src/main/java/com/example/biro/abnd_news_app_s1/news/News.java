package com.example.biro.abnd_news_app_s1.news;

import java.net.URL;

public class News {

    private String sectionName;
    private String publicationDate;
    private String title;
    private URL url;

    public News(String sectionName, String publicationDate, String title, URL url) {
        this.sectionName = sectionName;
        this.publicationDate = publicationDate;
        this.title = title;
        this.url = url;
    }

    public String getSectionName() {

        return sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
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

}
