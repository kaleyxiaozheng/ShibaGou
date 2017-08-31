package com.example.yihanwang.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.os.Bundle;
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

public class MapFragment extends Fragment implements OnMapReadyCallback, FragmentManager.OnBackStackChangedListener{
    private GoogleMap m_cGoogleMap;
    private Button toImage;
    private Button toList;
    private View tempView;

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
        if(tempView != null){
            return tempView;
        }

        View view = inflater.inflate(R.layout.map_fragment, container, false);
        tempView = view;
                // Get access to our MapFragment
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        // Set up an asyncronous callback to let us know when the map has loaded
        mapFrag.getMapAsync(this);

        toImage = (Button)view.findViewById(R.id.findPlant);
        toImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", LOCATION_GRAMPIANS.latitude);
                args.putDouble("location_lon", LOCATION_GRAMPIANS.longitude);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ImageFragment.class.getName()).commit();
            }
        });

        toList = (Button)view.findViewById(R.id.plantList);
        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ListFragment.class.getName()).commit();
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


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        m_cGoogleMap.setMyLocationEnabled(true);

    }


    @Override
    public void onBackStackChanged() {

    }
}


