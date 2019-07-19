package com.example.zomfitmonitor.screens.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityAddCenterBinding;
import com.example.zomfitmonitor.model.BaseResponse;
import com.example.zomfitmonitor.model.City;
import com.example.zomfitmonitor.model.add.AddCenterRequest;
import com.example.zomfitmonitor.network.ApiService;
import com.example.zomfitmonitor.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddCenterActivity extends AppCompatActivity {

    private ActivityAddCenterBinding binding;
    private Retrofit retrofit;
    private String selectedCity;
    private boolean isCenterNameFilled;
    private boolean isCitySelected;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_center);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        binding.toolbar.toolbarTitle.setText(getString(R.string.add_center));
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        retrofit = BasicUtils.connectApi();
        binding.enterCityName.editText.setHint(getString(R.string.enter_center_name));
        binding.enterCityImage.editText.setHint(getString(R.string.enter_city_url));
        binding.selectCity.editText.setHint("");
        loadAllCities();
        handleAddCenterClick();
        addTextWatcher();
    }

    private void addTextWatcher() {
        binding.enterCityName.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isCenterNameFilled = charSequence.length() > 0;
                handleAddButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleAddButtonState() {
        if (isCenterNameFilled) {
            binding.loginButton.setEnabled(true);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            binding.loginButton.setEnabled(false);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void loadAllCities() {
        showLoading(true);
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getAllCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                showLoading(false);
                setupSpinner(response.body());
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                showLoading(false);
                BasicUtils.makeToast(AddCenterActivity.this, "An error occurred!");
        }
        });
    }

    private void handleAddCenterClick() {
        binding.loginButton.setOnClickListener(v -> addCenter());
    }

    private void addCenter() {
        showLoading(true);
        ApiService apiService = retrofit.create(ApiService.class);
        AddCenterRequest request = new AddCenterRequest();
        request.centerName = binding.enterCityName.editText.getText().toString();
        request.centerImageUrl = binding.enterCityImage.editText.getText().toString();
        request.cityName = selectedCity;
        apiService.addCenter(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                showLoading(false);
                if(response.body().status) {
                    BasicUtils.makeToast(AddCenterActivity.this, "Center Added!");
                    onBackPressed();
                } else {
                    BasicUtils.makeToast(AddCenterActivity.this, response.body().message);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showLoading(false);
                BasicUtils.makeToast(AddCenterActivity.this, "An error occurred!");
            }
        });
    }

    private void setupSpinner(List<City> cityList) {
        spinner = binding.selectCity.spinner;
        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, getCityNameList(cityList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCity = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private List<String> getCityNameList(List<City> cityList) {
        List<String> cityNameList = new ArrayList<>();
        for(City city: cityList) {
            cityNameList.add(city.name);
        }
        return cityNameList;
    }

    private void showLoading(boolean show) {
        if(show) {
            binding.progressCircular.setVisibility(View.VISIBLE);
        } else {
            binding.progressCircular.setVisibility(View.GONE);
        }
    }
}
