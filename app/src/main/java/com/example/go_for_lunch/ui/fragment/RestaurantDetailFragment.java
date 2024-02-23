package com.example.go_for_lunch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.go_for_lunch.R;

public class RestaurantDetailFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get data from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String restaurantName = bundle.getString("restaurantName");
            String restaurantAddress = bundle.getString("restaurantAddress");
            String restaurantType = bundle.getString("restaurantType");
            float restaurantRating = bundle.getFloat("restaurantRating");

            // Update TextViews with real restaurant data
            TextView restaurantNameTextView = view.findViewById(R.id.restaurantName);
            restaurantNameTextView.setText(restaurantName);

            TextView restaurantAddressTextView = view.findViewById(R.id.restaurantAddress);
            restaurantAddressTextView.setText(restaurantAddress);

            TextView restaurantTypeTextView = view.findViewById(R.id.restaurantType);
            restaurantTypeTextView.setText(restaurantType);

            RatingBar restaurantRatingBar = view.findViewById(R.id.restaurantRating);
            restaurantRatingBar.setRating(restaurantRating);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false);
    }

}
