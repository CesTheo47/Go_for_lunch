package com.example.go_for_lunch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go_for_lunch.R;
import com.example.go_for_lunch.model.Restaurant;
import com.example.go_for_lunch.ui.activities.MainActivity;
import com.example.go_for_lunch.viewModel.RestaurantViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private RestaurantViewModel viewModel;
    private double currentLatitude;
    private double currentLongitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Zoom sur la position actuelle
        MainActivity mainActivity = (MainActivity) requireActivity();
        currentLatitude = mainActivity.getCurrentLatitude();
        currentLongitude = mainActivity.getCurrentLongitude();
        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f));

        // Observer les données des restaurants
        viewModel.getNearbyRestaurants().observe(getViewLifecycleOwner(), nearbySearchResponse -> {
            if (nearbySearchResponse != null && nearbySearchResponse.getRestaurants() != null) {
                // Parcourir les restaurants et placer des marqueurs
                for (Restaurant restaurant : nearbySearchResponse.getRestaurants()) {
                    LatLng restaurantLatLng = new LatLng(
                            restaurant.getGeometry().getLocation().getLat(),
                            restaurant.getGeometry().getLocation().getLng()
                    );
                    googleMap.addMarker(new MarkerOptions().position(restaurantLatLng).title(restaurant.getName()));
                }
            }
        });
    }
}