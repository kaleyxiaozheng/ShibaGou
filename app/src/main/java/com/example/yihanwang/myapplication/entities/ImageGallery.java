package com.example.yihanwang.myapplication.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ImageGallery {

    private double id;

    private List<Bitmap> bitmap = new ArrayList<>();

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public ImageGallery(double id, String filePath) {
        this.id = id;
        this.filePath = filePath;

    }

    public void addImage(Bitmap bitmap) {
        this.bitmap.add(bitmap);
    }

    public Bitmap getImage(int i) {
        if (this.bitmap.size() <= i) {
            return null;
        }
        return this.bitmap.get(i);
    }

    public int getImageCount() {
        return this.bitmap.size();
    }

    public double getId() {
        return id;
    }

}
