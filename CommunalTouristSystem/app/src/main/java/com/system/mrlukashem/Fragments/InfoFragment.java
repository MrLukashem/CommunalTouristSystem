package com.system.mrlukashem.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.mrlukashem.Interfaces.FillContentCallback;
import com.system.mrlukashem.communaltouristsystem.R;

public class InfoFragment extends Fragment {

    private FillContentCallback mFillContentCallback;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mFillContentCallback = (FillContentCallback)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.information_view, container, false);
        mFillContentCallback.invoke(view);

        return view;
    }


}
