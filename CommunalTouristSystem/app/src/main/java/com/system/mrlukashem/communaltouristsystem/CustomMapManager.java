package com.system.mrlukashem.communaltouristsystem;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.system.mrlukashem.Interfaces.MapManager;
import com.system.mrlukashem.nullobjects.NullPlace;
import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.utils.NullChecker;
import com.system.mrlukashem.utils.XmlContentContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrlukashem on 08.11.15.
 */
public class CustomMapManager implements MapManager<PlaceRefBase> {

    private final String ERROR_MAP_NULL = "You have to set GoogleMap instance, It can not be null!";

    private GoogleMap mMap;

    private static CustomMapManager mInstance = new CustomMapManager();

    private Map<String, Pair<PlaceRefBase, Marker>> mPlacesMap
            = new HashMap<>();

    private CustomMapManager() {}

    public static void setGoogleMap(@NonNull GoogleMap map) {
        mInstance.mMap = map;
    }

    public static CustomMapManager getInstance() {
        NullChecker.isNull(mInstance.mMap, mInstance.ERROR_MAP_NULL);

        return mInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean pushElement(@NonNull PlaceRefBase element, @NonNull String tag) {
        if(!element.isNil()) {
            MarkerOptions options = new MarkerOptions();
            options.position(element.getCords());
            options.title(element.getName());
            options.snippet(element.getDescription());

            Marker marker_result = mMap.addMarker(options);
            mPlacesMap.put(tag, new Pair<>(element, marker_result));

            return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeElement(@NonNull String tag) {
        if(!tag.isEmpty()) {
            mPlacesMap.remove(tag);

            return true;
        }

        return false;
    }

    @Override
    public PlaceRefBase findElementByTag(@NonNull String tag) {
        PlaceRefBase result = mPlacesMap.get(tag).first;
        if(result != null) {
            return result;
        }

        return new NullPlace();
    }

    @Override
    public PlaceRefBase findElementByMarker(@NonNull Marker marker) {

        for(Map.Entry<String, Pair<PlaceRefBase, Marker>> entry : mPlacesMap.entrySet()) {
            if(marker == entry.getValue().second) {
                return entry.getValue().first;
            }
        }

        return new NullPlace();
    }

    @Override
    public LatLng getCordsByElementTag(@NonNull String tag) {
        PlaceRefBase place = findElementByTag(tag);

        return place.getCords();
    }
}
