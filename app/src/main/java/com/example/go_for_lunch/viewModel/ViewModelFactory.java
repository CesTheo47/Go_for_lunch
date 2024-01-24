package com.example.go_for_lunch.viewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.go_for_lunch.service.RestaurantRepository;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RestaurantRepository restaurantRepository;

    private static ViewModelFactory factory;


    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    private ViewModelFactory() { this.restaurantRepository = new RestaurantRepository(); }

    @Override
    public <T extends ViewModel>  T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RestaurantViewModel.class)) {
            return (T) new RestaurantViewModel(restaurantRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
