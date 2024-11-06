package com.harsh.shah.threads.clone.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.database.StorageDatabase;

import io.appwrite.models.InputFile;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getUsersDatabase(new AuthListener() {
            @Override
            public void onAuthTaskStart() {

            }

            @Override
            public void onAuthSuccess(DataSnapshot snapshot) {
                nextScreen();
            }

            @Override
            public void onAuthFail(DatabaseError error) {
                showToast(error.getMessage());
                finishAffinity();
            }
        });

        StorageDatabase.INSTANCE.create(this);
        StorageDatabase.INSTANCE.uploadFile(InputFile.Companion.fromBytes(new byte[]{},"temp","image/png"));
        StorageDatabase.INSTANCE.downloadFile("672b82950007fbde1497");

    }

    private void nextScreen(){
        new Handler().postDelayed(() -> {
            Pair<View, String> p1 = Pair.create(findViewById(R.id.imageView), "splash_image");
            Intent intent = new Intent(SplashActivity.this, isUserLoggedIn() ? MainActivity.class : AuthActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, p1);
            startActivity(intent, options.toBundle());
        }, 3000);

        new Handler().postDelayed(this::finish, 6000);
    }
}