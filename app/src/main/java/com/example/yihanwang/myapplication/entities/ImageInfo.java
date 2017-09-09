package com.example.yihanwang.myapplication.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class ImageInfo extends RealmObject {

    private long id;

    private String name;

    private String commonName;

    private String guid;

    private String kingdom;

    private String family;

    private int count;

    private String rank;

    private String description;

    private double latitude;

    private double longtitude;

    @Ignore
    private List<Image> images = new ArrayList<>();

    public ImageInfo() {
    }


    public void setJsonValue(JSONObject jsonObject){
        try {
            this.id = System.nanoTime();
            this.commonName = jsonObject.getString("commonName");
            this.name = jsonObject.getString("name");
            this.family = jsonObject.getString("family");
            this.count = jsonObject.getInt("count");
            this.rank = jsonObject.getString("rank");
            this.kingdom = jsonObject.getString("kingdom");
            this.guid = jsonObject.getString("guid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public String getName() {
        return name;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getGuid() {
        return guid;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getFamily() {
        return family;
    }

    public int getCount() {
        return count;
    }

    public String getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return new ArrayList<>(images);
    }


    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public static class Image {
        private String id;
        private String imageUrl;
        private String thumbUrl;

        public Image(JSONObject jsonObject) {
            try {
                id = jsonObject.getString("image");
                imageUrl = jsonObject.getString("imageUrl");
                thumbUrl = jsonObject.getString("thumbnailUrl");
            } catch (JSONException e) {
                e.printStackTrace();
                ;
            }
        }

        public String getId() {
            return id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }
    }
}
