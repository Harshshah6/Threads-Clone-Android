package com.harsh.shah.threads.clone.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.activities.NewThreadActivity;
import com.harsh.shah.threads.clone.activities.ThreadViewActivity;
import com.harsh.shah.threads.clone.model.PollOptions;
import com.harsh.shah.threads.clone.model.ThreadModel;
import com.harsh.shah.threads.clone.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static HomeFragment instance;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<ThreadModel> data = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static Fragment getInstance() {
        if (instance == null)
            instance = new HomeFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshList();

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            //new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
            data.clear();
            refreshList();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void refreshList(){
        BaseActivity.mThreadsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ThreadModel threadModel = dataSnapshot.getValue(ThreadModel.class);
                    //Log.i("HomeFragment", "onDataChange: " + threadModel.profileImage());
                    if (threadModel != null) {
                        data.add(threadModel);
                        recyclerView.setAdapter(new Adapter(data));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setHeaderPos(TextView view, boolean isActivated) {
        if (isActivated) {
            TextViewCompat.setTextAppearance(view, R.style.Base_Widget_AppCompat_TextView_ButtonFilled);
            view.setBackgroundResource(R.drawable.button_background_filled);
        } else {
            TextViewCompat.setTextAppearance(view, R.style.Base_Widget_AppCompat_TextView_ButtonOutlined);
            view.setBackgroundResource(R.drawable.button_background_outlined);
        }
    }

    public static class PostImagesListAdapter extends RecyclerView.Adapter<PostImagesListAdapter.ViewHolder> {

        private boolean shouldHaveLeftPadding = true;
        private List<String> data = new ArrayList<>();

        public PostImagesListAdapter() {

        }

        public PostImagesListAdapter(List<String> data) {
            this.data = data;
        }

        public PostImagesListAdapter(List<String> data, boolean leftPadding) {
            this.data = data;
            this.shouldHaveLeftPadding = leftPadding;
        }

        public PostImagesListAdapter(boolean leftPadding) {
            this.shouldHaveLeftPadding = leftPadding;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item_image_item, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view, getItemCount());
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            int dp = ((int) (holder.itemView.getContext().getResources().getDisplayMetrics().density));
            params.setMargins((position == 0 && shouldHaveLeftPadding) ? dp * 60 : dp * 4, 0, dp * 4, dp*4);
            holder.itemView.setLayoutParams(params);
            ImageView imageView = holder.itemView.findViewById(R.id.imageView);
            if (!data.get(position).isEmpty()) {
                Picasso.get().load(data.get(position)).placeholder(R.drawable.photo_library_24px).into(imageView);
                setImage(Utils.getBitmapFromURL(data.get(position)), imageView);
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void setImage(Bitmap bitmap, ImageView imageView) {
            if (bitmap != null) {
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();
                int maxHeight = 200;

                float aspectRatio = (float) imageWidth / imageHeight;

                if (aspectRatio >= 1.5) {
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, maxHeight));
                    imageView.setAdjustViewBounds(false);
                } else if (aspectRatio <= 0.6) {
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(150, maxHeight));
                    imageView.setAdjustViewBounds(false);
                } else {
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(maxHeight, maxHeight));
                    imageView.setAdjustViewBounds(false);
                }

                imageView.setImageDrawable(new BitmapDrawable(imageView.getResources(), bitmap));
            }
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView, int itemCount) {
                super(itemView);
            }
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private ArrayList<ThreadModel> data = new ArrayList<>();

        public Adapter(ArrayList<ThreadModel> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = viewType == 1 ? LayoutInflater.from(getContext()).inflate(R.layout.main_head_layout, null) : LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (getItemViewType(position) == 1) {
                holder.itemView.setOnClickListener(view -> startActivity(new Intent(getContext(), NewThreadActivity.class)));
                ((TextView) holder.itemView.findViewById(R.id.username)).setText(BaseActivity.mUser.getUsername());
            }
            if (position == 0) return;
            final int newPosition = position - 1;

            ((TextView) holder.itemView.findViewById(R.id.username)).setText(data.get(newPosition).getUsername());

            final ShapeableImageView profileImage = holder.itemView.findViewById(R.id.profileImage);
            if (!data.get(newPosition).profileImage().isEmpty())
                Picasso.get().load(data.get(newPosition).profileImage()).placeholder(R.drawable.person_outline_24px).into(profileImage);

            ((TextView) holder.itemView.findViewById(R.id.time)).setText(Utils.calculateTimeDiff(Long.parseLong(data.get(newPosition).getTime())));
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(data.get(newPosition).getText());
            ((TextView) holder.itemView.findViewById(R.id.likes)).setText(String.valueOf(data.get(newPosition).getLikes().size()));
            ((TextView) holder.itemView.findViewById(R.id.comments)).setText(String.valueOf(data.get(newPosition).getComments().size()));
            ((TextView) holder.itemView.findViewById(R.id.reposts)).setText(String.valueOf(data.get(newPosition).getReposts().size()));

            //((RecyclerView) holder.itemView.findViewById(R.id.imagesListRecyclerView)).addItemDecoration(new MarginDecoration(holder.itemView.getContext(), 90, 24));
            ((RecyclerView) holder.itemView.findViewById(R.id.imagesListRecyclerView)).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            if (!data.get(newPosition).getImages().isEmpty())
                ((RecyclerView) holder.itemView.findViewById(R.id.imagesListRecyclerView)).setAdapter(new PostImagesListAdapter(data.get(newPosition).getImages()));

            if (data.get(newPosition).isIsPoll()) {
                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.GONE);

                ((TextView) holder.itemView.findViewById(R.id.poll_option_1)).setText(data.get(newPosition).getPollOptions().getOption1().getText());
                ((TextView) holder.itemView.findViewById(R.id.poll_option_2)).setText(data.get(newPosition).getPollOptions().getOption2().getText());
                if (data.get(newPosition).getPollOptions().getOption3().getVisibility()) {
                    ((TextView) holder.itemView.findViewById(R.id.poll_option_3)).setVisibility(View.VISIBLE);
                    ((TextView) holder.itemView.findViewById(R.id.poll_option_3)).setText(data.get(newPosition).getPollOptions().getOption3().getText());
                }
                if (data.get(newPosition).getPollOptions().getOption4().getVisibility()) {
                    ((TextView) holder.itemView.findViewById(R.id.poll_option_4)).setVisibility(View.VISIBLE);
                    ((TextView) holder.itemView.findViewById(R.id.poll_option_4)).setText(data.get(newPosition).getPollOptions().getOption4().getText());
                }
            } else
                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.GONE);

//            int rand = Utils.getRandomNumber(0, 2);
//            if (rand == 0) {
//                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.VISIBLE);
//                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.GONE);
//            }
//            if (rand == 1) {
//                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.GONE);
//                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.VISIBLE);
//            }
//            if (rand == 2) {
//                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.GONE);
//                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.GONE);
//            }
//            if (((TextView) holder.itemView.findViewById(R.id.poll_option_3_edittext)).getText().toString().isEmpty()) {
//                ((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).setVisibility(View.GONE);
//                ((TextView) holder.itemView.findViewById(R.id.poll_option_3_edittext)).setVisibility(View.GONE);
//            }
//            if (((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).getText().toString().isEmpty()) {
//                ((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).setVisibility(View.GONE);
//            } else
//                ((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(view -> startActivity(new Intent(getContext(), ThreadViewActivity.class).putExtra("thread", data.get(newPosition).getID())));

            holder.itemView.findViewById(R.id.poll_option_1).setOnClickListener(view -> {
                setHeaderPos((TextView) view, true);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_2), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_3), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_4), false);
            });
            holder.itemView.findViewById(R.id.poll_option_2).setOnClickListener(view -> {
                setHeaderPos((TextView) view, true);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_1), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_3), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_4), false);
            });
            holder.itemView.findViewById(R.id.poll_option_3).setOnClickListener(view -> {
                setHeaderPos((TextView) view, true);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_1), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_2), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_4), false);
            });
            holder.itemView.findViewById(R.id.poll_option_4).setOnClickListener(view -> {
                setHeaderPos((TextView) view, true);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_1), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_2), false);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_3), false);
            });
        }

        @Override
        public int getItemCount() {
            return data.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 1 : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}