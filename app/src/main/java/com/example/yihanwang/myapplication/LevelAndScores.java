package com.example.yihanwang.myapplication;

public class LevelAndScores {

    public int[] LEVEL_SCORE = new int[20];
    public String[] LEVEL_TITLE = new String[20];

    public LevelAndScores() {
        LEVEL_SCORE[0] = 20;
        LEVEL_TITLE[0] = "Level 1";

        for(int i = 1; i < LEVEL_SCORE.length; i++) {
            LEVEL_SCORE[i] = i*10 + LEVEL_SCORE[0] + LEVEL_SCORE[i-1];
            LEVEL_TITLE[i] = "Level " + (i+1);
        }
    }
}
