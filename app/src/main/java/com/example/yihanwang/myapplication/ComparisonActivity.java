package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

public class ComparisonActivity extends Activity {
    ImageView plantImage;
    ImageView plantPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparison_activity);
        plantImage = (ImageView) findViewById(R.id.plantImage);
        plantPhoto = (ImageView) findViewById(R.id.plantPhoto);

        Bundle b = getIntent().getExtras();
        double index1 = -1;
        int index2 = -1;
        if (b != null){
            index1 = b.getDouble("firstImage");
            index2 = b.getInt("secondImage");

            ImageInfo first  = ImageStorage.getInstance().getImagebyId(index1);
            plantImage.setImageDrawable(ImageStorage.getInstance().getDrawable(this.getAssets(), first));
            ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(first.getId());
            Bitmap bitmap = imageGalery.getImage(index2);
            plantPhoto.setImageBitmap(bitmap);

        }

    }

}
