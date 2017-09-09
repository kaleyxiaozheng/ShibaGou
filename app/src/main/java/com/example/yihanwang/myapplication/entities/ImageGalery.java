package com.example.yihanwang.myapplication.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ImageGalery {

    private long id;

    private List<Bitmap> bitmap = new ArrayList<>();

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public ImageGalery(long id, String filePath) {
        this.id = id;
        this.filePath = filePath;

    }

    public void addImage(Bitmap bitmap) {
        if (this.bitmap.size() >= 3) {
            this.removeImage(0);
        }
        this.bitmap.add(bitmap);
    }

    public void removeImage(int idx) {
        if (this.bitmap.size() > idx) {
            this.bitmap.remove(idx);
        }
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

    public long getId() {
        return id;
    }

}
