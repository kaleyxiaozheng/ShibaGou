package com.example.yihanwang.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ImageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment, container, false);

        ImageButton imageBtn1 = (ImageButton)view.findViewById(R.id.imageBtn1);
        imageBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("Plant","Plant 1");

                Fragment fragment = new ImageInformationFragment();
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        });

        ImageButton imageBtn2 = (ImageButton)view.findViewById(R.id.imageBtn2);
        imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("Plant","Plant 2");

                Fragment fragment = new ImageInformationFragment();
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        });

        ImageButton imageBtn3 = (ImageButton)view.findViewById(R.id.imageBtn3);
        imageBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("Plant","Plant 3");

                Fragment fragment = new ImageInformationFragment();
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        });

        ImageButton imageBtn4 = (ImageButton)view.findViewById(R.id.imageBtn4);
        imageBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("Plant","Plant 4");

                Fragment fragment = new ImageInformationFragment();
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        });

        return view;

    }
}

