package com.harsh.shah.threads.clone.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.databinding.ActivityAuthBinding;

public class AuthActivity extends BaseActivity {

    ActivityAuthBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.password.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                hideKeyboard(view);
            }
        });

        binding.password.setOnEditorActionListener((textView, i, keyEvent) -> {
            hideKeyboard(textView);
            binding.signIn.performClick();
            return false;
        });

        binding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usernameRegex = "^[a-zA-Z0-9._]{6,20}$";
                boolean isValid = s.toString().matches(usernameRegex);
                binding.username.setError(isValid ? null : "Contains invalid characters.");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        initOnClickListeners();
    }

    private void initOnClickListeners() {
        binding.signIn.setOnClickListener(view -> {
            showToast("Sign in clicked");
        });

        binding.loginRegister.setOnClickListener(view -> {
            binding.usernameLayout.setVisibility(binding.usernameLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            binding.loginRegister.setText(binding.loginRegister.getText().equals("Register") ? "Login" : "Register");
            binding.emailLayout.setHint(binding.loginRegister.getText().equals("Register") ? "Username or email" : "Email");
        });

    }


}