package com.example.yihanwang.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    private Button locateYourself;
    private Button chooseLocation;
    private Typeface tf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        locateYourself = (Button) view.findViewById(R.id.findPlant);
        //chooseLocation = (Button) view.findViewById(R.id.dragLocation);

        locateYourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(MapFragment.class.getName()).commit();
            }
        });

        tf = Typeface.createFromAsset(getActivity().getAssets(), "Aclonica.ttf");

        locateYourself.setTypeface(tf);
        //hooseLocation.setTypeface(tf);

        return view;
    }

}
