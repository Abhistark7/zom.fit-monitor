package com.example.zomfitmonitor.screens.browse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityBrowseBinding;
import com.example.zomfitmonitor.model.Center;
import com.example.zomfitmonitor.model.City;
import com.example.zomfitmonitor.model.activity.Activity;
import com.example.zomfitmonitor.network.ApiService;
import com.example.zomfitmonitor.utils.BasicUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BrowseActivity extends AppCompatActivity {

    private static final String ARG_BROWSE_TYPE = "arg_browse_type";
    private static final String BROWSE_CITY = "arg_browse_city";
    private static final String BROWSE_CENTER = "arg_browse_center";
    private static final String BROWSE_ACTIVITY = "arg_browse_activity";
    private String browseType;
    private ActivityBrowseBinding binding;
    private BrowseAdapter adapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_browse);
        initialize();
    }

    private void initialize() {
        showLoadingView();
        retrofit = BasicUtils.connectApi();
        extract();
        setupToolbar();
        setUpCenterRecyclerView();
        loadContent();
    }

    private void extract() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            browseType = intent.getExtras().getString(ARG_BROWSE_TYPE);
        }
    }

    private void setupToolbar() {
        switch (browseType) {
            case BROWSE_CITY : binding.toolbar.toolbarTitle.setText(getString(R.string.browse_city));
            break;
            case BROWSE_CENTER : binding.toolbar.toolbarTitle.setText(getString(R.string.browse_center));
            break;
            case BROWSE_ACTIVITY : binding.toolbar.toolbarTitle.setText(getString(R.string.browse_activity));
            break;
        }
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
    }

    private void setUpCenterRecyclerView() {
        adapter = new BrowseAdapter(this, browseType);
        binding.centersRecycler.setNestedScrollingEnabled(false);
        binding.centersRecycler.setAdapter(adapter);
        binding.centersRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadContent() {
        ApiService apiService = retrofit.create(ApiService.class);
        switch (browseType) {
            case BROWSE_CITY : apiService.getAllCities().enqueue(new Callback<List<City>>() {
                @Override
                public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                    showContentView();
                    adapter.updateCity(response.body());
                }

                @Override
                public void onFailure(Call<List<City>> call, Throwable t) {
                    showErrorView();
                }
            });
                break;
            case BROWSE_CENTER : apiService.getAllCenters().enqueue(new Callback<List<Center>>() {
                @Override
                public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                    showContentView();
                    adapter.updateCenter(response.body());
                }

                @Override
                public void onFailure(Call<List<Center>> call, Throwable t) {
                    showErrorView();
                }
            });
                break;
            case BROWSE_ACTIVITY : apiService.getAllActivities().enqueue(new Callback<List<Activity>>() {
                @Override
                public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                    showContentView();
                    adapter.updateActivity(response.body());
                }

                @Override
                public void onFailure(Call<List<Activity>> call, Throwable t) {
                    showErrorView();
                }
            });
                break;
        }
    }

    private void showLoadingView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
        binding.loadingView.setVisibility(View.GONE);
    }

    private void showContentView() {
        binding.contentView.setVisibility(View.VISIBLE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.GONE);
    }
}
