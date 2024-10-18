package com.harsh.shah.threads.clone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harsh.shah.threads.clone.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityNotificationFragment extends Fragment {

    private  static  ActivityNotificationFragment instance;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView chip_all;
    TextView chip_requests;
    TextView chip_replies;
    TextView chip_mentions;
    TextView chip_quotes;
    TextView chip_reposts;

    public ActivityNotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityNotificationFragment newInstance(String param1, String param2) {
        ActivityNotificationFragment fragment = new ActivityNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ActivityNotificationFragment getInstance() {
        if (instance == null) {
            instance = new ActivityNotificationFragment();
        }
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
        return inflater.inflate(R.layout.fragment_activity_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chip_all = view.findViewById(R.id.chip_all);
        chip_requests = view.findViewById(R.id.chip_requests);
        chip_replies = view.findViewById(R.id.chip_replies);
        chip_mentions = view.findViewById(R.id.chip_mentions);
        chip_quotes = view.findViewById(R.id.chip_quotes);
        chip_reposts = view.findViewById(R.id.chip_reposts);

        chip_all.setOnClickListener(view1 -> setPosition(0));
        chip_requests.setOnClickListener(view1 -> setPosition(1));
        chip_replies.setOnClickListener(view1 -> setPosition(2));
        chip_mentions.setOnClickListener(view1 -> setPosition(3));
        chip_quotes.setOnClickListener(view1 -> setPosition(4));
        chip_reposts.setOnClickListener(view1 -> setPosition(5));

        setPosition(0);

    }

    private void setPosition(int position){
        chip_all.setBackgroundResource(R.drawable.button_background_outlined);
        chip_requests.setBackgroundResource(R.drawable.button_background_outlined);
        chip_replies.setBackgroundResource(R.drawable.button_background_outlined);
        chip_mentions.setBackgroundResource(R.drawable.button_background_outlined);
        chip_quotes.setBackgroundResource(R.drawable.button_background_outlined);
        chip_reposts.setBackgroundResource(R.drawable.button_background_outlined);

        if(position == 0){
            chip_all.setBackgroundResource(R.drawable.button_background_filled);
        }
        else if(position == 1){
            chip_requests.setBackgroundResource(R.drawable.button_background_filled);
        }
        else if(position == 2){
            chip_replies.setBackgroundResource(R.drawable.button_background_filled);
        }
        else if(position == 3){
            chip_mentions.setBackgroundResource(R.drawable.button_background_filled);
        }
        else if(position == 4){
            chip_quotes.setBackgroundResource(R.drawable.button_background_filled);
        }
        else if(position == 5){
            chip_reposts.setBackgroundResource(R.drawable.button_background_filled);
        }

    }
}