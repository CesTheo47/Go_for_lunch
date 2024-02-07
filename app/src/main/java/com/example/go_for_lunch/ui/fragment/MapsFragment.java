package com.example.go_for_lunch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.go_for_lunch.R;
import com.example.go_for_lunch.ui.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private double currentLatitude;
    private double currentLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        // Get the current latitude and longitude from MainActivity
        MainActivity mainActivity = (MainActivity) requireActivity();
        currentLatitude = mainActivity.getCurrentLatitude();
        currentLongitude = mainActivity.getCurrentLongitude();

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Add a marker for the current location
        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));

        // Move the camera to the current location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 5f));
    }
}