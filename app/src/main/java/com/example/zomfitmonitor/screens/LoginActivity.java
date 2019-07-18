package com.example.zomfitmonitor.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityLoginBinding;
import com.example.zomfitmonitor.model.User;
import com.example.zomfitmonitor.model.login.LoginRequest;
import com.example.zomfitmonitor.model.login.LoginResponse;
import com.example.zomfitmonitor.network.ApiService;
import com.example.zomfitmonitor.screens.landing.MainActivity;
import com.example.zomfitmonitor.utils.BasicUtils;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static final int BASE_LENGTH = 0;
    private static final String ARG_USER = "user";
    private boolean isUsernameFilled;
    private boolean isPasswordFilled;
    private Retrofit retrofit;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        setUpEditTexts();
    }

    private void setUpEditTexts() {
        binding.enterPassword.editText.setHint(getString(R.string.password));
        binding.enterPassword.editText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.enterUsername.editText.setHint(getString(R.string.email_label));
        binding.enterPassword.editText.setTypeface(Typeface.create(
                "product_sans_regular", Typeface.NORMAL));
        setupEditTextListeners();
        setupLoginButtonClick();
    }

    private void setupEditTextListeners() {
        binding.enterUsername.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isUsernameFilled = charSequence.length() != BASE_LENGTH;
                handleLoginButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.enterPassword.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isPasswordFilled = charSequence.length() != BASE_LENGTH;
                handleLoginButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleLoginButtonState() {
        if (isUsernameFilled && isPasswordFilled) {
            binding.loginButton.setEnabled(true);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            binding.loginButton.setEnabled(false);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void setupLoginButtonClick() {
        binding.loginButton.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String email = binding.enterUsername.editText.getText().toString();
        String password = binding.enterPassword.editText.getText().toString();
        if (email.length() > BASE_LENGTH && password.length() > BASE_LENGTH) {
            loginWithServer(email, password);
        }
    }

    private void loginWithServer(String email, String password) {
        showLoadingView(true);
        Call<LoginResponse> responseCall = retrofit.create(ApiService.class)
                .login(new LoginRequest(email, password));
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().status) {
                    openHomeActivity(response.body().user);
                } else {
                    showLoadingView(false);
                    BasicUtils.makeToast(LoginActivity.this, getString(R.string.login_failed_label));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                BasicUtils.makeToast(LoginActivity.this, getString(R.string.an_error_occurred_label));
                showLoadingView(false);
            }
        });


    }

    private void showLoadingView(boolean show) {
        if (show) {
            binding.loginButton.setText("");
            binding.progressCircular.setVisibility(View.VISIBLE);
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        } else {
            binding.loginButton.setBackgroundColor(getResources().getColor(R.color.black));
            binding.loginButton.setText(getString(R.string.login));
            binding.progressCircular.setVisibility(View.GONE);
        }
    }

    private void openHomeActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, Parcels.wrap(user));
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
