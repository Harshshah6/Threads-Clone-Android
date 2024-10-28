package com.harsh.shah.threads.clone.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.Constants;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityNewThreadBinding;
import com.harsh.shah.threads.clone.model.PollOptions;
import com.harsh.shah.threads.clone.model.ThreadModel;
import com.harsh.shah.threads.clone.utils.MDialogUtil;

import java.util.ArrayList;

public class NewThreadActivity extends BaseActivity {

    ActivityNewThreadBinding binding;
    ArrayList<String> data = new ArrayList<>();
    ImagesListAdapter adapter = new ImagesListAdapter(data, dataList -> {
        data = dataList;
        if (dataList.isEmpty()) {
            binding.insertPoll.setVisibility(View.VISIBLE);
            binding.insertGif.setVisibility(View.VISIBLE);
        } else {
            binding.insertPoll.setVisibility(View.GONE);
            binding.insertGif.setVisibility(View.GONE);
        }
    });
    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), o -> {
        if (o == null) {
            Toast.makeText(NewThreadActivity.this, "No image Selected", Toast.LENGTH_SHORT).show();
        } else {
            for (Uri uri : o) {
                adapter.addData(uri.toString());
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edittext.requestFocus();
        binding.insertImage.setOnClickListener(view -> {
            if (adapter.getData().size() == 5)
                return;
            launcher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        binding.insertPoll.setOnClickListener(view -> {
            if(true)return;
            binding.pollLayout.setVisibility(View.VISIBLE);
            binding.constraintLayout2.setVisibility(View.GONE);
        });
        binding.pollRemove.setOnClickListener(view -> {
            binding.pollLayout.setVisibility(View.GONE);
            binding.constraintLayout2.setVisibility(View.VISIBLE);
        });

        binding.pollOption3Edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().isEmpty()) {
                    binding.pollOption4Edittext.setVisibility(View.GONE);
                } else {
                    binding.pollOption4Edittext.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.postButton.setOnClickListener(view -> {
            DatabaseReference databaseReference = mDatabase.getReference(Constants.THREADS).push();
            databaseReference.setValue(new ThreadModel(
                    adapter.getData(),
                    new ArrayList<>(),
                    true,
                    false,
                    binding.pollLayout.getVisibility() == View.VISIBLE,
                    new ArrayList<>(),
                    mUser.getUid(),
                    "",
                    databaseReference.getKey(),
                    binding.edittext.getText().toString().trim(),
                    System.currentTimeMillis() + "",
                    new PollOptions(
                            new PollOptions.PollOptionsItem(new ArrayList<>(), binding.pollOption1Edittext.getText().toString().trim(), true),
                            new PollOptions.PollOptionsItem(new ArrayList<>(), binding.pollOption2Edittext.getText().toString().trim(), true),
                            new PollOptions.PollOptionsItem(new ArrayList<>(), binding.pollOption3Edittext.getText().toString().trim(), !binding.pollOption3Edittext.getText().toString().trim().isEmpty()),
                            new PollOptions.PollOptionsItem(new ArrayList<>(), binding.pollOption4Edittext.getText().toString().trim(), !binding.pollOption4Edittext.getText().toString().trim().isEmpty())
                    ),
                    new ArrayList<>(),
                    (mUser.getProfileImage()==null||mUser.getProfileImage().isEmpty())?"":mUser.getProfileImage(),
                    mUser.getUsername(),
                    new ArrayList<>()
            )).addOnCompleteListener(task ->{
                if (task.isSuccessful()){
                    finish();
                }else
                    showToast(task.getException()==null?"An Error Occurred While Uploading Thread.":task.getException().toString());
            });
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    public void askAndPressback(View view) {

        MDialogUtil mDialogUtil = new MDialogUtil(this)
                .setTitle("Are you sure you want to exit?")
                .setMessage("", false);
        AlertDialog dialog = mDialogUtil.create();
        mDialogUtil.setB1("Exit", v -> {
            dialog.dismiss();
            finish();
        });
        mDialogUtil.setB2("Cancel", v -> dialog.dismiss());
        dialog.show();
    }


    interface dataChangeListener {
        void onChanged(ArrayList<String> data);
    }

    static class ImagesListAdapter extends RecyclerView.Adapter<ImagesListAdapter.ViewHolder> {

        private final ArrayList<String> data;
        private final dataChangeListener dataChangeListener;

        public ImagesListAdapter(ArrayList<String> data, dataChangeListener listener) {
            this.data = data;
            this.dataChangeListener = listener;
        }

        public ArrayList<String> getData() {
            return data;
        }

        public void setData(ArrayList<String> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
            dataChangeListener.onChanged(this.data);
        }

        public void addData(String data) {
            if (this.data.size() == 5) {
                return;
            }
            this.data.add(data);
            notifyItemInserted(this.data.size());
            dataChangeListener.onChanged(this.data);
        }

        public void removeData(int position) {
            this.data.remove(position);
            notifyItemRemoved(position);
            dataChangeListener.onChanged(this.data);
        }

        public void clearData() {
            this.data.clear();
            notifyDataSetChanged();
            dataChangeListener.onChanged(this.data);
        }

        public void updateData(int position, String data) {
            this.data.set(position, data);
            notifyItemChanged(position);
            dataChangeListener.onChanged(this.data);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.layout_images_horizontal, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            int dp = ((int) (holder.itemView.getContext().getResources().getDisplayMetrics().density));
            if (position == 0) {
                params.setMargins(dp * 100, dp * 8, dp * 8, dp * 8);
            } else {
                params.setMargins(dp * 8, dp * 8, dp * 8, dp * 8);
            }
            holder.shapeableImageView.setLayoutParams(params);

            holder.delete.setOnClickListener(view -> removeData(position));
            Uri uri = Uri.parse(data.get(position));
            holder.shapeableImageView.setImageURI(uri);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            public ShapeableImageView shapeableImageView, delete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                shapeableImageView = itemView.findViewById(R.id.shapeableImageView);
                delete = itemView.findViewById(R.id.delete);
            }
        }
    }
}