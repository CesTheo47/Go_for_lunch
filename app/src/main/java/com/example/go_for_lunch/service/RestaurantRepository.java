package com.example.go_for_lunch.service;

import com.example.go_for_lunch.BuildConfig;
import com.example.go_for_lunch.model.NearbySearchResponse;

import retrofit2.Call;

public class RestaurantRepository {

    public Call<NearbySearchResponse> getNearbySearchRestaurants() {

        return RetrofitApi.getInstance().getRestaurantApiService().getNearbyRestaurants(
                "48.856669,2.354444",
                50000,
                "restaurant",
                BuildConfig.GOOGLE_PLACES_API_KEY
        );

    }

}
