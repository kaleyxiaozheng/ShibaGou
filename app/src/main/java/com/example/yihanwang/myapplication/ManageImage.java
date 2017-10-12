package com.example.yihanwang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Kaley on 30/9/17.
 */

public class ManageImage implements GestureDetector.OnGestureListener {
    private ImageInfo imageinfo;
    private double lat;
    private double lon;
    private Context ctx;
    private int i;
    private List<ImageInfo> images = new ArrayList<>();
    private List<ImageInfo> imageList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    public ManageImage(ImageInfo imageinfo, Context ctx, double lat, double lon, int i, List<ImageInfo> images, List<ImageInfo> imageList, List<ImageView> imageViews) {
        this.imageinfo = imageinfo;
        this.ctx = ctx;
        this.lat = lat;
        this.lon = lon;
        this.i = i;
        this.images.addAll(images);
        this.imageList.addAll(imageList);
        this.imageViews.addAll(imageViews);

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
            {
                Intent intent = new Intent(ctx, PhotoImageActivity.class);
                Bundle args = new Bundle();
                args.putDouble("location_lat", lat);
                args.putDouble("location_lon", lon);
                args.putDouble("selected_image_id", imageinfo.getId());
                intent.putExtras(args);
                ctx.startActivity(intent);
            }
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
//        double[] imagesID = new double[imageViews.size()];
//
//        for(int j = 0; j < imageViews.size(); j++){
//            imagesID[j] = images.get(j).getId();
//            Log.i("imagesID", "id " + imagesID[j]);
//        }
//
//
//
//        Random r = new Random();
//        //Log.i("SwipeLocation", "Swipe location " + i);
//        images.remove(this.i);
//        //Log.i("remove images", "remove " + images.get(this.i).getId());
//        int i = r.nextInt(imageList.size() - 1);
//        //Log.i("add images", "add " + images.get(i).getId());
//        //Log.i("id", "image id " + imageList.get(i).getId());
//
//        if(imageList.size() > images.size()){
//            for(int n = 0; n < imagesID.length; n++){
//
//                if(imageList.get(i).getId() == imagesID[n]){
//                    i++;
//                }
//            }
//            images.add(this.i, imageList.get(i));
//            imageViews.get(this.i).setImageDrawable(ImageStorage.getInstance().getDrawable(ctx.getAssets(), images.get(this.i)));
//            imageinfo = imageList.get(i);
//        }
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
