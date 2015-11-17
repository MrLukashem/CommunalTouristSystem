package com.system.mrlukashem.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.mrlukashem.communaltouristsystem.R;

public class WaysListFragment extends Fragment {

    public static WaysListFragment newInstance() {
        WaysListFragment fragment = new WaysListFragment();

        return fragment;
    }

    public WaysListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ways_list, container, false);
    }
}
