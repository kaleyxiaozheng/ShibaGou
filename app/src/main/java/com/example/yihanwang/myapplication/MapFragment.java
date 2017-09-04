package com.example.yihanwang.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yihanwang.myapplication.entities.LocationTracker;
import com.example.yihanwang.myapplication.gps.LocationService;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.LocationListener;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment implements OnMapReadyCallback, FragmentManager.OnBackStackChangedListener {
    private GoogleMap m_cGoogleMap;
    private Location location;
    private Double mLatitude, mLongitude;
    private LocationTracker locationTracker;
    private Button toImage;
    private Button toList;
    private View tempView;
    private int PLACE_PICKER_REQUEST = 1;

    private Marker marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        if (tempView != null) {
            return tempView;
        }
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        tempView = view;
        // Get access to our MapFragment
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        // Set up an asyncronous callback to let us know when the map has loaded
        mapFrag.getMapAsync(this);
        mLatitude = LocationService.getInstance().getCurrentLat();
        mLongitude = LocationService.getInstance().getCurrentLon();
        toImage = (Button) view.findViewById(R.id.findPlant);
        toImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", mLatitude);
                args.putDouble("location_lon", mLongitude);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ImageFragment.class.getName()).commit();
            }
        });

        toList = (Button) view.findViewById(R.id.plantList);
        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ListFragment();
                Bundle args = new Bundle();
                args.putDouble("location_lat", mLatitude);
                args.putDouble("location_lon", mLongitude);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(ListFragment.class.getName()).commit();
            }
        });


        return view;
    }


    private void addMarkerOnMap(double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        Address currentLocation = getCurrentLocation(lat, lon);
        if (currentLocation == null || !"Victoria".equals(currentLocation.getAdminArea())) {
            return;
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("");
        if (marker != null) {
            marker.remove();
        }
        mLongitude = lon;
        mLatitude = lat;
        marker = m_cGoogleMap.addMarker(markerOptions);
        m_cGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private Address getCurrentLocation(double lat, double lon) {
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
            List<Address> fromLocation = geocoder.getFromLocation(lat, lon, 1);
            if (fromLocation.size() > 0) {
                return fromLocation.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Function is called once the map has fully loaded.
        // Set our map object to reference the loaded map
        m_cGoogleMap = googleMap;
        locationTracker = new LocationTracker(getActivity());
        location = locationTracker.getLocation();
        if (location != null) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
        }
        addMarkerOnMap(mLatitude, mLongitude);
        // set map to satellite map
        m_cGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        m_cGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

                //The code below demonstrate how to convert between LatLng and Location
                Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
                //Convert LatLng to Location
                Location location = new Location("Test");
                location.setLatitude(point.latitude);
                location.setLongitude(point.longitude);
                location.setTime(new Date().getTime()); //Set time as current Date
                //txtinfo.setText(location.toString());
                try {
                    List<Address> fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (fromLocation.size() > 0) {
                        String adminArea = fromLocation.get(0).getAdminArea();
                        Log.i("map", "location city " + adminArea);
                        if (!adminArea.equals("Victoria")) {
                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Convert Location to LatLng
                addMarkerOnMap(location.getLatitude(), location.getLongitude());

            }
        });
/*
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(getActivity());
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
*/


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
       // m_cGoogleMap.getUiSettings().setMapToolbarEnabled(false);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String address = String.format("Place:");
            }
        }
    }


    @Override
    public void onBackStackChanged() {

    }
}


