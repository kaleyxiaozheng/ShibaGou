package com.example.yihanwang.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.gps.LocationService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkPermission();

    }

    private void alertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.maindialog, null);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "teen_bd_it.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "teen.ttf");
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);
        title.setTypeface(font1);
        message.setTypeface(font2);
        title.setText("Flora Friend");

        List<ImageInfo> images = ImageStorage.getInstance().getImagesFromLocation(LocationService.getInstance().getCurrentLat(), LocationService.getInstance().getCurrentLon());
        message.setText("There are " + images.size() + " plants in your area, press play to begin");

        builder.setPositiveButton("Play", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fragment fragment = new ImageSelectFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", LocationService.getInstance().getCurrentLat());
                args.putDouble("location_lon", LocationService.getInstance().getCurrentLon());
                fragment.setArguments(args);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ImageFragment.class.getName()).commit();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setView(view);
        builder.show();

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, fragment).commit();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1000);
        } else {
            LocationService.getInstance(this).locateCurrentLocation();
            alertMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            LocationService.getInstance(this).locateCurrentLocation();
            alertMessage();
        }
    }
}