package com.example.yihanwang.myapplication.entities;

import io.realm.RealmObject;

public class ScoreRecord extends RealmObject {

    private int id;

    private int score;

    private double imageId;


    public ScoreRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getImageId() {
        return imageId;
    }

    public void setImageId(double imageId) {
        this.imageId = imageId;
    }
}
