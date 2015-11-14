package com.system.mrlukashem.communaltouristsystem;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.Interfaces.ServicesProvider;
import com.system.mrlukashem.utils.GPSListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public class GPSWayTracker extends GPSListener {

    private static GPSWayTracker mGPSWayTracker = new GPSWayTracker();

    private List<LatLng> mLastKnownTrackedPoints;

    private List<LatLng> mTrackedPoints = new ArrayList<>();

    public GPSWayTracker() {
        super();
    }

    public static GPSWayTracker getInstance() throws NullPointerException {
        if(mGPSWayTracker.mServicesProvider == null) {
            throw new NullPointerException(mGPSWayTracker.NO_SERVICES_PROVIDER_MSG);
        }

        return mGPSWayTracker;
    }

    public static GPSWayTracker getInstance(@NonNull ServicesProvider provider) {
        mGPSWayTracker.mServicesProvider = provider;

        return mGPSWayTracker;
    }

    public List<LatLng> getLastKnownTrackedPoints() {
        if(mLastKnownTrackedPoints == null) {
            return new ArrayList<>();
        }

        return mLastKnownTrackedPoints;
    }

    @Override
    public void stopUsingGPS() {
        super.stopUsingGPS();

        mLastKnownTrackedPoints = mTrackedPoints;
        mTrackedPoints = new ArrayList<>();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng newLocation
                = new LatLng(location.getLatitude(), location.getLongitude());
        mTrackedPoints.add(newLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
