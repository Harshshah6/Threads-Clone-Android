package com.harsh.shah.threads.clone.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.internal.EdgeToEdgeUtils;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.textSec), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.addIcon.setColorFilter(ContextCompat.getColor(this, R.color.textSec), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.favoriteIcon.setColorFilter(ContextCompat.getColor(this, R.color.textSec), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.personIcon.setColorFilter(ContextCompat.getColor(this, R.color.textSec), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}