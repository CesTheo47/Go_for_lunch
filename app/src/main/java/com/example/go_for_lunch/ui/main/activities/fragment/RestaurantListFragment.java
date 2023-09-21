package com.example.go_for_lunch.ui.main.activities.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.go_for_lunch.databinding.FragmentRestaurantListBinding;
import com.example.go_for_lunch.model.NearbySearchResponse;
import com.example.go_for_lunch.ui.main.RestaurantViewModel;
import com.example.go_for_lunch.ui.main.ViewModelFactory;
import com.example.go_for_lunch.ui.main.adapters.RestaurantRecyclerViewAdapter;

import javax.annotation.Nullable;

public class RestaurantListFragment extends Fragment {

    private RestaurantViewModel viewModel;
    private FragmentRestaurantListBinding mBinding;
    private RestaurantRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity(), ViewModelFactory.getInstance()).get(RestaurantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentRestaurantListBinding.inflate(inflater, container, false);

        initRecyclerView();

        // Observer pour les données de l'API
        viewModel.getNearbyRestaurants().observe(getViewLifecycleOwner(), new Observer<NearbySearchResponse>() {
            @Override
            public void onChanged(NearbySearchResponse nearbySearchResponse) {
                adapter.updateData(nearbySearchResponse.getRestaurants());
            }
        });
        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new RestaurantRecyclerViewAdapter();
        mBinding.restaurantList.setAdapter(adapter);
        mBinding.restaurantList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}