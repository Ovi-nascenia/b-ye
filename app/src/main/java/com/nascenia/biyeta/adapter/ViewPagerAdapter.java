package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    List<String> image;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<String> image) {
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        final ImageView proPics;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);


        // Capture position and set to the TextViews

        // Locate the ImageView in viewpager_item.xml
        proPics = (ImageView) itemView.findViewById(R.id.flag);
        // Capture position and set to the ImageView

        proPics.getParent().requestChildFocus(proPics,proPics);
        Picasso.with(context)
                .load(Utils.Base_URL + image.get(position))
                .into(proPics, new Callback() {
                    @Override
                    public void onSuccess() {
                        proPics.post(new Runnable() {
                            @Override
                            public void run() {
                                Utils.scaleImage(context, 2f, proPics);
                            }
                        });
                    }

                    @Override
                    public void onError() {
                    }
                });

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}