package com.example.go_for_lunch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13f));

        // Observe restaurant data
        viewModel.getNearbyRestaurants().observe(getViewLifecycleOwner(), nearbySearchResponse -> {
            if (nearbySearchResponse != null && nearbySearchResponse.getRestaurants() != null) {
                // Browse restaurants and place markers
                for (Restaurant restaurant : nearbySearchResponse.getRestaurants()) {
                    LatLng restaurantLatLng = new LatLng(
                            restaurant.getGeometry().getLocation().getLat(),
                            restaurant.getGeometry().getLocation().getLng()
                    );
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(restaurantLatLng)
                            .title(restaurant.getName())
                            .snippet(restaurant.getPlaceId());
                    Marker marker = googleMap.addMarker(markerOptions);

                    // Attach restaurant data to marker
                    marker.setTag(restaurant);
                }
            }
        });

        // Add a listener for marker clicks
        googleMap.setOnMarkerClickListener(marker -> {
            // Get the restaurant data from the marker's tag
            Restaurant restaurant = (Restaurant) marker.getTag();
            if (restaurant != null) {
                // Get the relevant information
                String placeId = restaurant.getPlaceId();
                String restaurantName = restaurant.getName();
                String restaurantAddress = restaurant.getVicinity();
                String restaurantType = restaurant.getTypes().isEmpty() ? "Unknown" : restaurant.getTypes().get(0); // Assuming the first type is the main type

                // Handle marker click
                handleMarkerClick(placeId, restaurantName, restaurantAddress, restaurantType);
            }

            return true;
        });
    }

    private void handleMarkerClick(String placeId, String restaurantName, String restaurantAddress, String restaurantType) {
        Bundle bundle = new Bundle();
        bundle.putString("placeId", placeId);
        bundle.putString("restaurantName", restaurantName);
        bundle.putString("restaurantAddress", restaurantAddress);
        bundle.putString("restaurantType", restaurantType);

        Navigation.findNavController(requireView()).navigate(R.id.action_mapsFragment_to_restaurantDetailFragment, bundle);
    }
}