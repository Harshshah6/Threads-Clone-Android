package com.harsh.shah.threads.clone.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityReplyToThreadBinding;
import com.harsh.shah.threads.clone.fragments.HomeFragment;
import com.harsh.shah.threads.clone.model.ThreadModel;
import com.harsh.shah.threads.clone.utils.MDialogUtil;
import com.harsh.shah.threads.clone.utils.Utils;

public class ReplyToThreadActivity extends BaseActivity {

    ActivityReplyToThreadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReplyToThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edittext.requestFocus();

        binding.imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.imagesRecyclerView.setAdapter(new HomeFragment.PostImagesListAdapter(false));

        if (getIntent().getExtras() == null || (getIntent().getExtras().getString("thread") == null))
            finish();

        mThreadsDatabaseReference.child(getIntent().getExtras().getString("thread")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ThreadModel threadModel = snapshot.getValue(ThreadModel.class);
                if (threadModel == null) {
                    finish();
                }
                setUpThreadView(threadModel == null ? new ThreadModel() : threadModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpThreadView(ThreadModel threadModel){
        binding.text.setText(threadModel.getText());
        binding.username.setText(threadModel.getUsername());
        binding.time.setText(Utils.calculateTimeDiff(Long.parseLong(threadModel.getTime())));

        binding.imagesRecyclerView.setAdapter(new HomeFragment.PostImagesListAdapter(threadModel.getImages(), false));

        if(threadModel.isIsPoll() && false) {
            binding.pollLayout.setVisibility(View.VISIBLE);
            binding.imagesRecyclerView.setVisibility(View.GONE);
        }
        else {
            binding.pollLayout.setVisibility(View.GONE);
            binding.imagesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void askAndPressback(View view) {
        if (binding.edittext.getText().toString().trim().isEmpty())
            //if ((adapter.getData() == null || adapter.getData().isEmpty())) {
        {
            onBackPressed();
            return;
        }

        MDialogUtil mDialogUtil = new MDialogUtil(this)
                .setTitle("Are you sure you want to exit?")
                .setMessage("", false);
        AlertDialog dialog = mDialogUtil.create();
        mDialogUtil.setB1("Exit", v -> {
            dialog.dismiss();
            onBackPressed();
        });
        mDialogUtil.setB2("Cancel", v -> dialog.dismiss());
        dialog.show();
    }
}