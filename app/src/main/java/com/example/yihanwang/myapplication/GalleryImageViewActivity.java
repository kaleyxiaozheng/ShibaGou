package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kaley on 19/9/17.
 */

public class GalleryImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_image_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle bundle = getIntent().getExtras();
        double imageId = bundle.getDouble("image_id");
        int imageGalleryId = bundle.getInt("image_gallery_id");
        Log.i("image", "image id " + imageId + ", image gallery id " + imageGalleryId);
        ScoreRecord record = Realm.getDefaultInstance().where(ScoreRecord.class).equalTo("id", imageGalleryId).findFirst();
        if (record != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(record.getImagePath());

            ImageView imageView = (ImageView) findViewById(R.id.gallery_image_view);
            imageView.setImageBitmap(bitmap);


        } else {
            Log.e("image", "cant find image gallery from  DB " + imageGalleryId);
        }
        ImageInfo imageInfo = ImageStorage.getInstance().getImagebyId(imageId);
        if (imageInfo != null) {
            TextView textView = (TextView) findViewById(R.id.gallery_image_text);
            Typeface font = Typeface.createFromAsset(getAssets(),"BlessingsthroughRaindrops.ttf");
            textView.setText(imageInfo.getName());
            textView.setTypeface(font);
        } else {
            Log.e("image", "Image id cant find " + imageId);
        }
    }
}
