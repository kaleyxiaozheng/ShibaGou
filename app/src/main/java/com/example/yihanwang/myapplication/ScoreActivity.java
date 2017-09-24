package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Kaley on 18/9/17.
 */

public class ScoreActivity extends Activity {
    private TextView nexLevel;
    private ImageView gallery;
    private String nextLevel;
    private TextView userScore;
    private TextView levelTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        Typeface font = Typeface.createFromAsset(getAssets(),"retganon.ttf");


        int total = ScoreUtils.getCurrentScores();
        userScore = (TextView) findViewById(R.id.displayScore);
        userScore.setText(String.valueOf(total));
        levelTxt = (TextView) findViewById(R.id.level);
        levelTxt.setText("You are level " + ScoreUtils.getCurrentLevel(total));
        levelTxt.setTypeface(font);
        nexLevel = (TextView) findViewById(R.id.nextLevel);
        nexLevel.setText("next level " + (ScoreUtils.getCurrentLevel(total) + 1));
        nexLevel.setTypeface(font);

        gallery = (ImageView) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subscription = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(subscription);
            }
        });

        CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.progressBar);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(total, animationDuration); // Default duration = 1500ms
    }

//    public String getLevelTitle(int total) {
//        String title = "";
//
//        for (int i = 0; i < 20; i++) {
//            if (total < las.LEVEL_SCORE[i]) {
//                if (i == 0) {
//                    title = "You are level 0";
//                    if (!title.isEmpty()) {
//                        return title;
//                    }
//                } else {
//                    title = "You are " + las.LEVEL_TITLE[i-1];
//                    if (!title.isEmpty()) {
//                        return title;
//                    }
//                }
//            }
//        }
//        return title;
//    }
//
//    public String getNextLevel(int total) {
//        String next = "";
//
//        for (int i = 0; i < 20; i++) {
//            if (total <= las.LEVEL_SCORE[i]) {
//                int imageNum = (las.LEVEL_SCORE[i] - total) / 10;
//                if (imageNum > 1) {
//                    next = "Find " + imageNum + " more plants to reach the next level";
//                    if (!next.isEmpty()) {
//                        return next;
//                    }
//                }
//                if (imageNum == 1) {
//                    next = "Find 1 more plant to reach the next level";
//                    if (!next.isEmpty()) {
//                        return next;
//                    }
//                }
//                if (imageNum == 0) {
//                    next = "Find " + (las.LEVEL_SCORE[i + 1] - total) / 10 + " more plants to reach the next level";
//                }
//            }
//        }
//        return next;
//    }
}
