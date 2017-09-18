package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.yihanwang.myapplication.gps.LocationService;

public class HomeFragment extends Fragment {
    private ImageButton locateYourself;
    private ImageButton score;
    private ImageButton plant;
    private ImageButton list;
    private ImageView ad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ad = (ImageView) getView().findViewById(R.id.logoAd);
        ad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(),
                        AppAdActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.home_fragment, container, false);

        locateYourself = (ImageButton) view.findViewById(R.id.iamhere);
        locateYourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(MapFragment.class.getName()).commit();
            }
        });

        score = (ImageButton) view.findViewById(R.id.scoreBtn);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScoreActivity.class);
                startActivity(intent);

            }
        });

        ad = (ImageView) view.findViewById(R.id.logoAd);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppAdActivity.class);
                startActivity(intent);

            }
        });

        plant = (ImageButton) view.findViewById(R.id.gamebtn);
        plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", LocationService.getInstance().getCurrentLat());
                args.putDouble("location_lon", LocationService.getInstance().getCurrentLon());
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ImageFragment.class.getName()).commit();

            }
        });

        list = (ImageButton) view.findViewById(R.id.listbtn);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ListFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", LocationService.getInstance().getCurrentLat());
                args.putDouble("location_lon", LocationService.getInstance().getCurrentLon());
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ListFragment.class.getName()).commit();

            }
        });

        return view;
    }
}
