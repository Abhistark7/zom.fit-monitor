package com.example.zomfitmonitor.screens.browse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ItemBrowseCardBinding;
import com.example.zomfitmonitor.model.Center;
import com.example.zomfitmonitor.model.City;
import com.example.zomfitmonitor.model.activity.Activity;

import java.util.ArrayList;
import java.util.List;

public class CenterListingAdapter extends RecyclerView.Adapter<CenterListingAdapter.CentersViewHolder> {

    private static final String BROWSE_CITY = "arg_browse_city";
    private static final String BROWSE_CENTER = "arg_browse_center";
    private static final String BROWSE_ACTIVITY = "arg_browse_activity";
    private String itemType;
    private Context context;
    private List<Center> centerList;
    private List<City> cityList;
    private List<Activity> activityList;

    public CenterListingAdapter(Context context, String itemType) {
        this.context = context;
        centerList = new ArrayList<>();
        cityList = new ArrayList<>();
        activityList = new ArrayList<>();
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public CentersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBrowseCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_browse_card, parent, false);
        return new CentersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CentersViewHolder holder, int position) {
        holder.bind(centerList.get(position));
    }

    public void updateCenter(List<Center> centerList) {
        this.centerList.clear();
        this.centerList.addAll(centerList);
        notifyDataSetChanged();
    }

    public void updateCity(List<City> cityList) {
        this.cityList.clear();
        this.cityList.addAll(cityList);
        notifyDataSetChanged();
    }

    public void updateActivity(List<Activity> activityList) {
        this.activityList.clear();
        this.activityList.addAll(activityList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(centerList.size() != 0) {
        return centerList.size();
        } else if(cityList.size() != 0) {
            return cityList.size();
        } else if(activityList.size() != 0) {
            return activityList.size();
        }
        return 0;
    }

    class CentersViewHolder extends RecyclerView.ViewHolder {

        private ItemBrowseCardBinding binding;

        public CentersViewHolder(ItemBrowseCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("DefaultLocale")
        public void bind(Center center) {
            binding.text1.setText(center.name);
            binding.text2.setText(center.cityName);
            binding.ratings.setVisibility(View.VISIBLE);
            binding.ratingValue.setText(center.rating);
            binding.text3.setText(String.format("%s%d", binding.getRoot().getContext()
                    .getString(R.string.no_of_activity), center.activityIdList.size()));
            Glide.with(context).load(center.imageUrl).into(binding.cardImage);
        }
    }
}
