package com.example.yihanwang.myapplication;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kaley on 18/9/17.
 */

public class ScoreUtils {

    public static int[] level_score = new int[20];
    public static String[] level_title = new String[20];

    public static void setScoresLevels() {
        level_score[0] = 0;
        level_score[1] = 20;

        level_title[0] = "Level 1";

        for (int i = 2; i < level_score.length; i++) {
            level_score[i] = i * 10 + level_score[1] + level_score[i - 1];
            level_title[i] = "Level " + (i + 1);
        }
    }


    public static int getCurrentScores() {
        int total = 0;
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

        for (ScoreRecord score : results) {
            total = total + score.getScore();
        }
        return total;
    }

    public static int getNextLevelImageNumber(int currentScore) {
        setScoresLevels();
        for (int i = 0; i < 20; i++) {
            if (currentScore < level_score[i]) {
                return (level_score[i]) / 10;
            }
        }
        return -1;
    }


    public static int getCurrentLevel(int currentScore) {
        setScoresLevels();
        for (int i = 0; i < 20; i++) {
            if (currentScore < level_score[i]) {
                if (i == 0) {
                    return 1;
                }
                return i;
            }
            if (currentScore == level_score[i]) {
                return i + 1;
            }
        }
        return -1;
    }

    public static int[] getLevel_score() {
        return level_score;
    }
//
//    public static String[] getLevel_title() {
//        return level_title;
//    }
}
