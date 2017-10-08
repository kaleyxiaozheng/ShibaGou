package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.Serializable;

/**
 * Created by Kaley on 18/9/17.
 */

public class AppAdActivity extends AppCompatActivity {
    private TextView con;
    private StringBuffer sb;

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_ad_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sb = new StringBuffer();
        sb.append("1. Plants Records \n");
        sb.append("Atlas of Living Australia occurrence download at http://www.ala.org.au. Accessed 3 November 2015");
        sb.append("\n\n");
        sb.append("2. Plants Images \n");
        sb.append("Atlas of Living Australia occurrence download at https://biocache.ala.org.au/. Accessed from http://www.ala.org.au");
        sb.append("\n\n");
        sb.append("3. Plants Information \n");
        sb.append("MediaWiki API download at https://en.wikipedia.org/. Is licensed: GPL-2.0+");
        sb.append("\n\n");
        sb.append("4. Google Maps Android \n");
        sb.append("Google Maps Android API is from https://developers.google.com/maps/documentation/android-api/reference Licensed under the Creative Commons Attribution 3.0 License");
        sb.append("\n\n");

        video = (VideoView) findViewById(R.id.appAd);
        String path = "android.resource://com.example.yihanwang.myapplication/" + R.raw.app_ad;
        Uri uri = Uri.parse(path);
        video.setVideoURI(uri);
        video.start();

        con = (TextView) findViewById(R.id.attribution);
        con.setText(sb);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
