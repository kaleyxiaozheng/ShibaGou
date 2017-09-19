package com.example.yihanwang.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

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
        ImageInfo imageInfo = ImageStorage.getInstance().getImagebyId(imageId);
        if(imageInfo != null){
            TextView textView = (TextView) findViewById(R.id.gallery_image_text);
            textView.setText(imageInfo.getName());

            ImageView imageView = (ImageView) findViewById(R.id.gallery_image_view);
            imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(getAssets(), imageInfo));
        }
    }
}
