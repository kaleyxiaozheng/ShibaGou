package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class InfoFragment extends Fragment {
    private TextView item;
    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this.getContext());

        Bundle args = getArguments();
        int position = args.getInt("current_position");
        ImageInfo imageInfo = ImageStorage.getInstance().getImageInfo(position);

        View view = inflater.inflate(R.layout.info_plant_fragment, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.PlantPhoto);
        final String url = imageInfo.getImages().get(0).getThumbUrl();
        this.queryImageInfo(imageInfo);
        Log.i("image", "show image url " + url);
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    InputStream inputStream = new URL(url).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    //Log.e("image", "can't read image from " + url);
                }
            }
        }.execute();

        item = (TextView) view.findViewById(R.id.PlantRecord);
        item.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    private void queryImageInfo(ImageInfo imageInfo) {
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
                                item.setText(info);
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
        request.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
