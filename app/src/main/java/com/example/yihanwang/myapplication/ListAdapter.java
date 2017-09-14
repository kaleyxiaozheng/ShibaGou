package com.example.yihanwang.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private final FragmentActivity activity;
    private List<ImageInfo> items = new ArrayList<>();

    public ListAdapter(FragmentActivity activity, List<ImageInfo> imagesFromLocation) {
        this.items.addAll(imagesFromLocation);
        this.activity = activity;
    }

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
        TextView textView = (TextView) viewHolder.mView.findViewById(R.id.image_name);
        textView.setText(imageInfo.getName());
        TextView basicInfo = (TextView) viewHolder.mView.findViewById(R.id.basicinfo);
        if (imageInfo.getDescription() != null) {
            basicInfo.setText(imageInfo.getDescription().substring(0, imageInfo.getDescription().indexOf('.')));
        }

        viewHolder.mView.findViewById(R.id.list_fragment_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new InfoFragment();
                Bundle args = new Bundle();
                args.putDouble("id", imageInfo.getId());
                fragment.setArguments(args);
                FragmentManager fragmentManager = ((FragmentActivity) viewHolder.mView.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(InfoFragment.class.getName()).commit();
            }
        });
        thumbView.setImageDrawable(ImageStorage.getInstance().getDrawable(activity.getAssets(), imageInfo));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public ImageInfo getItem(int position) {
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
