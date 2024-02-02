package com.example.go_for_lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go_for_lunch.databinding.ItemRestaurantBinding;
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

    // Récuperation des données
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        holder.mBinding.itemListName.setText(restaurant.getName());

        // Get full adress
        String fullAddress = restaurant.getVicinity();
        // Only text before the comma
        String addressBeforeComma = "";
        if (fullAddress != null && fullAddress.contains(",")) {
            addressBeforeComma = fullAddress.substring(0, fullAddress.indexOf(","));}
        holder.mBinding.itemListAddress.setText(addressBeforeComma);

        // get first restaurant type
        List<String> types = restaurant.getTypes();
        String firstType = (types != null && !types.isEmpty()) ? types.get(0) : "";
        holder.mBinding.itemListType.setText(firstType);

        /*holder.mBinding.itemListHour.(openingHours.isOpenNow());*/
        // Methode pour check si le restaurant à des horraires d'ouvertures => null
        /*holder.mBinding.itemListHour.setText(restaurant.getOpeningHours().toString());*/
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
