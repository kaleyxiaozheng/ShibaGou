package com.example.yihanwang.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yihanwang.myapplication.gps.LocationService;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkPermission();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, fragment).addToBackStack(HomeFragment.class.getName()).commit();

    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1000);
        } else {
            LocationService.getInstance(this).locateCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            LocationService.getInstance(this).locateCurrentLocation();
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (id == 1) {

//                String name = InstructionFragment.class.getName();
//                Fragment tag = getSupportFragmentManager().findFragmentByTag(name);
//                if(tag == null) {
//                    Fragment fragment = new InstructionFragment();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame_container, fragment, name).addToBackStack(name).commit();
//                }

            } else if (id == 2) {

//                String name = GalleryFragment.class.getName();
//                Fragment tag = getSupportFragmentManager().findFragmentByTag(name);
//                if(tag == null) {
//                    Fragment fragment = new GalleryFragment();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame_container, fragment, name).addToBackStack(name).commit();
//                }

            } else {

//                String name = InstructionFragment.class.getName();
//                Fragment tag = getSupportFragmentManager().findFragmentByTag(name);
//                if(tag == null) {
//                    Fragment fragment = new AboutUsFragment();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame_container, fragment, name).addToBackStack(name).commit();
//                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
