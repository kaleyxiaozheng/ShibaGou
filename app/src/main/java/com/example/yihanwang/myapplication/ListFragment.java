package com.example.yihanwang.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.image_list, container, false);
        mListView = (ListView) view.findViewById(R.id.image_list);
//        ImageStorage.getInstance().getImageInfo()
//        new ArrayAdapter<ImageInfo>(this.getContext(), R.layout.list_fragment,);

        return view;

    }


}

