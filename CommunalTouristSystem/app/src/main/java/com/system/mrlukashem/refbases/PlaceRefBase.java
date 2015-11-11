package com.system.mrlukashem.refbases;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mrlukashem on 08.11.15.
 */
public abstract class PlaceRefBase {

    protected String mName;

    protected String mDescription;

    protected String mPicPath;

    protected LatLng mCords = null;

    public PlaceRefBase() {}

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getPicPath();

    public abstract LatLng getCords();

    public abstract void setName(String name);

    public abstract void setDescription(String description);

    public abstract void setPicPath(String mPath);

    public abstract void setCords(LatLng cords);

    public abstract boolean isNil();
}
