package com.example.yihanwang.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kaley on 24/8/17.
 */

public class CustomSwip extends PagerAdapter {

    private int[] imageResource = {R.drawable.golddust_wattle, R.drawable.lightwood, R.drawable.pincushiionlily, R.drawable.spike_watte};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwip(Context c) {
        ctx = c;
    }

    @Override
    public int getCount() {
        return imageResource.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater.inflate(R.layout.activity_custom_swip, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);
            TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
            imageView.setImageResource(imageResource[position]);
            textView.setText("Image Counter :" + position);
            container.addView(itemView);

            return itemView;

    }

    @Override
    public void destroyItem (ViewGroup container,int position, Object object){

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}