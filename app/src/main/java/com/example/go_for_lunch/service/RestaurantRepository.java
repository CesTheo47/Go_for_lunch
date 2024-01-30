package com.example.go_for_lunch.service;

import com.example.go_for_lunch.BuildConfig;
import com.example.go_for_lunch.model.NearbySearchResponse;

import retrofit2.Call;

public class RestaurantRepository {

    public Call<NearbySearchResponse> getNearbySearchRestaurants(double latitude, double longitude) {

        return RetrofitApi.getInstance().getRestaurantApiService().getNearbyRestaurants(
                latitude + "," + longitude,
                5000,
                "restaurant",
                BuildConfig.GOOGLE_PLACES_API_KEY
        );

    }

}
