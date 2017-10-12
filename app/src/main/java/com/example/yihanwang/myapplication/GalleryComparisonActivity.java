package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;

/**
 * Created by Kaley on 11/10/17.
 */

public class GalleryComparisonActivity extends Activity {
    private ImageView plantImage;
    private ImageView plantPhoto;
    private ImageView nextPlant;

    private TextView plant;
    private TextView photo;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_comparison_activity);

        plantImage = (ImageView) findViewById(R.id.plantImage);
        plantPhoto = (ImageView) findViewById(R.id.plantPhoto);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "teen.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "teen.ttf");

        plant = (TextView) findViewById(R.id.plantname);
        photo = (TextView) findViewById(R.id.yourphoto);
        title = (TextView) findViewById(R.id.title);
        title.setText("Comparison");
        photo.setText("Your photo:");

        title.setTypeface(font1);
        photo.setTypeface(font2);

        Bundle b = getIntent().getExtras();
        double index1;
        int index2;
        if (b != null) {
            index1 = b.getDouble("firstImage");
            index2 = b.getInt("secondImage");

            ImageInfo first = ImageStorage.getInstance().getImagebyId(index1);
            String com = first.getCommonName();

            if (com.isEmpty()) {
                plant.setText(first.getName() + ":");
            } else {
                plant.setText(com + " (" + first.getName() + "):");
            }

            plant.setTypeface(font2);
            plantImage.setImageDrawable(ImageStorage.getInstance().getDrawable(this.getAssets(), first));
            ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(first.getId());
            Bitmap bitmap = imageGalery.getImage(index2);
            plantPhoto.setImageBitmap(bitmap);

        }
    }
}
