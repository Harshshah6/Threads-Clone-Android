package com.harsh.shah.threads.clone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.harsh.shah.threads.clone.R;
import com.harsh.shah.threads.clone.activities.EditProfileActivity;
import com.harsh.shah.threads.clone.activities.SettingsActivity;
import com.harsh.shah.threads.clone.activities.settings.PrivacyActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static ProfileFragment instance;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProfileFragment getInstance() {
        if (instance == null) {
            instance = new ProfileFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView segmentImage = view.findViewById(R.id.segmentImage);
        segmentImage.setOnClickListener(view1 -> startActivity(new Intent(getContext(), SettingsActivity.class)));

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(position==0?"Threads":position==1?"Replies":"Reposts")));
        viewPager.setAdapter(new PageAdapter(this));
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        initOnClickListeners(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void initOnClickListeners(View view){
        view.findViewById(R.id.edit_profile).setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        view.findViewById(R.id.lockImage).setOnClickListener(v-> startActivity(new Intent(getContext(), PrivacyActivity.class)));

    }

    static class PageAdapter extends FragmentStateAdapter {

        public PageAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }



        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position==0?ObjectFragment.newInstance():position==1?Object1Fragment.newInstance(null):Object1Fragment.newInstance("You haven't reposted any threads yet.");
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public static class ObjectFragment extends Fragment {
//        private static ObjectFragment instance;
//        public static ObjectFragment newInstance() {
//            if(instance==null)
//                instance = new ObjectFragment();
//            return instance;
//        }

        public ObjectFragment() {

        }
        public static ObjectFragment newInstance() {
            return new ObjectFragment();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.profile_setup_view, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }
    }

    public static class Object1Fragment extends Fragment {

        private static Object1Fragment instance;

        public Object1Fragment() {

        }

//        public static Object1Fragment newInstance(String arg) {
//           if(instance==null) {
//               instance = new Object1Fragment();
//               Bundle args = new Bundle();
//               args.putString("text", arg);
//               instance.setArguments(args);
//
//           }
//            return instance;
//        }

        public static Object1Fragment newInstance(String arg) {
            Object1Fragment fragment = new Object1Fragment();
            Bundle args = new Bundle();
            args.putString("text", arg);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.profile_tabs_replies_view, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            if (getArguments() == null) {
                return;
            }
            String text = getArguments().getString("text", "You haven't posted any replies yet.");
            ((TextView) view.findViewById(R.id.textView)).setText(text);
        }
    }


}