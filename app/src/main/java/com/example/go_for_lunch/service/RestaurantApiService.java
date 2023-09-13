package com.example.go_for_lunch.service;

import com.example.go_for_lunch.model.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantApiService {

    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearbyRestaurants(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String key
    );

}
