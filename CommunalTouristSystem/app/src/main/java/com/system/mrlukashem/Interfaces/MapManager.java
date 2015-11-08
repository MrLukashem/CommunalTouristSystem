package com.system.mrlukashem.Interfaces;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mrlukashem on 08.11.15.
 */
public interface MapManager <T> {

    boolean pushElement(T element, String tag);

    boolean removeElement(T element);

    T findElementByTag(String tag);

    LatLng getCordsByElementTag(String tag);
}
