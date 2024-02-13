package com.example.go_for_lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go_for_lunch.databinding.ItemRestaurantBinding;
import com.example.go_for_lunch.model.OpeningHours;
import com.example.go_for_lunch.model.Photo;
import com.example.go_for_lunch.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    private List<Restaurant> mRestaurants;
    /*private OpeningHours openingHours;*/

    public RestaurantRecyclerViewAdapter() {
        mRestaurants = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestaurantBinding binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // Get the data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        holder.mBinding.itemListName.setText(restaurant.getName());

        // Get full address
        String fullAddress = restaurant.getVicinity();
        // Only text before the comma(virgule)
        String addressBeforeComma = "";
        if (fullAddress != null && fullAddress.contains(",")) {
            addressBeforeComma = fullAddress.substring(0, fullAddress.indexOf(","));
        }
        holder.mBinding.itemListAddress.setText(addressBeforeComma);

        // get first restaurant type
        List<String> types = restaurant.getTypes();
        String firstType = (types != null && !types.isEmpty()) ? types.get(0) : "";
        holder.mBinding.itemListType.setText(firstType);

        // Check opening hours
        OpeningHours openingHours = restaurant.getOpeningHours();
        if (openingHours != null) {
            if (openingHours.isOpenNow()) {
                holder.mBinding.itemListHour.setText("Ouvert");
            } else {
                holder.mBinding.itemListHour.setText("Fermé");
            }
        } else {
            holder.mBinding.itemListHour.setText("Horaires indisponibles");
        }

        // Get photo TODO
        List<Photo> photo = restaurant.getPhotos();
        holder.mBinding.itemListAvatar.getDrawable();
    }


    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemRestaurantBinding mBinding;

        public ViewHolder(ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }

    public void updateData(List<Restaurant> restaurants) {
        this.mRestaurants = restaurants;
        notifyDataSetChanged();
    }
}
