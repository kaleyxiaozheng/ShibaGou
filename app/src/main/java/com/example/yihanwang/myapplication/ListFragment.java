package com.example.yihanwang.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ListFragment extends Fragment {
    private RequestQueue queue;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;

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

        listAdapter = new ListAdapter(getActivity(), ImageStorage.getInstance().getImagesFromLocation(lat, lon));

        recyclerView.setAdapter(listAdapter);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

