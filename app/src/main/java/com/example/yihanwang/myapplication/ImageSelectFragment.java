package com.example.yihanwang.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;
import com.example.yihanwang.myapplication.gps.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kaley on 18/9/17.
 */

public class ImageSelectFragment extends Fragment {

    private TextView image_number;
    private ImageView envelope;
    private List<ImageInfo> images = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private double lat;
    private double lon;
    private TextView title;
    private TextView message;

    public ImageSelectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_image, container, false);
        Bundle args = getArguments();
        lat = args.getDouble("location_lat");
        lon = args.getDouble("location_lon");

        this.images.clear();
        this.imageViews.clear();

        envelope = (ImageView) view.findViewById(R.id.envelope);
        envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.envelope_activity, null);
                Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "astron.ttf");
                Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "AYearWithoutRain.ttf");
                title = (TextView) view.findViewById(R.id.title);
                message = (TextView) view.findViewById(R.id.message);
                title.setTypeface(font1);
                message.setTypeface(font2);
                title.setText("CampingMate");

                int cur = ScoreUtils.getCurrentScores();
                int nex = ScoreUtils.getNextLevelImageNumber(cur);
                int dif = nex - cur/10;
                if(dif > 1){
                    message.setText("After finding " + dif + " plants in your area, you will go to next level");
                } else {
                    message.setText("After finding " + dif + " plant in your area, you will go to next level");
                }

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setView(view);
                builder.show();
            }
        });

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "retganon.ttf");

        int score = ScoreUtils.getCurrentScores();
        //Log.i("score", "current score " + score);
        int imageNumber = ScoreUtils.getNextLevelImageNumber(score);
        Log.i("image", "next level image number " + imageNumber);
        image_number = (TextView) view.findViewById(R.id.image_number);
        //TextView current_level = (TextView) view.findViewById(R.id.current_level);
        //TextView next_level = (TextView) view.findViewById(R.id.next_level);

        final List<ImageInfo> imageList = ImageStorage.getInstance().getImagesFromLocation(lat, lon);
        image_number.setTypeface(font);
        //current_level.setTypeface(font);
        //next_level.setTypeface(font);
        image_number.setText("There are " + imageList.size() + " plants nearby!");
        int lev = ScoreUtils.getCurrentLevel(score);
        //current_level.setText("you are level: " + lev);

        //next_level.setText("go to next Level, you need to take " + (imageNumber) + " more plants");
        imageNumber = Math.min(imageList.size(), imageNumber);
        for (int i = 0; i < imageNumber; i++) {
            ImageView image = new ImageView((getContext()));
            imageViews.add(image);
            this.images.add(imageList.get(i));
        }

        LinearLayout scrollImageLayout = (LinearLayout) view.findViewById(R.id.scroll_image_layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        for (int i = 0; i < this.images.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 3, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 0, 20, 20);

            imageViews.get(i).setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.get(i).setLayoutParams(layoutParams);
            scrollImageLayout.addView(imageViews.get(i));
            imageViews.get(i).setImageDrawable(ImageStorage.getInstance().getDrawable(getActivity().getAssets(), images.get(i)));
            //imageViews.get(i).setOnClickListener(new ImageClickListener(imageViews.get(i), getContext(), i));
            final ImageInfo imageinfo = images.get(i);

            ManageImage manageImage = new ManageImage(imageinfo, getActivity(), lat, lon, i, images, imageList, imageViews);
            final GestureDetector gestureDetector = new GestureDetector(getActivity(), manageImage);
//            final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
//
//                @Override
//                public boolean onDown(MotionEvent motionEvent) {
//                    return true;
//                }
//
//                @Override
//                public void onShowPress(MotionEvent motionEvent) {
//
//                }
//
//                @Override
//                public boolean onSingleTapUp(MotionEvent motionEvent) {
//                    switch (motionEvent.getAction()) {
//                        case MotionEvent.ACTION_UP:
//                        {
//                            Intent intent = new Intent(getActivity(), PhotoImageActivity.class);
//                            Bundle args = new Bundle();
//                            args.putDouble("location_lat", lat);
//                            args.putDouble("location_lon", lon);
//                            args.putDouble("selected_image_id", imageinfo.getId());
//                            intent.putExtras(args);
//                            startActivity(intent);
//                        }
//                    }
//                    return false;
//                }
//
//                @Override
//                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//                    return false;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent motionEvent) {
//                    Random r = new Random();
//                    Log.i("SwipeLocation", "Swipe location " + lo);
//                    images.remove(lo);
//                    int i = r.nextInt(imageList.size() - 1);
//                    images.add(lo, imageList.get(i));
//                    imageViews.get(lo).setImageDrawable(ImageStorage.getInstance().getDrawable(getActivity().getAssets(), images.get(lo)));
//
//                }
//
//                @Override
//                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                    return false;
//                }
//            });

            imageViews.get(i).setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }
        return view;
    }
}