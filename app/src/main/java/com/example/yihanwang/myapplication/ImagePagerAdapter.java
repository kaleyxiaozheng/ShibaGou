package com.example.yihanwang.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private final List<ImageInfo> images = new ArrayList<>();
    private Context ctx;
    private LayoutInflater layoutInflater;

    public ImagePagerAdapter(Context c, List<ImageInfo> imagesFromLocation) {
        ctx = c;
        this.images.addAll(imagesFromLocation);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.image_swipe, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "retganon.ttf");
        final TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
        textView.setText("Image :" + (position + 1) + "/" + images.size());
        textView.setText((position + 1) + "/" + images.size());

        final TextView count = (TextView) itemView.findViewById(R.id.imageCount);
        final TextView name = (TextView) itemView.findViewById(R.id.imageName);
        final ImageInfo imageInfo = images.get(position);

        count.setText("Image :" + (position + 1) + "/" + images.size());
        count.setText((position + 1) + "/" + images.size());

        String commonName = imageInfo.getCommonName();
        String sciencename = imageInfo.getName();

        if (!commonName.isEmpty()) {
            name.setText(commonName + " (" + sciencename + ")");
        } else {
            name.setText(sciencename);
        }

        name.setTypeface(font);
        imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(ctx.getAssets(), imageInfo));
        container.addView(itemView);

        return itemView;
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

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}