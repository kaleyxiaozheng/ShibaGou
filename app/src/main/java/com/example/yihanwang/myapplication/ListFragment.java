package com.example.yihanwang.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.yihanwang.myapplication.entities.ImageInfo;

public class ListFragment extends Fragment {
    private View view;
    private RequestQueue queue;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this.getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        View view = inflater.inflate(R.layout.image_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.image_list);
        recyclerView.setLayoutManager(layoutManager);
        Bundle args = getArguments();
        double lat = args.getDouble("location_lat");
        double lon = args.getDouble("location_lon");

        final ListAdapter listAdapter = new ListAdapter();
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                Log.i("http", "refresh list view");
                listAdapter.notifyDataSetChanged();
            }
        });
        ImageLoader.getPlantImagesInfo(lat, lon, queue);

        recyclerView.setAdapter(listAdapter);

        return view;

    }


}

