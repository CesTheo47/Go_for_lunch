package com.example.go_for_lunch.ui.main;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.go_for_lunch.R;
import com.example.go_for_lunch.databinding.ActivityMainBinding;
import com.example.go_for_lunch.ui.main.fragment.MapsFragment;
import com.example.go_for_lunch.ui.main.fragment.RestaurantListFragment;
import com.example.go_for_lunch.ui.main.fragment.WorkmatesListFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolBar();
        initNavView();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
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
                        Toast.makeText(MainActivity.this, "click logout", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });


    }
}
