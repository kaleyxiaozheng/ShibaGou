package com.example.yihanwang.myapplication;

import android.content.Context;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
=======
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
<<<<<<< HEAD
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.example.yihanwang.myapplication.entities.ImageGalery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Progress;

=======
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGalery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;
>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b

public class ImagePagerAdapter extends PagerAdapter {

    private final List<ImageInfo> images = new ArrayList<>();
    private Context ctx;
    private LayoutInflater layoutInflater;
<<<<<<< HEAD
=======
    private ImageView imageView;
>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b

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
<<<<<<< HEAD
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);

        final TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
        ImageInfo imageInfo = images.get(position);
        textView.setText("Image :" + (position + 1) + "/" + images.size());
        textView.setText((position + 1) + "/" + images.size());
=======
        imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);

        final TextView count = (TextView) itemView.findViewById(R.id.imageCount);
        final TextView name = (TextView) itemView.findViewById(R.id.imageName);
        final ImageInfo imageInfo = images.get(position);
        count.setText("Image :" + (position + 1) + "/" + images.size());
        count.setText((position + 1) + "/" + images.size());

        String commonName = imageInfo.getCommonName();
        String sciencename = imageInfo.getName();

        if(!commonName.isEmpty()){
            name.setText(commonName + " (" + sciencename + ")");
        } else {
            name.setText(sciencename);
        }

>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b
        final ImageGalery imageGalery = ImageGaleryStorage.getInstance().getImageGalery(imageInfo.getId());
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
                final int imageGalaryIdx = i;
                ImageView compare = (ImageView) itemView.findViewById(viewIds[i]);
<<<<<<< HEAD
                compare.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //switch (event.getAction()){
                          //  case MotionEvent.ACTION_UP:
                                Log.i("image", "remove image galary on " + imageGalaryIdx);
                                imageGalery.removeImage(imageGalaryIdx);
                                notifyDataSetChanged();
                        //}
                        return true;
                    }
                    /*
                    @Override
                    public boolean (View view, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_UP:
                                Log.i("image", "remove image galary on " + imageGalaryIdx);
                                imageGalery.removeImage(imageGalaryIdx);
                                notifyDataSetChanged();
                        }
                        return true;
                    }
                    */
=======
                compare.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_UP:
                                Log.i("image", "remove image galary on " + imageGalaryIdx);
                                //imageGalery.removeImage(imageGalaryIdx);
                                //notifyDataSetChanged();
                                Intent intent = new Intent(view.getContext(), ComparisonActivity.class);
                                Bundle b = new Bundle();
                                b.putDouble("firstImage", imageInfo.getId());
                                b.putInt("secondImage", imageGalaryIdx);
                                intent.putExtras(b);
                                view.getContext().startActivity(intent);
                        }
                        return true;
                    }
>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b
                });
                compare.setImageBitmap(image);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) compare.getLayoutParams();
                layoutParams.weight = 1;
            }
        }
        imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(ctx.getAssets(), imageInfo));
        container.addView(itemView);
<<<<<<< HEAD

        //AnimateHorizontalProgressBar progressBar = (AnimateHorizontalProgressBar)itemView.findViewById(R.id.progressBar);
        ProgressBar progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        //progressBar.setMax(1000);
        //progressBar.setProgress(400);
=======
>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("view", "destory item");
        container.removeView((View) object);
    }

<<<<<<< HEAD

=======
>>>>>>> 69da91aa6fba1bf7040a96bf3128aa7b0249e98b
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}