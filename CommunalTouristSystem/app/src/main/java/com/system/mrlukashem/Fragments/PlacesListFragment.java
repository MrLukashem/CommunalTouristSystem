package com.system.mrlukashem.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.mrlukashem.communaltouristsystem.R;

public class PlacesListFragment extends Fragment {

    public static PlacesListFragment newInstance(String param1, String param2) {
        PlacesListFragment fragment = new PlacesListFragment();

        return fragment;
    }

    public PlacesListFragment() {
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
        return inflater.inflate(R.layout.fragment_places_list, container, false);
    }
}
