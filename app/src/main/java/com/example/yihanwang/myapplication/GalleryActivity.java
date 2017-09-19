package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kaley on 18/9/17.
 */

public class GalleryActivity extends Activity {

    private int deviceWidth = 1024;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;

        GridView gridView = (GridView) findViewById(R.id.gallery_view);

        RealmResults<ScoreRecord> allRecords = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
        gridView.setAdapter(new ImageAdapter(this, allRecords, deviceWidth));

    }
}

class ImageAdapter extends BaseAdapter{

    private final int deviceWidth;
    private Context context;
    private List<Bitmap> images = new ArrayList<>();
    private List<Double> imageIds = new ArrayList<>();
    private List<Integer> imageGalleryIds = new ArrayList<>();

    public ImageAdapter(Context context, RealmResults<ScoreRecord> files, int deviceWidth){
        this.context = context;
        this.deviceWidth = deviceWidth;
        for(ScoreRecord file : files){
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getImagePath());
                images.add(bitmap);
                imageIds.add(file.getImageId());
                imageGalleryIds.add(file.getId());
                Log.i("db", "image gallery id " + file.getId());
            }catch(Exception e){
                Log.e("image", e.getMessage());
            }
        }
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams((deviceWidth - 50) / 3, (deviceWidth - 50) / 3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(images.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GalleryImageViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("image_id", imageIds.get(position));
                bundle.putInt("image_gallery_id", imageGalleryIds.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return imageView;
    }
}
