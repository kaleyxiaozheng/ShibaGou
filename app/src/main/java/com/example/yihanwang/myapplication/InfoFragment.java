package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.util.List;

public class InfoFragment extends Fragment {

    private ImageView house;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_fragment, container, false);
        Bundle args = getArguments();
        double id = args.getDouble("id");
        int idx = ImageStorage.getInstance().getIndexByImageId(id);
        InfoPagerAdapter adapter = new InfoPagerAdapter(getContext(), ImageStorage.getInstance().getAllImages());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.infoViewPager);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(idx);

//        house = (ImageView)  view.findViewById(R.id.house);
//        house.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
