package com.example.zomfitmonitor.screens.landing;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.FragmentBrowseBinding;
import com.example.zomfitmonitor.utils.BasicUtils;

public class BrowseFragment extends Fragment {
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
        binding.cityCard.cardImage.setBackgroundColor(getResources().getColor(R.color.teal500));
        binding.cityCard.getRoot().setOnClickListener(v -> BasicUtils.makeToast(getActivity(), "city"));
    }

    private void setupCenterCard() {
        binding.centerCard.centerText.setText(getString(R.string.browse_center));
        binding.centerCard.cardImage.setBackgroundColor(getResources().getColor(R.color.purple500));
        binding.centerCard.getRoot().setOnClickListener(v -> BasicUtils.makeToast(getActivity(), "center"));
    }

    private void setupActivityCard() {
        binding.activityCard.centerText.setText(getString(R.string.browse_activity));
        binding.activityCard.cardImage.setBackgroundColor(getResources().getColor(R.color.green500));
        binding.activityCard.getRoot().setOnClickListener(v -> BasicUtils.makeToast(getActivity(), "activity"));
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
