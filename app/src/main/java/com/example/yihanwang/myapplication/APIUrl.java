package com.example.yihanwang.myapplication;

/**
 * Created by Kaley on 30/8/17.
 */

public class APIUrl {

    public static String getPlantsList(double lat, double lon, int pageSize, int radius) {
        return "https://biocache.ala.org.au/ws/explore/group/Plants.json?lat=" + lat + "&lon=" + lon + "&radius=" + radius + "&pageSize=" + pageSize;
    }

    public static String getImageSearch(String guid) {
        return "http://bie.ala.org.au/ws/imageSearch/" + guid;
    }

    public static String getPlantInfo(String title) {
        return "https://en.wikipedia.org/w/api.php?action=query&format=json&titles=" + title + "&explaintext=&prop=extracts";
    }
}
