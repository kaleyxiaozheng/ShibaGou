package com.example.yihanwang.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by joey on 22/8/17.
 */

public class HomeFragment extends Fragment {
    private Button button;
    private TextView topText;
    private TextView subText;
    private Typeface tf1;
    private Typeface tf2;
    private Typeface tf3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        button = (Button) view.findViewById(R.id.mapButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(MapFragment.class.getName()).commit();
            }
        });

        topText = (TextView) view.findViewById(R.id.topTextView);
        subText = (TextView) view.findViewById(R.id.subTextView);
        tf1 = Typeface.createFromAsset(getActivity().getAssets(), "Bauhaus-93.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getAssets(), "Abadi_MT Condensed_Light.ttf");
        tf3 = Typeface.createFromAsset(getActivity().getAssets(), "Aclonica.ttf");
        topText.setTypeface(tf1);
        subText.setTypeface(tf2);
        button.setTypeface(tf3);

        return view;
    }

}
