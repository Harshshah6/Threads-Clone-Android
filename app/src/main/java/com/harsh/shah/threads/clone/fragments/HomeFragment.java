package com.harsh.shah.threads.clone.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.activities.NewThreadActivity;
import com.harsh.shah.threads.clone.activities.ThreadViewActivity;
import com.harsh.shah.threads.clone.utils.Utils;

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
        recyclerView.setAdapter(new Adapter());
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000));
    }

    public static class PostImagesListAdapter extends RecyclerView.Adapter<PostImagesListAdapter.ViewHolder> {

        private boolean shouldHaveLeftPadding = true;

        public PostImagesListAdapter() {

        }

        public PostImagesListAdapter(boolean leftPadding) {
            this.shouldHaveLeftPadding = leftPadding;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item_image_item, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new PostImagesListAdapter.ViewHolder(view,  getItemCount());
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            int dp = ((int) (holder.itemView.getContext().getResources().getDisplayMetrics().density));
            params.setMargins((position==0 && shouldHaveLeftPadding)?dp*90:dp*12, 0, dp * 12, 0);
            holder.itemView.setLayoutParams(params);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView, int itemCount) {
                super(itemView);
            }
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = viewType == 1 ? LayoutInflater.from(getContext()).inflate(R.layout.main_head_layout, null) : LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (getItemViewType(position) == 1)
                holder.itemView.setOnClickListener(view -> startActivity(new Intent(getContext(), NewThreadActivity.class)));
            if (position == 0) return;

            //((RecyclerView) holder.itemView.findViewById(R.id.imagesListRecyclerView)).addItemDecoration(new MarginDecoration(holder.itemView.getContext(), 90, 24));
            ((RecyclerView) holder.itemView.findViewById(R.id.imagesListRecyclerView)).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            ((RecyclerView) holder.itemView.findViewById(R.id.imagesListRecyclerView)).setAdapter(new PostImagesListAdapter());
            int rand = Utils.getRandomNumber(0, 2);
            if (rand == 0) {
                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.GONE);
            }
            if (rand == 1) {
                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.VISIBLE);
            }
            if (rand == 2) {
                holder.itemView.findViewById(R.id.imagesListRecyclerView).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.poll_layout).setVisibility(View.GONE);
            }
            if (((TextView) holder.itemView.findViewById(R.id.poll_option_3_edittext)).getText().toString().isEmpty()) {
                ((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).setVisibility(View.GONE);
                ((TextView) holder.itemView.findViewById(R.id.poll_option_3_edittext)).setVisibility(View.GONE);
            }
            if (((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).getText().toString().isEmpty()) {
                ((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).setVisibility(View.GONE);
            } else
                ((TextView) holder.itemView.findViewById(R.id.poll_option_4_edittext)).setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(view -> startActivity(new Intent(getContext(), ThreadViewActivity.class)));

            holder.itemView.findViewById(R.id.poll_option_1_edittext).setOnClickListener(view -> {
                setHeaderPos((TextView) view, true);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_2_edittext), false);
            });
            holder.itemView.findViewById(R.id.poll_option_2_edittext).setOnClickListener(view -> {
                setHeaderPos((TextView) view, true);
                setHeaderPos((TextView) holder.itemView.findViewById(R.id.poll_option_1_edittext), false);
            });

        }

        @Override
        public int getItemCount() {
            return 20;
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
    private void setHeaderPos(TextView view, boolean isActivated){
        if (isActivated){
            TextViewCompat.setTextAppearance(view, R.style.Base_Widget_AppCompat_TextView_ButtonFilled);
            view.setBackgroundResource(R.drawable.button_background_filled);
        }
        else{
            TextViewCompat.setTextAppearance(view, R.style.Base_Widget_AppCompat_TextView_ButtonOutlined);
            view.setBackgroundResource(R.drawable.button_background_outlined);
        }
    }
}