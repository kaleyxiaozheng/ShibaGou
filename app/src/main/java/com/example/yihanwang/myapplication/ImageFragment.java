package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageFragment extends Fragment {
    private ViewPager viewPager;
    private Button picture;
    private Button infoBtn;
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
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

        });
        picture = (Button) view.findViewById(R.id.takePhoto);
        picture.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        infoBtn = (Button) view.findViewById(R.id.plantInfo);
        infoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new InfoFragment();
                Bundle args = new Bundle();
                ImageInfo imageInfo = images.get(currentPosition);

                if (imageInfo != null) {
                    args.putDouble("id", imageInfo.getId());
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).addToBackStack(InfoFragment.class.getName()).commit();
                }
            }
        });


        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.getExtras() != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            SaveImage(bitmap);
        }
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = getContext().getExternalCacheDir().getAbsolutePath();
        File myDir = new File(root, "saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            ImageInfo imageInfo = images.get(currentPosition);
            ImageGaleryStorage.getInstance().addImageGalery(imageInfo.getId(), finalBitmap, file.getPath());
            viewPager.setAdapter(null);
            viewPager.setAdapter(customSwip);
            viewPager.setCurrentItem(currentPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

