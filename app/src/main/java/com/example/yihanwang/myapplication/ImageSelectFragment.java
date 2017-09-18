package com.example.yihanwang.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.gps.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageSelectFragment extends Fragment {

    private int selectedImageIdx = -1;

    private List<ImageInfo> images = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private Button playBtn;
    private Button skipBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_image, container, false);
        Bundle args = getArguments();
        final double lat = args.getDouble("location_lat");
        final double lon = args.getDouble("location_lon");

        this.images.clear();
        this.imageViews.clear();

        TextView textView = (TextView) view.findViewById(R.id.select_text_view);

        final List<ImageInfo> imageList = ImageStorage.getInstance().getImagesFromLocation(lat, lon);
        textView.setText("Image " + imageList.size());
        final ImageView imageView1 = (ImageView) view.findViewById(R.id.select_image_view1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.select_image_view2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.select_image_view3);
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        if (imageList.size() > 2) {
            this.images.add(imageList.get(0));
            this.images.add(imageList.get(1));
            this.images.add(imageList.get(2));
            for (int i = 0; i < this.images.size(); i++) {
                imageViews.get(i).setImageDrawable(ImageStorage.getInstance().getDrawable(getActivity().getAssets(), images.get(i)));
                imageViews.get(i).setOnClickListener(new ImageClickListener(imageViews.get(i), getContext(), i));
            }
        }
        this.playBtn = (Button) view.findViewById(R.id.play_button);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", lat);
                args.putDouble("location_lon", lon);
                ImageInfo imageInfo = images.get(selectedImageIdx);
                selectedImageIdx = -1;
                args.putDouble("selected_image_id", imageInfo.getId());
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ImageFragment.class.getName()).commit();

            }
        });
        this.skipBtn = (Button) view.findViewById(R.id.skip_button);
        this.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                images.remove(selectedImageIdx);
                skipBtn.setEnabled(false);
                playBtn.setEnabled(false);
                boolean found = false;
                int i = -1;
                do {
                    i = r.nextInt(imageList.size() - 1);
                    Log.i("random", "get number "+ i);
                    for (ImageInfo image : images) {
                        if (image.getId() == imageList.get(i).getId()) {
                            found = true;
                        }
                    }
                }while(found);
                images.add(selectedImageIdx, imageList.get(i));
                imageViews.get(selectedImageIdx).setImageDrawable(ImageStorage.getInstance().getDrawable(getActivity().getAssets(), images.get(selectedImageIdx)));

                selectedImageIdx = -1;
                for(ImageView v : imageViews){
                    v.setAlpha(1f);
                }
            }
        });
        return view;
    }

    class ImageClickListener implements View.OnClickListener {
        private int index = -1;
        private ImageView imageView;
        private Context context;

        public ImageClickListener(ImageView imageView, Context context, int index) {
            this.imageView = imageView;
            this.context = context;
            this.index = index;
        }

        @Override
        public void onClick(View view) {
            playBtn.setEnabled(true);
            skipBtn.setEnabled(true);
            for (ImageView image : imageViews) {
                image.setAlpha(1f);
            }
            if (selectedImageIdx != index) {
                this.imageView.setAlpha(0.5f);
                selectedImageIdx = index;
            }
        }
    }
}
