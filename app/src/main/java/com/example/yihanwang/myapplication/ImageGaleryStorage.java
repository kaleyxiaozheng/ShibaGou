package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.yihanwang.myapplication.entities.ImageGalery;

import java.util.ArrayList;
import java.util.List;

public class ImageGaleryStorage {

    private static final ImageGaleryStorage instance = new ImageGaleryStorage();

    private List<ImageGalery> items = new ArrayList<>();

    public static ImageGaleryStorage getInstance() {
        return instance;
    }

    public void addImageGalery(double id, Bitmap bitmap, String path) {
        Log.i("galery", "save image galery " + id);
        ImageGalery galery = getImageGalery(id);
        if (galery != null) {
            galery.addImage(bitmap);
        } else {
            ImageGalery imageGalery = new ImageGalery(id, path);
            imageGalery.addImage(bitmap);
            this.items.add(imageGalery);
        }
    }

    public void removeImageGalery(long id) {
        ImageGalery galery = getImageGalery(id);

        if (galery != null) {
            this.items.remove(galery);
        }
    }

    public ImageGalery getImageGalery(double id) {
        for (ImageGalery imageGalery : items) {
            if (imageGalery.getId() == id) {
                return imageGalery;
            }
        }
        return null;
    }
}
