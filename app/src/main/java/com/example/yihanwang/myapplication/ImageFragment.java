package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class ImageFragment extends Fragment {
    private ViewPager viewPager;
    private Button picture;
    private Button location;
    private Button infoBtn;
    private RequestQueue queue;
    private int currentPosition;
    private ImageLoader imageLoader = new ImageLoader();

    CustomSwip customSwip;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this.getContext());
        view = inflater.inflate(R.layout.image_fragment, container, false);
        Bundle args = getArguments();
        double lat = args.getDouble("location_lat");
        double lon = args.getDouble("location_lon");
        ImageStorage.getInstance().clearImage();
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                Log.i("http", "request has finished");
                customSwip.notifyDataSetChanged();
            }
        });
        imageLoader.getPlantImagesInfo(lat, lon, queue);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        customSwip = new CustomSwip(getActivity());
        viewPager.setAdapter(customSwip);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
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

        location = (Button) view.findViewById(R.id.locatePoint);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(MapFragment.class.getName()).commit();
            }
        });

        infoBtn = (Button) view.findViewById(R.id.plantInfo);
        infoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new InfoFragment();
                Bundle args = new Bundle();
                ImageInfo imageInfo = ImageStorage.getInstance().getImageInfo(currentPosition);
                args.putLong("id", imageInfo.getId());
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(InfoFragment.class.getName()).commit();
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
            ImageInfo imageInfo = ImageStorage.getInstance().getImageInfo(currentPosition);
            ImageGaleryStorage.getInstance().addImageGalery(imageInfo.getId(), finalBitmap, file.getPath());
            viewPager.setAdapter(null);
            viewPager.setAdapter(customSwip);
            viewPager.setCurrentItem(currentPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

