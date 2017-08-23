package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ImageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_image, container, false);
//        ImageButton imageBtn1 = (ImageButton)view.findViewById(R.id.imageBtn1);
//        imageBtn1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
////                Intent showInf = new Intent(ImageFragment.this, ImageInformationActivity.class);
////                showInf.putExtra("PLANT1","Plant:Gold Dust Wattle. Description:Straggling shrub to 2 m high and 1.5 m diameter. Phyllodes are variable, usually less than 2 cm long; narrow to orbicular. Flowers like golden balls appear in spring.");
////                startActivity(showInf);
//            }
//        });
//
//        ImageButton imageBtn2 = (ImageButton)view.findViewById(R.id.imageBtn2);
//        imageBtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent showInf = new Intent(getApplicationContext() , ImageInformationActivity.class);
////                showInf.putExtra("PLANT2","Plant:Hickory Wattle. Description: Small to medium-sized tree to 12 m high with light green, sickle-shaped phyllodes to 20 cm. Bears cream ball-shaped flowers in summer.");
////                startActivity(showInf);
//            }
//        });
//
//        ImageButton imageBtn3 = (ImageButton)view.findViewById(R.id.imageBtn3);
//        imageBtn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent showInf = new Intent(ImageFragment.this, ImageInformationActivity.class);
////                showInf.putExtra("PLANT3","Plant:Spike Wattle. Description: A prickly, dense shrub 1-3 m high by 2 m across, small trees are also known. Rigid, dark green phyllodes to 2 cm with sharp point. Flowers in cream spikes occur in winter and spring.");
////                startActivity(showInf);
//            }
//        });
//
//        ImageButton imageBtn4 = (ImageButton)view.findViewById(R.id.imageBtn4);
//        imageBtn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent showInf = new Intent(ImageFragment.this, ImageInformationActivity.class);
////                showInf.putExtra("PLANT4","Plant:Pincushion Lily.Description:The Pincushion Lily produces clumps of brown, branched stems up to 15cm tall, with tufts of spiky leaves, each about 1.5cm long. During springtime, the plant produces attractive round heads of star-shaped white flowers on a stem at the ends of the branches.");
////                startActivity(showInf);
//            }
//        });

        return view;

    }
}

