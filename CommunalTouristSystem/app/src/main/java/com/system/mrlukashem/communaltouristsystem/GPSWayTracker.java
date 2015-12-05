package com.system.mrlukashem.communaltouristsystem;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.Interfaces.MapManager;
import com.system.mrlukashem.Interfaces.ServicesProvider;
import com.system.mrlukashem.refbases.PlaceRefBase;
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

    private final String TAG = "GPSWayTracker";

    private LocalBinder mLocalBinder = new LocalBinder();

    private MapManager mMapManager;

    private String mWayTag;

    private void requestLocationUpdates(int minTime, int minDistance) {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                this);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                this);
    }

    public GPSWayTracker() {
        super();
    }

    public void startGPSListening(int minTime, int minDistance, ServicesProvider provider) throws GPSListenerException {
        try {
            if(provider != null) {
                mServicesProvider = provider;
            }

            Log.i(TAG, "startGPSListening");
            mLocationManager = mServicesProvider.getLocationService();
            boolean isPassiveProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!isPassiveProviderEnabled) {
                throw new GPSListenerException(NO_ENABLED_GPS_PROVIDER);
            } else {
                if(mLocationManager != null) {
                    requestLocationUpdates(minTime, minDistance);
                    mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    return;
                }
            }

            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled) {
                throw new GPSListenerException(NO_ENABLED_GPS_PROVIDER);
            } else {
                if(isGPSEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            minTime,
                            minDistance,
                            this);
                }
                else {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            minTime,
                            minDistance,
                            this);
                }
            }
        } catch(Exception exc) {
            Log.e("GPSListener:", "startGPSListening" + exc.toString());
            exc.printStackTrace();

            throw new GPSListenerException(exc.getMessage());
        }
    }

    public void setMapManager(@NonNull MapManager<?> mapManager, @NonNull String wayTag) {
        mMapManager = mapManager;
        mWayTag = wayTag;
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

        Log.i(TAG, "stopUsingGps");
        mLastKnownTrackedPoints = mTrackedPoints;
        mTrackedPoints = new ArrayList<>();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged");
        LatLng newLocation
                = new LatLng(location.getLatitude(), location.getLongitude());
        mTrackedPoints.add(newLocation);

        if(mMapManager != null) {
            boolean result = mMapManager.updateTrackingWay(
                                new LatLng(location.getLatitude(), location.getLongitude()),
                                mWayTag
                            );

            if(!result) {
                List<LatLng> list = new ArrayList<>();
                list.add(new LatLng(location.getLatitude(), location.getLongitude()));

                mMapManager.pushNewTrackingWay(
                        list,
                        mWayTag
                );
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "onStatusChanged");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    public class LocalBinder extends Binder {
        public GPSWayTracker getService() {
            return GPSWayTracker.this;
        }
    }
}
