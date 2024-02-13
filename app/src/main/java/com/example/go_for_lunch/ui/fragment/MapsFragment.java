package com.example.go_for_lunch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private RestaurantViewModel viewModel;
    private double currentLatitude;
    private double currentLongitude;
    private UiSettings UiSettings;

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

        UiSettings = map.getUiSettings();

        // Ui settings
        UiSettings.setZoomControlsEnabled(true);


        // get current location in Main Activity
        MainActivity mainActivity = (MainActivity) requireActivity();
        currentLatitude = mainActivity.getCurrentLatitude();
        currentLongitude = mainActivity.getCurrentLongitude();
        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);

        // Marker to current location
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // zoom to current location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f));

        // Observe restaurant data
        viewModel.getNearbyRestaurants().observe(getViewLifecycleOwner(), nearbySearchResponse -> {
            if (nearbySearchResponse != null && nearbySearchResponse.getRestaurants() != null) {
                // Browse restaurants and place markers
                for (Restaurant restaurant : nearbySearchResponse.getRestaurants()) {
                    LatLng restaurantLatLng = new LatLng(
                            restaurant.getGeometry().getLocation().getLat(),
                            restaurant.getGeometry().getLocation().getLng()
                    );
                    Marker marker = googleMap.addMarker(new MarkerOptions().position(restaurantLatLng).title(restaurant.getName()).snippet(restaurant.getPlaceId()));
                }
            }
        });
        // Add a listener for marker clicks
        googleMap.setOnMarkerClickListener(marker -> {
            // Get the restaurant ID from the marker snippet
            String placeId = marker.getSnippet();

            // Handle marker click
            handleMarkerClick(placeId);

            // Affichez le place ID dans un Toast pour le tester => à supprimer un fois finit
            Toast.makeText(getContext(), "Place ID: " + placeId, Toast.LENGTH_SHORT).show();


            // Return true to indicate that the listener has consumed the event
            return true;
        });
    }
    private void handleMarkerClick(String placeId) {
        // Implement your logic to navigate to restaurant details
        // Use the restaurant ID to display relevant details
    }
}