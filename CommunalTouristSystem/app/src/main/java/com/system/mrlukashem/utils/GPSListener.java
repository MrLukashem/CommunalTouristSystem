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

    public abstract void startGPSListening(int minTime, int minDistance, ServicesProvider provider) throws GPSListenerException;

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
