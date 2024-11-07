package com.harsh.shah.threads.clone.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityNewThreadBinding;
import com.harsh.shah.threads.clone.model.PollOptions;
import com.harsh.shah.threads.clone.model.ThreadModel;
import com.harsh.shah.threads.clone.utils.MDialogUtil;
import com.harsh.shah.threads.clone.utils.Utils;

import java.io.File;
import java.util.ArrayList;

public class NewThreadActivity extends BaseActivity {

    ActivityNewThreadBinding binding;
    ArrayList<String> data = new ArrayList<>();
    ImagesListAdapter adapter = new ImagesListAdapter(data, (listener, dataList) -> {
        data = dataList;
        //binding.recyclerView.setAdapter(new ImagesListAdapter(data, listener));
//        if (dataList.isEmpty()) {
//            binding.insertPoll.setVisibility(View.VISIBLE);
//            binding.insertGif.setVisibility(View.VISIBLE);
//        } else {
//            binding.insertPoll.setVisibility(View.GONE);
//            binding.insertGif.setVisibility(View.GONE);
//        }
    });
    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), o -> {
        if (o == null) {
            Toast.makeText(NewThreadActivity.this, "No image Selected", Toast.LENGTH_SHORT).show();
        } else {
            for (Uri uri : o) {
//                adapter.addData(uri.toString());
                if (data.size() < 6) {
                    //adapter.addData(uri.toString());

                    File inputFile = new File(uri.getPath());
                    File outputFile = new File(inputFile.getPath().replace(inputFile.getName(), "compressed_"+ inputFile.getName()));

                    data.add(uri.toString());
                    binding.recyclerView.setAdapter(new ImagesListAdapter(data));
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edittext.requestFocus();

        binding.textView.setText(mUser.getUsername());

        binding.insertImage.setOnClickListener(view -> {
            if (data.size() == 5)
                return;
            launcher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        binding.insertPoll.setOnClickListener(view -> {
            if (true) return;
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
            if (binding.edittext.getText().toString().trim().isEmpty())
                if ((adapter.getData() == null || adapter.getData().isEmpty()))
                    return;

            postThread();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //binding.recyclerView.setAdapter(adapter);
    }

    private void postThread() {
        final String pid = mThreadsDatabaseReference.push().getKey();

        ThreadModel threadModel = new ThreadModel(
                adapter.getData(),
                new ArrayList<>(),
                true,
                false,
                false,
                new ArrayList<>(),
                mUser.getUid(),
                "",
                pid,
                binding.edittext.getText().toString(),
                Utils.getNowInMillis() + "",
                new PollOptions(new PollOptions.PollOptionsItem(new ArrayList<>(), "", false), new PollOptions.PollOptionsItem(new ArrayList<>(), "", false), new PollOptions.PollOptionsItem(new ArrayList<>(), "", false), new PollOptions.PollOptionsItem(new ArrayList<>(), "", false)),
                new ArrayList<>(),
                mUser.getProfileImage(),
                mUser.getUsername(),
                new ArrayList<>()
        );
        showProgressDialog();
        mThreadsDatabaseReference.child(pid).setValue(threadModel).addOnCompleteListener(task -> {
            hideProgressDialog();
            if(!task.isSuccessful()) {
                showToast(task.getException().toString());
            }
            finish();
        });
    }

    public void askAndPressback(View view) {
        if (binding.edittext.getText().toString().trim().isEmpty())
            if ((adapter.getData() == null || adapter.getData().isEmpty())) {
                finish();
                return;
            }

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
        void onChanged(dataChangeListener dataChangeListener, ArrayList<String> data);
    }

    static class ImagesListAdapter extends RecyclerView.Adapter<ImagesListAdapter.ViewHolder> {

        private final ArrayList<String> data;
        private final dataChangeListener dataChangeListener;

        public ImagesListAdapter(ArrayList<String> data) {
            this.data = data;
            this.dataChangeListener = (listener, data1) -> {
            };
        }

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
            dataChangeListener.onChanged(this.dataChangeListener, this.data);
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

            holder.delete.setOnClickListener(view -> {
//                removeData(position)
                this.data.remove(position);
                dataChangeListener.onChanged(this.dataChangeListener, this.data);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            });
            Uri uri = Uri.parse(data.get(position));
            holder.shapeableImageView.setImageURI(uri);
        }

        public void addData(String data) {
            if (this.data.size() == 5) {
                return;
            }
            this.data.add(data);
            notifyItemInserted(this.data.size());
            dataChangeListener.onChanged(this.dataChangeListener, this.data);
        }

        public void removeData(int position) {
            this.data.remove(position);
            notifyItemRemoved(position);
            dataChangeListener.onChanged(this.dataChangeListener, this.data);
        }

        public void clearData() {
            this.data.clear();
            notifyDataSetChanged();
            dataChangeListener.onChanged(this.dataChangeListener, this.data);

        }

        public void updateData(int position, String data) {
            this.data.set(position, data);
            notifyItemChanged(position);
            dataChangeListener.onChanged(this.dataChangeListener, this.data);

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