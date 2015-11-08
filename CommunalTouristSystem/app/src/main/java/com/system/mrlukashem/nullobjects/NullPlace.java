package com.system.mrlukashem.nullobjects;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.refbases.PlaceRefBase;

/**
 * Created by mrlukashem on 08.11.15.
 */
public class NullPlace extends PlaceRefBase {

    private final String NULL_OBJECT_ERROR = "I am null object";

    public NullPlace() {
        super();
    }

    @Override
    public String getName() {
        return NULL_OBJECT_ERROR;
    }

    @Override
    public String getDescription() {
        return NULL_OBJECT_ERROR;
    }

    @Override
    public String getPicPath() {
        return NULL_OBJECT_ERROR;
    }

    @Override
    public LatLng getCords() {
        return new LatLng(.0, .0);
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void setDescription(String description) {
    }

    @Override
    public void setPicPath(String mPath) {
    }

    @Override
    public void setCords(LatLng cords) {
    }

    @Override
    public boolean isNil() {
        return true;
    }
}
