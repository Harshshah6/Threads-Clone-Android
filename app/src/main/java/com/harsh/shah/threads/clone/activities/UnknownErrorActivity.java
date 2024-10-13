package com.harsh.shah.threads.clone.activities;

import android.os.Bundle;
import android.view.View;

import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.databinding.ActivityUnknownErrorBinding;

public class UnknownErrorActivity extends BaseActivity {

    ActivityUnknownErrorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnknownErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().hasExtra("error")){
            binding.toolbarTitle.setText(getIntent().getStringExtra("error"));
        }

    }

    public void pressBack(View view) {
        finish();
    }
}