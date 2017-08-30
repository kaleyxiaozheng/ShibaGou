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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static com.example.yihanwang.myapplication.R.id.viewPager;

public class ListFragment extends Fragment {
    View view;
    ListView mListView;
    private Button button;
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

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layouts.list_fragment, container, false);
//        button = (Button)view.findViewById(R.id.plantList);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new ListFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_container, fragment).addToBackStack(ListFragment.class.getName()).commit();
//            }
//        });
//
//        return view;
//
//    }


}

