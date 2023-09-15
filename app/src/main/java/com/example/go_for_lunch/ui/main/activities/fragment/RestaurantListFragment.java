package com.example.go_for_lunch.ui.main.activities.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.go_for_lunch.R;
import com.example.go_for_lunch.model.NearbySearchResponse;
import com.example.go_for_lunch.ui.main.RestaurantViewModel;

import javax.annotation.Nullable;

public class RestaurantListFragment extends Fragment {

    private RestaurantViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RestaurantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        // Observer pour les données de l'API
        viewModel.getNearbyRestaurants().observe(getViewLifecycleOwner(), new Observer<NearbySearchResponse>() {
            @Override
            public void onChanged(NearbySearchResponse nearbySearchResponse) {
                // donné de l'API??

            }
        });
        return view;
    }
}