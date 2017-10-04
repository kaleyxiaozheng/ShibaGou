package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kaley on 29/9/17.
 */

public class EnvelopeActivity extends Activity {
    private TextView tip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.envelope_activity);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "teen.ttf");

        tip = (TextView) findViewById(R.id.message);
        int cur = ScoreUtils.getCurrentScores();
                int nex = ScoreUtils.getNextLevelImageNumber(cur);
                int dif = nex - cur/10;
                if(dif > 1){
                    tip.setText("After finding " + dif + " plants in your area, you will go to next level");
                } else {
                    tip.setText("After finding " + dif + " plant in your area, you will go to next level");
                }
        tip.setTypeface(font);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .3));

    }
}
