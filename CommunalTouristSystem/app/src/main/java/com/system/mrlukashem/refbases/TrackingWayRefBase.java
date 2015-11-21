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

    private String mTitle;

    private String mDescription;

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

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
