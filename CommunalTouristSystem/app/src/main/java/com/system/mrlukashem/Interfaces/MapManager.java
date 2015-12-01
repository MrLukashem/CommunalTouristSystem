package com.system.mrlukashem.Interfaces;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

import java.util.List;

/**
 * Created by mrlukashem on 08.11.15.
 */
public interface MapManager <T> {

    boolean pushElement(T element, String tag);

    boolean removeElement(String tag);

    T findElementByTag(String tag);

    T findElementByMarker(Marker marker);

    LatLng getCordsByElementTag(String tag);

    boolean pushNewTrackingWay(List<LatLng> points, String tag);

    TrackingWayRefBase findTrackingWayByTag(String tag);

    boolean updateTrackingWay(List<LatLng> points, String tag);

    boolean updateTrackingWay(LatLng point, String tag);

    List<PlaceRefBase> getPlacesList();

    List<TrackingWayRefBase> getTrackingWaysList();

    boolean saveTracingWayInDB(String tag);
}