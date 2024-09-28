package com.harsh.shah.threads.clone;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.harsh.shah.threads.clone.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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

        initOnClickListeners();
    }

    private void initOnClickListeners() {
        binding.signIn.setOnClickListener(view -> {
            Toast.makeText(AuthActivity.this, "Sign In", Toast.LENGTH_SHORT).show();
        });

        binding.loginRegister.setOnClickListener(view -> {
            binding.usernameLayout.setVisibility(binding.usernameLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            binding.loginRegister.setText(binding.loginRegister.getText().equals("Register") ? "Login" : "Register");
            binding.emailLayout.setHint(binding.loginRegister.getText().equals("Register") ? "Username or email" : "Email");
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}