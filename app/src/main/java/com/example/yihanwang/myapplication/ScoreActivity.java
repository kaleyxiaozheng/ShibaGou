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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScoreActivity extends Activity {
    private TextView view1;
    private TextView view2;
    private TextView view3;
    private TextView view4;
    private TextView view5;

//    public void getScoreList() {
//        List<TextView> scores = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            String id = "R.id.level" + (i + 1);
//            scores.add(i, (TextView) findViewById(Integer.valueOf(id)));
//            scores.get(i).setText("Level " + (i + 1));
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
            view1 = (TextView) findViewById(R.id.level1);
            view2 = (TextView) findViewById(R.id.level2);
            view3 = (TextView) findViewById(R.id.level3);
            view4 = (TextView) findViewById(R.id.level4);
            view5 = (TextView) findViewById(R.id.level5);
            view1.setText("Level 1");
            view2.setText("Level 2");
            view3.setText(":");
            view4.setText(":");
            view5.setText(":");

//        int total = 0;
//        Realm realm = Realm.getDefaultInstance();
//
//        realm.beginTransaction();
//        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
//
//        for (ScoreRecord score : results) {
//            total = total + score.getScore();
//        }
//        Log.i("score", "score " + total);
//
//        realm.commitTransaction();

    }

//    private void blinkEffect(TextView text) {
//        ObjectAnimator ani = ObjectAnimator.ofInt(text, "backgroundColor", Color.WHITE, Color.RED, Color.WHITE);
//        ani.setDuration(1000);
//        ani.setRepeatCount(Animation.INFINITE);
//        ani.setEvaluator(new ArgbEvaluator());
//        ani.setRepeatMode(Animation.INFINITE);
//        ani.setRepeatMode(Animation.REVERSE);
//        ani.start();
//
//    }

}
