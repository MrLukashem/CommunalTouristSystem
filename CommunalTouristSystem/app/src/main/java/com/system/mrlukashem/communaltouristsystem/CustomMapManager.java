package com.system.mrlukashem.communaltouristsystem;

import android.graphics.Color;
import android.util.Pair;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.system.mrlukashem.Interfaces.MapManager;
import com.system.mrlukashem.Interfaces.MoveMapCameraCB;
import com.system.mrlukashem.datebase.DatabaseWrapper;
import com.system.mrlukashem.nullobjects.NullPlace;
import com.system.mrlukashem.nullobjects.NullTrackingWay;
import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.refbases.TrackingWayRefBase;
import com.system.mrlukashem.utils.NullChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrlukashem on 08.11.15.
 */
public class CustomMapManager implements MapManager<PlaceRefBase>, GoogleMap.OnCameraChangeListener {

    private final String ERROR_MAP_NULL = "You have to set GoogleMap instance, It can not be null!";

    private final int DEFAULT_POLYLINE_WIDTH = 30;

    private GoogleMap mMap;

    private float mLastZoomLevel;

    private float mPolylineSize = .0f;

    private static CustomMapManager mInstance = new CustomMapManager();

    private Map<String, Pair<PlaceRefBase, Marker>> mPlacesMap
            = new HashMap<>();

    private Map<String, Pair<TrackingWayRefBase, Polyline>> mTrackingWayMap
            = new HashMap<>();

    private Map<String, Marker> mWayTagMarkerMap
            = new HashMap<>();

    private MoveMapCameraCB mMoveCameraCB;

    private CustomMapManager() {}

    private void refreshPolylinesOnMap(float polylineWidth) {
        for(Map.Entry<String, Pair<TrackingWayRefBase, Polyline>> entry : mTrackingWayMap.entrySet()) {
            entry.getValue().second.setWidth(polylineWidth);
        }
    }

    public static void setMoveCB(MoveMapCameraCB cb) {
        mInstance.mMoveCameraCB = cb;
    }

    public static void setGoogleMap(@NonNull GoogleMap map) {
        mInstance.mMap = map;
        mInstance.mMap.setOnCameraChangeListener(mInstance);

        float zoom = mInstance.mMap.getCameraPosition().zoom;
        mInstance.mPolylineSize = 700.0f / zoom;
    }

    public static CustomMapManager getInstance() throws NullPointerException {
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
            if(marker.equals(entry.getValue().second)) {
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

    @Override
    public boolean pushNewTrackingWay(@NonNull List<LatLng> points, @NonNull String tag, @NonNull String desc) {
        if(points.isEmpty()) {
            return false;
        }

        float size = mPolylineSize;
        PolylineOptions options
                = new PolylineOptions()
                .addAll(points)
                .width(DEFAULT_POLYLINE_WIDTH)
                .color(Color.RED);

        Polyline polyline = mMap.addPolyline(options);
        TrackingWayRefBase trackingWay = new TrackingWay();
        trackingWay.pushPoints(points);

        mTrackingWayMap.remove(tag);
        if(mTrackingWayMap.put(tag, new Pair<>(trackingWay, polyline)) == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean pushNewTrackingWay(@NonNull TrackingWayRefBase way) {
        if(way.isNill()) {
            return false;
        }

        float size = mPolylineSize;
        PolylineOptions options
                = new PolylineOptions()
                .addAll(way.getPoints())
                .width(DEFAULT_POLYLINE_WIDTH)
                .color(Color.RED);

        Polyline polyline = mMap.addPolyline(options);

        if(mWayTagMarkerMap.get(way.getTag()) == null) {
            String title = way.getTitle();
            MarkerOptions markerOptions
                    = new MarkerOptions()
                    .position(way.getPoints().get(0))
                    .title(way.getTitle())
                    .snippet(way.getDescription())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            Marker marker =  mMap.addMarker(markerOptions);
            mWayTagMarkerMap.put(way.getTag(), marker);
        }

        TrackingWayRefBase trackingWay = way;

        mTrackingWayMap.remove(way.getTag());
        if(mTrackingWayMap.put(way.getTag() , new Pair<>(trackingWay, polyline)) == null) {
            return false;
        }

        return true;
    }

    @Override
    public TrackingWayRefBase findTrackingWayByTag(@NonNull String tag) {
        Pair<TrackingWayRefBase, Polyline> result = mTrackingWayMap.get(tag);

        if(result == null) {
            return new NullTrackingWay();
        }

        return result.first;
    }

    @Override
    public boolean updateTrackingWay(List<LatLng> points, String tag) {
        Pair<TrackingWayRefBase, Polyline> result = mTrackingWayMap.get(tag);

        if(result == null || result.first.isNill()) {
            return false;
        }

        try {
            result.second.remove();

            List<LatLng> updatedPoints = result.first.getPoints();
            updatedPoints.addAll(points);

            mTrackingWayMap.remove(tag);
            if(mTrackingWayMap.get(tag) == null) {
                return pushNewTrackingWay(result.first);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();

            return false;
        }

        return false;
    }

    @Override
    public boolean updateTrackingWay(LatLng point, String tag) {
        List<LatLng> list = new ArrayList<>();
        list.add(point);

        return updateTrackingWay(list, tag);
    }


    @Override
    public List<PlaceRefBase> getPlacesList() {
        List<PlaceRefBase> list = new ArrayList<>();

        for(Map.Entry<String, Pair<PlaceRefBase, Marker>> entry : mPlacesMap.entrySet()) {
            list.add(entry.getValue().first);
        }

        return list;
    }

    @Override
    public List<TrackingWayRefBase> getTrackingWaysList() {
        List<TrackingWayRefBase> list = new ArrayList<>();

        for(Map.Entry<String, Pair<TrackingWayRefBase, Polyline>> entry : mTrackingWayMap.entrySet()) {
           list.add(entry.getValue().first);
        }

        return list;
    }

    @Override
    public boolean saveTracingWayInDB(@NonNull String tag, @NonNull DatabaseWrapper databaseWrapper) {
        TrackingWayRefBase trackingWayRefBase = mTrackingWayMap.get(tag).first;
        if(trackingWayRefBase == null) {
            return false;
        }

        boolean result;
        try {
            databaseWrapper.delete(tag);
            result = databaseWrapper.put(trackingWayRefBase);
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        return result;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        float diff = mLastZoomLevel - cameraPosition.zoom;
        if(diff < 0) {
            mPolylineSize -= diff; //TODO: better algorithm.
        } else if(diff > 0) {
            mPolylineSize += diff;
        }

        //refreshPolylinesOnMap(mPolylineSize);
    }

    public boolean isTrackingWayMarker(Marker marker) {
        for(Map.Entry<String, Marker> entry : mWayTagMarkerMap.entrySet()) {
            if(entry.getValue().equals(marker)) {
                return true;
            }
        }

        return false;
    }

    public void moveCamera(LatLng latLng) {
        mMoveCameraCB.move(latLng);
    }
}
