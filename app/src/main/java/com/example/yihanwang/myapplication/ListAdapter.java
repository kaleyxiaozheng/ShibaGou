package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private List<ImageInfo> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ImageInfo imageInfo = this.getItem(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        final ImageView thumbView = (ImageView) viewHolder.mView.findViewById(R.id.thumbnail);
        final String thumbUrl = imageInfo.getImages().get(0).getThumbUrl();
        TextView textView = (TextView) viewHolder.mView.findViewById(R.id.image_name);
        textView.setText(imageInfo.getName());
        TextView basicInfo = (TextView) viewHolder.mView.findViewById(R.id.basicinfo);
        basicInfo.setText(imageInfo.getDescription().substring(0, imageInfo.getDescription().indexOf('.')));

        viewHolder.mView.findViewById(R.id.list_fragment_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new InfoFragment();
                Bundle args = new Bundle();
                args.putLong("id", imageInfo.getId());
                fragment.setArguments(args);
                FragmentManager fragmentManager = ((FragmentActivity)viewHolder.mView.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(InfoFragment.class.getName()).commit();
            }
        });
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
        return items.size();
    }

    public void addItem(ImageInfo imageInfo){
        this.items.add(imageInfo);
        this.notifyItemInserted(this.items.size() - 1);
    }

    public ImageInfo getItem(int position){
        return this.items.get(position);
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
