package com.example.gavs9.sismos;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by nanoj on 5/6/2016.
 */
public class Base extends AppCompatActivity {

    public Location getLocation() {
        current = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);
        if (current == null) {
            String locationProvider = LocationManager.PASSIVE_PROVIDER;
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            current = locationManager.getLastKnownLocation(locationProvider);
        }

        return current;
    }

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected GoogleApiClient mLocationClient;
    protected Location current;
}
