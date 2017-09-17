package com.example.yihanwang.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;


public class InfoPagerAdapter extends PagerAdapter {

    private final List<ImageInfo> images = new ArrayList<>();
    private Context ctx;
    private LayoutInflater layoutInflater;
    private TextView item;
    private TextView title;

    public InfoPagerAdapter(Context c, List<ImageInfo> images) {
        ctx = c;
        this.images.addAll(images);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.info_swipe, container, false);
        ImageInfo imageInfo = images.get(position);
        final ImageView imageView = (ImageView) view.findViewById(R.id.PlantPhoto);
        item = (TextView) view.findViewById(R.id.PlantRecord);
        item.setMovementMethod(new ScrollingMovementMethod());
        if(imageInfo != null  && imageInfo.getDescription() != null) {
            String[] words = imageInfo.getDescription().split("==");
            StringBuilder sb = new StringBuilder();
                sb.append(words[0]);
            item.setText(sb);
        }
        if(imageInfo != null) {
            title = (TextView) view.findViewById(R.id.PlantRecordTitle);
            String commonName = imageInfo.getCommonName();
            String sciencename = imageInfo.getName();

            if(!commonName.isEmpty()){
                title.setText(commonName + " (" + sciencename + ")");
            } else {
                title.setText(sciencename);
            }
            imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(ctx.getAssets(), imageInfo));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("view", "destory item");
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}