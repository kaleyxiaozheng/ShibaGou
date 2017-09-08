package com.example.yihanwang.myapplication;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Kaley on 30/8/17.
 */

public class ImageStorage {

    private static ImageStorage instance = new ImageStorage();
    private List<ImageInfo> images = new ArrayList<>();

    private ImageStorage() {
    }

    public static ImageStorage getInstance() {
        return instance;
    }

    public void clearImage() {
        this.images.clear();
    }

    public void addImage(ImageInfo imageInfo) {
        this.images.add(imageInfo);
    }

    public int getImageCount() {
        return images.size();
    }

    public ImageInfo getImageInfo(int position) {
        if (position < this.images.size()) {
            return this.images.get(position);
        }
        return null;
    }

    public ImageInfo getImageInfoById(long id) {
        for (ImageInfo imageInfo : images) {
            if (imageInfo.getId() == id) {
                return imageInfo;
            }
        }
        return null;
    }

}
