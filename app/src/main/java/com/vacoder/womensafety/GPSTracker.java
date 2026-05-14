```java
package com.vacoder.womensafety;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.vacoder.womensafety.utils.LocationUtils;

public class GpsTracker extends Service implements LocationListener {
    private final Context mContext;
    private Location location;
    private double latitude;
    private double longitude;

    private final LocationManager locationManager;

    public GpsTracker(Context context) {
        this.mContext = context;
        this.locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        getLocation();
    }

    public Location getLocation() {
        try {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
                return null;
            }

            if (isNetworkEnabled) {
                LocationUtils.requestLocationUpdates(mContext, locationManager, LocationManager.NETWORK_PROVIDER, this);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location!= null) {
                    updateCoordinates(location);
                }
            }

            if (isGPSEnabled) {
                if (location == null) {
                    LocationUtils.requestLocationUpdates(mContext, locationManager, LocationManager.GPS_PROVIDER, this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!= null) {
                        updateCoordinates(location);
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }

    private void updateCoordinates(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public void stopUsingGPS() {
        LocationUtils.removeUpdates(locationManager, this);
    }

    public double getLatitude() {
        return location!= null ? location.getLatitude() : latitude;
    }

    public double getLongitude() {
        return location!= null? location.getLongitude() : longitude;
    }

    public boolean canGetLocation() {
        return location!= null;
    }

    public void showSettingsAlert() {
        new AlertDialog.Builder(mContext)
                .setTitle("GPS Settings")
               .setMessage("GPS is not enabled. Do you want to go to settings menu?")
               .setPositiveButton("Settings", (dialog, which) -> mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
               .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
               .show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        updateCoordinates(location);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
```