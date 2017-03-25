package com.example.igor.login.test.myapplication.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.igor.login.test.myapplication.R;
import com.example.igor.login.test.myapplication.clients.HttpClient;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GpsActivity extends BaseActivity {


    @Override
    protected int getDrawerId() {
        return R.id.activity_gps;
    }

    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGPS;
    TextView tvEnabledNet;
    TextView tvStatusNet;
    TextView tvLocationNet;

    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);
        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
        tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
        tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000 * 15, 10, locationListener);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 15, 10,
                    locationListener);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            try {

                showLocation(locationManager.getLastKnownLocation(provider));
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));

            String params = "task_id=1&" +   "user_id=1&" +  "lon=" + location.getLongitude() +  "&lat=" + location.getLatitude() +  "&speed=" + location.getSpeed();
            try {
                HttpClient.sendPostv1("api/v1/location" , params, getApplicationContext());
            }catch (Exception e){

            }

        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvLocationNet.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";

        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    private void checkEnabled() {
        tvEnabledGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNet.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void onClickLocationSettings(View view) {
//        startActivity(new Intent(
//                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        locationManager.removeUpdates(locationListener);
//        locationManager = null;
    }

    ;

}
