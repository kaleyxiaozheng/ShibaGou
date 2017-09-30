package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;
import com.example.yihanwang.myapplication.entities.TextProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImageFragment extends Fragment {
    public static int id = 1;
    private ViewPager viewPager;
    private int currentPosition;
    ImagePagerAdapter customSwip;

    View view;
    private List<ImageInfo> images = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_fragment, container, false);

        Bundle args = getArguments();
        double lat = args.getDouble("location_lat");
        double lon = args.getDouble("location_lon");

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        this.images = ImageStorage.getInstance().getImagesFromLocation(lat, lon);
        customSwip = new ImagePagerAdapter(getActivity(), images);
        viewPager.setAdapter(customSwip);
        double imageId = args.getDouble("selected_image_id");
        if (imageId != 0.0) {
            for(int i=0; i<this.images.size(); i++){
                if(this.images.get(i).getId() == imageId){
                    currentPosition = i;
                    Log.i("image", "show selected image " + imageId);
                    viewPager.setCurrentItem(currentPosition);
                    //customSwip = new ImagePagerAdapter(getActivity(), images);
                    break;
                }
            }
        }

        //viewPager.setAdapter(customSwip);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

        });
        return view;
    }
}

