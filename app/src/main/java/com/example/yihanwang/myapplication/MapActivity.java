package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Fragment implements OnMapReadyCallback {
    private GoogleMap m_cGoogleMap;
    private Button toImage, toHome;

    private static final LatLng LOCATION_GRAMPIANS
            = new LatLng(-37.6145,142.3244);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);
                // Get access to our MapFragment
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        // Set up an asyncronous callback to let us know when the map has loaded
        mapFrag.getMapAsync(this);

        toImage = (Button)view.findViewById(R.id.findPlant);
        toHome = (Button)view.findViewById(R.id.goBack);
        toImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent image = new Intent(MapActivity.this,ImageActivity.class);
//                startActivity(image);
                Fragment fragment = new ImageActivity();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.map_fragment, fragment).addToBackStack(null).commit();

            }
        });

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Function is called once the map has fully loaded.
        // Set our map object to reference the loaded map
        m_cGoogleMap = googleMap;
        // Move the focus of the map to be on the Grampians park. 15 is for zoom
        m_cGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_GRAMPIANS,15));

        m_cGoogleMap.addMarker(new MarkerOptions().position(LOCATION_GRAMPIANS).title("You Are Here"));
        //set map to satellite map
        m_cGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }
}


