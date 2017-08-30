package com.example.yihanwang.myapplication.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaley on 30/8/17.
 */

public class ImageInfo {

    private String name;

    private String commonName;

    private String guid;

    private String kingdom;

    private String family;

    private int count;

    private String rank;

    private List<Image> images = new ArrayList<>();

    public ImageInfo(JSONObject jsonObject) {
        try {
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
