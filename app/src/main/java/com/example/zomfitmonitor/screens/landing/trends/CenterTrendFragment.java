package com.example.zomfitmonitor.screens.landing.trends;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.FragmentCenterTrendBinding;
import com.example.zomfitmonitor.model.Center;
import com.example.zomfitmonitor.model.activity.Activity;
import com.example.zomfitmonitor.network.ApiService;
import com.example.zomfitmonitor.utils.BasicUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CenterTrendFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private FragmentCenterTrendBinding binding;
    private CenterTrendAdapter adapter;
    private Retrofit retrofit;

    public CenterTrendFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_center_trend, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        setupSwipe();
        showLoadingView();
        retrofit = BasicUtils.connectApi();
        setUpCenterRecyclerView();
        loadAllActivity();
    }

    private void setupSwipe() {
        binding.swipe.setOnRefreshListener(() -> loadAllActivity());
    }

    private void setUpCenterRecyclerView() {
        adapter = new CenterTrendAdapter(getActivity());
        binding.centersRecycler.setNestedScrollingEnabled(false);
        binding.centersRecycler.setAdapter(adapter);
        binding.centersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadAllActivity() {
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getTrendingCenters().enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                showContentView();
                adapter.update(response.body());
                if(binding.swipe.isRefreshing()) {
                    binding.swipe.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                showErrorView();
                if(binding.swipe.isRefreshing()) {
                    binding.swipe.setRefreshing(false);
                }
            }
        });
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
