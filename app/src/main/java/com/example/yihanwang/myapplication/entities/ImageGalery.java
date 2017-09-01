package com.example.yihanwang.myapplication.entities;

import android.graphics.Bitmap;

/**
 * Created by Kaley on 1/9/17.
 */

public class ImageGalery {

    private long id;

    private Bitmap bitmap;

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public ImageGalery(long id, Bitmap bitmap, String filePath) {
        this.id = id;
        this.bitmap = bitmap;
        this.filePath = filePath;

    }

    public long getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
