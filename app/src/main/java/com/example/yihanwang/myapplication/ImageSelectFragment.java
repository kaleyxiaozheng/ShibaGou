package com.example.yihanwang.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * Created by kaley on 18/9/17.
 */

public class ImageSelectFragment extends Fragment {

    private TextView image_number;
    private ImageView bird;
    private ImageView instruction;
    private ImageView next_plant;
    private List<ImageInfo> images = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private double lat;
    private double lon;
    private List<ImageInfo> imageList;

    public ImageSelectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_image, container, false);
        Bundle args = getArguments();
        lat = args.getDouble("location_lat");
        lon = args.getDouble("location_lon");

        this.images.clear();
        this.imageViews.clear();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "teen.ttf");

        instruction = (ImageView) view.findViewById(R.id.instruction);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InstructionActivity.class));
            }
        });

        bird = (ImageView) view.findViewById(R.id.bird);
        bird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), EnvelopeActivity.class));
            }

        });

        int score = ScoreUtils.getCurrentScores();
        int imageNumber = ScoreUtils.getNextLevelImageNumber(score);
        Log.i("image", "next level image number " + imageNumber);
        image_number = (TextView) view.findViewById(R.id.image_number);


        imageList = ImageStorage.getInstance().getImagesFromLocation(lat, lon);

        image_number.setTypeface(font);
        int result = ScoreUtils.getCurrentScores();
        int level = ScoreUtils.getCurrentLevel(result);
        image_number.setText("There are " + imageList.size() + " plants nearby!");
        int lev = ScoreUtils.getCurrentLevel(score);

        imageNumber = imageList.size();
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

            final ImageInfo imageinfo = images.get(i);

            ManageImage manageImage = new ManageImage(imageinfo, getActivity(), lat, lon, i, images, imageList, imageViews);
            final GestureDetector gestureDetector = new GestureDetector(getActivity(), manageImage);

            imageViews.get(i).setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }
        List<Double> databaseImagesId = new ArrayList<>(getImagesDatabase());
        if(imageViews.size() > databaseImagesId.size()){
            for (int i = 0; i < imageViews.size(); i++) {
                if (databaseImagesId.contains(imageList.get(i).getId())) {
                    imageViews.get(i).setAlpha((float) 0.3);
                }
            }
        } else {
            for (int i = 0; i < databaseImagesId.size(); i++) {
                if (databaseImagesId.contains(imageList.get(i).getId())) {
                    imageViews.get(i).setAlpha((float) 0.3);
                }
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Double> databaseImagesId = new ArrayList<>(getImagesDatabase());

        if(imageViews.size() > databaseImagesId.size()){
            for (int i = 0; i < imageViews.size(); i++) {
                if (databaseImagesId.contains(imageList.get(i).getId())) {
                    imageViews.get(i).setAlpha((float) 0.3);
                }
            }
        } else {
            for (int i = 0; i < databaseImagesId.size(); i++) {
                if (databaseImagesId.contains(imageList.get(i).getId())) {
                    imageViews.get(i).setAlpha((float) 0.3);
                }
            }
        }
    }

    public List<Double> getImagesDatabase() {

        Realm realm = Realm.getDefaultInstance();
        List<Double> imagesId = new ArrayList<>();

        realm.beginTransaction();
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
        for (ScoreRecord score : results) {
            if (!imagesId.contains(score.getImageId())) {
                imagesId.add(score.getImageId());
            }
        }
        realm.commitTransaction();

        return imagesId;
    }
}