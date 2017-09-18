package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

public class ComparisonActivity extends Activity {
    private ImageView plantImage;
    private ImageView plantPhoto;

    private TextView plant;
    private TextView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparison_activity);
        plantImage = (ImageView) findViewById(R.id.plantImage);
        plantPhoto = (ImageView) findViewById(R.id.plantPhoto);

        plant = (TextView) findViewById(R.id.plantname);
        photo = (TextView) findViewById(R.id.yourphoto);

        photo.setText("Your photo");

        Bundle b = getIntent().getExtras();
        double index1 = -1;
        int index2 = -1;
        if (b != null) {
            index1 = b.getDouble("firstImage");
            index2 = b.getInt("secondImage");

            ImageInfo first = ImageStorage.getInstance().getImagebyId(index1);
            String com = first.getCommonName();

            if (com.isEmpty()) {
                plant.setText(first.getName());
            } else {
                plant.setText(com + " (" + first.getName() +")");
            }

            plantImage.setImageDrawable(ImageStorage.getInstance().getDrawable(this.getAssets(), first));
            ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(first.getId());
            Bitmap bitmap = imageGalery.getImage(index2);
            plantPhoto.setImageBitmap(bitmap);

        }

    }

}
