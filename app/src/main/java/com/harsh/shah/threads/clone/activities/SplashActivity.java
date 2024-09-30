package com.harsh.shah.threads.clone.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;

import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Pair<View, String> p1 = Pair.create(findViewById(R.id.imageView), "splash_image");
            Intent intent = new Intent(SplashActivity.this, mAuth.getCurrentUser() == null ? AuthActivity.class : ProfileActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, p1);
            startActivity(intent, options.toBundle());
            finish();
        }, 3000);


    }
}