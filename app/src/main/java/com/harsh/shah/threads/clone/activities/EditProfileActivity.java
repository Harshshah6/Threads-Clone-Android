package com.harsh.shah.threads.clone.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityEditProfileBinding;
import com.suke.widget.SwitchButton;

public class EditProfileActivity extends BaseActivity {

    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.visibilityLayout.setOnClickListener(v -> binding.switchButton.toggle());
        binding.switchButton.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                binding.textView13.setText("Private profiles can only reply to their followers. Switch to public to reply to anyone.");
            }else{
                binding.textView13.setText("If you switch to private, you won't be able to reply to others unless they follow you.");
            }
        });

        binding.textView7.setOnClickListener(view -> {
            Snackbar.make(binding.getRoot(), "Cannot change the username until 30 days.", Snackbar.LENGTH_SHORT).show();
        });
    }
}