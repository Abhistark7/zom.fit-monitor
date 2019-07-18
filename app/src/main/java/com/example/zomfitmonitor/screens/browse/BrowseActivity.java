package com.example.zomfitmonitor.screens.browse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityBrowseBinding;

import org.parceler.Parcels;

public class BrowseActivity extends AppCompatActivity {

    private static final String ARG_BROWSE_TYPE = "arg_browse_type";
    private static final String BROWSE_CITY = "arg_browse_city";
    private static final String BROWSE_CENTER = "arg_browse_center";
    private static final String BROWSE_ACTIVITY = "arg_browse_activity";
    private String browseType;
    private ActivityBrowseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_browse);
        initialize();
    }

    private void initialize() {
        extract();
    }

    private void extract() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            browseType = intent.getExtras().getString(ARG_BROWSE_TYPE);
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
