package com.example.yihanwang.myapplication;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;

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

    public void addImage(ImageInfo imageInfo){
        this.images.add(imageInfo);
    }
}
