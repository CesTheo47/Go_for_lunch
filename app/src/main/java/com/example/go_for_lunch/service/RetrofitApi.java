package com.example.go_for_lunch.service;

import retrofit2.Retrofit;

public class RetrofitApi {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .build();
}
