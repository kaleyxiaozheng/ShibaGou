package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ScoreRecord;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kaley on 18/9/17.
 */

public class ScoreActivity extends Activity {
    private TextView nexLevel;
    private ImageView gallery;
    private String currentLevel;
    private String nextLevel;
    private TextView userScore;
    private TextView levelTxt;
    LevelAndScores las;

    public ScoreActivity() {
        las = new LevelAndScores();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        Typeface font = Typeface.createFromAsset(getAssets(),"BlessingsthroughRaindrops.ttf");

        nexLevel = (TextView) findViewById(R.id.nextLevel);

        int total = ScoreUtils.getScore();
        userScore = (TextView) findViewById(R.id.displayScore);
        userScore.setText(String.valueOf(total));
        levelTxt = (TextView) findViewById(R.id.level);
        levelTxt.setText(getLevelTitle(total));
        levelTxt.setTypeface(font);
        nextLevel = getNextLevel(total);
        nexLevel.setText(nextLevel);
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

    public String getLevelTitle(int total) {
        String title = "";

        for (int i = 0; i < 20; i++) {
            if (total < las.LEVEL_SCORE[i]) {
                if (total == 0) {
                    title = "You are in level 0";
                    if (!title.isEmpty()) {
                        return title;
                    }
                } else {
                    title = "You are in " + las.LEVEL_TITLE[i-1];
                    if (!title.isEmpty()) {
                        return title;
                    }
                }
            }
        }
        return title;
    }

    public String getNextLevel(int total) {
        String next = "";

        for (int i = 0; i < 20; i++) {
            if (total <= las.LEVEL_SCORE[i]) {
                int imageNum = (las.LEVEL_SCORE[i] - total) / 10;
                if (imageNum > 1) {
                    next = "Find " + imageNum + " more plants to reach the next level";
                    if (!next.isEmpty()) {
                        return next;
                    }
                }
                if (imageNum == 1) {
                    next = "Find 1 more plant to reach the next level";
                    if (!next.isEmpty()) {
                        return next;
                    }
                }
                if (imageNum == 0) {
                    next = "Find " + (las.LEVEL_SCORE[i + 1] - total) / 10 + " more plants to reach the next level";
                }
            }
        }
        return next;
    }
}
