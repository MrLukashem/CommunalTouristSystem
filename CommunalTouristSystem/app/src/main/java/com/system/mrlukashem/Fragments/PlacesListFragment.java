package com.system.mrlukashem.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.mrlukashem.Interfaces.FillPlacesListCallback;
import com.system.mrlukashem.communaltouristsystem.R;

public class PlacesListFragment extends Fragment {

    private FillPlacesListCallback mCallback;

    public PlacesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallback = (FillPlacesListCallback)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);

        return mCallback.fillPlacesList(view);
    }
}
