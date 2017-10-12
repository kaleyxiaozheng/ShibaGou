package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

public class ComparisonActivity extends AppCompatActivity {
    private ImageView plantImage;
    private ImageView plantPhoto;
    private ImageView nextPlant;

    private TextView plant;
    private TextView photo;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparison_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        plantImage = (ImageView) findViewById(R.id.plantImage);
        plantPhoto = (ImageView) findViewById(R.id.plantPhoto);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "teen.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "teen.ttf");

        plant = (TextView) findViewById(R.id.plantname);
        photo = (TextView) findViewById(R.id.yourphoto);
        title = (TextView) findViewById(R.id.title);
        title.setText("Comparison");
        photo.setText("Your photo:");

        nextPlant = (ImageView) findViewById(R.id.nextPlant);
        nextPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
