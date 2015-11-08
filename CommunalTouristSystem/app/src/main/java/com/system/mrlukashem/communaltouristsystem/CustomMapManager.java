package com.system.mrlukashem.communaltouristsystem;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.Interfaces.MapManager;
import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.utils.NullChecker;
import com.system.mrlukashem.utils.XmlContentContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 08.11.15.
 */
public class CustomMapManager implements MapManager<PlaceRefBase> {

    private final String ERROR_MAP_NULL = "You have to set GoogleMap instance, It can not be null!";

    private GoogleMap mMap;

    private static CustomMapManager mInstance;

    private List<PlaceRefBase> mPlacesList
            = new ArrayList<>();

    private CustomMapManager() {}

    public static void setGoogleMap(@NonNull GoogleMap map) {
        mInstance.mMap = map;
    }

    public static CustomMapManager getInstance() {
        NullChecker.isNull(mInstance.mMap, mInstance.ERROR_MAP_NULL);

        if(mInstance == null) {
            mInstance = new CustomMapManager();
        }

        return mInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean pushElement(@NonNull PlaceRefBase element, @NonNull String tag) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeElement(@NonNull PlaceRefBase element) {
        return false;
    }

    @Override
    public PlaceRefBase findElementByTag(@NonNull String tag) {
        return null;
    }

    @Override
    public LatLng getCordsByElementTag(@NonNull String tag) {
        PlaceRefBase place = findElementByTag(tag);

        return place.getCords();
    }
}
