package com.example.yihanwang.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Myadapter extends ArrayAdapter<String> {
    String[] plantNames;
    int[] plants;
    Context mContext;

    public Myadapter(Context context, String[] plantNames, int[] plants) {
        super(context, R.layout.listview_item);
        this.plantNames = plantNames;
        this.plants = plants;
        mContext = context;
    }

    @Override
    public int getCount() {
        return plantNames.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder mViewHolder = new Viewholder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            mViewHolder.mPlant = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (Viewholder) convertView.getTag();
        }
        mViewHolder.mPlant.setImageResource(plants[position]);
        mViewHolder.mName.setText(plantNames[position]);
        return convertView;
    }

    static  class Viewholder
    {
        ImageView mPlant;
        TextView mName;
    }
}
