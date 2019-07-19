package com.example.zomfitmonitor.screens.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityAddCityBinding;
import com.example.zomfitmonitor.model.BaseResponse;
import com.example.zomfitmonitor.model.add.AddCityRequest;
import com.example.zomfitmonitor.network.ApiService;
import com.example.zomfitmonitor.utils.BasicUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddCityActivity extends AppCompatActivity {

    private ActivityAddCityBinding binding;
    private boolean isCityNameFilled;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_city);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        binding.toolbar.toolbarTitle.setText(getString(R.string.add_city));
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.enterCityName.editText.setHint(getString(R.string.enter_city_name));
        binding.enterCityImage.editText.setHint(getString(R.string.enter_city_url));
        handleAddClick();
        addTextWatcher();
    }

    private void handleAddClick() {
        binding.loginButton.setOnClickListener(v -> addCity());
    }

    private void handleAddButtonState() {
        if (isCityNameFilled) {
            binding.loginButton.setEnabled(true);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            binding.loginButton.setEnabled(false);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void addTextWatcher() {
        binding.enterCityName.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isCityNameFilled = charSequence.length() > 0;
                handleAddButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addCity() {
        showLoading(true);
        ApiService apiService = retrofit.create(ApiService.class);
        AddCityRequest addCityRequest = new AddCityRequest();
        addCityRequest.cityName = binding.enterCityName.editText.getText().toString();
        addCityRequest.cityImageUrl = binding.enterCityImage.editText.getText().toString();
        apiService.addCity(addCityRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                showLoading(false);
                if(response.body().status) {
                    BasicUtils.makeToast(AddCityActivity.this, "City Added");
                    onBackPressed();
                } else {
                    BasicUtils.makeToast(AddCityActivity.this, response.body().message);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showLoading(false);
                BasicUtils.makeToast(AddCityActivity.this, "An error occurred!");
            }
        });
    }

    private void showLoading(boolean show) {
        if(show) {
            binding.progressCircular.setVisibility(View.VISIBLE);
        } else {
            binding.progressCircular.setVisibility(View.GONE);
        }
    }
}
