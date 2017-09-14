package com.example.yihanwang.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGalery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;


public class ImagePagerAdapter extends PagerAdapter {

    private long then;
    private int longClickDuration = 5000;
    private ImageView compare;
    private final List<ImageInfo> images = new ArrayList<>();
    private Context ctx;
    private LayoutInflater layoutInflater;

    public ImagePagerAdapter(Context c, List<ImageInfo> imagesFromLocation) {
        ctx = c;
        this.images.addAll(imagesFromLocation);
        this.managingImageView();
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

        final TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
        ImageInfo imageInfo = images.get(position);
        textView.setText("Image :" + (position + 1) + "/" + images.size());
        textView.setText((position + 1) + "/" + images.size());
        ImageGalery imageGalery = ImageGaleryStorage.getInstance().getImageGalery(imageInfo.getId());
        if (imageGalery != null) {
            int viewIds[] = {R.id.compare_image_view1, R.id.compare_image_view2, R.id.compare_image_view3};
            if (imageGalery.getImageCount() > 0) {
                View galeryContainer = itemView.findViewById(R.id.image_galery_container);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galeryContainer.getLayoutParams();
                layoutParams.weight = 1;
            }
            for (int i = 0; i < viewIds.length; i++) {
                Bitmap image = imageGalery.getImage(i);
                if (image == null) {
                    break;
                }
                compare = (ImageView) itemView.findViewById(viewIds[i]);
                compare.setImageBitmap(image);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) compare.getLayoutParams();
                layoutParams.weight = 1;
            }
        }
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

    public void managingImageView() {
        if(compare != null){
            compare.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        then = (long) System.currentTimeMillis();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        if ((System.currentTimeMillis() - then) > longClickDuration) {
            /* Implement long click behavior here */
                            System.out.println("Long Click has happened!");
                            return false;
                        } else {
            /* Implement short click behavior here or do nothing */
                            System.out.println("Short Click has happened...");
                            return true;
                        }
                    }
                    return true;
                }
            });
        }

    }

}

