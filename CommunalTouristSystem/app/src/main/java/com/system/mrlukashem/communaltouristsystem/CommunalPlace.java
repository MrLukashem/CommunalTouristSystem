package com.system.mrlukashem.communaltouristsystem;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.refbases.PlaceRefBase;

/**
 * Created by mrlukashem on 08.11.15.
 */
public class CommunalPlace extends PlaceRefBase {

    private final String EMPTY_STRING = "";

    public CommunalPlace() {
        super();

        mName = EMPTY_STRING;
        mDescription = EMPTY_STRING;
        mPicPath = EMPTY_STRING;
        mCords = null;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public String getPicPath() {
        return mPicPath;
    }

    @Override
    public LatLng getCords() {
        return mCords;
    }

    @Override
    public void setName(@NonNull String name) {
        if(!name.isEmpty()) {
            mName = name;
        }
    }

    @Override
    public void setDescription(@NonNull String description) {
        if(!description.isEmpty()) {
            mDescription = description;
        }
    }

    @Override
    public void setPicPath(@NonNull String path) {
        if(!path.isEmpty()) {
            mPicPath = path;
        }
    }

    @Override
    public void setCords(@NonNull LatLng cords) {
        mCords = cords;
    }

    @Override
    public boolean isNil() {
        return false;
    }
}
