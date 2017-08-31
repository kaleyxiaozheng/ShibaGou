package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Kaley on 31/8/17.
 */

public class ListAdapter extends RecyclerView.Adapter {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageInfo imageInfo = ImageStorage.getInstance().getImageInfo(position);
        final ImageView thumbView = (ImageView) ((ViewHolder)holder).mView.findViewById(R.id.thumbnail);
        final String thumbUrl = imageInfo.getImages().get(0).getThumbUrl();

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    InputStream inputStream = new URL(thumbUrl).openStream();
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
                    thumbView.setImageBitmap(bitmap);
                } else {
                    Log.e("image", "can't read image from " + thumbUrl);
                }
            }
        }.execute();

    }

    @Override
    public int getItemCount() {
        return ImageStorage.getInstance().getImageCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }
}
