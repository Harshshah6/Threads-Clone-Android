package com.harsh.shah.threads.clone.activities;

import android.content.Intent;
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
import com.harsh.shah.threads.clone.utils.Utils;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    int selectedFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClickListeners();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
            if (fragment != null) {
                if (fragment instanceof HomeFragment) {
                    setFragmentIcon(0);
                }
                if (fragment instanceof SearchFragment) {
                    setFragmentIcon(1);
                }
                if (fragment instanceof ActivityNotificationFragment) {
                    setFragmentIcon(2);
                }
                if (fragment instanceof ProfileFragment) {
                    setFragmentIcon(3);
                }
            }
        });
        setFragment(selectedFragment);


//        ImageViewCompat.setImageTintList(binding.searchIcon, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.textSec)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.hideKeyboard(this);
    }

    @Override
    protected void onResume() {
        try{
            super.onResume();
            Utils.hideKeyboard(this);
        }catch (Exception ignored){}
    }

    private void setOnClickListeners() {
        binding.homeIconLayout.setOnClickListener(v -> setFragment(0));
        binding.searchIconLayout.setOnClickListener(v -> setFragment(1));
        binding.addIconLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NewThreadActivity.class)));
        binding.favoriteIconLayout.setOnClickListener(v -> setFragment(2));
        binding.personIconLayout.setOnClickListener(v -> setFragment(3));
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        selectedFragment = position;

        if (position == 0) {
            fragmentManager.popBackStack("root", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            FragmentManager fm = getSupportFragmentManager();
//            int count = fm.getBackStackEntryCount();
//            for(int i = 0; i < count; ++i) {
//                fm.popBackStack();
//            }
            fragmentTransaction.addToBackStack("root");
            fragmentTransaction.add(R.id.fragmentContainerView, HomeFragment.getInstance());
            fragmentTransaction.commit();
        } else if (position == 1)
            fragmentTransaction.replace(R.id.fragmentContainerView, SearchFragment.getInstance()).addToBackStack(null).commit();
//        else if(position == 2)
//            fragmentTransaction.replace(R.id.fragmentContainerView, AddThreadFragment.getInstance()).addToBackStack(null).commit();
        else if (position == 2)
            fragmentTransaction.replace(R.id.fragmentContainerView, ActivityNotificationFragment.getInstance()).addToBackStack(null).commit();
        else if (position == 3)
            fragmentTransaction.replace(R.id.fragmentContainerView, ProfileFragment.newInstance("", "")).addToBackStack(null).commit();

        setFragmentIcon(position);
    }

    private void setFragmentIcon(int position){
        binding.homeIcon.setColorFilter(getResources().getColor(position == 0 ? R.color.textMain : R.color.textSec));

        binding.searchIcon.setColorFilter(getResources().getColor(position == 1 ? R.color.textMain : R.color.textSec));
        //binding.addIcon.setColorFilter(getResources().getColor(position==2?R.color.textMain:R.color.textSec));
        binding.favoriteIcon.setColorFilter(getResources().getColor(position == 2 ? R.color.textMain : R.color.textSec));
        binding.personIcon.setColorFilter(getResources().getColor(position == 3 ? R.color.textMain : R.color.textSec));

        binding.homeIcon.setImageResource(position == 0 ? R.drawable.home_24px : R.drawable.home_24px_outline);
        binding.favoriteIcon.setImageResource(position == 2 ? R.drawable.favorite_24px : R.drawable.favorite_outline_24px);
        binding.personIcon.setImageResource(position == 3 ? R.drawable.person_24px : R.drawable.person_outline_24px);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("No", null)
                    .create().show();
            return;
        }
        super.onBackPressed();
    }
}