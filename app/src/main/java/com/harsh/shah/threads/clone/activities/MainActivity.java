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

import java.util.ArrayList;

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
            if(position == 2)
                return;
        }

        if (selectedFragment == position)
            return;
        selectedFragment = position;
        if(position == 0)
            fragmentTransaction.replace(R.id.fragmentContainerView, HomeFragment.getInstance()).commit();
        else if (position == 1)
            fragmentTransaction.replace(R.id.fragmentContainerView, SearchFragment.getInstance()).commit();
        else if(position == 2)
            fragmentTransaction.replace(R.id.fragmentContainerView, AddThreadFragment.getInstance()).commit();
        else if (position == 3)
            fragmentTransaction.replace(R.id.fragmentContainerView, ActivityNotificationFragment.getInstance()).commit();
        else if (position == 4)
            fragmentTransaction.replace(R.id.fragmentContainerView, ProfileFragment.newInstance("","")).commit();

        binding.homeIcon.setColorFilter(getResources().getColor(position==0?R.color.textMain:R.color.textSec));
        binding.searchIcon.setColorFilter(getResources().getColor(position==1?R.color.textMain:R.color.textSec));
        binding.addIcon.setColorFilter(getResources().getColor(position==2?R.color.textMain:R.color.textSec));
        binding.favoriteIcon.setColorFilter(getResources().getColor(position==3?R.color.textMain:R.color.textSec));
        binding.personIcon.setColorFilter(getResources().getColor(position==4?R.color.textMain:R.color.textSec));

        binding.favoriteIcon.setImageResource(position == 3 ? R.drawable.favorite_24px : R.drawable.favorite_outline_24px);
        binding.personIcon.setImageResource(position == 4 ? R.drawable.person_24px : R.drawable.person_outline_24px);
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