package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Kaley on 29/9/17.
 */

public class TipActivity extends Activity {
    private TextView tip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_activity);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "teen.ttf");

        tip = (TextView) findViewById(R.id.tip);
        tip.setText("➤ Click on a photo you have taken to compare \n\n ➤ Swipe up on a photo you just took to delete it");
        tip.setTypeface(font);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .5));
    }
}
