package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kaley on 18/9/17.
 */

public class ScoreActivity extends Activity {
    private TextView curLevel;
    private TextView nexLevel;
    private Button gallery;
    private String currentLevel;
    private String nextLevel;
    LevelAndScores las;

    public ScoreActivity() {
        las = new LevelAndScores();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        curLevel = (TextView) findViewById(R.id.currentLevel);
        nexLevel = (TextView) findViewById(R.id.nextLevel);

        int total = 0;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

        for (ScoreRecord score : results) {
            total = total + score.getScore();
        }
        realm.commitTransaction();

        currentLevel = getLevelTitle(total);
        nextLevel = getNextLevel(total);

        if (currentLevel.isEmpty()) {
            currentLevel = "Something wrong with your level";
        }

        if (nextLevel.isEmpty()) {
            nextLevel = "Something wrong with the application";
        }

        curLevel.setText(currentLevel);
        nexLevel.setText(nextLevel);

        gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subscription = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(subscription);

            }
        });
    }

    public String getLevelTitle(int total) {
        String title = "";

        for (int i = 0; i < 20; i++) {
            if (total < las.LEVEL_SCORE[i]) {
                if (i == 0) {
                    title = "Your level is 0";
                    if (!title.isEmpty()) {
                        return title;
                    }
                } else {
                    title = "Your level is " + las.LEVEL_TITLE[i - 1];
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
