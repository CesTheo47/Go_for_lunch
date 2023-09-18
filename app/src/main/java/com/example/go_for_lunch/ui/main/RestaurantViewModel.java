package com.example.go_for_lunch.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go_for_lunch.model.NearbySearchResponse;
import com.example.go_for_lunch.service.RestaurantRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantViewModel extends ViewModel {

    private final RestaurantRepository repository;

    private MutableLiveData<NearbySearchResponse> nearbyRestaurants = new MutableLiveData<>();

    public LiveData<NearbySearchResponse> getNearbyRestaurants() {
        return nearbyRestaurants;
    }

    public RestaurantViewModel(RestaurantRepository restaurantRepository) {
        repository = restaurantRepository;
    }

    public void loadNearbyRestaurants() {

        repository.getNearbySearchRestaurants().enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                if (response.isSuccessful()) {
                    nearbyRestaurants.postValue(response.body());
                } else {
                    //Toast.makeText(context, "Échec de la récupération des données. Veuillez réessayer plus tard.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponse> call, Throwable t) {

            }
        });
    }
}
