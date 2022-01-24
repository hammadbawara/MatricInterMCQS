package com.hz_apps.matricintermcqs.saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hz_apps.matricintermcqs.R;

public class Fragment_Saved extends Fragment {

    TabLayout savedTabLayout;
    ViewPager2 savedViewPager;
    String[] tabTitles = {"Saved Tests", "Bookmarked"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__saved, container, false);

        savedTabLayout = view.findViewById(R.id.savedTabLayout);
        savedViewPager = view.findViewById(R.id.savedViewPager);

        SavedFragmentAdapter adapter = new SavedFragmentAdapter(requireActivity());

        savedViewPager.setAdapter(adapter);

        new TabLayoutMediator(savedTabLayout, savedViewPager, (tab, position) -> tab.setText(tabTitles[position])).attach();


        return view;

    }
}