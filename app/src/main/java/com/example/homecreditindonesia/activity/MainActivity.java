package com.example.homecreditindonesia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homecreditindonesia.R;
import com.example.homecreditindonesia.adapter.ProductMenuAdapter;
import com.example.homecreditindonesia.model.Data;
import com.example.homecreditindonesia.model.Datum;
import com.example.homecreditindonesia.model.Item;
import com.example.homecreditindonesia.retrofit.ApiService;
import com.example.homecreditindonesia.utils.HCIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private GridView gridView;
    private TextView sectionTitle;
    private LinearLayout layoutArticle;

    List<Datum> data;
    Datum datumProducts, datumArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress = new ProgressDialog(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                progress.setTitle("Loading");
                progress.setMessage("Retrieving Data...");
                progress.setCancelable(false);
                progress.show();
            }
        });

        getDataFromApi();
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
                                        sectionTitle = findViewById(R.id.tv_section_title);
                                        sectionTitle.setText(datumArticles.getSectionTitle());
                                    }
                                }
                                ProductMenuAdapter adapter = new ProductMenuAdapter(MainActivity.this, datumProducts.getItems());
                                gridView.setAdapter(adapter);

                                createArticleUI(datumArticles.getItems());

                            }
                        } catch (Exception e ) {
                            HCIUtils.showMessage(MainActivity.this, e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Log.e("DATA === ", t.toString());
                        if (progress != null && progress.isShowing()) {
                            progress.dismiss();
                        }
                        HCIUtils.showMessage(MainActivity.this,"Connection Problem", "Please check your internet connection", "Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recreate();
                            }
                        }, false);
                    }
                });
    }

    private void createArticleUI(List<Item> listArticles) {
        layoutArticle = findViewById(R.id.layout_article);
        layoutArticle.removeAllViews();
        int count = 0;

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
            count++;
            if (count == listArticles.size()){
                progress.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}