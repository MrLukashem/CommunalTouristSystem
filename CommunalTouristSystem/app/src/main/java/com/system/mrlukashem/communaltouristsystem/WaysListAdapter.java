package com.system.mrlukashem.communaltouristsystem;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.system.mrlukashem.refbases.TrackingWayRefBase;

/**
 * Created by mrlukashem on 17.11.15.
 */
public class WaysListAdapter extends ArrayAdapter<TrackingWayRefBase> {

    public WaysListAdapter(Context context, int resource, TrackingWayRefBase[] elements) {
        super(context, resource);
    }
}
