package com.example.go_for_lunch.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    // Singleton => une seule instance de Retrofit
    private static RetrofitApi instance = null;

    private final RestaurantApiService service;

    private RetrofitApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RestaurantApiService.class);
    }

    public static RetrofitApi getInstance() {
        if (instance == null) {
            instance = new RetrofitApi();
        }

        return instance;
    }

    public RestaurantApiService getRestaurantApiService() {
        return service;
    }

}
