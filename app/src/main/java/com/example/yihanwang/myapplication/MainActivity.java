package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mPlanetTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setHomeAsUpIndicator(null);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        });

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, fragment).addToBackStack(HomeFragment.class.getName()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(id == 1){
                String name = InstructionFragment.class.getName();
                Fragment tag = getSupportFragmentManager().findFragmentByTag(name);
                if(tag == null) {
                    Fragment fragment = new InstructionFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, fragment, name).addToBackStack(name).commit();
                }
            } else if(id == 2) {

            } else {
                String name = InstructionFragment.class.getName();
                Fragment tag = getSupportFragmentManager().findFragmentByTag(name);
                if(tag == null) {
                    Fragment fragment = new AboutUsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, fragment, name).addToBackStack(name).commit();
                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
