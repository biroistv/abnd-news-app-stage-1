package com.example.biro.abnd_news_app_s1.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.biro.abnd_news_app_s1.R;
import com.example.biro.abnd_news_app_s1.utils.DateFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final News news = getItem(position);

        assert news != null;
        viewHolder.listItemTitle.setText(news.getTitle());
        viewHolder.listItemSelection.setText(news.getSectionName());
        viewHolder.listItemPublicationDate.setText(DateFormatter.formatDate(news.getPublicationDate()));

        if (news.getContributor() != null)
            viewHolder.listItemContributor.setText(news.getContributor());
        else
            viewHolder.listItemContributor.setVisibility(View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getUrl().toString()));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.list_item_title)
        TextView listItemTitle;

        @BindView(R.id.list_item_section)
        TextView listItemSelection;

        @BindView(R.id.list_item_pubdate)
        TextView listItemPublicationDate;

        @BindView(R.id.list_item_contributor)
        TextView listItemContributor;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
