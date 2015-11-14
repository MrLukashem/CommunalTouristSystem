package com.system.mrlukashem.communaltouristsystem;

import com.google.android.gms.maps.model.LatLng;

import com.system.mrlukashem.refbases.TrackingWayRefBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public class TrackingWay extends TrackingWayRefBase {

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

    public List<LatLng> getPoints() {
        return mWayPoints;
    }

    @Override
    public boolean isNill() {
        return false;
    }
}
