package com.system.mrlukashem.Interfaces;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by mrlukashem on 08.11.15.
 */
public interface MapManager <T> {

    boolean pushElement(T element, String tag);

    boolean removeElement(String tag);

    T findElementByTag(String tag);

    T findElementByMarker(Marker marker);

    LatLng getCordsByElementTag(String tag);
}
