package com.example.yihanwang.myapplication;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kaley on 31/8/17.
 */

public class ImageLoader {

    public static void getPlantImagesInfo(double lat, double lon, final RequestQueue queue) {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, APIUrl.getPlantsList(lat, lon, 50, 1), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("http", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ImageInfo imageInfo = new ImageInfo(jsonObject);
                        getImageUrl(imageInfo, queue);
                    }
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("http", error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
//        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
//            @Override
//            public void onRequestFinished(Request<Object> request) {
//                Log.i("http", "request has finished");
//                customSwip.notifyDataSetChanged();
//            }
//        });
    }

    private static void getImageUrl(final ImageInfo imageInfo, RequestQueue queue) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl.getImageSearch(imageInfo.getGuid()), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONObject("searchResults").getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject jsonObject = results.getJSONObject(i);
                        ImageInfo.Image image = new ImageInfo.Image(jsonObject);
                        imageInfo.addImage(image);
                    }
                    if (imageInfo.getImages().size() > 0
                            && imageInfo.getImages().get(0).getThumbUrl() != null
                            && imageInfo.getImages().get(0).getImageUrl() != null) {
                        ImageStorage.getInstance().addImage(imageInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("http", error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

}
