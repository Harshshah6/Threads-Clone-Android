package com.harsh.shah.threads.clone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.Constants;
import com.harsh.shah.threads.clone.databinding.ActivityAuthBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    ActivityAuthBinding binding;
    private boolean isModeRegister = false;

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

        addTextWatchers();
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        binding.signIn.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();
            String username = binding.username.getText().toString().trim();

            if (isModeRegister) {
                if (binding.usernameLayout.getError() == null && binding.emailLayout.getError() == null && binding.passwordLayout.getError() == null) {
                    createNewUser(email, username, password);
                    if(true)
                        return;
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(authResultTask);
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username).build();
                    mAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
                }
            } else {
                if (binding.emailLayout.getError() == null && binding.passwordLayout.getError() == null) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authResultTask);
                }
            }
        });

        binding.loginRegister.setOnClickListener(view -> {
            binding.usernameLayout.setVisibility(binding.usernameLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            binding.loginRegister.setText(binding.loginRegister.getText().equals("Register") ? "Login" : "Register");
            binding.signIn.setText(binding.loginRegister.getText().equals("Register") ? "Log in" : "Sign up");
            binding.emailLayout.setHint(binding.loginRegister.getText().equals("Register") ? "Username or email" : "Email");
            isModeRegister = !binding.loginRegister.getText().equals("Register");
        });

        binding.loginWithGoogle.setOnClickListener(view -> {
            startActivityForResult(googleSignInClient.getSignInIntent(), 22);

        });

    }


    private void addTextWatchers() {

        binding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateUsername(s.toString());
            }

            private boolean isUsernameValid(String username) {
                String usernameRegex = "^[a-zA-Z0-9._]{6,20}$";
                return username.matches(usernameRegex);
            }

            private void validateUsername(String username) {
                if (isUsernameValid(username))
                    binding.usernameLayout.setError(null);
                else if (username.trim().length() > 20 || username.trim().length() < 6)
                    binding.usernameLayout.setError("Username must be between 6 and 20 characters.");
                else if (username.contains(" "))
                    binding.usernameLayout.setError("Username should not contain spaces.");
                else
                    binding.usernameLayout.setError("Contains invalid characters.");
            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() < 6)
                    binding.passwordLayout.setError("Password must be at least 6 characters.");
                else
                    binding.passwordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.email.addTextChangedListener(new TextWatcher() {
            public final Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isModeRegister) {
                    if (!validate(charSequence.toString()))
                        binding.emailLayout.setError("Invalid email address.");
                    else
                        binding.emailLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            public boolean validate(String emailStr) {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
                return matcher.matches();
            }
        });
    }

    private void successLogin() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}