package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.gps.LocationService;

/**
 * Created by Kaley on 28/9/17.
 */

public class OneListActivity extends AppCompatActivity {

    private ImageView plant;
    private TextView name;
    private TextView info;
    private ImageView house;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle args = getIntent().getExtras();
        double id = args.getDouble("image_info_id");

        Typeface font = Typeface.createFromAsset(getAssets(), "teen.ttf");
        final ImageInfo i = ImageStorage.getInstance().getImageInfoById(id);
        plant = (ImageView) findViewById(R.id.PlantPhoto);
        plant.setImageDrawable(ImageStorage.getInstance().getDrawable(getAssets(), i));

        name = (TextView) findViewById(R.id.PlantRecordTitle);
        String commonName = i.getCommonName();
        String sciencename = i.getName();

        if (!commonName.isEmpty()) {
            name.setText(commonName + " (" + sciencename + ")");
        } else {
            name.setText(sciencename);
        }

        name.setTypeface(font);

        info = (TextView) findViewById(R.id.PlantRecord);
        info.setMovementMethod(new ScrollingMovementMethod());
        if (i != null && i.getDescription() != null) {
            String[] words = i.getDescription().split("==");
            StringBuilder sb = new StringBuilder();
            sb.append(words[0]);
            info.setText(sb);
        }

        house = (ImageView) findViewById(R.id.nextPlant);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Fragment fragment = new ImageSelectFragment();
//                Bundle args = new Bundle();
//                args.putDouble("image_info_id", i.getId());
//                fragment.setArguments(args);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_container, fragment).addToBackStack(ImageSelectFragment.class.getName()).commit();
            }
        });

    }

}
