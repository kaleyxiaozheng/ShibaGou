package com.example.yihanwang.myapplication;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageStorage {

    private static ImageStorage instance = new ImageStorage();

    private static final int DISTANCE = 1000; // 1km

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
        List<ImageInfo> rangeImages = new ArrayList<>();
        for (ImageInfo imageInfo : images) {
            String locations = imageInfo.getLocations();
            try {
                JSONArray jsonObject = new JSONArray(locations);
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject o = (JSONObject) jsonObject.get(i);
                    double lat1 = o.getDouble("lat");
                    double lon1 = o.getDouble("lon");
                    Location l1 = new Location("l1");
                    l1.setLatitude(lat);
                    l1.setLongitude(lon);
                    Location l2 = new Location("l2");
                    l2.setLatitude(lat1);
                    l2.setLongitude(lon1);
                    if (Math.abs(l1.distanceTo(l2)) <= 100 * DISTANCE) {
                        rangeImages.add(imageInfo);
                        break;
                    }
//                    Log.i("distance", "from " + lat + "," + lon + " to " + lat1 + "," + lon1 + " = " + l1.distanceTo(l2) + "");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return rangeImages;
    }

    public List<ImageInfo> getAllImages() {
        return images;
    }
}
