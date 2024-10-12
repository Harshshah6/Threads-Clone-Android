package com.harsh.shah.threads.clone.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityMainBinding;
import com.harsh.shah.threads.clone.fragments.ActivityNotificationFragment;
import com.harsh.shah.threads.clone.fragments.AddThreadFragment;
import com.harsh.shah.threads.clone.fragments.HomeFragment;
import com.harsh.shah.threads.clone.fragments.ProfileFragment;
import com.harsh.shah.threads.clone.fragments.SearchFragment;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    int selectedFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setFragment(selectedFragment);
        setOnClickListeners();

//        ImageViewCompat.setImageTintList(binding.searchIcon, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.textSec)));
    }

    private void setOnClickListeners() {
        binding.homeIconLayout.setOnClickListener(v -> setFragment(0));
        binding.searchIconLayout.setOnClickListener(v -> setFragment(1));
        binding.addIconLayout.setOnClickListener(v -> setFragment(2));
        binding.favoriteIconLayout.setOnClickListener(v -> setFragment(3));
        binding.personIconLayout.setOnClickListener(v -> setFragment(4));
    }

    public void setFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        Fragment f = fragmentManager.findFragmentByTag("addThreadFragment");
        if(f != null && f.isVisible()){
            fragmentTransaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance("","")).commit();
        }

        if (selectedFragment == position)
            return;
        selectedFragment = position;
        if(position == 0)
            fragmentTransaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance("","")).commit();
        else if (position == 1)
            fragmentTransaction.replace(R.id.fragmentContainerView, SearchFragment.newInstance("","")).commit();
        else if(position == 2)
            fragmentTransaction.replace(R.id.fragmentContainerView, AddThreadFragment.newInstance("","")).commit();
        else if (position == 3)
            fragmentTransaction.replace(R.id.fragmentContainerView, ActivityNotificationFragment.newInstance("","")).commit();
        else if (position == 4)
            fragmentTransaction.replace(R.id.fragmentContainerView, ProfileFragment.newInstance("","")).commit();
    }

    @Override
    public void onBackPressed() {

        new MaterialAlertDialogBuilder(this)
                .setTitle("Are you sure?")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                .setNegativeButton("No", null)
                .create().show();
    }
}