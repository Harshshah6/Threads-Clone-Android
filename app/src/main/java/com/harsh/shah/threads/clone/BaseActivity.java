package com.harsh.shah.threads.clone;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harsh.shah.threads.clone.activities.ProfileActivity;
import com.harsh.shah.threads.clone.model.User;

import java.util.concurrent.CountDownLatch;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    public FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;
    public DatabaseReference mUsersDatabaseReference;
    public DatabaseReference gUsernamesDatabaseReference;
    public GoogleSignInOptions googleSignInOptions;
    public GoogleSignInClient googleSignInClient;
    public AlertDialog progressDialog;

    public OnCompleteListener<AuthResult> authResultTask = task -> {
        if (!task.isSuccessful()) {
            showToast("Error: " + task.getException());
            return;
        }

        loginTask(task);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(isNightMode()){
                //icon color -> white
                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
            }else{
                //icon color -> black
                getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
            }
        }


        setContentView(R.layout.activity_base);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mDatabase.getReference(Constants.UsersDBReference);
        gUsernamesDatabaseReference = mDatabase.getReference(Constants.GUsernamesDBReference);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.webApplicationID)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(BaseActivity.this, googleSignInOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22) {
            showProgressDialog();
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(BaseActivity.this, task -> {
                            if (!task.isSuccessful()) {
                                Log.e(TAG, "onActivityResult: ", task.getException());
                                showToast("Error: " + task.getException());
                                return;
                            }
                            mUsersDatabaseReference.child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User mUser = snapshot.getValue(User.class);
                                    if (mUser == null) {
                                        User user = new User(
                                                ""+task.getResult().getUser().getUid(),
                                                "" + task.getResult().getUser().getPhotoUrl(),
                                                "google:google",
                                                "public",
                                                "" + task.getResult().getUser().getDisplayName(),
                                                "google",
                                                "",
                                                "" + task.getResult().getUser().getEmail(),
                                                "" +(task.getResult().getUser().getDisplayName().trim().replace(" ", "_"))
                                        );
                                        gUsernamesDatabaseReference.child(user.getUsername()).setValue(task.getResult().getUser().getUid()).isComplete();
                                        mUsersDatabaseReference.child(task.getResult().getUser().getUid()).setValue(user).addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                                                finish();
                                            } else {
                                                Log.e(TAG, "onDataChange: ", task1.getException());
                                                showToast("Error: " + task1.getException());
                                            }
                                        });
                                    } else {
                                        startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        });
                    }
                    hideProgressDialog();
                } catch (ApiException e) {
                    hideProgressDialog();
                    Log.e(TAG, "onActivityResult: ", e);
                }
            }
            hideProgressDialog();
        }
    }

    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public void logoutUser() {
        mAuth.signOut();
        try {
            googleSignInClient.signOut();
        } catch (Exception e) {
            Log.e(TAG, "logoutUser(): ", e);
        }
    }

    public void getUsersDatabase(AuthListener authListener) {
        authListener.onAuthTaskStart();
        mUsersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                authListener.onAuthSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                authListener.onAuthFail(error);
            }
        });
        //mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(authResultTask);
    }

    public void getDatabase(String path, AuthListener authListener){
        authListener.onAuthTaskStart();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                authListener.onAuthSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                authListener.onAuthFail(error);
            }
        });
    }

    public void loginTask(Task<AuthResult> task) {
        Log.i(TAG, "loginTask: " + task.getResult().getUser().getDisplayName());
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = getProgressDialogInit();
        }
        try {
            progressDialog.show();
        } catch (Exception ignored) {
        }
    }

    public void hideProgressDialog() {
        try {
            progressDialog.dismiss();
            progressDialog = null;
        } catch (Exception ignored) {
        }
    }

    private AlertDialog getProgressDialogInit() {
        return new MaterialAlertDialogBuilder(this).setView(R.layout.progress_dialog).setCancelable(false).setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT)).create();
    }

    public static interface AuthListener {
        void onAuthTaskStart();

        void onAuthSuccess(DataSnapshot snapshot);

        void onAuthFail(DatabaseError error);
    }

    public static interface UserQueryListener {
        boolean isUsernameAvailable(String username);

    }

    public boolean isNightMode() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}