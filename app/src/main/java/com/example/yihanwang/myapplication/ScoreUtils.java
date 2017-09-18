package com.example.yihanwang.myapplication;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kaley on 18/9/17.
 */

public class ScoreUtils {

    public static int getScore() {
        int total = 0;
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

        for (ScoreRecord score : results) {
            total = total + score.getScore();
        }
        return total;
    }

    public static int getNextLevelImageNumber(int currentScore) {
        LevelAndScores las = new LevelAndScores();
        for (int i = 0; i < 20; i++) {
            if (currentScore < las.LEVEL_SCORE[i]) {
                return (las.LEVEL_SCORE[i]) / 10;
            }
        }
        return -1;
    }


    public static int getCurrentLevel(int currentScore) {
        LevelAndScores las = new LevelAndScores();
        for (int i = 0; i < 20; i++) {
            if (currentScore <= las.LEVEL_SCORE[i]) {
                return i;
            }
        }
        return -1;
    }

}
