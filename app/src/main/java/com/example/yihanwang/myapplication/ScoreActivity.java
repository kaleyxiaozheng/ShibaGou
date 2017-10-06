package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Kaley on 18/9/17.
 */

public class ScoreActivity extends AppCompatActivity {
    //private TextView nexLevel;
    private ImageView gallery;
    private ImageView home;
    private TextView userScore;
    private TextView levelTxt;
    boolean check = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Typeface font = Typeface.createFromAsset(getAssets(), "teen.ttf");

        int total = ScoreUtils.getCurrentScores();
        float imageNumber = ScoreUtils.getNextLevelImageNumber(total) * 10;
        int level = ScoreUtils.getCurrentLevel(total);
        float percent = (level-1) / imageNumber;
        float progress = percent * 100;
        userScore = (TextView) findViewById(R.id.score);
        userScore.setText("Your score: " + total);
        levelTxt = (TextView) findViewById(R.id.level);
        //levelTxt.setText("You are level " + ScoreUtils.getCurrentLevel(total));
        levelTxt.setText("Level\n" + ScoreUtils.getCurrentLevel(total));
        levelTxt.setTypeface(font);

//        nexLevel = (TextView) findViewById(R.id.nextLevel);
//        nexLevel.setText("next level " + (ScoreUtils.getCurrentLevel(total) + 1));
//        nexLevel.setTypeface(font);

        gallery = (ImageView) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subscription = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(subscription);
            }
        });

        home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.circleBar);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        for (int i = 0; i < 20; i++) {
            if (total == ScoreUtils.getLevel_score()[i]) {
                check = true;
            }
        }

        //if(check == true){
        //    circularProgressBar.setProgressWithAnimation(100, animationDuration);
        //}
        //else {
        circularProgressBar.setProgressWithAnimation(progress, animationDuration); // Default duration = 1500ms

        //}
    }


}