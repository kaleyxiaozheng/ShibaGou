package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.yihanwang.myapplication.entities.ImageInfo;

/**
 * Created by Kaley on 30/9/17.
 */

public class ManageImage implements GestureDetector.OnGestureListener {
    private ImageInfo imageinfo;

    public ManageImage(ImageInfo imageinfo) {
        this.imageinfo = imageinfo;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
            {
                Intent intent = new Intent(getActivity(), PhotoImageActivity.class);
                Bundle args = new Bundle();
                args.putDouble("location_lat", lat);
                args.putDouble("location_lon", lon);
                args.putDouble("selected_image_id", imageinfo.getId());
                intent.putExtras(args);
                startActivity(intent);
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

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
