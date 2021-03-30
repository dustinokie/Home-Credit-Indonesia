package com.example.homecreditindonesia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecreditindonesia.R;
import com.example.homecreditindonesia.activity.MainActivity;
import com.example.homecreditindonesia.model.Item;
import com.example.homecreditindonesia.utils.HCIUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuAdapter extends BaseAdapter {

    private MainActivity activity;
    private List<Menus> listMenus;

    public ProductMenuAdapter(MainActivity act, List<Item> itemArr) {
        this.activity = act;
        setListMenus(itemArr);
    }

    @Override
    public int getCount() { return  listMenus.size(); }

    @Override
    public Object getItem(int position) {
        return listMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MenuViewHolder holder;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_menu, null);

            holder = new MenuViewHolder(v);
            v.setTag(holder);

        }else{
            holder = (MenuViewHolder) v.getTag();
        }

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        if(width <= 720) {
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
        } else {
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
        }

        Menus menus = listMenus.get(position);
        Picasso.get().load(menus.getImage()).into(holder.icon);
        holder.title.setText(menus.getName());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( menus.getLink() ) );
                activity.startActivity(browse);
            }
        });

        return v;
    }

    private void setListMenus(List<Item> itemArr) {
        try {


            listMenus = new ArrayList<>();
            Menus menus;

            for (int i = 0; i < itemArr.size(); i++) {
                Item item = itemArr.get(i);
                menus = new Menus();
                menus.setImage(item.getProductImage());
                menus.setName(item.getProductName());
                menus.setLink(item.getLink());

                listMenus.add(menus);
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    class Menus {
        String name;
        String image;
        String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    class MenuViewHolder {

        ImageView icon;
        TextView title;

        public MenuViewHolder(View v) {
            icon = v.findViewById(R.id.iv_menu);
            title = v.findViewById(R.id.tv_menu);
        }
    }
}
