package com.example.zomfitmonitor.screens.landing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.FragmentBrowseBinding;
import com.example.zomfitmonitor.screens.browse.BrowseActivity;
import com.example.zomfitmonitor.utils.BasicUtils;

public class BrowseFragment extends Fragment {
    private static final String ARG_BROWSE_TYPE = "arg_browse_type";
    private static final String BROWSE_CITY = "arg_browse_city";
    private static final String BROWSE_CENTER = "arg_browse_center";
    private static final String BROWSE_ACTIVITY = "arg_browse_activity";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private FragmentBrowseBinding binding;

    public BrowseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        setupCityCard();
        setupCenterCard();
        setupActivityCard();
    }

    private void setupCityCard() {
        binding.cityCard.centerText.setText(getString(R.string.browse_city));
        binding.cityCard.container.setBackgroundColor(getResources().getColor(R.color.teal500));
        binding.cityCard.cardImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_cityscape));
        binding.cityCard.getRoot().setOnClickListener(v -> openBrowseActivity(BROWSE_CITY));
    }

    private void setupCenterCard() {
        binding.centerCard.centerText.setText(getString(R.string.browse_center));
        binding.centerCard.container.setBackgroundColor(getResources().getColor(R.color.purple500));
        binding.centerCard.cardImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));
        binding.centerCard.getRoot().setOnClickListener(v -> openBrowseActivity(BROWSE_CENTER));
    }

    private void setupActivityCard() {
        binding.activityCard.centerText.setText(getString(R.string.browse_activity));
        binding.activityCard.container.setBackgroundColor(getResources().getColor(R.color.green500));
        binding.activityCard.cardImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_running_person));
        binding.activityCard.getRoot().setOnClickListener(v -> openBrowseActivity(BROWSE_ACTIVITY));
    }

    private void openBrowseActivity(String browseType) {
        Intent intent = new Intent(getActivity(), BrowseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BROWSE_TYPE, browseType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
