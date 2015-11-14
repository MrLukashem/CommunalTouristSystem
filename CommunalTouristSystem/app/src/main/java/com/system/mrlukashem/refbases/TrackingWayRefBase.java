package com.system.mrlukashem.refbases;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public abstract class TrackingWayRefBase {

    private List<LatLng> mWayPoints = new ArrayList<>();

    private String mTag = "";

    public String getTag() {
        return mTag;
    }

    public void setTag(@NonNull String tag) {
        mTag = tag;
    }

    public abstract void pushPoint(LatLng point);

    public abstract void pushPoints(List<LatLng> points);

    public abstract void clearWayData();

    public abstract List<LatLng> getPoints();

    public abstract boolean isNill();
}
