package com.example.yihanwang.myapplication.entities;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;

public class ImageInfo extends RealmObject {

    private double id;

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

    private String imageUrl;

    private String thumbnailUrl;

    private String Locations;

    public ImageInfo() {
    }


    public void setJsonValue(JSONObject jsonObject) {
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

    public double getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getLocations() {
        return Locations;
    }

}
