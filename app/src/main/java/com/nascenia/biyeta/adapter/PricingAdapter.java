package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nascenia.biyeta.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2/8/2018.
 */

public class PricingAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public PricingAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("Red", "red"));
        mItems.add(new Item("Magenta", null));
        mItems.add(new Item("Dark Gray", "Dark grey ffsdf fdsfsd"));
        mItems.add(new Item("Gray", "GRAY"));
        mItems.add(new Item("Green", "green"));
        mItems.add(new Item("Cyan", "CYAN"));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

//    @Override
//    public long getItemId(int i) {
//        return mItems.get(i).drawableId;
//    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
//        ImageView picture;
        TextView name1, name2;
        LinearLayout ln_img;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            //            v.setTag(R.id.picture, v.findViewById(R.id.picture));
//            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

//        picture = (ImageView) v.getTag(R.id.picture);
        name1 = (TextView) v.findViewById(R.id.text1);
        name2 = (TextView) v.findViewById(R.id.text2);
        ln_img = v.findViewById(R.id.ln_img);

        Item item = getItem(i);

//        picture.setImageResource(item.drawableId);
        name1.setText(item.name1);
        if(item.name2!=null) {
            name2.setVisibility(View.VISIBLE);
            ln_img.setVisibility(View.GONE);
            name2.setText(item.name2);
        }else{
            ln_img.setVisibility(View.VISIBLE);
            name2.setVisibility(View.GONE);
        }

        return v;
    }

    private static class Item {
        public final String name1;
        public final String name2;
//        public final int drawableId;

        Item(String name1, String name2) {
            this.name1 = name1;
            this.name2 = name2;
//            this.drawableId = drawableId;
        }
    }
}
