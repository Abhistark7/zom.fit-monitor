package com.example.zomfitmonitor.screens.landing.trends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ItemTrendsBinding;
import com.example.zomfitmonitor.model.activity.Activity;
import com.example.zomfitmonitor.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class ActivityTrendAdapter extends RecyclerView.Adapter<ActivityTrendAdapter.ActivityTrendViewHolder> {

    private static final String HASH = "#";
    private Context context;
    private List<Activity> activityList;

    public ActivityTrendAdapter(Context context) {
        this.context = context;
        activityList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ActivityTrendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_trends, parent, false);
        return new ActivityTrendViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityTrendViewHolder holder, int position) {
        holder.bind(activityList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void update(List<Activity> activityList) {
        this.activityList.clear();
        this.activityList.addAll(activityList);
        notifyDataSetChanged();
    }

    class ActivityTrendViewHolder extends RecyclerView.ViewHolder {

        private ItemTrendsBinding binding;

        public ActivityTrendViewHolder(ItemTrendsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Activity activity, int position) {
            binding.text1.setText(activity.name);
            binding.rank.setText(format("%s%d", HASH, position + 1));
            binding.text2.setText(activity.bookedSlots);
            binding.cardImage.setPaddingRelative(80, 80, 80, 80);
            ImageUtils.fetchSvg(context, activity.iconUrl, binding.cardImage);
        }
    }
}
