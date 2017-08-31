package com.example.yihanwang.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yihanwang.myapplication.entities.LocationTracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback, FragmentManager.OnBackStackChangedListener {
    private GoogleMap m_cGoogleMap;
    private Location location;
    private Double mLatitude,mLongitude;
    private LocationTracker locationTracker;
    private Button toImage;
    private Button toList;
    private View tempView;
    private int PLACE_PICKER_REQUEST = 1;

    private static final LatLng LOCATION_GRAMPIANS
            = new LatLng(-37.6145, 142.3244);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        locationTracker = new LocationTracker(getActivity());
        location = locationTracker.getLocation();
      //  latitude = location.getLatitude();
        // longitude = location.getLongitude();

        if (tempView != null) {
            return tempView;
        }

        View view = inflater.inflate(R.layout.map_fragment, container, false);
        tempView = view;
        // Get access to our MapFragment
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        // Set up an asyncronous callback to let us know when the map has loaded
        mapFrag.getMapAsync(this);
        /*
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                       List<Address> addressList =  geocoder.getFromLocation(latitude,longitude,1);
                        String str = addressList.get(0).getLocality();
                        m_cGoogleMap.addMarker(new MarkerOptions().position(latLng).title("You Are Here"));
                        m_cGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }

        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
      */

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


        m_cGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

                //The code below demonstrate how to convert between LatLng and Location

                //Convert LatLng to Location
                Location location = new Location("Test");
                location.setLatitude(point.latitude);
                location.setLongitude(point.longitude);
                location.setTime(new Date().getTime()); //Set time as current Date
                //txtinfo.setText(location.toString());

                //Convert Location to LatLng
                LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(newLatLng)
                        .title(newLatLng.toString());

                m_cGoogleMap.addMarker(markerOptions);

            }
        });


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Function is called once the map has fully loaded.
        // Set our map object to reference the loaded map
        m_cGoogleMap = googleMap;
        //latitude = 37.8770;
        //longitude = 145.0443;
        LatLng latLng = new LatLng(mLatitude,mLongitude);
        // Move the focus of the map to be on the Grampians park. 15 is for zoom
         m_cGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        m_cGoogleMap.addMarker(new MarkerOptions().position(latLng).title("You Are Here"));
        // set map to satellite map
        // m_cGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

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

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,getActivity());
                String address = String.format("Place:");
            }
        }
    }


    @Override
    public void onBackStackChanged() {

    }
}


