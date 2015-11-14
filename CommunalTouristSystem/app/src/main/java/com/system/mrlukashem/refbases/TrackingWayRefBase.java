package com.system.mrlukashem.refbases;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public abstract class TrackingWayRefBase {

    private List<LatLng> mWayPoints = new ArrayList<>();

    public void pushPoint(LatLng point) {
        mWayPoints.add(point);
    }

    public void pushPoints(List<LatLng> points) {
        mWayPoints.addAll(points);
    }

    public void clearWayData() {
        mWayPoints.clear();
    }

    public abstract boolean isNill();
}
