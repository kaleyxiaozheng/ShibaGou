package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import java.io.Serializable;

/**
 * Created by Kaley on 18/9/17.
 */

public class AppAdActivity extends AppCompatActivity {

    private VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_ad_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        video = (VideoView)findViewById(R.id.appAd);
        String path = "android.resource://com.example.yihanwang.myapplication/" + R.raw.app_ad;
        Uri uri = Uri.parse(path);
        video.setVideoURI(uri);
        video.start();
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);


    }
}
