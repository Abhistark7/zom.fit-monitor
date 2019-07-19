package com.example.zomfitmonitor.screens.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityAddTimingSlotsBinding;

import java.util.Calendar;

public class AddTimingSlots extends AppCompatActivity {

    private static final String ARG_SLOT_NUMBER = "arg_slot_number";
    private static final String ARG_START_TIME = "arg_start_time";
    private static final String ARG_END_TIME = "arg_end_time";
    private static final String ARG_DATE = "arg_date";
    private static final String ARG_TOTAL_SEATS = "arg_total_seats";
    private String slotNumber;
    private boolean isStartTimeFilled;
    private boolean isEndTimeFilled;
    private boolean isDateFilled;
    private boolean isTotalSeatsFilled;
    private ActivityAddTimingSlotsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_timing_slots);
        extract();
        initialize();
    }

    private void extract() {
        slotNumber = getIntent().getStringExtra(ARG_SLOT_NUMBER);
    }

    private void initialize() {
        binding.toolbar.toolbarTitle.setText(getString(R.string.add_slot));
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.enterCityName.editText.setHint(getString(R.string.select_start_time));
        binding.enterCityImage.editText.setHint(getString(R.string.select_end_time));
        binding.enterTotalSeats.editText.setHint(getString(R.string.enter_total_seats));
        binding.enterTotalSeats.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.enterCityName.getRoot().setOnClickListener(v -> openStartTimeSelector());
        binding.enterCityImage.getRoot().setOnClickListener(v -> openEndTimeSelector());
        binding.enterCost.editText.setHint(getString(R.string.select_date));
        binding.enterCost.getRoot().setOnClickListener(v -> openDatePicker());
        binding.loginButton.setOnClickListener(v -> addSlot());
        handleAddButtonState();
        handleEditTexts();
    }

    private void handleAddButtonState() {
        if (isDateFilled && isEndTimeFilled && isStartTimeFilled && isTotalSeatsFilled) {
            binding.loginButton.setEnabled(true);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            binding.loginButton.setEnabled(false);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void handleEditTexts() {
        binding.enterCityName.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openStartTimeSelector();
            }
        });
        binding.enterCityImage.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openEndTimeSelector();
            }
        });
        binding.enterCost.editText.setOnFocusChangeListener((view, b) -> {
            if(b) {
                openDatePicker();
            }
        });
        binding.enterTotalSeats.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isTotalSeatsFilled = charSequence.length() > 0;
                handleAddButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void openStartTimeSelector() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTimingSlots.this,
                (timePicker, selectedHour, selectedMinute) -> {
                    binding.enterCityName.editText.setText(String.format("%d:%d", selectedHour, selectedMinute));
                    isStartTimeFilled = true;
                    removeEditTextFocus();
                    handleAddButtonState();
                },
                hour, minute, true);
        mTimePicker.setTitle("Select Start Time");
        mTimePicker.show();
        removeEditTextFocus();
    }

    private void openEndTimeSelector() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTimingSlots.this,
                (timePicker, selectedHour, selectedMinute) -> {
                    binding.enterCityImage.editText.setText(String.format("%d:%d", selectedHour, selectedMinute));
                    isEndTimeFilled = true;
                    handleAddButtonState();
                },
                hour, minute, true);
        mTimePicker.setTitle("Select End Time");
        mTimePicker.show();
        removeEditTextFocus();
    }

    private void openDatePicker() {
        Calendar currentDate = Calendar.getInstance();
        int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(AddTimingSlots.this,
                (datepicker, selectedyear, selectedmonth, selectedday) -> {
                    selectedmonth = selectedmonth + 1;
                    binding.enterCost.editText.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    isDateFilled = true;
                    handleAddButtonState();
                }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
        removeEditTextFocus();
    }

    private void removeEditTextFocus() {
        binding.enterCityName.editText.clearFocus();
        binding.enterCityImage.editText.clearFocus();
        binding.enterCost.editText.clearFocus();
    }

    private void addSlot() {
        Intent intent = new Intent();
        intent.putExtra(ARG_START_TIME, binding.enterCityName.editText.getText().toString());
        intent.putExtra(ARG_END_TIME, binding.enterCityImage.editText.getText().toString());
        intent.putExtra(ARG_DATE, binding.enterCost.editText.getText().toString());
        intent.putExtra(ARG_SLOT_NUMBER, slotNumber);
        intent.putExtra(ARG_TOTAL_SEATS, binding.enterTotalSeats.editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
