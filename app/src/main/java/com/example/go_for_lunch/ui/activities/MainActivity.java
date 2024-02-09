package com.example.go_for_lunch.ui.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.go_for_lunch.R;
import com.example.go_for_lunch.databinding.ActivityMainBinding;
import com.example.go_for_lunch.viewModel.RestaurantViewModel;
import com.example.go_for_lunch.viewModel.ViewModelFactory;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RestaurantViewModel viewModel;

    private double currentLatitude;
    private double currentLongitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        initToolBar();
        initNavView();

        // Initializing currentLocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
    }

    // get currentLocation
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                loadNearbyRestaurants();
            } else {
                LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1);

                LocationCallback locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                        Location location1 = locationResult.getLastLocation();
                        currentLatitude = location1.getLatitude();
                        currentLongitude = location1.getLongitude();

                        loadNearbyRestaurants();
                    }
                };
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }

        });
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    // Get restaurant around with ViewModel
    private void loadNearbyRestaurants() {
        viewModel.loadNearbyRestaurants(currentLatitude, currentLongitude);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(RestaurantViewModel.class);
    }

    private void initToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        binding.toolbar.setNavigationOnClickListener(v -> binding.drawerLayout.open());
    }

    private void initNavView() {
        TextView email = binding.navView.getHeaderView(0).findViewById(R.id.tVUserEmailDrawerMenu);
        email.setText("test@gmail.com");

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_lunch:
                        Toast.makeText(MainActivity.this, "click lunch", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "click settings", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_logout:
                        signOut();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}
