package com.example.homecreditindonesia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecreditindonesia.R;
import com.example.homecreditindonesia.activity.MainActivity;
import com.example.homecreditindonesia.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends BaseAdapter {

    private MainActivity activity;
    private List<Articles> listArticles;

    public ArticleAdapter(MainActivity act, List<Item> itemArr) {
        this.activity = act;
        setListMenus(itemArr);
    }

    @Override
    public int getCount() {
        return listArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return listArticles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ArticleViewHolder holder;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_article, null);

            holder = new ArticleViewHolder(v);
            v.setTag(holder);

        }else{
            holder = (ArticleViewHolder) v.getTag();
        }

        Articles articles = listArticles.get(position);
        Picasso.get().load(articles.getImage()).into(holder.articleImage);
        holder.title.setText(articles.getTitle());

        return v;
    }

    private void setListMenus(List<Item> itemArr) {
        try {


            listArticles = new ArrayList<>();
            Articles articles;

            for (int i = 0; i < itemArr.size(); i++) {
                Item item = itemArr.get(i);
                articles = new Articles();
                articles.setImage(item.getArticleImage());
                articles.setTitle(item.getArticleTitle());
                articles.setLink(item.getLink());

                listArticles.add(articles);
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    class Articles {
        String title;
        String image;
        String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    class ArticleViewHolder {

        ImageView articleImage;
        TextView title;

        public ArticleViewHolder(View v) {
            articleImage = v.findViewById(R.id.iv_article);
            title = v.findViewById(R.id.tv_article);
        }
    }
}
