package com.system.mrlukashem.nullobjects;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public class NullTrackingWay extends TrackingWayRefBase {

    private final String EMPTY_OBJECT_EXCEPTION = "This is empty object!";

    @Override
    public void pushPoint(LatLng point) {
    }

    @Override
    public void pushPoints(List<LatLng> points) {
    }

    @Override
    public void clearWayData() {
    }

    @Override
    public List<LatLng> getPoints() {
        return new ArrayList<>();
    }

    @Override
    public boolean isNill() {
        return true;
    }
}
