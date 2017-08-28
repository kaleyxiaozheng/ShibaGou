package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ImageFragment extends Fragment {
    private ViewPager viewPager;
    private Button picture;
    private ImageView imageView;
    CustomSwip customSwip;
    View view;
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager)viewPager.findViewById(R.id.viewPager);
        customSwip = new CustomSwip(getActivity());
        viewPager.setAdapter(customSwip);
    }
    */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.image_fragment, container, false);
        viewPager =  (ViewPager)view.findViewById(R.id.viewPager);
        customSwip = new CustomSwip(getActivity());
        viewPager.setAdapter(customSwip);

        //viewPager = inflater.inflate(R.layout.viewPager, container, false);
//        ImageButton imageBtn1 = (ImageButton)view.findViewById(R.id.imageBtn1);
//        imageBtn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Bundle args = new Bundle();
//                args.putString("Plant","Plant 1");
//
//                Fragment fragment = new ImageInformationFragment();
//                fragment.setArguments(args);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
//            }
//        });
//
//        ImageButton imageBtn2 = (ImageButton)view.findViewById(R.id.imageBtn2);
//        imageBtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putString("Plant","Plant 2");
//
//                Fragment fragment = new ImageInformationFragment();
//                fragment.setArguments(args);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
//            }
//        });
//
//        ImageButton imageBtn3 = (ImageButton)view.findViewById(R.id.imageBtn3);
//        imageBtn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putString("Plant","Plant 3");
//
//                Fragment fragment = new ImageInformationFragment();
//                fragment.setArguments(args);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
//            }
//        });
//
//        ImageButton imageBtn4 = (ImageButton)view.findViewById(R.id.imageBtn4);
//        imageBtn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putString("Plant","Plant 4");
//
//                Fragment fragment = new ImageInformationFragment();
//                fragment.setArguments(args);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
//            }
//        });




        picture = (Button)view.findViewById(R.id.takePhoto);
        imageView = (ImageView)view.findViewById(R.id.image);
        picture.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        return view;

    }

    @Override
        public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

    }

}

