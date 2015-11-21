package com.system.mrlukashem.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.mrlukashem.Interfaces.FillWaysListCallback;
import com.system.mrlukashem.communaltouristsystem.R;

public class WaysListFragment extends Fragment {

    private FillWaysListCallback mCallback;

    public WaysListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallback = (FillWaysListCallback)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ways_list, container, false);

        return mCallback.fillWaysList(view);
    }
}
