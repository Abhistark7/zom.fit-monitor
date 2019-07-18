package com.example.zomfitmonitor.screens.landing.trends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ItemTrendsBinding;
import com.example.zomfitmonitor.model.Center;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class CenterTrendAdapter extends RecyclerView.Adapter<CenterTrendAdapter.CenterTrendViewHolder> {

    private static final String HASH = "#";
    private Context context;
    private List<Center> centerList;

    public CenterTrendAdapter(Context context) {
        this.context = context;
        this.centerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CenterTrendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_trends, parent, false);
        return new CenterTrendViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CenterTrendViewHolder holder, int position) {
        holder.bind(centerList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return centerList.size();
    }

    public void update(List<Center> centerList) {
        this.centerList.clear();
        this.centerList.addAll(centerList);
        notifyDataSetChanged();
    }

    class CenterTrendViewHolder extends RecyclerView.ViewHolder {

        private ItemTrendsBinding binding;

        public CenterTrendViewHolder(ItemTrendsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Center center, int position) {
            binding.text1.setText(center.name);
            binding.newText2.setText(center.cityName);
            binding.rank.setText(format("%s%d", HASH, position + 1));
            binding.text3.setText(String.format("%s%s",
                    binding.getRoot().getContext().getString(R.string.total_bookings),
                    String.valueOf(center.currentBookingCount)));
            Glide.with(context).load(center.imageUrl).into(binding.cardImage);
        }
    }
}
