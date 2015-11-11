package com.system.mrlukashem.communaltouristsystem;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.system.mrlukashem.refbases.PlaceRefBase;

/**
 * Created by mrlukashem on 11.11.15.
 */
public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        CustomMapManager customMapManager = CustomMapManager.getInstance();
        PlaceRefBase place = customMapManager.findElementByMarker(marker);


        return null;
    }
}
