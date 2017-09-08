package com.example.yihanwang.myapplication;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;

public class ImageLoader {

    private final List<ImageAvailableListener> listeners = new ArrayList<>();

    public final void addListener(ImageAvailableListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ImageAvailableListener l) {
        listeners.remove(l);
    }

    public void getPlantImagesInfo(final double lat, final double lon, final RequestQueue queue) {
        final Realm realm = Realm.getDefaultInstance();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, APIUrl.getPlantsList(lat, lon, 50, 1), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("http", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        final JSONObject jsonObject = response.getJSONObject(i);
                        ImageInfo imageInfo;
                        realm.beginTransaction();
                        imageInfo = realm.createObject(ImageInfo.class);
                        imageInfo.setJsonValue(jsonObject);
                        imageInfo.setLatitude(lat);
                        imageInfo.setLongtitude(lon);
                        realm.commitTransaction();
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
    }

    private void getImageUrl(final ImageInfo imageInfo, final RequestQueue queue) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl.getImageSearch(imageInfo.getGuid()), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONObject("searchResults").getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject jsonObject = results.getJSONObject(i);
                        final ImageInfo.Image image = new ImageInfo.Image(jsonObject);
                        imageInfo.addImage(image);

                    }
                    if (imageInfo.getImages().size() > 0
                            && imageInfo.getImages().get(0).getThumbUrl() != null
                            && imageInfo.getImages().get(0).getImageUrl() != null) {
                        queryImageInfo(imageInfo, queue);
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

    private void queryImageInfo(final ImageInfo imageInfo, RequestQueue queue) {
        String url = null;
        try {
            url = APIUrl.getPlantInfo(URLEncoder.encode(imageInfo.getName(), "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url == null) {
            Log.e("http", "image info url is null");
            return;
        }
        Log.i("http", "load image info " + url);
        final Realm realm = Realm.getDefaultInstance();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject query = response.getJSONObject("query");
                            JSONObject pages = query.getJSONObject("pages");
                            Iterator<String> keys = pages.keys();
                            if (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject keyObjs = pages.getJSONObject(key);
                                String info = keyObjs.getString("extract");
                                realm.beginTransaction();
                                imageInfo.setDescription(info);
                                realm.commitTransaction();
                            }
                            if (imageInfo.getDescription() != null && !imageInfo.getDescription().isEmpty()) {
                                ImageStorage.getInstance().addImage(imageInfo);
                                notifyListeners(imageInfo);
                            }
                        } catch (JSONException e) {
                            Log.w("http", "no extract value for the query");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("http", error.getMessage());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    private void notifyListeners(ImageInfo imageInfo) {
        for (ImageAvailableListener l : listeners) {
            l.imageAvailable(imageInfo);
        }
    }

    interface ImageAvailableListener {
        void imageAvailable(ImageInfo imageInfo);
    }
}
