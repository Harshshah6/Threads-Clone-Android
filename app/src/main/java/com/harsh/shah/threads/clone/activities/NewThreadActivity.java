package com.harsh.shah.threads.clone.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.databinding.ActivityNewThreadBinding;

import java.util.ArrayList;

public class NewThreadActivity extends BaseActivity {

    ActivityNewThreadBinding binding;
    ArrayList<String> data = new ArrayList<>();
    ImagesListAdapter adapter = new ImagesListAdapter(data, dataList ->{
        if(!dataList.isEmpty()) {
            binding.pollLayout.setVisibility(View.VISIBLE);
            binding.insertGif.setVisibility(View.VISIBLE);
        }
        else {
            binding.pollLayout.setVisibility(View.GONE);
            binding.insertGif.setVisibility(View.GONE);
        }
    });
    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), o -> {
        if (o == null) {
            Toast.makeText(NewThreadActivity.this, "No image Selected", Toast.LENGTH_SHORT).show();
        } else {
            //Glide.with(getApplicationContext()).load(o).into(imageView);
            //adapter.addData(o.toString());
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
            if(adapter.getData().size() == 5)
                return;
            launcher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        binding.insertPoll.setOnClickListener(view -> {
            binding.pollLayout.setVisibility(View.VISIBLE);
            binding.constraintLayout2.setVisibility(View.GONE);
        });
        binding.pollRemove.setOnClickListener(view -> {
            binding.pollLayout.setVisibility(View.GONE);
            binding.constraintLayout2.setVisibility(View.VISIBLE);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setAdapter(adapter);
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
            notifyItemInserted(this.data.size() - 1);
            dataChangeListener.onChanged(this.data);
        }

        public void removeData(String data) {
            int index = this.data.indexOf(data);
            this.data.remove(data);
            notifyItemRemoved(index);
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
                params.setMargins(dp*100,dp*8,dp*8,dp*8);
            }else{
                params.setMargins(dp*8,dp*8,dp*8,dp*8);
            }
            holder.shapeableImageView.setLayoutParams(params);

            holder.delete.setOnClickListener(view -> removeData(data.get(position)));
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

    interface dataChangeListener {
        void onChanged(ArrayList<String> data);
    }
}