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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InfoFragment extends Fragment {
    private TextView item;
    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this.getContext());

        Bundle args = getArguments();
        long position = args.getLong("id");
        ImageInfo imageInfo = ImageStorage.getInstance().getImageInfoById(position);

        View view = inflater.inflate(R.layout.info_plant_fragment, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.PlantPhoto);
        final String url = imageInfo.getImages().get(0).getImageUrl();
        item = (TextView) view.findViewById(R.id.PlantRecord);
        item.setMovementMethod(new ScrollingMovementMethod());
        item.setText(imageInfo.getDescription());
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


        return view;
    }

}
