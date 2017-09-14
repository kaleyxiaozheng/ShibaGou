package com.example.yihanwang.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;

public class InfoFragment extends Fragment {

    private TextView item;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        double position = args.getDouble("id");
        ImageInfo imageInfo = ImageStorage.getInstance().getImageInfoById(position);

        View view = inflater.inflate(R.layout.info_swipe, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.PlantPhoto);
        item = (TextView) view.findViewById(R.id.PlantRecord);
        item.setMovementMethod(new ScrollingMovementMethod());
        if(imageInfo != null  && imageInfo.getDescription() != null) {
            item.setText(imageInfo.getDescription());
        }
        if(imageInfo != null) {
            TextView title = (TextView) view.findViewById(R.id.PlantRecordTitle);
            title.setText(imageInfo.getName());
            imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(getActivity().getAssets(), imageInfo));
        }
        return view;
    }

}
