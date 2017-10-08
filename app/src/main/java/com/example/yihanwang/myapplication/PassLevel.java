package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kaley on 5/10/17.
 */

public class PassLevel  extends Activity {

    private TextView text;
    private ImageView nextPlant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_level_activity);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "teen.ttf");

        int result = 0;
        RealmResults<ScoreRecord> total = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
        for (ScoreRecord score : total) {
            result += score.getScore();
        }
        int currentLevel = ScoreUtils.getCurrentLevel(result);
        int nextLevel = ScoreUtils.getNextLevelImageNumber(result);

//        nextPlant = (ImageView) findViewById(R.id.nextPlant);
//        nextPlant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        text = (TextView) findViewById(R.id.textView);
        text.setText("Congratulations! You have reached level  " + currentLevel);
        text.setTypeface(font);
        //map = (ImageView) findViewById(R.id.map);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .3));
    }
}
