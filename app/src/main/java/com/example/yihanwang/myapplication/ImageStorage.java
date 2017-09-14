package com.example.yihanwang.myapplication;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageStorage {

    private static ImageStorage instance = new ImageStorage();

    private List<ImageInfo> images = new ArrayList<>();

    private ImageStorage() {
    }

    public static ImageStorage getInstance() {
        return instance;
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

    public ImageInfo getImageInfoById(double id) {
        for (ImageInfo imageInfo : images) {
            if (imageInfo.getId() == id) {
                return imageInfo;
            }
        }
        return null;
    }

    @Nullable
    public Drawable getDrawable(AssetManager assets, ImageInfo imageInfo) {
        try {
            InputStream open = assets.open("images/" + imageInfo.getName());
            Drawable drawable = Drawable.createFromStream(open, null);
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ImageInfo> getImagesFromLocation(double lat, double lon) {
        return new ArrayList<>(images);
    }
}
