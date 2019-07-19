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
import com.example.zomfitmonitor.databinding.FragmentAddBinding;
import com.example.zomfitmonitor.screens.add.AddActivityActivity;
import com.example.zomfitmonitor.screens.add.AddCenterActivity;
import com.example.zomfitmonitor.screens.add.AddCityActivity;

public class AddFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private FragmentAddBinding binding;

    public AddFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.addCity.optionTitle.setText(getString(R.string.add_city));
        binding.addCenter.optionTitle.setText(getString(R.string.add_center));
        binding.addActivity.optionTitle.setText(getString(R.string.add_activity));
        binding.addCity.getRoot().setOnClickListener(v -> openAddCity());
        binding.addCenter.getRoot().setOnClickListener(v -> openAddCenter());
        binding.addActivity.getRoot().setOnClickListener(v -> openAddActivity());
    }

    private void openAddCity() {
        Intent intent = new Intent(getActivity(), AddCityActivity.class);
        startActivity(intent);
    }

    private void openAddCenter() {
        Intent intent = new Intent(getActivity(), AddCenterActivity.class);
        startActivity(intent);
    }

    private void openAddActivity() {
        Intent intent = new Intent(getActivity(), AddActivityActivity.class);
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
