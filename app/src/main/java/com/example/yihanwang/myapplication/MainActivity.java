package com.example.yihanwang.myapplication;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, fragment).addToBackStack(HomeFragment.class.getName()).commit();

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(id == 1){

//                String name = InstructionFragment.class.getName();
//                Fragment tag = getSupportFragmentManager().findFragmentByTag(name);
//                if(tag == null) {
//                    Fragment fragment = new InstructionFragment();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frame_container, fragment, name).addToBackStack(name).commit();
//                }

            } else if(id == 2) {

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
