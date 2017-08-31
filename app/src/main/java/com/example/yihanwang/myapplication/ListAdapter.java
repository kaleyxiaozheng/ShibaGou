package com.example.yihanwang.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.ArrayList;

/**
 * Created by Kaley on 31/8/17.
 */

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ImageInfo> item;

    public ListAdapter(Context con, ArrayList<ImageInfo> record) {
        context = con;
        item = record;
    }



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
