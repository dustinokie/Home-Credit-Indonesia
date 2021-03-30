package com.example.homecreditindonesia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecreditindonesia.R;
import com.example.homecreditindonesia.adapter.ArticleAdapter;
import com.example.homecreditindonesia.adapter.ProductMenuAdapter;
import com.example.homecreditindonesia.model.Data;
import com.example.homecreditindonesia.model.Datum;
import com.example.homecreditindonesia.model.Item;
import com.example.homecreditindonesia.retrofit.ApiService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MainActivity act;
    private GridView gridView;
    private TextView sectionTitle;
    private ListView listArticle;
    private LinearLayout layoutArticle;

    List<Datum> data;
    Datum datumProducts, datumArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromApi();

//        gridView = findViewById(R.id.grid_layout);
//        for (int i = 0; i < data.size(); i++) {
//            if (data.get(i).getSection().equals("products")) {
//                datum = data.get(i);
//            }
//        }
//        ProductMenuAdapter adapter = new ProductMenuAdapter(this, datum.getItems());
//        gridView.setAdapter(adapter);
    }

    private void getDataFromApi () {
        ApiService.endpoint().getData()
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data > call, Response<Data> response) {
                        try {
                            if (response.isSuccessful()) {
                                data = response.body().getData();
                                Log.d("DATA ==== ", data.toString());

                                gridView = findViewById(R.id.grid_layout);

                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).getSection().equals("products")) {
                                        datumProducts = data.get(i);
                                    } else if (data.get(i).getSection().equals("articles")) {
                                        datumArticles = data.get(i);
                                    }
                                }
                                ProductMenuAdapter adapter = new ProductMenuAdapter(MainActivity.this, datumProducts.getItems());
                                gridView.setAdapter(adapter);

                                createArticleUI(datumArticles.getItems());

                            }
                        } catch (Exception e ) {
                            Toast.makeText(act, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Log.e("DATA === ", t.toString());
                    }
                });
    }

    private void createArticleUI(List<Item> listArticles) {
        layoutArticle = findViewById(R.id.layout_article);
        layoutArticle.removeAllViews();

        for (int i = 0; i < listArticles.size(); i++) {
            Item item = listArticles.get(i);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_article, null);
            ImageView imgArticle = view.findViewById(R.id.iv_article);
            TextView tvArticle = view.findViewById(R.id.tv_article);

            Picasso.get().load(item.getArticleImage()).into(imgArticle);
            tvArticle.setText(item.getArticleTitle());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( item.getLink() ) );
                    startActivity(browse);
                }
            });

            layoutArticle.addView(view);
        }
    }
}