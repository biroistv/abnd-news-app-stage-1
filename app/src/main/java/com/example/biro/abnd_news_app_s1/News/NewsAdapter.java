package com.example.biro.abnd_news_app_s1.News;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.biro.abnd_news_app_s1.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        News news = getItem(position);

        ((TextView)view.findViewById(R.id.list_item_title)).setText(news.getTitle());
        ((TextView)view.findViewById(R.id.list_item_section)).setText(news.getSectionName());
        ((TextView)view.findViewById(R.id.list_item_pubdate)).setText(news.getPublicationDate());

        return view;
    }
}
