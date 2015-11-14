package com.system.mrlukashem.utils;

import android.app.Service;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.system.mrlukashem.Interfaces.ServicesProvider;

/**
 * Created by mrlukashem on 14.11.15.
 */
public abstract class GPSListener extends Service implements LocationListener {;

    protected final String NO_SERVICES_PROVIDER_MSG = "ServicesProvider is not set!";

    protected final String NO_ENABLED_GPS_PROVIDER = "GPS provider is not enabled";

    protected GPSListener() {}

    protected ServicesProvider mServicesProvider;

    protected LocationManager mLocationManager;

    public void startGPSListening(int minTime, int minDistance) throws GPSListenerException {
        try {
            mLocationManager = mServicesProvider.getLocationService();
            boolean isPassiveProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!isPassiveProviderEnabled) {
                throw new GPSListenerException(NO_ENABLED_GPS_PROVIDER);
            } else {
                if(mLocationManager != null) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER,
                            minTime,
                            minDistance,
                            this);

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

    public void stopUsingGPS() {
        if(mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }

    public static class GPSListenerException extends Exception {

        public GPSListenerException() {
            super();
        }

        public GPSListenerException(String msg) {
            super(msg);
        }
    }
}
