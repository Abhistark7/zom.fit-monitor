package com.example.zomfitmonitor.screens.add;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityAddActivityBinding;
import com.example.zomfitmonitor.model.BaseResponse;
import com.example.zomfitmonitor.model.Center;
import com.example.zomfitmonitor.model.activity.Timing;
import com.example.zomfitmonitor.model.add.AddActivityRequest;
import com.example.zomfitmonitor.network.ApiService;
import com.example.zomfitmonitor.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddActivityActivity extends AppCompatActivity {

    private ActivityAddActivityBinding binding;
    private static final String ARG_SLOT_NUMBER = "arg_slot_number";
    private static final String SLOT_ONE = "slot_one";
    private static final String SLOT_TWO = "slot_two";
    private static final String SLOT_THREE = "slot_three";
    private static final String SLOT_FOUR = "slot_four";
    private static final String ARG_START_TIME = "arg_start_time";
    private static final String ARG_END_TIME = "arg_end_time";
    private static final String ARG_DATE = "arg_date";
    private static final String ARG_TOTAL_SEATS = "arg_total_seats";
    private static final String HYPHEN = " - ";
    private static final String COMMA = ", ";
    private int slotsVisible = 0;
    private String startTime;
    private String endTime;
    private String date;
    private String totalSeats;
    private Retrofit retrofit;
    private String selectedCenter;
    private boolean isActivityNameFilled;
    private boolean isCostFilled;
    private Spinner spinner;
    private Timing timing1;
    private Timing timing2;
    private Timing timing3;
    private Timing timing4;
    private List<Timing> timingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_activity);
        initialize();
    }

    private void initialize() {
        removeEditTextFocus();
        retrofit = BasicUtils.connectApi();
        binding.toolbar.toolbarTitle.setText(getString(R.string.add_activity));
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.enterCityName.editText.setHint(getString(R.string.enter_activity_name));
        binding.enterCityImage.editText.setHint(getString(R.string.enter_city_url));
        binding.enterCost.editText.setHint(getString(R.string.enter_cost));
        binding.selectCity.editText.setHint("");
        binding.enterCost.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.enterCost.editText.setTypeface(Typeface.create(
                "product_sans_regular", Typeface.NORMAL));
        binding.slot1.editText.setHint(getString(R.string.add_slot_1));
        binding.slot2.editText.setHint(getString(R.string.add_slot_2));
        binding.slot3.editText.setHint(getString(R.string.add_slot_3));
        binding.slot4.editText.setHint(getString(R.string.add_slot_4));
        loadAllCenters();
        handleAddActivityClick();
        addTextWatcher();
        setupAddSlot();
    }

    private void setupAddSlot() {
        binding.slot1.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openAddSlot(slotsVisible);
            }
        });
        binding.slot2.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openAddSlot(slotsVisible);
            }
        });
        binding.slot3.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openAddSlot(slotsVisible);
            }
        });
        binding.slot4.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openAddSlot(slotsVisible);
            }
        });
    }

    private void loadAllCenters() {
        showLoading(true);
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getAllCenters().enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                showLoading(false);
                setupSpinner(response.body());
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                showLoading(false);
                BasicUtils.makeToast(AddActivityActivity.this, "An error occurred!");
            }
        });
    }

    private void setupSpinner(List<Center> centers) {
        spinner = binding.selectCity.spinner;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, getCenterNameList(centers));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCenter = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<String> getCenterNameList(List<Center> centerList) {
        List<String> cityNameList = new ArrayList<>();
        for (Center center : centerList) {
            cityNameList.add(center.name);
        }
        return cityNameList;
    }

    private void handleAddActivityClick() {
        binding.loginButton.setOnClickListener(v -> addActivity());
    }

    private void addActivity() {
        showLoading(true);
        ApiService apiService = retrofit.create(ApiService.class);
        addTimingsToList();
        AddActivityRequest request = new AddActivityRequest();
        request.activityName = binding.enterCityName.editText.getText().toString();
        request.activityImageUrl = binding.enterCityImage.editText.getText().toString();
        request.cost = binding.enterCost.editText.toString();
        request.centerName = selectedCenter;
        request.timingList = timingList;
        apiService.addActivity(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                showLoading(false);
                if (response.body().status) {
                    BasicUtils.makeToast(AddActivityActivity.this, "Activity added successfully!");
                    onBackPressed();
                } else {
                    BasicUtils.makeToast(AddActivityActivity.this, response.body().message);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showLoading(false);
                BasicUtils.makeToast(AddActivityActivity.this, "An error occurred!");
            }
        });
    }

    private void addTimingsToList() {
        if(timing1 != null) {
            timingList.add(timing1);
        }
        if(timing2 != null) {
            timingList.add(timing2);
        }
        if(timing3 != null) {
            timingList.add(timing3);
        }
        if(timing4 != null) {
            timingList.add(timing3);
        }
    }

    private void addTextWatcher() {
        binding.enterCityName.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isActivityNameFilled = charSequence.length() > 0;
                handleAddButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.enterCost.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isCostFilled = charSequence.length() > 0;
                handleAddButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void openAddSlot(int slotNumber) {
        String selectedSlot = null;
        switch (slotNumber) {
            case 0:
                selectedSlot = SLOT_ONE;
                break;
            case 1:
                selectedSlot = SLOT_TWO;
                break;
            case 2:
                selectedSlot = SLOT_THREE;
                break;
            case 3:
                selectedSlot = SLOT_FOUR;
                break;
        }
        Intent intent = new Intent(AddActivityActivity.this, AddTimingSlots.class);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SLOT_NUMBER, selectedSlot);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                startTime = data.getStringExtra(ARG_START_TIME);
                endTime = data.getStringExtra(ARG_END_TIME);
                date = data.getStringExtra(ARG_DATE);
                totalSeats = data.getStringExtra(ARG_TOTAL_SEATS);
                String slotNumber = data.getStringExtra(ARG_SLOT_NUMBER);
                updateTimingList(slotsVisible, startTime, endTime, date, totalSeats);
                updateSlot(slotNumber, startTime, endTime, date, totalSeats);
                slotsVisible++;
                removeEditTextFocus();
            }
        }
    }


    private void updateTimingList(int slotsVisible, String startTime, String endTime, String date, String totalSeats) {
        switch (slotsVisible) {
            case 0:
                timing1 = new Timing();
                timing1.bookedCount = 0;
                timing1.isAvailable = true;
                timing1.date = date;
                timing1.time = startTime + HYPHEN + endTime;
                timing1.maxCount = Integer.valueOf(totalSeats);
                break;
            case 1:
                timing2 = new Timing();
                timing2.bookedCount = 0;
                timing2.isAvailable = true;
                timing2.date = date;
                timing2.time = startTime + HYPHEN + endTime;
                timing2.maxCount = Integer.valueOf(totalSeats);
                break;
            case 2:
                timing3 = new Timing();
                timing3.bookedCount = 0;
                timing3.isAvailable = true;
                timing3.date = date;
                timing3.time = startTime + HYPHEN + endTime;
                timing3.maxCount = Integer.valueOf(totalSeats);
                break;
            case 3:
                timing4 = new Timing();
                timing4.bookedCount = 0;
                timing4.isAvailable = true;
                timing4.date = date;
                timing4.time = startTime + HYPHEN + endTime;
                timing4.maxCount = Integer.valueOf(totalSeats);
                break;
        }
    }

    private void updateSlot(String slotNumber, String startTime, String endTime, String date, String totalSeats) {
        switch (slotNumber) {
            case SLOT_ONE:
                binding.slot1.getRoot().setVisibility(View.VISIBLE);
                binding.slot1.editText.setText(String.format(
                        "%s%s%s%s%s%s%s%s", startTime, HYPHEN, endTime, COMMA, date, COMMA, totalSeats, getString(R.string.seats)));
                break;
            case SLOT_TWO:
                binding.slot2.editText.setText(String.format(
                        "%s%s%s%s%s%s%s%s", startTime, HYPHEN, endTime, COMMA, date, COMMA, totalSeats, getString(R.string.seats)));
                break;
            case SLOT_THREE:
                binding.slot3.editText.setText(String.format(
                        "%s%s%s%s%s%s%s%s", startTime, HYPHEN, endTime, COMMA, date, COMMA, totalSeats, getString(R.string.seats)));
                break;
            case SLOT_FOUR:
                binding.slot4.editText.setText(String.format(
                        "%s%s%s%s%s%s%s%s", startTime, HYPHEN, endTime, COMMA, date, COMMA, totalSeats, getString(R.string.seats)));
                break;
        }
    }

    private void removeEditTextFocus() {
        binding.slot1.editText.clearFocus();
        binding.slot2.editText.clearFocus();
        binding.slot3.editText.clearFocus();
        binding.slot4.editText.clearFocus();
    }

    private void handleAddButtonState() {
        if (isActivityNameFilled && isCostFilled) {
            binding.loginButton.setEnabled(true);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            binding.loginButton.setEnabled(false);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void showLoading(boolean show) {
        if (show) {
            binding.progressCircular.setVisibility(View.VISIBLE);
        } else {
            binding.progressCircular.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        removeEditTextFocus();
        super.onResume();
    }
}
