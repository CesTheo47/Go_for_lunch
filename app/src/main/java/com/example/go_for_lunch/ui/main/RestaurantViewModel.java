package com.example.go_for_lunch.ui.main;

import android.content.Context;
import android.widget.Toast;

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
    private final Context context;

    public RestaurantViewModel(Context context) {
        repository = new RestaurantRepository();
        this.context = context;
    }

    public LiveData<NearbySearchResponse> getNearbyRestaurants() {
        MutableLiveData<NearbySearchResponse> data = new MutableLiveData<>();
        Call<NearbySearchResponse> call = repository.getNearbySearchRestaurants();

        call.enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(context, "Échec de la récupération des données. Veuillez réessayer plus tard.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                // ??

            }
        });

        return data;
    }
}
