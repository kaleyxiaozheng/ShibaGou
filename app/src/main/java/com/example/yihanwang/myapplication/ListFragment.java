package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class ImageFragment extends Fragment {
    View view;
    ListView mListView;
    String[] plants = {"Gold Dust Wattle", "Hickory Wattle", "Spike Wattle", " PincushiionLily"
    };
    String[] plantInfo = {"Typically a straggly shrub reaching 2 metres in height. The ‘leaves’ (phyllodes) are variable in shape but are generally rounded oblongs or ovals (some forms may have linear phyllodes). The golden-yellow, globular flower heads are produced singly or in pairs on stalks from the phyllode bases. These are followed by distinctive spiral-shaped or twisted pods which become brown and papery at maturity.",
            "Tree occasionally reaching 12 metres in height, though often less. ‘Leaves’ (phyllodes) are long (up to 18 centimetres) and curved or sickle-shaped and have 3 or more prominent longitudinal veins. The phyllodes often hang sharply downwards. Flower heads are globular and whitish to pale yellow. Pods are long, narrow and twisted or coiled. The seeds are attached to the pods by a large fleshy structure called a funicle and remain suspended from the pods for a short period. Very prone to disfiguring insect galls.",
            "A prickly, dense shrub 1-3 m high by 2 m across, small trees are also known. Rigid, dark green phyllodes to 2 cm with sharp point. Flowers in cream spikes occur in winter and spring.",
            "Small tufted plant of the family Liliaceae endemic to western Victoria, in the Grampians National Park, where it grows in low open shrubland on sandstone outcrops."
    };
    int[] plantNums = {R.drawable.golddust_wattle,
            R.drawable.heath_wattle,
            R.drawable.spike_watte,
            R.drawable.pincushiionlily
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Listview of Plants");
        mListView = (ListView) findViewById(R.id.listview);
        Myadapter myadapter = new Myadapter(MainActivity.this, plants, plantNums);
        mListView.setAdapter(myadapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(MainActivity.this, ListDetailActivity.class);
                mIntent.putExtra("PlantName",plants[i]);
                mIntent.putExtra("Description",plantInfo[i]);
                startActivity(mIntent);
            }
        });




        view = inflater.inflate(R.layout.image_fragment, container, false);
        viewPager =  (ViewPager)view.findViewById(R.id.viewPager);
        customSwip = new CustomSwip(getActivity());
        viewPager.setAdapter(customSwip);


        picture = (Button)view.findViewById(R.id.takePhoto);
        imageView = (ImageView)view.findViewById(R.id.image);
        picture.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        location = (Button)view.findViewById(R.id.locatePoint);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(MapFragment.class.getName()).commit();
            }
        });

        return view;

    }


}

