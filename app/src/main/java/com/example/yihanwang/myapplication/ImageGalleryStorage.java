package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.yihanwang.myapplication.entities.ImageGallery;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryStorage {

    private static final ImageGalleryStorage instance = new ImageGalleryStorage();

    private List<ImageGallery> items = new ArrayList<>();

    public static ImageGalleryStorage getInstance() {
        return instance;
    }

    public void addImageGallery(double id, Bitmap bitmap, String path) {
        Log.i("gallery", "save image gallery " + id);
        ImageGallery gallery = getImageGallery(id);
        if (gallery != null) {
            gallery.addImage(bitmap);
        } else {
            ImageGallery imageGallery = new ImageGallery(id, path);
            imageGallery.addImage(bitmap);
            this.items.add(imageGallery);
        }
    }

    public void removeImageGallery(long id) {
        ImageGallery gallery = getImageGallery(id);

        if (gallery != null) {
            this.items.remove(gallery);
        }
    }

    public ImageGallery getImageGallery(double id) {
        for (ImageGallery imageGallery : items) {
            if (imageGallery.getId() == id) {
                return imageGallery;
            }
        }
        return null;
    }
}
