package com.moondroid.awesome_step_event.bottomfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.moondroid.awesome_step_event.MainActivity;
import com.moondroid.awesome_step_event.R;
import com.moondroid.awesome_step_event.tabfragment.TabFragment01;
import com.moondroid.awesome_step_event.tabfragment.TabFragment02;
import com.moondroid.awesome_step_event.tabfragment.TabFragment03;
import com.moondroid.awesome_step_event.tabfragment.TabFragment04;
import com.moondroid.awesome_step_event.view.DialogActivity;
import com.moondroid.awesome_step_event.view.QuestionActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomFragment1 extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager pager;
    private CollectionAdapter adapter;
    private FragmentManager fragmentManager;
    private CircleImageView btnDialog;
    private CircleImageView btnQuestion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.bottom_01_toolbar);
        tabLayout = view.findViewById(R.id.bottom_01_tab_layout);
        pager = view.findViewById(R.id.bottom_01_view_pager);
        fragmentManager = getActivity().getSupportFragmentManager();
        btnDialog = view.findViewById(R.id.menu_ic_show_dialog);
        btnQuestion = view.findViewById(R.id.menu_ic_show_customer_center);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new CollectionAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager, false);

        btnDialog.setOnClickListener(dialogClickListener);
        btnQuestion.setOnClickListener(questionClickListener);


    }

    View.OnClickListener dialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), DialogActivity.class));
        }
    };

    View.OnClickListener questionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), QuestionActivity.class));
        }
    };

    public class CollectionAdapter extends FragmentPagerAdapter {

        Fragment[] fragments;
        String[] titles = new String[]{"일별", "주차별", "월별", "랭킹"};

        public CollectionAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            fragments = new Fragment[]{new TabFragment01(), new TabFragment02(), new TabFragment03(), new TabFragment04()};
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
