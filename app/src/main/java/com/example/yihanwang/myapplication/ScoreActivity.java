package com.example.yihanwang.myapplication;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScoreActivity extends Activity {
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private TextView score4;
    private TextView score5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        score1 = (TextView) findViewById(R.id.level1);
        score2 = (TextView) findViewById(R.id.level2);
        score3 = (TextView) findViewById(R.id.level3);
        score4 = (TextView) findViewById(R.id.level4);
        score5 = (TextView) findViewById(R.id.level5);

        int total = 0;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

        for (ScoreRecord score : results) {
            total = total + score.getScore();
        }
        Log.i("score", "score " + total);

        realm.commitTransaction();

        if (total <= 100) {
            StringBuilder str1 = new StringBuilder();
            str1.append("Level 1 \n");
            str1.append(total + "/100");
            score1.setText(str1);
            score2.setText("Level 2 \n0/300");
            score3.setText("Level 3 \n0/500");
            score4.setText("Level 4 \n0/700");
            score5.setText("Level 5 \n0/900");
            blinkEffect(score1);
        } else if (total <= 300) {
            StringBuilder str2 = new StringBuilder();
            str2.append("Level 2 \n");
            str2.append(total + "/200");
            score1.setText("Level 1 \n100/100");
            score2.setText(str2);
            score3.setText("Level 3 \n0/500");
            score4.setText("Level 4 \n0/700");
            score5.setText("Level 5 \n0/900");
            blinkEffect(score2);
        } else if (total <= 500) {
            StringBuilder str3 = new StringBuilder();
            str3.append("Level 3 \n");
            str3.append(total + "/300");
            score1.setText("Level 1 \n100/100");
            score2.setText("Level 2 \n300/300");
            score3.setText(str3);
            score4.setText("Level 4 \n0/700");
            score5.setText("Level 5 \n0/900");
            blinkEffect(score3);
        } else if (total <= 700) {
            StringBuilder str4 = new StringBuilder();
            str4.append("Level 4 \n");
            str4.append(total + "/400");
            score1.setText("Level 1 \n100/100");
            score2.setText("Level 2 \n300/300");
            score3.setText("Level 3 \n500/500");
            score4.setText(str4);
            score5.setText("Level 5 \n0/900");
            blinkEffect(score4);
        } else {
            StringBuilder str5 = new StringBuilder();
            str5.append("Level 5 \n");
            str5.append(total + "/500");
            score1.setText("Level 1 \n100/100");
            score2.setText("Level 2 \n300/300");
            score3.setText("Level 3 \n500/500");
            score4.setText("Level 4 \n700/700");
            score5.setText(str5);
            blinkEffect(score5);
        }
    }

    private void blinkEffect(TextView text) {
        ObjectAnimator ani = ObjectAnimator.ofInt(text, "backgroundColor", Color.WHITE, Color.RED, Color.WHITE);
        ani.setDuration(1000);
        ani.setRepeatCount(Animation.INFINITE);
        ani.setEvaluator(new ArgbEvaluator());
        ani.setRepeatMode(Animation.INFINITE);
        ani.setRepeatMode(Animation.REVERSE);
        ani.start();

    }
}
